package com.jpmsoft.jputils.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 苏相短信发送响应体
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/6 11:31
 */
@Data
public class SxResVo implements Serializable {

    private static final long serialVersionUID = 8136026258940581013L;

    private String status;
    private String msg;
    private String content;
}
