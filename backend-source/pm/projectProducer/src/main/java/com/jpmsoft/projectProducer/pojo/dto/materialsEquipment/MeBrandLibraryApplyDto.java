package com.jpmsoft.projectProducer.pojo.dto.materialsEquipment;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:03
 */
@Data

public class MeBrandLibraryApplyDto extends BaseFileRequest implements Serializable {


    private static final long serialVersionUID = 4657825752030839506L;
    @ProcessFields(type = ProcessFieldEnum.id)
    private String blaId;
    private String blaCode;
    private String blaTitle;
    private String blaRemark;
    private Date blaDate;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;

    private List<MeBrandLibraryDto> meBrandLibrarys;
}
