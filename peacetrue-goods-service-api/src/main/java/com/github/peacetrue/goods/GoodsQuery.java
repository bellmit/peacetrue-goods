package com.github.peacetrue.goods;

import com.github.peacetrue.core.OperatorImpl;
import com.github.peacetrue.core.Range;
import lombok.*;

import java.math.BigDecimal;


/**
 * @author xiayx
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoodsQuery extends OperatorImpl<Long> {

    public static final GoodsQuery DEFAULT = new GoodsQuery();

    private static final long serialVersionUID = 0L;

    /** 主键 */
    private Long[] id;
    /** 名称 */
    private String name;
    /** 展示状态. 上架、下架 */
    private GoodsDisplay display;
    /** 价格(元). 最大到亿，保留 2 位小数 */
    private BigDecimal price;
    /** 序号 */
    private Range.Long serialNumber = new Range.Long();
    /** 订单序号 */
    private Range.Long goodsCount = new Range.Long();
    /** 创建者. 主键 */
    private Long creatorId;
    /** 创建时间 */
    private Range.LocalDateTime createdTime = new Range.LocalDateTime();
    /** 最近修改者. 主键 */
    private Long modifierId;
    /** 最近修改时间 */
    private Range.LocalDateTime modifiedTime = new Range.LocalDateTime();

    public GoodsQuery(Long[] id) {
        this.id = id;
    }

}
