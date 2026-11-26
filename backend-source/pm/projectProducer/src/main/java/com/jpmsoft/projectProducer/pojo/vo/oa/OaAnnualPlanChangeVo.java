package com.jpmsoft.projectProducer.pojo.vo.oa;

import com.jpmsoft.common.model.BaseFileResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 年度计划变更
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/2 15:00
 */
@Data
public class OaAnnualPlanChangeVo extends BaseFileResponse implements Serializable {


    private static final long serialVersionUID = -2791845027154326092L;
    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;

    private String apId;
    private String apTitle;
    private String apYear;
    private String apRemark;
    private int dtlCount;
    private int addCount;
    private int delCount;
    private List<OaAnnualPlanChangeDtlVo> oaAnnualPlanChangeDtls;
}
