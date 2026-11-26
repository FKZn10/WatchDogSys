package com.jpmsoft.projectProducer.service.peInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeFormDetailRequest;
import com.jpmsoft.common.model.request.PeInfo.PeFormRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.peInfo.PeFormMapper;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeForm;
import com.jpmsoft.projectProducer.service.peInfo.IPeFormDetailService;
import com.jpmsoft.projectProducer.service.peInfo.IPeFormService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class PeFormImpl extends ServiceImpl<PeFormMapper, PeForm> implements IPeFormService {

    @Resource
    private IPeFormDetailService peFormDetailService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdatePeForm(PeFormRequest request) {
        PeForm pojo;
        if (ValidateUtil.isEmpty(request.getfId())) {
            pojo = new PeForm();
            BeanUtils.copyProperties(request, pojo);
            pojo.setfCreateTime(new Date());
            pojo.setfId(GenerateUUID.getUUID());
            baseMapper.insert(pojo);
            return BaseResponse.ok(pojo.getfId());
        } else {
            pojo = baseMapper.selectById(request.getfId());
            if (pojo == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            PeForm entity = new PeForm();
            BeanUtils.copyProperties(request, entity);
            entity.setfCreateTime(pojo.getfCreateTime());
            entity.setfCreateUid(pojo.getfCreateUid());
            entity.setfCreateUname(pojo.getfCreateUname());
            baseMapper.updateById(entity);
            return BaseResponse.ok(entity.getfId());
        }

    }

    @Override
    public BaseResponse getPeFormList(PeFormRequest request, String uId) {
        //因代建单位需要录入考核评分信息，去除只有内部用户才能查找的权限校验（20220620：张）
//        if (sysUserService.checkIsOutUser(uId))
//            return  BaseResponse.fail(ResMsg.noPermission);
        QueryWrapper<PeForm> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        if (!ValidateUtil.isEmpty(request.getfName()))
            wrapper.like("F_Name", request.getfName());
        if (!ValidateUtil.isEmpty(request.getfType()))
            wrapper.like("F_Type", request.getfType());

        if (!ValidateUtil.isAnObjEmpty(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC"))
                wrapper.orderByAsc(request.getSortFiled());
            else
                wrapper.orderByDesc(request.getSortFiled());
        } else
            wrapper.orderByDesc("F_Create_Time");

        IPage<PeForm> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deletePeFormByFId(String fId) {
        PeForm pojo = baseMapper.selectById(fId);
        if (pojo == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        peFormDetailService.deletePeFormToDeleteDetail(fId);
        baseMapper.deleteById(fId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getPeFormCascadeInfo(String fId) {
        PeForm pojo = baseMapper.selectById(fId);
        if (pojo == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        PeFormRequest data = new PeFormRequest();
        BeanUtils.copyProperties(pojo, data);
        List<PeFormDetailRequest> childs = peFormDetailService.getPeFormDetailCascadeInfo(fId);
        data.setPeFormDetails(childs);
        return BaseResponse.ok(data);
    }

}
