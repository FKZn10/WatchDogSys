package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertSelect;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertSelectVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/8 16:17
 */
public interface SecExpertSelectMapper extends BaseMapper<SecExpertSelect> {

    SecExpertSelectVo getSecExpertSelectById(@Param("id") String id);

    List<SecExpertSelectVo> getSecExpertSelectList(@Param("page") IPage<SecExpertSelectVo> page,
                                                   @Param("params") Map<String, Object> params);


    @Update("update Sec_Expert_Select set State=#{state} where id=#{id}")
    Integer updateState(@Param("id") String id, @Param("state") Integer state);
}
