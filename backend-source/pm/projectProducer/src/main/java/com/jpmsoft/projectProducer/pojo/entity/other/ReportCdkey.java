package com.jpmsoft.projectProducer.pojo.entity.other;

import lombok.Data;

/**
 * 问卷报表生成二维码信息
 *
 * @Author: 李文涛 aaron
 * @date: 2025/1/2 10:49
 */
@Data
public class ReportCdkey {

    private String id;
    private String rcKey;//二维码标识
    private String reportId;//问卷报表
}
