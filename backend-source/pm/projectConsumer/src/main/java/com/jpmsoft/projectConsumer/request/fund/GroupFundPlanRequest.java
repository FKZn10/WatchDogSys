package com.jpmsoft.projectConsumer.request.fund;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.util.List;

@Data
public class GroupFundPlanRequest extends BasePageEntity {
    private String id;
    private String pId;
    private String projectId;
    private String contractId;
    private String projectName;
    private Integer gfpMonth;
    private Integer gfpYear;
    private String groupType;
    private String usaId;
    private String gfpApplicationCid;
    private String gfpApplicationCidName;
    private String gfpdType;
    private String payCompany;
    private List<String> gfpFundDemandAmounts;
    private List<String> createTimes;
    private Integer state;
    private String createUname;

    /**
     * 历史版本id
     */
    private String groupId;

    /**
     * 是否历史版本
     */
    private String groupMainType;

    /**
     * 计量计价id
     */
    private String cmpId;
}
