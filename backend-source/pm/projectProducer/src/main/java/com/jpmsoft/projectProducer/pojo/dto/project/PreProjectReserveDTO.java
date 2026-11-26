package com.jpmsoft.projectProducer.pojo.dto.project;

import com.jpmsoft.common.base.BaseFileDto;
import com.jpmsoft.common.model.request.project.PreOwnerUnitDTO;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 项目储备
 */
@Data
public class PreProjectReserveDTO extends BaseFileDto {

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
    private String pprRemark;
    private BigDecimal pprRough;
    private String pprReserveCpy;
    private Date pprPlanTime;
    private String pprOthers;
    private String pprReserveStatus;
    private String pprReserveStage;
    private String pprReserveProgress;

    private String pprManagerUnitName;
    private String pprBusinessUnitName;
    private String pprReserveCpyName;
    private String pprManagerDepName;

    private List<String>  pprNameList;
    private List<String>  pprModelList;
    private List<BigDecimal>  pprRoughList;
    private List<String>  pprReserveCpyNameList;
    private List<String>  pprManagerUnitNameList;
    private List<String>  pprManagerDepNameList;
    private List<String>  pprReserveStageList;
    private List<String>  pprReserveStatusList;
    private List<String>  createUnameList;
    private List<String>  stateList;

    private String pprPlanTimeStr;
    private String createTimeStr;

    private List<PreOwnerUnitDTO> preOwnerUnits;

    private String pprRegion;

    private String pprProjectManager;
    private String pprProjectManagerName;
    private String pprProjectLeader;
    private String pprProjectLeaderName;
    private String pprEngineer;
    private String pprEngineerName;
    private String pprEngineerLeader;
    private String pprEngineerLeaderName;

    // 项目分类
    private String pprType;

    private String projectId;
    private String projectState;
}
