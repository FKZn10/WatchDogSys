package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvRoughDeclareDtl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface InvRoughDeclareDtlMapper extends BaseMapper<InvRoughDeclareDtl> {

    @Select("Select * from Inv_Rough_Declare_Dtl where Rd_Id=(select Rd_Id from Inv_Rough_Declare_Dtl where Rdd_Id=#{rddId})")
    List<InvRoughDeclareDtl> getAllByRddId(@Param("rddId") String rddId);

    @Select("Select rdd.Rdd_Id from Inv_Rough_Declare_Dtl rdd left join Inv_Rough_Declare rd on rd.Rd_Id=rdd.Rd_Id where rd.P_Id=#{pId}")
    List<String> getAllByPId(@Param("pId") String pId);
}
