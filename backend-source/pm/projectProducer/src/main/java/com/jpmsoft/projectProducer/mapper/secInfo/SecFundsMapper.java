package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecFunds;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/19 8:42
 */
public interface SecFundsMapper extends BaseMapper<SecFunds> {

    @Delete("delete from Sec_Funds where Sec_Id in (Select Sec_Id from Sec_Info where Bp_Id=#{bpId})")
    Integer deleteByBpId(@Param("bpId") String bpId);
}
