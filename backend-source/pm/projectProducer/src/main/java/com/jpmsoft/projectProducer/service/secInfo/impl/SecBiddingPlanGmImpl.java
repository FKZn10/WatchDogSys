package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecBiddingPlanGmMppaer;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingPlanGm;
import com.jpmsoft.projectProducer.service.secInfo.ISecBiddingPlanGmService;
import com.jpmsoft.projectProducer.service.secInfo.ISecInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/12/26 13:50
 */
@Service
public class SecBiddingPlanGmImpl extends ServiceImpl<SecBiddingPlanGmMppaer, SecBiddingPlanGm>
        implements ISecBiddingPlanGmService {
    @Resource
    private ISecInfoService secInfoService;


    @Override
    public BaseResponse selectLastApprovedMoney(String secId) {
        QueryWrapper<SecBiddingPlanGm> wrapper = new QueryWrapper<>();
        wrapper.eq("sec_id", secId);
        wrapper.eq("state", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper.orderByDesc("create_time");
        wrapper.last(" limit 1");
        SecBiddingPlanGm entity = baseMapper.selectOne(wrapper);
        if (ValidateUtil.isNotNull(entity))
            return BaseResponse.ok(entity.getBpMoney());
        else
            return BaseResponse.ok();
    }
}
