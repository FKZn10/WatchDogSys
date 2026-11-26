package com.jpmsoft.common.model.request.SecInfo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JudgeExtractionDTO extends BaseFileRequest {

    private String id;
    @JsonProperty("pId")
    private String pId;
    @JsonProperty("pName")
    private String pName;
    private String secId;
    private String secName;
    private Date bidTime;
    private String bidLocation;
    @JsonProperty("cId")
    private String cId;
    @JsonProperty("cName")
    private String cName;
    private String contactPerson;
    private String contactPhone;
    private Integer insideCount;
    private Integer outCount;
    private String avoidUnit;
    private String avoidUnitName;
    private String remark;
    private String attachment;
    private String attachmentIds;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    /**
     * 区分是集团还是小城投
     * 集团 01
     * 小城投 02
     */
    private String jeCategory = "01";

    private List<InsExpertRequest> judgeExtractions;

    private String experType;

    private Date extractTime;


    private String exDId; // 所属部门ID
    private String exDName;
}