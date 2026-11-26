package com.jpmsoft.gmgws.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.gmgws.mapper.AgreementMapper;
import com.jpmsoft.gmgws.pojo.entity.Agreement;
import com.jpmsoft.gmgws.service.AgreementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AgreementServiceImpl extends ServiceImpl<AgreementMapper, Agreement> implements AgreementService {


    //协议接触
    @Override
    public BaseResponse relieve(String id,String aRelieveId) {
        if(ValidateUtil.isEmpty(id))return BaseResponse.fail("id不能为空");
        if(ValidateUtil.isEmpty(aRelieveId))return BaseResponse.fail("aRelieveID不能为空");
        LambdaQueryWrapper<Agreement> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Agreement::getId,id).eq(Agreement::getState,7).eq(Agreement::getARelieveId,aRelieveId);

        List<Agreement> agreements = baseMapper.selectList(queryWrapper);
        if(ValidateUtil.isEmpty(agreements)){
            return BaseResponse.fail("没有符合的记录");
        }else{
            Agreement agreement = new Agreement();
            agreement.setId(aRelieveId);
            agreement.setAIsRelieve("02");
            baseMapper.updateById(agreement);
        }
        return  BaseResponse.ok();
    }
}
