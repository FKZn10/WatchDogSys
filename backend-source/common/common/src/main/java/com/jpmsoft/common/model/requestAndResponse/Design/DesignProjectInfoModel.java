package com.jpmsoft.common.model.requestAndResponse.Design;

import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.ProjectCommonRes;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 设计管理（项目信息）
 */
public class DesignProjectInfoModel extends ProjectCommonRes {

    @ProcessFields(type = ProcessFieldEnum.id)
    private String id;
    //业务id
    private String businessId;
    //业务类型 1、方案设计 2、初步设计 3、施工图设计
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
    private Date CreateTime;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBusinessId() {
        return businessId;
    }

    public void setBusinessId(String businessId) {
        this.businessId = businessId;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    @Override
    public String getpId() {
        return pId;
    }

    @Override
    public void setpId(String pId) {
        this.pId = pId;
    }

    public Float getLandArea() {
        return landArea;
    }

    public void setLandArea(Float landArea) {
        this.landArea = landArea;
    }

    public Float getCalculatedBuildingArea() {
        return calculatedBuildingArea;
    }

    public void setCalculatedBuildingArea(Float calculatedBuildingArea) {
        this.calculatedBuildingArea = calculatedBuildingArea;
    }

    public Float getBuildingFootprintArea() {
        return buildingFootprintArea;
    }

    public void setBuildingFootprintArea(Float buildingFootprintArea) {
        this.buildingFootprintArea = buildingFootprintArea;
    }

    public Float getTotalConstructionArea() {
        return totalConstructionArea;
    }

    public void setTotalConstructionArea(Float totalConstructionArea) {
        this.totalConstructionArea = totalConstructionArea;
    }

    public Float getAboveGroundBuildingArea() {
        return aboveGroundBuildingArea;
    }

    public void setAboveGroundBuildingArea(Float aboveGroundBuildingArea) {
        this.aboveGroundBuildingArea = aboveGroundBuildingArea;
    }

    public Float getUndergroundBuildingArea() {
        return undergroundBuildingArea;
    }

    public void setUndergroundBuildingArea(Float undergroundBuildingArea) {
        this.undergroundBuildingArea = undergroundBuildingArea;
    }

    public Float getCivilDefenseArea() {
        return civilDefenseArea;
    }

    public void setCivilDefenseArea(Float civilDefenseArea) {
        this.civilDefenseArea = civilDefenseArea;
    }

    public Float getGreenArea() {
        return greenArea;
    }

    public void setGreenArea(Float greenArea) {
        this.greenArea = greenArea;
    }

    public Integer getTotalMotorVehiclesNum() {
        return totalMotorVehiclesNum;
    }

    public void setTotalMotorVehiclesNum(Integer totalMotorVehiclesNum) {
        this.totalMotorVehiclesNum = totalMotorVehiclesNum;
    }

    public Integer getAboveGroundMotorVehiclesNum() {
        return aboveGroundMotorVehiclesNum;
    }

    public void setAboveGroundMotorVehiclesNum(Integer aboveGroundMotorVehiclesNum) {
        this.aboveGroundMotorVehiclesNum = aboveGroundMotorVehiclesNum;
    }

    public Integer getUnderGroundMotorVehiclesNum() {
        return underGroundMotorVehiclesNum;
    }

    public void setUnderGroundMotorVehiclesNum(Integer underGroundMotorVehiclesNum) {
        this.underGroundMotorVehiclesNum = underGroundMotorVehiclesNum;
    }

    public Integer getChargingMotorVehiclesNum() {
        return chargingMotorVehiclesNum;
    }

    public void setChargingMotorVehiclesNum(Integer chargingMotorVehiclesNum) {
        this.chargingMotorVehiclesNum = chargingMotorVehiclesNum;
    }

    public Integer getTotalVehiclesNum() {
        return totalVehiclesNum;
    }

    public void setTotalVehiclesNum(Integer totalVehiclesNum) {
        this.totalVehiclesNum = totalVehiclesNum;
    }

    public Integer getAboveGroundVehiclesNum() {
        return aboveGroundVehiclesNum;
    }

    public void setAboveGroundVehiclesNum(Integer aboveGroundVehiclesNum) {
        this.aboveGroundVehiclesNum = aboveGroundVehiclesNum;
    }

    public Integer getUnderGroundVehiclesNum() {
        return underGroundVehiclesNum;
    }

    public void setUnderGroundVehiclesNum(Integer underGroundVehiclesNum) {
        this.underGroundVehiclesNum = underGroundVehiclesNum;
    }

    public Integer getChargingVehiclesNum() {
        return chargingVehiclesNum;
    }

    public void setChargingVehiclesNum(Integer chargingVehiclesNum) {
        this.chargingVehiclesNum = chargingVehiclesNum;
    }

    public Float getVolumeRate() {
        return volumeRate;
    }

    public void setVolumeRate(Float volumeRate) {
        this.volumeRate = volumeRate;
    }

    public Float getBuildingDensity() {
        return buildingDensity;
    }

    public void setBuildingDensity(Float buildingDensity) {
        this.buildingDensity = buildingDensity;
    }

    public Float getGreenAreaRate() {
        return greenAreaRate;
    }

    public void setGreenAreaRate(Float greenAreaRate) {
        this.greenAreaRate = greenAreaRate;
    }

    public Float getMaxBuildingHeight() {
        return maxBuildingHeight;
    }

    public void setMaxBuildingHeight(Float maxBuildingHeight) {
        this.maxBuildingHeight = maxBuildingHeight;
    }

    public String getSupplementDescription() {
        return supplementDescription;
    }

    public void setSupplementDescription(String supplementDescription) {
        this.supplementDescription = supplementDescription;
    }

    public Float getHardPavingArea() {
        return hardPavingArea;
    }

    public void setHardPavingArea(Float hardPavingArea) {
        this.hardPavingArea = hardPavingArea;
    }

    public BigDecimal getSpecialInvestmentEstimates() {
        return specialInvestmentEstimates;
    }

    public void setSpecialInvestmentEstimates(BigDecimal specialInvestmentEstimates) {
        this.specialInvestmentEstimates = specialInvestmentEstimates;
    }

    public Float getDecorationArea() {
        return decorationArea;
    }

    public void setDecorationArea(Float decorationArea) {
        this.decorationArea = decorationArea;
    }

    public String getDecorationScopeDescription() {
        return decorationScopeDescription;
    }

    public void setDecorationScopeDescription(String decorationScopeDescription) {
        this.decorationScopeDescription = decorationScopeDescription;
    }

    public Float getTotalMullionArea() {
        return totalMullionArea;
    }

    public void setTotalMullionArea(Float totalMullionArea) {
        this.totalMullionArea = totalMullionArea;
    }

    public String getMullionScopeDescription() {
        return mullionScopeDescription;
    }

    public void setMullionScopeDescription(String mullionScopeDescription) {
        this.mullionScopeDescription = mullionScopeDescription;
    }

    public BigDecimal getSingleCost() {
        return singleCost;
    }

    public void setSingleCost(BigDecimal singleCost) {
        this.singleCost = singleCost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getDelFlag() {
        return delFlag;
    }

    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }

    public String getCreateUid() {
        return CreateUid;
    }

    public void setCreateUid(String createUid) {
        CreateUid = createUid;
    }

    public String getCreateUname() {
        return CreateUname;
    }

    public void setCreateUname(String createUname) {
        CreateUname = createUname;
    }

    public Date getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(Date createTime) {
        CreateTime = createTime;
    }
}
