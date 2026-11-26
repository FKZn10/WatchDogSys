package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.mapper.SysUserPwdChangeMapper;
import com.jpmsoft.userProducer.pojo.entity.SysUserPwdChange;
import com.jpmsoft.userProducer.service.ISysUserPwdChangeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SysUserPwdChangeImpl extends ServiceImpl<SysUserPwdChangeMapper, SysUserPwdChange> implements ISysUserPwdChangeService {
    @Override
    public void insertUserPwdChange(String oldPw, String newPw, String uID, String uName, String accountUId) {
        SysUserPwdChange entity = new SysUserPwdChange();
        entity.setPcOld(oldPw);
        entity.setPcNew(newPw);
        entity.setPcCreateUid(uID);
        entity.setPcCreateUname(uName);
        entity.setPcCreateTime(new Date());
        entity.setUId(accountUId);
        entity.setPcId(GenerateUUID.getUUID());
        baseMapper.insert(entity);
    }

    @Override
    public Boolean checkPwdUsed(String pwd, String uId) {
        //不能使用最近3次相同的密码
        QueryWrapper<SysUserPwdChange> wrapper = new QueryWrapper<>();
        wrapper.eq("U_Id", uId);
        wrapper.orderByDesc("PC_Create_Time");
        wrapper.last("limit 0,3");
        List<SysUserPwdChange> data = baseMapper.selectList(wrapper);
        List<SysUserPwdChange> has = data.stream()
                .filter(n -> (ValidateUtil.isNotEmpty(n.getPcOld()) && n.getPcOld().equals(pwd))
                        || (ValidateUtil.isNotEmpty(n.getPcNew()) && n.getPcNew().equals(pwd))).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(has))
            return true;
        else
            return false;
    }

    @Override
    public void delUserPwdChangeRecord(String uId) {
        QueryWrapper<SysUserPwdChange> wrapper = new QueryWrapper<>();
        wrapper.eq("U_Id", uId);
        baseMapper.delete(wrapper);
    }
}
