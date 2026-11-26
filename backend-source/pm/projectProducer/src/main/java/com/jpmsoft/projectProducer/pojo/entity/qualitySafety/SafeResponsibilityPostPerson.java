package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 安全责任书岗位人员
 */
@Data
@TableName("safe_responsibility_post_person")
public class SafeResponsibilityPostPerson {
    /**
     * 主键
     */
    @TableId(type = IdType.INPUT)
    private String id;

    /**
     * 项目id
     */
    private String pId;

    /**
     * 合同id
     */
    private String conId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 所属单位
     */
    private String unitId;

    /**
     * 申报的岗位id
     */
    private String postId;

    /**
     * 工种
     */
    private String jobCategory;

    /**
     * 联系方式
     */
    private String phone;

    /**
     * 证书编号
     */
    private String certificateNumber;

    /**
     * 身份证文件
     */
    private String identityCardFile;

    /**
     * 上岗证书文件
     */
    private String inductionCertificateFile;

    /**
     * 资质文件
     */
    private String qualificationsFile;

    /**
     * 任职开始时间
     */
    private Date startTime;

    /**
     * 任职结束时间
     */
    private Date endTime;

    /**
     * 流程状态
     */
    private Integer state;

    /**
     * 审批时间
     */
    private Date stateTime;

    /**
     * 排序号
     */
    private Integer sortNo;

    /**
     * 创建人id
     */
    private String createUid;

    /**
     * 创建人姓名
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改人id
     */
    private String updateUid;

    /**
     * 修改人姓名
     */
    private String updateUname;

    /**
     * 修改时间
     */
    private Date updateTime;
}