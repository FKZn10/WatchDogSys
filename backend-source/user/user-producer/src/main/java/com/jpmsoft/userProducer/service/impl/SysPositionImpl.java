package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysPositionMapper;
import com.jpmsoft.userProducer.pojo.dto.PositionDto;
import com.jpmsoft.userProducer.pojo.entity.SysPosition;
import com.jpmsoft.userProducer.pojo.vo.SysPositionVo;
import com.jpmsoft.userProducer.service.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SysPositionImpl extends ServiceImpl<SysPositionMapper, SysPosition> implements ISysPositionService {

    @Resource
    private ISysPositionRoleService sysPositionRoleService;
    @Resource
    private ISysPositionUserService sysPositionUserService;
    @Resource
    private ISysSequenceSetService sysSequenceSetService;
    @Resource
    private ISysFlowUserService sysFlowUserService;

    @Override
    public BaseResponse GetPositionList(PositionDto request) {
        if (ValidateUtil.isEmpty(request.getpStatus()))
            request.setpStatus(null);
        if (ValidateUtil.isEmpty(request.getdId()))
            request.setdId(null);
        if (ValidateUtil.isEmpty(request.getpName()))
            request.setpName(null);
        List<SysPositionVo> models = baseMapper.GetPositionList((request.getPage() - 1) * request.getSize(), request.getSize()
                , request.getpStatus(), request.getdId(), request.getpName(), request.getSortType(), request.getSortFiled());
        Integer total = baseMapper.GetPositionListCount(request.getpStatus(), request.getdId(), request.getpName());
        return BaseResponse.ok(models, total);
    }


    @Override
    public List<SysPosition> GetPositionList() {
        QueryWrapper<SysPosition> wrapper = new QueryWrapper<>();
        List<SysPosition> pojos = baseMapper.selectList(wrapper);
        return pojos;
    }

    @Override
    public List<SysPosition> GetPositionList(List<String> idlist) {

        QueryWrapper<SysPosition> wrapper = new QueryWrapper<>();
        List<SysPosition> pojos = baseMapper.selectList(wrapper);
        List<SysPosition> newpojos = pojos.stream().filter(item -> idlist.contains(item.getpId())).collect(Collectors.toList());
        return newpojos;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse setDuty(String pId, boolean enable) {
        SysPosition entity = baseMapper.selectById(pId);
        if (entity == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.disableAllSyncDepDuty(entity.getdId());
        entity.setpDuty(enable);
        baseMapper.updateById(entity);
        return BaseResponse.ok(pId);
    }

    @Override
    public boolean departmentHavPosition(String did) {
        QueryWrapper<SysPosition> wrapper = new QueryWrapper<>();
        wrapper.eq("D_ID", did);
        return baseMapper.exists(wrapper);
    }

    @Override
    public BaseResponse getPositionByRole(String roleId) {
        return BaseResponse.ok(baseMapper.getPositionByRole(roleId));
    }

    @Override
    public BaseResponse GetPSelectByDID(PositionDto request) {
        QueryWrapper<SysPosition> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Status", "1");
        List<SysPosition> pojos = baseMapper.selectList(wrapper);
        List<SysPosition> models = null;
        if (!ValidateUtil.isEmpty(request.getdId()))
            models = pojos.stream()
                    .filter((SysPosition s) -> request.getdId().equals(s.dId))
                    .collect(Collectors.toList());
        else
            models = pojos.stream().collect(Collectors.toList());
        List<Map<String, String>> entitys = new ArrayList<>();
        for (SysPosition model : models) {
            Map<String, String> data = new HashMap<>();
            data.put("id", model.getpId());
            data.put("name", model.getpName());
            entitys.add(data);
        }
        return BaseResponse.ok(entitys);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse CreateOrUpdatePosition(PositionDto request) {
        SysPosition sysPosition;
        Boolean flag = true;
        String codeRule = null;
        if (!ValidateUtil.isEmpty(request.getpId())) {
            sysPosition = baseMapper.selectById(request.getpId());
            if (sysPosition == null)
                return BaseResponse.fail(BaseMsg.infoNotExist);
        } else {
            flag = false;
            sysPosition = new SysPosition();
            if (sysSequenceSetService.sequenceRestEnable("SysPosition", request.getTeId()) == true) {
                request.setpCode(sysSequenceSetService.generateCodeByTCode("SysPosition", request, request.getTeId()));
                if (ValidateUtil.isEmpty(request.getpCode()))
                    return BaseResponse.fail(BaseMsg.codeGenerateFail);
                codeRule = "SysPosition";
            }
        }
        sysPosition.setdId(request.getdId());
        sysPosition.setpMdmNo(request.pMdmNo);
        sysPosition.setpName(request.pName);
        sysPosition.setpRemark(request.pRemark);
        if (!ValidateUtil.isEmpty(request.getpSort()))
            sysPosition.setpSort(Integer.parseInt(request.getpSort()));
        sysPosition.setpCode(request.getpCode());
        sysPosition.setpStatus(request.pStatus);
        sysPosition.setpDuty(request.getpDuty());
        QueryWrapper<SysPosition> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Code", request.getpCode());
        if (!ValidateUtil.isEmpty(request.getpId()))
            wrapper.ne("P_ID", request.getpId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(BaseMsg.infoIsExist);

        if (flag == false) {
            sysPosition.setpId(GenerateUUID.getUUID());
            sysPosition.setpCreatetime(new Date());
            sysPosition.setpCreateuName(request.getpCreateuName());
            sysPosition.setpCreateuId(request.getpCreateuId());
            baseMapper.insert(sysPosition);
            sysPositionRoleService.CreatePositionRoles(request.getRoleIds(), sysPosition.getpId());
        } else {
            sysPositionRoleService.CreatePositionRoles(request.getRoleIds(), sysPosition.getpId());
            baseMapper.updateById(sysPosition);
        }
        if (ValidateUtil.isNotEmpty(codeRule))
            sysSequenceSetService.refershAutoNo(codeRule, request, request.getTeId());
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse DeletePosition(PositionDto request) {
        QueryWrapper<SysPosition> wrapper = new QueryWrapper<>();
        wrapper.eq("P_ID", request.getpId());
        SysPosition entity = baseMapper.selectOne(wrapper);
        if (entity != null) {
            if (sysFlowUserService.positionExist(entity.getpId())) return BaseResponse.fail(BaseMsg.bpmNotDel);
            sysPositionRoleService.deletePositionRoles(request.getpId());
            sysPositionUserService.deletePositionUserByPid(request.getpId());
            baseMapper.deleteById(request.getpId());
            return BaseResponse.ok();
        } else {
            return BaseResponse.fail(BaseMsg.infoNotExist);
        }
    }


}
