package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.userProducer.pojo.dto.SysPortalIndexConfigDto;
import com.jpmsoft.userProducer.pojo.entity.SysPortalIndexConfig;
import com.jpmsoft.userProducer.pojo.entity.SysRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SysPortalIndexConfigMapper extends BaseMapper<SysPortalIndexConfig> {

    IPage<SysPortalIndexConfigDto> getSysPortalIndexConfigList(@Param("page") IPage<SysPortalIndexConfigDto> page,
                                                              @Param("params") Map<String, Object> query);

    List<SysRole> queryRoleListByUid(@Param("uid") String uid);
}
