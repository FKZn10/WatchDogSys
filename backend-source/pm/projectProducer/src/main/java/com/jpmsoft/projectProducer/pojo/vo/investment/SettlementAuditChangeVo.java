package com.jpmsoft.projectProducer.pojo.vo.investment;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementAuditDetailChange;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class SettlementAuditChangeVo {

    private String id;
    private String projectId;
    private String projectName;
    private Integer auditContractNum;
    private BigDecimal auditContractMoney;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;
    private Integer delFlag;
    private Integer status;

    private  String saId;

    private Date changeTime;

    private List<SettlementAuditDetailChange> detailList;
}
