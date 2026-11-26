package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.dto.SysDepartmentDto;
import com.jpmsoft.userProducer.pojo.entity.SysDepartment;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SysDepartmentMapper extends BaseMapper<SysDepartment> {


    List<String> getUserDepartment(@Param("uId") String uId);

    /**
     * 查询用户部门授权为 《组织机构授权，负责人部门授权》类型且用户为该负责人的部门
     * @param uId
     * @return
     */
    List<String> getUserDutyDepartment(@Param("uId") String uId);

    List<SysDepartmentDto> getDepartmentTreeList(@Param("params") Map<String, Object> params);

    List<SysDepartmentDto> getUserDepartmentByUid(@Param("uId") String uId);

    List<String>  getUserDepIdsByUid(@Param("uId") String uId);

    SysDepartmentDto getRootDepartment();

    SysDepartmentDto getDepartmentByCode(@Param("xmgldw")String xmgldw);
}
