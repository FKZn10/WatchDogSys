package com.jpmsoft.userProducer.enums;

public enum SequenceRestEnum {

    TIMEOUT("时间", "01"),
    NUM_OUT("数值越界", "02"),
    CONST_CHANGE("常量变更", "03"),
    PCS_CHANGE("项目合同标段变更", "04");

    private String name;
    private String key;

    SequenceRestEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (SequenceRestEnum c : SequenceRestEnum.values()) {
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
