package com.jpmsoft.userProducer.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 地区
 *
 * @Author: 李文涛 aaron
 * @date: 2024/3/27 9:39
 */
@Data
@TableName("Sys_Region")
public class SysRegion {

    @TableId(type = IdType.INPUT)
    private String id;
    private String srCode;
    private String srParentCode;
    private Integer srLevel;
    private String srName;
    private String srProvince;
    private String srCity;
    private String srDistrict;
    private String srTown;
    private String srQp;
    private String srJp;
    private String srFirstChar;
    private BigDecimal srLat;
    private BigDecimal srLng;
    private Integer srTel;
    private Integer srZip;
    private Integer srStatus;
}
