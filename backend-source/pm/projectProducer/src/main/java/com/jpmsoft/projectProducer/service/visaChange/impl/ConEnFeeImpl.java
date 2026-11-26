package com.jpmsoft.projectProducer.service.visaChange.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.visaChange.ConEnFeeMapper;
import com.jpmsoft.projectProducer.pojo.dto.visaChange.ConEnFeeDTO;
import com.jpmsoft.projectProducer.pojo.entity.visaChange.ConEnFee;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.visaChange.IConEnFeeService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/19 10:35
 */
@Service
public class ConEnFeeImpl extends FileImpl<ConEnFeeMapper, ConEnFee, ConEnFeeDTO> implements IConEnFeeService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    @CodeGeneration(key = "id", codeParam = "cefCode", method = "setCefCode", codeRule = "ConEnFee", beanClass = ConEnFeeDTO.class)
    public BaseResponse createOrUpdateConEnFee(ConEnFeeDTO request) {
        ConEnFee entity = new ConEnFee();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getConEnFeeList(ConEnFeeDTO request, String uId) {
        String authSql = sysUserService.getAuthSql("Con_En_Fee", uId, "Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConEnFeeDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pName").put("conName").put("cefCode").put("state").put("enisSubCode")
                .put("enisName").put("enisCode").put("enisProjectName").put("pId").put("createUname");
        IPage<ConEnFeeDTO> page = new Page<>(request.getPage(), request.getSize());
        List<ConEnFeeDTO> data = baseMapper.getConEnFeeList(page, params.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConEnFeeById(String id) {
        return BaseResponse.ok(baseMapper.getConEnFeeById(id));
    }
}
