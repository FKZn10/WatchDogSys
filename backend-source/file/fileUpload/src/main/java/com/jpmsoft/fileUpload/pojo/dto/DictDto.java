package com.jpmsoft.fileUpload.pojo.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class DictDto implements Serializable {

    private static final long serialVersionUID = 6539861790333181367L;
    private String code;
    private String name;
    private Integer sort;
}
