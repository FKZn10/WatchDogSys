package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.entity.SysJiMuReport;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/10 16:47
 */
public interface ISysJiMuReportService extends IService<SysJiMuReport> {


    void generateJimuReportPdf();
}
