package com.jpmsoft.userProducer.pojo.vo;
import java.io.Serializable;
import java.util.List;
public class DepartmentVo implements Serializable {
    private static final long serialVersionUID = -9053268757084913172L;
    public String key ;
    public String title ;

    public String value ;

    public String dId ;
    public String code ;
    public String dName ;
    public String dParentid ;
    public String cId;
    public List<PositionVo> positions ;
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getdId() {
        return dId;
    }

    public void setdId(String dId) {
        this.dId = dId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getdName() {
        return dName;
    }

    public void setdName(String dName) {
        this.dName = dName;
    }

    public String getdParentid() {
        return dParentid;
    }

    public void setdParentid(String dParentid) {
        this.dParentid = dParentid;
    }

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId;
    }

    public List<PositionVo> getPositions() {
        return positions;
    }

    public void setPositions(List<PositionVo> positions) {
        this.positions = positions;
    }
}
