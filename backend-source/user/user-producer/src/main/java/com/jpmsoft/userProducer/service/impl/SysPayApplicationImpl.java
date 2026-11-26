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
import com.jpmsoft.userProducer.mapper.SysPayApplicationMapper;
import com.jpmsoft.userProducer.pojo.dto.SysPayApplicationDTO;
import com.jpmsoft.userProducer.pojo.entity.SysPayApplication;
import com.jpmsoft.userProducer.service.ISysPayApplicationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/7 15:13
 */
@Service
public class SysPayApplicationImpl extends ServiceImpl<SysPayApplicationMapper, SysPayApplication>
        implements ISysPayApplicationService {
    @Override
    public BaseResponse createOrUpdateSysPayApplication(SysPayApplicationDTO request) {
        SysPayApplication entity = new SysPayApplication();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isNotEmpty(request.getPaId())) {
            SysPayApplication model = baseMapper.selectById(request.getPaId());
            if (model == null) return BaseResponse.fail(BaseMsg.infoNotExist);
            baseMapper.updateById(entity);
        } else {
            entity.setPaId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        }
        return BaseResponse.ok(entity.getPaId());
    }

    @Override
    public BaseResponse getSysPayApplicationList(SysPayApplicationDTO request) {
        QueryMap<SysPayApplicationDTO> query = new QueryMap<>(request);
        query.put("appName").put("appCode").put("appType").put("teName")
                .put("teCode").put("teType").put("teChargeType");
        IPage<SysPayApplicationDTO> page = new Page<>(request.getPage(), request.getSize());
        List<SysPayApplicationDTO> data = baseMapper.getSysPayApplicationList(page, query.getQuery());
        return BaseResponse.ok(data, Integer.parseInt(String.valueOf(page.getTotal())));
    }

    @Override
    public BaseResponse delSysPayApplicationByPaId(String paId) {
        SysPayApplication model = baseMapper.selectById(paId);
        if (model == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(paId);
        return BaseResponse.ok();
    }
}
