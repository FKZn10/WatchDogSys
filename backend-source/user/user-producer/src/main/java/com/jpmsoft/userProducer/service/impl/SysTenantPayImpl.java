package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysTenantPayMapper;
import com.jpmsoft.userProducer.pojo.dto.SysTenantPayDTO;
import com.jpmsoft.userProducer.pojo.entity.SysTenantPay;
import com.jpmsoft.userProducer.pojo.vo.SysTenantPayVO;
import com.jpmsoft.userProducer.service.ISysTenantPayService;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 14:40
 */
@Service
public class SysTenantPayImpl extends ServiceImpl<SysTenantPayMapper, SysTenantPay> implements ISysTenantPayService {

    @Override
    public BaseResponse createOrUpdateSysTenantPay(SysTenantPayDTO request) {
        SysTenantPay entity = new SysTenantPay();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isNotEmpty(request.getPayId())) {
            SysTenantPay tenant = baseMapper.selectById(request.getPayId());
            if (tenant == null) return BaseResponse.fail(BaseMsg.infoNotExist);
            baseMapper.updateById(entity);
        } else {
            entity.setPayId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            baseMapper.insert(entity);
        }
        return BaseResponse.ok(entity.getPayId());
    }

    @Override
    public BaseResponse getSysTenantPayList(SysTenantPayDTO request) {
        QueryMap<SysTenantPayDTO> query = new QueryMap<>(request);
        query.put("teName").put("teCode").put("teType").put("teChargeType")
                .put("state").put("payType").put("payWay").put("payStatus").put("createUname");
        IPage<SysTenantPayVO> page = new Page<>(request.getPage(), request.getSize());
        List<SysTenantPayVO> datas = baseMapper.getSysTenantPayList(page, query.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSysTenantPayById(String id) {
        return BaseResponse.ok(baseMapper.getSysTenantPayById(id));
    }
}
