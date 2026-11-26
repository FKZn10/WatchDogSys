package com.jpmsoft.userProducer.rabbitMQ;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.pojo.dto.UserDto;
import com.jpmsoft.userProducer.pojo.entity.SysInterface;
import com.jpmsoft.userProducer.pojo.entity.SysLoggerInfo;
import com.jpmsoft.userProducer.pojo.entity.SysUser;
import com.jpmsoft.userProducer.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 监听队列 interfaceLog
 * created by:aaron
 * date: 2021-06-03 23:50
 */
@Slf4j
@Component
@RabbitListener(queuesToDeclare = {@Queue(value = "interfaceLog")})
public class ReceiverLog {

    @Resource
    private ISysUserService ISysUserService;

    @Resource
    private ISysLoggerInfoService sysLoggerInfoService;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysInterfaceService sysInterfaceService;
    @Value("${system.logSign}")
    private Boolean logSign;

    /**
     * 请求地址转换成接口管理的ID
     *
     * @param url
     * @return
     */
    private SysInterface getInterfaceId(String url) {
        if (ValidateUtil.isEmpty(url)) return null;
        List<SysInterface> ifs = sysInterfaceService.getAll();
        url = xgFiter(url.replaceAll("\\\\", "/"));
        String finalUrl = url;
        List<SysInterface> faces = ifs.stream().filter(f -> ValidateUtil.isNotEmpty(f.getIfUrl()) && finalUrl.contains(f.getIfUrl())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(faces)) return faces.stream().findFirst().get();
        return null;
    }

    private String xgFiter(String url) {
        url = url.replaceAll("//", "/");
        if (url.contains("//")) xgFiter(url);
        return url;
    }

    @RabbitHandler
    public void receiverInterfaceLog(String msg) {
        try {
            Map<String, Object> data = JacksonUtils.parseObject(msg, Map.class);
            SysLoggerInfo model = new SysLoggerInfo();
            model.setlId(GenerateUUID.getUUID());
            model.setlClientip(data.get("userIp").toString());
            model.setlUrl(data.get("url").toString());
            model.setlServerip(data.get("serverIp").toString());
            model.setlResponsetime(Integer.parseInt(data.get("time").toString()));
            model.setlRequesttype(null);
            model.setlRequestmode(data.get("method").toString());
            model.setlRequesttime(new Date(Long.parseLong(data.get("requestTime").toString())));
            model.setlRequestparameter(data.get("requestData").toString());
            model.setlHttpstatus(data.get("status").toString());
            model.setlContent(data.get("L_Content") == null ? null : data.get("L_Content").toString());
            model.setlCreateuId(ValidateUtil.isNull(data.get("userId")) ? null : data.get("userId").toString());
            model.setlActionEnd(data.get("actionEnd") == null ? null : data.get("actionEnd").toString());
            //region 匹配接口管理ID
            SysInterface su = getInterfaceId(model.getlUrl());
            if (ValidateUtil.isNotNull(su)) {
                model.setIfId(su.getId());
                model.setlFunModule(su.getIfPower());
            }
            //endregion
            /**
             * todo:根据接口配置信息 判断当前日志属于那个功能模块
             */
            //model.setlFunModule("");
            if (data.get("userId") != null) {
                SysUser user = ISysUserService.getById(data.get("userId").toString());
                if (user != null)
                    model.setlCreateuName(user.getuName());
            }
            if (!ValidateUtil.isNull(data.get("otherRequest"))) {//需要额外处理的请求
                String other = data.get("otherRequest").toString();
                if (other.equals("userLogin")) {
                    //用户登录
                    UserDto request = JacksonUtils.parseObject(data.get("requestData").toString(), UserDto.class);
                    SysUser user = ISysUserService.getUserByAccountAndPassword(request);
                    if (user != null) {
                        model.setlCreateuId(user.getuId());
                        model.setlCreateuName(user.getuName());
                        model.setlRequestparameter("{}");
                    }
                } else if (other.equals("downloadFile")) {
                    String userId = model.getlUrl().substring(model.getlUrl().lastIndexOf("/") + 1);
                    SysUser user = ISysUserService.getById(userId);
                    if (user != null) {
                        model.setlCreateuId(userId);
                        model.setlCreateuName(user.getuName());
                        model.setlContent("{}");
                    } else {
                        return;
                    }
                }
            }
            if (ValidateUtil.isNotNull(logSign) && logSign) {
                InterfaceEncDecDTO ied = sysConfigEntityService.getInterfaceEncDec();
                if (ValidateUtil.isNotNull(ied) && ValidateUtil.isNotEmpty(ied.getLogSignUrl())) {
                    BaseResponse res = apiUtilsService.sendPost(ied.getLogSignUrl(), model, null);
                    if (ValidateUtil.isNotNull(res) && ValidateUtil.isNotNull(res.getData())) {
                        model.setSign(res.getData().toString());
                        sysLoggerInfoService.save(model);
                    }
                } else sysLoggerInfoService.save(model);
            } else
                sysLoggerInfoService.save(model);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error(ex.getMessage(), ex);
        }
    }
}
