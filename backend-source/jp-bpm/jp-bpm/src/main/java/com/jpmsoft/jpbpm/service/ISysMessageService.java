package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.File;
import com.jpmsoft.jpbpm.pojo.entity.SysMessage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISysMessageService extends IService<SysMessage> {

    void generateMassage(String massageType, List<String> userList, String fileId, String actId, String taskNmae, String resultstr, String opinion, String userId, String customerId, String token, String pId,String tCode,String year);
    void generateMassage(File fileobj, String taskNmae, String actId, String userId, String uName, String phoneNumber, String smsCont, String actName, String resultstr, String opinion, String customerId);
}
