package com.github.peacetrue.goods;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 商品服务接口
 *
 * @author xiayx
 */
public interface GoodsService {

    /** 新增 */
    Mono<GoodsVO> add(GoodsAdd params);

    /** 分页查询 */
    Mono<Page<GoodsVO>> query(GoodsQuery params, Pageable pageable, String... projection);

    /** 全量查询 */
    Flux<GoodsVO> query(GoodsQuery params, Sort sort, String... projection);

    /** 全量查询 */
    Flux<GoodsVO> query(GoodsQuery params, String... projection);

    /** 获取 */
    Mono<GoodsVO> get(GoodsGet params, String... projection);

    /** 修改 */
    Mono<Integer> modify(GoodsModify params);

    /** 设置展示状态（上下架） */
    Mono<Integer> setDisplay(GoodsSetDisplay params);

    /** 设置价格 */
    Mono<Integer> setPrice(GoodsSetPrice params);

    /** 删除 */
    Mono<Integer> delete(GoodsDelete params);

}
