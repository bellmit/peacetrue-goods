package com.github.peacetrue.goods;

import com.github.peacetrue.core.CodeCapable;
import com.github.peacetrue.core.NameCapable;

public enum GoodsDisplay implements CodeCapable, NameCapable {

    OFFLINE("下架"),
    ONLINE("上架"),
    ;

    private final String name;

    GoodsDisplay(String name) {
        this.name = name;
    }

    @Override
    public String getCode() {
        return this.name();
    }

    @Override
    public String getName() {
        return name;
    }
}
