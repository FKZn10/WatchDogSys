package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 补抽专家选取记录详情
 *
 * @Author: 李文涛 aaron
 * @date: 2023/11/27 14:16
 */
@Data
@TableName("Sec_Supplement_Record_Dtl")
public class SecSupplementRecordDtl {
    @TableId(type = IdType.INPUT)
    private String srdId;
    private String exId;
    private String sesId;
    private Integer srdCount;
    /**
     * 选取类型(抽取|自选)
     */
    private String srdType;
    private Integer srdAvoid;
    private Integer srdAttend;
    private Integer srdVacation;
    private Date srdTime;
    private String srdMark;
}
