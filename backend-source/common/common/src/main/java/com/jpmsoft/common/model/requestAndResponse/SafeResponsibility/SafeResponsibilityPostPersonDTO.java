package com.jpmsoft.common.model.requestAndResponse.SafeResponsibility;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 安全责任书岗位人员
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SafeResponsibilityPostPersonDTO extends BaseFileDto {
    /**
     * 主键
     */
    private String id;

    /**
     * 项目id
     */
    private String pId;
    private String pName;

    /**
     * 合同id
     */
    private String conId;
    private String conName;

    /**
     * 姓名
     */
    private String name;

    /**
     * 所属单位
     */
    private String unitId;
    private String unitName;

    /**
     * 申报的岗位id
     */
    private String postId;
    private String postName;

    /**
     * 层级名称
     */
    private String levelName;

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
    private String identityCardFileIds;

    /**
     * 上岗证书文件
     */
    private String inductionCertificateFile;
    private String inductionCertificateFileIds;

    /**
     * 资质文件
     */
    private String qualificationsFile;
    private String qualificationsFileIds;

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