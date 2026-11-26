package com.jpmsoft.jpbpm.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;


@Data
@TableName("Sys_Task_Processin_Time")
public class SysTaskProcessinTime {
    @TableId(type = IdType.INPUT)
    public String tptId;
    public String tpId;
    public Date tptBeginTime;
    public Date tptDoTime;
    public String tptActId;
    public BigDecimal tptDuration;
    public String tptActName;
    public String tptActType;
    public String tptApprovalType;
    public String tptUserName;
    public String tptUser;
    public String tptUserType;
    public String tptCId;
    public String tptCName;
    public String tptDId;
    public String tptDName;
    public String tptDCode;
    public String tptName;
    public String tptPDid;
    public String tptPDname;
    public String tptPDcode;
    public Float tptTimeoutTime;
    public String tptFileId;
    public Date tptApprovalTime;
    public Integer tptApprovalState;
    public String tptUserAccount;
    public String tptFileCode;
    public String tptExplain;
    public String tptrUserId;
    public String tptrUserName;
    public String tptrUserType;
    public String tptrUserAccount;
    public String tptrDId;
    public String tptrDName;
    public String tptrDCode;
    public String tptrActId;
    public String tptrActName;
    public String tptPId;
    private String teId;
}
