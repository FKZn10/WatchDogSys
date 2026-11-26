package com.jpmsoft.fileUpload.enums;

/**
 * OSS存储操作类型
 */
public enum OssActionEnum {

    upload("上传文件", "01"),
    deleteFile("删除文件", "02"),
    createDir("创建目录", "03"),
    deleteDir("删除目录", "04"),
    download("下载文件", "05");

    private String name;
    private String index;

    OssActionEnum(String name, String index) {
        this.name = name;
        this.index = index;
    }

    public static OssActionEnum getEnum(String key) {
        for (OssActionEnum c : OssActionEnum.values()) {
            if (c.getIndex().equals(key)) {
                return c;
            }
        }
        return null;
    }
    
    public static String getName(String index) {
        for (OssActionEnum c : OssActionEnum.values()) {
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
