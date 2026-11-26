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
public class SecCalibrationSupervisionVO extends BaseFileResponse {

    private String scsId;
    private String secId;
    private String scsTenderee;
    private String scsBiddingType;
    private String scsScope;
    private String scsRemark;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;

    private String secName;
    private String tendereeName;
    private List<SecProjectResponse> secProjects;
    private List<SecCalibrationDtlsVO> secCalibrationDtls;
    private List<SecSupervisionDtlsVO> secSupervisionDtls;
}
