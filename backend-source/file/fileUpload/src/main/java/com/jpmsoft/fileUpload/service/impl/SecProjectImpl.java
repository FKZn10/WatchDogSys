package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.fileUpload.mapper.SecProjectMapper;
import com.jpmsoft.fileUpload.pojo.entity.SecProject;
import com.jpmsoft.fileUpload.service.ISecProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SecProjectImpl extends ServiceImpl<SecProjectMapper, SecProject> implements ISecProjectService {

    @Override
    public List<String> getSecIdsByPid(String pId) {
        QueryWrapper<SecProject> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        List<SecProject> datas = baseMapper.selectList(wrapper);
        if (datas != null && datas.size() > 0)
            return datas.stream().map(SecProject::getSecId).collect(Collectors.toList());
        return null;
    }
}
