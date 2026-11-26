package com.jpmsoft.common.enums;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/3 9:47
 */
public enum ProcessFieldEnum {

    id(0),
    state(1),
    stateTime(2),
    createUid(3),
    createUname(4),
    createTime(5);
    private final int key;

    private ProcessFieldEnum(int key) {
        this.key = key;
    }

    public int getKey() {
        return this.key;
    }
}
