package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.fileUpload.service.IPayApplyPrintService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 付款申请书打印
 * <p>
 * 合同支付详情页点"打印"后, 按支付ID渲染付款申请书(20万以上/以下)并下载 .docx.
 * 全部为新增代码, 不修改任何原有逻辑.
 *
 * @author 付款打印
 */
@Slf4j
@RestController
@Api(tags = "付款申请书打印", value = "付款申请书打印")
@RequestMapping("/jpm")
public class PayApplyPrintController {

    @Resource
    private IPayApplyPrintService payApplyPrintService;

    @ApiOperation(value = "打印付款申请书")
    @GetMapping("/payApplyPrint")
    public void payApplyPrint(HttpServletResponse response, @RequestParam String payId) {
        log.info("打印付款申请书, payId={}", payId);
        payApplyPrintService.printPayApply(response, payId);
    }

    @ApiOperation(value = "付款申请书预览(免token, 供打印件二维码扫码查看)")
    @GetMapping("/payApplyPreview/{payId}")
    public void payApplyPreview(HttpServletResponse response, @PathVariable("payId") String payId) {
        log.info("预览付款申请书(扫码), payId={}", payId);
        payApplyPrintService.previewPayApply(response, payId);
    }
}