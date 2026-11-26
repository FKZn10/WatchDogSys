package com.jpmsoft.common.model.request.SecInfo;

import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 招标工作方案及询价文件
 *
 */
@Data
public class SecWorkSchemaInquiryDocDTO extends BaseFileRequest implements Serializable {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
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

    private String secId;
    private String wsidRemark;
    //询价文件
    @FileFields(index = 1)
    private String inquiryDoc;
    @FileFields(index = 1, type = "inquiryDocFileIds")
    private String inquiryDocFileIds;


    private String secName;
    private String secOrganizeForm;
    private String secBiddingType;
    private String tendereeName;
    private Date secStartTime;
    private Integer secBiddingCycle;
    private String secPlace;
    private String secType;
    private BigDecimal secMoney;
    private String secAgencyName;
    private String secContractingDepartment;
    private String secTradingPlace;
    private String secOtherValuation;
    private String secPricingMethod;

    private List<SecInviteDetailInquiryDocDTO> secInviteDetailInquiryDocs;
    private List<SecProjectResponse> secProjects;
}
