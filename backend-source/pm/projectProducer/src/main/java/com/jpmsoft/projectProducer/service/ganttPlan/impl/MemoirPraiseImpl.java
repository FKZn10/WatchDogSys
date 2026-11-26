package com.jpmsoft.projectProducer.service.ganttPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.ganttPlan.MemoirPraiseMapper;
import com.jpmsoft.projectProducer.pojo.dto.ganttPlan.MemoirPraiseDTO;
import com.jpmsoft.projectProducer.pojo.entity.ganttPlan.MemoirPraise;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.ganttPlan.IMemoirPraiseService;
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
public class MemoirPraiseImpl extends ServiceImpl<MemoirPraiseMapper, MemoirPraise> implements IMemoirPraiseService {
    @Resource
    private RedisUtils redisUtils;

    @Override
    public BaseResponse memoirPraise(String id, String token) {
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        if (exists(id, user.getId())) return BaseResponse.ok();
        MemoirPraise entity = new MemoirPraise();
        entity.setId(GenerateUUID.getUUID());
        entity.setMiId(id);
        entity.setMpCpy(user.getCId());
        entity.setMpUid(user.getId());
        entity.setMpUname(user.getName());
        Integer sort = baseMapper.maxSort(id);
        entity.setMpSort(ValidateUtil.isNull(sort) ? 1 : sort + 1);
        entity.setMpTime(new Date());
        baseMapper.insert(entity);
        return BaseResponse.ok();
    }


    private boolean exists(String id, String uid) {
        QueryWrapper<MemoirPraise> wrapper = new QueryWrapper<>();
        wrapper.eq("mi_id", id);
        wrapper.eq("mp_uid", uid);
        return baseMapper.exists(wrapper);
    }

    @Override
    public BaseResponse deleteMemoirPraise(String id, String token) {
        MemoirPraise entity = baseMapper.selectById(id);
        if (ValidateUtil.isNull(entity)) return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public List<MemoirPraiseDTO> selectByMiIds(List<String> miIds) {
        if (CollectionUtils.isEmpty(miIds)) return new ArrayList<>();
        return baseMapper.selectByMiIds(miIds);
    }

}
