package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("project_register_info")
public class ProjectRegisterInfo implements Serializable {

    private static final long serialVersionUID = 7349035019473215004L;
    /**
     * id
     */
    private String id;

    /**
     * 登记业务类型：01 项目申报，02 项目论证，对应 register_type 字段。
     */
    private String registerType;

    /**
     * 项目登记主键（Pro_Project_Register.id），用于同一项目的申报、论证校验。
     */
    private String proProjectRegisterId;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String address;

    /**
     *
     */
    private String projectCode;

    /**
     *
     */
    private String platformUrl;

    /**
     *
     */
    private String buildingType;

    //预期效益
    private String expectedBenefit;
    /**
     *
     */
    private String declareUnit;

    /**
     *
     */
    private String jointDeclareUnit;

    /**
     *
     */
    private String designUnit;

    /**
     *
     */
    private String constructionUnit;

    /**
     *
     */
    private String supervisionUnit;

    /**
     *
     */
    private String serviceUnit;

    /**
     *
     */
    private Date startDate;

    /**
     *
     */
    private Date endDate;

    /**
     *
     */
    private String contact;

    /**
     *
     */
    private String contactPhone;

    /**
     *
     */
    private String description;

    /**
     *
     */
    private String technicalInfo;
    private String projectRegisterInfoFileId;
    /**
     *
     */
    private String declarationAttachment;

    /**
     *
     */
    private Boolean isIntegratedPlatform;

    private Integer state;

    /**
     * 审批时间
     */
    private Date stateTime;

    /**
     * 创建人id
     */
    private String createUid;

    /**
     * 创建人
     */
    private String createUname;

    /**
     * 创建时间
     */
    private Date createTime;

    private String versionId;
    private Integer lastest;
    private String projectId;
    private String solutionId;
    private String evaluateObjectContractId;
    private String evaluateObjectContractVersionId;
    private String personnelEnsure;
    private String fundsEnsure;
    private String systemEnsure;
    private String othersEnsure;

    // 省
    private String province;

    // 市
    private String city;

    // 区
    private String district;


    // 地图类型
    private String mapType;

    // 经度
    private String longitude;

    // 纬度
    private String latitude;

    private String beReduceEmploymentState;

    private String beReduceEmploymentText;

    private String beConserveResourcesState;

    private String beConserveResourcesText;

    private String beReduceCostsState;

    private String beReduceCostsText;

    private String beQualityImprovementState;

    private String beQualityImprovementText;

    private String beEnsureSafetyState;

    private String beEnsureSafetyText;

    private String beOtherState;

    private String beOtherText;

    // 立项文件
    private String approvalFileId;

    // 土地合同文件
    private String landContractFileId;

    // 建设工程规划证
    private String planningPermitFileId;

    // 施工图审查合格证
    private String drawingReviewFileId;

    /**
     * 施工许可证
     */
    private String constructionLicenseFileId;

    /**
     * 申报年度
     */
    private Integer declareYear;

    /**
     * 申报类型
     */
    private String declareType;

    /**
     * 论证目的编码，registerType=02 时每次只能选择一个目的。
     */
    private String declarePurpose;

    /**
     * 论证结果，对应 argumentation_result 字段，最长20个字符。
     */
    private String argumentationResult;

    /**
     * 评分状态
     */
    private String scoreState;

    /**
     * 智能建造自评总分（前端传值，后端正存；供驾驶舱/列表直接取数）
     */
    private java.math.BigDecimal totalScore;

    /**
     * 动态分（第三方平台推送评分，预留字段）
     */
    private java.math.BigDecimal dynamicScore;

    /**
     * 结束检测时间（预留字段）
     */
    private Date endDetectTime;

    /**
     * 检测状态（预留字段）
     */
    private String detectStatus;

    private String otherFileId;
    private String otherFileIds;
}