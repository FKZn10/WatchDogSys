package com.jpmsoft.projectProducer.mapper.qualitySafety;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.response.Qs.QsRecordResponse;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.ProSafetyLedgerDTO;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.ProSafetyLedgerDtlDTO;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsRecordDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsPlanTask;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsRecord;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsPlanTaskNewVo;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsPlanTaskSmsVo;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsPlanTaskVo;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsRecordVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/9/20 14:41
 */
public interface QsPlanTaskMapper extends BaseMapper<QsPlanTask> {

    List<QsPlanTaskVo> getQsPlanTaskList(@Param("page") IPage<QsPlanTaskVo> page, @Param("params") Map<String, Object> params);

    QsPlanTaskVo getQsPlanTaskListStatistics(@Param("params") Map<String, Object> params);


    List<QsPlanTaskVo> getQsPlanTaskListExport( @Param("params") Map<String, Object> params);

    QsPlanTaskVo getQsPlanTaskById(@Param("id") String id);

    IPage<QsPlanTaskNewVo> getQsPlanTaskPage(@Param("page") IPage<QsPlanTaskNewVo> page, @Param("params") Map<String, Object> params);

    List<ProSafetyLedgerDtlDTO> getProSafetyLedgerPage( @Param("params") Map<String, Object> query);

    List<ProSafetyLedgerDtlDTO> getProSafetyLedgerPageExport(@Param("params") Map<String, Object> query);


    List<ProSafetyLedgerDtlDTO> getProSafetyLedgerPageSumTotal(@Param("params") Map<String, Object> query);


    ProSafetyLedgerDTO getProSafetyLedger(@Param("params") Map<String, Object> query);

    List<QsRecordVo> getQualitySafetyRecordList(@Param("params") Map<String, Object> params);

    List<QsPlanTaskVo> getQsPlanTaskLists(@Param("projectId")String projectId,@Param("companyId")String companyId,
                                          @Param("startTime")Date startTime,@Param("endTime")Date endTime);

    List<QsRecordResponse> getQsRecord2(@Param("id")String id,@Param("qsTypeId") String qsTypeId);

    List<QsRecordResponse> getHighQsRecordList(@Param("page") IPage<QsRecordDto> page, @Param("params") Map<String, Object> params);

    List<QsRecordResponse> getHighQsRecordTzList(@Param("page") IPage<QsRecordDto> page, @Param("params") Map<String, Object> params);

    List<QsRecord> getQsPlanTaskQsRecordById(@Param("id")String id);

    List<QsPlanTaskVo> selectQsPlanTaskList(@Param("pId") String pId, @Param("ptType") String ptType,@Param("format") String format);

    List<ConContract> getConContractList(@Param("projectIds")List<String> projectIds);

    List<QsPlanTask> selectLists(@Param("projectIds")List<String> projectIds, @Param("xcStart")Date xcStart, @Param("xcEnd")Date xcEnd, @Param("ptCategory")String ptCategory);

    List<QsRecord> selectQsRecordLists(@Param("projectIds")List<String> projectIds,@Param("xcStart")Date xcStart, @Param("xcEnd")Date xcEnd, @Param("ptCategory")String ptCategory);

    List<QsPlanTaskSmsVo> selectListWithoutRectify();

    List<ConContract> getConContractListAll(@Param("projectIds")List<String> projectIds);

    List<QsPlanTask> selectListsAll(@Param("projectIds")List<String> projectIds, @Param("xcStart")Date xcStart,
                                    @Param("xcEnd")Date xcEnd, @Param("ptCategory")String ptCategory,
                                    @Param("qstIds")List<String> qstIds, @Param("ptType")String ptType);

    List<QsRecord> selectQsRecordListsAll(@Param("projectIds")List<String> projectIds, @Param("xcStart")Date xcStart, @Param("xcEnd")Date xcEnd, @Param("ptCategory")String ptCategory,
                                          @Param("qstIds")List<String> qstIds, @Param("ptType")String ptType);

    List<QsPlanTaskVo> getProSafetyLedgerPageNew(@Param("params") Map<String, Object> query);

    List<QsRecordResponse> getQsRecordList(@Param("qstIds")List<String> qstIds);

    @Update("update qs_plan_task set Pt_Rectify_Actual_Time = #{ptRectifyActualTime}" +
            ",Overdue_Days=#{overdueDays} ,Pt_Rectify_Require_Time=#{ptRectifyRequireTime} where id = #{id}")
    void updateTimeById(QsPlanTask d);



    @Select("select * from Qs_Plan_Task where (state!=3 and state!=7 and state!=0) or (state=7 and (out_Time_Status='2' or out_Time_Status='3'))")
    List<QsPlanTask> selectRefreshOutTimeStatus();

    List<QsPlanTaskVo> getQsPlanTaskListSH(@Param("page") Page<QsPlanTaskVo> page, @Param("params") Map<String, Object> query);
}