package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSourcesFunds;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;


public interface SecSourcesFundsMapper extends BaseMapper<SecSourcesFunds> {


    @Delete("delete from Sec_Sources_Funds where Sec_Id in (Select Sec_Id from Sec_Info where Bp_Id=#{bpId})")
    Integer delSecSourcesFundsByBpId(@Param("bpId") String bpId);
}
