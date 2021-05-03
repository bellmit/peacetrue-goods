package com.github.peacetrue.goods;

import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import reactivefeign.spring.config.ReactiveFeignClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.Nullable;

/**
 * 商品客户端
 *
 * @author xiayx
 */
@ReactiveFeignClient(name = "peacetrue-goods", url = "${peacetrue.Goods.url:${peacetrue.server.url:}}")
public interface GoodsServiceClient {

    @PostMapping(value = "/goods")
    Mono<GoodsVO> add(GoodsAdd params);

    @GetMapping(value = "/goods", params = "page")
    Mono<Page<GoodsVO>> query(@Nullable @SpringQueryMap GoodsQuery params, @Nullable Pageable pageable, @SpringQueryMap String... projection);

    @GetMapping(value = "/goods", params = "sort")
    Flux<GoodsVO> query(@SpringQueryMap GoodsQuery params, Sort sort, @SpringQueryMap String... projection);

    @GetMapping(value = "/goods")
    Flux<GoodsVO> query(@SpringQueryMap GoodsQuery params, @SpringQueryMap String... projection);

    @GetMapping(value = "/goods/get")
    Mono<GoodsVO> get(@SpringQueryMap GoodsGet params, @SpringQueryMap String... projection);

    @PutMapping(value = "/goods")
    Mono<Integer> modify(GoodsModify params);

    @DeleteMapping(value = "/goods/delete")
    Mono<Integer> delete(@SpringQueryMap GoodsDelete params);

}
