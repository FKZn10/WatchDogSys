package com.jpmsoft.projectProducer.enums;

public enum ProjectAcceptanceAuditStatusEnum {
    Completed_Not_Verified("已完工未初验", "01"),
    Verified_Not_Completed("已初验未竣工", "02"),
    Completed_Not_Sent("已竣工未送审", "03"),
    Sent_Not_Approved("已送审未审定", "04"),
    Completed_Audited("已审定", "05");

    private String name;
    private String key;

    ProjectAcceptanceAuditStatusEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (ProjectAcceptanceAuditStatusEnum c : ProjectAcceptanceAuditStatusEnum.values()) {
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