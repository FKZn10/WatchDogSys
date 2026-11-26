package com.jpmsoft.userProducer.enums;

public enum CompanyTypeEnum {

    Build_Unit("建设单位", "01"),
    Agent_Construction("代建单位", "02"),
    Construction_Control("监理单位", "03"),
    Design_Unit("设计单位", "04"),
    Construction_Unit("全过程咨询单位", "05");

    private String name;
    private String key;

    CompanyTypeEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (CompanyTypeEnum c : CompanyTypeEnum.values()) {
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
