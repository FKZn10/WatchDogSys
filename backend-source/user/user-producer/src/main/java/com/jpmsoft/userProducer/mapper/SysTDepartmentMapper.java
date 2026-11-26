package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysTDepartment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SysTDepartmentMapper extends BaseMapper<SysTDepartment> {


    @Select("select mn.* from SYS_T_Department mn " +
            "left join SYS_T_Organization st on st.To_Id=mn.To_Id where st.TO_Cid=#{cId} " +
            "order by mn.TD_Sort ASC")
    List<SysTDepartment> getTDepartmentTreeList(@Param("cId") String cId);


    @Select("select mn.* from SYS_T_Department mn " +
            "left join SYS_T_Organization st on st.To_Id=mn.To_Id where st.TO_Cid=#{cId} " +
            "and  mn.TD_ParentID='0' " +
            "order by mn.TD_Sort ASC")
    List<SysTDepartment> getTDepartmentParentList(@Param("cId") String cId);
}
