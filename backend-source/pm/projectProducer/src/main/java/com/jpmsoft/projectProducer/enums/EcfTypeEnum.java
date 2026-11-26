package com.jpmsoft.projectProducer.enums;

/**
 * 项目阶段
 */
public enum EcfTypeEnum {

    EcFee("变更费用申报", "01"),
    EcFeeClear("变更费用申报清标", "02"),
    EcFeeChange("变更计量报审单", "03");

    private String name;
    private String key;

    EcfTypeEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (EcfTypeEnum c : EcfTypeEnum.values()) {
            if (c.getkey().equals(key)) {
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

    public String getkey() {
        return key;
    }

    public void setkey(String key) {
        this.key = key;
    }
}
