package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.requestAndResponse.SecCommonModel;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 资格预审
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:14
 */
@Data
public class SecQePreTrialVo extends SecCommonModel implements Serializable {

    private static final long serialVersionUID = -5787048707507884785L;
    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;

    private String secId;
    private String sqtValuationMethod;
    private BigDecimal sqtAmount;
    private String sqtRemarks;
    private String sqtOtherPricing;

    private String secOrganizeForm;
    private String secBiddingType;
    private Date secStartTime;
    private Date secEndTime;
    private Integer secBiddingCycle;
    private String secPlace;
    private String secTradingPlace;
    private String tendereeName;
    private List<SecProjectResponse> secProjects;
}
