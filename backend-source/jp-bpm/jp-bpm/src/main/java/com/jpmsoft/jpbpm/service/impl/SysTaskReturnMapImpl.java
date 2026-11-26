package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.jpbpm.mapper.SysTaskReturnMapMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysTaskReturnMap;
import com.jpmsoft.jpbpm.pojo.entity.TaskProcessUser;
import com.jpmsoft.jpbpm.service.ISysTaskReturnMapService;
import com.jpmsoft.jpbpm.service.SysConfigEntityService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysTaskReturnMapImpl extends ServiceImpl<SysTaskReturnMapMapper, SysTaskReturnMap> implements ISysTaskReturnMapService {
    @Resource
    private SysConfigEntityService sysConfigEntityService;

    @Override
    public int create(SysTaskReturnMap sysTaskReturnMap, String customerId) {
        //查询连续驳回后是否能够原路返回配置。
        List<String> codeList = new ArrayList<>();
        codeList.add("continuouslyrRejectReturn");//待办推送地址
        Map<String, Object> configSet = sysConfigEntityService.getConfigEntity(codeList, customerId);
        if ("1".equals(configSet.get("continuouslyrRejectReturn"))) {
            sysTaskReturnMap.setId(GenerateUUID.getUUID());
            sysTaskReturnMap.setOperateTime(new Date());
            return baseMapper.insert(sysTaskReturnMap);
        } else {
            return 0;
        }
    }

    @Override
    public List<SysTaskReturnMap> selectByFileId(String fileId) {
        QueryWrapper<SysTaskReturnMap> wrapper = new QueryWrapper<>();
        wrapper.eq("file_Id", fileId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public int DeleteById(String id) {
        return baseMapper.deleteById(id);
    }
}
