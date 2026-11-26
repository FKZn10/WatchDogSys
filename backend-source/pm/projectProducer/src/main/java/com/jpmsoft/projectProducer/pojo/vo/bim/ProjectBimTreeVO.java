package com.jpmsoft.projectProducer.pojo.vo.bim;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/6/10 14:58
 * @Description:
 * @Version: 1.0
 */
@Data
public class ProjectBimTreeVO implements Serializable {
    private static final long serialVersionUID = -1691887157506122055L;

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
