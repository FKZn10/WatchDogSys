package com.jpmsoft.jputils.dto;

import lombok.Data;

import java.util.List;

/**
 * 阿里云批量短信发送模板
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/11 9:44
 */
@Data
public class AliyunBathSmsDto {

    /**
     * 接收短信的手机号码，
     * JSON 数组格式，eg: ["1590***0000","13500***000"]
     */
    private List<String> phoneNumbers;
    /**
     * 短信签名名称，
     * JSON 数组格式，eg: ["阿里云","阿里巴巴"]
     */
    private List<String> signNames;
    /**
     * 短信模板CODE
     */
    private String templateCode;
    /**
     * 短信模板变量对应的实际值，
     * JSON 数组格式，eg：[{"code":"1234"},{"code":"2345"}]
     */
    private List<Object> templateParams;
}
