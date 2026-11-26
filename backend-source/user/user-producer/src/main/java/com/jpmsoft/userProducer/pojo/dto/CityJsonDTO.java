package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/29 10:16
 */
@Data
public class CityJsonDTO {

    private String code;
    private String parentcode;
    private Integer level;
    private String name;
    private String province;
    private String city;
    private String district;
    private String town;
    private String pinyin;
    private String jianpin;
    private String firstchar;
    private Integer tel;
    private Integer zip;
    private BigDecimal lng;
    private BigDecimal lat;
}
