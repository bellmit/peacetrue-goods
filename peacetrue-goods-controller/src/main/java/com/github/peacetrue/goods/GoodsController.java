package com.github.peacetrue.goods;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

/**
 * 商品控制器
 *
 * @author xiayx
 */
@Slf4j
@RestController
@RequestMapping(value = "/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @PostMapping(consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<GoodsVO> addByForm(GoodsAdd params) {
        log.info("新增商品信息(请求方法+表单参数)[{}]", params);
        return goodsService.add(params);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<GoodsVO> addByJson(@RequestBody GoodsAdd params) {
        log.info("新增商品信息(请求方法+JSON参数)[{}]", params);
        return goodsService.add(params);
    }

    @GetMapping(params = "page")
    public Mono<Page<GoodsVO>> query(GoodsQuery params, Pageable pageable, String... projection) {
        log.info("分页查询商品信息(请求方法+参数变量)[{}]", params);
        return goodsService.query(params, pageable, projection);
    }

    @GetMapping
    public Flux<GoodsVO> query(GoodsQuery params, Sort sort, String... projection) {
        log.info("全量查询商品信息(请求方法+参数变量)[{}]", params);
        return goodsService.query(params, sort, projection);
    }

    @GetMapping("/{id}")
    public Mono<GoodsVO> getByUrlPathVariable(@PathVariable Long id, String... projection) {
        log.info("获取商品信息(请求方法+路径变量)详情[{}]", id);
        return goodsService.get(new GoodsGet(id), projection);
    }

    @RequestMapping("/get")
    public Mono<GoodsVO> getByPath(GoodsGet params, String... projection) {
        log.info("获取商品信息(请求路径+参数变量)详情[{}]", params);
        return goodsService.get(params, projection);
    }

    @PutMapping(value = {"", "/*"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<Integer> modifyByForm(GoodsModify params) {
        log.info("修改商品信息(请求方法+表单参数)[{}]", params);
        return goodsService.modify(params);
    }

    @PutMapping(value = {"", "/*"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Integer> modifyByJson(@RequestBody GoodsModify params) {
        log.info("修改商品信息(请求方法+JSON参数)[{}]", params);
        return goodsService.modify(params);
    }

    @DeleteMapping("/{id}")
    public Mono<Integer> deleteByUrlPathVariable(@PathVariable Long id) {
        log.info("删除商品信息(请求方法+URL路径变量)[{}]", id);
        return goodsService.delete(new GoodsDelete(id));
    }

    @DeleteMapping(params = "id")
    public Mono<Integer> deleteByUrlParamVariable(GoodsDelete params) {
        log.info("删除商品信息(请求方法+URL参数变量)[{}]", params);
        return goodsService.delete(params);
    }

    @RequestMapping(path = "/delete")
    public Mono<Integer> deleteByPath(GoodsDelete params) {
        log.info("删除商品信息(请求路径+URL参数变量)[{}]", params);
        return goodsService.delete(params);
    }

    /*------ 商家接口 -----*/
    @PutMapping(value = {"/{id}/display/{display}"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<Integer> setDisplayByForm(@PathVariable Long id, @PathVariable GoodsDisplay display) {
        GoodsSetDisplay params = new GoodsSetDisplay(id, display);
        log.info("设置商品展示状态(请求方法+表单参数)[{}]", params);
        return goodsService.setDisplay(params);
    }

    @PutMapping(value = {"/display"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Integer> setDisplayByJson(@RequestBody GoodsSetDisplay params) {
        log.info("设置商品展示状态(请求方法+JSON参数)[{}]", params);
        return goodsService.setDisplay(params);
    }

    @PutMapping(value = {"/{id}/price/{price}"}, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public Mono<Integer> setPriceByForm(@PathVariable Long id, @PathVariable BigDecimal price) {
        GoodsSetPrice params = new GoodsSetPrice(id, price);
        log.info("设置商品价格(请求方法+表单参数)[{}]", params);
        return goodsService.setPrice(params);
    }

    @PutMapping(value = {"/price"}, consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Integer> setPriceByJson(@RequestBody GoodsSetPrice params) {
        log.info("设置商品价格(请求方法+JSON参数)[{}]", params);
        return goodsService.setPrice(params);
    }

}
