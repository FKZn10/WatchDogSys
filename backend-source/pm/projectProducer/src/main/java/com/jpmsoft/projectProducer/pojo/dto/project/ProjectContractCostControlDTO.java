package com.jpmsoft.projectProducer.pojo.dto.project;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BasePageEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.List;

/**
 * 项目施工合同造价控制统计分页查询条件。
 * pId 和 projectIds 同时指定时取交集；列表为空时不按列表筛选，pId 条件仍生效。
 * 两种项目条件均未指定时查询全部符合审批和删除状态的项目。
 * 无论是否指定项目，仅返回未删除、审批已结束的项目。
 */
@Data
@JsonIgnoreProperties("pageStart")
@EqualsAndHashCode(callSuper = true)
public class ProjectContractCostControlDTO extends BasePageEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 单个项目 ID，可选。
     */
    @JsonProperty("pId")
    private String pId;
    /**
     * 多个项目 ID，可选；不允许包含空值或空白字符串。
     */
    private List<String> projectIds;

    /**
     * 分页默认页码为 1，每页 10 条；每页数量上限为 100。
     */
    public ProjectContractCostControlDTO() {
        setPage(1);
        setSize(10);
    }

    /**
     * 获取单个项目筛选条件，并固定 JSON 字段名为 pId。
     *
     * @return 项目 ID
     */
    @JsonProperty("pId")
    public String getPId() {
        return pId;
    }

    /**
     * 设置单个项目筛选条件。
     *
     * @param pId 项目 ID
     */
    @JsonProperty("pId")
    public void setPId(String pId) {
        this.pId = pId;
    }
}
