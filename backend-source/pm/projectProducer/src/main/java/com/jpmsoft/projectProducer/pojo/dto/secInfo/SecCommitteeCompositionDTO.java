package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 定标委员会及监督委员会构成
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:33
 */
@Data
public class SecCommitteeCompositionDTO extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String sccId;
    private String secId;
    private String sccTenderee;
    private String sccBiddingType;
    private String sccScope;
    private String sccRemark;
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

    private List<SecCalibrationCommitteeDTO> secCalibrationCommittees;
    private List<SecSupervisionCommitteeDTO> secSupervisionCommittees;
}
