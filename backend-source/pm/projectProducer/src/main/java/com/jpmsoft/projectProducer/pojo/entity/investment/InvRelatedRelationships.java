package com.jpmsoft.projectProducer.pojo.entity.investment;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jpmsoft.common.model.BaseModeRequest;
import lombok.Data;

/**
 * 三算关联信息
 */
@Data
@TableName("Inv_Related_Relationships")
public class InvRelatedRelationships extends BaseModeRequest {
    private String id;
    private String mainId;
    private String oldSubId;
    private String nowSubId;
}
