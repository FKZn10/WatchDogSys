package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecInfoChangeRes;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoChange;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface SecInfoChangeMapper extends BaseMapper<SecInfoChange> {

    List<SecInfoChangeRes> getSecInfoChangeList(@Param("bpcId") String bpcId);

    @Select("select Sic_Id from Sec_Info_Change where Bpc_Id=#{bpcId}")
    List<String> getSecInfoChangeIds(@Param("bpcId") String bpcId);

    List<SecInfoChange> getSecInfoChangeListById(@Param("notStateList") List<Integer> notStateList, @Param("secId") String secId);

}
