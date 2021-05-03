package com.github.peacetrue.goods;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author xiayx
 */
@Data
@ConfigurationProperties(prefix = "peacetrue.goods")
public class ServiceGoodsProperties {

}
