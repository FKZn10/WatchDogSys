package com.jpmsoft.projectProducer.pojo.entity.challengePlan;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.util.Date;

/**
 * @author tianhelin
 * @date 2025/5/30
 */
@Data
public class ChallengePlanAnnual {
    @TableId(type = IdType.INPUT)
    private String id;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String declareYear;
    private String pId;
    private String orderNumber;
    private String problems;
    private String planProgress;
    private String remark;
    private String actualBuilderYear;
}