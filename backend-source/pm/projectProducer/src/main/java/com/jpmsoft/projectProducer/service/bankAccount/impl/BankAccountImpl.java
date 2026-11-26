package com.jpmsoft.projectProducer.service.bankAccount.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.enums.UserTypeEnum;
import com.jpmsoft.common.model.request.Account.BankAccountRequest;
import com.jpmsoft.common.model.response.Account.BankAccountResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.bankAccount.BankAccountMapper;
import com.jpmsoft.projectProducer.pojo.entity.bankAccount.BankAccount;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.service.bankAccount.IBankAccountService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class BankAccountImpl extends FileImpl<BankAccountMapper, BankAccount, BankAccountRequest> implements IBankAccountService {

    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateBankAccount(BankAccountRequest request) {
        
        BankAccount entity = new BankAccount();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getBaId());
    }

    @Override
    public BaseResponse getBankAccountList(BankAccountRequest request) {
        Map<String, Object> params = setParams(null, request);
        IPage<BankAccountResponse> page = new Page<>(request.getPage(), request.getSize());
        List<BankAccountResponse> datas = baseMapper.getBankAccountList(page, params);
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getBankAccountAuthList(BankAccountRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Ba_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Ba_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        Map<String, Object> params = setParams(authSql, request);
        SysUser user = sysUserService.getById(uId);
        //外部用户仅可查看本单位用户
        if (ValidateUtil.isNotEmpty(user.getUType())
                && user.getUType().equals(UserTypeEnum.Out_User.getIndex().toString()))
            params.put("baCId", user.getCId());
        IPage<BankAccountResponse> page = new Page<>(request.getPage(), request.getSize());
        List<BankAccountResponse> datas = baseMapper.getBankAccountList(page, params);
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getBankAccountById(String baId) {
        BankAccountResponse data = baseMapper.getBankAccountById(baId);
        return BaseResponse.ok(data);
    }

    private Map<String, Object> setParams(String authSql, BankAccountRequest request) {
        QueryMap<BankAccountRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("baCId").put("baCName").put("baType").put("baName").put("baNu")
                .put("baBank").put("baBankName").put("baState").put("pId").put("pName").put("payNames");
        return params.getQuery();
    }
}
