package com.github.peacetrue.goods;

import com.github.peacetrue.spring.core.io.support.YamlPropertySourceFactory;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;


/**
 * @author xiayx
 */
@Configuration
@EnableConfigurationProperties(ControllerGoodsProperties.class)
@ComponentScan(basePackageClasses = ControllerGoodsAutoConfiguration.class)
@PropertySource(value = "classpath:/application-goods-controller.yml", factory = YamlPropertySourceFactory.class)
public class ControllerGoodsAutoConfiguration {

}
