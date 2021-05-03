package com.github.peacetrue.goods;

import com.github.peacetrue.core.OperatorImpl;
import lombok.*;

import javax.validation.constraints.*;
import java.math.BigDecimal;


/**
 * @author xiayx
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoodsAdd extends OperatorImpl<Long> {

    private static final long serialVersionUID = 0L;

    /** 封面图片. 多个之间使用,分割 */
    @NotNull
    @Size(max = 10)
    private String[] coverImages;
    /** 封面视频. 多个之间使用,分割 */
    @NotNull
    @Size(max = 10)
    private String[] coverVideos;
    /** 名称 */
    @NotNull
    @Size(min = 1, max = 255)
    private String name;
    /** 简介. 富文本 */
    @NotNull
    @Size(min = 1, max = 16777215)
    @ToString.Exclude
    private String detail;
    /** 展示状态. 上架、下架 */
    private GoodsDisplay display;
    /** 价格(元). 最大到亿，保留 2 位小数 */
    @NotNull
    @DecimalMin("0")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price;
    /** 备注 */
    @Size(min = 1, max = 255)
    private String remark;
    /** 序号 */
    @Min(1)
    private Long serialNumber;

    @Size(min = 1, max = 500)
    public String getCoverImage() {
        return String.join(",", coverImages);
    }

    @Size(min = 1, max = 255)
    public String getCoverVideo() {
        return String.join(",", coverVideos);
    }
}
