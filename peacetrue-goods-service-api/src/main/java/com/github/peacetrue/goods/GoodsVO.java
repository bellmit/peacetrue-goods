package com.github.peacetrue.goods;

import com.github.peacetrue.core.IdCapable;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author xiayx
 */
@Data
public class GoodsVO implements Serializable, IdCapable<Long> {

    private static final long serialVersionUID = 0L;

    /** 主键 */
    private Long id;
    /** 封面图片. 多个之间使用,分割 */
    private String coverImage;
    /** 封面图片链接地址 */
    private String[] coverImageUrls;
    /** 封面视频. 多个之间使用,分割 */
    private String coverVideo;
    /** 封面视频链接地址 */
    private String[] coverVideoUrls;
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

    public String[] getCoverImages() {
        return coverImage.split(",");
    }

    public String[] getCoverVideos() {
        return coverVideo.split(",");
    }
}
