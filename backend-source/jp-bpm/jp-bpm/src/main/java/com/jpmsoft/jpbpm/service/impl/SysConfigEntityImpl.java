package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.SysConfigEntityMapper;
import com.jpmsoft.jpbpm.pojo.dto.DepartmentPCjConfDTO;
import com.jpmsoft.jpbpm.pojo.entity.SysConfigEntity;
import com.jpmsoft.jpbpm.service.SysConfigEntityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
@Transactional
public class SysConfigEntityImpl extends ServiceImpl<SysConfigEntityMapper, SysConfigEntity> implements SysConfigEntityService {

    public Map<String, Object> getConfigEntity(List<String> CodeList,String customerId) {
        Map<String, Object> list = new HashMap<>();
        QueryWrapper<SysConfigEntity> wrapper = new QueryWrapper<>();
        wrapper.in("Con_Code", CodeList);
        if(!"All".equals(customerId) ){
            wrapper.eq("Te_Id", customerId);
        }
        List<SysConfigEntity> Qlist = baseMapper.selectList(wrapper);
        Qlist.forEach(n -> {
            list.put(n.getConCode(), n.getConValue());
        });
        return list;
    }

    @Override
    public String getConfByCode(String code) {
        QueryWrapper<SysConfigEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", code);
        wrapper.eq("Con_Status", 1);
        List<SysConfigEntity> codes = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(codes)) return codes.get(0).getConValue();
        return null;
    }

    @Override
    public boolean endProcessGenerateCode(String customerId) {
        QueryWrapper<SysConfigEntity> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", "endProcessGenerateCode");
        if(!"All".equals(customerId) ){
            wrapper.eq("Te_Id", customerId);
        }
        SysConfigEntity entity = baseMapper.selectOne(wrapper);
        String value = entity == null ? null : entity.getConValue();
        if (ValidateUtil.isNotEmpty(value) && value.equals("1")) return true;
        return false;
    }

    @Override
    public DepartmentPCjConfDTO getDepartmentPCjConf() {
        String conf = getConfByCode("departmentPCjConf");
        if (ValidateUtil.isNotEmpty(conf))
            return JacksonUtils.parseObject(conf, DepartmentPCjConfDTO.class);
        return new DepartmentPCjConfDTO();
    }
}
