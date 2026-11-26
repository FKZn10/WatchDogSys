package com.jpmsoft.projectProducer.mapper.materialsEquipment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.MeEnter.MeInspectionReportResponse;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeInspectionReport;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface MeInspectionReportMapper extends BaseMapper<MeInspectionReport> {


    List<MeInspectionReportResponse> getMeInspectionReportList(@Param("page") IPage<MeInspectionReportResponse> page, @Param("params") Map<String, Object> params);

    MeInspectionReportResponse getMeInspectionReportByIrId(@Param("irId") String irId);
}
