package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectApprovalModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.enums.ApprovalProjectStateEnum;
import com.jpmsoft.projectProducer.mapper.project.PreProjectApprovalMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectApproval;
import com.jpmsoft.projectProducer.pojo.request.fund.EngineeringFundApplicationRequest;
import com.jpmsoft.projectProducer.pojo.vo.fund.EngineeringFundApplicationVo;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.project.IPreProjectApprovalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class PreProjectApprovalServiceImpl extends ServiceImpl<PreProjectApprovalMapper, PreProjectApproval> implements IPreProjectApprovalService {
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ISysConfigService sysConfigService;
    @Override
    public BaseResponse createOrUpdatePreProjectApproval(PreProjectApprovalModel request) {
        log.info("request:{}",JacksonUtils.toJSONString(request));
        List<String> fileRelationIds = new ArrayList<>();
        fileRelationIds.add(request.getFileRelationId());
        fileRelationIds.add(request.getApprovalProjectFile());
        fileRelationIds.add(request.getApprovalProjectScoreFile());
        String primaryKey = request.getId();
        if (StrUtil.isEmpty(primaryKey)) {
           PreProjectApproval preProjectApproval = new PreProjectApproval();
           BeanUtil.copyProperties(request, preProjectApproval);
           preProjectApproval.setId(GenerateUUID.getUUID());
           preProjectApproval.setCreateTime(DateTime.now());
           preProjectApproval.setApprovalProjectState(ApprovalProjectStateEnum.NotStarted.getIndex());
           baseMapper.insert(preProjectApproval);
           //创建流程
           if(sysFileService.FileHelper(preProjectApproval,request,"create",request.getFileRelationId())){
                sysFileAnnexService.addFormCopyAndRefreshRelationFile(fileRelationIds,Collections.singletonList(request.getFileIds()), preProjectApproval.getId());
           }else{
                return BaseResponse.fail(ResMsg.infoNotExist);
           }
        }else {
           PreProjectApproval preProjectApproval = baseMapper.selectById(primaryKey);
           BeanUtil.copyProperties(request, preProjectApproval);
           baseMapper.updateById(preProjectApproval);
           sysFileAnnexService.refreshRelationFile(fileRelationIds,Collections.singletonList(request.getFileIds()),preProjectApproval.getId());
        }
        return BaseResponse.ok(primaryKey);
    }

    @Override
    public BaseResponse getPreProjectApprovalList(PreProjectApprovalModel request) {
        QueryMap<PreProjectApprovalModel> params = new QueryMap<>(request);
        params.put("approvalCompanyName").put("approvalPlanYear").put("approvalProjectName").put("approvalProjectType")
                .put("approvalProjectLeaderName").put("approvalProjectState");
        IPage<PreProjectApprovalModel> page = new Page<>(request.getPage(), request.getSize());
        List<PreProjectApprovalModel> preProjectApprovalModels
                = baseMapper.getPreProjectApprovalModelList(params.getQuery(),page);
        log.info("preProjectApprovalModels:{}",JacksonUtils.toJSONString(preProjectApprovalModels));
        return BaseResponse.ok(preProjectApprovalModels,Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getPreProjectApprovalById(PreProjectApprovalModel request) {
        PreProjectApprovalModel preProjectApprovalModel = baseMapper.getPreProjectApprovalById(request.getId());
        if (preProjectApprovalModel == null){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        return BaseResponse.ok(preProjectApprovalModel);
    }

    @Override
    public BaseResponse approvalData(PreProjectApprovalModel request) {
        PreProjectApproval preProjectApproval = baseMapper.selectById(request.getId());
        if (preProjectApproval == null){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        preProjectApproval.setApprovalProjectState(request.getApprovalProjectState());
        preProjectApproval.setApprovalProjectScore(request.getApprovalProjectScore());
        baseMapper.updateById(preProjectApproval);
        List<String> fileRelationIds = new ArrayList<>();
        fileRelationIds.add(request.getFileRelationId());
        fileRelationIds.add(request.getApprovalProjectFile());
        fileRelationIds.add(request.getApprovalProjectScoreFile());
        sysFileAnnexService.refreshRelationFile(fileRelationIds,Collections.singletonList(request.getFileIds()),preProjectApproval.getId());
        return BaseResponse.ok();
    }

}
