package com.jpmsoft.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageDetailDTO {

    private String id;
    private String remark;
    private String attachment;
    private String attachmentIds;
    private String createUid;
    private String createUname;
    private Date createTime;
    private Integer state;
    private Date stateTime;
    private String scCode;
    private String scCategory;
    private String scUnitName;
    private Object storageChangeDetailList;
}
