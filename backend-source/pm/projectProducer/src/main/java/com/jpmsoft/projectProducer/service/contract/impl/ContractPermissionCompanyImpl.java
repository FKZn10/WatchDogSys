package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ContractPermissionCompanyMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractPermissionCompanyDTO;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractPermissionUserDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractPermissionCompany;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.contract.IContractPermissionCompanyService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/2/6 16:47
 */
@Service
public class ContractPermissionCompanyImpl extends ServiceImpl<ContractPermissionCompanyMapper, ContractPermissionCompany> implements IContractPermissionCompanyService {

    @Resource
    private RedisUtils redisUtils;

    @Override
    public BaseResponse addPermissionCompany(String conId, String cId, String token) {
        QueryWrapper<ContractPermissionCompany> wrapper = new QueryWrapper<>();
        wrapper.eq("con_id", cId);
        wrapper.eq("c_id", cId);
        if (!baseMapper.exists(wrapper)) {
            Integer sort = baseMapper.selectMaxSort(cId);
            ContractPermissionCompany entity = new ContractPermissionCompany();
            entity.setId(GenerateUUID.getUUID());
            entity.setCpcSort(ValidateUtil.isNull(sort) ? 1 : sort + 1);
            entity.setConId(conId);
            entity.setCId(cId);
            LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
            entity.setCreateTime(new Date());
            entity.setCreateUname(user.getName());
            entity.setCreateUid(user.getId());
            baseMapper.insert(entity);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deletePermissionCompanyById(String id) {
        ContractPermissionCompany entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getPermissionCompanyList(ContractPermissionCompanyDTO request) {
        IPage<ContractPermissionCompanyDTO> page = new Page<>(request.getPage(), request.getSize());
        List<ContractPermissionCompanyDTO> data = baseMapper.getPermissionCompanyList(page, request);
        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }
}
