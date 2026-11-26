package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.jpbpm.pojo.entity.sysTaskMassage;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ISysTaskMassageService  extends IService<sysTaskMassage> {
    void deleteUselessData();

    void  insertSysTaskMassage(List<sysTaskMassage> list);

    BaseResponse setSysTaskMassageReadType(String id,String uId);
}
