package com.jpmsoft.userProducer.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 表字段修改记录
 */
@Data
public class FiledChangeRecordVo implements Serializable {

    private static final long serialVersionUID = 3607797539523484393L;

    private String fcrId;
    private String fcrTable;
    private String fcrFiled;
    private String fcrType;
    private String fcrKey;
    private String fcrOld;
    private String fcrNew;
    private String fcrUpdateUid;
    private String fcrUpdateUname;
    private Date fcrUpdateTime;
    private String actId;
    private String actName;
}
