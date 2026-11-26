package com.jpmsoft.common.model.response.Project;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PreProjectOrgStructUnitResponse {

    private String id;
    /**
     * 项目组织机构表id
     */
    private String orgStructId;
    /**
     * 单位所属类型 01:立项相关单位；02：五方责任主体；03、其他单位
     */
    private String unitType;
    /**
     * 组织机构类型
     */
    private String orgStructType;
    /**
     * 是否有XX单位
     */
    private Integer haveUnit;
    /**
     * 单位id
     */
    private String unitId;

    private String unitName;

    //排序
    private Integer sort;

    /**
     * 创建人id
     */
    private String createUid;
    /**
     * 创建人姓名
     */
    private String createUname;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改人
     */
    private String updateUid;
    /**
     * 修改时间
     */
    private Date updateTime;

    private List<PreProjectOrgStructUserResponse> preProjectOrgStructUserList;


}
