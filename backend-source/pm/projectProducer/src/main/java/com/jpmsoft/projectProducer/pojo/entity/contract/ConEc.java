package com.jpmsoft.projectProducer.pojo.entity.contract;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 变更事项申请
 */
@Data
@TableName("Con_EC")
public class ConEc {

    @TableId(type = IdType.INPUT)
    private String ecId;
    private String conId;
    private String preEcId;
    private String secId;
    private String ecSupervisorId;
    private String ecConsultingUnitId;
    private String ecCode;
    private String clId;
    private String ecResponsibleParty;
    private String ecMainCause;
    private BigDecimal ecApplyMoney;
    /**
     * 甲方核定金额，单位元；施工合同造价控制表按审批状态分别累计。
     */
    private BigDecimal partyAAmount;
    /**
     * 变更事项，来源 change_name，不自动沿用 ecName。
     */
    private String changeName;
    /**
     * 乙方报审金额，单位元，来源 party_b_amount。
     */
    private BigDecimal partyBAmount;
    private Integer ecApplyMoneyType;
    private String ecIsBgzl;
    private Integer ecDuration;
    private Integer ecDurationType;
    private String ecGrade;
    private BigDecimal ecTotalMoney;
    private BigDecimal ecTotalMoneyPlus;
    private BigDecimal ecTotalMoneyMinus;
    private BigDecimal ecFinalMoney;
    private BigDecimal ecTotalMoneyC;
    private Float ecTotalMoneyCRate;
    private String ecRemark;
    private String ecWarn;
    private String ecContent;
    private String ecReason;
    private Integer ecDesignOrder;
    private String ecDesignOrderCode;
    private String ecDesignOrderDoc;
    private String ecDesignDrawing;
    private String ecCunitCostDoc;
    private String ecNegotiationDoc;
    private String ecCreateUid;
    private String ecCreateUname;
    private Date ecCreateTime;
    private String ecCreateCId;
    private Integer ecState;
    private Date ecStateTime;

    private String ecBgzlId;
    private Integer ecDrawingLibrary;
    private String ecDesignId;
    private String ecPartName;
    private String ecType;
    private String ecChangeGist;
    private String ecGistOther;
    private String dcoId;
    private String ecDoc;
    private String ecDrawing;
    private String ecCostBook;
    private String ecDep;

    private String ecReqFile;
    private String ecApprovalFile;
    private String ecRelevantFile;
    private String ecApplyFile;
    private String ecMaterialFile;
    private String ecOpinionFile;
    private String ecFilingsFile;
    private String ecEnisFile;

    private String ecHbFileYj;
    private String ecHbFileYsd;
    private String ecHbFileSdd;
    private String ecHbFileBgd;
    private String ecHbFileHyjy;
    private String ecHbFileBsys;
    private String ecHbFileTz;

    private String ecPutForward;
    private String ecPart;

    private String ecConstruction;
    private String ecCostConsulting;
    private String ecAuditType;
    private BigDecimal ecFinalMoneyAfter;
    private String ecName;
    private String ecApprovalMatter;
    private String teId;

    private String enisId;
    private String ecChangeType;

    private String ecCnature;
    private String pId;
    private String ecSjFile;
    private String ecJlFile;
    private Boolean ecNewEc;
    private String ecConstructionName;

    /**
     *云上建工审批状态： 0-被驳回； 1-审批中； 3-审批通过
     */
    private String thirdStatus;

    /**
     * 云上建工审批意见
     */
    private String thirdApprovalComment;

    private BigDecimal ecTotalMoneyMinusC;

    private BigDecimal ecTotalMoneyPlusC;

    private String cmnId;

    //是否历史数据 0是非历史数据 1是历史数据
    private String isHistory;


    /**
     * 第三方请求id
     */
    private String thirdRequestId;

    /**
     * 云上建工用户id
     */
    private String thirdUserId;

    /**
     * 印章类别  0-公章，1-技术专用章
     *
     */
    private String sealCategory;

    /**
     * 专业审核 ： 0、道路 1、电气 2、房建 3、给排水 4、工程造价 5、公交规划 6、轨道交通 7、景观绿化 8、交通工程 9、交通规划
     * 10、交通综治 11、结构 12、路基路面 13、路线交叉 14、桥梁涵洞 15、燃气 16、水土保持 17、隧道 18、岩土 19、智能交通
     */
    private String professionalReview;

    /**
     * 项目类别（0-A类，1-B类，3-C类）
     */
    private String projectCategory;

    /**
     * 事由
     */
    private String reason;

    // 多合同变更签证id
    private String multiContractConEcId;

    // 合同可用金额
    private BigDecimal contractAvailableMoney;

    // 概算可用金额
    private BigDecimal estimateAvailableMoney;

    // 合同累计变更工期（包含本次）
    private BigDecimal ecTotalDayC;

    // 累计正变更工期
    private BigDecimal ecTotalDayPlusC;
    // 累计负变更工期
    private BigDecimal ecTotalDayMinusC;

    private String ecDataSeparate;

    /**
     *     区分 暂列金 01 默认 或者 暂估价 02
     */
    private String ecDataSeparateType;

    private String itemCategory;
}