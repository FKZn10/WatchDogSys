package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.requestAndResponse.SecCommonModel;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 招标采购申请
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/13 16:16
 */
@Data
public class SecProcurementApplyVo extends SecCommonModel implements Serializable {

    private static final long serialVersionUID = 893452316599141303L;
    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;

    private String secId;
    private String spaValuationMethod;
    private BigDecimal spaAmount;
    private String spaRemarks;
    private String spaOtherPricing;

    private String secOrganizeForm;
    private String secBiddingType;
    private Date secStartTime;
    private Date secEndTime;
    private String secConType;
    private Integer secBiddingCycle;
    private String secPlace;
    private String secTradingPlace;
    private String tendereeName;

    private List<SecProjectResponse> secProjects;
}
