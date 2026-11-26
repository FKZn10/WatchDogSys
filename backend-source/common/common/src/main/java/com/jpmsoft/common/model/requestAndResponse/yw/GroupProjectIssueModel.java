package com.jpmsoft.common.model.requestAndResponse.yw;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jpmsoft.common.base.BaseFileDto;
import lombok.Data;

import java.util.Date;
@Data
public class GroupProjectIssueModel extends BaseFileDto {
    private static final long serialVersionUID = -2117833123258649012L;
    /**
     *
     */
    private String id;

    /**
     *
     */
    private String projectId;
    /**
     *
     */
    private String projectName;

    /**
     *
     */
    private String yearPlan;

    /**
     *
     */
    private String existProblem;

    /**
     *
     */
    private String nextSuggestion;

    /**
     *
     */
    private String responsibleUnit;

    /**
     *
     */
    private String completeImplementation;

    /**
     *
     */
    private String issueCategory;

    /**
     *
     */
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    private Date registerDate;

    /**
     *
     */
    private String currentProgress;

    /**
     * 是否集团领导出面协调 0否 1 是
     */
    private Integer isGroupLeaderMeetCoordinate;

    /**
     *
     */
    private String projectLeader;
    /**
     *
     */
    private String projectLeaderName;

    /**
     *
     */
    private String specificCoordinateItems;

    /**
     *
     */
    private String cancelTime;

    /**
     *
     */
    private String responsiblePerson;
    /**
     *
     */
    private String responsiblePersonName;

    /**
     *
     */
    private String specificContact;
    /**
     *
     */
    private String specificContactName;

    /**
     *
     */
    private String remark;

    /**
     *
     */
    private String fileId;
    private String fileIds;
}