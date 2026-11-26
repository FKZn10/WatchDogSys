package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.userProducer.mapper.FlowActivityMapper;
import com.jpmsoft.userProducer.pojo.dto.FlowActivityDto;
import com.jpmsoft.userProducer.pojo.entity.FlowActivity;
import com.jpmsoft.userProducer.service.IFlowActivityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FlowActivityImpl extends ServiceImpl<FlowActivityMapper, FlowActivity> implements IFlowActivityService {

    /**
     * 新增流程步骤信息
     *
     * @param request
     * @return
     */
    @Override
    public void CreateFlowActivity(FlowActivityDto request) {
        FlowActivity flowActivityobj = new FlowActivity();
        flowActivityobj.setActid(request.getActid());
        flowActivityobj.setActname(request.getActname());
        flowActivityobj.setActcode(request.getActcode());
        flowActivityobj.setActtype(request.getActtype());
        flowActivityobj.setActorder(request.getActorder());
        flowActivityobj.setActbacktype(request.getActbacktype());
        flowActivityobj.setActflowid(request.getActflowid());
        flowActivityobj.setActusertype(request.getActusertype());
        flowActivityobj.setActcountersigntitle(request.getActcountersigntitle());
        flowActivityobj.setActremark(request.getActremark());
        flowActivityobj.setActhour(request.getActhour());
        flowActivityobj.setActcoderole(request.getActcoderole());
        flowActivityobj.setActallowconcurrency(request.getActallowconcurrency());
        flowActivityobj.setActconcurrentmerger(request.getActconcurrentmerger());
        flowActivityobj.setActusershowtype(request.getActusershowtype());
        flowActivityobj.setActcancarboncopy(request.getActcancarboncopy());
        flowActivityobj.setActcanfinish(request.getActcanfinish());
        flowActivityobj.setActcancoordination(request.getActcancoordination());
        flowActivityobj.setActtemplatecode(request.getActtemplatecode());
        baseMapper.insert(flowActivityobj);
    }


    /**
     * 删除流程步骤信息
     *
     * @param flowId
     * @return
     */
    @Override
    public void DeleteFlowActivity(String flowId) {
        QueryWrapper<FlowActivity> FlowActivityWrapper = new QueryWrapper<>();
        FlowActivityWrapper.eq("ActFlowID", flowId);
        baseMapper.delete(FlowActivityWrapper);
    }


}
