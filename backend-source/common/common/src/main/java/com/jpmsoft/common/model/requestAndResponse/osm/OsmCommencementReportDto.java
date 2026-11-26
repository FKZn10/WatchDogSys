package com.jpmsoft.common.model.requestAndResponse.osm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;

//开工报告
@Data
public class OsmCommencementReportDto extends BaseFileRequest {

    private String id;
    /**
     * 项目id
     */
    private String pId;

    @JsonProperty("pName")
    private String pName;

    /**
     * 监理规划和细则
     */
    private String supervisorPlanRuleFile;
    /**
     * 施工组织设计
     */
    private String organizationDesignFile;
    /**
     * 临时布置方案
     */
    private String temporaryLayoutPlanFile;
    /**
     * 办理人id
     */
    private String handlerUserId;
    /**
     * 办理人姓名
     */
    private String handlerUserName;
    /**
     * 办理时间
     */
    private Date handlerTime;
    /**
     * 是否需要用印
     */
    private Integer usingSeals;
    /**
     * 用印类型
     */
    private String sealsType;
    /**
     * 用印个数
     */
    private String sealsNum;
    /**
     * 用印文件份数
     */
    private Integer sealsFileNum;
    /**
     * 备注说明
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


}
