package com.jpmsoft.common.model.requestAndResponse.Project;

import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CompanyRegisterModel extends BasePageEntity {
    /**
     * id
     */
    private String id;

    /**
     *
     */
    private String creditCode;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String type;
    /**
     * 省市区
     */
    private List<String> provinceCityDistrictList;
    private String provinceCityDistrict;
    /**
     *
     */
    private String address;

    /**
     *
     */
    private String licenseUrl;
    private String licenseUrlFileIds;
    /**
     *
     */
    private String contactPerson;

    /**
     *
     */
    private String contactPhone;

    /**
     *
     */
    private String status;

    /**
     *
     */
    private String auditComment;

    /**
     *
     */
    private String declarationAttachment;
    /**
     *
     */
    private String declarationAttachmentFileIds;
    /**
     * 审批时间
     */
    private LocalDateTime stateTime;

    /**
     * 创建人id
     */
    private String createUid;

    /**
     * 创建人
     */
    private String createUname;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    private String authCode;

    public String uPassword;
}
