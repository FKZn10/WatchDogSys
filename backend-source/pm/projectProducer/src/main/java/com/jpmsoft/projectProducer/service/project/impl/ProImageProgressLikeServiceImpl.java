package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressLikeModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.project.ProImageProgressLikeMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProImageProgressLike;
import com.jpmsoft.projectProducer.service.project.IProImageProgressLikeService;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProImageProgressLikeServiceImpl extends ServiceImpl<ProImageProgressLikeMapper, ProImageProgressLike> implements IProImageProgressLikeService {

    @Override
    public BaseResponse createProImageProgressLike(ProImageProgressLikeModel request) {
        ProImageProgressLikeModel likeModel = baseMapper.getProImageProgressLikeByUid(request);
        // if (likeModel != null) return BaseResponse.fail("您已点过赞，不可重复点赞");
        if (likeModel != null) {
            baseMapper.deleteById(likeModel.getId());
            return BaseResponse.ok("取消点赞！",likeModel.getId());
        }
        ProImageProgressLike entity = new ProImageProgressLike();
        BeanUtils.copyProperties(request, entity);
        entity.setCreateTime(new Date());
        entity.setId(GenerateUUID.getUUID());
        baseMapper.insert(entity);
        return BaseResponse.ok("点赞成功！",entity.getId());
    }

    @Override
    public BaseResponse deleteProImageProgressLikeById(String id) {
        int sum = baseMapper.deleteById(id);
        return sum == 1 ? BaseResponse.ok() : BaseResponse.fail("删除失败");
    }
}
