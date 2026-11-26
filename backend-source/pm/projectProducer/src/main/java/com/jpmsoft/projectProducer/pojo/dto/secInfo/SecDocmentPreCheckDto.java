package com.jpmsoft.projectProducer.pojo.dto.secInfo;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 对应基础配置cjSecDocmentPreCheck
 *
 * @author: 李文涛 aaron
 * @date: 2022/7/29 10:43
 */
@Data
public class SecDocmentPreCheckDto implements Serializable {

    private static final long serialVersionUID = 7645540173560242943L;
    private List<String> secType;
    private List<String> cpdCode;
}
