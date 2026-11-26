package com.jpmsoft.common.model.requestAndResponse.Contract;

import com.jpmsoft.common.model.BaseFileRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectDtlModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 合同
 */
@Data
public class EstimateListModel extends BaseFileRequest {

    private String id;
    private String fid;
    private String number;
    private String parenetId;
    private String code;
    private String name;
    private String unit;
    private Float count;
    private BigDecimal price;
    private BigDecimal total;
    private String remark;
    private Integer order;
    private Integer level;
    private Integer type;
    private Integer state;
    private String path;
    private Integer leafNode;
    private List<EstimateListModel> childNodes;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }


    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getParenetId() {
        return parenetId;
    }

    public void setParenetId(String parenetId) {
        this.parenetId = parenetId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Float getCount() {
        return count;
    }

    public void setCount(Float Count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer Level) {
        this.level = level;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer Type) {
        this.type = type;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer State) {
        this.state = state;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String Path) {
        this.path = path;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getLeafNode() {
        return leafNode;
    }

    public void setLeafNode(Integer leafNode) {
        this.leafNode = leafNode;
    }

    public List<EstimateListModel> getChildNodes() {
        return this.childNodes;
    }

    public void setChildNodes(List<EstimateListModel> childNodes) {
        this.childNodes = childNodes;
    }

}
