package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.entity.SysFileTemplatePrint;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/12 9:32
 */
public interface ISysFileTemplatePrintService extends IService<SysFileTemplatePrint> {

    List<SysFileTemplatePrint> getJiMuPrint(List<String> tIds);
}
