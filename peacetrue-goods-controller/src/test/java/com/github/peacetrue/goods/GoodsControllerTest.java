package com.github.peacetrue.goods;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

/**
 * @author xiayx
 */
@SpringBootTest(classes = TestControllerGoodsAutoConfiguration.class)
@ActiveProfiles({"goods-controller-test", "goods-service-test"})
@AutoConfigureWebTestClient
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class GoodsControllerTest {

    @Autowired
    private WebTestClient client;

    @Test
    @Order(10)
    void add() {
        this.client.post().uri("/goods")
                .bodyValue(GoodsServiceImplTest.ADD)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(GoodsVO.class).value((Consumer<GoodsVO>) vo -> GoodsServiceImplTest.vo = vo);
    }

    @Test
    @Order(20)
    void queryForPage() {
        this.client.get()
                .uri("/goods?page=0")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.totalElements").isEqualTo(1);
    }

    @Test
    @Order(30)
    void queryForList() {
        this.client.get()
                .uri("/goods")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody().jsonPath("$.size()").isEqualTo(1);
    }

    @Test
    @Order(40)
    void get() {
        this.client.get()
                .uri("/goods/{0}", GoodsServiceImplTest.vo.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(GoodsVO.class)
                .value(GoodsVO::getCreatorId, Matchers.equalTo(GoodsServiceImplTest.vo.getCreatorId()))
        ;
    }


    @Test
    @Order(50)
    void modify() {
        GoodsModify modify = GoodsServiceImplTest.MODIFY;
        modify.setId(GoodsServiceImplTest.vo.getId());
        this.client.put()
                .uri("/goods/{id}", GoodsServiceImplTest.vo.getId())
                .bodyValue(modify)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Integer.class).isEqualTo(1);
    }

    @Test
    @Order(55)
    void setDisplay() {
        this.client.put()
                .uri("/goods/{id}/display/{display}?operatorId=1", GoodsServiceImplTest.vo.getId(), GoodsServiceImplTest.MODIFY.getDisplay().ordinal())
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Integer.class).isEqualTo(0);
    }

    @Test
    @Order(60)
    void delete() {
        this.client.delete()
                .uri("/goods/{0}", GoodsServiceImplTest.vo.getId())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody(Integer.class).isEqualTo(1);
    }

}
