package com.jpmsoft.projectProducer.pojo.entity.enghonorpunish;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("Eng_Honor_Unit")
public class EngHonorUnit implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String ehId;

    private String unitId;

}
