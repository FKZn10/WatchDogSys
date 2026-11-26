package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ConInvoiceSignMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConInvoiceSignDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConInvoiceSign;
import com.jpmsoft.projectProducer.service.contract.IConInvoiceInfoService;
import com.jpmsoft.projectProducer.service.contract.IConInvoiceSignService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/3/28 15:14
 */
@Service
public class ConInvoiceSignImpl extends FileImpl<ConInvoiceSignMapper, ConInvoiceSign, ConInvoiceSignDTO> implements IConInvoiceSignService {
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IConInvoiceInfoService conInvoiceInfoService;

    @Override
    public BaseResponse createOrUpdateConInvoiceSign(ConInvoiceSignDTO request) {
        ConInvoiceSign entity = new ConInvoiceSign();
        if (ValidateUtil.isNotNull(request.getIsTime()))
            request.setIsYear(DateUtil.getTimeYear(request.getIsTime()));
        createOrUpdateFileEntity(entity, request);
        conInvoiceInfoService.createOrUpdate(request.getConInvoiceInfos(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getConInvoiceSignList(ConInvoiceSignDTO request, String uid) {
        String authSql = sysUserService.getAuthSql("Con_Invoice_Sign", uid, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        QueryMap<ConInvoiceSignDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("conId").put("conName")
                .put("pCode").put("conCode").put("ciCode").put("state").put("createUname")
                .put("ciPayerName").put("ciType").put("ciStatus").put("ciIncomeStatus").put("ciPayerUnit");
        IPage<ConInvoiceSignDTO> page = new Page<>(request.getPage(), request.getSize());
        List<ConInvoiceSignDTO> data = baseMapper.getConInvoiceSignList(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConInvoiceSignById(String id) {
        return BaseResponse.ok(baseMapper.getConInvoiceSignById(id));
    }
}
