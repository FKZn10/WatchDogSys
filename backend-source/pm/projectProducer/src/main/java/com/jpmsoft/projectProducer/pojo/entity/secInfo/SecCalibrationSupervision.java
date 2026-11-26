package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

/**
 * 入围定标委员会及监督委员会构成
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:33
 */
@Data
@TableName("Sec_Calibration_Supervision")
public class SecCalibrationSupervision {

    @TableId(type = IdType.INPUT)
    private String scsId;
    private String secId;
    private String scsTenderee;
    private String scsBiddingType;
    private String scsScope;
    private String scsRemark;
    private String createUname;
    private String createUid;
    private Date createTime;
    private Integer state;
    private Date stateTime;
}
