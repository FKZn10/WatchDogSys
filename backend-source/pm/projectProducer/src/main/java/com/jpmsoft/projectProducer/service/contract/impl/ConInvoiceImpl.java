package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.mapper.contract.ConInvoiceMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConInvoiceDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConInvoice;
import com.jpmsoft.projectProducer.service.contract.IConInvoiceService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/19 8:59
 */
@Service
public class ConInvoiceImpl extends FileImpl<ConInvoiceMapper, ConInvoice, ConInvoiceDTO> implements IConInvoiceService {
    @Resource
    private ISysUserService sysUserService;


    @Override
    @CodeGeneration(key = "id", codeParam = "ciCode", method = "setCiCode", codeRule = "ConInvoice", beanClass = ConInvoiceDTO.class)
    public BaseResponse createOrUpdateInvoice(ConInvoiceDTO request) {
        ConInvoice entity = new ConInvoice();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getInvoiceList(ConInvoiceDTO request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_Invoice", uId, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        QueryMap<ConInvoiceDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("conId").put("conName")
                .put("pCode").put("conCode").put("ciCode").put("state").put("createUname")
                .put("ciPayerName").put("ciType").put("ciStatus").put("ciIncomeStatus").put("ciPayerUnit");
        IPage<ConInvoiceDTO> page = new Page<>(request.getPage(), request.getSize());
        List<ConInvoiceDTO> data = baseMapper.getInvoiceList(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getInvoiceById(String id) {
        return BaseResponse.ok(baseMapper.getInvoiceById(id));
    }
}
