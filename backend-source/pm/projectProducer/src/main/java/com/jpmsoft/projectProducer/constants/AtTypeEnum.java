package com.jpmsoft.projectProducer.constants;

import lombok.Getter;

@Getter
public enum AtTypeEnum {
    AWARD("奖励",0),
    PUNISH("惩罚",1),
    ;

    private String name;

    private Integer code;

    AtTypeEnum(String name, Integer code) {
        this.name = name;
        this.code = code;
    }
}
