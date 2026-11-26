package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.File;
import com.jpmsoft.jpbpm.pojo.entity.SysShortMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysShortMessageService extends IService<SysShortMessage> {

    void generateSMS(String smsType, List<String> userList, String fileId,String  actId,String taskNmae,String resultstr,String opinion,String userId,String customerId,String token,String pId,String tCode,String  year);
    void generateSMS(File fileobj, String taskNmae, String actId, String userId, String uName, String phoneNumber, String smsCont, String actName, String resultstr, String opinion,String customerId );
}

