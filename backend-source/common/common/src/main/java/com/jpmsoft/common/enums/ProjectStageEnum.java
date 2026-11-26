package com.jpmsoft.common.enums;

/**
 * 项目阶段
 */
@Deprecated
public enum ProjectStageEnum {

    Proposal_Stage("立项阶段", "01"),
    Feasibility_Study("可行性研究", "02"),
    Conceptual_Cesign("方案设计", "03"),
    Design_Estimate("初步设计及概算", "04"),
    Design_Chart("施工图设计", "05");

    private String name;
    private String key;

    ProjectStageEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (ProjectStageEnum c : ProjectStageEnum.values()) {
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
