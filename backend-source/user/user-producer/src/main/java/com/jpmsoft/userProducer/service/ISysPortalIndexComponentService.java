package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.dto.SysPortalIndexComponentDto;
import com.jpmsoft.userProducer.pojo.entity.SysPortalIndexComponent;

import java.util.List;
import java.util.Set;

public interface ISysPortalIndexComponentService extends IService<SysPortalIndexComponent> {

    void createOrUpdateSysPortalIndexComponent(List<SysPortalIndexComponentDto> components, String id);

    List<SysPortalIndexComponentDto> getSysPortalIndexComponentByMainIdsList(List<String> mainIds);

    List<SysPortalIndexComponent> getSysPortalIndexComponentByRoleIds(Set<String> roleIds);
}
