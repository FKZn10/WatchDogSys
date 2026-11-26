package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class ProjectRegisterInfoModel extends BaseFileDto {
    /**
     * id
     */
    private String id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String address;
    private List<String> provinceCityDistrictList;
    private String provinceCityDistrict;
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
    private String projectRegisterInfoFileIds;

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
    private String projectId;
    private String solutionId;
    private String versionId;
    private Integer lastest;
    private String evaluateObjectContractId;
    private String evaluateObjectContractVersionId;

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

    private List<ProjectRegisterInfoUnitModel> unitList;

    private String operationType;

    // 立项文件
    private String approvalFileId;

    private String approvalFileIds;

    // 土地合同文件
    private String landContractFileId;

    private String landContractFileIds;

    // 建设工程规划证
    private String planningPermitFileId;

    private String planningPermitFileIds;

    // 施工图审查合格证
    private String drawingReviewFileId;

    private String drawingReviewFileIds;

    /**
     * 施工许可证
     */
    private String constructionLicenseFileId;
    private String constructionLicenseFileIds;
    private List<PreProjectRegisterInfoScoreModel> scoreList;
    private String personnelEnsure;
    private String fundsEnsure;
    private String systemEnsure;
    private String othersEnsure;


    /**
     * 申报年度
     */
    private Integer declareYear;

    /**
     * 申报类型
     */
    private String declareType;

    /**
     * 申报目的
     */
    private String declarePurpose;

    /**
     * 评分状态
     */
    private String scoreState;

    /**
     * 智能建造自评总分（前端传值，后端正存；供驾驶舱/列表直接取数）
     */
    private BigDecimal totalScore;

    /**
     * 动态分（第三方平台推送评分，预留字段）
     */
    private BigDecimal dynamicScore;

    /**
     * 结束检测时间（预留字段）
     */
    private Date endDetectTime;

    /**
     * 检测状态（预留字段）
     */
    private String detectStatus;

    /**
     * 其他证明材料
     */
    private String otherFileId;
    private String otherFileIds;
}
