package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.PreProjectChangeNameMapper;
import com.jpmsoft.jpbpm.pojo.entity.PreProjectChangeName;
import com.jpmsoft.jpbpm.pojo.entity.PreProject;
import com.jpmsoft.jpbpm.service.PreProjectChangeNameService;
import com.jpmsoft.jpbpm.service.PreProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.jpmsoft.common.utils.ValidateUtil;

@Service
public class PreProjectChangeNameImpl extends ServiceImpl<PreProjectChangeNameMapper, PreProjectChangeName> implements PreProjectChangeNameService {

    @Autowired
    private PreProjectService preProjectService;

    @Override
    public void approve2SyncProjectName(String id) {
        PreProjectChangeName pojo = baseMapper.selectById(id);
        PreProject pro = preProjectService.getById(pojo.getpId());
        if (ValidateUtil.isEmpty(pro.getpNameHistory())) {
            pro.setpName(pojo.getPcnNewName());
            pro.setpNameHistory(pojo.getPcnNewName());
        } else {
            pro.setpName(pojo.getPcnNewName());
            pro.setpNameHistory(pro.getpNameHistory() + ";" + pojo.getPcnNewName());
        }
        preProjectService.updateById(pro);
    }
}
