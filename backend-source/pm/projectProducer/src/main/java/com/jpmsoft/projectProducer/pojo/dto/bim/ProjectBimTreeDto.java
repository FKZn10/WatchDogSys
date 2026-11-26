package com.jpmsoft.projectProducer.pojo.dto.bim;

import lombok.Data;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/6/10 14:58
 * @Description:
 * @Version: 1.0
 */
@Data
public class ProjectBimTreeDto {

    /**
     * 模型树id
     */
    private String bimTreeId;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;
}
