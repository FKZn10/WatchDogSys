package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.mapper.SysDepartmentMapper;
import com.jpmsoft.fileUpload.pojo.entity.SysDepartment;
import com.jpmsoft.fileUpload.service.ISysDepartmentService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/7/3 9:40
 */
@Service
public class SysDepartmentImpl extends ServiceImpl<SysDepartmentMapper, SysDepartment> implements ISysDepartmentService {
    @Override
    public List<String> getAllChildDepartmentByCid(String cId) {
        QueryWrapper<SysDepartment> wrapper = new QueryWrapper<>();
        wrapper.eq("D_Status", "1");
        List<SysDepartment> list = baseMapper.selectList(wrapper);
        Set<String> datas = new HashSet<>();
        List<SysDepartment> cDeps = list.stream().filter(n -> ValidateUtil.isNotEmpty(n.getdCompanyId()) && n.getdCompanyId().equals(cId)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(cDeps)) {
            for (SysDepartment cDep : cDeps) {
                datas.add(cDep.getdId());
                getTreeChildrenId(datas, cDep.getdId(), list);
            }
        }
        return new ArrayList<>(datas);
    }

    public void getTreeChildrenId(Set<String> datas, String parentId, List<SysDepartment> list) {
        for (SysDepartment pojo : list) {
            if (pojo.getdParentid().equals(parentId)) {
                datas.add(pojo.getdId());
                getTreeChildrenId(datas, pojo.getdId(), list);
            }
        }
    }
}
