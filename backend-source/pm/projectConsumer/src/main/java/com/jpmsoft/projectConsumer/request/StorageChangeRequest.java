package com.jpmsoft.projectConsumer.request;


import com.jpmsoft.common.model.BaseFileRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageChangeRequest extends BaseFileRequest {
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
    private String scUnitName;
    /***
     * 区分是集团定标抽取还是小城投定标抽取
     * 01 集团
     * 02 小城投
     */
    private String scCategory = "01";
    private List<StorageChangeDetail> storageChangeDetailList;
}