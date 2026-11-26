package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.CarbonCopyResponse;
import com.jpmsoft.jpbpm.pojo.dto.AgencyStatisticsDto;
import com.jpmsoft.jpbpm.pojo.entity.CarbonCopy;
import com.jpmsoft.jpbpm.pojo.vo.AgencyStatisticsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CarbonCopyMapper extends BaseMapper<CarbonCopy> {

    List<CarbonCopyResponse> getUserCarbonCopyList(@Param("params") Map<String, Object> params);

    Integer getUserCarbonCopyListCount(@Param("params") Map<String, Object> params);

    List<CarbonCopyResponse> getCarbonCopyList(@Param("params") Map<String, Object> params);

    Integer getCarbonCopyListCount(@Param("params") Map<String, Object> params);

    List<AgencyStatisticsVo> statisticsUserAgency(@Param("params") AgencyStatisticsDto params,@Param("teId") String teId);

    List<Map> getDataToSql(@Param("sqlStr") String sqlStr);

    List<String>  getUserCarbonCopyTitle(@Param("params") Map<String, Object> params,@Param("key") String key);

    List<String> getSupervisionCarbonCopyUids(@Param("depCodes") List<String> depCodes, @Param("positionCodes") List<String> positionCodes, @Param("pId") String pId, @Param("unitIds") List<String> unitIds);
}
