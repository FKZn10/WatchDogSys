package com.jpmsoft.jpbpm.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.entity.SysExtendValue;
import com.jpmsoft.jpbpm.mapper.SysExtendValueMapper;
import com.jpmsoft.jpbpm.service.SysExtendValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysExtendValueImpl extends ServiceImpl<SysExtendValueMapper, SysExtendValue> implements SysExtendValueService {

    @Override
    public BaseResponse createOrUpdateSysExtendValue(List<SysExtendValue> extendConfs, String customerId) {
        if (extendConfs.size() > 0) {
            QueryWrapper<SysExtendValue> wrapper = new QueryWrapper<>();
            wrapper.eq("FileID", extendConfs.get(0).getFileid());
            if (!"All".equals(customerId)) {
                wrapper.eq("Te_Id", customerId);
            }
            baseMapper.delete(wrapper);
            extendConfs.forEach(n -> {
                baseMapper.insert(n);
            });
        }
        return BaseResponse.ok("操作成功");

    }

    @Override
    public BaseResponse getSysExtendValueByFileId(String fileId, String customerId) {
        QueryWrapper<SysExtendValue> wrapper = new QueryWrapper<>();
        wrapper.eq("FileID", fileId);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<SysExtendValue> list = baseMapper.selectList(wrapper);
        return BaseResponse.ok(list, list.size());

    }

    @Override
    public BaseResponse getSysExtendValueByFileIds(List<String> fileIds, String customerId) {
        QueryWrapper<SysExtendValue> wrapper = new QueryWrapper<>();
        wrapper.in("FileID", fileIds);
        if (!"All".equals(customerId)) {
            wrapper.eq("Te_Id", customerId);
        }
        List<SysExtendValue> list = baseMapper.selectList(wrapper);
        return BaseResponse.ok(list, list.size());
    }

}
