package com.jpmsoft.projectProducer.pojo.vo.materialsEquipment;

import com.jpmsoft.common.model.BaseFileResponse;
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
public class MeBrandLibraryVo extends BaseFileResponse implements Serializable {

    private static final long serialVersionUID = 442958112319130867L;
    private String id;
    private String blaId;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;
    private String brandName;
    private String brandCode;
    private String metId;
    private Integer brandEnable;
    private String brandLevel;
    private String brandSupplier;
    private String brandProducer;
    private Float brandLeadTime;
    ///规格型号
    private String brandStandardType;
    ///产品执行标准
    private String brandExecutionStandards;
    ///是否取得绿色建材认证 0 否 1 是
    private Integer brandIsGreenBuildingCertification;
    ///是否绿色建材评价 0 否 1 是
    private Integer brandIsGreenBuildingEvaluation;
    private String brandRemark;
    private String editUid;
    private String editUname;
    private Date editTime;

    private String metName;
    private String brandSupplierName;
    private String brandProducerName;
    private String blaCode;
    private String blaTitle;
    // 源id
    private String originId;


}
