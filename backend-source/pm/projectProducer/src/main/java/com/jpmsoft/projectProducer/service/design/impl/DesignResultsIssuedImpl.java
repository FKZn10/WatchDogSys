package com.jpmsoft.projectProducer.service.design.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.Design.DesignResultsIssuedRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignIssuedDetailModel;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignQrCodeModel;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignResultsIssuedModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.design.DesignResultsIssuedMapper;
import com.jpmsoft.projectProducer.pojo.dto.system.SelectDto;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignIssued;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignQrCode;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignResultsIssued;
import com.jpmsoft.projectProducer.service.design.IDesignIssuedDetailService;
import com.jpmsoft.projectProducer.service.design.IDesignIssuedService;
import com.jpmsoft.projectProducer.service.design.IDesignQrCodeService;
import com.jpmsoft.projectProducer.service.design.IDesignResultsIssuedService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DesignResultsIssuedImpl extends ServiceImpl<DesignResultsIssuedMapper, DesignResultsIssued> implements IDesignResultsIssuedService {

    @Resource
    private IDesignIssuedDetailService designIssuedDetailService;

    @Resource
    private IDesignIssuedService designIssuedService;

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysUserService sysUserService;

    @Resource
    private UserProducerFeign userProducerFeign;

    @Resource
    private IDesignQrCodeService designQrCodeService;

    @Resource
    private RedisUtils redisUtils;


    @Override
    public BaseResponse createOrupdateDesignResultsIssued(DesignResultsIssuedModel request, String token) {
        DesignResultsIssued drl = new DesignResultsIssued();
        BeanUtils.copyProperties(request, drl);
        List<DesignIssuedDetailModel> filteredList = new ArrayList<>();
        if (ValidateUtil.isNotNull(request.getDriPaperNum()) && !CollectionUtils.isEmpty(request.getDesignIssuedDetails())) {
            int sumOfDidPaperNum = request.getDesignIssuedDetails().stream()
                    .mapToInt(detail -> ValidateUtil.isNotNull(detail.getDidPaperNum()) ? detail.getDidPaperNum() : 0)
                    .sum();
            if (request.getDriPaperNum() < sumOfDidPaperNum) {
                return BaseResponse.fail(ResMsg.DriPaPerNumNotEnough);
            }
        }
        if (ValidateUtil.isEmpty(request.getId())) {
            drl.setCreateTime(new Date());
            drl.setId(GenerateUUID.getUUID());
            drl.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(drl);
            if ("01".equals(request.getDesignIsIssued())) {
                designIssuedService.createOrUpdateDesignIssued(request, drl.getId(), token);
            }
            if ("03".equals(request.getDiIsIssued())) {
                if (ValidateUtil.isNotNull(request.getDesignIssuedDetails())) {
                    filteredList = request.getDesignIssuedDetails().stream()
                            .filter(detail -> "03".equals(detail.getDidIsIssuedTask()))
                            .collect(Collectors.toList());
                }
                request.setDesignIssuedDetails(filteredList);
                if (!filteredList.isEmpty()) {
                    designIssuedService.createOrUpdateDesignIssued(request, drl.getId(), token);
                }
            }
            sysFileService.saveFile(drl.getId(), request.gettId(), drl.getState() + "", drl.getCreateUid(), drl.getCreateUname()
                    , drl.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId()), drl.getId());
            return BaseResponse.ok(drl.getId());
        } else if ("2".equals(request.getDriIsChange()) && "02".equals(request.getDriFormType()) && ValidateUtil.isEmpty(request.getDriParentId())) {
            DesignResultsIssued entity = baseMapper.selectById(request.getId());
            if (entity == null) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            if (entity.getDriPaperNum() > request.getDriPaperNum()){
                // 纸质份数不符合要求
                return BaseResponse.fail(ResMsg.DriPaPerNumNotEnough);
            }
            // 需要修改的字段
            entity.setDriPaperNum(request.getDriPaperNum());
            entity.setDriIssuedDescribe(request.getDriIssuedDescribe());
            entity.setDriParentId(request.getId());
            entity.setDriIsChange(request.getDriIsChange());
            entity.setDesignIsIssued(request.getDesignIsIssued());

            entity.setId(GenerateUUID.getUUID());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            entity.setDriChangeUid(redisUtils.getUid(token));
            baseMapper.insert(entity);
            if ("01".equals(request.getDesignIsIssued()) && !CollectionUtils.isEmpty(request.getDesignIssuedDetails())) {
                designIssuedService.createOrUpdateDesignIssued(request, drl.getId(), token);
            }
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Collections.singletonList(request.getFileRelationId()), drl.getId());
            return BaseResponse.ok(entity.getId());
        } else {
            DesignResultsIssued entity = baseMapper.selectById(request.getId());
            if (entity == null) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            drl.setCreateTime(entity.getCreateTime());
            drl.setStateTime(entity.getStateTime());
            drl.setCreateUid(entity.getCreateUid());
            if (ValidateUtil.isEmpty(request.getDesignIsIssued())) {
                drl.setDesignIsIssued(entity.getDesignIsIssued());
            }
            baseMapper.updateById(drl);
            if ("01".equals(request.getDesignIsIssued())) {
                designIssuedService.createOrUpdateDesignIssued(request, drl.getId(), token);
            }
            if ("03".equals(request.getDiIsIssued())) {
                if (ValidateUtil.isNotNull(request.getDesignIssuedDetails())) {
                    filteredList = request.getDesignIssuedDetails().stream()
                            .filter(detail -> "03".equals(detail.getDidIsIssuedTask()))
                            .collect(Collectors.toList());
                }
                request.setDesignIssuedDetails(filteredList);
                if (!filteredList.isEmpty()) {
                    designIssuedService.createOrUpdateDesignIssued(request, drl.getId(), token);
                }
            }
            sysFileService.saveFile(drl.getId(), request.gettId(), drl.getState() + "", drl.getCreateUid(), drl.getCreateUname(),
                    drl.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Collections.singletonList(request.getFileRelationId())
                    , Collections.singletonList(request.getFileIds()), drl.getId());
            return BaseResponse.ok(drl.getId());
        }
    }


    @Override
    public BaseResponse getDesignResultsIssuedList(DesignResultsIssuedRequest request, String uId) {
        return this.getDesignResultsIssuedListByIds(request, uId, null);
    }


    @Override
    public BaseResponse getDesignResultsIssuedClassInfo(DesignResultsIssuedRequest request, String uId) {
        return this.getDesignResultsIssuedClassInfoByIds(request, uId, null);
    }

    @Override
    public BaseResponse getDesignResultsIssuedById(String id, String uid) {
        String authSql = buildNewAuthSql(uid);
        log.info("-------------------------------------------------------------------");
        log.info("鉴权sql ：  " + authSql);
        log.info("-------------------------------------------------------------------");
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        DesignResultsIssuedModel data = baseMapper.getDesignResultsIssuedById(id, authSql);
        if (data == null) {
            // 如果没有数据，返回空对象
            data = new DesignResultsIssuedModel();
        }
        List<DesignIssued> designIssuedList = designIssuedService.list(
                new LambdaQueryWrapper<DesignIssued>()
                        .eq(DesignIssued::getDiEntityId, id)
        );
        boolean hasIssuedTask03 = designIssuedList.stream()
                .anyMatch(issued -> "03".equals(issued.getDiIsIssued()));
        if (ValidateUtil.isNotEmpty(data.getDesignIsIssued())) {
            if ("01".equals(data.getDesignIsIssued()) || hasIssuedTask03) {
                List<DesignIssuedDetailModel> designIssuedDetailModel = designIssuedDetailService.getDesignIssueReissueDetailById(id);
                if (designIssuedDetailModel == null) {
                    designIssuedDetailModel = Collections.emptyList();
                }
                data.setDesignIssuedDetails(designIssuedDetailModel);
            } else {
                data.setDesignIssuedDetails(Collections.emptyList());
            }
        } else {
            data.setDesignIssuedDetails(Collections.emptyList());
        }
        return BaseResponse.ok(data);
    }


    /**
     * 此方法已弃用
     *
     * @param dirType
     * @param uId
     * @return
     */
    @Override
    public BaseResponse getDesignResultsLssuedTree(String dirType, String uId) {
        String authSql = buildNewAuthSql(uId);
        DesignResultsIssuedRequest request = new DesignResultsIssuedRequest();
        List<DesignResultsIssuedModel> designResultsIssuedList = baseMapper.getDesignResultsIssuedList(null, request, authSql, null);
        List<String> driTypes = designResultsIssuedList.stream()
                .map(DesignResultsIssuedModel::getDriType)
                .distinct()
                .sorted()
                .collect(Collectors.toList());
        SelectDto selectDto = new SelectDto();
        String cglx = "JPMsoft_CGLX";
        selectDto.setCodes(Arrays.asList(cglx));
        BaseResponse dictionaryTreeByCodes = userProducerFeign.getDictionaryTreeByCodes(selectDto);
        return dictionaryTreeByCodes;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse delDesignResultsIssuedById(DesignResultsIssuedModel request, String token) {
        if (ValidateUtil.isEmpty(request.getId())) {
            return BaseResponse.fail(ResMsg.notId);
        }
        LambdaQueryWrapper<DesignResultsIssued> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DesignResultsIssued::getId, request.getId());
        DesignResultsIssued entity = baseMapper.selectOne(queryWrapper);
        if (entity == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        String driFormType = entity.getDriFormType();
        if (ValidateUtil.isNotEmpty(driFormType)) {
            if (!driFormType.equals("02")) {
                String dqcId = entity.getDqcId();
                if (ValidateUtil.isNotEmpty(dqcId)) {
                    DesignQrCode dqcEntity = new DesignQrCode();
                    DesignQrCodeModel dqc = designQrCodeService.getDesignQrCodeById(dqcId, token);
                    if (dqc != null) {
                        BeanUtils.copyProperties(dqc, dqcEntity);
                        dqcEntity.setDqcUsed("02");
                        designQrCodeService.updateById(dqcEntity);
                    }
                }
            }
        }
        baseMapper.delete(queryWrapper);
        sysFileService.deleteFileById(request.getId());
        return BaseResponse.ok();
    }

    private BaseResponse getDesignResultsIssuedClassInfoByIds(DesignResultsIssuedRequest request, String uId, List<String> ids) {

        String authSql = null;
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<ClassInfoAnnotation>();
        if (ValidateUtil.isEmpty(request.getLedger()) || request.getLedger().equals("02")) {
            authSql = buildNewAuthSql(uId);
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
            classInfoAnnotationList = new ArrayList<ClassInfoAnnotation>();
            try {
                classInfoAnnotationList = ClassInfoUtils.getClassInfo(DesignResultsIssuedRequest.class.getName());
                String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);

                List<Map<String, Object>> result = baseMapper.getAllValues(selectClause, request, authSql, ids);

                classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
            } catch (Exception e) {
                log.error("getDesignResultsIssuedClassInfo error:{}", e);
                return BaseResponse.ok(classInfoAnnotationList);
            }
        } else if (request.getLedger().equals("01")) {
//            authSql = sysUserService.getAuthSql("design_results_issued", uId, null, OutUserAuthEnum.Organization.getIndex());
            authSql = sysUserService.getAuthSql("design_results_issued", uId, null, OutUserAuthEnum.Only_Inner.getIndex());
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
            try {
                classInfoAnnotationList = ClassInfoUtils.getClassInfo(DesignResultsIssuedRequest.class.getName());
                String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);

                List<Map<String, Object>> result = baseMapper.getAllValuesCJTZ(selectClause, request, authSql, ids);

                classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
            } catch (Exception e) {
                log.error("getDesignResultsIssuedClassInfo error:{}", e);
                return BaseResponse.ok(classInfoAnnotationList);
            }
        }

        return BaseResponse.ok(classInfoAnnotationList);
    }

    private BaseResponse getDesignResultsIssuedListByIds(DesignResultsIssuedRequest request, String uId, List<String> ids) {
        String authSql = null;
        List<DesignResultsIssuedModel> data = new ArrayList<>();
        Page<DesignResultsIssuedRequest> page = new Page<>(request.getPage(), request.getSize());
        if (ValidateUtil.isEmpty(request.getLedger()) || request.getLedger().equals("02")) {
            authSql = buildNewAuthSql(uId);
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
            data = baseMapper.getDesignResultsIssuedList(page, request, authSql, ids);
        } else if ("01".equals(request.getLedger())) {
            authSql = sysUserService.getAuthSql("design_results_issued", uId, null, OutUserAuthEnum.Only_Inner.getIndex());
            if (ValidateUtil.isEmpty(authSql)) {
                return BaseResponse.fail(ResMsg.noPermission);
            }
            request.setState(7);
            data = baseMapper.getDesignResultsIssuedListCJTZ(page, request, authSql, ids);
        }

        if (CollectionUtils.isEmpty(data)) {
            return BaseResponse.ok(new ArrayList<>(), 0);
        }

        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }

    // 根据补发表得id查到对应的模块数据
    @Override
    public BaseResponse getDesignIssuedInfoById(String id, String uid) {
        String authSql = buildNewAuthSql(uid);
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notId);
        }
        DesignIssued entity = designIssuedService.getById(id);
        if (entity == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (ValidateUtil.isEmpty(entity.getDiEntityId())) {
            return BaseResponse.fail(ResMsg.notId);
        }
        String entityId = entity.getDiEntityId();
        DesignResultsIssuedModel data = baseMapper.getDesignResultsIssuedById(entityId, authSql);
        if (data == null) {
            // 如果没有数据，返回空对象
            data = new DesignResultsIssuedModel();
        }
        data.setDiCompanyType(entity.getDiCompanyType());
        data.setDiIsIssued(entity.getDiIsIssued());
        if (ValidateUtil.isNotEmpty(data.getDesignIsIssued())) {
            if ("01".equals(data.getDesignIsIssued()) || "03".equals(data.getDiIsIssued())) {
                List<DesignIssuedDetailModel> designIssuedDetailModel = designIssuedDetailService.getDesignIssueReissueDetailById(entityId);
                if (designIssuedDetailModel == null) {
                    designIssuedDetailModel = Collections.emptyList();
                }
                data.setDesignIssuedDetails(designIssuedDetailModel);
            } else {
                data.setDesignIssuedDetails(Collections.emptyList());
            }
        } else {
            data.setDesignIssuedDetails(Collections.emptyList());
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getDownloadLogListForDesign(DesignResultsIssuedModel request, String uid) {
        IPage<DesignResultsIssuedModel> page = new Page<>(request.getPage(), request.getSize());
        List<DesignResultsIssuedModel> designResultsIssuedList = baseMapper.getDownloadLogListForDesign(page, request);
        return BaseResponse.ok(designResultsIssuedList, (int) page.getTotal());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse backWriteIssuedParent(DesignResultsIssuedModel request, String uId) {
        if (ValidateUtil.isEmpty(request.getId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        DesignResultsIssued designResultsIssuedSource = getById(request.getId());
        if (designResultsIssuedSource == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!ApprovalStatusEnum.Already_Over.getIndex().equals(designResultsIssuedSource.getState())) {
            return BaseResponse.ok("当前流程状态不允许此操作");
        }
        if (ValidateUtil.isEmpty(designResultsIssuedSource.getDriParentId())) {
            return BaseResponse.ok("当前流程未存在需要变更的数据");
        }
        DesignResultsIssued designResultsIssuedTarget = getById(designResultsIssuedSource.getDriParentId());
        if (designResultsIssuedTarget == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        designResultsIssuedTarget.setDriPaperNum(designResultsIssuedSource.getDriPaperNum());
        designResultsIssuedTarget.setDriIssuedDescribe(designResultsIssuedSource.getDriIssuedDescribe());
        updateById(designResultsIssuedTarget);
        baseMapper.deleteById(request.getId());
        sysFileService.deleteFileById(request.getId());
        LambdaQueryWrapper<DesignIssued> designIssuedLambdaQueryWrapper = new LambdaQueryWrapper<>();
        designIssuedLambdaQueryWrapper.eq(DesignIssued::getDiEntityId, designResultsIssuedSource.getId());
        List<DesignIssued> designIssuedList = designIssuedService.list(designIssuedLambdaQueryWrapper);
        designIssuedList.forEach(designIssued -> {
            designIssued.setDiEntityId(designResultsIssuedTarget.getId());
            designIssuedService.updateById(designIssued);
        });
        return BaseResponse.ok();
    }

    /**
     *
     * 外部用户按组织机构鉴权，下发人和补发人能看到自己参与的数据
     *
     *
     * @param uId
     * @return
     */
    public String buildNewAuthSql(String uId) {
        String authSql = sysUserService.getAuthSql("design_results_Issued",uId,"Create_Uid",OutUserAuthEnum.Organization.getIndex());
        if (ValidateUtil.isNotEmpty(authSql)) {
            authSql = authSql.replaceFirst("where","where ( ");
            authSql = authSql + " or (select count(*) from design_Issued dizb left join design_Issued_detail didzb on didzb.di_Id=dizb.id where dizb.di_entity_ID=mn.id and didzb.did_uid='"+uId+"')>0)";
            return authSql;
        }
        return null;
    }
}