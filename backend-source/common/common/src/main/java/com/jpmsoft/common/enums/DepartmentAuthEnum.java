package com.jpmsoft.common.enums;

@Deprecated
public enum DepartmentAuthEnum {

    ALL_PROJECT("全部项目授权", "01"),
    Main_Department("按项目主管部门授权", "02"),
    Project_Department("按项目组织机构授权(内部用户)", "03"),
    Department_Duty("按项目组织机构授权,负责人按部门鉴权", "04");

    private String name;
    private String key;

    DepartmentAuthEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (DepartmentAuthEnum c : DepartmentAuthEnum.values()) {
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
