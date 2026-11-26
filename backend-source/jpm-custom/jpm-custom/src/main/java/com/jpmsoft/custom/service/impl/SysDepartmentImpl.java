package com.jpmsoft.custom.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.custom.mapper.SysDepartmentMapper;
import com.jpmsoft.custom.pojo.entity.SysDepartment;
import com.jpmsoft.custom.service.ISysDepartmentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SysDepartmentImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements ISysDepartmentService {
    @Override
    public List<String> getUserDepIdsByUid(String uId) {
        if (ValidateUtil.isEmpty(uId)) return null;
        return baseMapper.getUserDepIdsByUid(uId);
    }
}
