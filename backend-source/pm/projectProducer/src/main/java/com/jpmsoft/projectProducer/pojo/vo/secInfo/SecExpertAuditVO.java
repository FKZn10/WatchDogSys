package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/5 9:14
 */
@Data
public class SecExpertAuditVO extends BaseFileRequest implements Serializable {


    private static final long serialVersionUID = 3171661722631785029L;
    private String id;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String seaName;
    private String seaRemark;
    private String seaCid;

    private int detailCount;
    private Integer year;
    private List<SecExpertAuditDetailVO> secExpertAuditDetails;
}
