package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.userProducer.config.RequestDataHelper;
import com.jpmsoft.userProducer.mapper.FileMapper;
import com.jpmsoft.userProducer.pojo.entity.File;
import com.jpmsoft.userProducer.service.FileService;
import com.jpmsoft.userProducer.service.ISysSplitTableDtlService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class FileImpl extends ServiceImpl<FileMapper, File> implements FileService {

    @Resource
    private ISysSplitTableDtlService sysSplitTableDtlService;

    @Override
    public boolean exists(String flowId, String tCode) {
        String stCode = sysSplitTableDtlService.getSplitTableCode(tCode);
//        if (ValidateUtil.isEmpty(stCode)) {
        RequestDataHelper.setRequestData(stCode);
        QueryWrapper<File> wrapper = new QueryWrapper<>();
        wrapper.eq("Flow_Id", flowId);
        return baseMapper.exists(wrapper);
//        } else {
//            Integer count = baseMapper.existsByFlow(flowId, stCode);
//            return null != count && count > 0;
//        }
    }
}
