package com.github.peacetrue.goods;

import com.github.peacetrue.core.CreateModify;
import lombok.*;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 商品实体类
 *
 * @author xiayx
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Goods implements Serializable, com.github.peacetrue.core.Id<Long>, CreateModify<Long> {

    private static final long serialVersionUID = 0L;

    /** 主键 */
    @Id
    private Long id;
    /** 封面图片. 多个之间使用,分割 */
    private String coverImage;
    /** 封面视频. 多个之间使用,分割 */
    private String coverVideo;
    /** 名称 */
    private String name;
    /** 简介. 富文本 */
    @ToString.Exclude
    private String detail;
    /** 展示状态. 上架、下架 */
    private GoodsDisplay display;
    /** 价格(元). 最大到亿，保留 2 位小数 */
    private BigDecimal price;
    /** 备注 */
    private String remark;
    /** 序号 */
    private Long serialNumber;
    /** 下单数量 */
    private Long orderCount;
    /** 创建者. 主键 */
    private Long creatorId;
    /** 创建时间 */
    private LocalDateTime createdTime;
    /** 最近修改者. 主键 */
    private Long modifierId;
    /** 最近修改时间 */
    private LocalDateTime modifiedTime;

}
