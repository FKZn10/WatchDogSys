package com.jpmsoft.projectProducer.service.secInfo.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.SecJGExpertAuditMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGExpertAuditDTO;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecJGExpertAuditDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGExpertAudit;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecJGExpertAuditVO;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.secInfo.ISecExpertService;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGExpertAuditDetailService;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGExpertAuditService;
import com.jpmsoft.projectProducer.service.secInfo.ISecJGExpertService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;


@Service
public class SecJGExpertAuditImpl extends FileImpl<SecJGExpertAuditMapper, SecJGExpertAudit,SecJGExpertAuditDTO> implements ISecJGExpertAuditService {

    @Resource
    private ISecJGExpertAuditDetailService secJGExpertAuditDetailService;
    @Resource
    private ISecJGExpertService secJGExpertService;

    @Override
    public BaseResponse createOrUpdateSecJGExpertAudit(SecJGExpertAuditDTO request, LogUserInfoVo user) {
        List<SecJGExpertAuditDetailDTO> secJGExpertAuditDetails = request.getSecJGExpertAuditDetails();
        if(CollectionUtil.isEmpty(secJGExpertAuditDetails)){
            return BaseResponse.fail("申请的入库的专家数据不能为空");
        }
        SecJGExpertAudit entity = new SecJGExpertAudit();
        entity.setSeaCid(user.getCId());
        createOrUpdateFileEntity(entity, request);
        secJGExpertAuditDetailService.deleteCreate(request.getSecJGExpertAuditDetails(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }



    @Override
    public BaseResponse createOrUpdateSecJGExpertAuditDz(SecJGExpertAuditDTO request, LogUserInfoVo user) {
        if (ValidateUtil.isEmpty(request.getId())) {
            QueryWrapper<SecJGExpertAudit> wrapper = new QueryWrapper<>();
            wrapper.eq("Sea_Cid", user.getCId());
            wrapper.eq("sea_inside_type", request.getSeaInsideType());
            Long num = baseMapper.selectCount(wrapper);
            if (num == null || num < 1L) num = 1L;
            else num += 1L;
            request.setSeaName(user.getCName() + "第" + num + "批次");
        }
        return createOrUpdateSecJGExpertAudit(request, user);
    }

    @Override
    public BaseResponse getSecJGExpertAuditList(SecJGExpertAuditDTO request) {
        QueryMap<SecJGExpertAuditDTO> params = new QueryMap<>(request);
        params.put("seaName").put("createUname").put("state").put("seaInsideType");
        IPage<SecJGExpertAuditVO> page = new Page<>(request.getPage(), request.getSize());
        List<SecJGExpertAuditVO> data = baseMapper.getSecExpertAuditList(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSecJGExpertAuditById(String id) {
        SecJGExpertAudit sea = baseMapper.selectById(id);
        if (sea == null) return BaseResponse.fail(ResMsg.infoNotExist);
        SecJGExpertAuditVO data = new SecJGExpertAuditVO();
        BeanUtils.copyProperties(sea, data);
        data.setSecJGExpertAuditDetails(secJGExpertAuditDetailService.getDetailList(id));
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse approveSyncExpertState(String seaId) {
        SecJGExpertAudit sea = baseMapper.selectById(seaId);
        if (sea == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (!Objects.equals(sea.getState(), ApprovalStatusEnum.Already_Over.getIndex()))
            return BaseResponse.fail(ResMsg.stateCannotAct);
        List<String> ids = secJGExpertAuditDetailService.getExIdList(seaId);
        secJGExpertService.updateExpertStateBath(ids, sea.getState());
        return BaseResponse.ok();
    }
}
