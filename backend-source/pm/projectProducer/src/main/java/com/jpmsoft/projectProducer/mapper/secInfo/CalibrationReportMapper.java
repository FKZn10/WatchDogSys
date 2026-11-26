package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.CalibrationReport;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.CalibrationReportVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author 李文涛 aaron
 * @date 2022/07/08 09:31
 */
public interface CalibrationReportMapper extends BaseMapper<CalibrationReport> {

    List<CalibrationReportVo> getCalibrationReportList(@Param("page") IPage<CalibrationReportVo> page, @Param("params") Map<String, Object> params);

    CalibrationReportVo getCalibrationReportById(@Param("id") String id);

    CalibrationReportVo getCalibrationReportBySecId(@Param("secId") String secId);
}




