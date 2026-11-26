package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecControlPriceResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecControlPrice;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface SecControlPriceMapper extends BaseMapper<SecControlPrice> {

    List<SecControlPriceResponse> getControlPriceList(@Param("params") Map<String, Object> params);

    Integer getControlPriceListCount(@Param("params") Map<String, Object> params);

    SecControlPriceResponse getControlPriceBycpId(@Param("cpId") String cpId);


    @Select("select * from Sec_Control_Price where Sec_Id=#{secId} and Cp_State=7 order by Cp_State_Time desc limit 1")
    SecControlPriceResponse getControlPriceBysecId(@Param("secId") String secId);

    @Select("select Sec_Control_Price.* from Sec_Control_Price left join Sec_Project on Sec_Control_Price.Sec_Id=Sec_Project.Sec_Id where Sec_Project.P_Id= #{pId} and Sec_Control_Price.Cp_State=7")
    List<SecControlPriceResponse> getControlPriceBypId(@Param("pId") String pId);
}
