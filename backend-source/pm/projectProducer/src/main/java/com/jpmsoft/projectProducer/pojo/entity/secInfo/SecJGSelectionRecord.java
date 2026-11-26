package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 专家选取记录（建管）
 */
@Data
@TableName("Sec_JG_Selection_Record")
public class SecJGSelectionRecord {
    @TableId(type = IdType.INPUT)
    private String id;
    //会议/咨询类型
    private String srType;
    //选取时间
    private Date srAccessTime;
    //会议事项简介
    private String srAbout;
    //会议开始时间
    private Date srStart;
    //选取人所属主管机构
    private String srCompetent;
    //咨询事项
    private String srItems;
    //资料清单
    private String srDataLists;
    //注意事项
    private String srNotes;
    //联系人
    private String srContact;
    //电话
    private String srPhone;
    private Integer state;
    private Date stateTime;
    private Date srCreateTime;
    private String srCreateUid;
    private String srCreateUname;
    //抽取人id
    private String extractUid;
    private String extractUname;
    /**
     * 专家选取申请ID
     */
    private String esId;
    private String teId;
    //技术类人员抽取申请专业详情（内部）
    private String srInsideExList;
    //技术类人员抽取申请专业详情（外部）
    private String srOutsideExList;
    //经济类人员抽取申请专业详情（外部）
    private String srOutsideEsList;
    //技术类专家总人数（内部）
    private Integer srInsideTechnologySum;
    //经济类专家总人数（内部）
    private Integer srInsideEconomicsSum;
    //技术类人数（内部）
    private Integer srInsideTechnologyNum;
    //经济类人数（内部）
    private Integer srInsideEconomicsNum;
    //专家总人数（内部）
    private Integer srInsideExpertNum;
    //技术类专家总人数（外部）
    private Integer srOutsideTechnologySum;
    //经济类专家总人数（外部）
    private Integer srOutsideEconomicsSum;
    //技术类人数（外部）
    private Integer srOutsideTechnologyNum;
    //经济类人数（外部）
    private Integer srOutsideEconomicsNum;
    //专家总人数（外部）
    private Integer srOutsideExpertNum;
    //技术类专家总人数
    private Integer srTechnologySum;
    //经济类专家总人数
    private Integer srEconomicsSum;
    //技术类人数
    private Integer srTechnologyNum;
    //经济类人数
    private Integer srEconomicsNum;
    //专家总人数
    private Integer srExpertNum;
    //开标时间
    private Date srBidTime;
    //开标地点
    private String srLocation;
    //申请单位
    private String srCompany;
    private String pId;
    private String secId;
    //使用方式
    private String srMode;
    //其他说明
    private String srOtherTxt;
    /**
     * 回避企业id
     */
    private String avoidCompanyId;
    /**
     * 回避企业名称
     */
    private String avoidCompanyName;

    /**
     * 备注
     */
    private String remark;


    //是否是全过程
    private Integer srIsAllProcessAsk;

    //标段名称
    private String secName;

    // 区分是专家抽取还是评委抽取 01 - 专家  02 - 评委
    private String srIsJudge;
}
