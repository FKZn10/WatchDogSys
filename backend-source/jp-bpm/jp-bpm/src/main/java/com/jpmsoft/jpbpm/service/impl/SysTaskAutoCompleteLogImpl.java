package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.mapper.SysTaskAutoCompleteLogMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysTaskAutoCompleteLog;
import com.jpmsoft.jpbpm.service.SysTaskAutoCompleteLogService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class SysTaskAutoCompleteLogImpl extends ServiceImpl<SysTaskAutoCompleteLogMapper, SysTaskAutoCompleteLog> implements SysTaskAutoCompleteLogService {
    @Override
    public BaseResponse getTaskAutoCompleteLogByTpIds(List<String> tpIds, String customerId) {
        if (CollectionUtils.isEmpty(tpIds)) return BaseResponse.ok();
        QueryWrapper<SysTaskAutoCompleteLog> Wrapper = new QueryWrapper<>();
        Wrapper.in("Tp_Id", tpIds);
        if (!"All".equals(customerId)) {
            Wrapper.eq("Te_Id", customerId);
        }
        return BaseResponse.ok(baseMapper.selectList(Wrapper), 0);
    }

    @Override
    public BaseResponse getTaskAutoCompleteLogByTaskIds(List<String> TaskIds, String customerId) {
        if (CollectionUtils.isEmpty(TaskIds)) return BaseResponse.ok();
        QueryWrapper<SysTaskAutoCompleteLog> Wrapper = new QueryWrapper<>();
        Wrapper.in("Task_Id", TaskIds);
        Wrapper.eq("Tacl_Results", "失败");
        if (!"All".equals(customerId)) {
            Wrapper.eq("Te_Id", customerId);
        }
        return BaseResponse.ok(baseMapper.selectList(Wrapper), 0);
    }

    @Override
    public BaseResponse deleteTaskAutoCompleteLogByTaskId(String id, String customerId) {
        QueryWrapper<SysTaskAutoCompleteLog> Wrapper = new QueryWrapper<>();
        Wrapper.eq("Task_Id", id);
        Wrapper.eq("Tacl_Results", "失败");
        if (!"All".equals(customerId)) {
            Wrapper.eq("Te_Id", customerId);
        }
        return BaseResponse.ok(baseMapper.delete(Wrapper), 0);
    }
}
