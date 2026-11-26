package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("Sec_Win_Notice_Dtl")
public class SecWinNoticeDtl {
    @TableId(type = IdType.INPUT)
    private String wndId;
    private String wnId;
    private String cId;
    private Double wndPriceAndRate;
    private String wndSort;
    private String wndPleader;
    private Double wndPriceAndPrice;
    private String cName;
}