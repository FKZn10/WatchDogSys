package com.jpmsoft.jpbpm.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.jpbpm.pojo.entity.SysFileTemplateDtl;
import org.springframework.stereotype.Service;

@Service
public interface SysFileTemplateDtlService  extends IService<SysFileTemplateDtl> {
    SysFileTemplateDtl getInterfaceVersion(String tId, String pageCode, String pId, String dId);
    SysFileTemplateDtl getInterfaceVersionByCode(String tId, String pageCode);
}
