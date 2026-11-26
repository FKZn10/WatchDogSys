package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.feign.FileFeign;
import com.jpmsoft.projectProducer.mapper.contract.ConMeasureRecordMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMearDeclareDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMearUpperAmtDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConMeasureRecordDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasureRecord;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasureRecordCheck;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConMeasureRecordVo;
import com.jpmsoft.projectProducer.service.contract.IConMeasureRecordCheckService;
import com.jpmsoft.projectProducer.service.contract.IConMeasureRecordService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.MathUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 李文涛 aaron
 * @date 2022/07/13 08:29
 */
@Service
public class ConMeasureRecordImpl extends ServiceImpl<ConMeasureRecordMapper, ConMeasureRecord> implements IConMeasureRecordService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IConMeasureRecordCheckService conMeasureRecordCheckService;
    @Resource
    private FileFeign fileFeign;


    @Override
    public void createOrUpdateConMeasureRecord(List<ConMeasureRecordDto> datas, String meaId) {
        List<ConMeasureRecord> olds = getConMeasureRecordByMeaId(meaId);
        if (ValidateUtil.isNotEmpty(datas)) {
            List<String> oldIds = olds.stream().map(ConMeasureRecord::getId).collect(Collectors.toList());
            Date now = new Date();
            //region 删除数据
            List<String> dels = oldIds.stream().filter(n -> !datas.stream().map(ConMeasureRecordDto::getId).collect(Collectors.toList()).contains(n)).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(dels)) {
                conMeasureRecordCheckService.deleteConMeasureRecordCheckByMearIds(dels);
                baseMapper.deleteBatchIds(dels);
                sysFileService.deleteByFileIds(dels);
            }
            //endregion

            //region 新增数据
            List<ConMeasureRecordDto> adds = datas.stream().filter(n -> ValidateUtil.isEmpty(n.getId()) || !oldIds.contains(n.getId())).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(adds)) {
                List<ConMeasureRecord> addRecords = new ArrayList<>();
                List<SysFile> files = new ArrayList<>();
                for (ConMeasureRecordDto add : adds) {
                    ConMeasureRecord record = new ConMeasureRecord();
                    BeanUtils.copyProperties(add, record);
                    record.setId(GenerateUUID.getUUID());
                    record.setCreateTime(now);
                    record.setStateTime(now);
                    record.setMeaId(meaId);
                    addRecords.add(record);
                    record.setMearFinalAmount(record.getMearDeclareAmount());
                    sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(add.getFileRelationId(), add.getMearDraft(), add.getMearDraftSupervisor()), add.getId());
                    files.add(setFile(add, record, now));
                }
                saveBatch(addRecords);
                sysFileService.saveBatch(files);
            }
            //endregion

            //region 更新数据
            List<ConMeasureRecordDto> updates = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getId()) && (ValidateUtil.isNotEmpty(oldIds) && oldIds.contains(n.getId()))).collect(Collectors.toList());
            if (ValidateUtil.isNotEmpty(updates)) {
                List<ConMeasureRecord> upRecords = new ArrayList<>();
                List<SysFile> files = new ArrayList<>();
                for (ConMeasureRecordDto update : updates) {
                    ConMeasureRecord old = olds.stream().filter(n -> n.getId().equals(update.getId())).findFirst().get();
                    ConMeasureRecord record = new ConMeasureRecord();
                    BeanUtils.copyProperties(update, record);
                    record.setCreateUid(old.getCreateUid());
                    record.setCreateUname(old.getCreateUname());
                    record.setCreateTime(old.getCreateTime());
                    upRecords.add(record);
                    sysFileAnnexService.refreshRelationFile(Arrays.asList(update.getFileRelationId(), update.getMearDraft(), update.getMearDraftSupervisor()), Arrays.asList(update.getFileIds(), update.getMearDraftIds(), update.getMearDraftSupervisorIds()), update.getId());
                    files.add(setFile(update, record, now));
                }
                updateBatchById(upRecords);
                sysFileService.updateBatchById(files);
            }
            //endregion
        }
    }

    @Override
    public BaseResponse webAutoSaveConMeasureRecord(ConMeasureRecordDto request, String token) {
        ConMeasureRecord entity = new ConMeasureRecord();
        BeanUtils.copyProperties(request, entity);
        ConMeasureRecord old;
        if (ValidateUtil.isNotEmpty(request.getId())) {
            old = baseMapper.selectById(request.getId());
            if (old == null && !ValidateUtil.isAnObjEmpty(request.getMeaId(), request.getBoqdId()))
                old = getConMeasureRecordByMeaBoqd(request.getMeaId(), request.getBoqdId());
        } else if (!ValidateUtil.isAnObjEmpty(request.getMeaId(), request.getBoqdId()))
            old = getConMeasureRecordByMeaBoqd(request.getMeaId(), request.getBoqdId());
        else old = null;
        if (old != null) {
            if (entity.getMearDeclareAmount() == null || entity.getMearDeclareAmount().compareTo(0f) == 0) {
                conMeasureRecordCheckService.deleteConMeasureRecordCheckByMearIds(Arrays.asList(old.getId()));
                baseMapper.deleteById(old.getId());
                sysFileService.deleteFileById(old.getId());
                fileFeign.backDelFileByRelations(old.getMeaId(), token, Arrays.asList(request.getFileRelationId(), request.getMearDraft(), request.getMearDraftSupervisor()));
            } else {
                entity.setId(old.getId());
                entity.setCreateUid(old.getCreateUid());
                entity.setCreateUname(old.getCreateUname());
                entity.setCreateTime(old.getCreateTime());
                baseMapper.updateById(entity);
                sysFileService.updateById(setFile(request, entity, new Date()));
                sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getMearDraft(), request.getMearDraftSupervisor()), Arrays.asList(request.getFileIds(), request.getMearDraftIds(), request.getMearDraftSupervisorIds()), entity.getId());
            }
        } else {
            if (entity.getMearDeclareAmount() == null || entity.getMearDeclareAmount().compareTo(0f) == 0)
                return BaseResponse.ok();
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            baseMapper.insert(entity);
            sysFileService.save(setFile(request, entity, new Date()));
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getMearDraft(), request.getMearDraftSupervisor()), entity.getId());
        }
        return BaseResponse.ok(entity.getId());
    }


    @Override
    public void updateFinalAmount(ConMeasureRecordCheck check) {
        ConMeasureRecord cmr = baseMapper.selectById(check.getMearId());
        if (ValidateUtil.isNull(cmr)) return;
        //单价 （审核合价/审核后数量）
        BigDecimal money = check.getMearcExamineTotal().divide(new BigDecimal(check.getMearcAmount().toString()), 2, RoundingMode.HALF_UP);
        //更新后的最终审核合量
        BigDecimal decAmt = new BigDecimal(check.getMearcAmount().toString());
        cmr.setMearFinalAmount(check.getMearcAmount());
        cmr.setMearFinalTotal(money.multiply(decAmt));
        cmr.setMearCumulativeAmount(MathUtil.floatAddFloat(cmr.getMearUpperAmount(), check.getMearcAmount()));
        cmr.setMearCumulativeTotal(MathUtil.decAdd(cmr.getMearUpperTotal(), cmr.getMearFinalTotal()));
        cmr.setState(ApprovalStatusEnum.Already_Over.getIndex());
        baseMapper.updateById(cmr);
        sysFileService.onlyUpdateState(ApprovalStatusEnum.Already_Over.getIndex(), cmr.getId());
    }


    @Override
    public ConMearUpperAmtDto getConMearUpperAmt(String conId, String boqdCode) {
        ConMearUpperAmtDto data = baseMapper.getConMearUpperAmt(conId, boqdCode);
        if (ValidateUtil.isNull(data)) data = new ConMearUpperAmtDto();
        return data;
    }

    @Override
    public List<ConMeasureRecordVo> getAllConMearByConIdAndCode(String conId, String boqdCode) {
        return baseMapper.getAllConMearByConIdAndCode(conId, boqdCode);
    }

    @Override
    public List<ConMearDeclareDto> getConMearDeclareByConId(String conId) {
        return baseMapper.getConMearDeclareByConId(conId);
    }

    @Override
    public void return2InitiatorRestoreAmount(String meaId) {
        baseMapper.return2InitiatorRestoreAmount(meaId);
        sysFileService.onlyUpdateState(ApprovalStatusEnum.Not_Started.getIndex(), baseMapper.getAllIdByMeaId(meaId));
    }

    @Override
    public boolean can2deleteBoqd(List<String> boqdIds) {
        if (CollectionUtils.isEmpty(boqdIds)) return true;
        QueryWrapper<ConMeasureRecord> wrapper = new QueryWrapper<>();
        wrapper.ne("State", ApprovalStatusEnum.Invalid.getIndex());
        if (boqdIds.size() == 1) wrapper.eq("Boqd_Id", boqdIds.get(0));
        else wrapper.in("Boqd_Id", boqdIds);
        return !baseMapper.exists(wrapper);
    }

    @Override
    public ConMeasureRecord getConMeasureRecordByMeaBoqd(String meaId, String boqdId) {
        QueryWrapper<ConMeasureRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("Mea_Id", meaId);
        wrapper.eq("Boqd_Id", boqdId);
        List<ConMeasureRecord> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) return data.get(0);
        return null;
    }

    @Override
    public List<ConMeasureRecordVo> getApprovedData(List<String> boqdIds) {
        if (CollectionUtils.isEmpty(boqdIds)) return new ArrayList<>();
        return baseMapper.getApprovedData(boqdIds);
    }

    private List<ConMeasureRecord> getConMeasureRecordByMeaId(String meaId) {
        QueryWrapper<ConMeasureRecord> wrapper = new QueryWrapper<>();
        wrapper.eq("Mea_Id", meaId);
        return baseMapper.selectList(wrapper);
    }

    private SysFile setFile(ConMeasureRecordDto dto, ConMeasureRecord record, Date now) {
        SysFile file = new SysFile();
        file.setFileid(record.getId());
        file.setFileflowstatus(record.getState() + "");
        file.settId(dto.gettId());
        file.settCode(dto.gettCode());
        file.setFilecontract(dto.getConId());
        file.setFileproject(dto.getPId());
        file.setFilecreatedate(now);
        file.setFiletitle(dto.getFileTitle());
        file.setFileRelationId(dto.getFileRelationId());
        file.setFilecreateuserName(record.getCreateUname());
        file.setFilecreateuser(record.getCreateUid());
        file.setFilecode(dto.getFileCode());
        return file;
    }
}




