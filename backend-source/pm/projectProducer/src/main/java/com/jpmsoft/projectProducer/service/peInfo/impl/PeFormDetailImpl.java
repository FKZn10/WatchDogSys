package com.jpmsoft.projectProducer.service.peInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeFormDetailRequest;
import com.jpmsoft.common.model.request.PeInfo.PeFormDetailStandardRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.peInfo.PeFormDetailMapper;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeFormDetail;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.peInfo.IPeFormDetailService;
import com.jpmsoft.projectProducer.service.peInfo.IPeFormDetailStandardService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PeFormDetailImpl extends ServiceImpl<PeFormDetailMapper, PeFormDetail> implements IPeFormDetailService {

    @Resource
    private IPeFormDetailStandardService peFormDetailStandardService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "fdId", codeParam = "fdCode", method = "setFdCode", codeRule = "PeFormDetail", beanClass = PeFormDetailRequest.class)
    public BaseResponse createOrUpdatePeFormDetail(PeFormDetailRequest request) {
        if (ValidateUtil.isEmpty(request.getfId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        PeFormDetail pojo = new PeFormDetail();
        if (ValidateUtil.isEmpty(request.getFdCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);
        BeanUtils.copyProperties(request, pojo);
        if (ValidateUtil.isEmpty(request.getFdId())) {
            pojo.setFdId(GenerateUUID.getUUID());
            baseMapper.insert(pojo);
            return BaseResponse.ok(pojo.getFdId());
        } else {
            PeFormDetail entity = baseMapper.selectById(request.getFdId());
            if (entity == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            baseMapper.updateById(pojo);
            return BaseResponse.ok(pojo.getFdId());
        }
    }

    @Override
    public BaseResponse getPeFormDetailList(PeFormDetailRequest request, String uId) {
        if (sysUserService.checkIsOutUser(uId))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryWrapper<PeFormDetail> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        wrapper.eq("F_Id", request.getfId());
        if (!ValidateUtil.isAnObjEmpty(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC"))
                wrapper.orderByAsc(request.getSortFiled());
            else
                wrapper.orderByDesc(request.getSortFiled());
        } else
            wrapper.orderByDesc("Fd_Order");
        IPage<PeFormDetail> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deletePeFormDetailByFdId(String fdId) {
        PeFormDetail pojo = baseMapper.selectById(fdId);
        if (pojo == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        peFormDetailStandardService.deleteDetailToDeleteStandard(fdId);
        baseMapper.deleteById(fdId);
        return BaseResponse.ok();
    }

    @Override
    public void deletePeFormToDeleteDetail(String fId) {
        QueryWrapper<PeFormDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("F_ID", fId);
        List<PeFormDetail> models = baseMapper.selectList(wrapper);
        if (models != null && models.size() > 0) {
            List<String> fdIds = new ArrayList<>();
            models.forEach(model -> {
                fdIds.add(model.getFdId());
            });
            peFormDetailStandardService.deletePeFormToDeleteStandard(fdIds);
            baseMapper.deleteBatchIds(fdIds);
        }
    }

    @Override
    public List<PeFormDetailRequest> getPeFormDetailCascadeInfo(String fId) {
        QueryWrapper<PeFormDetail> wrapper = new QueryWrapper<>();
        wrapper.eq("F_ID", fId);
        List<PeFormDetail> models = baseMapper.selectList(wrapper);
        List<PeFormDetailRequest> datas = new ArrayList<>();
        List<PeFormDetailStandardRequest> standards = peFormDetailStandardService.getPeFormDetailStandardByFid(fId);
        models.forEach(model -> {
            PeFormDetailRequest data = new PeFormDetailRequest();
            BeanUtils.copyProperties(model, data);
            if (standards != null && standards.size() > 0) {
                data.setPeFormStandards(standards.stream().filter(s -> s.getFdId().equals(data.getFdId())).collect(Collectors.toList()));
            }
            datas.add(data);
        });
        return datas;
    }
}
