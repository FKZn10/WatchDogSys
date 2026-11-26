package com.jpmsoft.projectProducer.service.subcontract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.Subcontract.SubcontractorRecordRequest;
import com.jpmsoft.common.model.response.Subcontract.SubcontractorRecordResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.subcontract.SubcontractorRecordMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysCompany;
import com.jpmsoft.projectProducer.pojo.entity.subcontract.SubcontractorRecord;
import com.jpmsoft.projectProducer.service.other.ISysCompanyService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.subcontract.ISubcontractorRecordService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class SubcontractorRecordImpl extends ServiceImpl<SubcontractorRecordMapper, SubcontractorRecord> implements ISubcontractorRecordService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysCompanyService sysCompanyService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSubcontractorRecord(SubcontractorRecordRequest request) {
        SubcontractorRecord entity = new SubcontractorRecord();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getSrId())) {
            entity.setSrId(GenerateUUID.getUUID());
            entity.setSrCreateTime(new Date());
            entity.setSrStateTime(null);
            baseMapper.insert(entity);
            sysFileService.saveFile(entity.getSrId(), request.gettId(), entity.getSrState() + "", entity.getSrCreateUid(), entity.getSrCreateUname()
                    , entity.getSrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSrBusinessLicense(), request.getSrIdcardScan()
                    , request.getSrAchievementProve(), request.getSrQualificationCertificate()), entity.getSrId());

            return BaseResponse.ok(entity.getSrId());
        } else {
            SubcontractorRecord model = baseMapper.selectById(request.getSrId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (JudgeUtil.nonModifyState(model.getSrState()))
                return BaseResponse.fail(ResMsg.stateNotModify);
            entity.setSrCreateUid(model.getSrCreateUid());
            entity.setSrCreateTime(model.getSrCreateTime());
            entity.setSrCreateUname(model.getSrCreateUname());
            entity.setSrState(model.getSrState());
            entity.setSrStateTime(model.getSrStateTime());
            baseMapper.updateById(entity);
            sysFileService.saveFile(entity.getSrId(), request.gettId(), entity.getSrState() + "", entity.getSrCreateUid(), entity.getSrCreateUname()
                    , entity.getSrCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "update", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getSrBusinessLicense(), request.getSrIdcardScan(), request.getSrAchievementProve(), request.getSrQualificationCertificate()),
                    Arrays.asList(request.getFileIds(), request.getSrBusinessLicenseIds(), request.getSrIdcardScanIds(), request.getSrAchievementProveIds(), request.getSrQualificationCertificateIds()), entity.getSrId());

            return BaseResponse.ok(entity.getSrId());
        }
    }

    @Override
    public BaseResponse getSubcontractorRecordList(SubcontractorRecordRequest request) {
        QueryMap<SubcontractorRecordRequest> params = new QueryMap<>(request);
        params.put("srTitle").put("srUnit").put("srType").put("srSpecialty")
                .put("srQualificationType").put("srRpName").put("srRpTel").put("srCreditCode")
                .put("srUnitName").put("srUnitIdName").put("srState").put("srUnitType");
        IPage<SubcontractorRecordResponse> page = new Page<>(request.getPage(), request.getSize());
        List<SubcontractorRecordResponse> datas = baseMapper.getSubcontractorRecordList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSubcontractorRecordBySrId(String srId) {
        return BaseResponse.ok(baseMapper.getSubcontractorRecordBySrId(srId));
    }

    @Override
    public BaseResponse checkCreditCodeIsExist(String crditCode) {
        if (sysCompanyService.havingCreditCode(crditCode))
            return BaseResponse.ok(Boolean.TRUE);
        QueryWrapper<SubcontractorRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("Sr_Credit_Code", crditCode);
        wrapper.ne("Sr_State", ApprovalStatusEnum.Invalid.getIndex());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.ok(Boolean.TRUE);
        return BaseResponse.ok(Boolean.FALSE);
    }

    @Override
    public BaseResponse syncSubcontractorRecordComapny(String srId) {
        SubcontractorRecord sr = baseMapper.selectById(srId);
        if (sr == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        SysCompany comp = sysCompanyService.getByCreditCode(sr.getSrCreditCode());
        if (comp == null) {
            comp = new SysCompany();
            comp.setcCreditcode(sr.getSrCreditCode());
            comp.setcName(sr.getSrUnitName());
            comp.setcCreatetime(new Date());
            comp.setcCreateuId(sr.getSrCreateUid());
            comp.setcCreateuName(sr.getSrCreateUname());
            comp.setcType(sr.getSrUnitType());
            comp.setcContactperson(sr.getSrRpName());
            comp.setcContactpersontel(sr.getSrRpTel());
            comp.setcBusinessLicense(sr.getSrBusinessLicense());
            comp.setcQualification(sr.getSrQualificationCertificate());
            comp.setcId(GenerateUUID.getUUID());
            comp.setcState("1");
            sysCompanyService.save(comp);
        }
        sr.setSrUnit(comp.getcId());
        baseMapper.updateById(sr);
        return BaseResponse.ok();
    }
}
