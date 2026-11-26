package com.jpmsoft.common.enums;

/**
 * 外部用户鉴权权限
 */
public enum OutUserAuthEnum {

    NOT("外部用户没有权限,仅内部用户鉴权", 0),
    Unit_Auth("监理单位或全过程咨询单位组织机构授权，其他类型单位的用户按单位授权", 1),
    Organization("外部单位完全按组织机构授权", 2),
    Only_Inner("内部用户不鉴权，外部用户无权", 3),
    Only_Creator("仅可查看当前操作人创建的记录", 4),
    Approver_Creator("仅限创建人与审批人查看", 5);

    private String name;
    private Integer index;

    OutUserAuthEnum(String name, Integer index) {
        this.name = name;
        this.index = index;
    }

    public static String getName(int index) {
        for (OutUserAuthEnum c : OutUserAuthEnum.values()) {
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
