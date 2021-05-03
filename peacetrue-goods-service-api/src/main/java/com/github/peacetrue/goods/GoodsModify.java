package com.github.peacetrue.goods;

import com.github.peacetrue.core.IdCapable;
import com.github.peacetrue.core.OperatorImpl;
import lombok.*;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;


/**
 * @author xiayx
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoodsModify extends OperatorImpl<Long> implements IdCapable<Long> {

    private static final long serialVersionUID = 0L;

    /** 主键 */
    @NotNull
    @Min(1)
    private Long id;
    /** 封面图片. 多个之间使用,分割 */
    @Size(max = 10)
    private String[] coverImages;
    /** 封面视频. 多个之间使用,分割 */
    @Size(max = 10)
    private String[] coverVideos;
    /** 名称 */
    @Size(min = 1, max = 255)
    private String name;
    /** 简介. 富文本 */
    @ToString.Exclude
    @Size(min = 1, max = 16777215)
    private String detail;
    /** 价格(元). 最大到亿，保留 2 位小数 */
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price;
    /** 备注 */
    @Size(min = 1, max = 255)
    private String remark;
    /** 序号 */
    @Min(0)
    private Long serialNumber;

    @Size(min = 1, max = 500)
    public String getCoverImage() {
        return coverImages == null ? null : String.join(",", coverImages);
    }

    @Size(min = 1, max = 255)
    public String getCoverVideo() {
        return coverVideos == null ? null : String.join(",", coverVideos);
    }
}
