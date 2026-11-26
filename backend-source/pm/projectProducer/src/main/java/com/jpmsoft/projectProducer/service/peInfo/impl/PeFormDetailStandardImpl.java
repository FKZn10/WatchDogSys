package com.jpmsoft.projectProducer.service.peInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeFormDetailStandardRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.peInfo.PeFormDetailStandardMapper;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeFormDetailStandard;
import com.jpmsoft.projectProducer.service.peInfo.IPeFormDetailStandardService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PeFormDetailStandardImpl extends ServiceImpl<PeFormDetailStandardMapper, PeFormDetailStandard> implements IPeFormDetailStandardService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdatePeFormStandard(PeFormDetailStandardRequest request) {
        PeFormDetailStandard pojo = new PeFormDetailStandard();
        BeanUtils.copyProperties(request, pojo);
        if (ValidateUtil.isEmpty(request.getsId())) {
            pojo.setsId(GenerateUUID.getUUID());
            baseMapper.insert(pojo);
            return BaseResponse.ok(pojo.getsId());
        } else {
            PeFormDetailStandard entity = baseMapper.selectById(request.getsId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            baseMapper.updateById(pojo);
            return BaseResponse.ok(pojo.getsId());
        }
    }

    @Override
    public BaseResponse getPeFormStandardList(PeFormDetailStandardRequest request) {
        QueryWrapper<PeFormDetailStandard> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        wrapper.eq("Fd_Id", request.getFdId());
        if (!ValidateUtil.isAnObjEmpty(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC"))
                wrapper.orderByAsc(request.getSortFiled());
            else
                wrapper.orderByDesc(request.getSortFiled());
        } else
            wrapper.orderByDesc("S_Order");
        List<PeFormDetailStandard> data = baseMapper.selectList(wrapper);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse deletePeFormStandardById(String sId) {
        PeFormDetailStandard pojo = baseMapper.selectById(sId);
        if (pojo == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(sId);
        return BaseResponse.ok();
    }

    @Override
    public void deleteDetailToDeleteStandard(String fdId) {
        baseMapper.deletePeFormDetailStandardByfdId(fdId);
    }

    @Override
    public void deletePeFormToDeleteStandard(List<String> fdIds) {
        baseMapper.deletePeFormToDeleteStandard(fdIds);
    }

    @Override
    public List<PeFormDetailStandardRequest> getPeFormDetailStandardByFid(String fId) {
        return baseMapper.getPeFormDetailStandardByFid(fId);
    }
}
