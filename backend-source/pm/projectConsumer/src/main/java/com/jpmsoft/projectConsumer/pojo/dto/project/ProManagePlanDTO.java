package com.jpmsoft.projectConsumer.pojo.dto.project;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 建设管理方案
 *
 * @Author: 李文涛 aaron
 * @date: 2023/10/18 10:18
 */
@Data
public class ProManagePlanDTO extends ProjectCommonRes implements Serializable {

    private static final long serialVersionUID = -3972643474507819450L;
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
    private String pmpTitle;
    private String pmpRemark;

}
