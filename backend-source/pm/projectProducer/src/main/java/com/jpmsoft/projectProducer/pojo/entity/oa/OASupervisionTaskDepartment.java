package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 督办任务
 */
@Data
@TableName("OA_Supervision_Task_Department")
public class OASupervisionTaskDepartment {
    @TableId(type = IdType.INPUT)
    private String stdId;
    private String stId;
    private String stdDid;
}
