package com.jpmsoft.projectProducer.pojo.dto.bim;

import lombok.Data;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/6/14 14:44
 * @Description:
 * @Version: 1.0
 */
@Data
public class ProjectBimInfoDto {

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目地址
     */
    private String address;

    /**
     * 经度
     */
    private Float longitude;

    /**
     * 纬度
     */
    private Float latitude;
}
