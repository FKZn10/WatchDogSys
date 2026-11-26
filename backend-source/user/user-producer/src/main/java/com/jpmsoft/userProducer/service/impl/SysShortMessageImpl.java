package com.jpmsoft.userProducer.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.requestAndResponse.Sms.SysShortMessageModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysShortMessageMapper;
import com.jpmsoft.userProducer.pojo.dto.sms.GmSmsWebServiceDto;
import com.jpmsoft.userProducer.pojo.entity.SysShortMessage;
import com.jpmsoft.userProducer.pojo.entity.SysShortMessageLog;
import com.jpmsoft.userProducer.service.IApiUtilsService;
import com.jpmsoft.userProducer.service.ISysConfigEntityService;
import com.jpmsoft.userProducer.service.ISysShortMessageLogService;
import com.jpmsoft.userProducer.service.ISysShortMessageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SysShortMessageImpl extends ServiceImpl<SysShortMessageMapper, SysShortMessage> implements ISysShortMessageService {

    @Resource
    private ISysShortMessageLogService sysShortMessageLogService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysConfigEntityService sysConfigEntityService;

    @Override
    public BaseResponse getSysShortMessageList(SysShortMessageModel request) {
        QueryMap<SysShortMessageModel> queryMap = new QueryMap<>(request);
        queryMap.put("smUname").put("smMobilePhone");
        List<SysShortMessageModel> datas = baseMapper.getSysShortMessageList(queryMap.getQuery());
        Integer total = baseMapper.getSysShortMessageListCount(queryMap.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse createOrUpdateShortMessage(SysShortMessageModel request) {
        SysShortMessage entity = new SysShortMessage();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getSmId())) {
            entity.setSmCreateTime(new Date());
            entity.setSmId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        } else {
            SysShortMessage model = baseMapper.selectById(entity.getSmId());
            if (model == null)
                return BaseResponse.fail(BaseMsg.infoNotExist);
            entity.setSmCreateTime(model.getSmCreateTime());
            entity.setSmCreateUname(model.getSmCreateUname());
            entity.setSmCreateUid(model.getSmCreateUid());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getSmId());
    }

    @Override
    public BaseResponse gmSendSmsByMessage() {
        QueryWrapper<SysShortMessage> wrapper = new QueryWrapper<>();
        wrapper.isNotNull("Sm_Mobile_Phone");
        wrapper.last("limit 20");
        List<SysShortMessage> datas = baseMapper.selectList(wrapper);
        if (datas == null) {
            log.info("GM短信发送，暂无需要发送的短信！");
            return new BaseResponse("没有短信需要发送！");
        }
//        Client client = jaxWsClientConfig.client();
//        if (client == null)
//            return new BaseResponse("无法链接WebService服务器！");
        List<String> del = new ArrayList<>();
        List<SysShortMessageLog> entitys = new ArrayList<>();
        InterfaceEncDecDTO encDec = sysConfigEntityService.getInterfaceEncDec();
        for (SysShortMessage data : datas) {
            GmSmsWebServiceDto model = new GmSmsWebServiceDto();
            model.setContent(data.getSmContent());
            model.setMobile(data.getSmMobilePhone());
            model.setNeedreport("0");
            BaseResponse res = apiUtilsService.sendGmSms(model);
            String result = null;
            if (res.getSuccess() && res.getStatusCode() == HttpStatus.SC_OK)
                result = res.getData().toString();
            SysShortMessageLog smsLog = new SysShortMessageLog();
            smsLog.setSmlId(GenerateUUID.getUUID());
            smsLog.setSmId(data.getSmId());
            smsLog.setSmlActid(data.getSmId());
            smsLog.setSmlContent(data.getSmContent());
            smsLog.setSmlCreateMethod(data.getSmCreateMethod());
            smsLog.setSmlCreateTime(new Date());
            smsLog.setSmlCreateUid(data.getSmCreateUid());
            smsLog.setSmlCreateUname(data.getSmCreateUname());
            smsLog.setSmlFileid(data.getSmFileid());
            smsLog.setSmlFlowid(data.getSmFlowid());
            smsLog.setSmlFrom(data.getSmFrom());
            smsLog.setSmlMobilePhone(data.getSmMobilePhone());
            smsLog.setSmlSendResultMsg(result);
            smsLog.setSmlSendTime(new Date());
            smsLog.setSmlUid(data.getSmUid());
            smsLog.setSmlUname(data.getSmUname());
            smsLog.setSmId(data.getSmId());
            smsLog.setSmlSendResult("0");
            if (!ValidateUtil.isEmpty(result)) {
                log.info("短信发送响应结果：" + result);
                if (result.contains("<code>0</code>")) {
                    del.add(data.getSmId());
                    smsLog.setSmlSendResult("ok");
                }
            }
            smsLog.setSign(apiUtilsService.sign(encDec,encDec.getSmsSign(),log));
            entitys.add(smsLog);
        }
        if (del.size() > 0)
            removeByIds(del);
        if (entitys.size() > 0)
            sysShortMessageLogService.saveBatch(entitys);
//        client.destroy();
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse batchCreateShortMessage(List<SysShortMessageModel> sysShortMessageModelList) {
        List<SysShortMessage> sysShortMessageList=new ArrayList<>();
        if(CollUtil.isNotEmpty(sysShortMessageModelList)){
            sysShortMessageList= BeanUtil.copyToList(sysShortMessageModelList,SysShortMessage.class);
        }

        if(CollUtil.isNotEmpty(sysShortMessageList))
              saveBatch(sysShortMessageList);

        return BaseResponse.ok();
    }
}
