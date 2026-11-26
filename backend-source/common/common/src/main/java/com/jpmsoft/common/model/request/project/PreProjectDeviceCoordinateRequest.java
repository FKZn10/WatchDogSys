package com.jpmsoft.common.model.request.project;

import lombok.Data;

import java.util.List;

@Data
public class PreProjectDeviceCoordinateRequest {

    /**
     * 项目id
     */
    private String pId;

    /**
     * 查询父子项目 0或者空 查询所有 1、查询父项目 2、查询子项目
     */
    private Integer projectStatus=2;

    /**
     * 项目所属区域
     */
    private List<String> eachDistrictList;


}
