package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.RegisterStateEnum;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectSolutionDetailApplicationModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectSolutionDetailsModel;
import com.jpmsoft.common.model.requestAndResponse.Project.PreProjectSolutionModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.mapper.other.SysFileannexMapper;
import com.jpmsoft.projectProducer.mapper.other.SysUserMapper;
import com.jpmsoft.projectProducer.mapper.other.ZNPJIndicatorInfoTypeConfigMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectSolutionDetailsMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectSolutionInfoMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectSolutionMapper;
import com.jpmsoft.projectProducer.mapper.project.ProjectRegisterInfoMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.SupportDocumentDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.other.ZNPJIndicatorInfoTypeConfig;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectSolution;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectSolutionDetails;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectSolutionInfo;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectRegisterInfo;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.project.IPreProjectSolutionService;
import com.jpmsoft.projectProducer.utils.IdWorker;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class PreProjectSolutionServiceImpl
        extends ServiceImpl<PreProjectSolutionMapper,PreProjectSolution> implements IPreProjectSolutionService {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private ProjectRegisterInfoMapper projectRegisterInfoMapper;
    @Autowired
    private PreProjectSolutionDetailsMapper preProjectSolutionDetailsMapper;
    @Autowired
    private PreProjectSolutionInfoMapper preProjectSolutionInfoMapper;
    @Autowired
    private ZNPJIndicatorInfoTypeConfigMapper znpjIndicatorInfoTypeConfigMapper;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Autowired
    private SysFileannexMapper sysFileannexMapper;
    @Override
    public BaseResponse createOrUpdatePreProjectSolution(PreProjectSolutionModel request) {
        log.info("createOrUpdatePreProjectSolution request:{}", JacksonUtils.toJSONString(request));
        if (request == null) {
            return BaseResponse.fail("参数错误");
        }
        if (StrUtil.isEmpty(request.getProjectId())){
            return BaseResponse.fail("缺失项目");
        }
        String primaryId = request.getId();
        List<SupportDocumentDto> documentDtos = new ArrayList<>();
        SupportDocumentDto supportDocumentDto =new SupportDocumentDto();
        supportDocumentDto.setSupportDocument(request.getSolutionFileId());
        supportDocumentDto.setSupportDocumentFileIDs(request.getSolutionFileIds());
        documentDtos.add(supportDocumentDto);
        PreProjectSolution preProjectSolution ;
        if (primaryId == null){
            primaryId = IdWorker.getGenerateID();
            preProjectSolution= new PreProjectSolution();
            BeanUtils.copyProperties(request, preProjectSolution);
            preProjectSolution.setId(primaryId);
            preProjectSolution.setVersionId(GenerateUUID.getUUID());
            preProjectSolution.setLastest(1);
            preProjectSolution.setCreateTime(new Date());
            generateDetails(request, primaryId);
            saveFile(preProjectSolution, documentDtos);
            ProjectRegisterInfo preProjectRegister
                    = projectRegisterInfoMapper.selectByProjectId(request.getProjectId());
            if (preProjectRegister != null){
                preProjectSolution.setProjectRegisterInfoId(preProjectRegister.getId());
                preProjectRegister.setSolutionId(primaryId);
                projectRegisterInfoMapper.updateById(preProjectRegister);
            }
            baseMapper.insert(preProjectSolution);
        }else {
            preProjectSolution = baseMapper.selectById(primaryId);
            updateStatusByResultId(primaryId);
            BeanUtils.copyProperties(request, preProjectSolution);
            generateDetails(request, primaryId);
            saveFile(preProjectSolution, documentDtos);
            ProjectRegisterInfo preProjectRegister
                    = projectRegisterInfoMapper.selectByProjectId(request.getProjectId());
            if (preProjectRegister != null){
                preProjectSolution.setProjectRegisterInfoId(preProjectRegister.getId());
                preProjectRegister.setSolutionId(primaryId);
                projectRegisterInfoMapper.updateById(preProjectRegister);
            }
            baseMapper.updateById(preProjectSolution);
        }
        return BaseResponse.ok(primaryId);
    }
    private void saveFile(PreProjectSolution entity, List<SupportDocumentDto> documentDtos) {
        log.info("saveFile:{}",JacksonUtils.toJSONString(documentDtos));
        for (SupportDocumentDto supportDocumentDto:documentDtos){
            sysFileAnnexService.addFormCopyAndRefreshRelationFile(Collections.singletonList(supportDocumentDto.getSupportDocument()), Collections.singletonList(supportDocumentDto.getSupportDocumentFileIDs()), entity.getId());
        }
    }

    private void generateDetails(PreProjectSolutionModel request, String primaryId) {
        List<PreProjectSolutionDetails> preProjectSolutionDetails =new ArrayList<>();
        if (CollectionUtil.isNotEmpty(request.getPreProjectSolutionDetails())){
            for (PreProjectSolutionDetailsModel preProjectSolutionDetailsModel : request.getPreProjectSolutionDetails()) {
                PreProjectSolutionDetails preProjectSolutionDetail = new PreProjectSolutionDetails();
                BeanUtils.copyProperties(preProjectSolutionDetailsModel, preProjectSolutionDetail);
                preProjectSolutionDetail.setSolutionId(primaryId);
                preProjectSolutionDetail.setId(IdWorker.getGenerateID());
                preProjectSolutionDetail.setApplications(JacksonUtils.toJSONString(
                        preProjectSolutionDetailsModel.getApplicationObject()));
                preProjectSolutionDetail.setDelFlag(1);
                preProjectSolutionDetail.setStatus(1);
                preProjectSolutionDetails.add(preProjectSolutionDetail);
            }
        }
        log.info("generateDetails preProjectSolutionDetails:{}", JacksonUtils.toJSONString(preProjectSolutionDetails));
        if (CollectionUtil.isNotEmpty(preProjectSolutionDetails)){
            preProjectSolutionDetailsMapper.insertBatch(preProjectSolutionDetails);
        }
    }

    private void updateStatusByResultId(String primaryId) {
        preProjectSolutionDetailsMapper.updateStatusById(primaryId,2);
    }

    @Override
    public BaseResponse deletePreProjectSolution(PreProjectSolutionModel request) {
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getPreProjectSolutionList(PreProjectSolutionModel request, String uId) {
        LogUserInfoVo userInfoVo = redisUtils.getUserInfoByUid(uId);
        log.info("getPreProjectSolutionList request:{}", JacksonUtils.toJSONString(request));
        QueryMap<PreProjectSolutionModel> queryMap = new QueryMap<>(request);
        queryMap.put("projectName").put("projectCode").put("auditStatus");
        SysUser sysUser = sysUserMapper.selectById(uId);
        if (!"1".equals(sysUser.getUType())){
            queryMap.put("createUid", userInfoVo.getCId());
        }
        IPage<PreProjectSolutionModel> page = new Page<>(request.getPage(), request.getSize());
        List<PreProjectSolutionModel> solutionModels = baseMapper.getPreProjectSolutionList(queryMap.getQuery(), page);
        log.info("getPreProjectSolutionList response:{}", JacksonUtils.toJSONString(solutionModels));
        return BaseResponse.ok(solutionModels);
    }

    @Override
    public BaseResponse getPreProjectSolutionById(String id) {
        PreProjectSolutionModel solutionModel = baseMapper.getPreProjectSolutionById(id);
        log.info("getPreProjectSolutionById:{}", JacksonUtils.toJSONString(solutionModel));
        if (solutionModel == null){
            return BaseResponse.fail("未查询到数据");
        }
        List<PreProjectSolutionDetailsModel> solutionDetails = solutionModel.getPreProjectSolutionDetails();
        for (PreProjectSolutionDetailsModel solutionDetail : solutionDetails) {
            if (solutionDetail.getApplications() != null){
                solutionDetail.setApplicationObject(JacksonUtils.parseObject(solutionDetail.getApplications(),
                        PreProjectSolutionDetailApplicationModel.class));
            }
        }
        log.info("getPreProjectSolutionById response:{}", JacksonUtils.toJSONString(solutionModel));
        return BaseResponse.ok(solutionModel);
    }

    @Override
    public BaseResponse approvalPreProjectSolution(PreProjectSolutionModel request) {
        log.info("approvalPreProjectRegisterInfo");
        PreProjectSolution entity = baseMapper.selectById(request.getId());
        if (entity == null){
            return BaseResponse.fail("项目不存在");
        }
        entity.setAuditStatus(request.getAuditStatus());
        if (RegisterStateEnum.Rejected.getkey().equals(request.getAuditStatus())){
            if (StrUtil.isEmpty(request.getAuditComment())){
                return BaseResponse.fail("请填写审核意见");
            }
        }
        entity.setAuditComment(request.getAuditComment());
        baseMapper.updateById(entity);
        return BaseResponse.ok(entity);
    }

    @Override
    public void refreshInfo() {
        List<PreProjectSolutionInfo> preProjectSolutionInfos = preProjectSolutionInfoMapper.selectLists();
        log.info("preProjectSolutionInfos:{}", JacksonUtils.toJSONString(preProjectSolutionInfos));
        List<ZNPJIndicatorInfoTypeConfig> znpjIndicatorInfoTypeConfigs
                = znpjIndicatorInfoTypeConfigMapper.selectList(new QueryWrapper<>());
        //log.info("znpjIndicatorInfoTypeConfigs:{}", JacksonUtils.toJSONString(znpjIndicatorInfoTypeConfigs));
        if (CollectionUtil.isEmpty(preProjectSolutionInfos)){
            for (PreProjectSolutionInfo preProjectSolution : preProjectSolutionInfos) {
                ZNPJIndicatorInfoTypeConfig znpjIndicatorInfoTypeConfig
                        = znpjIndicatorInfoTypeConfigs.stream()
                        .filter(n->n.getTypeCode().equals(preProjectSolution.getInfoType()))
                        .findFirst().orElse(null);
                if (znpjIndicatorInfoTypeConfig != null){
                    preProjectSolution.setIndicatorId(znpjIndicatorInfoTypeConfig.getIndicatorId());
                    preProjectSolution.setIndicatorVersionId(znpjIndicatorInfoTypeConfig.getIndicatorVersionId());
                }
                if (preProjectSolution.getCreateUid() == null){
                    preProjectSolution.setCreateUid("68D319413F904195A859973BD4C5702D");
                }
                if (preProjectSolution.getCreateUname() == null){
                    preProjectSolution.setCreateUname("admin");
                }
                if (preProjectSolution.getStateTime() == null){
                    preProjectSolution.setStateTime(new Date());
                }
                preProjectSolution.setState(7);
                log.info("preProjectSolution:{}", JacksonUtils.toJSONString(preProjectSolution));
                preProjectSolutionInfoMapper.updateById(preProjectSolution);
                if (StrUtil.isNotEmpty(preProjectSolution.getSupportingDocument())){
                    List<SysFileannex> sysFileannexList =sysFileannexMapper.selectByRelationId(preProjectSolution.getSupportingDocument());
                    if (CollectionUtil.isNotEmpty(sysFileannexList)){
                        for (SysFileannex sysFileannex : sysFileannexList) {
                            if (sysFileannex.getUploadStatus()!=1){
                                sysFileAnnexService.refreshRelationFile(sysFileannex.getFileRelationId(), sysFileannex.getFileId(), preProjectSolution.getId());
                            }
                        }
                    }
                }
            }
        }

    }

    @Override
    public BaseResponse modifyPreProjectSolution(PreProjectSolutionModel request) {
        log.info("modifyPreProjectSolution request:{}", JacksonUtils.toJSONString(request));
        if (request == null) {
            return BaseResponse.fail("参数错误");
        }
        if (StrUtil.isEmpty(request.getProjectId())){
            return BaseResponse.fail("缺失项目");
        }
        if (StrUtil.isEmpty(request.getVersionId())){
            return BaseResponse.fail("缺失版本id");
        }
        QueryWrapper<PreProjectSolution> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("version_id", request.getVersionId());
        List<PreProjectSolution> oldSolution = baseMapper.selectList(queryWrapper);
        for (PreProjectSolution preProjectSolution : oldSolution){
            preProjectSolution.setLastest(0);
            baseMapper.updateById(preProjectSolution);
        }
        List<SupportDocumentDto> documentDtos = new ArrayList<>();
        SupportDocumentDto supportDocumentDto =new SupportDocumentDto();
        supportDocumentDto.setSupportDocument(request.getSolutionFileId());
        supportDocumentDto.setSupportDocumentFileIDs(request.getSolutionFileIds());
        documentDtos.add(supportDocumentDto);
        PreProjectSolution preProjectSolution =new PreProjectSolution();
        BeanUtils.copyProperties(request, preProjectSolution);
        String primaryId = IdWorker.getGenerateID();
        preProjectSolution.setCreateTime(new Date());
        preProjectSolution.setId(primaryId);
        preProjectSolution.setLastest(1);
        generateDetails(request, primaryId);
        saveFile(preProjectSolution, documentDtos);
        ProjectRegisterInfo preProjectRegister
                = projectRegisterInfoMapper.selectByProjectId(request.getProjectId());
        if (preProjectRegister != null){
            preProjectSolution.setProjectRegisterInfoId(preProjectRegister.getId());
            preProjectRegister.setSolutionId(primaryId);
            projectRegisterInfoMapper.updateById(preProjectRegister);
        }
        baseMapper.insert(preProjectSolution);
        return BaseResponse.ok(primaryId);
    }

    @Override
    public BaseResponse getPreProjectSolutionListModifyInfo(PreProjectSolutionModel request, String uId) {
        LogUserInfoVo userInfoVo = redisUtils.getUserInfoByUid(uId);
        if (request == null){
            return BaseResponse.fail("参数错误");
        }
        if (StrUtil.isEmpty(request.getVersionId())){
            return BaseResponse.fail("缺失版本id");
        }
        log.info("getPreProjectSolutionList request:{}", JacksonUtils.toJSONString(request));
        QueryMap<PreProjectSolutionModel> queryMap = new QueryMap<>(request);
        queryMap.put("projectName").put("projectCode").put("auditStatus").put("versionId");
        SysUser sysUser = sysUserMapper.selectById(uId);
        if (!"1".equals(sysUser.getUType())){
            queryMap.put("createUid", userInfoVo.getCId());
        }
        IPage<PreProjectSolutionModel> page = new Page<>(request.getPage(), request.getSize());
        List<PreProjectSolutionModel> solutionModels = baseMapper.getPreProjectSolutionListModifyInfo(queryMap.getQuery(), page);
        log.info("getPreProjectSolutionList response:{}", JacksonUtils.toJSONString(solutionModels));
        return BaseResponse.ok(solutionModels);
    }
}
