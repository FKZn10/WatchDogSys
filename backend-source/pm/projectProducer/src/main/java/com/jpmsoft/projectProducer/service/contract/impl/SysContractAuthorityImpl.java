package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.ContractAuthorityBathDTO;
import com.jpmsoft.common.model.request.ContractAuthorityDTO;
import com.jpmsoft.common.model.request.SysContractAuthorityRequest;
import com.jpmsoft.common.model.response.SysContractAuthorityResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.SysContractAuthorityMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.SysContractAuthority;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.contract.ISysContractAuthorityService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysContractAuthorityImpl extends ServiceImpl<SysContractAuthorityMapper, SysContractAuthority> implements ISysContractAuthorityService {

    @Resource
    private ISysUserService sysUserService;

    @Override
    public List<String> getConIdByCidFromContractAuthority(String cId) {
        return baseMapper.getConIdByCidFromContractAuthority(cId);
    }

    @Override
    public ContractAuthorityDTO getConIdByCidFromContractAuthority(String cId, String uid) {
        QueryWrapper<SysContractAuthority> wrapper=new QueryWrapper<>();
        wrapper.eq("Ca_User_Id",uid);
        if (ValidateUtil.isNotEmpty(cId)) wrapper.or().eq("Ca_C_Id",cId);
        List<SysContractAuthority> data=baseMapper.selectList(wrapper);
        ContractAuthorityDTO auth=new ContractAuthorityDTO();
        if (!CollectionUtils.isEmpty(data))
        {
            List<String> enables=new ArrayList<>();
            List<String> disables=new ArrayList<>();
            List<SysContractAuthority> users=data.stream().filter(f->ValidateUtil.isNotEmpty(f.getCaUserId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(users)) {
                users.forEach(e -> {
                    if (e.getCaType().equalsIgnoreCase("disable")) disables.add(e.getConId());
                    if (e.getCaType().equalsIgnoreCase("enable")) enables.add(e.getConId());
                });
            }
            List<SysContractAuthority> coms=data.stream().filter(f->ValidateUtil.isNotEmpty(f.getCaCId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(coms)) {
                coms.forEach(e -> {
                    if (!disables.contains(e.getConId())&&!enables.contains(e.getConId())) {
                        if (e.getCaType().equalsIgnoreCase("disable")) disables.add(e.getConId());
                        if (e.getCaType().equalsIgnoreCase("enable")) enables.add(e.getConId());
                    }
                });
            }
            auth.setDisables(disables);
            auth.setEnables(enables);
        }
        return auth;
    }

    @Override
    public BaseResponse createContractAuthority(SysContractAuthorityRequest request) {
        QueryWrapper<SysContractAuthority> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", request.getConId());
        wrapper.eq("Ca_C_Id", request.getCaCId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.caCpyContractExists);
        SysContractAuthority entity = new SysContractAuthority();
        BeanUtils.copyProperties(request, entity);
        entity.setCaId(GenerateUUID.getUUID());
        entity.setCaCreateTime(new Date());
        baseMapper.insert(entity);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse createOrDelAuthBath(ContractAuthorityBathDTO request, LogUserInfoVo user) {
        if (CollectionUtils.isEmpty(request.getConIds())||ValidateUtil.isAnObjEmpty(request.getCaType(),request.getAction()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (CollectionUtils.isEmpty(request.getCompanyIds())&&CollectionUtils.isEmpty(request.getUserIds()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (request.getAction().equalsIgnoreCase("delete"))
            deleteBath(request);
        else  if (request.getAction().equalsIgnoreCase("add"))
        {
            deleteBath(request);
            List<SysContractAuthority> entities=new ArrayList<>();
            Date now=new Date();
            for (String conId : request.getConIds()) {
                if (!CollectionUtils.isEmpty(request.getCompanyIds()))
                {
                    for (String companyId : request.getCompanyIds()) {
                        SysContractAuthority entity=en(conId,request,now,user);
                        entity.setCaCId(companyId);
                        entities.add(entity);
                    }
                }
                else
                {
                    for (String uid : request.getUserIds()) {
                        SysContractAuthority entity=en(conId,request,now,user);
                        entity.setCaUserId(uid);
                        entities.add(entity);
                    }
                }
            }
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        }else return BaseResponse.fail(ResMsg.unknownAction);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deleteContractAuthBath(List<String> ids) {
        if (!CollectionUtils.isEmpty(ids)) baseMapper.deleteBatchIds(ids);
        return BaseResponse.ok();
    }

    private SysContractAuthority en(String conId,ContractAuthorityBathDTO request,Date now, LogUserInfoVo user){
        SysContractAuthority entity=new SysContractAuthority();
        entity.setCaId(GenerateUUID.getUUID());
        entity.setConId(conId);
        entity.setCaType(request.getCaType());
        entity.setCaCreateUname(user.getName());
        entity.setCaCreateUid(user.getId());
        entity.setTeId(user.getTeId());
        entity.setCaCreateTime(now);
        return entity;
    }

    private void deleteBath(ContractAuthorityBathDTO request){
        QueryWrapper<SysContractAuthority> wrapper=new QueryWrapper<>();
        wrapper.in("con_id",request.getConIds());
        if (!CollectionUtils.isEmpty(request.getCompanyIds()))
            wrapper.in("Ca_C_Id",request.getCompanyIds());
        else
            wrapper.in("Ca_User_Id",request.getUserIds());
        baseMapper.delete(wrapper);
    }

    @Override
    public BaseResponse deleteContractAuthorityByCaId(String caId) {
        SysContractAuthority entity = baseMapper.selectById(caId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        baseMapper.deleteById(caId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getContractAuthorityList(SysContractAuthorityRequest request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Ca_Create_Uid", OutUserAuthEnum.NOT.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<SysContractAuthorityRequest> params = new QueryMap<>(request);
        params.put("authSql"," where 1=1 ").put("conName").put("caCName")
                .put("pName").put("pId").put("caCreateUname").put("caUserName")
                .put("caUserId").put("caCId");
        IPage<SysContractAuthorityResponse> page=new Page<>(request.getPage(),request.getSize());
        List<SysContractAuthorityResponse> datas = baseMapper.getContractAuthorityList(page,params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }
}
