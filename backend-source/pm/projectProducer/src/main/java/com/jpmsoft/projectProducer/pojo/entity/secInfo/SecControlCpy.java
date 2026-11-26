package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/24 20:33
 */
@Data
@TableName("Sec_Control_Cpy")
public class SecControlCpy {

    @TableId(type = IdType.INPUT)
    private String id;
    private String cpId;
    private String sccAuditUnit;
    private String sccUnitPrice;
    private Date createTime;
}
