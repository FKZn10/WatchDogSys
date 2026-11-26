package com.jpmsoft.userProducer.pojo.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * AI问答集成基础配置信息
 * deepseek/aliyun/tencent等等
 *
 * @Author: 李文涛 aaron
 * @date: 2025/2/17 13:58
 */
@Data
public class AIDockConfigInfoDTO implements Serializable {
    /**
     * api地址
     */
    private String url = "https://api.deepseek.com/v1";
    /**
     * 使用模型名称
     */
    private String model = "deepseek-r1";
    /**
     * api-key
     */
    private String apiKey = "";
}
