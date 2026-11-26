package com.jpmsoft.projectConsumer.pojo.dto.materialsEquipment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.common.base.ProcessFields;
import com.jpmsoft.common.enums.ProcessFieldEnum;
import com.jpmsoft.common.model.BaseFileRequest;
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
public class MeBrandRecommendDto extends BaseFileRequest implements Serializable {

    private static final long serialVersionUID = 138398752785382594L;

    @ProcessFields(type = ProcessFieldEnum.id)
    private String brId;
    private String brCode;
    private String brTitle;
    @JsonProperty("pId")
    private String pId;
    private String secId;
    private String brRemark;
    @ProcessFields(type = ProcessFieldEnum.state)
    private Integer state;
    @ProcessFields(type = ProcessFieldEnum.stateTime)
    private Date stateTime;
    @ProcessFields(type = ProcessFieldEnum.createUid)
    private String createUid;
    @ProcessFields(type = ProcessFieldEnum.createUname)
    private String createUname;
    @ProcessFields(type = ProcessFieldEnum.createTime)
    private Date createTime;

    @JsonProperty("pName")
    private String pName;
    private String secName;
    private List<MeBrandRecommendDtlDto> meBrandRecommendDtls;

}
