package com.jpmsoft.projectProducer.pojo.dto.fundPlan;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * 进度申报
 * @Author: 李文涛 aaron
 * @date: 2024/9/24 19:50
 */
@Data
public class ProgressDeclarationDTO extends BaseFileRequest {

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
    private Date pdDate;
    private String pdRemark;
    private String pdCompany;
    private String pdDepartment;

    private String pdCompanyName;
    private String pdDepartmentName;

    private List<ProgressDeclarationDtlDTO> progressDeclarationDtls;
}
