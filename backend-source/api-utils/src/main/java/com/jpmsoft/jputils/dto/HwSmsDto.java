package com.jpmsoft.jputils.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 华为云短信
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/12 10:58
 */
@Data
public class HwSmsDto extends AliyunSmsDto implements Serializable {
    private String url;
}
