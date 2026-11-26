package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysTaskProcessinTime;
import com.jpmsoft.jpbpm.pojo.vo.ApprovalDetailsVo;
import com.jpmsoft.jpbpm.pojo.vo.ApprovalTimelimitVo;
import com.jpmsoft.jpbpm.pojo.vo.RejectStatisticsVo;
import com.jpmsoft.jpbpm.pojo.vo.SysDepartmentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.omg.PortableInterceptor.INACTIVE;

import java.util.List;
import java.util.Map;

@Mapper
public interface SysTaskProcessinTimeMapper extends BaseMapper<SysTaskProcessinTime> {


    Integer batchAddition(@Param("list") List<SysTaskProcessinTime> List, @Param("festivalHoliday") String festivalHoliday, @Param("workTimeType") String workTimeType, @Param("workShift") String workShift, @Param("closingTime") String closingTime);


    List<ApprovalTimelimitVo> getPrescription(@Param("params") Map<String, Object> params);


    Integer getPrescriptionCount(@Param("params") Map<String, Object> params);

    List<ApprovalDetailsVo> getPrescriptionDetails(@Param("params") Map<String, Object> params);


    Integer getPrescriptionDetailsCount(@Param("params") Map<String, Object> params);


    List<ApprovalTimelimitVo> getPrescriptionD(@Param("params") Map<String, Object> params);


    Integer getPrescriptionDCount(@Param("params") Map<String, Object> params);

    List<Map<String, String>> getUserInfo(@Param("uId") String uId, @Param("teId") String teId);


    Map<String, String> getDName(@Param("fileId") String fileId, @Param("teId") String teId);


    List<String> getTpId(@Param("fileIds") List<String> fileIds, @Param("teId") String teId);

    List<SysDepartmentVo> getDNameByUid(@Param("uIds") List<String> uIds, @Param("teId") String teId);


    List<SysDepartmentVo> getDNameByPid(@Param("pIds") List<String> pIds, @Param("teId") String teId);


    @Update("update  Sys_Task_Processin_Time set Tpt_Duration=#{tptDuration}  where tp_id=#{tpId}")
    Integer updateSysTaskProcessinTimeByTpId(@Param("tpId") String tpId, @Param("tptDuration") String tptDuration);


    @Update("update  Sys_Task_Processin_Time set Tpt_Approval_Time='${approvalTime}',Tpt_Approval_State='${state}' where Tpt_File_Id='${fileId}'")
    Integer updateTaskProcessinTime(@Param("approvalTime") String approvalTime, @Param("state") String state, @Param("fileId") String fileId);


    List<RejectStatisticsVo> getRejectStatisticsD(@Param("params") Map<String, Object> params);

    Integer getRejectStatisticsDCount(@Param("params") Map<String, Object> params);

    List<RejectStatisticsVo> getRejectStatisticsPD(@Param("params") Map<String, Object> params);

    Integer getRejectStatisticsPDCount(@Param("params") Map<String, Object> params);

    List<RejectStatisticsVo> getRejectStatisticsUD(@Param("params") Map<String, Object> params);

    Integer getRejectStatisticsUDCount(@Param("params") Map<String, Object> params);

    List<RejectStatisticsVo> getRejectStatisticsRUD(@Param("params") Map<String, Object> params);

    Integer getRejectStatisticsRUDCount(@Param("params") Map<String, Object> params);


    List<SysTaskProcessinTime> getTaskProcessinTimeNotCode(@Param("teId") String teId, @Param("starTime") String starTime, @Param("endTime") String endTime);


    void updateTptFileCodeByFileIds(@Param("id") String id);
}
