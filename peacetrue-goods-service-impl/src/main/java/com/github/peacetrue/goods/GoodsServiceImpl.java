package com.github.peacetrue.goods;

import com.github.peacetrue.core.IdCapable;
import com.github.peacetrue.core.OperatorCapable;
import com.github.peacetrue.core.Operators;
import com.github.peacetrue.result.ResultType;
import com.github.peacetrue.result.exception.DataResultException;
import com.github.peacetrue.result.exception.ResultException;
import com.github.peacetrue.spring.data.domain.SortUtils;
import com.github.peacetrue.spring.data.relational.core.query.CriteriaUtils;
import com.github.peacetrue.spring.data.relational.core.query.QueryUtils;
import com.github.peacetrue.spring.data.relational.core.query.UpdateUtils;
import com.github.peacetrue.spring.util.BeanUtils;
import com.github.peacetrue.util.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.PayloadApplicationEvent;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityOperations;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.data.relational.core.query.Update;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.function.Tuple2;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Function;

/**
 * 商品服务实现
 *
 * @author xiayx
 */
@Slf4j
@Service
public class GoodsServiceImpl implements GoodsService {

    public static final String[] PROPERTY_DETAIL = BeanUtils.getPropertyNames(Goods.class);
    public static final String[] PROPERTY_LIST = Arrays.stream(PROPERTY_DETAIL).filter(item -> !"detail".equals(item)).toArray(String[]::new);

    @Autowired
    private R2dbcEntityOperations entityOperations;
    @Autowired
    private ApplicationEventPublisher eventPublisher;

    public static Criteria buildCriteria(GoodsQuery params) {
        return CriteriaUtils.and(
                CriteriaUtils.nullableCriteria(CriteriaUtils.smartIn("id"), params::getId),
                CriteriaUtils.nullableCriteria(Criteria.where("name")::like, value -> "%" + value + "%", params::getName),
                CriteriaUtils.nullableCriteria(Criteria.where("display")::is, params::getDisplay),
                CriteriaUtils.nullableCriteria(Criteria.where("serialNumber")::greaterThanOrEquals, params.getSerialNumber()::getLowerBound),
                CriteriaUtils.nullableCriteria(Criteria.where("serialNumber")::lessThanOrEquals, params.getSerialNumber()::getUpperBound),
                CriteriaUtils.nullableCriteria(Criteria.where("goodsCount")::greaterThanOrEquals, params.getGoodsCount()::getLowerBound),
                CriteriaUtils.nullableCriteria(Criteria.where("goodsCount")::lessThanOrEquals, params.getGoodsCount()::getUpperBound),
                CriteriaUtils.nullableCriteria(Criteria.where("creatorId")::is, params::getCreatorId),
                CriteriaUtils.nullableCriteria(Criteria.where("createdTime")::greaterThanOrEquals, params.getCreatedTime()::getLowerBound),
                CriteriaUtils.nullableCriteria(Criteria.where("createdTime")::lessThan, DateUtils.DATE_CELL_EXCLUDE, params.getCreatedTime()::getUpperBound),
                CriteriaUtils.nullableCriteria(Criteria.where("modifierId")::is, params::getModifierId),
                CriteriaUtils.nullableCriteria(Criteria.where("modifiedTime")::greaterThanOrEquals, params.getModifiedTime()::getLowerBound),
                CriteriaUtils.nullableCriteria(Criteria.where("modifiedTime")::lessThan, DateUtils.DATE_CELL_EXCLUDE, params.getModifiedTime()::getUpperBound)
        );
    }

    @Override
    @Transactional
    public Mono<GoodsVO> add(GoodsAdd params) {
        log.info("新增商品信息[{}]", params);
        if (params.getDisplay() == null) params.setDisplay(GoodsDisplay.OFFLINE);
        BeanUtils.setDefaultValue(params);
        Goods entity = BeanUtils.map(params, Goods.class);
        Operators.setCreateModify(params, entity);
        return GoodsServiceImpl.getNextSerialNumber(entityOperations.getDatabaseClient())
                .doOnNext(entity::setSerialNumber)
                .flatMap(serialNumber -> entityOperations.insert(entity))
                .map(item -> BeanUtils.map(item, GoodsVO.class))
                .doOnNext(item -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(item, params)))
                ;
    }

    private static Mono<Long> getNextSerialNumber(DatabaseClient databaseClient) {
        //TODO https://github.com/mirromutth/r2dbc-mysql/issues/180
        return databaseClient
                .sql("select IFNULL(MAX(serial_number),0) from goods")
                .map(row -> row.get(0, BigDecimal.class))
                .first()
                .map(BigDecimal::longValue)
                .defaultIfEmpty(0L)
                .map(serialNumber -> serialNumber + 100)
                .doOnNext(serialNumber -> log.debug("取得下一个可用序号为[{}]", serialNumber))
                ;
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<Page<GoodsVO>> query(GoodsQuery params, Pageable pageable, String... projection) {
        log.info("分页查询商品信息[{}]", params);
        Criteria where = buildCriteria(params);
        return entityOperations.count(Query.query(where).columns(PROPERTY_LIST), Goods.class)
                .filter(total -> total > 0)
                .<Page<GoodsVO>>flatMap(total -> {
                    Query query = Query.query(where).with(pageable).sort(pageable.getSortOr(SortUtils.SORT_SERIAL_NUMBER));
                    return entityOperations.select(query, Goods.class)
                            .map(item -> BeanUtils.map(item, GoodsVO.class))
                            .collectList()
                            .doOnNext(item -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(item, params)))
                            .map(item -> new PageImpl<>(item, pageable, total));
                })
                .switchIfEmpty(Mono.just(new PageImpl<>(Collections.emptyList(), pageable, 0L)))
                ;
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<GoodsVO> query(GoodsQuery params, Sort sort, String... projection) {
        log.info("全量查询商品信息[{}]", params);
        Criteria where = buildCriteria(params);
        Query query = Query.query(where).columns(PROPERTY_LIST).sort(sort).limit(100);
        return entityOperations.select(query, Goods.class)
                .map(item -> BeanUtils.map(item, GoodsVO.class))
                .collectList()
                .doOnNext(item -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(item, params)))
                .flatMapIterable(Function.identity())
                ;
    }

    @Override
    @Transactional(readOnly = true)
    public Flux<GoodsVO> query(GoodsQuery params, String... projection) {
        return query(params, SortUtils.SORT_SERIAL_NUMBER, projection);
    }

    @Override
    @Transactional(readOnly = true)
    public Mono<GoodsVO> get(GoodsGet params, String... projection) {
        log.info("获取商品信息[{}]", params);
//        Criteria where = CriteriaUtils.and(
//                CriteriaUtils.nullableCriteria(Criteria.where("id")::is, params::getId),
//        );
//        Criteria where = Criteria.where("id").is(params.getId());
        Query query = QueryUtils.id(params::getId).columns(PROPERTY_DETAIL);
        return entityOperations.selectOne(query, Goods.class)
                .map(item -> BeanUtils.map(item, GoodsVO.class))
                .doOnNext(item -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(item, params)))
                ;
    }

    @Override
    @Transactional
    public Mono<Integer> modify(GoodsModify params) {
        log.info("修改商品信息[{}]", params);
        return this.modifyGeneric(params);
    }

    private <T extends IdCapable<Long> & OperatorCapable<Long>> Mono<Integer> modifyGeneric(T params) {
        Query idQuery = QueryUtils.id(params::getId).columns(PROPERTY_LIST);
        return entityOperations.selectOne(idQuery, Goods.class)
                .doOnNext(entity -> {
                    if (entity.getOrderCount() > 0) {
                        throw new DataResultException(ResultType.failure.name(),
                                String.format("商品[%s]已关联订单信息，不允许修改", entity.getName()),
                                entity.getOrderCount());
                    }
                    if (entity.getDisplay() == GoodsDisplay.ONLINE) {
                        throw new ResultException(ResultType.failure.name(),
                                String.format("商品[%s]已上架，不允许修改，请下架后再修改", entity.getName()));
                    }
                })
                .zipWhen(entity -> {
                    Goods modify = BeanUtils.map(params, Goods.class);
                    modify.setModifierId(params.getOperatorId());
                    modify.setModifiedTime(LocalDateTime.now());
                    Update update = UpdateUtils.selectiveUpdateFromExample(modify);
                    return entityOperations.update(idQuery, update, Goods.class);
                })
                .map(tuple2 -> {
                    GoodsVO vo = BeanUtils.map(tuple2.getT1(), GoodsVO.class);
                    BeanUtils.copyProperties(params, vo, BeanUtils.EMPTY_PROPERTY_VALUE);
                    eventPublisher.publishEvent(new PayloadApplicationEvent<>(vo, params));
                    return tuple2.getT2();
                })
                .defaultIfEmpty(0);
    }

    @Override
    @Transactional
    public Mono<Integer> setDisplay(GoodsSetDisplay params) {
        log.info("[{}]商品[{}]", params.getDisplay().getName(), params.getId());
        Query idQuery = QueryUtils.id(params::getId);
        return entityOperations.selectOne(idQuery.columns(PROPERTY_LIST), Goods.class)
                .doOnNext(entity -> log.debug("取得商品详情[{}]", entity))
                .filter(entity -> !params.getDisplay().equals(entity.getDisplay()))
                .flatMap(vo -> {
                    log.debug("[{}]商品[{}]，执行 SQL 更新", params.getDisplay().getName(), params.getId());
                    Update update = UpdateUtils.setModify(Update.update("display", params.getDisplay()), params);
                    return entityOperations.update(idQuery, update, Goods.class);
                })
                .defaultIfEmpty(0)
                ;
    }

    @Override
    @Transactional
    public Mono<Integer> setPrice(GoodsSetPrice params) {
        log.info("设置商品[{}]的价格为[{}]", params.getId(), params.getPrice());
        Query idQuery = QueryUtils.id(params::getId);
        return entityOperations.selectOne(idQuery.columns(PROPERTY_LIST), Goods.class)
                .doOnNext(entity -> log.debug("取得改价商品详情[{}]", entity))
                .filter(entity -> !params.getPrice().equals(entity.getPrice()))
                .flatMap(vo -> {
                    log.debug("执行 SQL 更新，设置商品[{}]的价格为[{}]", params.getPrice(), params.getId());
                    Update update = UpdateUtils.setModify(Update.update("price", params.getPrice()), params);
                    return entityOperations.update(idQuery, update, Goods.class);
                })
                .defaultIfEmpty(0)
                ;
    }

    @Override
    @Transactional
    public Mono<Integer> delete(GoodsDelete params) {
        log.info("删除商品信息[{}]", params);
        Query idQuery = QueryUtils.id(params::getId);
        return entityOperations.selectOne(idQuery, Goods.class)
                .map(item -> BeanUtils.map(item, GoodsVO.class))
                .zipWhen(region -> entityOperations.delete(idQuery, Goods.class))
                .doOnNext(tuple2 -> eventPublisher.publishEvent(new PayloadApplicationEvent<>(tuple2.getT1(), params)))
                .map(Tuple2::getT2)
                .defaultIfEmpty(0);
    }

}
