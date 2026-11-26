package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.jpbpm.mapper.SysFlowNextUserSelectMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysFlowNextUserSelect;
import com.jpmsoft.jpbpm.service.SysFlowNextUserSelectService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class SysFlowNextUserSelectImpl  extends ServiceImpl<SysFlowNextUserSelectMapper, SysFlowNextUserSelect> implements SysFlowNextUserSelectService {

    @Transactional
    @Override
    public Boolean create(SysFlowNextUserSelect sysFlowNextUserSelect) {
        sysFlowNextUserSelect.setId(GenerateUUID.getUUID());
        sysFlowNextUserSelect.setCreateTime(new Date());
        baseMapper.insert(sysFlowNextUserSelect);
        return true;
    }


    @Override
    public List<SysFlowNextUserSelect> getSysFlowNextUserSelectList(String flowId, String targetActId,String fileId) {
        QueryWrapper<SysFlowNextUserSelect>   queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("flow_id", flowId);
        queryWrapper.eq("target_Act_Id", targetActId);
        queryWrapper.eq("file_id", fileId);
     return baseMapper.selectList(queryWrapper);
    }

}
