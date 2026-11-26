package com.jpmsoft.jputils.dto;

import lombok.Data;

import java.util.List;

/**
 * 华为IOT云通信 短信请求body
 *
 * @author: 李文涛 aaron
 * @date: 2022/10/19 10:22
 */
@Data
public class HwIotSmsBody {

    private String account;
    private String password;
    private List<MtSmsMessage> requestLists;
}
