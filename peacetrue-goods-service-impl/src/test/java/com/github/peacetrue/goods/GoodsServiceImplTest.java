package com.github.peacetrue.goods;

import com.github.peacetrue.spring.util.BeanUtils;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import reactor.test.StepVerifier;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author : xiayx
 * @since : 2020-05-22 16:43
 **/
@SpringBootTest(classes = TestServiceGoodsAutoConfiguration.class)
@ActiveProfiles("goods-service-test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GoodsServiceImplTest {

    public static final EasyRandomParameters parameters = new EasyRandomParameters().randomize(Serializable.class, () -> 1L);
    public static final EasyRandom EASY_RANDOM = new EasyRandom(parameters);
    public static final GoodsAdd ADD = EASY_RANDOM.nextObject(GoodsAdd.class);
    public static final GoodsModify MODIFY = EASY_RANDOM.nextObject(GoodsModify.class);
    public static GoodsVO vo;

    static {
        //price=0.723174202997146853277854461339302361011505126953125
        //price=0.72
        ADD.setPrice(new BigDecimal("1.00"));
        ADD.setDisplay(GoodsDisplay.OFFLINE);
        ADD.setOperatorId(1L);
        MODIFY.setDisplay(GoodsDisplay.OFFLINE);
        MODIFY.setOperatorId(1L);
    }

    @Autowired
    private GoodsServiceImpl service;

    @Test
    @Order(10)
    void add() {
        service.add(ADD)
                .as(StepVerifier::create)
                .assertNext(data -> {
                    Assertions.assertEquals(data.getCreatorId(), ADD.getOperatorId());
                    vo = data;
                })
                .verifyComplete();
    }

    @Test
    @Order(20)
    void queryForPage() {
        GoodsQuery params = BeanUtils.map(vo, GoodsQuery.class);
        service.query(params, PageRequest.of(0, 10))
                .as(StepVerifier::create)
                .assertNext(page -> Assertions.assertEquals(1, page.getTotalElements()))
                .verifyComplete();
    }

    @Test
    @Order(30)
    void queryForList() {
        GoodsQuery params = BeanUtils.map(vo, GoodsQuery.class);
        service.query(params)
                .as(StepVerifier::create)
                .expectNextCount(1)
                .verifyComplete();
    }

    @Test
    @Order(40)
    void get() {
        GoodsGet params = BeanUtils.map(vo, GoodsGet.class);
        service.get(params)
                .as(StepVerifier::create)
                .assertNext(item -> Assertions.assertEquals(vo.getId(), item.getId()))
                .verifyComplete();
    }

    @Test
    @Order(50)
    void modify() {
        GoodsModify params = MODIFY;
        params.setId(vo.getId());
        service.modify(params)
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();
    }

    @Test
    @Order(50)
    void setDisplay() {
        GoodsSetDisplay params = new GoodsSetDisplay();
        params.setId(vo.getId());
        params.setDisplay(GoodsDisplay.ONLINE);
        params.setOperatorId(1L);
        service.setDisplay(params)
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();
    }

    @Test
    @Order(60)
    void delete() {
        GoodsDelete params = new GoodsDelete(vo.getId());
        service.delete(params)
                .as(StepVerifier::create)
                .expectNext(1)
                .verifyComplete();
    }
}
