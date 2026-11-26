package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.other.SysMessageMapper;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.CjSafetyJdyMessageDTO;
import com.jpmsoft.projectProducer.pojo.entity.other.SysMessage;
import com.jpmsoft.projectProducer.pojo.entity.other.SysMessageReceive;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsPlanTask;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysMessageReceiveService;
import com.jpmsoft.projectProducer.service.other.ISysMessageService;
import com.jpmsoft.projectProducer.service.project.IPreProjectService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class SysMessageImpl extends ServiceImpl<SysMessageMapper, SysMessage> implements ISysMessageService {

    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISysMessageReceiveService sysMessageReceiveService;
    @Resource
    private IPreProjectService preProjectService;

    @Override
    public void createMessage(List<QsPlanTask> tasks) {
        CjSafetyJdyMessageDTO conf = sysConfigService.getCjSafetyJdyMessage();
        if (conf.getQualityLqSend() == false && conf.getQualityYqSen() == false &&
                conf.getSafetyLqSend() == false && conf.getSafetyYqSend() == false) return;
        List<SysMessage> messages = new ArrayList<>();
        List<SysMessageReceive> receives = new ArrayList<>();
        Date now = new Date();
        for (QsPlanTask task : tasks) {
            if (!task.getOutTimeStatus().equals("2") && !task.getOutTimeStatus().equals("3")) continue;//不是逾期也不是临期
            SysMessage message = new SysMessage();
            message.setId(GenerateUUID.getUUID());
            message.setCreateTime(now);
            message.setCreateUid(conf.getJdyUid());
            message.setSmType("01");//平台消息
            message.setSmtCode("系统生成");
            SysMessageReceive receive = new SysMessageReceive();
            receive.setId(GenerateUUID.getUUID());
            receive.setSmId(message.getId());
            receive.setSmrUser(conf.getJdyUid());
            receive.setSmrSendTime(now);
            receive.setSmrRead(Boolean.FALSE);
            receive.setSmrSendStatus("0");
            if (task.getPtCategory().equals("01"))//质量检查
            {
                if (task.getOutTimeStatus().equals("2") && conf.getQualityLqSend()) {
                    message.setSmTitle(conf.getQualityLqTitle());
                    message.setSmContent(replaceContent(conf.getQualityLqMsg(), task));
                    messages.add(message);
                    receives.add(receive);
                }
                if (task.getOutTimeStatus().equals("3") && conf.getQualityYqSen()) {
                    message.setSmTitle(conf.getQualityYqTitle());
                    message.setSmContent(replaceContent(conf.getQualityYqMsg(), task));
                    messages.add(message);
                    receives.add(receive);
                }
            } else if (task.getPtCategory().equals("02"))//安全检查
            {
                if (task.getOutTimeStatus().equals("2") && conf.getSafetyLqSend()) {
                    message.setSmTitle(conf.getSafetyLqTitle());
                    message.setSmContent(replaceContent(conf.getSafetyLqMsg(), task));
                    messages.add(message);
                    receives.add(receive);
                }
                if (task.getOutTimeStatus().equals("3") && conf.getSafetyYqSend()) {
                    message.setSmTitle(conf.getSafetyYqTitle());
                    message.setSmContent(replaceContent(conf.getSafetyYqMsg(), task));
                    messages.add(message);
                    receives.add(receive);
                }
            }
        }
        if (!CollectionUtils.isEmpty(messages)) {
            saveBatch(messages, 100);
            sysMessageReceiveService.saveBatch(receives, 100);
            log.info("城建质量安全监督员平台消息生成成功！");
        }
    }

    private String replaceContent(String msg, QsPlanTask task) {
        String chekTime = DateUtil.Date2String(task.getPtEvaluationTime(), "yyyy-MM-dd");
        if (ValidateUtil.isNotEmpty(task.getPId()) && msg.contains("[pName]")) {
            PreProject pro = preProjectService.getById(task.getPId());
            if (pro != null) {
                msg = msg.replaceAll("[pName]", pro.getpName());
            }
        }
        msg = msg.replaceAll("[ptEvaluationTime]", chekTime);
        return msg;
    }
}
