package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.pay.PayFieldDtlDto;
import com.jpmsoft.common.model.response.pay.ConPayResponse;
import com.jpmsoft.common.model.response.pay.PayFieldDtlVo;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.PayFieldDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPay;
import com.jpmsoft.projectProducer.pojo.entity.contract.PayFieldDtl;
import com.jpmsoft.projectProducer.service.contract.IPayFieldDtlService;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/5 14:54
 */
@Service
public class PayFieldDtlImpl extends ServiceImpl<PayFieldDtlMapper, PayFieldDtl> implements IPayFieldDtlService {

//    @Resource
//    private ExpressionParserUtil expressionParserUtil;

    @Override
    public Map<String, Object> getConPayAndFiledDtl(ConPayResponse con) {
        Map<String, Object> data = new HashMap<>();
        data = BeanMap.create(con);
        List<PayFieldDtlVo> dtls = getPayFieldDtlByPayId(con.getPayId());
        if (ValidateUtil.isNotEmpty(dtls))
            for (PayFieldDtlVo dtl : dtls)
                data.put(dtl.getPfCode(), dtl.getPfValue());
        return data;
    }


    @Override
    public List<PayFieldDtlVo> getPayFieldDtlByPayId(String payId) {
        if (ValidateUtil.isEmpty(payId)) return null;
        return baseMapper.getPayFieldDtlByPayId(payId);
    }

    @Override
    public void createOrUpdatePayFieldDtl(List<PayFieldDtlDto> datas, ConPay pay) {
        delPayFieldDtlByPayId(pay.getPayId());
        if (ValidateUtil.isNotEmpty(datas)) {
            List<PayFieldDtl> entitys = new ArrayList<>();
            entitys = datas.stream().map(n -> {
                PayFieldDtl entity = new PayFieldDtl();
                entity.setPfId(n.getPfId());
                entity.setPfValue(ValidateUtil.isEmpty(n.getPfValue()) ? "" : n.getPfValue());
                entity.setPayId(pay.getPayId());
                entity.setCreateUid(pay.getPayCreateUid());
                entity.setCreateUname(pay.getPayCreateUname());
                entity.setCreateTime(pay.getPayCreateTime());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void delPayFieldDtlByPayId(String payId) {
        QueryWrapper<PayFieldDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Pay_Id", payId);
        baseMapper.delete(wrapper);
    }

    @Override
    public BaseResponse calculationValue(List<PayFieldDtlDto> datas) {
//        datas = datas.stream().sorted(Comparator.comparing(PayFieldDtlDto::getPfSort)).collect(Collectors.toList());
//        Map<String, Object> map = new HashMap<>();
//        datas.forEach(data -> {
//            map.put(data.getPfCode(), ValidateUtil.isEmpty(data.getPfValue()) ? 0 : data.getPfValue());
//        });
//        List<PayFieldDtlVo> results = new ArrayList<>();
//        results = datas.stream().map(n -> {
//            PayFieldDtlVo result = new PayFieldDtlVo();
//            BeanUtils.copyProperties(n, result);
//            if (ValidateUtil.isNotEmpty(result.getPfFormula())) {
//                result.setPfValue(expressionParserUtil.parser(result.getPfFormula(), map, BigDecimal.class).toString());
//            } else
//                result.setPfValue(n.getPfValue());
//            return result;
//        }).collect(Collectors.toList());
//        return BaseResponse.ok(results);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse validValue(List<PayFieldDtlDto> datas) {
//        datas = datas.stream().sorted(Comparator.comparing(PayFieldDtlDto::getPfSort)).collect(Collectors.toList());
//        Map<String, Object> map = new HashMap<>();
//        datas.forEach(data -> {
//            map.put(data.getPfCode(), data.getPfValue());
//        });
//        List<PayFieldDtlVo> results = new ArrayList<>();
//        results = datas.stream().map(n -> {
//            PayFieldDtlVo result = new PayFieldDtlVo();
//            BeanUtils.copyProperties(n, result);
//            if (ValidateUtil.isNotEmpty(result.getPfVerification())) {
//                result.setPfVerificationPass(expressionParserUtil.parser(result.getPfVerification(), map, Boolean.class));
//            } else
//                result.setPfVerificationPass(Boolean.TRUE);
//            return result;
//        }).collect(Collectors.toList());
//        return BaseResponse.ok(results);
        return BaseResponse.ok();
    }

}
