package com.jpmsoft.projectProducer.pojo.entity.yw;

import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class GroupProjectIssue extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2932507273616262082L;
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
    private Date registerDate;

    /**
     *
     */
    private String currentProgress;

    /**
     *
     */
    private Integer isGroupLeaderMeetCoordinate;

    /**
     *
     */
    private String projectLeader;

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
    private String specificContact;

    /**
     *
     */
    private String remark;

    /**
     *
     */
    private String fileId;

    /**
     *
     */
    private String createUid;

    /**
     *
     */
    private String createUname;

    /**
     *
     */
    private Date createTime;

    /**
     *
     */
    private Integer state;

    /**
     *
     */
    private Date stateTime;
}