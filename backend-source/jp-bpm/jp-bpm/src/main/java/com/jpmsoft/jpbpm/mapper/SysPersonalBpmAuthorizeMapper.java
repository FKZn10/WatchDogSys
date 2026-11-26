package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysPersonalBpmAuthorize;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SysPersonalBpmAuthorizeMapper extends BaseMapper<SysPersonalBpmAuthorize> {


    List<String> getCreateUidByAuthorizerUid(@Param("uId") String uId);

}
