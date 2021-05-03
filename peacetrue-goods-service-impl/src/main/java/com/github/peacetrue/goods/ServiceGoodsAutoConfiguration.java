package com.github.peacetrue.goods;

import com.github.peacetrue.spring.core.io.support.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import java.util.Objects;

/**
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(ServiceGoodsProperties.class)
@ComponentScan(basePackageClasses = ServiceGoodsAutoConfiguration.class)
@PropertySource(value = "classpath:/application-goods-service.yml", factory = YamlPropertySourceFactory.class)
public class ServiceGoodsAutoConfiguration {

    private ServiceGoodsProperties properties;

    public ServiceGoodsAutoConfiguration(ServiceGoodsProperties properties) {
        this.properties = Objects.requireNonNull(properties);
    }

}
