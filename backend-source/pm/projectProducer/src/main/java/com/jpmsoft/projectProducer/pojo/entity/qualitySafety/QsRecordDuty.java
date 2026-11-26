package com.jpmsoft.projectProducer.pojo.entity.qualitySafety;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 质量安全检查记录责任人
 *
 * @Author: 李文涛 aaron
 * @date: 2023/9/20 13:38
 */
@Data
@TableName("Qs_Record_Duty")
public class QsRecordDuty {

    @TableId(type = IdType.INPUT)
    private String id;
    //质量安全检查记录表id
    private String qsrId;
    //责任单位ID
    private String qrdCpyId;
    //责任人ID
    private String qrdUserId;
    //部门id
    private String qrdDepId;
    //部门名称
    private String qrdDepName;
    //排序号
    private String qrdSort;
    //责任人名称（手工录入） 关联查询返回qrdUserName
    private String qrdUName;
}
