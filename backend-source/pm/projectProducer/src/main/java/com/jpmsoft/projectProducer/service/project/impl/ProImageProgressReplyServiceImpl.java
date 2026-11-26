package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressReplyModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.project.ProImageProgressReplyMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProImageProgressReply;
import com.jpmsoft.projectProducer.service.project.IProImageProgressReplyService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProImageProgressReplyServiceImpl extends ServiceImpl<ProImageProgressReplyMapper, ProImageProgressReply> implements IProImageProgressReplyService {

    @Override
    public BaseResponse createProImageProgressReply(ProImageProgressReplyModel request) {
        ProImageProgressReply entity = new ProImageProgressReply();
        BeanUtils.copyProperties(request, entity);
        entity.setCreateTime(new Date());
        entity.setId(GenerateUUID.getUUID());
        baseMapper.insert(entity);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse deleteProImageProgressReplyById(String id) {
        int sum = baseMapper.deleteById(id);
        return sum == 1 ? BaseResponse.ok() : BaseResponse.fail("删除失败");
    }
}
