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
import com.jpmsoft.userProducer.mapper.SysTenantMapper;
import com.jpmsoft.userProducer.pojo.dto.SysTenantDTO;
import com.jpmsoft.userProducer.pojo.entity.SysTenant;
import com.jpmsoft.userProducer.pojo.vo.SysTenantVO;
import com.jpmsoft.userProducer.service.ISysSequenceSetService;
import com.jpmsoft.userProducer.service.ISysTenantService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/2 9:35
 */
@Service
public class SysTenantImpl extends ServiceImpl<SysTenantMapper, SysTenant> implements ISysTenantService {

    @Resource
    private ISysSequenceSetService sysSequenceSetService;

    @Override
    public BaseResponse createOrUpdateSysTenant(SysTenantDTO request) {
        SysTenant entity = new SysTenant();
        BeanUtils.copyProperties(request, entity);
        String codeRule = null;
        if (ValidateUtil.isNotEmpty(request.getTeId())) {
            SysTenant tenant = baseMapper.selectById(request.getTeId());
            if (tenant == null) return BaseResponse.fail(BaseMsg.infoNotExist);
            baseMapper.updateById(entity);
        } else {
            if (sysSequenceSetService.sequenceRestEnable("SysTenant", null)) {
                entity.setTeCode(sysSequenceSetService.generateCodeByTCode("SysTenant", request, null));
                if (ValidateUtil.isEmpty(entity.getTeCode()))
                    return BaseResponse.fail(BaseMsg.codeGenerateFail);
                codeRule = "SysTenant";
            }
            entity.setTeId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            baseMapper.insert(entity);
        }
        if (ValidateUtil.isNotEmpty(codeRule)) sysSequenceSetService.refershAutoNo(codeRule, request, null);
        return BaseResponse.ok(entity.getTeId());
    }

    @Override
    public BaseResponse getSysTenantList(SysTenantDTO request) {
        QueryMap<SysTenantDTO> query = new QueryMap<>(request);
        query.put("teName").put("teCode").put("teType").put("teNo")
                .put("teLegal").put("teContacts").put("teContactsTel")
                .put("teChargeType").put("teStatus").put("state")
                .put("teIndustry").put("teSize");
        IPage<SysTenantVO> page = new Page<>(request.getPage(), request.getSize());
        List<SysTenantVO> datas = baseMapper.getSysTenantList(page, query.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSysTenantById(String id) {
        SysTenant tenant = baseMapper.selectById(id);
        if (tenant == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        SysTenantVO vo = new SysTenantVO();
        BeanUtils.copyProperties(tenant, vo);
        return BaseResponse.ok(vo);
    }
}
