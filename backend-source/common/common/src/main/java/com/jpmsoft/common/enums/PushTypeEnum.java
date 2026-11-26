package com.jpmsoft.common.enums;

public enum PushTypeEnum {

    Project("项目信息", "01"),
    Contract("总包合同", "02"),
    Subcontract("分包合同", "03"),
    Con_Confirm("合同确认", "04"),
    Work_Report("工作日报(光明监理施工日志数据包推送)", "05"),
    Con_Sup_Site("旁站记录(光明数据包推送)", "06"),
    Con_Contract("合同(光明、北林)", "07"),
    User_Pwd("北京密码推送", "08"),
    Bj_Pay("北京请款信息推送", "09"),
    Task("代办推送", "10"),
    Bj_ProSec("北京标段信息、北林采购信息", "11"),
    Done("已办推送", "12"),
    UnRead("待阅", "13"),
    Read("已阅", "14"),
    Receive("我方接收推送数据", "15"),
    OneNet("一网通推送", "16"),
    Withdraw("待办撤回", "17"),
    CC_NOTICE("抄送通知", "18");

    private String name;
    private String key;

    PushTypeEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (PushTypeEnum c : PushTypeEnum.values()) {
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
