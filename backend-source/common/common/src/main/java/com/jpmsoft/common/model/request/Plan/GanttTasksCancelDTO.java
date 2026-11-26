package com.jpmsoft.common.model.request.Plan;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GanttTasksCancelDTO extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    private String taskId;
    private String gtcExplanation;
}