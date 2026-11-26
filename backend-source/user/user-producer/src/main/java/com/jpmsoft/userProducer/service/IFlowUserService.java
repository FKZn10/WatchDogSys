package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.dto.FlowUserDto;
import com.jpmsoft.userProducer.pojo.entity.FlowUser;

import java.util.List;


public interface IFlowUserService extends IService<FlowUser> {
    /**
     * 新增流程步骤信息
     *
     * @param request
     * @return
     */
    void CreateFlowUser(FlowUserDto request);


    /**
     * 删除流程步骤信息
     *
     * @param flowId
     * @return
     */
    void DeleteFlowUser(String flowId);


    List<String> getRoleUserFlowByUId(String rId);
}
