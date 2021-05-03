package com.github.peacetrue.goods;

import com.github.peacetrue.core.OperatorImpl;
import lombok.*;

import javax.validation.constraints.NotNull;


/**
 * @author xiayx
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class GoodsGet extends OperatorImpl<Long> {

    private static final long serialVersionUID = 0L;

    @NotNull
    private Long id;

}
