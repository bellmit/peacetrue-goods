package com.github.peacetrue.goods;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebAutoConfiguration;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.test.web.reactive.server.WebTestClientBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.reactive.server.WebTestClient;

/**
 * @author xiayx
 */
@Configuration
@ImportAutoConfiguration(classes = {
        TestServiceGoodsAutoConfiguration.class,
        WebMvcAutoConfiguration.class,
        HttpMessageConvertersAutoConfiguration.class,
        ControllerGoodsAutoConfiguration.class,
        SpringDataWebAutoConfiguration.class,
})
@EnableAutoConfiguration
public class TestControllerGoodsAutoConfiguration {

    @Bean
    public WebTestClientBuilderCustomizer upBufferLimit() {
        return new WebTestClientBuilderCustomizer() {
            @Override
            public void customize(WebTestClient.Builder builder) {
                builder.codecs(clientCodecConfigurer -> clientCodecConfigurer.defaultCodecs().maxInMemorySize(Integer.MAX_VALUE));
            }
        };
    }
}
