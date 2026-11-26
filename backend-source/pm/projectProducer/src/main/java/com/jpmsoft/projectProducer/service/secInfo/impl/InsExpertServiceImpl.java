package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.StorageChangeDTO;
import com.jpmsoft.common.model.StorageDetailDTO;
import com.jpmsoft.common.model.request.SecInfo.InsExpertRequest;
import com.jpmsoft.common.model.request.SecInfo.InsExpertSearchDTO;
import com.jpmsoft.common.model.request.SecInfo.JudgeExtractionDTO;
import com.jpmsoft.common.model.request.SecInfo.JudgeExtractionSearchDTO;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecExpertModel;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.common.utils.VoInfoUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.InsExpertMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.StorageChangeRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.InsExpert;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.JudgeExtraction;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.StorageChange;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.StorageChangeDetail;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.*;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @description 针对表【ins_expert】的数据库操作Service实现
 * @createDate 2025-04-24 08:49:27
 */
@Slf4j
@Service
public class InsExpertServiceImpl extends FileImpl<InsExpertMapper, InsExpert, InsExpertRequest>
        implements InsExpertService {


    @Autowired
    private ISysFileService sysFileService;

    @Autowired
    private ISecExpertService secExpertService;

    @Autowired
    private ISysFileAnnexService sysFileAnnexService;

    @Autowired
    private IApiUtilsService apiUtilsService;

    @Autowired
    private StorageChangeDetailService storageChangeDetailService;

    @Autowired
    private StorageChangeService storageChangeService;

    @Autowired
    private JudgeExtractionDetailService judgeExtractionDetailService;

    @Autowired
    private JudgeExtractionService judgeExtractionService;

    @Resource
    private InsExpertServiceImpl insExpertService;

    @Override
    public BaseResponse getInsExpertClassInfo(InsExpertSearchDTO request) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(InsExpertSearchDTO.class.getName());
            for (ClassInfoAnnotation classInfoAnnotation : classInfoAnnotationList) {
                if (!classInfoAnnotation.getType().equals("date")) {
                    List<Object> dictionaryValues = baseMapper.getInsExpertClassInfo(classInfoAnnotation.getDbValue());
                    dictionaryValues = dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                    if (classInfoAnnotation.getValue().equals("exPracticeCode")) {//工程注册类证书
                        if (!CollectionUtils.isEmpty(dictionaryValues)) {
                            List<Object> values = new ArrayList<>();
                            for (Object dictionaryValue : dictionaryValues) {
                                values.addAll(Arrays.asList(dictionaryValue.toString().split(",")));
                            }
                            classInfoAnnotation.setDictionaryValues(values.stream().distinct().collect(Collectors.toList()));
                        }
                    } else {
                        classInfoAnnotation.setDictionaryValues(dictionaryValues);
                    }
                    classInfoAnnotation.setNo(classInfoAnnotation.getNo());
                }
            }
        } catch (Exception e) {
            log.error("queryFinalAccountsClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        classInfoAnnotationList = classInfoAnnotationList.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());

        return BaseResponse.ok(classInfoAnnotationList);
    }

    /**
     * `Ex_Create_Uid` varchar(36) DEFAULT NULL,
     * `Ex_Create_Uname` varchar(50) DEFAULT NULL,
     * `Ex_Create_Time` timestamp NULL DEFAULT NULL,
     * `Ex_State` int(11) DEFAULT NULL,
     * `Ex_State_Time` timestamp NULL DEFAULT NULL,
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateInsideExpert(InsExpertRequest request) {
        InsExpert entity = new InsExpert();
        BeanUtils.copyProperties(request, entity);
        if (StringUtils.isEmpty(request.getExId()) || StringUtils.isBlank(request.getExId())) {
            entity.setExId(UUID.randomUUID().toString());
            entity.setExCreateTime(new Date());
            entity.setExIdNumberEncrypt(entity.getExIdNumber());
            entity.setExIdNumber(apiUtilsService.SelfSm2Decrypt(entity.getExIdNumber()));
            entity.setExState(ApprovalStatusEnum.Not_Started.getIndex());
            save(entity);
            sysFileService.saveFile(entity.getExId(), request.gettId(), entity.getExState() + "", entity.getExCreateUid(), entity.getExCreateUname(), entity.getExCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getExCardFile(), request.getExTitleFile(), request.getExPromiseFile(), request.getExOtherFile(), request.getFileRelationId()), entity.getExId());
            return BaseResponse.ok(entity.getExId());
        } else {
            if (!StringUtils.isEmpty(request.getExIdNumber()) && !StringUtils.isBlank(request.getExIdNumber())) {
                entity.setExIdNumberEncrypt(entity.getExIdNumber());
                entity.setExIdNumber(apiUtilsService.SelfSm2Decrypt(entity.getExIdNumber()));
            }
            saveOrUpdate(entity);
            sysFileService.saveFile(entity.getExId(), request.gettId(), entity.getExState() + "", entity.getExCreateUid(), entity.getExCreateUname(), entity.getExCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getExCardFile(), request.getExTitleFile(), request.getExPromiseFile(), request.getExOtherFile(), request.getFileRelationId()),
                    Arrays.asList(request.getExCardFileIds(), request.getExTitleFileIds(), request.getExPromiseFileIds(), request.getExOtherFileIds(), request.getFileIds()), entity.getExId());
            return BaseResponse.ok(entity.getExId());
        }
    }


    @Override
    public BaseResponse queryNotInLib() {
        List<InsExpert> list = lambdaQuery().eq(InsExpert::getInLibrary, "false").isNotNull(InsExpert::getExId).list().stream().peek(
                item -> item.setExIdNumber(VoInfoUtil.handleUserIdCard(item.getExIdNumber()))
        ).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(list))
            return BaseResponse.fail("没有未入库的内部专家");
        return BaseResponse.ok(list);
    }

    /**
     * @return
     */
    @Override
    public BaseResponse queryInLib() {
        List<InsExpert> list = lambdaQuery().eq(InsExpert::getInLibrary, "true").isNotNull(InsExpert::getExId).list().stream().peek(
                item -> item.setExIdNumber(VoInfoUtil.handleUserIdCard(item.getExIdNumber()))
        ).collect(Collectors.toList());

        if (CollectionUtils.isEmpty(list))
            return BaseResponse.fail("没有已入库的内部专家");
        return BaseResponse.ok(list);
    }

    @Override
    public BaseResponse queryNotInLibByPage(Integer page, Integer size) {
        Page<InsExpert> pageInfo = new Page<>(page, size);
        Page<InsExpert> resPage = lambdaQuery().eq(InsExpert::getInLibrary, "false").isNotNull(InsExpert::getExId).eq(InsExpert::getExState, ApprovalStatusEnum.Already_Over.getIndex()).page(pageInfo);
        List<InsExpert> records = resPage.getRecords().stream().peek(
                item -> item.setExIdNumber(VoInfoUtil.handleUserIdCard(item.getExIdNumber()))
        ).collect(Collectors.toList());
        return BaseResponse.ok(records, (int) resPage.getTotal());
    }

    @Override
    public BaseResponse queryInLibByPage(Integer page, Integer size) {
        Page<InsExpert> pageInfo = new Page<>(page, size);
        Page<InsExpert> resPage = lambdaQuery().eq(InsExpert::getInLibrary, "true").isNotNull(InsExpert::getExId).page(pageInfo);
        List<InsExpert> records = resPage.getRecords().stream().peek(
                item -> item.setExIdNumber(VoInfoUtil.handleUserIdCard(item.getExIdNumber()))
        ).collect(Collectors.toList());
        return BaseResponse.ok(records, (int) resPage.getTotal());
    }


    @Override
    public BaseResponse chooseExpert(JudgeExtractionSearchDTO request) {
        if (request.getInsideNum() == 0 && request.getOutNum() == 0) {
            return BaseResponse.fail("未指定抽取人数");
        }
        List<InsExpert> ins = new ArrayList<>();
        List<SecExpertModel> out = new ArrayList<>();
        if (request.getInsideNum() > 0) {
            LambdaQueryWrapper<InsExpert> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(InsExpert::getInLibrary, "true");
            wrapper.eq(InsExpert::getExCategory, request.getJeCategory());
            if (!CollectionUtils.isEmpty(request.getAvoidUser()))
                wrapper.notIn(InsExpert::getExId, request.getAvoidUser());
            if (ValidateUtil.isNotEmpty(request.getAvoidUnit()))
                wrapper.notIn(InsExpert::getCId, Arrays.asList(request.getAvoidUnit().split(",")));
            List<InsExpert> insExperts = insExpertService.list(wrapper);
            if (CollectionUtils.isEmpty(insExperts) || insExperts.size() < request.getInsideNum())
                return BaseResponse.fail("集团本部评委可抽取人数不足！");
            Collections.shuffle(insExperts);//随机打乱
            ins = insExperts.subList(0, request.getInsideNum());
        }
        if (request.getOutNum() > 0) {
            List<SecExpertModel> expertModels = secExpertService.getExtractExpertList(request.getAvoidUser(),
                    ValidateUtil.isNotEmpty(request.getAvoidUnit()) ? Arrays.asList(request.getAvoidUnit().split(",")) : null);
            if (CollectionUtils.isEmpty(expertModels) || expertModels.size() < request.getOutNum())
                return BaseResponse.fail("外部评委可抽取人数不足！");
            Collections.shuffle(expertModels);//随机打乱
            out = expertModels.subList(0, request.getOutNum());
        }
        // 合并结果集
        List<SecExpertModel> returnData = new ArrayList<>();
        // 添加内部专家
        if (!CollectionUtils.isEmpty(ins)) {
            for (InsExpert insExpert : ins) {
                SecExpertModel model = new SecExpertModel();
                BeanUtils.copyProperties(insExpert, model);
                model.setExIdNumber(VoInfoUtil.handleUserIdCard(insExpert.getExIdNumber()));
                model.setExtractTime(new Date());
                model.setExperType("01");
                returnData.add(model);
            }
        }
        // 添加外部专家
        if (!CollectionUtils.isEmpty(out)) {
            for (SecExpertModel secExpertModel : out) {
                SecExpertModel model = new SecExpertModel();
                BeanUtils.copyProperties(secExpertModel, model);
                model.setExCellphoneNumber(secExpertModel.getExHomePhone());
                model.setExIdNumber(VoInfoUtil.handleUserIdCard(apiUtilsService.SelfSm2Decrypt(secExpertModel.getExIdNumber())));
                model.setExtractTime(new Date());
                model.setExperType("02");
                returnData.add(model);
            }
        }
        return BaseResponse.ok(returnData);
    }

    @Override
    public BaseResponse getExpertByExId(String exId) {
        if (ValidateUtil.isEmpty(exId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        InsExpert entity = this.getById(exId);
        if (ValidateUtil.isNull(entity)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        // 身份证加密处理
        String plaintextIdNumber = apiUtilsService.SelfSm2Decrypt(entity.getExIdNumberEncrypt());
        entity.setExIdNumber(VoInfoUtil.handleUserIdCard(plaintextIdNumber));
        return BaseResponse.ok(entity);
    }

    @Override
    public BaseResponse inLibraryOrChange(StorageChangeRequest request) {
        StorageChange storageChange = new StorageChange();
        BeanUtils.copyProperties(request, storageChange);
        log.info("storage：{}", storageChange.toString());
        List<StorageChangeDetail> storageChangeDetailList = request.getStorageChangeDetailList();
        if (Objects.isNull(storageChange.getId()) || StringUtils.isEmpty(storageChange.getId())) {
            storageChange.setCreateTime(new Date());
            if (Objects.isNull(storageChange.getState())) {
                storageChange.setState(0);
            }
            storageChange.setId(UUID.randomUUID().toString());
            storageChangeDetailList.forEach(s -> {
                if (StringUtils.isEmpty(s.getId()))
                    s.setId(UUID.randomUUID().toString());
                s.setScId(storageChange.getId());
            });

            storageChangeService.save(storageChange);
            storageChangeDetailService.saveBatch(storageChangeDetailList);
            sysFileService.saveFile(storageChange.getId(), request.gettId(), storageChange.getState() + "", storageChange.getCreateUid(), storageChange.getCreateUname(), storageChange.getCreateTime(), request.getpId()
                    , request.getFileTitle(), request.getFileCode(), "create", request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(storageChange.getAttachment(), request.getFileRelationId()), storageChange.getId());
            return BaseResponse.ok(storageChange.getId());
        } else {
            QueryWrapper<StorageChangeDetail> detailQueryWrapper = new QueryWrapper<>();
            detailQueryWrapper.eq("Sc_Id", storageChange.getId());
            storageChangeDetailService.remove(detailQueryWrapper);

            storageChangeDetailList.forEach(s -> {
                if (StringUtils.isEmpty(s.getId()))
                    s.setId(UUID.randomUUID().toString());
                s.setScId(storageChange.getId());
            });
            storageChangeService.saveOrUpdate(storageChange);
            storageChangeDetailService.saveOrUpdateBatch(storageChangeDetailList);
            sysFileService.saveFile(storageChange.getId(), request.gettId(), storageChange.getState() + "", storageChange.getCreateUid(), storageChange.getCreateUname(), storageChange.getCreateTime(), request.getpId()
                    , request.getFileTitle(), request.getFileCode(), "update", request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(storageChange.getAttachment(), request.getFileRelationId()),
                    Arrays.asList(storageChange.getAttachmentIds(), request.getFileIds()), storageChange.getId());
            return BaseResponse.ok(storageChange.getId());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse rewriteInsExpert(String storageChangeId) {
        StorageChange entity = storageChangeService.getById(storageChangeId);
        if (entity.getState().equals(ApprovalStatusEnum.Already_Over.getIndex())) {
            storageChangeDetailService.lambdaQuery().eq(StorageChangeDetail::getScId, storageChangeId).list().forEach(storageChangeDetail -> {
                String exId = storageChangeDetail.getExId();
                List<InsExpert> list = lambdaQuery().eq(InsExpert::getExId, exId).list();
                if (!CollectionUtils.isEmpty(list)) {
                    InsExpert insExpert = list.get(0);
                    if (storageChangeDetail.getChangeType().equals("删除")) {
                        insExpert.setInLibrary("false");
                        saveOrUpdate(insExpert);
                    } else if (storageChangeDetail.getChangeType().equals("添加")) {
                        insExpert.setInLibrary("true");
                        insExpert.setExFinishDate(new Date());
                        saveOrUpdate(insExpert);
//                        storageChangeDetail.setChangeType("无变动");
//                        storageChangeDetailService.saveOrUpdate(storageChangeDetail);
                    }
                }
            });
        }
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse reject(String exId) {
        InsExpert entity = getById(exId);
        entity.setExState(0);
        saveOrUpdate(entity);
        sysFileService.saveFile(entity.getExId(), null, entity.getExState() + "", entity.getExCreateUid(), entity.getExCreateUname(), entity.getExCreateTime(), null, null, null, "update", null, null, null, null);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deleteExpert(String exId) {
        removeById(exId);
        sysFileService.deleteFileById(exId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse queryStorageChange(Integer page, Integer size, String uid) {
        Page<StorageChange> pageInfo = new Page<>(page, size);
        List<StorageChangeDTO> records = storageChangeService.lambdaQuery().eq(StorageChange::getScCategory, "01")
                .and(e -> e.eq(StorageChange::getCreateUid, uid)
                        .or(s -> s.ne(StorageChange::getState, ApprovalStatusEnum.Not_Started.getIndex()).ne(StorageChange::getState, ApprovalStatusEnum.Invalid.getIndex())))
                .orderByDesc(StorageChange::getCreateTime).page(pageInfo).getRecords().stream().map(
                        item -> {
                            StorageChangeDTO storageChangeDTO = new StorageChangeDTO();
                            BeanUtils.copyProperties(item, storageChangeDTO);
                            return storageChangeDTO;
                        }
                ).collect(Collectors.toList());
        return BaseResponse.ok(records, (int) pageInfo.getTotal());
    }

    @Override
    public BaseResponse queryStorageDetail(String storageId) {
        List<StorageChangeDetail> list = storageChangeDetailService.selectByScId(storageId);
        log.info("list的size:    {}", list.size());
        StorageDetailDTO storageDetailDTO = new StorageDetailDTO();
        StorageChange one = storageChangeService.lambdaQuery().eq(StorageChange::getId, storageId).one();
        BeanUtils.copyProperties(one, storageDetailDTO);
        storageDetailDTO.setStorageChangeDetailList(list);
        return BaseResponse.ok(storageDetailDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateJudgeExtraction(JudgeExtractionDTO request) {
        JudgeExtraction entity = new JudgeExtraction();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getpId())) {
            request.setpId(request.getPId());
        }
        if (StringUtils.isEmpty(request.getId()) || StringUtils.isBlank(request.getId())) {
            entity.setId(UUID.randomUUID().toString());
            entity.setCreateTime(new Date());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            judgeExtractionService.save(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                    , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(entity.getAttachment(), request.getFileRelationId()), entity.getId());
            judgeExtractionDetailService.saveInsideExpert(request.getJudgeExtractions(), entity.getId());
            return BaseResponse.ok(entity.getId());
        } else {
            judgeExtractionService.saveOrUpdate(entity);
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname(),
                    entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getAttachment(), request.getFileRelationId()), Arrays.asList(request.getAttachmentIds(), request.getFileIds()), entity.getId());
            judgeExtractionDetailService.saveInsideExpert(request.getJudgeExtractions(), entity.getId());
            return BaseResponse.ok(entity.getId());
        }

    }

    @Override
    public BaseResponse deleteJudgeExtraction(String judgeExtractionId) {
        judgeExtractionDetailService.deleteByExId(judgeExtractionId);
        judgeExtractionService.removeById(judgeExtractionId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse queryJudgeExtraction(String judgeExtractionId) {
        return BaseResponse.ok(judgeExtractionService.selectById(judgeExtractionId));
    }

    @Override
    public BaseResponse queryJudgeExtractionByPage(JudgeExtractionSearchDTO request,String uid) {
        Page<JudgeExtraction> pageInfo = new Page<>(request.getPage(), request.getSize());
        QueryWrapper<JudgeExtraction> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getJeCategory()))
            wrapper.in("Je_Category", request.getJeCategory());
        if (ValidateUtil.isNotNull(request.getPName()))
            wrapper.in("p_name", request.getPName());
        if (ValidateUtil.isNotNull(request.getSecName()))
            wrapper.in("sec_name", request.getSecName());
        if (ValidateUtil.isNotNull(request.getCName()))
            wrapper.in("c_name", request.getCName());
        if (ValidateUtil.isNotNull(request.getInsideCount()))
            wrapper.in("Inside_Count", request.getInsideCount());
        if (ValidateUtil.isNotNull(request.getOutCount()))
            wrapper.in("Out_Count", request.getOutCount());
        if (ValidateUtil.isNotNull(request.getCreateUname()))
            wrapper.in("Create_Uname", request.getCreateUname());
        if (ValidateUtil.isNotNull(request.getCreateTime()))
            wrapper.in("to_char(Create_Time,'YYYY-MM-DD')", request.getCreateTime());
        if (ValidateUtil.isNotNull(request.getState()))
            wrapper.in("state", request.getState());
        IPage<JudgeExtraction> records = judgeExtractionService.page(pageInfo, wrapper);
        return BaseResponse.ok(records.getRecords(), (int) records.getTotal());
    }


    @Override
    public BaseResponse getJudgeExtractionBySecId(String secId) {
        if(ValidateUtil.isEmpty(secId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        LambdaQueryWrapper<JudgeExtraction> secDocumentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        secDocumentLambdaQueryWrapper.eq(JudgeExtraction::getSecId, secId);
        secDocumentLambdaQueryWrapper.eq(JudgeExtraction::getState, ApprovalStatusEnum.Already_Over.getIndex());
        secDocumentLambdaQueryWrapper.orderByDesc(JudgeExtraction::getCreateTime);
        secDocumentLambdaQueryWrapper.last("LIMIT 1");
        JudgeExtraction judgeExtraction = judgeExtractionService.getOne(secDocumentLambdaQueryWrapper);
        if(ValidateUtil.isEmpty(judgeExtraction)){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        return BaseResponse.ok(judgeExtraction);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void encryptIdNumber() {
        List<InsExpert> list = this.list();
        for (InsExpert insExpert : list) {
            String encryptedIdNumber = apiUtilsService.Sm2EncryptionFront(insExpert.getExIdNumber(), null);
            insExpert.setExIdNumberEncrypt(encryptedIdNumber);
        }
        this.updateBatchById(list);
    }
}




