package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 可研报告
 */
@Data
@TableName("Design_Feasibility")
public class DesignFeasibility {
    @TableId(type = IdType.INPUT)
    private String dfId;
    //可研报告名称
    private String dfName;
    //项目id
    private String pId;
    //投资估算
    private BigDecimal dfInvestment;
    //备注
    private String dfRemark;
    private String dfCreateUid;
    private String dfCreateUname;
    private Date dfCreateTime;
    private Integer dfState;
    private Date dfStateTime;
    //标段id
    private String secId;
    //编制单位
    private String dfUnit;
    //项目负责人
    private String dfPm;
    //可研范围
    private String dfScope;
    //开始时间
    private Date dfStart;
    //结束时间
    private Date dfEnd;
    private String teId;
    //合同Id
    private String conId;
    //编制单位联系方式
    private String dfUnitTel;
    //批复时间
    private Date dfApprovalTime;

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
