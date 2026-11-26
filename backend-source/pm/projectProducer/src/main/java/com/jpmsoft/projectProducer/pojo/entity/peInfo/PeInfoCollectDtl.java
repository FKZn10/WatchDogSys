package com.jpmsoft.projectProducer.pojo.entity.peInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Pe_Info_Collect_Dtl")
public class PeInfoCollectDtl {
    @TableId(type = IdType.INPUT)
    private String pecdId;
    private String pecId;
    private String peId;
}
