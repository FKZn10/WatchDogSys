package com.jpmsoft.projectConsumer.pojo.dto.secInfo;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 质询
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:35
 */
@Data
public class SecQuestionDTO extends BaseFileRequest {
    @ProcessFields(type = ProcessFieldEnum.id)
    private String sqId;
    private String secId;
    private String sqTenderee;
    private String sqBiddingType;
    private String sqScope;
    private Boolean sqWhether;
    private String sqResult;
    private String sqRemark;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;

    private List<SecDefenseReviewDTO> secDefenseReviews;
    private List<SecDefenseSupervisionDTO> secDefenseSupervisions;
}
