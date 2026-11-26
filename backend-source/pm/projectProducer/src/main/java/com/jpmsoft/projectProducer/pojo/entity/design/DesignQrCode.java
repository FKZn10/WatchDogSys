package com.jpmsoft.projectProducer.pojo.entity.design;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("design_qr_code")
public class DesignQrCode {

    @TableId
    private String id;

    private Date createTime;

    private String createUid;

    private String createUname;

    private Integer State;

    private Date StateTime;

    private String pId;

    private Date dqcApplyTime;

    private String dqcUsed;

    private String dqcStatus;

    private String dqCode;

    private String dqPurpose;
}
