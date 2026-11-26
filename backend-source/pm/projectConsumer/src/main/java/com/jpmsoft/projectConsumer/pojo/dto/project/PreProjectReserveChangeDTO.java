package com.jpmsoft.projectConsumer.pojo.dto.project;

import com.jpmsoft.common.base.BaseFileDto;
import com.jpmsoft.common.model.request.project.PreChangeOwnerUnitDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目储备
 */
@Data
public class PreProjectReserveChangeDTO extends BaseFileDto {
    private String pprId;
    private String pprName;
    private String pprCode;
    private String pprCompose;
    private String pprModel;
    private String pprShape;
    private String pprClassify;
    private String pprSource;
    private String pprApproval;
    private String pprConstructionYear;
    private String pprOthers;
    private String pprOverview;
    private String pprManagerUnit;
    private String pprManagerDep;
    private String pprBusinessUnit;
    private String pprWorkStage;
    private String pprWorkProgress;
    private String pprRemark;
    private BigDecimal pprRough;
    private String pprReserveCpy;
    private Date pprPlanTime;
    private String pprReserveStatus;
    private String pprReserveStage;
    private String pprReserveProgress;


    private String pprManagerUnitName;
    private String pprBusinessUnitName;
    private String pprReserveCpyName;
    private String pprManagerDepName;

    private List<PreChangeOwnerUnitDTO> preOwnerUnits;

    private String pprRegion;

    private String pprProjectManager;
    private String pprProjectManagerName;
    private String pprProjectLeader;
    private String pprProjectLeaderName;
    private String pprEngineer;
    private String pprEngineerName;
    private String pprEngineerLeader;
    private String pprEngineerLeaderName;

    private String pprType;

    private String excludeDeprecated;
}
