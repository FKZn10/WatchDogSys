package com.jpmsoft.userProducer.enums;

/**
 * 登录渠道
 * 北京工行
 * 第三方登录返回URL返回的IP
 */
public enum ChannelTypeEnum {

    Dedicated_Line("专线", "0"),
    Internet("互联网", "1");

    private String name;
    private String key;

    ChannelTypeEnum(String name, String key) {
        this.name = name;
        this.key = key;
    }

    public static String getName(String key) {
        for (ChannelTypeEnum c : ChannelTypeEnum.values()) {
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
