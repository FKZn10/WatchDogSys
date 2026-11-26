package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.ganttPlan.MemoirCommentMapper;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirCommentDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.MemoirComment;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.ganttPlan.IMemoirCommentService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/9/5 11:49
 */
@Service
public class MemoirCommentImpl extends ServiceImpl<MemoirCommentMapper, MemoirComment> implements IMemoirCommentService {
    @Resource
    private RedisUtils redisUtils;

    @Override
    public BaseResponse memoirComment(MemoirCommentDTO data, String token) {
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        MemoirComment entity = new MemoirComment();
        entity.setId(GenerateUUID.getUUID());
        entity.setMiId(data.getMiId());
        entity.setMcContent(data.getMcContent());
        entity.setMcCpy(user.getCId());
        entity.setMcUid(user.getId());
        entity.setMcUname(user.getName());
        Integer sort = baseMapper.maxSort(data.getMiId());
        entity.setMcSort(ValidateUtil.isNull(sort) ? 1 : sort + 1);
        entity.setMcTime(new Date());
        baseMapper.insert(entity);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deleteMemoirComment(String id, String token) {
        MemoirComment entity = baseMapper.selectById(id);
        if (ValidateUtil.isNull(entity)) return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public List<MemoirCommentDTO> selectByMiIds(List<String> miIds) {
        if (CollectionUtils.isEmpty(miIds)) return new ArrayList<>();
        return baseMapper.selectByMiIds(miIds);
    }
}
