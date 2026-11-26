package com.jpmsoft.projectProducer.service.bankAccount.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Account.BankAccountChangeRequest;
import com.jpmsoft.common.model.response.Account.BankAccountChangeResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.bankAccount.BankAccountChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.bankAccount.BankAccountChange;
import com.jpmsoft.projectProducer.service.bankAccount.IBankAccountChangeService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class BankAccountChangeImpl extends FileImpl<BankAccountChangeMapper, BankAccountChange, BankAccountChangeRequest> implements IBankAccountChangeService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateBankAccountChange(BankAccountChangeRequest request) {
        BankAccountChange entity = new BankAccountChange();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getBacId());
    }

    @Override
    public BaseResponse getBankAccountChangeList(BankAccountChangeRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Bank_Account_Change", uId, "Bac_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Bac_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<BankAccountChangeRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("conName");
        IPage<BankAccountChangeResponse> page = new Page<>(request.getPage(), request.getSize());
        List<BankAccountChangeResponse> datas = baseMapper.getBankAccountChangeList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getBankAccountChangeById(String bacId) {
        BankAccountChangeResponse data = baseMapper.getBankAccountChangeById(bacId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getBankAccountChangeByConId(String conId) {
        QueryWrapper<BankAccountChange> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        wrapper.eq("Bac_State", ApprovalStatusEnum.Already_Over.getIndex());
        List<BankAccountChange> newObj = baseMapper.selectList(wrapper);
        return BaseResponse.ok(newObj);
    }
}
