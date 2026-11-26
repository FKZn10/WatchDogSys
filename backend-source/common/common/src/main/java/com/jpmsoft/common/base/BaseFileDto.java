package com.jpmsoft.common.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程表单实体父类
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/1 10:12
 */
@Data
public class BaseFileDto extends BasePageEntity implements Serializable {

    private static final long serialVersionUID = 2772325171905524358L;
    //region 流程表固定字段
    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    //endregion

    //region 流程file相关字段
    @JsonProperty("pId")
    private String pId;
    private String secId;
    private String conId;
    @JsonProperty("tId")
    private String tId;
    @JsonProperty("tCode")
    private String tCode;
    private String fileTitle;
    private String fileCode;
    private String fileRelationId;
    private String fileIds;
    //endregion

}
