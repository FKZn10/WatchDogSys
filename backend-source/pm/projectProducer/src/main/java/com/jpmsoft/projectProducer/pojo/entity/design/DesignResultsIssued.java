package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author jjz
 * @date 2025/08/13
 * @description: 成果下发
 */
@Data
@TableName("design_results_Issued")
public class DesignResultsIssued {

    @TableId
    private String id;

    // 创建时间
    private Date createTime;

    // 创建人ID
    private String createUid;

    // 创建人姓名
    private String createUname;

    // 状态
    private Integer State;

    // 状态更新时间
    private Date StateTime;

    // 项目ID
    private String pId;

    // 成果名称
    private String driName;

    // 业务类型
    private String driBussnisType;

    // 成果类型
    private String driType;

    // 纸质成果份数
    private Integer driPaperNum;

    // 专业类别
    private String driCategory;

    // 失效日期  null 为长期有效
    private Date driTerm;

    // 下发详情
    private String driIssuedDescribe;

    // 备注
    private String driRemark;

    // 二维码ID
    private String dqcId;

    // 成果收发类型
    private String driFormType;

    // 是否下发 01 下发 02 不下发 03 补充下发
    private String designIsIssued;

    // 勘察设计成果父级id
    private String driParentId;

    // 变更人
    private String driChangeUid;

    // 是否变更 2 变更 默认不变更
    private String driIsChange;



}
