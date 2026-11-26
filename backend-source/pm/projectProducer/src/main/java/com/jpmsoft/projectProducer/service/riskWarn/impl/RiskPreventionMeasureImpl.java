package com.jpmsoft.projectProducer.service.riskWarn.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.riskWarn.RiskPreventionMeasureMapper;
import com.jpmsoft.projectProducer.pojo.dto.riskWarn.RiskPreventionMeasureDTO;
import com.jpmsoft.projectProducer.pojo.entity.riskWarn.RiskPreventionMeasure;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.riskWarn.IRiskPreventionMeasureService;
import com.jpmsoft.projectProducer.service.riskWarn.IRiskTemplateRelationService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/3/29 11:31
 */
@Service
public class RiskPreventionMeasureImpl extends ServiceImpl<RiskPreventionMeasureMapper, RiskPreventionMeasure> implements IRiskPreventionMeasureService {
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IRiskTemplateRelationService riskTemplateRelationService;

    @Override
    public BaseResponse createOrUpdate(RiskPreventionMeasureDTO request) {
        if (ValidateUtil.isEmpty(request.getRpmParentId())) request.setRpmParentId("0");
        RiskPreventionMeasure entity = new RiskPreventionMeasure();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getId())) {
            Integer sort = baseMapper.getMaxSore(request.getRpmParentId());
            entity.setRpmSort(sort == null ? 1 : sort + 1);
            entity.setCreateTime(new Date());
            entity.setId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        } else {
            RiskPreventionMeasure old = baseMapper.selectById(entity.getId());
            if (old == null) return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setCreateUname(old.getCreateUname());
            entity.setCreateUid(old.getCreateUid());
            entity.setCreateTime(old.getCreateTime());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse delete(String id) {
        RiskPreventionMeasure old = baseMapper.selectById(id);
        if (old == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (havingChild(id)) return BaseResponse.fail(ResMsg.delDownNodeFirst);
        if (riskTemplateRelationService.havingPreventionMeasure(id)) return BaseResponse.fail(ResMsg.delAssociated);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse enable(String id, String status) {
        RiskPreventionMeasure old = baseMapper.selectById(id);
        if (status.equals("0")) {
            if (old == null) return BaseResponse.fail(ResMsg.infoNotExist);
            if (havingUnDisableChild(id)) return BaseResponse.fail(ResMsg.disableDownNodeFirst);
            if (riskTemplateRelationService.havingPreventionMeasure(id))
                return BaseResponse.fail(ResMsg.disableRelationData);
        }
        old.setRpmStatus(status);
        baseMapper.updateById(old);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getTree(LogUserInfoVo user) {
        if (sysUserService.checkIsOutUser(user.getId())) return BaseResponse.fail(ResMsg.noPermission);

        List<RiskPreventionMeasureDTO> all = ValidateUtil.isNotEmpty(user.getTeId()) ? baseMapper.getAllWithTeId(user.getTeId()) : baseMapper.getAll();
        if (!CollectionUtils.isEmpty(all)) {
            //顶级节点
            List<RiskPreventionMeasureDTO> tops = all.stream().filter(n -> ValidateUtil.isEmpty(n.getRpmParentId()) || n.getRpmParentId().equals("0")).collect(Collectors.toList());
            List<RiskPreventionMeasureDTO> data = new ArrayList<>();
            for (RiskPreventionMeasureDTO dto : tops) {
                List<RiskPreventionMeasureDTO> child = new ArrayList<>();
                makeChildNodes(all, child, dto.getId());
                dto.setChildNodes(child);
                data.add(dto);
            }
            return BaseResponse.ok(data);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse upOrDown(String id, String action, String teId) {
        RiskPreventionMeasure old = baseMapper.selectById(id);
        if (old == null) return BaseResponse.fail(ResMsg.infoNotExist);
        QueryWrapper<RiskPreventionMeasure> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        wrapper.eq("Rpm_Parent_Id", old.getRpmParentId());
        wrapper.orderByAsc("Rpm_Sort");
        List<RiskPreventionMeasure> sibling = baseMapper.selectList(wrapper);
        //当前层级只有一条数据
        if (sibling.size() == 1) return BaseResponse.fail(ResMsg.canNotMove);
        int index = sibling.indexOf(old);
        List<RiskPreventionMeasure> updates = new ArrayList<>();
        RiskPreventionMeasure change;
        if (action.equalsIgnoreCase("up")) {
            if (index == 0) return BaseResponse.fail(ResMsg.canNotMove);
            change = sibling.get(index - 1);
        } else if (action.equalsIgnoreCase("down")) {
            if (index == sibling.size() - 1) return BaseResponse.fail(ResMsg.canNotMove);
            change = sibling.get(index + 1);
        } else return BaseResponse.fail(ResMsg.unknownAction);
        int current = old.getRpmSort();
        old.setRpmSort(change.getRpmSort());
        change.setRpmSort(current);
        updates.add(old);
        updates.add(change);
        updateBatchById(updates);
        return BaseResponse.ok();
    }

    private void makeChildNodes(List<RiskPreventionMeasureDTO> allDatas, List<RiskPreventionMeasureDTO> datas, String parentId) {
        List<RiskPreventionMeasureDTO> nodes = allDatas.stream().filter(n -> n.getRpmParentId().equals(parentId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(nodes)) {
            nodes.forEach(model -> {
                List<RiskPreventionMeasureDTO> child = new ArrayList<>();
                makeChildNodes(allDatas, child, model.getId());
                model.setChildNodes(child);
                datas.add(model);
            });
        }
    }

    private boolean havingUnDisableChild(String id) {
        QueryWrapper<RiskPreventionMeasure> wrapper = new QueryWrapper<>();
        wrapper.eq("Rpm_Parent_Id", id);
        wrapper.eq("Rpm_Status", 1);
        return baseMapper.exists(wrapper);
    }

    private boolean havingChild(String id) {
        QueryWrapper<RiskPreventionMeasure> wrapper = new QueryWrapper<>();
        wrapper.eq("Rpm_Parent_Id", id);
        return baseMapper.exists(wrapper);
    }
}
