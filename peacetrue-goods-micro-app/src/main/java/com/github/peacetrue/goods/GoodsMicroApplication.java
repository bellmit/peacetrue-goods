package com.github.peacetrue.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

/**
 * 商品
 *
 * @author xiayx
 */
@SpringBootApplication
public class GoodsMicroApplication {

    public static void main(String[] args) {
        SpringApplication.run(GoodsMicroApplication.class, args);
    }

    @ConditionalOnProperty(name = "spring.security.user.name")
    @EnableWebFluxSecurity
    public static class SecurityConfig {
        @Bean
        public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
            http
                    .authorizeExchange(exchanges ->
                            exchanges
                                    .pathMatchers("/goods/**").hasAuthority("SCOPE_goods")
                                    .anyExchange().authenticated()
                    )
                    .oauth2ResourceServer(oauth2ResourceServer ->
                            oauth2ResourceServer.jwt(withDefaults())
                    );
            return http.build();
        }
    }
}
