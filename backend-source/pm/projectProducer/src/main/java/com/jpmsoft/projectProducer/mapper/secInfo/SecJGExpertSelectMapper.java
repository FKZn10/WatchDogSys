package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGExpertSelect;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface SecJGExpertSelectMapper extends BaseMapper<SecJGExpertSelect> {


    @Update("update Sec_JG_Expert_Select set State=#{state} where id=#{id}")
    Integer updateState(@Param("id") String id, @Param("state") Integer state);
}
