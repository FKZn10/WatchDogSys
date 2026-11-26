package com.jpmsoft.common.model.requestAndResponse.osm;

import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.util.Date;

//设计交底
@Data
public class OsmDesignBriefingDto extends BaseFileRequest {

    private String id;
    /**
     * 合同id
     */
    private String conId;

    private String conName;

    /**
     * 标题名称
     */
    private String title;
    /**
     * 纪要编号
     */
    private String code;
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
