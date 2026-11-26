package com.jpmsoft.userProducer.enums;

/**
 * 短信平台标识枚举
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/1 14:54
 */
public enum SmsPlatformEnum {

    Aliyun("阿里云短信", "Aliyun"),
    Sxsms("苏相项目SMS", "Sxsms"),
    Huawei("华为云短信", "Huawei"),
    HwIot("华为Iot短信", "HwIot"),
    GmSms("光明短信", "GmSms"),
    CrcSms("华润", "CrcSms"),
    YdSms("中国移动", "YdSms"),
    SubSms("SUBMAIL", "SubSms"),
    BlSms("北林", "BlSms"),
    Unknown("未知平台", "Unknown");

    private String name;
    private String key;

    SmsPlatformEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static SmsPlatformEnum getEnum(String key) {
        for (SmsPlatformEnum c : SmsPlatformEnum.values()) {
            if (c.getkey().equals(key)) {
                return c;
            }
        }
        return SmsPlatformEnum.Unknown;
    }

    public static String getName(String key) {
        for (SmsPlatformEnum c : SmsPlatformEnum.values()) {
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
