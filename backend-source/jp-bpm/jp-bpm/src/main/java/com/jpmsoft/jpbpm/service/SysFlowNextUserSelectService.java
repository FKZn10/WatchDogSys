package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.SysFlowNextUserSelect;

import java.util.List;

public interface SysFlowNextUserSelectService extends IService<SysFlowNextUserSelect> {
    Boolean create(SysFlowNextUserSelect sysFlowNextUserSelect);

    List<SysFlowNextUserSelect> getSysFlowNextUserSelectList(String flowId, String targetActId, String fileId);
}
