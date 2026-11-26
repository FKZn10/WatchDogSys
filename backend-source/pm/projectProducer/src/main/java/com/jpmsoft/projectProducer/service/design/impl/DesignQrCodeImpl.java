package com.jpmsoft.projectProducer.service.design.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.request.Design.DesignQrCodeRequest;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignQrCodeModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.design.DesignQrCodeMapper;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignQrCode;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignResultsIssued;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.service.design.IDesignQrCodeService;
import com.jpmsoft.projectProducer.service.design.IDesignResultsIssuedService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@Service
public class DesignQrCodeImpl extends ServiceImpl<DesignQrCodeMapper, DesignQrCode> implements IDesignQrCodeService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysFileService sysFileService;

    @Resource
    private IDesignResultsIssuedService designResultsIssuedService;

    @Resource
    private ISysConfigService sysConfigService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "id", codeParam = "dqCode", method = "setDqCode", codeRule = "dqCode", beanClass = DesignQrCodeModel.class)
    public BaseResponse createOrupdateDesignQrCode(DesignQrCodeModel request, String token) {
        if (qrCodeIsExist(request.getDqCode(), request.getId()))
            throw new JPMException(ResMsg.codeIsRepeat);
        String dqCode = request.getDqCode();
        DesignQrCode dqc = new DesignQrCode();
        BeanUtils.copyProperties(request, dqc);
        if (ValidateUtil.isEmpty(request.getId())) {
            Date now = new Date();
            dqc.setCreateTime(now);
            dqc.setId(GenerateUUID.getUUID());
            dqc.setState(ApprovalStatusEnum.Not_Started.getIndex());
            dqc.setDqcApplyTime(now);
            baseMapper.insert(dqc);
            sysFileService.saveFile(dqc.getId(), request.gettId(), dqc.getState() + "", request.getCreateUid(), dqc.getCreateUname()
                    , dqc.getCreateTime(), request.getpId(), request.getFileTitle(), request.gettCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId()), dqc.getId());
        } else {
            DesignQrCode entity = baseMapper.selectById(request.getId());
            if (entity == null) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            dqc.setCreateTime(entity.getCreateTime());
            dqc.setCreateUname(entity.getCreateUname());
            dqc.setCreateUid(entity.getCreateUid());
            dqc.setDqcApplyTime(entity.getDqcApplyTime());
            baseMapper.updateById(dqc);
            sysFileService.saveFile(dqc.getId(), request.gettId(), dqc.getState() + "", dqc.getCreateUid(), dqc.getCreateUname(),
                    dqc.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId())
                    , Arrays.asList(request.getFileIds()), entity.getId());
        }
        return BaseResponse.ok(dqc.getId());
    }

    @Override
    public DesignQrCodeModel getDesignQrCodeById(String id, String token) {

        DesignQrCodeModel designQrCodeModel = baseMapper.getDesignQrCodeById(id);

        return designQrCodeModel;
    }

    @Override
    public BaseResponse deleteDesignQrCodeById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notId);
        }
        DesignQrCode designQrCode = getById(id);
        if (ValidateUtil.isEmpty(designQrCode.getDqcUsed()) && designQrCode.getDqcUsed().equals("01")) {
            return BaseResponse.fail("二维码已使用");
        }
 /*       DesignQrCode entity = baseMapper.selectById(id);
        LambdaQueryWrapper<DesignResultsIssued> riQueryWrapper = new LambdaQueryWrapper<>();
        riQueryWrapper.in(DesignResultsIssued::getDqcId, id);
        if (designResultsIssuedService.count(riQueryWrapper) > 0) {
            return BaseResponse.ok(entity);
        }*/
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse checkDqcuserd(DesignQrCodeModel request, String token) {
        if (ValidateUtil.isEmpty(request.getId()) && ValidateUtil.isEmpty(request.getDriId())) {
            return BaseResponse.fail(ResMsg.notId);
        }

        DesignQrCode entity = baseMapper.selectById(request.getId());
        if (entity == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        if (request.getDqcUsed() == null) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        entity.setDqcUsed(request.getDqcUsed());
        baseMapper.updateById(entity);

        DesignResultsIssued designResultsIssued = designResultsIssuedService.getById(request.getDriId());

        if (designResultsIssued == null) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }

        if (ValidateUtil.isNotEmpty(designResultsIssued.getDqcId())) {
            LambdaQueryWrapper<DesignQrCode> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(DesignQrCode::getId, designResultsIssued.getDqcId());
            DesignQrCode dqc = baseMapper.selectOne(queryWrapper);
            if (dqc != null) {
                dqc.setDqcUsed("02");
                baseMapper.updateById(dqc);
            }
        }

        if (request.getDqcUsed().equals("02")) {
            designResultsIssued.setDqcId(null);
        } else {
            designResultsIssued.setDqcId(request.getId());
        }

        // 加强判断
        if (ValidateUtil.isEmpty(designResultsIssued.getId())) {
            return BaseResponse.fail("无法更新，缺少主键 ID");
        }
        DesignResultsIssued updateResult = new DesignResultsIssued();
        BeanUtils.copyProperties(designResultsIssued, updateResult);
        boolean result = designResultsIssuedService.updateById(updateResult);
        if (!result) {
            return BaseResponse.fail("更新失败，请重试");
        }

        return BaseResponse.ok(entity.getId());
    }


    @Override
    public BaseResponse getDesignQrCodeList(DesignQrCodeRequest request, String uId) {
        return this.getDesignQrCodeListByIds(request, uId, null);
    }


    @Override
    public BaseResponse getDesignQrCodeClassInfo(DesignQrCodeRequest request, String uId) {
        return this.getDesignQrCodeClassInfoByIds(request, uId, null);
    }

    @Override
    public BaseResponse getDesignQrCodeDateById(String id, String uId) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notId);
        }

        LambdaQueryWrapper<DesignResultsIssued> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DesignResultsIssued::getDqcId, id);
        List<DesignResultsIssued> designResultsIssuedlist = designResultsIssuedService.list(queryWrapper);

        if (designResultsIssuedlist == null || designResultsIssuedlist.isEmpty()) {
            return BaseResponse.fail("二维码未关联业务数据");
        }

        DesignResultsIssued designResultsIssued = designResultsIssuedlist.get(0);
        if (ValidateUtil.isEmpty(designResultsIssued.getId())) {
            return BaseResponse.fail("未找到对应的业务数据");
        }
        SysFile sysFile = sysFileService.getById(designResultsIssued.getId());

        if (sysFile == null) {
            return BaseResponse.fail("未找到对应的文件信息");
        }

        Map<String, String> map = new HashMap<>();
        map.put("entityID", designResultsIssued.getId());
        map.put("tCode", sysFile.gettCode());

        return BaseResponse.ok(map);
    }


    private BaseResponse getDesignQrCodeListByIds(DesignQrCodeRequest request, String uId, List<String> ids) {

        Page<DesignQrCodeRequest> page = new Page<>(request.getPage(), request.getSize());


        String authSql = null;
        List<DesignQrCodeModel> data = baseMapper.getDesignQrCodeList(page, request, authSql, ids);

        if (data == null || CollectionUtils.isEmpty(data)) {
            return BaseResponse.ok(new ArrayList<>(), 0);
        }
        return BaseResponse.ok(data.stream()
                        .sorted(Comparator.comparing(
                                DesignQrCodeModel::getCreateTime,
                                Comparator.nullsFirst(Comparator.naturalOrder())
                        ))
                        .collect(Collectors.toList()),
                Long.valueOf(page.getTotal()).intValue());
    }


    private BaseResponse getDesignQrCodeClassInfoByIds(DesignQrCodeRequest request, String uId, List<String> ids) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<ClassInfoAnnotation>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(DesignQrCodeRequest.class.getName());
            String authSql = null;
            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);

            List<Map<String, Object>> result = baseMapper.getAllValues(selectClause, request, authSql, ids);
            classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getDesignQrCodeClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        return BaseResponse.ok(classInfoAnnotationList);
    }


    private final ReentrantLock lock = new ReentrantLock();

    private String generateDqCode() {
        lock.lock();
        try {
            String datePrefix = new SimpleDateFormat("yyyyMMdd").format(new Date());
            DesignQrCode latestRecord = baseMapper.findLatestByDate(datePrefix);
            int nextSeq = 1;

            if (latestRecord != null && latestRecord.getDqCode() != null && latestRecord.getDqCode().startsWith(datePrefix)) {
                try {
                    String seqStr = latestRecord.getDqCode().substring(datePrefix.length() + 1);
                    nextSeq = Integer.parseInt(seqStr) + 1;
                } catch (NumberFormatException e) {
                    nextSeq = 1;
                }
            }

            return String.format("%s-%04d", datePrefix, nextSeq);
        } finally {
            lock.unlock();
        }
    }


    private Boolean qrCodeIsExist(String dqCode, String id) {
        QueryWrapper<DesignQrCode> wrapper = new QueryWrapper<>();
        wrapper.eq("dq_Code", dqCode);
        if (ValidateUtil.isNotEmpty(id)) wrapper.ne("id", id);
        return baseMapper.selectCount(wrapper) > 0;
    }
}