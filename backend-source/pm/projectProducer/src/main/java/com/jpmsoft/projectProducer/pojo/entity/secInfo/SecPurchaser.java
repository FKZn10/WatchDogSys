package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 采购人代表信息
 * 其他采购子表
 *
 * @Author: 李文涛 aaron
 * @date: 2023/10/8 16:54
 */
@Data
@TableName("Sec_Purchaser")
public class SecPurchaser {
    @TableId(type = IdType.INPUT)
    private String id;
    private String sopId;
    private String spName;
    private String spTechTitle;
    private String spDepartment;
    private String spPhone;
    private Boolean spStatute;
    private Boolean spExperience;
    private Integer spSort;
}
