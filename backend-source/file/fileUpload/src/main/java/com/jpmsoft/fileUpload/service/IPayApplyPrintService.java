package com.jpmsoft.fileUpload.service;

import javax.servlet.http.HttpServletResponse;

/**
 * 付款申请书打印
 * <p>
 * 根据支付ID(payId)渲染付款申请书(20万以上/20万以下)模板并下载.
 * 数据来自 projectProducer 现成接口(getConPayPrintByPayId), 模板来自 SysConfig+SysFileAnnex.
 * 本服务全部为新增代码, 不修改任何原有逻辑.
 *
 * @author 付款打印
 */
public interface IPayApplyPrintService {

    /**
     * 渲染付款申请书并下载
     *
     * @param response 响应
     * @param payId    支付ID
     */
    void printPayApply(HttpServletResponse response, String payId);

    /**
     * 渲染付款申请书并以 PDF 形式内联预览(供微信扫码查看, 无token)
     *
     * @param response 响应
     * @param payId    支付ID
     */
    void previewPayApply(HttpServletResponse response, String payId);
}
