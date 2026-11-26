package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 专家选取记录
 */
@Data
@TableName("Sec_Selection_Record")
public class SecSelectionRecord {
    @TableId(type = IdType.INPUT)
    private String srId;
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
    /**
     * 专家选取申请ID
     */
    private String esId;
    private String teId;
    //技术类人员抽取申请专业详情
    private String srExList;
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

    //评分方法
    private String srScoreMethod;

    // 标段类型  E 表示默认值 即 之前的数据
    private String srSectionClassify;

    // 代办发送 01 表示已经发送  其他表示 未发送
    private String srSend;

    private String keyExpertSelection;

    /**
     * 重点专家使用申请条件一
     */
    private String conditionA;

    /**
     * 重点专家使用申请条件二
     */
    private String conditionB;

    /**
     * 重点专家使用申请条件三
     */
    private String conditionC;

    /**
     * 重点专家使用申请条件四
     */
    private String conditionD;

    /**
     * 重点专家使用申请条件五
     */
    private String conditionE;

    /**
     * 重点专家使用申请条件四的申请依据
     */
    private String conditionDDetails;

    /**
     * 重点专家使用申请条件五的下级条件
     */
    private String accordingD;

    /**
     * 重点专家使用申请条件五的申请依据
     */
    private String accordingDDetails;

    /**
     * 重点专家使用申请条件五的文件
     */
    private String expertDemonstration;

    /**
     * 重点专家使用申请原因
     */
    private String reason;

    /**
     * 标段类型
     */
    private String secType;
}
