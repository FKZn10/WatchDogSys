package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Data
public class SecJGExpertAuditDTO extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = -3992215172481412082L;
    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;
    private String seaName;
    private String seaRemark;
    /**
     * 内外部专家入库 0：外部 1、内部
     */
    private Integer seaInsideType;


    private List<SecJGExpertAuditDetailDTO> secJGExpertAuditDetails;
}
