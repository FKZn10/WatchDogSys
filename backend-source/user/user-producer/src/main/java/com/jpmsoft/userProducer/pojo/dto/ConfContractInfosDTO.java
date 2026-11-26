package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/17 9:51
 */
@Data
public class ConfContractInfosDTO implements Serializable {

    private List<String> conType;
    private List<String> positionName;
    private String message;

}
