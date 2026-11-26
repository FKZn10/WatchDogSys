package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.mapper.SysTaskProcessinTimeMapper;
import com.jpmsoft.jpbpm.mapper.sysTaskMassageMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysTaskProcessinTime;
import com.jpmsoft.jpbpm.pojo.entity.sysTaskMassage;
import com.jpmsoft.jpbpm.service.ISysTaskMassageService;
import com.jpmsoft.jpbpm.service.SysTaskProcessinTimeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@Transactional
public class sysTaskMassageImpl extends ServiceImpl<sysTaskMassageMapper, sysTaskMassage> implements ISysTaskMassageService {

    @Override
    public void deleteUselessData() {
        baseMapper.deleteUselessData();
    }


    @Override
    public void insertSysTaskMassage(List<sysTaskMassage> list) {
        baseMapper.insertSysTaskMassage(list);

    }

    @Override
    public BaseResponse setSysTaskMassageReadType(String id, String uId) {
        QueryWrapper<sysTaskMassage> queryWrapper = new QueryWrapper<sysTaskMassage>();
        queryWrapper.eq("task_Id", id);
        queryWrapper.eq("u_Ids", uId);
        List<sysTaskMassage> sysTaskMassages = baseMapper.selectList(queryWrapper);
        for (sysTaskMassage sysTaskMassage : sysTaskMassages) {
            sysTaskMassage.setReadType("1");
            sysTaskMassage.setReadTime(new Date());
            baseMapper.updateById(sysTaskMassage);
        }

        return BaseResponse.ok("设置成功");
    }

}
