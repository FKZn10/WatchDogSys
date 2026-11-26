package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 初步设计
 */
@Data
@TableName("Design_Initial")
public class DesignInitial {

    @TableId(type = IdType.INPUT)
    private String diId;
    //项目ID
    private String pId;
    //初步设计标题
    private String diTitle;
    //估算金额
    private BigDecimal diEstimate;
    //初步设计概算金额
    private BigDecimal diBudget;
    //是否超可研批复标准
    private Integer diEfs;
    //是否符合设计任务书
    private Integer diDesignBrief;
    //设计单位ID
    private String diDesignUnit;
    //是否符合政府指标
    private Integer diGovernment;
    //使用单位确认
    private Integer diUserConfirm;
    //创建人ID
    private String diCreateUid;
    private String diCreateUname;
    private Date diCreateTime;
    private Integer diState;
    private Date diStateTime;
    //建安控制价
    private BigDecimal diControlPrice;
    //建安概算价
    private BigDecimal diEstimatePrice;
    //设计内容
    private String diContent;
    //开始时间
    private Date diStart;
    //结束时间
    private Date diEnd;
    //合同
    private String conId;
    private String teId;
    //设计单位联系方式
    private String diDesignUnitTel;
    //专业审查会议
    private String diReviewMeetings;

    /**
     *云上建工审批状态： 0-被驳回； 1-审批中； 3-审批通过
     */
    private String thirdStatus;

    /**
     * 云上建工审批意见
     */
    private String thirdApprovalComment;

    /**
     *
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

}
