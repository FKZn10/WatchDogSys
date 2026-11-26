package com.jpmsoft.userProducer.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


@Repository
public interface SysSequenceFactoryMapper {

    @Select("${sql}")
    String sqlRuleGenerate(@Param("sql") String sql);

    @Select("select  ${filed} from  Pre_Project where P_ID=#{pId}")
    String getProjectInfo(@Param("pId") String pId, @Param("filed") String filed);

    @Select("select  ${filed} from  Con_Contract where Con_Id=#{conId}")
    String getContractInfo(@Param("conId") String conId, @Param("filed") String filed);

    @Select("select  ${filed} from  Sec_Info where Sec_Id=#{secId}")
    String getSecInfo(@Param("secId") String secId, @Param("filed") String filed);


    @Select("select ${cloumn} from  ${table} where ${key}=#{id}")
    String getLinkedTableValue(@Param("table") String table, @Param("cloumn") String cloumn, @Param("id") String id, @Param("key") String key);
}
