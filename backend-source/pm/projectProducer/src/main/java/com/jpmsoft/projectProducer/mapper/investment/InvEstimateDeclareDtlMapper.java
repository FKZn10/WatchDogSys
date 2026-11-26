package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvEstimateDeclareDtl;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface InvEstimateDeclareDtlMapper extends BaseMapper<InvEstimateDeclareDtl> {

    @Select("Select * from Inv_Estimate_Declare_Dtl where ed_Id=(select ed_Id from Inv_Estimate_Declare_Dtl where edd_Id=#{eddId})")
    List<InvEstimateDeclareDtl> getAllByEddId(@Param("eddId") String eddId);

    @Select("Select edd.edd_Id from Inv_Estimate_Declare_Dtl edd left join Inv_Estimate_Declare ed on ed.ed_Id=edd.ed_Id where ed.P_Id=#{pId}")
    List<String> getAllByPId(@Param("pId") String pId);
}
