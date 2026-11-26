package com.jpmsoft.common.model.request.SecInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 专家执业证书
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/27 10:08
 */
@Data
public class SecJGExpertCertificateDto implements Serializable {

    private static final long serialVersionUID = -3710163764834360635L;

    private String id;
    private String exId;
    private String ecName;
    private String ecLevel;
    private String ecMajor;
    private String ecDepartment;
    private Date ecTime;
    private String ecPracticing;
    //执业证书附件
    private String ecDoc;
    private Integer ecSort;
    private String createUid;
    private String createUname;
    private Date createTime;

    private String ecDocIds;

}
