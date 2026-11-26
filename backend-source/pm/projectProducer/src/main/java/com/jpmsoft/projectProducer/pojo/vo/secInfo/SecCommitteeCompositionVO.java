package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.BaseFileResponse;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 入围定标委员会及监督委员会构成
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:33
 */
@Data
public class SecCommitteeCompositionVO extends BaseFileResponse {

    private String sccId;
    private String secId;
    private String sccTenderee;
    private String sccBiddingType;
    private String sccScope;
    private String sccRemark;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;

    private String secName;
    private String tendereeName;
    private List<SecProjectResponse> secProjects;
    private List<SecCalibrationCommitteeVO> secCalibrationCommittees;
    private List<SecSupervisionCommitteeVO> secSupervisionCommittees;
}
