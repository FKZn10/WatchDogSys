package com.jpmsoft.common.model.response.Project;

/**
 * 项目信息全查询时使用的模型
 */
public class PreProjectWhenGetAllResponse {

    private String pId;
    private String pParentId;
    private String pName;
    private Float pLongitude;
    private Float pLatitude;

    private String dataSourceType;

    public String getDataSourceType() {
        return dataSourceType;
    }

    public void setDataSourceType(String dataSourceType) {
        this.dataSourceType = dataSourceType;
    }

    public String getpId() {
        return pId;
    }

    public void setpId(String pId) {
        this.pId = pId;
    }

    public String getpParentId() {
        return pParentId;
    }

    public void setpParentId(String pParentId) {
        this.pParentId = pParentId;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public Float getpLongitude() {
        return pLongitude;
    }

    public void setpLongitude(Float pLongitude) {
        this.pLongitude = pLongitude;
    }

    public Float getpLatitude() {
        return pLatitude;
    }

    public void setpLatitude(Float pLatitude) {
        this.pLatitude = pLatitude;
    }
}
