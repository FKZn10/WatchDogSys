package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.TaskProcessResponse;
import com.jpmsoft.jpbpm.pojo.dto.WorkFlowFileDto;
import com.jpmsoft.jpbpm.pojo.entity.TaskProcessin;
import com.jpmsoft.jpbpm.pojo.vo.ApprovalDurationVo;
import com.jpmsoft.jpbpm.pojo.vo.SysTaskProcessinInfoVo;
import com.jpmsoft.jpbpm.pojo.vo.TaskCoordinationTaskVo;
import com.jpmsoft.jpbpm.pojo.vo.TaskStepsVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

@Mapper
public interface TaskProcessinMapper extends BaseMapper<TaskProcessin> {


    List<TaskProcessResponse> getUserTaskProcessList(@Param("params") Map<String, Object> params, @Param("tableCodes") Map<String, Object> tableCodes);

    Integer getUserTaskProcessListCount(@Param("params") Map<String, Object> params, @Param("tableCodes") Map<String, Object> tableCodes);

    /**
     * 获取用户已办任务列表（去重版）
     * 对于同一个tp_user和tp_entityid，只保留最新的一条办理记录
     *
     * @param params 查询参数
     * @param tableCodes 分表代码映射
     * @return 去重后的任务列表
     */
    List<TaskProcessResponse> getUserTaskProcessListDistinct(@Param("params") Map<String, Object> params, @Param("tableCodes") Map<String, Object> tableCodes);

    /**
     * 获取用户已办任务列表总数（去重版）
     *
     * @param params 查询参数
     * @param tableCodes 分表代码映射
     * @return 总数
     */
    Integer getUserTaskProcessListDistinctCount(@Param("params") Map<String, Object> params, @Param("tableCodes") Map<String, Object> tableCodes);

    List<TaskProcessResponse> getTaskProcessList(@Param("params") Map<String, Object> params, @Param("tableCodes") Map<String, Object> tableCodes);

    Integer getTaskProcessListCount(@Param("params") Map<String, Object> params, @Param("tableCodes") Map<String, Object> tableCodes);


    @Select("select tp_id,tp_flowid,tp_actid,tp_entityid,tp_dotime,tp_user,ActCode from  ${table} left  join  SYS_FlowActivity  on ${table}.tp_actid=SYS_FlowActivity.ActID where tp_entityid=#{tpEntityid} and tp_type='提交' order  by   tp_dotime desc;")
    List<Map<String, Object>> getSomeFieldsTaskProcessin(@Param("tpEntityid") String tpEntityid, @Param("table") String table);

    List<ApprovalDurationVo> getApprovalDuration(@Param("tpEntityid") String tpEntityid, @Param("teId") String teId, @Param("tableCode") String tableCode);

    List<TaskStepsVo> getApprovalTask(@Param("tpEntityid") String tpEntityid, @Param("teId") String teId);


    @Select("select min(TP_DOTIME) as minDoTime,max(TP_DOTIME) as maxDotiome  from  ${table}")
    Map<String, Object> getMinTimeAndMaxTime(@Param("table") String table);

    List<SysTaskProcessinInfoVo> getTaskProcessinNotTime(@Param("fileIds") List<String> fileIds, @Param("teId") String teId);


    @Select("select  Con_Id as  conId,Con_Name as conName from  Con_Contract  where   Con_Name like concat('%',#{conName},'%')")
    List<Map<String, String>> getContractInfo(@Param("conName") String conName);


    List<Map<String, String>> getContractInfoByIds(@Param("conIds") List<String> conIds);


    List<TaskCoordinationTaskVo> getUserTaskProcessListCJ(@Param("params") Map<String, Object> params);

    Integer getUserTaskProcessCountCJ(@Param("params") Map<String, Object> params);

    List<String> getUserTaskProcessKeyCJ(@Param("params") Map<String, Object> params,@Param("key") String key);

    List<WorkFlowFileDto> getApprovalUserByIds(@Param("ids") List<String> ids);
}
