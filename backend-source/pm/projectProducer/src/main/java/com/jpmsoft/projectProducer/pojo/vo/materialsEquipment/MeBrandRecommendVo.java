package com.jpmsoft.projectProducer.pojo.vo.materialsEquipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.model.BaseFileResponse;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 材料设备品牌推荐表
 *
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:40
 */
@Data
public class MeBrandRecommendVo extends BaseFileResponse implements Serializable {

    private static final long serialVersionUID = 138398752785382594L;

    private String brId;
    private String brCode;
    private String brTitle;
    @JsonProperty("pId")
    private String pId;
    private String secId;
    private String brRemark;
    private Integer state;
    private Date stateTime;
    private String createUid;
    private String createUname;
    private Date createTime;

    @JsonProperty("pName")
    private String pName;
    private String secName;
    private List<MeBrandRecommendDtlVo> meBrandRecommendDtls;

}
