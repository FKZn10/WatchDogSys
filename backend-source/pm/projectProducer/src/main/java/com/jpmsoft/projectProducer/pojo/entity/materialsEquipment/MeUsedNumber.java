package com.jpmsoft.projectProducer.pojo.entity.materialsEquipment;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 使用报审使用数量
 *
 * @Author: 李文涛 aaron
 * @date: 2024/2/4 9:48
 */
@Data
@TableName("Me_Used_Number")
public class MeUsedNumber {
    @TableId(type = IdType.INPUT)
    private String id;
    private String uaId;
    private String eadId;
    private Integer munNumber;
}
