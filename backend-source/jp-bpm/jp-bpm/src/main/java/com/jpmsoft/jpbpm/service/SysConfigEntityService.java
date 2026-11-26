package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.dto.DepartmentPCjConfDTO;
import com.jpmsoft.jpbpm.pojo.entity.SysConfigEntity;

import java.util.List;
import java.util.Map;

public interface SysConfigEntityService  extends IService<SysConfigEntity> {

    Map<String, Object> getConfigEntity(List<String> CodeList,String customerId);

    String getConfByCode(String code);

    /**
     * 是否流程审批结束才执行编码生成逻辑
     * 此逻辑临时使用
     *
     * @return
     */
    boolean endProcessGenerateCode(String customerId);

    DepartmentPCjConfDTO getDepartmentPCjConf();
}
