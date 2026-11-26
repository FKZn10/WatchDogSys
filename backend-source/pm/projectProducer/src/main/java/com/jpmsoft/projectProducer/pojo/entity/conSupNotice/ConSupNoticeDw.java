package com.jpmsoft.projectProducer.pojo.entity.conSupNotice;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 监理通知单其他收文单位
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/6 14:42
 */
@Data
@TableName("Con_Sup_Notice_Dw")
public class ConSupNoticeDw {
    @TableId(type = IdType.INPUT)
    private String id;
    private String snId;
    private String sndCompanyId;
    private Integer sndSort;
    @TableField(exist = false)
    private String sndCompanyName;
}
