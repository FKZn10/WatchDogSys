package com.jpmsoft.common.model.response.SecInfo;

import com.jpmsoft.common.model.requestAndResponse.SecCommonModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class SecContractSignPleaseReviewResponse extends SecCommonModel implements Serializable {

    private String csprId;

    private String secId;
    /**
     * 报审事项
     */
    private String csprReviewMatter;
    /**
     * 报审单位
     */
    private String csprApprovalUnit;
    /**
     * 中标单位
     */
    private String csprWinningBidder;
    /**
     * 报审内容
     */
    private String csprApprovalContent;
    /**
     * 签订合同价
     */
    private Double csprSignContractPrice;

    private String createUid;

    private String createUname;

    private Date createTime;

    private String createDid;

    private String updateUid;

    private Date updateTime;

    private Integer state;

    private Date stateTime;

    private String remark;

    //送审资料
    private List<SecReviewMaterialResponse> secReviewMaterialList;

}
