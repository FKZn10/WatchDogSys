package com.jpmsoft.jputils.controller;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jputils.feign.UserFeign;
import com.jpmsoft.jputils.utils.CodeUtil;
import com.jpmsoft.jputils.utils.RedisUtils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "验证码", value = "验证码")
@RequestMapping("/v1/authCode")
public class AuthCodeController {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private UserFeign userFeign;

    @GetMapping("/toGetAuthCode")
    public void toGetAuthCode(HttpServletRequest req, HttpServletResponse resp) {
        // 调用工具类生成的验证码和验证码图片
        Map<String, Object> codeMap = CodeUtil.generateCodeAndPic();

        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        redisUtils.set("session_auto_code_" + session.getId(), codeMap.get("code").toString(), getCodeAgeing());
        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Access-Control-Expose-Headers", "Se-Auth-Code");
        resp.setHeader("Se-Auth-Code", session.getId());
        resp.setDateHeader("Expires", -1);

        resp.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos;
        try {
            sos = resp.getOutputStream();
            ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    @GetMapping("/toGetAuthCodeRegister")
    public void toGetAuthCodeRegister(HttpServletRequest req, HttpServletResponse resp) {
        // 调用工具类生成的验证码和验证码图片
        Map<String, Object> codeMap = CodeUtil.generateCodeAndPic();

        // 将四位数字的验证码保存到Session中。
        HttpSession session = req.getSession();
        log.info("session_auto_code_register_{}", session.getId());
        redisUtils.set("session_auto_code_register_" + session.getId(), codeMap.get("code").toString(), getCodeAgeing());
        // 禁止图像缓存。
        resp.setHeader("Pragma", "no-cache");
        resp.setHeader("Cache-Control", "no-cache");
        resp.setHeader("Access-Control-Expose-Headers", "Se-Auth-Code");
        resp.setHeader("Se-Auth-Code", session.getId());
        resp.setDateHeader("Expires", -1);

        resp.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        ServletOutputStream sos;
        try {
            sos = resp.getOutputStream();
            ImageIO.write((RenderedImage) codeMap.get("codePic"), "jpeg", sos);
            sos.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    /**
     * 获取图片验证码时效
     *
     * @return
     */
    private Integer getCodeAgeing() {
        int num = 60;
        try {
            Object ageing = redisUtils.get("Config_graphicCodeAgeing");
            if (ValidateUtil.isNotNull(ageing)) return Integer.parseInt(ageing.toString());
            BaseResponse res = userFeign.GetConfigValueByCode("graphicCodeAgeing");
            if (res != null && res.getSuccess() && res.getStatusCode() == HttpStatus.SC_OK
                    && res.getData() != null && ValidateUtil.isNotEmpty(res.getData().toString()))
                num = Integer.parseInt(res.getData().toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        redisUtils.set("Config_graphicCodeAgeing", num);
        return num;
    }
}
