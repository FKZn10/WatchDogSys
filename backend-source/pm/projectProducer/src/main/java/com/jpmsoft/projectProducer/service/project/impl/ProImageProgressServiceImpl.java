package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.Project.ProImageProgressModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.ProImageProgressMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProImageProgress;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.project.IProImageProgressService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ProImageProgressServiceImpl extends ServiceImpl<ProImageProgressMapper, ProImageProgress> implements IProImageProgressService {
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public BaseResponse createOrUpdateProImageProgress(ProImageProgressModel request) {
        ProImageProgress entity = new ProImageProgress();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getId())) {
            entity.setCreateTime(new Date());
            entity.setId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            BaseResponse fileRefresh = sysFileAnnexService.addFormRefreshRelationFile(request.getImg(), entity.getId());
            if (fileRefresh.getStatusCode() != HttpStatus.OK.value() || fileRefresh.getSuccess() != Boolean.TRUE)
                throw new JPMException("图片上传异常！");
        } else {
            ProImageProgress model = baseMapper.selectById(entity.getId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setCreateTime(model.getCreateTime());
            entity.setCreateUid(model.getCreateUid());
            entity.setCreateUname(model.getCreateUname());
            baseMapper.updateById(entity);
            BaseResponse fileRefresh = sysFileAnnexService.addFormRefreshRelationFile(request.getImg(), entity.getId());
            if (fileRefresh.getStatusCode() != HttpStatus.OK.value() || fileRefresh.getSuccess() != Boolean.TRUE)
                throw new JPMException("图片上传异常！");
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getProImageProgressList(ProImageProgressModel request, String userId) {
        QueryMap<ProImageProgressModel> params = new QueryMap<>(request);
        params.put("title").put("pId").put("pName").put("userId", userId);
        List<ProImageProgressModel> datas = baseMapper.getProImageProgressList(params.getQuery());
        Integer total = baseMapper.getProImageProgressListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getProImageProgressById(String id, String userId) {
        return BaseResponse.ok(baseMapper.getProImageProgressById(id, userId), 1);
    }


    @Override
    public BaseResponse deleteProImageProgressById(String id) {
        int sum = baseMapper.deleteById(id);
        return sum == 1 ? BaseResponse.ok() : BaseResponse.fail("删除失败");
    }
}
