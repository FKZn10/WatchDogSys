package com.jpmsoft.projectProducer.service.project.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.projectProducer.mapper.project.ProManagePlanMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.ProManagePlanDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.ProManagePlan;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.project.IProManagePlanService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/10/18 10:21
 */
@Service
public class ProManagePlanImpl extends FileImpl<ProManagePlanMapper, ProManagePlan, ProManagePlanDTO> implements IProManagePlanService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    public BaseResponse createOrUpdateProManagePlan(ProManagePlanDTO request) {
        ProManagePlan entity = new ProManagePlan();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getProManagePlanList(ProManagePlanDTO request, String uId) {
        String authSql = sysUserService.getAuthSql("Pro_Manage_Plan", uId, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        QueryMap<ProManagePlanDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("pCode").put("pId")
                .put("pmpTitle").put("state").put("createUname");
        IPage<ProManagePlanDTO> page = new Page<>(request.getPage(), request.getSize());
        List<ProManagePlanDTO> datas = baseMapper.getProManagePlanList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getProManagePlanById(String id) {
        return BaseResponse.ok(baseMapper.getProManagePlanById(id));
    }
}
