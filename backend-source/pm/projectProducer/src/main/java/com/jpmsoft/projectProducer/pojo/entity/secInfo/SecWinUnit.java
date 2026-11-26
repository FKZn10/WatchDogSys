package com.jpmsoft.projectProducer.pojo.entity.secInfo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 中标单位
 */
@Data
@TableName("Sec_Win_Unit")
public class SecWinUnit {

    @TableId(type = IdType.INPUT)
    private String wuId;
    private String wnId;
    private String wuCompanyId;
}
