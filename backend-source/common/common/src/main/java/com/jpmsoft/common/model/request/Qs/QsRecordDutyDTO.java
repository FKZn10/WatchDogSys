package com.jpmsoft.common.model.request.Qs;

import lombok.Data;

import java.io.Serializable;

/**
 * 质量安全检查记录责任人
 *
 * @Author: 李文涛 aaron
 * @date: 2023/9/20 13:38
 */
@Data
public class QsRecordDutyDTO implements Serializable {

    private static final long serialVersionUID = -1652908871194233270L;
    private String id;
    private String qsrId;
    private String qrdCpyId;
    private String qrdUserId;
    //部门id
    private String qrdDepId;
    //部门名称
    private String qrdDepName;
    private String qrdSort;

    private String qrdCpyName;
    private String qrdUserName;
    //责任人名称（手工录入） 关联查询返回qrdUserName
    private String qrdUName;
}
