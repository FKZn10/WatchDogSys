package com.jpmsoft.projectProducer.constants;

import lombok.Getter;

@Getter
public enum MarkStateEnum {

    REGISTERED("已登记","2"),
    UNREGISTERED("未登记","1"),
    ;

    private final String name;

    private final String code;

    MarkStateEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
