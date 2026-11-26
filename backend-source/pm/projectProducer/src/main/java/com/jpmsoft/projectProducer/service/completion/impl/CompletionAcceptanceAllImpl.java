package com.jpmsoft.projectProducer.service.completion.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Completion.CompletionAcceptanceAllRequest;
import com.jpmsoft.common.model.response.Completion.CompletionAcceptanceAllResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.completion.CompletionAcceptanceAllMapper;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionAcceptanceAll;
import com.jpmsoft.projectProducer.service.completion.ICompletionAcceptanceAllService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CompletionAcceptanceAllImpl extends FileImpl<CompletionAcceptanceAllMapper, CompletionAcceptanceAll, CompletionAcceptanceAllRequest> implements ICompletionAcceptanceAllService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateCompletionAcceptanceAll(CompletionAcceptanceAllRequest request) {
        CompletionAcceptanceAll entity = new CompletionAcceptanceAll();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getCaaId());
    }

    @Override
    public BaseResponse getCompletionAcceptanceAllList(CompletionAcceptanceAllRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Completion_Acceptance_All", uId, "Caa_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Caa_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<CompletionAcceptanceAllRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("conName")
                .put("caaSunitId").put("caaDunitId").put("caaCcunitId");
        IPage<CompletionAcceptanceAllResponse> page = new Page<>(request.getPage(), request.getSize());
        List<CompletionAcceptanceAllResponse> datas = baseMapper.getCompletionAcceptanceAllList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getCompletionAcceptanceAllListNoAuth(CompletionAcceptanceAllRequest request) {
        QueryMap<CompletionAcceptanceAllRequest> params = new QueryMap<>(request);
        params.put("pName").put("pId").put("conName").put("caaState").put("caaCreateUname")
                .put("caaSunitId").put("caaDunitId").put("caaCcunitId");
        IPage<CompletionAcceptanceAllResponse> page = new Page<>(request.getPage(), request.getSize());
        List<CompletionAcceptanceAllResponse> datas = baseMapper.getCompletionAcceptanceAllList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getCompletionAcceptanceAllById(String caaId) {
        CompletionAcceptanceAllResponse data = baseMapper.getCompletionAcceptanceAllById(caaId);
        return BaseResponse.ok(data);
    }
}
