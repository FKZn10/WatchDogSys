package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.userProducer.mapper.FlowUserMapper;
import com.jpmsoft.userProducer.pojo.dto.FlowUserDto;
import com.jpmsoft.userProducer.pojo.entity.FlowUser;
import com.jpmsoft.userProducer.service.IFlowUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class FlowUserImpl extends ServiceImpl<FlowUserMapper, FlowUser> implements IFlowUserService {
    /**
     * 新增流程步骤信息
     *
     * @param request
     * @return
     */
    @Override
    public void CreateFlowUser(FlowUserDto request) {
        FlowUser FlowUser = new FlowUser();
        FlowUser.setFlowuserid(request.getFlowuserid());
        FlowUser.setFlowuseract(request.getFlowuseract());
        FlowUser.setFlowuserflow(request.getFlowuserflow());
        FlowUser.setFlowuserrole(request.getFlowuserrole());
        baseMapper.insert(FlowUser);
    }


    /**
     * 删除流程步骤信息
     *
     * @param flowId
     * @return
     */
    @Override
    public void DeleteFlowUser(String flowId) {
        QueryWrapper<FlowUser> FlowActivityWrapper = new QueryWrapper<>();
        FlowActivityWrapper.eq("FlowUserFlow", flowId);
        baseMapper.delete(FlowActivityWrapper);
    }

    @Override
    public List<String> getRoleUserFlowByUId(String rId){
       return  baseMapper.getFlowByFlowUserRole(rId);
    }



}
