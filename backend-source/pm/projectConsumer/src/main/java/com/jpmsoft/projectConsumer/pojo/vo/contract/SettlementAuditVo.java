package com.jpmsoft.projectConsumer.pojo.vo.contract;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
public class SettlementAuditVo   extends BaseFileRequest {
    private String id;
    private String projectId;
    private String projectName;
    private Integer auditContractNum;
    private BigDecimal auditContractMoney;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer delFlag;
    private Integer status;

    private String type;  //类型，保存还是修改

    private List<SettlementAuditDetailVo> detailList;

    private String saId;

    private List<String> projectNameList;
    private List<Integer> stateList;
    private List<Integer> auditContractNumList;
    private List<BigDecimal> auditContractMoneyList;
    private List<String> createUnameList;
}
