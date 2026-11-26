package com.jpmsoft.common.enums;

/**
 * 签收状态
 */
@Deprecated
public enum DeliveryStatusEnum {

    NOT("未签收", 0),
    Already("已签收", 1);

    private String name;
    private Integer index;

    DeliveryStatusEnum(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (DeliveryStatusEnum c : DeliveryStatusEnum.values()) {
            if (c.getIndex() == index) {
                return c.name;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }
}
