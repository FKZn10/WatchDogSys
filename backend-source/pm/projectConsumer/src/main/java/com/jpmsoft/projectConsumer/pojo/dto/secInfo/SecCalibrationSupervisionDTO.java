package com.jpmsoft.projectConsumer.pojo.dto.secInfo;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
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
public class SecCalibrationSupervisionDTO extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String scsId;
    private String secId;
    private String scsTenderee;
    private String scsBiddingType;
    private String scsScope;
    private String scsRemark;
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

    private List<SecCalibrationDtlsDTO> secCalibrationDtls;
    private List<SecSupervisionDtlsDTO> secSupervisionDtls;
}
