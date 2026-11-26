package com.jpmsoft.projectProducer.pojo.entity.oa;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * <p>
 * 消假表
 * </p>
 *
 * @author 刘凯锋
 * @since 2022-09-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("OA_Attendance_Leave_eliminate")
public class OaAttendanceLeaveEliminate implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "object_id", type = IdType.INPUT)
    private String objectId;


    /**
     * 请假申请表id
     */
    @TableField("Al_Id")
    private String alId;


    /**
     * 审批状态
     */
    @TableField("Approve_Status")
    private String approveStatus;


}
