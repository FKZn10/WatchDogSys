package com.jpmsoft.userProducer.pojo.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户考勤组
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/15 9:01
 */
@Data
public class UserAttendanceGroupVo implements Serializable {

    private static final long serialVersionUID = -4429771304739782964L;

    private String agId;
    private String agName;

}
