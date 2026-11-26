package com.jpmsoft.common.model.response.SecInfo;

import com.jpmsoft.common.model.requestAndResponse.SecCommonModel;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 控制价请审
 */
@Data
public class SecControlPricePleaseReviewResponse extends SecCommonModel implements Serializable {


    private String cpprId;

    private String secId;
    /**
     * 报审事项
     */
    private String cpprReviewMatter;
    /**
     * 报审单位
     */
    private String cpprApprovalUnit;
    /**
     * 报审内容
     */
    private String cpprApprovalContent;

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
