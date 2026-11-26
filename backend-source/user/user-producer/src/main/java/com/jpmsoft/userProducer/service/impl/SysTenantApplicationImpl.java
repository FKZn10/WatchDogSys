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
import com.jpmsoft.userProducer.mapper.SysTenantApplicationMapper;
import com.jpmsoft.userProducer.pojo.dto.SysTenantApplicationDTO;
import com.jpmsoft.userProducer.pojo.entity.SysTenantApplication;
import com.jpmsoft.userProducer.pojo.vo.SysTenantApplicationVO;
import com.jpmsoft.userProducer.service.ISysTenantApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 9:42
 */
@Service
public class SysTenantApplicationImpl extends ServiceImpl<SysTenantApplicationMapper, SysTenantApplication>
        implements ISysTenantApplicationService {

    @Override
    public BaseResponse createOrUpdateSysTenantApplication(SysTenantApplicationDTO request) {
        SysTenantApplication entity = new SysTenantApplication();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isNotEmpty(request.getTaId())) {
            SysTenantApplication model = baseMapper.selectById(request.getTaId());
            if (model == null) return BaseResponse.fail(BaseMsg.infoNotExist);
            baseMapper.updateById(entity);
        } else {
            entity.setTaId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        }
        return BaseResponse.ok(entity.getTaId());
    }

    @Override
    public BaseResponse getSysTenantApplicationList(SysTenantApplicationDTO request) {
        QueryMap<SysTenantApplicationDTO> query = new QueryMap<>(request);
        query.put("appName").put("appCode").put("appType").put("teName")
                .put("teCode").put("teType").put("teChargeType");
        IPage<SysTenantApplicationVO> page = new Page<>(request.getPage(), request.getSize());
        List<SysTenantApplicationVO> data = baseMapper.getSysTenantApplicationList(page, query.getQuery());
        return BaseResponse.ok(data, Integer.parseInt(String.valueOf(page.getTotal())));
    }

    @Override
    public BaseResponse delSysTenantApp(String taId) {
        SysTenantApplication model = baseMapper.selectById(taId);
        if (model == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(taId);
        return BaseResponse.ok();
    }
}
