package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
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
public class SecClearTeamDTO extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String sctId;
    private String secId;
    private String sctTenderee;
    private String sctBiddingType;
    private String sctScope;
    private String sctRemark;
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

    private List<SecClearTeamDtlDTO> secClearTeamDtls;
}
