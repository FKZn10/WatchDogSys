package com.jpmsoft.projectConsumer.pojo.dto.acpm;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 投拓保证金申请
 * Created By:李文涛 aaron
 * Created Time:2022/6/23 15:32
 */
@Data
public class AcpmInvituoMarginApplicationDto extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = -6382578845580089526L;

    private String id;
    private String acpId;
    private String imaCode;
    private BigDecimal imaBidBondMoney;
    private String imaPayee;
    private String imaPayeeBank;
    private String imaPayeeCaName;
    private String imaPayeeCa;
    private String imaRemark;
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
    //收款单位名
    private String imaPayeeName;
    //收款单位简称
    private String imaPayeeAbbName;
    //投标保函/投标保证金
    private List<String> imaBidBondMoneys;
}
