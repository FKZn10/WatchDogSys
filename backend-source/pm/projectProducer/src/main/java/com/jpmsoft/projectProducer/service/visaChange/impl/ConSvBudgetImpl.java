package com.jpmsoft.projectProducer.service.visaChange.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.visaChange.ConSvBudgetMapper;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConSvBudgetDTO;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConSvBudget;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.visaChange.IConSvBudgetService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/19 10:35
 */
@Service
public class ConSvBudgetImpl extends FileImpl<ConSvBudgetMapper, ConSvBudget, ConSvBudgetDTO> implements IConSvBudgetService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    @CodeGeneration(key = "id", codeParam = "csbCode", method = "setCsbCode", codeRule = "ConSvBudget", beanClass = ConSvBudgetDTO.class)
    public BaseResponse createOrUpdateConSvBudget(ConSvBudgetDTO request) {
        ConSvBudget entity = new ConSvBudget();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getConSvBudgetList(ConSvBudgetDTO request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Sv_Budget", uId, "Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConSvBudgetDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("conName").put("csbCode").put("state").put("svisSubCode")
                .put("svisName").put("svisCode").put("svisProjectName").put("pId").put("createUname");
        IPage<ConSvBudgetDTO> page = new Page<>(request.getPage(), request.getSize());
        List<ConSvBudgetDTO> data = baseMapper.getConSvBudgetList(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConSvBudgetById(String id) {
        return BaseResponse.ok(baseMapper.getConSvBudgetById(id));
    }

    @Override
    public BaseResponse getNotContainAmtByConId(String conId) {
        return BaseResponse.ok(baseMapper.getNotContainAmtByConId(conId));
    }
}
