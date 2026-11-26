package com.jpmsoft.common.model.requestAndResponse.zjt;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

@Data
public class ProjectUpStatisticsDTO extends BaseModeRequest {
    //填报年份过滤
    private Integer yearFilter;
    //填报月份过滤
    private Integer monthFilter;
    //region  代码逻辑内根据yearFilter  monthFilter赋值
    //填报年份过滤
    private Integer lastYear;
    //填报月份过滤
    private Integer lastMonth;
    //填报月份过滤 拼接0的月份
    private String monthFt;
    //endregion

    //省份
    private String province;
    //市
    private String city;
    //区
    @JsonProperty("pEachDistrict")
    private String pEachDistrict;
    //单位名称
    @JsonProperty("pConstructionUnit")
    private String pConstructionUnit;
    //单位名称
    @JsonProperty("pConstructionUnitName")
    private String pConstructionUnitName;
    //单位类型
    @JsonProperty("pConstructionUnitType")
    private String pConstructionUnitType;
    //本月填报项目数
    private int projectCount;
    //本月新增项目数
    private int projectAddNew;
    //上月填报数
    private int lastFillingCount;
    //本月填报数
    private int fillingCount;

}
