package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SysFlowResponse;
import com.jpmsoft.jpbpm.pojo.entity.Flow;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface FlowMapper extends BaseMapper<Flow> {

    List<SysFlowResponse> getFlowList(@Param("params") Map<String, Object> params);

    Integer getFlowListCount(@Param("params") Map<String, Object> params);

    List<Flow> getSomeFieldsFlow(@Param("tCode") String tCode,@Param("teId") String teId);


    @Select("select   *   from ${tTable} where  ${idCode}=#{fileId};")
    Map<String, Object> getData(@Param("idCode") String idCode, @Param("tTable") String tTable, @Param("fileId") String fileId);
}
