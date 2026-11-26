package com.jpmsoft.projectProducer.mapper.qualitySafety;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import com.jpmsoft.common.model.request.Qs.QualitySafetyStatisticFowYWRequest;
import com.jpmsoft.common.model.response.Qs.QsRecordResponse;
import com.jpmsoft.projectProducer.pojo.entity.multiproject.MpcQualityAndSafety;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsRecord;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QualitySafetyStatisticFowYWVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface QsRecordMapper extends BaseMapper<QsRecord> {

    List<QsRecordResponse> getQsRecordList(@Param("page") IPage<QsRecordResponse> page, @Param("params") Map<String, Object> params);

    List<QsRecordResponse> getQsRecordLedgerList(@Param("page") IPage<QsRecordResponse> page,
                                                  @Param("params") Map<String, Object> params);

    QsRecordResponse getQsRecordByQsrId(@Param("qsrId") String qsrId);

    Map<String, Integer> getWorkbenchDetailStatistics(@Param("qsrCategory") Integer qsrCategory, @Param("teId") String teId);


    /**
     * 最近七天每日新增（按创建时间统计）
     *
     * @return
     */
    Map<String, Integer> getLastSevenDaysCreate(@Param("qsrCategory") Integer qsrCategory, @Param("teId") String teId);


    /**
     * 最近七天每日新增（按检查时间统计）
     *
     * @return
     */
    Map<String, Integer> getLastSevenDaysCreateOther(@Param("qsrCategory") Integer qsrCategory, @Param("teId") String teId);

    /**
     * 最近七天每日关闭
     *
     * @return
     */
    Map<String, Integer> getLastSevenDaysClose(@Param("qsrCategory") Integer qsrCategory, @Param("teId") String teId);


    List<Map<String, Object>> getMonthlyCreateStats(@Param("qsrCategory") Integer qsrCategory, @Param("teId") String teId, @Param("timeColumn") String timeColumn);

    List<Map<String, Object>> getMonthlyCloseStats(@Param("qsrCategory") Integer qsrCategory, @Param("teId") String teId);

    /**
     * 统计年度 季度 月度
     *
     * @param qsrCategory
     * @param pConstructionUnit
     * @param pCompetentDepartment
     * @return
     */
    List<Map<String, Integer>> getYearQuarterMonthStatistics(@Param("qsrCategory") Integer qsrCategory, @Param("pConstructionUnit") String pConstructionUnit,
                                                             @Param("pCompetentDepartment") String pCompetentDepartment, @Param("teId") String teId);

    MpcQualityAndSafety countMpcQualityAndSafety();

    void updateRectifyStatus(@Param("qsrIdList") List<String> qsrIdList, @Param("qsrRectifyStatus") String qsrRectifyStatus);

    void updateRectifyStatusByIds(@Param("qsRecordList") List<QsRecord> qsRecordList);

    Page<QualitySafetyStatisticFowYWVO> countGroupByQstTypeIdList(@Param("request") QualitySafetyStatisticFowYWRequest request, @Param("typeIdList") List<String> typeIdList, @Param("page") Page page);
    List<QualitySafetyStatisticFowYWVO> countGroupByQstTypeIdListSum(@Param("request") QualitySafetyStatisticFowYWRequest request, @Param("typeIdList") List<String> typeIdList);

    List<QualitySafetyStatisticFowYWVO> countGroupByQstTypeIdListImport(@Param("request") QualitySafetyStatisticFowYWRequest request, @Param("typeIdList") List<String> typeIdList);

    List<QsRecordResponse> getWorkbenchDetailStatisticsList(@Param("page") Page<QsRecordResponse> page, @Param("params") QsRecordRequest request);
}
