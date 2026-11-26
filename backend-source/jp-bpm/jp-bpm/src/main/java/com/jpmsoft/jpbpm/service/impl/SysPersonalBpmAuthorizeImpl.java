package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.jpbpm.mapper.SysPersonalBpmAuthorizeMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysPersonalBpmAuthorize;
import com.jpmsoft.jpbpm.service.SysPersonalBpmAuthorizeService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysPersonalBpmAuthorizeImpl extends ServiceImpl<SysPersonalBpmAuthorizeMapper, SysPersonalBpmAuthorize> implements SysPersonalBpmAuthorizeService {
    @Override
    public List<String> getCreateUidByAuthorizerUid(String uId, String customerId) {
//        try {
//            return baseMapper.getCreateUidByAuthorizerUid(uId);
//        } catch (Exception ex) {
        QueryWrapper<SysPersonalBpmAuthorize> wrapper = new QueryWrapper<>();
        wrapper.eq("Ba_Authorizer_Uid", uId);
        wrapper.eq("Ba_State", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper.ge("Ba_End_Time", new Date());
        wrapper.le("Ba_Start_Time", new Date());
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<SysPersonalBpmAuthorize> datas = baseMapper.selectList(wrapper);
        List<String> ids = new ArrayList<>();
        datas.forEach(data -> {
            ids.add(data.getBaCreateUid());
        });
        return ids;
        //}
    }
}
