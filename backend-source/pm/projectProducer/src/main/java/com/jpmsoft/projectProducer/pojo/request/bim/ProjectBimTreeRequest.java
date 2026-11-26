package com.jpmsoft.projectProducer.pojo.request.bim;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/6/10 10:13
 * @Description:
 * @Version: 1.0
 */
@Data
public class ProjectBimTreeRequest implements Serializable {
    private static final long serialVersionUID = -3871321999020629880L;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * bim模型树id
     */
    private String bimTreeId;

    private String createId;

    private String createName;
}
