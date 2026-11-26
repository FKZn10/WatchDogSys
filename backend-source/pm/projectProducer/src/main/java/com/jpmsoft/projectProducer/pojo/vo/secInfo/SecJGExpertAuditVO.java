package com.jpmsoft.projectProducer.pojo.vo.secInfo;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class SecJGExpertAuditVO extends BaseFileRequest implements Serializable {


    private static final long serialVersionUID = 3171661722631785029L;
    private String id;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String seaName;
    private String seaRemark;

    private int detailCount;
    private int detailInsideCount;//内部专家数
    private List<SecJGExpertAuditDetailVO> secJGExpertAuditDetails;
}
