package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.mapper.contract.ConEcoMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConEcoDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConEco;
import com.jpmsoft.projectProducer.service.contract.IConEcoService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/11/14 10:28
 */
@Service
public class ConEcoImpl extends FileImpl<ConEcoMapper, ConEco, ConEcoDTO> implements IConEcoService {

    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "ecoId", codeParam = "ecoCode", method = "setEcoCode", codeRule = "ConEco", beanClass = ConEcoDTO.class)
    public BaseResponse createOrUpdateConEco(ConEcoDTO request) {
        ConEco entity = new ConEco();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getEcoId());
    }

    @Override
    public BaseResponse getConEcoList(ConEcoDTO request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Eco", uId, "Eco_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Eco_State");
        QueryMap<ConEcoDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("conName").put("ecName").put("pCode").put("conCode").put("ecCode").put("ecoState").put("ecoCreateUname").put("ecoEngineerName").put("ecoPartyName").put("ecoCode");
        IPage<ConEcoDTO> page = new Page<>(request.getPage(), request.getSize());
        List<ConEcoDTO> data = baseMapper.getConEcoList(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConEcoById(String ecoId) {
        return BaseResponse.ok(baseMapper.getConEcoById(ecoId));
    }
}
