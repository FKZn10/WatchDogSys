package com.jpmsoft.projectProducer.pojo.vo.investment;

import com.jpmsoft.common.model.BaseFileResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 基建投资月报
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/5 9:00
 */
@Data
public class InvInfrastructureMonthVo extends BaseFileResponse implements Serializable {


    private static final long serialVersionUID = -2429425946572332882L;
    private String id;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String imYear;
    private String imMon;
    private String imRemark;

    private List<InvInfrastructureMonthDtlVo> invInfrastructureMonthDtls;
}
