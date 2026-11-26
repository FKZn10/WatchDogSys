package com.jpmsoft.common.model.request.Contract;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConContractPayPlanModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEstimateMaintenanceDtlModel;
import com.jpmsoft.common.model.requestAndResponse.Contract.ConEstimateModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 合同
 */
@Data
public class ConEstimateMaintenanceRequest extends BaseFileRequest {

    private String cemId;
    private String pId;
    private String pName;
    private String conId;
    private String conName;
    private String conCode;
    private String secId;
    private String secName;
    private String cemType;
    private String createUserId;
    private String createUserName;
    private Date createTime;
    private String UpdateUserId;


    private String UpdateUserName;
    private Date updateUserTime;
    private Integer state;
    private Date aprrovalEndTime;

    /**
     * 合同概算维护清单表
     **/
    private List<ConEstimateMaintenanceDtlModel> conEstimateMaintenanceDtlModel;

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }
}
