package com.jpmsoft.common.enums;

/**
 * 单位类型
 */
public enum UserTypeEnum {

    Inner_User("内部用户", 1),
    Out_User("外部用户", 2);

    private String name;
    private Integer index;

    UserTypeEnum(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (UserTypeEnum c : UserTypeEnum.values()) {
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
