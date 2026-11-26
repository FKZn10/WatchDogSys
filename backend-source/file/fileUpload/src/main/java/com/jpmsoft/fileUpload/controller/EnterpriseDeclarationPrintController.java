package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.fileUpload.pojo.dto.EnterpriseDeclarationPrintDTO;
import com.jpmsoft.fileUpload.service.IEnterpriseDeclarationPrintService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 企业申报表打印
 * <p>
 * 前端把整条企业申报数据作为对象 POST 过来，后端渲染申报表模板并转 PDF 下载。
 * 全部为新增代码，不修改任何原有逻辑。
 *
 * @author 企业申报表打印
 */
@Slf4j
@RestController
@Api(tags = "企业申报表打印", value = "企业申报表打印")
@RequestMapping("/jpm")
public class EnterpriseDeclarationPrintController {

    @Resource
    private IEnterpriseDeclarationPrintService enterpriseDeclarationPrintService;

    @ApiOperation(value = "打印企业申报表(PDF)")
    @PostMapping("/enterpriseDeclarationPrint")
    public void enterpriseDeclarationPrint(HttpServletResponse response,
                                          @RequestBody EnterpriseDeclarationPrintDTO data) {
        log.info("打印企业申报表, companyName={}, declareYear={}",
                data == null ? null : data.getCompanyName(), data == null ? null : data.getDeclareYear());
        enterpriseDeclarationPrintService.printEnterpriseDeclaration(response, data);
    }
}
