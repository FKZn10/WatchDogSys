package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

import java.util.List;

@Data
public class ColumnSqlDTO {
    private List<String> columnIdList;
    private String create;
    private String delete;
    private String remark;
}
