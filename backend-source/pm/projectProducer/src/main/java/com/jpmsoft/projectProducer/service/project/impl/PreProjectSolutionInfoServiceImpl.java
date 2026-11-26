package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectSolutionInfoModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.mapper.other.SysFileMapper;
import com.jpmsoft.projectProducer.mapper.other.SysUserMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectSolutionDetailsMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectSolutionInfoMapper;
import com.jpmsoft.projectProducer.mapper.project.ProjectRegisterInfoMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.SupportDocumentDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.other.ZNPJIndicatorInfoTypeConfig;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectSolutionInfo;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.project.IPreProjectSolutionInfoService;
import com.jpmsoft.projectProducer.utils.IdWorker;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class PreProjectSolutionInfoServiceImpl extends ServiceImpl<PreProjectSolutionInfoMapper,PreProjectSolutionInfo> implements IPreProjectSolutionInfoService {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ProjectRegisterInfoMapper projectRegisterInfoMapper;
    @Autowired
    private PreProjectSolutionDetailsMapper preProjectSolutionDetailsMapper;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Autowired
    private SysFileMapper sysFileMapper;
    @Override
    public BaseResponse createOrUpdatePreProjectSolutionInfo(PreProjectSolutionInfoModel request) {
        if (request == null) {
            return BaseResponse.fail("参数错误");
        }
        log.info("createOrUpdatePreProjectSolutionInfo request:{}", JacksonUtils.toJSONString(request));
        if (StrUtil.isEmpty(request.getProjectId())){
            return BaseResponse.fail("缺失项目");
        }
        String primaryId = request.getId();
        PreProjectSolutionInfo entity;
        List<SupportDocumentDto> documentDtos = new ArrayList<>();
        SupportDocumentDto supportDocumentDto =new SupportDocumentDto();
        supportDocumentDto.setSupportDocument(request.getSupportingDocument());
        supportDocumentDto.setSupportDocumentFileIDs(request.getSupportingDocumentIds());
        documentDtos.add(supportDocumentDto);
        if (primaryId == null){
            entity = new PreProjectSolutionInfo();
            primaryId = IdWorker.getGenerateID();
            entity.setId(primaryId);
            BeanUtils.copyProperties(request, entity);
            entity.setCreateTime(new Date());
            entity.setState(ApprovalStatusEnum.Already_Over.getIndex());
            entity.setStateTime(new Date());
            if (entity.getSum() ==null||entity.getSum().equals(BigDecimal.ZERO)){
                entity.setSum(BigDecimal.ONE);
            }
            log.info("createOrUpdatePreProjectSolutionInfo insert entity:{}", JacksonUtils.toJSONString(entity));
            saveFile(entity, documentDtos);
            baseMapper.insert(entity);
        }else {
            entity = baseMapper.selectById(primaryId);
            BeanUtils.copyProperties(request, entity);
            entity.setState(ApprovalStatusEnum.Already_Over.getIndex());
            entity.setStateTime(new Date());
            if (entity.getSum() ==null||entity.getSum().equals(BigDecimal.ZERO)){
                entity.setSum(BigDecimal.ONE);
            }
            log.info("createOrUpdatePreProjectSolutionInfo update entity:{}", JacksonUtils.toJSONString(entity));
            saveFile(entity, documentDtos);
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(primaryId);
    }
    private void saveFile(PreProjectSolutionInfo entity, List<SupportDocumentDto> documentDtos) {
        log.info("saveFile:{}",JacksonUtils.toJSONString(documentDtos));
        for (SupportDocumentDto supportDocumentDto:documentDtos){
            sysFileAnnexService.addFormCopyAndRefreshRelationFile(Collections.singletonList(supportDocumentDto.getSupportDocument()), Collections.singletonList(supportDocumentDto.getSupportDocumentFileIDs()), entity.getId());
        }
    }
    @Override
    public BaseResponse approvalPreProjectSolutionInfo(PreProjectSolutionInfoModel request) {
        log.info("approvalPreProjectSolutionInfo request:{}", JacksonUtils.toJSONString(request));
        if (request == null){
            return BaseResponse.fail("参数错误");
        }
        PreProjectSolutionInfo entity = baseMapper.selectById(request.getId());
        if (entity == null){
            return BaseResponse.fail("数据不存在");
        }
        entity.setState(request.getState());
        entity.setStateTime(new Date());
        log.info("approvalPreProjectSolutionInfo update entity:{}", JacksonUtils.toJSONString(entity));
        baseMapper.updateById(entity);
        return BaseResponse.ok(entity);
    }

    @Override
    public BaseResponse getPreProjectSolutionInfoList(PreProjectSolutionInfoModel request, String uId) {
        LogUserInfoVo userInfoVo = redisUtils.getUserInfoByUid(uId);
        log.info("getPreProjectSolutionList request:{}", JacksonUtils.toJSONString(request));
        QueryMap<PreProjectSolutionInfoModel> queryMap = new QueryMap<>(request);
        queryMap.put("projectName").put("infoType");
        SysUser sysUser = sysUserMapper.selectById(uId);
        if (!"1".equals(sysUser.getUType())){
            queryMap.put("createUid", userInfoVo.getCId());
        }
        log.info("getPreProjectSolutionList queryMap:{}", JacksonUtils.toJSONString(queryMap));
        IPage<PreProjectSolutionInfoModel> page = new Page<>(request.getPage(), request.getSize());
        List<PreProjectSolutionInfoModel> solutionInfoModels = baseMapper.getPreProjectSolutionInfoList(queryMap.getQuery(), page);
        log.info("getPreProjectSolutionList response:{}", JacksonUtils.toJSONString(solutionInfoModels));
        return BaseResponse.ok(solutionInfoModels);
    }

    @Override
    public BaseResponse getPreProjectSolutionInfoById(String id) {
        PreProjectSolutionInfoModel solutionInfoModel = baseMapper.getPreProjectSolutionInfoById(id);
        if (solutionInfoModel == null){
            return BaseResponse.fail("未查询到数据");
        }
        log.info("getPreProjectSolutionById:{}", JacksonUtils.toJSONString(solutionInfoModel));
        return BaseResponse.ok(solutionInfoModel);
    }

    @Override
    public BaseResponse createOrUpdatePreProjectSolutionInfoWithOutFlow(PreProjectSolutionInfoModel request) {
        if (request == null) {
            return BaseResponse.fail("参数错误");
        }
        log.info("createOrUpdatePreProjectSolutionInfo request:{}", JacksonUtils.toJSONString(request));
        if (StrUtil.isEmpty(request.getProjectId())){
            return BaseResponse.fail("缺失项目");
        }
        String primaryId = request.getId();
        PreProjectSolutionInfo entity;
        List<SupportDocumentDto> documentDtos = new ArrayList<>();
        SupportDocumentDto supportDocumentDto =new SupportDocumentDto();
        supportDocumentDto.setSupportDocument(request.getSupportingDocument());
        supportDocumentDto.setSupportDocumentFileIDs(request.getSupportingDocumentIds());
        documentDtos.add(supportDocumentDto);
        if (primaryId == null){
            entity = new PreProjectSolutionInfo();
            primaryId = IdWorker.getGenerateID();
            entity.setId(primaryId);
            BeanUtils.copyProperties(request, entity);
            entity.setCreateTime(new Date());
            entity.setState(ApprovalStatusEnum.Already_Over.getIndex());
            entity.setStateTime(new Date());
            if (entity.getSum() ==null||entity.getSum().equals(BigDecimal.ZERO)){
                entity.setSum(BigDecimal.ONE);
            }
            log.info("createOrUpdatePreProjectSolutionInfo insert entity:{}", JacksonUtils.toJSONString(entity));
            ZNPJIndicatorInfoTypeConfig indicatorInfoTypeConfig = preProjectSolutionDetailsMapper.getZNPJIndicatorInfoTypeConfig(entity.getInfoType());
            if (indicatorInfoTypeConfig != null){
                entity.setIndicatorId(indicatorInfoTypeConfig.getIndicatorId());
                entity.setIndicatorVersionId(indicatorInfoTypeConfig.getIndicatorVersionId());
            }
            saveFile(entity, documentDtos);
            baseMapper.insert(entity);
            SysFile sysFile = getSysFile(entity);
            sysFileMapper.insert(sysFile);
        }else {
            entity = baseMapper.selectById(primaryId);
            BeanUtils.copyProperties(request, entity);
            entity.setState(ApprovalStatusEnum.Already_Over.getIndex());
            entity.setStateTime(new Date());
            if (entity.getSum() ==null||entity.getSum().equals(BigDecimal.ZERO)){
                entity.setSum(BigDecimal.ONE);
            }
            ZNPJIndicatorInfoTypeConfig indicatorInfoTypeConfig = preProjectSolutionDetailsMapper.getZNPJIndicatorInfoTypeConfig(entity.getInfoType());
            if (indicatorInfoTypeConfig != null){
                entity.setIndicatorId(indicatorInfoTypeConfig.getIndicatorId());
                entity.setIndicatorVersionId(indicatorInfoTypeConfig.getIndicatorVersionId());
            }
            log.info("createOrUpdatePreProjectSolutionInfo update entity:{}", JacksonUtils.toJSONString(entity));
            saveFile(entity, documentDtos);
            baseMapper.updateById(entity);
            SysFile sysFile =sysFileMapper.selectByFileId(primaryId);
            if (sysFile != null){
                sysFile.settCode("SchemeInformationInput");
                sysFile.settId("cb21720776a74ac3acc0650badc31887");
                sysFile.setFilecode("SchemeInformationInput");
                sysFile.setFiletitle("方案信息录入");
                sysFile.setFileproject(entity.getProjectId());
                sysFile.setFilecreateuser(entity.getCreateUid());
                sysFile.setFilecreateusername(entity.getCreateUname());
                sysFile.setFileflowstatus(ApprovalStatusEnum.Already_Over.getIndex().toString());
            }else {
                sysFile = getSysFile(entity);
                sysFileMapper.insert(sysFile);
            }
        }
        return BaseResponse.ok(primaryId);
    }
    private static SysFile getSysFile(PreProjectSolutionInfo n) {
        SysFile sysFile = new SysFile();
        sysFile.setFileid(n.getId());
        sysFile.settCode("SchemeInformationInput");
        sysFile.settId("cb21720776a74ac3acc0650badc31887");
        sysFile.setFilecode("SchemeInformationInput");
        sysFile.setFiletitle("方案信息录入");
        sysFile.setFileproject(n.getProjectId());
        sysFile.setFilecreateuser(n.getCreateUid());
        sysFile.setFilecreateusername(n.getCreateUname());
        sysFile.setFileflowstatus(ApprovalStatusEnum.Already_Over.getIndex().toString());
        return sysFile;
    }
}
