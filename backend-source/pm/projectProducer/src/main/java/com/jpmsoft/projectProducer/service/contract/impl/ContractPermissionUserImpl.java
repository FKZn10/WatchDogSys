package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ContractPermissionUserMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ContractPermissionUserDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.ContractPermissionUser;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.contract.IContractPermissionUserService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/2/6 15:20
 */
@Service
public class ContractPermissionUserImpl extends ServiceImpl<ContractPermissionUserMapper, ContractPermissionUser>
        implements IContractPermissionUserService {

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IConContractService conContractService;


    @Override
    public BaseResponse addContractOne(String uid, String conId, String token) {
        QueryWrapper<ContractPermissionUser> wrapper = new QueryWrapper<>();
        wrapper.eq("u_id", uid);
        wrapper.eq("con_id", conId);
        if (!baseMapper.exists(wrapper)) {
            Integer sort = baseMapper.selectMaxSort(uid);
            ContractPermissionUser entity = new ContractPermissionUser();
            entity.setId(GenerateUUID.getUUID());
            entity.setCpuSort(ValidateUtil.isNull(sort) ? 1 : sort + 1);
            entity.setConId(conId);
            entity.setUId(uid);
            LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
            entity.setCreateTime(new Date());
            entity.setCreateUname(user.getName());
            entity.setCreateUid(user.getId());
            baseMapper.insert(entity);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse addContractUserBatch(List<String> userIds, String conId, String token) {
        QueryWrapper<ContractPermissionUser> wrapper = new QueryWrapper<>();
        wrapper.in("u_id", userIds);
        wrapper.eq("con_id", conId);
        List<ContractPermissionUser> data = baseMapper.selectList(wrapper);
        List<String> oldUsers = new ArrayList<>();
        if (!CollectionUtils.isEmpty(data))
            oldUsers = data.stream().map(ContractPermissionUser::getUId).collect(Collectors.toList());
        List<String> finalOldUsers = oldUsers;
        List<String> addUsers = userIds.stream().filter(f -> !finalOldUsers.contains(f)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(addUsers)) {
            Integer sort = baseMapper.selectContractMaxSort(conId);
            List<ContractPermissionUser> entities = new ArrayList<>();
            LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
            for (String uid : addUsers) {
                ContractPermissionUser entity = new ContractPermissionUser();
                entity.setId(GenerateUUID.getUUID());
                entity.setCpuSort(ValidateUtil.isNull(sort) ? 1 : sort + 1);
                entity.setConId(conId);
                entity.setUId(uid);
                entity.setCreateTime(new Date());
                entity.setCreateUname(user.getName());
                entity.setCreateUid(user.getId());
                entities.add(entity);
            }
            if (!CollectionUtils.isEmpty(addUsers)) saveBatch(entities);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse addContractAll(String uid, String token) {
        QueryWrapper<ConContract> conWrapper = new QueryWrapper<>();
        conWrapper.ne("con_Approval_State", ApprovalStatusEnum.Invalid.getIndex());
        List<ConContract> contracts = conContractService.list(conWrapper);
        if (!CollectionUtils.isEmpty(contracts)) {
            QueryWrapper<ContractPermissionUser> wrapper = new QueryWrapper<>();
            wrapper.eq("u_id", uid);
            List<ContractPermissionUser> data = baseMapper.selectList(wrapper);
            List<String> conIds = new ArrayList<>();
            if (!CollectionUtils.isEmpty(data)) {
                List<String> olds = data.stream().map(ContractPermissionUser::getConId).collect(Collectors.toList());
                List<ConContract> cons = contracts.stream().filter(f -> !olds.contains(f.getConId())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(cons))
                    conIds = cons.stream().map(ConContract::getConId).collect(Collectors.toList());
            } else {
                conIds = contracts.stream().map(ConContract::getConId).collect(Collectors.toList());
            }
            if (!CollectionUtils.isEmpty(conIds)) {
                LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
                Integer sort = baseMapper.selectMaxSort(uid);
                sort = ValidateUtil.isNull(sort) ? 1 : sort + 1;
                List<ContractPermissionUser> entities = new ArrayList<>();
                for (int i = 0; i < conIds.size(); i++) {
                    ContractPermissionUser entity = new ContractPermissionUser();
                    entity.setId(GenerateUUID.getUUID());
                    entity.setCpuSort(sort + i);
                    entity.setConId(conIds.get(i));
                    entity.setUId(uid);
                    entity.setCreateTime(new Date());
                    entity.setCreateUname(user.getName());
                    entity.setCreateUid(user.getId());
                    entities.add(entity);
                }
                if (!CollectionUtils.isEmpty(entities)) saveBatch(entities, 100);
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deleteContractAll(String uid) {
        QueryWrapper<ContractPermissionUser> wrapper = new QueryWrapper<>();
        wrapper.eq("u_id", uid);
        baseMapper.delete(wrapper);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deleteByConIdAndUid(String uid, String conId) {
        QueryWrapper<ContractPermissionUser> wrapper = new QueryWrapper<>();
        wrapper.eq("u_id", uid);
        wrapper.eq("con_id", conId);
        baseMapper.delete(wrapper);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deleteById(String id) {
        ContractPermissionUser entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse selectUserPage(ContractPermissionUserDTO request) {
        IPage<ContractPermissionUserDTO> page = new Page<>(request.getPage(), request.getSize());
        List<ContractPermissionUserDTO> data = baseMapper.selectUserPage(page, request);
        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse selectContractPage(ContractPermissionUserDTO request) {
        IPage<ConContractResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConContractResponse> data = baseMapper.selectContractPage(page, request);
        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getCurrentUserConIdList(String token) {
        if (ValidateUtil.isEmpty(token)) {
            return BaseResponse.fail(ResMsg.tokenInvalid);
        }
        String uId = redisUtils.getUid(token);
        List<ContractPermissionUser> list = lambdaQuery()
                .eq(ContractPermissionUser::getUId, uId)
                .select(ContractPermissionUser::getConId)
                .list();
        if (CollectionUtils.isEmpty(list)) {
            return BaseResponse.ok(Collections.emptyList());
        }
        return BaseResponse.ok(list.stream().map(ContractPermissionUser::getConId).collect(Collectors.toSet()));
    }
}
