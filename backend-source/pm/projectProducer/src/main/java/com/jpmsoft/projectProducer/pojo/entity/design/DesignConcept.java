package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 方案设计报审
 */
@Data
@TableName("Design_Concept")
public class DesignConcept {
    @TableId(type = IdType.INPUT)
    private String dcId;
    //项目ID
    private String pId;
    //方案设计名称
    private String dcName;
    //是否符合可研报告
    private Boolean dcFsr;
    //是否符合设计任务书
    private Boolean dcAssignment;
    //是否符合政府指标
    private Boolean dcGovernment;
    //使用单位是否确认
    private Boolean dcUser;
    //方案是否已审查
    private Boolean dcExamine;
    //设计单位ID
    private String dcUnitId;
    //图纸版本编号
    private String dcDrawingVersion;
    //图纸专业
    private String dcDrawingSpecialty;
    //计划开始时间
    private Date dcPlanStart;
    //计划结束时间
    private Date dcPlanEnd;
    private String dcCreateUid;
    private String dcCreateUname;
    private Date dcCreateTime;
    private Integer dcState;
    private Date dcStateTime;
    private String teId;
    //合同Id
    private String conId;
    //设计单位联系方式
    private String dcUnitTel;
    //建筑面积
    private BigDecimal dcFloorArea;
    //估算金额
    private BigDecimal dcAmount;

    /**
     *云上建工审批状态： 0-被驳回； 1-审批中； 3-审批通过
     */
    private String thirdStatus;

    /**
     * 云上建工审批意见
     */
    private String thirdApprovalComment;

    /**
     * 第三方返回的流程id
     */
    private String thirdRequestId;

    /**
     * 是否盖公司章
     */
    private Boolean companySealFlag;

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
     * 项目类别：0、A类，1、B类，3、C类
     */
    private String projectCategory;

    /**
     * 事由
     */
    private String reason;

    /**
     * 云上建工用户id
     */
    private String thirdUserId;

    /**
     * 日照分析是否通过
     */
    private Boolean dcSunlightAnalysis;

}
