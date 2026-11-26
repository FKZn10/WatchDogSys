package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 项目问题详情（清单）
 */
@Data
@TableName("OA_Problem_Dtl")
public class OaProblemDtl {
    @TableId(type = IdType.INPUT)
    private String pmdId;
    private String pmId;
    private String pmdTitle;
    private String pmdType;
    private String pmdContent;
    private String pmdSolution;
    private Integer pmdStatus;
    private Date pmdCreateTime;
    private String pmdDutyUid;
    private String pmdDutyUname;
    private String pmdCode;
}
