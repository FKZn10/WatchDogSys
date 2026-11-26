package com.jpmsoft.projectProducer.pojo.entity.project;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@TableName("Pre_Project_Reserve_dtl")
public class PreProjectReserveDtl  extends BaseEntity {

    private String pprName;
    private String pprCode;
    private String pprCompose;
    private String pprModel;
    private String pprShape;
    private String pprClassify;
    private String pprSource;
    private String pprApproval;
    private String pprConstructionYear;
    private String pprOverview;
    private String pprManagerUnit;
    private String pprManagerDep;
    private String pprBusinessUnit;
    private String pprWorkStage;
    private String pprWorkProgress;
    private String pprOthers;
    private String pprRemark;
    private BigDecimal pprRough;
    private String pprReserveCpy;
    private Date pprPlanTime;
    private String pprReserveStatus;
    private String pprReserveStage;
    private String pprReserveProgress;
    private String pprManagerDepName;
    private String pprId;

    private String pprProjectManager;
    private String pprProjectLeader;
    private String pprEngineer;
    private String pprEngineerLeader;
}
