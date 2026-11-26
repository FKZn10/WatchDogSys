package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 设计管理（项目信息）
 */
@Data
@TableName("Design_Project_Info")
public class DesignProjectInfo {
    @TableId(type = IdType.INPUT)
    private String id;
    //业务id
    private String businessId;
    //业务类型 1、方案设计 2、初步设计
    private Integer businessType;
    //项目id
    private String pId;
    //用地面积
    private Float landArea;
    //计容建筑面积
    private Float calculatedBuildingArea;
    //建筑占地面积
    private Float buildingFootprintArea;
    //总建筑面积
    private Float totalConstructionArea;
    //地上建筑面积
    private Float aboveGroundBuildingArea;
    //地下建筑面积
    private Float undergroundBuildingArea;
    //人防面积
    private Float civilDefenseArea;
    //绿地面积
    private Float greenArea;
    //总机动车位数
    private Integer totalMotorVehiclesNum;
    //地上机动车位数
    private Integer aboveGroundMotorVehiclesNum;
    //地下机动车位数
    private Integer underGroundMotorVehiclesNum;
    //机动车充电车位数
    private Integer chargingMotorVehiclesNum;
    //总非机动车位数（非机动车）
    private Integer totalVehiclesNum;
    //地上非机动车位数（非机动车）
    private Integer aboveGroundVehiclesNum;
    //地下非机动车位数（非机动车）
    private Integer underGroundVehiclesNum;
    //非机动车充电车位数（非机动车）
    private Integer chargingVehiclesNum;
    //容积率
    private Float volumeRate;
    //建筑密度
    private Float buildingDensity;
    //绿地率
    private Float greenAreaRate;
    //最高建筑高度
    private Float maxBuildingHeight;
    //项目补充描述
    private String supplementDescription;
    //硬质铺装面积
    private Float hardPavingArea;
    //专项投资估算
    private BigDecimal specialInvestmentEstimates;
    //装修面积
    private Float decorationArea;
    //装修范围描述
    private String decorationScopeDescription;
    //幕墙总面积
    private Float totalMullionArea;
    //幕墙范围及材质描述
    private String mullionScopeDescription;
    //单方造价
    private BigDecimal singleCost;
    //备注
    private String remark;
    //数据状态0、历史 1、正常
    private Integer status;
    //删除状态 0、删除 1、正常
    private Integer delFlag;
    //创建人id
    private String CreateUid;
    //创建人姓名
    private String CreateUname;
    //创建时间
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date CreateTime;

}
