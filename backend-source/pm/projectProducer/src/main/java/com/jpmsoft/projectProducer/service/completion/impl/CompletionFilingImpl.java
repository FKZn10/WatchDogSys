package com.jpmsoft.projectProducer.service.completion.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Completion.CompletionFilingRequest;
import com.jpmsoft.common.model.response.Completion.CompletionFilingResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.completion.CompletionFilingMapper;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionFiling;
import com.jpmsoft.projectProducer.pojo.entity.completion.CompletionFilingDetails;
import com.jpmsoft.projectProducer.service.completion.ICompletionFilingDetailsService;
import com.jpmsoft.projectProducer.service.completion.ICompletionFilingService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class CompletionFilingImpl extends FileImpl<CompletionFilingMapper, CompletionFiling, CompletionFilingRequest> implements ICompletionFilingService {

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private ICompletionFilingDetailsService iCompletionFilingDetailsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateCompletionFiling(CompletionFilingRequest request) {
        CompletionFiling entity = new CompletionFiling();
        createOrUpdateFileEntity(entity, request);
        if(!CollectionUtils.isEmpty(request.getCompletionFilingDetails())){
            iCompletionFilingDetailsService.createiCompletionFilingDetails(request.getCompletionFilingDetails(),entity.getCfId(),"delete");
        }

        return BaseResponse.ok(entity.getCfId());
    }

    @Override
    public BaseResponse getCompletionFilingList(CompletionFilingRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Cf_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Cf_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<CompletionFilingRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("conName").put("cfType")
                .put("secId").put("cfContractorId").put("cfState").put("cfCreateUname")
                .put(request.getCfCreateTimes(), "cfCreateTimeStart", "cfCreateTimeEnd");
        IPage<CompletionFilingResponse> page = new Page<>(request.getPage(), request.getSize());
        List<CompletionFilingResponse> datas = baseMapper.getCompletionFilingList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getCompletionFilingListNoAuth(CompletionFilingRequest request) {
        QueryMap<CompletionFilingRequest> params = new QueryMap<>(request);
        params.put("pName").put("pId").put("conName").put("cfType").put("secId")
                .put("cfContractorId").put("cfState").put("cfCreateUname")
                .put(request.getCfCreateTimes(), "cfCreateTimeStart", "cfCreateTimeEnd");
        IPage<CompletionFilingResponse> page = new Page<>(request.getPage(), request.getSize());
        List<CompletionFilingResponse> datas = baseMapper.getCompletionFilingList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getCompletionFilingById(String cfId) {
        CompletionFilingResponse data = baseMapper.getCompletionFilingById(cfId);
        QueryWrapper<CompletionFilingDetails> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("cf_Id",cfId);
        data.setCompletionFilingDetails(iCompletionFilingDetailsService.getCompletionFilingDetailsByCfid(queryWrapper));
        return BaseResponse.ok(data);
    }

}
