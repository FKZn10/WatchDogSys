package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.FlowUser;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FlowUserService extends IService<FlowUser> {

    List<FlowUser>  getFlowUserInFlowUserActS(List<String> flowUserActs,String customerId);
}
