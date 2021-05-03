package com.github.peacetrue.goods;

import io.r2dbc.spi.ConnectionFactories;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.Statement;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

/**
 * @author : xiayx
 * @since : 2021-04-25 13:47
 **/
@Slf4j
class R2dbcTest {

    @Test
    void normal() {
        ConnectionFactory connectionFactory = ConnectionFactories.get(
                "r2dbc:mysql://root:12345678@127.0.0.1:3306/common"
        );
        Object serialNumber = Mono.from(connectionFactory.create())
                .flatMap(connection -> {
                    String sql = "select MAX(serial_number) from goods";
                    Statement statement = connection.createStatement(sql);
                    return Mono.from(statement.execute());
                })
                .flatMap(result -> Mono.from(result.map((row, meta) -> row.get(0))))
                .block();
        log.info("get the serialNumber: {}", serialNumber);
    }

    @Test
    void basic() {
        ConnectionFactory connectionFactory = ConnectionFactories.get(
                "r2dbc:mysql://root:12345678@127.0.0.1:3306/common"
        );
        Object serialNumber = Mono.from(connectionFactory.create())
                .flatMap(connection -> {
                    String sql = "select IFNULL(MAX(serial_number),0) from goods";
                    Statement statement = connection.createStatement(sql);
                    return Mono.from(statement.execute());
                })
                .flatMap(result -> Mono.from(result.map((row, meta) -> row.get(0))))
                .block();
        log.info("get the serialNumber: {}", serialNumber);
    }

    @Test
    void name() {
        Flux.just(1, null, 2, null, 3)
                .doOnNext(item -> log.info("item: {}", item))
                .subscribeOn(Schedulers.immediate())
                .subscribe()
        ;
    }
}
