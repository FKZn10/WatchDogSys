package com.jpmsoft.projectProducer.mapper.zjt;

import com.jpmsoft.common.model.requestAndResponse.zjt.ProjectUpStatisticsDTO;
import com.jpmsoft.common.model.requestAndResponse.zjt.ProjectUpStatisticsParentDTO;
import com.jpmsoft.projectProducer.pojo.vo.zjt.ZjtExportDataVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ZjtStatisticsMapper {

    List<ProjectUpStatisticsDTO> getProjectUpStatistics(@Param("params") ProjectUpStatisticsDTO params,
                                                        @Param("start") int start, @Param("limit") int limit);


    List<ProjectUpStatisticsDTO> getProjectUpCpyInfo(@Param("params") ProjectUpStatisticsDTO params);

    ProjectUpStatisticsParentDTO getProjectUpStatisticsTotal(@Param("params") ProjectUpStatisticsDTO params);

    List<ZjtExportDataVO> exportProject(@Param("params") Map<String, Object> params);
}
