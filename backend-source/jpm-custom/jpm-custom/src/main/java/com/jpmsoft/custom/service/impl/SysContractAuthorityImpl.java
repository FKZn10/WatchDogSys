package com.jpmsoft.custom.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.ContractAuthorityDTO;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.custom.mapper.SysContractAuthorityMapper;
import com.jpmsoft.custom.pojo.entity.SysContractAuthority;
import com.jpmsoft.custom.service.ISysContractAuthorityService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysContractAuthorityImpl extends ServiceImpl<SysContractAuthorityMapper, SysContractAuthority> implements ISysContractAuthorityService {

    @Override
    public List<String> getConIdByCidFromContractAuthority(String cId) {
        return baseMapper.getConIdByCidFromContractAuthority(cId);
    }

    @Override
    public ContractAuthorityDTO getConIdByCidFromContractAuthority(String cId, String uid) {
        QueryWrapper<SysContractAuthority> wrapper = new QueryWrapper<>();
        wrapper.eq("Ca_User_Id", uid);
        if (ValidateUtil.isNotEmpty(cId)) wrapper.or().eq("Ca_C_Id", cId);
        List<SysContractAuthority> data = baseMapper.selectList(wrapper);
        ContractAuthorityDTO auth = new ContractAuthorityDTO();
        if (!CollectionUtils.isEmpty(data)) {
            List<String> enables = new ArrayList<>();
            List<String> disables = new ArrayList<>();
            List<SysContractAuthority> users = data.stream().filter(f -> ValidateUtil.isNotEmpty(f.getCaUserId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(users)) {
                users.forEach(e -> {
                    if (e.getCaType().equalsIgnoreCase("disable")) disables.add(e.getConId());
                    if (e.getCaType().equalsIgnoreCase("enable")) enables.add(e.getConId());
                });
            }
            List<SysContractAuthority> coms = data.stream().filter(f -> ValidateUtil.isNotEmpty(f.getCaCId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(coms)) {
                coms.forEach(e -> {
                    if (!disables.contains(e.getConId()) && !enables.contains(e.getConId())) {
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

}
