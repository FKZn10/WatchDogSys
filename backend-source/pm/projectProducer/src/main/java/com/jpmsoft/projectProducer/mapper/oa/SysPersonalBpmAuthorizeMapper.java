package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.Oa.SysPersonalBpmAuthorizeModel;
import com.jpmsoft.projectProducer.pojo.entity.oa.SysPersonalBpmAuthorize;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SysPersonalBpmAuthorizeMapper extends BaseMapper<SysPersonalBpmAuthorize> {

    List<SysPersonalBpmAuthorizeModel> getPersonalBpmAuthorizeList(@Param("params") Map<String, Object> params);

    Integer getPersonalBpmAuthorizeListCount(@Param("params") Map<String, Object> params);

    SysPersonalBpmAuthorizeModel getPersonalBpmAuthorizeById(@Param("baId") String baId);
}
