package com.jpmsoft.projectProducer.pojo.entity.other;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 系统外部用户注册 审批时分配岗位
 */
@Data
@TableName("OUTER_USER_POSITION")
public class OuterUserPosition {

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    private String ourId;
    private String pId;
    private String outerDid;
    private String outerPid;
    private String innerDid;
    private String innerPid;
    private Integer oupSort;
}
