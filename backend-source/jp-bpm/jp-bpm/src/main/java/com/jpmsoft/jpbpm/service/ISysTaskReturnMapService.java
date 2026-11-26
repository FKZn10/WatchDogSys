package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.SysTaskReturnMap;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISysTaskReturnMapService extends IService<SysTaskReturnMap> {
    int create(SysTaskReturnMap sysTaskReturnMap,String customerId);

    List<SysTaskReturnMap> selectByFileId(String fileId);

    int DeleteById(String id);
}
