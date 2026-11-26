package com.jpmsoft.projectProducer.pojo.dto.materialsEquipment;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 材料设备品牌库
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/23 9:48
 */
@Data
public class MeBrandLibraryDto extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = -928308237169118148L;

    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    private String blaId;
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
    private String brandName;
    private String brandCode;
    private String metId;
    private Integer brandEnable;
    private String brandLevel;
    private String brandSupplier;
    private String brandProducer;
    ///规格型号
    private String brandStandardType;
    ///产品执行标准
    private String brandExecutionStandards;
    ///是否取得绿色建材认证 0 否 1 是
    private Integer brandIsGreenBuildingCertification;
    ///是否绿色建材评价 0 否 1 是
    private Integer brandIsGreenBuildingEvaluation;
    private Float brandLeadTime;
    private String brandRemark;
    private String editUid;
    private String editUname;
    private Date editTime;
}
