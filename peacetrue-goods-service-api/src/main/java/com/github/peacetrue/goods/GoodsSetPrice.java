package com.github.peacetrue.goods;

import com.github.peacetrue.core.OperatorImpl;
import lombok.*;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * @author xiayx
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoodsSetPrice extends OperatorImpl<Long> {

    private static final long serialVersionUID = 0L;

    @NotNull
    @Min(1)
    private Long id;
    /** 价格(元). 最大到亿，保留 2 位小数 */
    @NotNull
    @DecimalMin("0")
    @Digits(integer = 9, fraction = 2)
    private BigDecimal price;

}
