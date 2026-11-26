package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecInfoResponse;
import com.jpmsoft.jpbpm.pojo.entity.SecInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;

@Mapper
public interface SecInfoMapper extends BaseMapper<SecInfo> {

    @Update("update Sec_Info set Sec_Flow_State=#{state},Sec_Flow_Time=#{stateTime} where Bp_Id=#{bpId}")
    Integer updateByBpId(@Param("bpId") String bpId,@Param("state") Integer state,@Param("stateTime") Date stateTime);

}
