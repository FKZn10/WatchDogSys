package com.jpmsoft.projectProducer.service.peInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.PeInfo.PeGradeRuleRequest;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.peInfo.PeGradeRuleMapper;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeGradeRule;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.peInfo.IPeGradeRuleService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class PeGradeRuleImpl extends ServiceImpl<PeGradeRuleMapper, PeGradeRule> implements IPeGradeRuleService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdatePeGradeRule(PeGradeRuleRequest request) {
        PeGradeRule pojo;
        if (ValidateUtil.isEmpty(request.getrId())) {
            pojo = new PeGradeRule();
            BeanUtils.copyProperties(request, pojo);
            pojo.setrCreateTime(new Date());
            pojo.setrId(GenerateUUID.getUUID());
            baseMapper.insert(pojo);
            return BaseResponse.ok(pojo.getrId());
        } else {
            pojo = baseMapper.selectById(request.getrId());
            if (pojo == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            PeGradeRule entity = new PeGradeRule();
            BeanUtils.copyProperties(request, entity);
            entity.setrCreateTime(pojo.getrCreateTime());
            entity.setrCreateUid(pojo.getrCreateUid());
            entity.setrCreateUname(pojo.getrCreateUname());
            baseMapper.updateById(entity);
            return BaseResponse.ok(entity.getrId());
        }
    }

    @Override
    public BaseResponse getPeGradeRuleList(PeGradeRuleRequest request, String uId) {
        if (sysUserService.checkIsOutUser(uId))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryWrapper<PeGradeRule> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getTeId()))
            wrapper.eq("Te_Id", request.getTeId());
        if (!ValidateUtil.isAnObjEmpty(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC"))
                wrapper.orderByAsc(request.getSortFiled());
            else
                wrapper.orderByDesc(request.getSortFiled());
        } else
            wrapper.orderByDesc("R_Create_Time");
        IPage<PeGradeRule> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

    @Override
    public BaseResponse deletePeGradeRuleByRId(String rId) {
        PeGradeRule pojo = baseMapper.selectById(rId);
        if (pojo == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(rId);
        return BaseResponse.ok();
    }
}
