package com.jpmsoft.projectProducer.pojo.entity.peInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 上会呈批清单
 * Created By:李文涛 aaron
 * Created Time:2022/6/16 10:08
 */
@Data
@TableName("Pe_Meeting_Approval_List")
public class PeMeetingApprovalList {

    @TableId(type = IdType.INPUT)
    private String malId;
    private String peId;
    private String maId;
}
