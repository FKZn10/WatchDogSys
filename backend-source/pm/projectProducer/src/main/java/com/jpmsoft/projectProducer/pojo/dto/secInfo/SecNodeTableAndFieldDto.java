package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import lombok.Data;

import java.io.Serializable;

/**
 * 标段业务 表对应状态字段以及标段ID字段
 * 用于统计标段节点数量
 *
 * @Author: 李文涛 aaron
 * @date: 2023/2/21 17:27
 */
@Data
public class SecNodeTableAndFieldDto implements Serializable {

    private String table;
    private String stateField;
    private String secIdField;
}
