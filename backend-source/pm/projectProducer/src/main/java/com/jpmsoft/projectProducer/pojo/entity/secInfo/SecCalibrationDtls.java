package com.jpmsoft.projectProducer.pojo.entity.secInfo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * 入围定标委员
 *
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:31
 */
@Data
@TableName("Sec_Calibration_Dtls")
public class SecCalibrationDtls {

    @TableId(type = IdType.INPUT)
    private String scdId;
    private String scsId;
    private String scdUname;
    private String scdPhone;
    private String scdCompany;
    private String scdRemark;
    private Integer scdSort;
}
