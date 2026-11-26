package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.PreProjectChangeName;
import org.springframework.stereotype.Service;

@Service
public interface PreProjectChangeNameService extends IService<PreProjectChangeName> {

    void approve2SyncProjectName(String id);
}
