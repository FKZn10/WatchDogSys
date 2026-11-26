package com.jpmsoft.projectProducer.pojo.dto.bim;

import lombok.Data;


/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/7/12 18:47
 * @Description:
 * @Version: 1.0
 */
@Data
public class ProjectDto {

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目类型
     */
    private String projectType;
}
