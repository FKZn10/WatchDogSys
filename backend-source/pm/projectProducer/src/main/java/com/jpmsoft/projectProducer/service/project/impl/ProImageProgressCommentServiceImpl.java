package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressCommentModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.project.ProImageProgressCommentMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProImageProgressComment;
import com.jpmsoft.projectProducer.service.project.IProImageProgressCommentService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProImageProgressCommentServiceImpl extends ServiceImpl<ProImageProgressCommentMapper, ProImageProgressComment> implements IProImageProgressCommentService {

    @Override
    public BaseResponse createProImageProgressComment(ProImageProgressCommentModel request) {
        ProImageProgressComment entity = new ProImageProgressComment();
        BeanUtils.copyProperties(request, entity);
        entity.setCreateTime(new Date());
        entity.setId(GenerateUUID.getUUID());
        baseMapper.insert(entity);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getProImageProgressCommentListByIpId(String ipId) {
        List<ProImageProgressCommentModel> datas = baseMapper.getProImageProgressCommentListByIpId(ipId);
        Integer total = baseMapper.getProImageProgressCommentListByIpIdCount(ipId);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getProImageProgressCommentById(String id) {
        return BaseResponse.ok(baseMapper.getProImageProgressCommentById(id), 1);
    }

    @Override
    public BaseResponse deleteProImageProgressCommentById(String id) {
        int sum = baseMapper.deleteById(id);
        return sum == 1 ? BaseResponse.ok() : BaseResponse.fail("删除失败");
    }
}
