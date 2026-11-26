package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.PayChangeAssociationRequest;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.common.model.response.Contract.PayChangeAssociationResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.PayChangeAssociationMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.PayChangeAssociation;
import com.jpmsoft.projectProducer.service.contract.IPayChangeAssociationService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 支付变更关联
 */
@Service
public class PayChangeAssociationImpl extends ServiceImpl<PayChangeAssociationMapper, PayChangeAssociation> implements IPayChangeAssociationService {
    /**
     * 创建支付变更关联列表
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createPayChangeAssociationList(ConPayRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        this.remove(Wrappers.<PayChangeAssociation>lambdaQuery().eq(PayChangeAssociation::getPayId, request.getPayId()));
        List<PayChangeAssociationRequest> list = request.getPayChangeAssociationList();
        if (ValidateUtil.isNotEmpty(list)) {
            List<PayChangeAssociation> saveList = new ArrayList<>();
            for (PayChangeAssociationRequest item : list) {
                PayChangeAssociation entity = new PayChangeAssociation();
                BeanUtils.copyProperties(item, entity);
                entity.setId(GenerateUUID.getUUID());
                entity.setPayId(request.getPayId());
                saveList.add(entity);
            }
            this.saveBatch(saveList);
        }
        return BaseResponse.ok();
    }

    /**
     * 根据payId获取支付变更关联列表
     *
     * @param payId
     * @return
     */
    @Override
    public BaseResponse getAssociationListByPayId(String payId) {
        if (ValidateUtil.isEmpty(payId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<PayChangeAssociationResponse> list = baseMapper.getAssociationListByPayId(payId);
        if (ValidateUtil.isEmpty(list)) {
            return BaseResponse.ok();
        }
        return BaseResponse.ok(list);
    }

    @Override
    public BaseResponse existsAssociation(String conId, String payId) {
        if (ValidateUtil.isEmpty(conId) && ValidateUtil.isEmpty(payId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (ValidateUtil.isNotEmpty(payId)) {
            QueryWrapper<PayChangeAssociation> wrapper = new QueryWrapper<>();
            wrapper.eq("pay_id", payId);
            return BaseResponse.ok(baseMapper.selectList(wrapper));
        }

        if (ValidateUtil.isNotEmpty(conId)) {
            return BaseResponse.ok(baseMapper.selectCountByConId(conId));
        }
        return BaseResponse.fail();
    }
}