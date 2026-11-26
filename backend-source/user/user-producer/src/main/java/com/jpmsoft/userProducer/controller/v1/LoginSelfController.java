package com.jpmsoft.userProducer.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.pojo.dto.SelfLoginExchangeDTO;
import com.jpmsoft.userProducer.pojo.entity.PreProject;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.pojo.vo.LoginSelfVo;
import com.jpmsoft.userProducer.service.IApiUtilsService;
import com.jpmsoft.userProducer.service.IPreProjectService;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.service.ISysUserService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 自平台单点登录
 * 不通项目之间单点登录
 */
@ApiIgnore
@Slf4j
@RestController
@RequestMapping("/v1/loginSelf")
public class LoginSelfController {

    @Resource
    private ISysConfigEntityService sysConfigEntityService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private RedisUtils redisUtils;
    @Resource(name = "restTemplateApi")
    private RestTemplate restTemplateApi;
    /**
     * 混合码
     */
    private final static String mdSalt = "123456";


    @ApiIgnore
    @GetMapping("/selfLogin/getAnythingLogin")
    private BaseResponse getAnythingLogin(@RequestParam String pId, @RequestParam String account) throws UnsupportedEncodingException {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("u_account", URLDecoder.decode(account, "UTF-8"));
        wrapper.eq("u_State", 1);
        List<SysUser> users = sysUserService.list(wrapper);
        if (CollectionUtils.isEmpty(users)) throw new JPMException("用户校验不通过,跳转失败！");
        String otherToken = GenerateUUID.getUUID();
        Map<String, String> redisData = new HashMap<>();
        redisData.put("uId", users.stream().findFirst().get().getuId());
        //设置300秒过期
        redisUtils.set("OtherAuth_" + otherToken, JacksonUtils.toJSONString(redisData), 300);
        Map<String, String> data = new HashMap<>();
        data.put("otherToken", otherToken);
        data.put("pId", pId);
        PreProject project = preProjectService.getById(pId);
        data.put("projectName", project.getPName());
        return BaseResponse.ok(data);
    }

    /**
     * 跳转通平台系统
     * 例如 城建跳转义乌 跳转苏项
     *
     * @param code
     * @return
     */
    @ApiIgnore
    @GetMapping("/getLoginInfo")
    public BaseResponse getLoginInfo(@RequestParam String code, @RequestHeader String token) throws Exception {
        String confStr = sysConfigEntityService.getSysConfigByCode(code);
        if (ValidateUtil.isEmpty(confStr)) return BaseResponse.fail(String.format(BaseMsg.configIsHiatus, code));
        Map<String, String> conf = JacksonUtils.parseObject(confStr, Map.class);
        LoginSelfVo ls = JacksonUtils.parseObject(confStr, LoginSelfVo.class);
        if (ls == null || conf == null) return BaseResponse.fail(String.format(BaseMsg.configIsError, code));
        SysUser user = sysUserService.getById(redisUtils.getUid(token));
        String sourceValue = BeanUtils.getProperty(user, ls.getCurrentKey());
        log.info("soucreValue:" + sourceValue + " encode:" + URLEncoder.encode(sourceValue, "UTF-8"));
        try {
            SelfLoginExchangeDTO request = new SelfLoginExchangeDTO();
            request.setSourceValue(apiUtilsService.Sm2EncryptionFront(sourceValue));
            request.setCheckKey(ls.getThirdKey());
            request.setPlatform(ls.getPlatform());
            request.setChannelType(ls.getChannelType());
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = ls.getTargetApiUrl().endsWith("/") ? ls.getTargetApiUrl() + "api/v1/loginSelf/selfLogin" : ls.getTargetApiUrl() + "/api/v1/loginSelf/selfLogin";
            BaseResponse baseResponse = restTemplateApi.postForObject(url, formEntity, BaseResponse.class);
            if (baseResponse != null && baseResponse.isSuccess() && baseResponse.getStatusCode() == HttpStatus.OK.value()) {
                conf.put("onceToken", baseResponse.getData() + "");
                // 移除后台使用的配置项
                Field[] fields = ls.getClass().getDeclaredFields();
                for (Field field : fields)
                    conf.remove(field.getName());
                return BaseResponse.ok(conf);
            } else return baseResponse;
        } catch (Exception ex) {
            ex.printStackTrace();
            return BaseResponse.fail("认证异常！");
        }
    }

    /**
     * 自己平台传入源校验字段值，和本系统需要核对的字段名 校验通过返回一次性令牌
     *
     * @return
     */
    @ApiIgnore
    @PostMapping("/selfLogin")
    public BaseResponse selfLogin(@RequestBody SelfLoginExchangeDTO request) {
        log.info("selfLogin:" + request);
        String sourceValue = apiUtilsService.SelfSm2Decrypt(request.getSourceValue());
        if (ValidateUtil.isEmpty(sourceValue)) throw new JPMException("解密失败！校验不通过！");
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq(request.getCheckKey(), sourceValue);
        wrapper.eq("u_State", 1);
        List<SysUser> users = sysUserService.list(wrapper);
        if (CollectionUtils.isEmpty(users)) throw new JPMException("用户校验不通过,跳转失败！");
        String otherToken = GenerateUUID.getUUID();
        Map<String, String> redisData = new HashMap<>();
        redisData.put("uId", users.stream().findFirst().get().getuId());
        if (ValidateUtil.isNotEmpty(request.getChannelType()))
            redisData.put("channelType", request.getChannelType());
        if (ValidateUtil.isNotEmpty(request.getPlatform()))
            redisData.put("platform", request.getPlatform());
        //设置300秒过期
        redisUtils.set("OtherAuth_" + otherToken, JacksonUtils.toJSONString(redisData), 300);
        return BaseResponse.ok(otherToken);
    }


    /**
     * 组装POST请求参数
     *
     * @param object
     * @return
     */
    private HttpEntity<String> assemblyRequestParam(Object object) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }

}
