package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.project.PreChangeOwnerUnitDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.project.PreProjectReserveChangeMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.PreProjectReserveChangeDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.*;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.project.*;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/6 14:22
 */
@Service
public class PreProjectReserveChangeImpl extends BaseFileImpl<PreProjectReserveChangeMapper, PreProjectReserveChange, PreProjectReserveChangeDTO> implements IPreProjectReserveChangeService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IPreProjectReserveService preProjectReserveService;
    @Resource
    private ISysFileService sysFileService;

    @Resource
    private IPreProjectReserveDtlService preProjectReserveDtlService;

    @Resource
    private IPreChangeOwnerUnitService preChangeOwnerUnitService;

    @Resource
    private IPreOwnerUnitService preOwnerUnitService;
    @Resource
    private IPreProjectService preProjectService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateProjectReserveChange(PreProjectReserveChangeDTO request) {
        List<PreChangeOwnerUnitDTO> ownerUnits = request.getPreOwnerUnits();
        if (ownerUnits.size() > 2) {
            return BaseResponse.fail("业主单位不能超过2个");
        }
        PreProjectReserveChange entity = new PreProjectReserveChange();
        createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId())) {
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        } else {
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        }

        // 处理业主单位详情
        preChangeOwnerUnitService.saveChangeOwnerUnit(entity.getId(), ownerUnits);

        // 允许流程结束的储备项目维护时更新项目组织机构
        if (JudgeUtil.isApprovedState(request.getState())) {
            preProjectService.projectApproved2ImpTempDepartment(request.getPId(), true);
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getProjectReserveChangeList(PreProjectReserveChangeDTO request) {
        IPage<PreProjectReserveChangeDTO> page = new Page<>(request.getPage(), request.getSize());
        List<PreProjectReserveChangeDTO> data = baseMapper.getProjectReserveChangeList(page, request);
        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getProjectReserveChangeById(String id) {
        PreProjectReserveChangeDTO data = baseMapper.getProjectReserveChangeById(id);
        if (data == null) return BaseResponse.fail(ResMsg.infoNotExist);
        List<PreChangeOwnerUnitDTO> ownerUnitDTOS = preChangeOwnerUnitService.getChangeOwnerUnitByPprcId(data.getId());
        data.setPreOwnerUnits(ownerUnitDTOS);
        return BaseResponse.ok(data);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncProjectReserve(String id) {
        PreProjectReserveChange change=baseMapper.selectById(id);
        if (change == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(change.getState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        PreProjectReserve project=preProjectReserveService.getById(change.getPprId());
        if (project == null) return BaseResponse.fail(ResMsg.infoNotExist);

        PreProjectReserveDtl reserveDtl =  preProjectReserveDtlService.getpreProjectReservePprById(project.getId());
        PreProjectReserveDtl dtl = new PreProjectReserveDtl();
        org.springframework.beans.BeanUtils.copyProperties(project,dtl);
        dtl.setPprId(project.getId());
        if(ValidateUtil.isNull(reserveDtl)){
            dtl.setId(GenerateUUID.getUUID());
            preProjectReserveDtlService.save(dtl);
        } else {
                dtl.setId(reserveDtl.getId());
                preProjectReserveDtlService.updateById(dtl);
        }

        String status=project.getPprReserveStatus();
        BeanUtils.copyProperties(change,project);
        project.setId(change.getPprId());
        project.setPprReserveStatus(status);//不允许修改储备状态
        preProjectReserveService.updateById(project);

        if (StrUtil.equalsIgnoreCase(project.getPprModel(), "007")) {
            // 删除原业主单位
            preOwnerUnitService.remove(Wrappers.<PreOwnerUnit>lambdaQuery().eq(PreOwnerUnit::getPprId, project.getId()));
            // 获取变更后的业主单位
            List<PreChangeOwnerUnit> changeOwnerUnits = preChangeOwnerUnitService.list(Wrappers.<PreChangeOwnerUnit>lambdaQuery().eq(PreChangeOwnerUnit::getPprcId, change.getId()));
            // 保存新的业主单位
            if (CollUtil.isNotEmpty(changeOwnerUnits)) {
                List<PreOwnerUnit> ownerUnits = changeOwnerUnits.stream().map(changeOwnerUnit -> {
                    PreOwnerUnit ownerUnit = new PreOwnerUnit();
                    BeanUtils.copyProperties(changeOwnerUnit, ownerUnit);
                    ownerUnit.setId(GenerateUUID.getUUID());
                    ownerUnit.setPprId(project.getId());
                    return ownerUnit;
                }).collect(Collectors.toList());
                preOwnerUnitService.saveBatch(ownerUnits);
            }
        }

        return BaseResponse.ok();
    }

    @Override
    public void deleteByPprId(String pprId) {
        QueryWrapper<PreProjectReserveChange> wrapper=new QueryWrapper<>();
        wrapper.eq("ppr_id",pprId);
        List<PreProjectReserveChange> entitys=baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(entitys))
        {
            sysFileService.deleteByFileIds(entitys.stream().map(PreProjectReserveChange::getId).collect(Collectors.toList()));
            baseMapper.deleteBatchIds(entitys.stream().map(PreProjectReserveChange::getId).collect(Collectors.toList()));
        }
    }
}
