package com.jpmsoft.fileUpload.service;

import com.jpmsoft.fileUpload.pojo.dto.EnterpriseDeclarationPrintDTO;

import javax.servlet.http.HttpServletResponse;

/**
 * 企业申报表打印
 * <p>
 * 前端 POST 整条企业申报数据对象，后端渲染 Word 模板并转 PDF 下载。
 * 全部为新增代码，不改动任何原有逻辑。
 *
 * @author 企业申报表打印
 */
public interface IEnterpriseDeclarationPrintService {

    /**
     * 渲染企业申报表并以 PDF 形式下载。
     *
     * @param response 响应
     * @param data     企业申报数据（前端传入的完整对象）
     */
    void printEnterpriseDeclaration(HttpServletResponse response, EnterpriseDeclarationPrintDTO data);
}
