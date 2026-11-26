package com.jpmsoft.fileUpload.enums;

import com.jpmsoft.common.utils.ValidateUtil;

/**
 * 合同工程量清单类别
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/19 14:20
 */
public enum BoqdTypeEnum {

    Dxgc("单项工程", "01"),
    Dwgc("单位工程", "02"),
    Fbfx("分部分项", "03"),
    Djcsf("单价措施费", "04"),
    QtProject("其他项目", "05"),
    Fbfxqd("分部分项清单", "06"),
    Other("其他", "07");

    private String name;
    private String index;

    BoqdTypeEnum(String name, String index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(String index) {
        for (BoqdTypeEnum c : BoqdTypeEnum.values()) {
            if (c.getIndex().equals(index)) {
                return c.name;
            }
        }
        return null;
    }

    public static String getCode(String name) {
        if (ValidateUtil.isEmpty(name)) return null;
        for (BoqdTypeEnum c : BoqdTypeEnum.values()) {
            if (c.getName().equals(name)) {
                return c.index;
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
