package com.jpmsoft.fileUpload.service;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/12 10:47
 */
public interface IEasyExcelService {

    /**
     * 导出基础配置
     *
     * @param response
     */
    void exportConf(HttpServletResponse response);

    /**
     * 导入基础配置
     *
     * @param file
     * @return
     */
    BaseResponse importSysConf(MultipartFile file, LogUserInfoVo user);

    /**
     * 工程季报导入
     *
     * @param file
     * @return
     */
    BaseResponse importOaQuarterlyReport(MultipartFile file, Integer year, Integer quarter);

    /**
     * 导出文档模板
     *
     * @param response
     */
    void exportSysFileTemp(HttpServletResponse response);

    /**
     * 导入文档模板
     *
     * @param file
     * @return
     */
    BaseResponse importSysFileTemp(MultipartFile file, LogUserInfoVo user);
}
