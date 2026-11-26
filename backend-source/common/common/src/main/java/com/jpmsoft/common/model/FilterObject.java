package com.jpmsoft.common.model;

import lombok.Data;

@Data
public class FilterObject {
    private String dbValue;
    private String label;

    public FilterObject(String dbValue, String label) {
        this.dbValue = dbValue;
        this.label = label;
    }
}
