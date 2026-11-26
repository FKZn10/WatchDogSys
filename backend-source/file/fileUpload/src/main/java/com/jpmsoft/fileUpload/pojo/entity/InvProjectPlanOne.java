package com.jpmsoft.fileUpload.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Inv_Project_Plan_One")
public class InvProjectPlanOne {

    @TableId(type = IdType.INPUT)
    private String ippoId;
    private String pId;
    private String ippoYear;
    private String ippoDocNumber;
    private BigDecimal ippoReleaseFunds;
    private BigDecimal ippoAnnualReleaseFunds;
    private BigDecimal ippoAnnualAccumFund;
    private String ippoFile;
    private String ippoCreateUid;
    private String ippoCreateUname;
    private Date ippoCreateTime;
}
