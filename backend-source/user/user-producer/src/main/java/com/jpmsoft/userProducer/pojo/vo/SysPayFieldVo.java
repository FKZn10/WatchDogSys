package com.jpmsoft.userProducer.pojo.vo;

import lombok.Data;

import java.util.Date;

/**
 * 模板字段
 * Created By:李文涛 aaron
 * Created Time:2022/7/5 10:06
 */
@Data
public class SysPayFieldVo {


    private static final long serialVersionUID = -6890197752148198637L;

    private String id;
    private String ptId;
    private String pfName;
    private String pfCode;
    private String pfType;
    private String pfFormula;
    private String pfVerification;
    private String pfRemark;
    private String pfSort;
    private String pfWrite;
    private String createUid;
    private String createUname;
    private Date createTime;

}
