package com.jpmsoft.projectProducer.pojo.entity.contract;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 工程联系单
 */
@Data
@TableName("Con_Contact_List")
public class ConContactList {
    @TableId(type = IdType.INPUT)
    private String clId;
    private String conId;
    private String clCode;
    private String clType;
    private Integer clNeedCost;
    private BigDecimal clCost;
    private String clLocation;
    private String clDrawingNo;
    private String clTitle;
    private String clContent;
    private String clCreateUid;
    private String clCreateUname;
    private Date clCreateTime;
    private Integer clState;
    private Date clStateTime;
    private String clWithSupUnit;
    private String teId;

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

}
