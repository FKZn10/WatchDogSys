package com.jpmsoft.projectProducer.pojo.entity.design;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 施工图设计
 */
@Data
@TableName("Design_Construction")
public class DesignConstruction {

    @TableId(type = IdType.INPUT)
    private String dcId;
    //项目ID
    private String pId;
    //施工图名称
    private String dcName;
    //预算金额
    private BigDecimal dcBudget;
    //日照分析字典
    private String dcSunshine;
    //图纸专业字典
    private String dcSpecialty;
    //基坑支护论证字典
    private String dcFps;
    //是否符合政府指标
    private Integer dcGovernment;
    //使用单位确认
    private Integer dcUserConfirm;
    //计划开始时间
    private Date dcPlanStart;
    //计划完成时间
    private Date dcPlanEnd;
    private String dcCreateUid;
    private String dcCreateUname;
    private Date dcCreateTime;
    private Integer dcState;
    private Date dcStateTime;
    //专业类别
    private String dcCategory;
    //设计内容
    private String dcContent;
    //开始时间
    private Date dcStart;
    //结束时间
    private Date dcEnd;
    //合同ID
    private String conId;
    //标段ID
    private String secId;
    //图纸版本
    private String dcDrawing;
    //其他
    private String dcOther;
    private String teId;
    //设计单位
    private String dcDesignUnit;
    //设计单位联系方式
    private String dcDesignUnitTel;

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

    /**
     * 是否下发
     */
    private String issued;
}
