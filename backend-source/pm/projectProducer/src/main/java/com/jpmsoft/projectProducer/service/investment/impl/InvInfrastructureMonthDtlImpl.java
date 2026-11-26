package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvInfrastructureMonthDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvInfrastructureMonthDtlDto;
import com.jpmsoft.projectProducer.pojo.dto.investment.InvInfrastructureQueryDto;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvInfrastructureMonth;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvInfrastructureMonthDtl;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvInfrastructureMonthDtlVo;
import com.jpmsoft.projectProducer.service.investment.IInvInfrastructureMonthDtlService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/5 9:07
 */
@Service
public class InvInfrastructureMonthDtlImpl extends ServiceImpl<InvInfrastructureMonthDtlMapper, InvInfrastructureMonthDtl> implements IInvInfrastructureMonthDtlService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    public void createOrUpdateInfrastructureMonthDtl(List<InvInfrastructureMonthDtlDto> datas, InvInfrastructureMonth im) {
        delInfrastructureMonthDtlByImId(im.getId());
        if (ValidateUtil.isNotEmpty(datas)) {
            List<InvInfrastructureMonthDtl> entitys = new ArrayList<>();
            entitys = datas.stream().map(n -> {
                InvInfrastructureMonthDtl entity = new InvInfrastructureMonthDtl();
                BeanUtils.copyProperties(n, entity);
                entity.setId(ValidateUtil.isEmpty(n.getId()) ? GenerateUUID.getUUID() : n.getId());
                entity.setCreateUid(im.getCreateUid());
                entity.setCreateUname(im.getCreateUname());
                entity.setCreateTime(im.getCreateTime());
                entity.setImId(im.getId());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void delInfrastructureMonthDtlByImId(String imId) {
        QueryWrapper<InvInfrastructureMonthDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Im_Id", imId);
        baseMapper.delete(wrapper);
    }

    @Override
    public BaseResponse getInvInfrastructureMonthDtlList(InvInfrastructureQueryDto request, String uId) {
        String authSql = sysUserService.getAuthSql("Inv_Infrastructure_Month_Dtl", uId, "Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<InvInfrastructureQueryDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pId").put("imYear")
                .put("imMon").put("state").put("createUname").put("imdSchoolName");
        IPage<InvInfrastructureMonthDtlVo> page = new Page<>(request.getPage(), request.getSize());
        List<InvInfrastructureMonthDtlVo> datas = baseMapper.getInvInfrastructureMonthDtlList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }
}
