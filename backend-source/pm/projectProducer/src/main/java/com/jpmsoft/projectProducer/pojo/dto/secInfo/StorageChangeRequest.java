package com.jpmsoft.projectProducer.pojo.dto.secInfo;


import com.jpmsoft.common.base.FileFields;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.StorageChangeDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StorageChangeRequest extends BaseFileRequest {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    private String remark;
    @FileFields(index = 1)
    private String attachment;
    @FileFields(index = 1,type = "attachmentIds")
    private String attachmentIds;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
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
