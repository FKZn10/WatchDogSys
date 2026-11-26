package com.jpmsoft.custom.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.custom.pojo.entity.SysDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysDepartmentMapper extends BaseMapper<SysDepartment> {
    List<String> getUserDepIdsByUid(@Param("uId") String uId);
}
