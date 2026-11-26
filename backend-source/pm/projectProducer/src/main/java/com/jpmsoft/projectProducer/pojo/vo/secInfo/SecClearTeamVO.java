package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.BaseFileResponse;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 清标小组
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:29
 */
@Data
public class SecClearTeamVO extends BaseFileResponse {

    private String sctId;
    private String secId;
    private String sctTenderee;
    private String sctBiddingType;
    private String sctScope;
    private String sctRemark;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;

    private String secName;
    private String tendereeName;
    private List<SecProjectResponse> secProjects;
    private List<SecClearTeamDtlVO> secClearTeamDtls;
}
