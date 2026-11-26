package com.jpmsoft.projectProducer.pojo.entity.project;

import lombok.Data;

/**
 * 项目申报-联合申报单位子表
 */
@Data
public class ProjectRegisterInfoUnit {
    private String id;
    private String priId;
    private String unitType;
    private String unitName;
    private String unitContact;
    private String unitPhone;
}