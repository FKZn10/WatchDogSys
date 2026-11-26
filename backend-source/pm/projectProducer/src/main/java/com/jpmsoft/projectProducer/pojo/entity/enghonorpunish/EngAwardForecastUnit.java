package com.jpmsoft.projectProducer.pojo.entity.enghonorpunish;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("Eng_Award_Forecast_Unit")
public class EngAwardForecastUnit implements Serializable {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    private String eafId;

    private String unitId;

}
