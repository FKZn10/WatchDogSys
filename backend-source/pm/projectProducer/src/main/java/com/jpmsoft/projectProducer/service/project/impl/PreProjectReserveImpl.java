package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.project.PpOwnerUnitDTO;
import com.jpmsoft.common.model.request.project.PreOwnerUnitDTO;
import com.jpmsoft.common.model.request.project.PreProjectRequest;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.project.PreProjectReserveMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.PreProjectReserveDTO;
import com.jpmsoft.projectProducer.pojo.entity.BaseEntity;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectReserve;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectReserveDtl;
import com.jpmsoft.projectProducer.pojo.request.investment.PreProjectReserveRequest;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.*;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/6 14:22
 */
@Service
public class PreProjectReserveImpl extends BaseFileImpl<PreProjectReserveMapper, PreProjectReserve, PreProjectReserveDTO> implements IPreProjectReserveService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IPreProjectReserveChangeService preProjectReserveChangeService;

    @Resource
    private IPreProjectReserveDtlService preProjectReserveDtlService;

    @Resource
    private IPreOwnerUnitService preOwnerUnitService;
    @Resource
    private IPreProjectService preProjectService;
    @Resource
    private UserProducerFeign userProducerFeign;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "id", codeParam = "pprCode", method = "setPprCode", codeRule = "PreProjectReserve", beanClass = PreProjectReserveDTO.class)
    public BaseResponse createOrUpdateProjectReserve(PreProjectReserveDTO request) {
        List<PreOwnerUnitDTO> ownerUnits = request.getPreOwnerUnits();
        if (ownerUnits.size() > 2) {
            return BaseResponse.fail("业主单位不能超过2个");
        }
        PreProjectReserve entity = new PreProjectReserve();
        createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId())) {
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        } else {
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        }

        PreProjectReserveDtl reserveDtl =  preProjectReserveDtlService.getpreProjectReservePprById(entity.getId());
        PreProjectReserveDtl dtl = new PreProjectReserveDtl();
        BeanUtils.copyProperties(entity,dtl);
        dtl.setPprId(entity.getId());

        // 处理业主单位详情
        preOwnerUnitService.saveOwnerUnit(entity.getId(), ownerUnits);

        if(ValidateUtil.isNull(reserveDtl)){
            dtl.setId(GenerateUUID.getUUID());
            preProjectReserveDtlService.save(dtl);

        } else {
            if(!reserveDtl.getState().equals(7)){
                dtl.setId(reserveDtl.getId());
                preProjectReserveDtlService.updateById(dtl);
            }
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getProjectReserveList(PreProjectReserveDTO request,String uId) {
        IPage<PreProjectReserveDTO> page = new Page<>(request.getPage(), request.getSize());
        List<PreProjectReserveDTO> data = baseMapper.getProjectReserveList(page, request
                , sysUserService.getAuthSqlInnerUserDepAndOutOrg(uId,"ProjectReserve_CJ","Pre_Project_Reserve"));
        data.forEach(item -> item.setPreOwnerUnits(preOwnerUnitService.getPreOwnerUnitByPprId(item.getId())));
        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getProjectReserveById(String id) {
        PreProjectReserveDTO data = baseMapper.getProjectReserveById(id);
        if (data == null) return BaseResponse.fail(ResMsg.infoNotExist);

        List<PreOwnerUnitDTO> preOwnerUnits = preOwnerUnitService.getPreOwnerUnitByPprId(data.getId());
        data.setPreOwnerUnits(preOwnerUnits);

        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse updateReserveStatus(String id, String status, String pId) {
        PreProjectReserve entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (ValidateUtil.isNotNull(pId)) {
            PreProject project = preProjectService.getById(pId);
            if (project == null) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            if (project.getState() != 7) {
                return BaseResponse.fail(ResMsg.stateCannotAct);
            }
        }
        entity.setPprReserveStatus(status);
        baseMapper.updateById(entity);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deleteProjectReserveById(String id) {
        PreProjectReserve entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (baseMapper.projectUser(id) > 0) return BaseResponse.fail("该项目储备已被使用，不允许删除！");
        preProjectReserveChangeService.deleteByPprId(id);
        sysFileService.deleteFileById(id);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse queryReserveListClassInfo(PreProjectReserveDTO request,String uId) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(PreProjectReserveRequest.class.getName());
            String authSql=sysUserService.getAuthSqlInnerUserDepAndOutOrg(uId,"ProjectReserve_CJ","Pre_Project_Reserve");
            for (ClassInfoAnnotation classInfoAnnotation : classInfoAnnotationList) {
                if(!classInfoAnnotation.getType().equals("date")){
                    if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())){
                        if (classInfoAnnotation.getValue().equals("pprNameList")){
                            List<Object> dictionaryValues =baseMapper.getpprNameListByCode(classInfoAnnotation.getDbValue(),request,uId,authSql);
                            dictionaryValues=ClassInfoUtils.processDictionaryValues(dictionaryValues).collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(1);
                        }else if (classInfoAnnotation.getValue().equals("pprModelList")){
                            List<Object> dictionaryValues =baseMapper.getPprModelListByCode(classInfoAnnotation.getDbValue(),request,uId,authSql);
                            dictionaryValues=ClassInfoUtils.processDictionaryValues(dictionaryValues).collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(2);
                        }else if (classInfoAnnotation.getValue().equals("pprRoughList")){
                            List<Object> dictionaryValues =baseMapper.getPprRoughListByCode(classInfoAnnotation.getDbValue(),request,uId,authSql);
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct()
                                    .sorted(Comparator.comparingDouble(o -> ((Number) o).doubleValue()))
                                    .collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(3);
                        }else if (classInfoAnnotation.getValue().equals("pprReserveCpyNameList")){
                            List<Object> dictionaryValues =baseMapper.getPprReserveCpyNameListByCode(classInfoAnnotation.getDbValue(),request,uId,authSql);
                            dictionaryValues=ClassInfoUtils.processDictionaryValues(dictionaryValues).collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(4);
                        }else if (classInfoAnnotation.getValue().equals("pprManagerUnitNameList")){
                            List<Object> dictionaryValues =baseMapper.gePprManagerUnitNameListByCode(classInfoAnnotation.getDbValue(),request,uId,authSql);
                            dictionaryValues=ClassInfoUtils.processDictionaryValues(dictionaryValues).collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(5);
                        }else if (classInfoAnnotation.getValue().equals("pprReserveStageList")){
                            List<Object> dictionaryValues =baseMapper.getPprReserveStageListByCode(classInfoAnnotation.getDbValue(),request,uId,authSql);
                            dictionaryValues=ClassInfoUtils.processDictionaryValues(dictionaryValues).collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(6);
                        }else if (classInfoAnnotation.getValue().equals("pprReserveStatusList")){
                            List<Object> dictionaryValues =baseMapper.getPprReserveStatusListByCode(classInfoAnnotation.getDbValue(),request,uId,authSql);
                            dictionaryValues=ClassInfoUtils.processDictionaryValues(dictionaryValues).collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(7);
                        }else if (classInfoAnnotation.getValue().equals("pprPlanTimeStr")){
                            List<Object> dictionaryValues =baseMapper.getPprPlanTimeListByCode(classInfoAnnotation.getDbValue(),request,uId,authSql);
                            dictionaryValues=ClassInfoUtils.processDictionaryValues(dictionaryValues).collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(8);
                        }else if (classInfoAnnotation.getValue().equals("createUnameList")){
                            List<Object> dictionaryValues =baseMapper.getCreateUnameListByCode(classInfoAnnotation.getDbValue(),request,uId,authSql);
                            dictionaryValues=ClassInfoUtils.processDictionaryValues(dictionaryValues).collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(9);
                        }else if (classInfoAnnotation.getValue().equals("createTimeStr")){
                            List<Object> dictionaryValues =baseMapper.getCreateTimeListByCode(classInfoAnnotation.getDbValue(),request,uId,authSql);
                            dictionaryValues=ClassInfoUtils.processDictionaryValues(dictionaryValues).collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(10);
                        }else if (classInfoAnnotation.getValue().equals("state")){
                            List<Object> dictionaryValues =baseMapper.getStateByCode(classInfoAnnotation.getDbValue(),request,uId,authSql);
                            dictionaryValues=ClassInfoUtils.processDictionaryValues(dictionaryValues).collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(11);
                        }else if (classInfoAnnotation.getValue().equals("pprManagerDepNameList")){
                            List<Object> dictionaryValues =baseMapper.getPprManagerDepNameListByCode(classInfoAnnotation.getDbValue(),request,uId,authSql);
                            dictionaryValues=ClassInfoUtils.processDictionaryValues(dictionaryValues).collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(11);
                        }else{
                            List<Object> dictionaryValues =baseMapper.getValueByCode(classInfoAnnotation.getDbValue(),request,uId,authSql);
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(12);

                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("queryReserveListClassInfo error:{}",e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        classInfoAnnotationList=classInfoAnnotationList.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());

        return BaseResponse.ok(classInfoAnnotationList);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncProjectReserveToProject(String id, String token) {
        PreProjectReserveDTO entity = baseMapper.getProjectReserveById(id);
        if (entity == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (entity.getState() == null || !JudgeUtil.isApprovedState(entity.getState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        PreProject project = preProjectService.getOne(Wrappers.<PreProject>lambdaQuery().eq(PreProject::getPprId, entity.getId()));

        PreProjectRequest preProject = new PreProjectRequest();
        if (ValidateUtil.isNotNull(project)) {
            BeanUtils.copyProperties(project, preProject);
            preProject.setpId(project.getpId());
        }
        preProject.setPprId(entity.getId());
        preProject.setpName(entity.getPprName());
        preProject.setpProjectManager(entity.getPprProjectManager());
        preProject.setpProjectLeader(entity.getPprProjectLeader());
        preProject.setpEngineer(entity.getPprEngineer());
        preProject.setpEngineerLeader(entity.getPprEngineerLeader());
        if (ValidateUtil.isNotEmpty(entity.getPprCompose())) {
            preProject.setpInvestmentConstitute("[" + entity.getPprCompose() + "]");
        }
        if (ValidateUtil.isNotEmpty(entity.getPprShape())) {
            preProject.setpBuildType("[" + entity.getPprShape() + "]");
        }
        preProject.setpRegion(entity.getPprRegion());
        preProject.setpCategory(entity.getPprType());
        String pprModel = entity.getPprModel();
        preProject.setpBuildModel(pprModel);
        if ("007".equals(pprModel)) {
            List<PreOwnerUnitDTO> preOwnerUnits = preOwnerUnitService.getPreOwnerUnitByPprId(entity.getId());
            preProject.setPpOwnerUnits(preOwnerUnits.stream().map(ownerUnit -> {
                PpOwnerUnitDTO ppOwnerUnitDTO = new PpOwnerUnitDTO();
                BeanUtils.copyProperties(ownerUnit, ppOwnerUnitDTO);
                ppOwnerUnitDTO.setId(GenerateUUID.getUUID());
                return ppOwnerUnitDTO;
            }).collect(Collectors.toList()));
        }
        if ("099".equals(pprModel)) {
            preProject.setpBuildModelRemark(entity.getPprOthers());
        }
        preProject.setpProfessionalType(entity.getPprClassify());
        preProject.setpOverview(entity.getPprOverview());
        LogUserInfoVo userInfoVo = redisUtils.getUserInfoByToken(token);
        if (ValidateUtil.isNull(project)) {
            preProject.setpImpState("03");
            preProject.setState(ApprovalStatusEnum.Not_Started.getIndex());
            preProject.setStateTime(new Date());
            preProject.setpCreateTime(new Date());
            preProject.setpCreateUserId(userInfoVo.getId());
            preProject.setpCreateUserName(userInfoVo.getName());
        }

        // 处理所属分支机构、事业部
        preProject.setpCompetentDepartment(entity.getPprManagerUnit());
        preProject.setpDepartment(entity.getPprManagerDep());

        // 储备编码
        preProject.setpCode(entity.getPprCode());

        //文档模板相关
        preProject.setFileRelationId(GenerateUUID.getUUID());
        FileTemplateTree projectInfo = userProducerFeign.getFileTemplateByCode("ProjectInfo");
        preProject.settCode(projectInfo.gettCode());
        preProject.setFileCode(projectInfo.gettCode());
        preProject.setFileTitle(projectInfo.gettName());
        preProject.settId(projectInfo.gettId());

        BaseResponse orUpdatePreProject = preProjectService.createOrUpdatePreProject(preProject, userInfoVo.getId(), true);
        if (orUpdatePreProject.getSuccess()) {
            String pId = orUpdatePreProject.getData().toString();
            preProjectService.projectApproved2ImpTempDepartment(pId, true);
            sysFileService.update(Wrappers.<SysFile>lambdaUpdate().set(SysFile::getFileproject, pId).eq(SysFile::getFileproject, entity.getId()));
        }
        return orUpdatePreProject;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse changeReserveCodeToProjectCode(String pId, String token) {
        if (ValidateUtil.isEmpty(pId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        PreProject project = preProjectService.getById(pId);
        // if (!ApprovalStatusEnum.Already_Over.getIndex().equals(project.getState())) {
        //     return BaseResponse.fail(ResMsg.stateCannotAct);
        // }
        String pCode = project.getpCode();
        if (!pCode.startsWith("CB-") || ValidateUtil.isEmpty(project.getPprId())) {
            return BaseResponse.ok();
        }
        String codeRule = "Project";
        if (apiUtilsService.sequenceRestEnable(codeRule)) {
            String code = apiUtilsService.generateCodeByCustomized(codeRule, project.getpId(), null, null, project);
            if (ValidateUtil.isEmpty(code)) {
                return BaseResponse.fail("编号生成失败");
            }

            QueryWrapper<PreProject> wrapper = new QueryWrapper<>();
            wrapper.eq("P_Code", code);
            if (!ValidateUtil.isEmpty(pId)) {
                wrapper.ne("P_Id", pId);
            }
            wrapper.eq("deleted", Boolean.FALSE);
            if (preProjectService.count(wrapper) > 0) {
                return BaseResponse.fail(ResMsg.codeIsRepeat);
            }

            apiUtilsService.refershAutoNoByCustomized(codeRule, project.getpId(), null, null, project);
            project.setpCode(code);
        }
        LogUserInfoVo userInfoVo = redisUtils.getUserInfoByToken(token);
        project.setpCreateUserId(userInfoVo.getId());
        project.setpCreateUserName(userInfoVo.getName());
        project.setpCreateTime(new Date());
        preProjectService.updateById(project);
        return BaseResponse.ok(project.getpCode());
    }

    /**
     * 在建设模式变更时刷新项目储备编码
     *
     * @param id
     * @param oldPprModel
     * @return
     */
    @Override
    public BaseResponse refreshProjectReserveCode(String id, String oldPprModel) {
        if (ValidateUtil.isAnObjEmpty(id, oldPprModel)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        PreProjectReserve projectReserve = getById(id);
        if (ValidateUtil.isNull(projectReserve)) {
            return BaseResponse.fail(ResMsg.noData);
        }
        // 建设模式相同，不刷新
        if (projectReserve.getPprModel().equalsIgnoreCase(oldPprModel)) {
            return BaseResponse.ok();
        }
        String codeRule = "PreProjectReserve";
        if (apiUtilsService.sequenceRestEnable(codeRule)) {
            String code = apiUtilsService.generateCodeByCustomized(codeRule, projectReserve.getId(), null, null, projectReserve);
            if (ValidateUtil.isEmpty(code)) {
                return BaseResponse.fail("编号生成失败");
            }

            LambdaQueryWrapper<PreProjectReserve> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(PreProjectReserve::getPprCode, code);
            if (!ValidateUtil.isEmpty(id)) {
                wrapper.ne(BaseEntity::getId, id);
            }
            if (this.count(wrapper) > 0) {
                return BaseResponse.fail(ResMsg.codeIsRepeat);
            }

            apiUtilsService.refershAutoNoByCustomized(codeRule, projectReserve.getId(), null, null, projectReserve);
            projectReserve.setPprCode(code);
            this.updateById(projectReserve);
        }
        return BaseResponse.ok();
    }
}
