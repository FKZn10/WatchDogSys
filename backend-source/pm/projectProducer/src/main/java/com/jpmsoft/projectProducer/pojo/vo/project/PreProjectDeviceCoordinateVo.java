package com.jpmsoft.projectProducer.pojo.vo.project;


import lombok.Data;

import java.util.Date;

@Data
public class PreProjectDeviceCoordinateVo {

    private String uuid;

    /**
     * 项目id
     */
    private String projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 项目编码
     */
    private String projectCode;

    /**
     * x点坐标
     */
    private String pointX;

    /**
     * y点坐标
     */
    private String pointY;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 创建人id
     */
    private String createUserId;

    /**
     * 创建人名称
     */
    private String createUsername;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 更新人id
     */
    private String updateUserId;

    /**
     * 更新人名称
     */
    private String updateUsername;


}
