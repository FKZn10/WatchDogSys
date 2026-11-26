package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class SysRegionDTONew implements Serializable {

    private static final long serialVersionUID = -4121031636047672172L;

    private String id;
    private String srCode;
    private Integer srLevel;
    private String srName;
    private Integer srStatus;
    private String srParentCode;
    private List<SysRegionDTONew> children;

}
