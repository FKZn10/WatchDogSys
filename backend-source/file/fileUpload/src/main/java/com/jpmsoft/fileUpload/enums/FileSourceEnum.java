package com.jpmsoft.fileUpload.enums;

/**
 * 附件来源类型
 */
public enum FileSourceEnum {

    UserUpload("用户上传", "01"),
    SystemFile("系统关联", "02");

    private String name;
    private String index;

    FileSourceEnum(String name, String index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(String index) {
        for (FileSourceEnum c : FileSourceEnum.values()) {
            if (c.getIndex().equals(index)) {
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

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
