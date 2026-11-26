package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

import java.util.Date;

/**
 * 材料设备品牌库
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/23 9:48
 */
@Data
@TableName("Me_BrandLibrary")
public class MeBrandLibrary extends BaseEntity {

    private String blaId;
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
    private String teId;
}
