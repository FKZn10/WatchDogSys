package com.jpmsoft.common.model.request.project;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.response.Project.PreProjectOrgStructUnitResponse;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PreProjectOrgStructRequest extends BaseFileRequest {

    private String id;
    /**
     * 项目id
     */
//    private String pId;

    private String pName;

    //项目管理单位
    private String pCompetentDepartment;

    private String pCompetentDepartmentName;

    /**
     * 证明文件
     */
    private String proofFile;

    private String proofFileIds;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建人id
     */
    private String createUid;
    /**
     * 创建人姓名
     */
    private String createUname;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 流程状态
     */
    private Integer state;
    /**
     * 审批时间
     */
    private Date stateTime;

    private List<PreProjectOrgStructUnitResponse> preProjectOrgStructUnitList;


}
