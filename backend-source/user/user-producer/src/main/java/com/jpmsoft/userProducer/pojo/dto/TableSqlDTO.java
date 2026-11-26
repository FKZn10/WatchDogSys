package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

@Data
public class TableSqlDTO {
    private String tableId;
    private String create;
    private String delete;
    private String remark;
    private String columns;
}
