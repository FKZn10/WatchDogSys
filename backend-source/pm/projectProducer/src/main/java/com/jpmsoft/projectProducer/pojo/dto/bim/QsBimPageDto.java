package com.jpmsoft.projectProducer.pojo.dto.bim;

import lombok.Data;

import java.util.Date;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2022/6/22 14:49
 * @Description:
 * @Version: 1.0
 */
@Data
public class QsBimPageDto {

    /**
     *质量安全表主键id
     */
    private String qsrId;

    /**
     *项目id
     */
    private String projectId;

    /**
     *合同名称
     */
    private String conName;

    /**
     * 类别：0-质量;1-安全
     */
    private Integer qsrType;

    /**
     *检查类型
     */
    private String checkType;

    /**
     *问题类别
     */
    private String problemCategory;

    /**
     *问题性质
     */
    private String problemNature;

    /**
     *检查时间
     */
    private Date checkTime;

    /**
     *要求整改时间
     */
    private Date rectifyRequireTime;

    /**
     *整改责任人
     */
    private String rectifyUserName;
}
