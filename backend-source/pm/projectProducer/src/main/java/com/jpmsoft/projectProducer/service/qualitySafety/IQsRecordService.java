package com.jpmsoft.projectProducer.service.qualitySafety;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Qs.QsRecordRequest;
import com.jpmsoft.common.model.request.Qs.QualitySafetyStatisticFowYWRequest;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsRecord;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QualitySafetyStatisticFowYWVO;

import java.util.List;

public interface IQsRecordService extends IService<QsRecord> {

    BaseResponse createOrUpdateQsRecord(QsRecordRequest request);

    /**
     * 巡检任务批量操作检查记录
     *
     * @param data
     * @param ptId
     */
    void createOrUpdateQsRecord(List<QsRecordRequest> data, String ptId);

    BaseResponse getQsRecordList(QsRecordRequest request, String uId);

    /**
     * 分页查询质量安全问题台账。
     *
     * @param request 查询条件
     * @param uId 当前用户ID
     * @return 按项目和问题分类汇总的台账
     */
    BaseResponse getQsRecordLedgerList(QsRecordRequest request, String uId);

    BaseResponse getQsRecordByQsrId(String qsrId);

    /**
     * 整改
     * @param request
     * @return
     */
    BaseResponse updateRectifyPic(QsRecordRequest request);


    Boolean canToDeleteQsType(String qstId);


    /**
     * 看板二级页面统计
     *
     * @return
     */
    Object getWorkbenchDetailStatistics(Integer qsrCategory,String teId);

    /**
     * 质量安全二级页面 统计点开后的详情
     *
     * @param request
     * @param teId
     * @return
     */
    BaseResponse getWorkbenchDetailStatisticsList(QsRecordRequest request, String teId);


    BaseResponse getLastSevenDaysStatistics(Integer qsrCategory,String teId);


    BaseResponse getQsMonthlyStatisticsByYear(Integer qsrCategory, String teId);

    /**
     * 统计年度季度月度
     *
     * @param request
     * @return
     */
    BaseResponse getYearQuarterMonthStatistics(QsRecordRequest request);


    BaseResponse updateRecord(QsRecordRequest request);


    BaseResponse getQualitySafetyStatisticFowYW(QualitySafetyStatisticFowYWRequest request);

    List<QualitySafetyStatisticFowYWVO> getQualitySafetyStatisticFowYWExport(QualitySafetyStatisticFowYWRequest request);

    BaseResponse createOrUpdateQsRecordRequest(QsRecordRequest request);

    BaseResponse deleteByQsrId(String qsrId);
}
