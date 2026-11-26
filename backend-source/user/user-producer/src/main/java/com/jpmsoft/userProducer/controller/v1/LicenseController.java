package com.jpmsoft.userProducer.controller.v1;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.JpmLicenseDTO;
import com.jpmsoft.common.utils.JpmCheckUtil;
import com.jpmsoft.common.utils.ProMachineCodeUtil;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.Xml2ObjectUtil;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;

@Slf4j
@RestController
@ApiIgnore
@Api(tags = "license", value = "license")
@RequestMapping("/v1/license")
public class LicenseController {

    /**
     * 实体类直接下载为XML文件
     *
     * @param response 响应对象，用于设置下载头和获取输出流
     * @throws IOException 流操作/XML转换异常
     */
    @PostMapping("/downloadXml")
    public void downloadXml(HttpServletResponse response, @RequestBody JpmLicenseDTO license) throws IOException, NoSuchAlgorithmException {
        if (license == null) throw new JPMException("缺少必填参数！");
        if (ValidateUtil.isAnObjEmpty(license.getCompany(), license.getCode())) throw new JPMException("缺少必填参数！");
        if (ValidateUtil.isNull(license.getExpiredDate())) license.setExpiredDate("0");//0标识不限制使用期限
        license.setCode(JpmCheckUtil.getCode(license));
        response.setContentType("application/xml;charset=UTF-8"); // 设置文件类型为XML
        response.setHeader("Content-Disposition",
                "attachment;filename=" + URLEncoder.encode("jpm-license.xml", "UTF-8")); // 下载文件名，解决中文乱码
        response.setHeader("Pragma", "no-cache"); // 禁止缓存
        response.setHeader("Cache-Control", "no-cache"); // 禁止缓存
        // 3. 核心操作：将实体类写入响应输出流，浏览器直接下载
        Xml2ObjectUtil.object2XmlStream(license, response.getOutputStream());
        // 4. 刷新并关闭流，确保数据全部输出
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @PostMapping("/checkTest")
    public BaseResponse checkTest(@RequestParam String path) {
        JpmCheckUtil.check(path);
        return BaseResponse.ok();
    }

    @PostMapping("/getCode")
    public BaseResponse getCode() throws Exception {
        return BaseResponse.ok(ProMachineCodeUtil.getProUniqueMachineCode());
    }
}
