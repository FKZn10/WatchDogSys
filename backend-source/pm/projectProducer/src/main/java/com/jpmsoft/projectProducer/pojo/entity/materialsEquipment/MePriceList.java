package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import lombok.Data;

/**
 * 核价单
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/4 14:27
 */
@Data
@TableName("Me_Price_List")
public class MePriceList extends BaseEntity {

    @JsonProperty("pId")
    private String pId;
    private String plCode;
    private String plApplicant;
    private String plPm;
    private String plReason;
    private String plRemark;
    private String conId;
    private String teId;

}
