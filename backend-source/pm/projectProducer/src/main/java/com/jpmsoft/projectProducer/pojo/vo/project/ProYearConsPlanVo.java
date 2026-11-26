package com.jpmsoft.projectProducer.pojo.vo.project;

import com.jpmsoft.common.model.BaseFileResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/9 15:12
 */
@Data
public class ProYearConsPlanVo extends BaseFileResponse implements Serializable {

    private static final long serialVersionUID = 4006208359796550731L;

    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private Date ycpStartTime;
    private Date ycpEndTime;
    private String ycpRemark;
    private List<ProYearPlanSelectVo> proYearPlanSelects;
}
