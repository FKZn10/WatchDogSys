package com.jpmsoft.projectProducer.pojo.vo.acpm;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 代建投标文件审批
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:53
 */
@Data
public class AcpmDJTBDocumentsApprovalVo extends BaseFileRequest implements Serializable {


    private static final long serialVersionUID = -1818994890924494246L;
    private String id;
    private String acpId;
    private String daCode;
    private BigDecimal daBidBondMoney;
    private String daArea;
    private Date daTenderClosingTime;
    private String daAuthorizedAgent;
    private String daAuthorizedAgentId;
    private String daSpecialInstructions;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String updateUid;
    private String updateUname;
    private Date updateTime;
    private Integer state;
    private Date stateTime;

    private String piaCode;
    private String piaName;
    private String piaArea;
    private String piaType;
    private String piaDeveloper;
    //被授权执行主体 单位名
    private String daAuthorAgentName;
    //被授权执行主体 单位简称
    private String daAuthorAgentAbbName;
    private List<AcpmDJTBSubjectTenderVo> subjectTenders;
}
