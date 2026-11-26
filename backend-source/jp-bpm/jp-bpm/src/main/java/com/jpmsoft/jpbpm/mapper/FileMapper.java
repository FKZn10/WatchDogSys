package com.jpmsoft.jpbpm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SysFileResponse;
import com.jpmsoft.jpbpm.pojo.entity.File;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Mapper
public interface FileMapper extends BaseMapper<File> {

    @Update("update  ${table} set FileFlowStatus=#{FileFlowStatus},Flow_Id=#{FlowID} where  FileID=#{FileID}")
    Integer updateFileFlowStatusByFileId(@Param("table") String table,@Param("FileFlowStatus") Integer FileFlowStatus, @Param("FileID") String FileID, @Param("FlowID") String FlowID);


    Integer updateFileFlowStatusByFileIdList(@Param("table") String table,@Param("FileFlowStatus") Integer FileFlowStatus, @Param("FileIds") List<String>  FileIds);


    @Update("update  ${table} set FileFlowStatus=#{FileFlowStatus},Flow_Id=#{FlowID} where  FileID  in (${FileIds})")
    Integer updateFileFlowStatusByFileIds(@Param("table") String table,@Param("FileFlowStatus") Integer FileFlowStatus, @Param("FileIds") String FileIds, @Param("FlowID") String FlowID);


    List<SysFileResponse> getFileList(@Param("params") Map<String, Object> params);
    List<String> getFileTitles(@Param("params") Map<String, Object> params,@Param("key") String key);

    Integer getFileListCount(@Param("params") Map<String, Object> params);

    /**
     * 更新业务表流程状态
     *
     * @param table      业务表表名
     * @param primaryKey 业务表表主键
     * @param stateKey   业务表流程状态字段
     * @param timeKey    业务表流程状态时间字段
     * @param id         业务表主键值
     * @param state      业务表流程状态值
     * @param time       业务表流程时间值
     * @return
     */
    @Update("update ${table} set ${stateKey}=#{state},${timeKey}=#{time} where ${primaryKey}=#{id}")
    Integer updateBusinessStateAndStateTime(@Param("table") String table, @Param("primaryKey") String primaryKey, @Param("stateKey") String stateKey, @Param("timeKey") String timeKey,
                                            @Param("id") String id, @Param("state") Integer state, @Param("time") Date time);


    /**
     * 更新业务表流程状态
     *
     * @param table      业务表表名
     * @param primaryKey 业务表表主键
     * @param stateKey   业务表流程状态字段
     * @param timeKey    业务表流程状态时间字段
     * @param ids        业务表主键值
     * @param state      业务表流程状态值
     * @param time       业务表流程时间值
     * @return
     */
    @Update("update ${table} set ${stateKey}=#{state},${timeKey}=#{time} where ${primaryKey}  in (${ids})")
    Integer updateBusinessStateAndStateTimeBath(@Param("table") String table, @Param("primaryKey") String primaryKey, @Param("stateKey") String stateKey, @Param("timeKey") String timeKey,
                                                @Param("ids") String ids, @Param("state") Integer state, @Param("time") Date time);


    /**
     * 更新业务表流程状态
     *
     * @param table      业务表表名
     * @param primaryKey 业务表表主键
     * @param stateKey   业务表流程状态字段
     * @param id         业务表主键值
     * @param state      业务表流程状态值
     * @return
     */
    @Update("update ${table} set ${stateKey}=#{state} where ${primaryKey}=#{id}")
    Integer updateBusinessState(@Param("table") String table, @Param("primaryKey") String primaryKey, @Param("stateKey") String stateKey,
                                @Param("id") String id, @Param("state") Integer state);

    /**
     * 更新业务表流程状态
     *
     * @param table      业务表表名
     * @param primaryKey 业务表表主键
     * @param stateKey   业务表流程状态字段
     * @param ids        业务表主键值
     * @param state      业务表流程状态值
     * @return
     */
    @Update("update ${table} set ${stateKey}=#{state} where ${primaryKey} in (${ids})")
    Integer updateBusinessStateBath(@Param("table") String table, @Param("primaryKey") String primaryKey, @Param("stateKey") String stateKey,
                                    @Param("ids") String ids, @Param("state") Integer state);

    /**
     * 更新合同表流程状态以及后置生成的编码
     * 如果已经有编码值则不生成
     *
     * @param table      业务表表名
     * @param primaryKey 业务表表主键
     * @param stateKey   业务表流程状态字段
     * @param timeKey    业务表流程状态时间字段
     * @param id         业务表主键值
     * @param state      业务表流程状态值
     * @param time       业务表流程时间值
     * @param code       编码
     * @return
     */
    @Update("update ${table} set ${stateKey}=#{state},${timeKey}=#{time},Con_Code=#{code} where ${primaryKey}=#{id} and (Con_Code is null or Con_Code='')")
    Integer updateContractStateAndStateTime(@Param("table") String table, @Param("primaryKey") String primaryKey, @Param("stateKey") String stateKey, @Param("timeKey") String timeKey,
                                            @Param("id") String id, @Param("state") Integer state, @Param("time") Date time, @Param("code") String code);

    /**
     * 更新合同表流程状态以及后置生成的编码
     * 如果已经有编码值则不生成
     *
     * @param table      业务表表名
     * @param primaryKey 业务表表主键
     * @param stateKey   业务表流程状态字段
     * @param id         业务表主键值
     * @param state      业务表流程状态值
     * @param code       编码
     * @return
     */
    @Update("update ${table} set ${stateKey}=#{state},${timeKey}=#{time},Con_Code=#{code} where ${primaryKey}=#{id} and (Con_Code is null or Con_Code='')")
    Integer updateContractState(@Param("table") String table, @Param("primaryKey") String primaryKey, @Param("stateKey") String stateKey,
                                @Param("id") String id, @Param("state") Integer state, @Param("code") String code);


    @Update("update Pre_Project set P_Status='1' where P_Id=#{id}")
    Integer updateProjectStatus(@Param("id") String id);

    @Update("update   ${table} set FileFlowStatus=#{fileFlowStatus} where FileID  in (select  tp_id   from  Sys_Task_Processin where  tp_entityid=#{tpEntityId})")
    Integer updateFileByFIleIds(@Param("table") String table,@Param("tpEntityId") String tpEntityId, @Param("fileFlowStatus") Integer fileFlowStatus);


    List<Map<String, Object>> getFileByCode(@Param("tCodes") List<String> tCodes, @Param("uId") String uId, @Param("teId") String teId ,@Param("tableCode") String tableCode);


    SysFileResponse getFileByFileId(@Param("fileId") String fileId,@Param("tableCode") String tableCode);


    Map<String, Object> getMinTimeAndMaxTime(@Param("teId") String teId,@Param("teId") String tableCode);


    @Select("select *  from  ${table} where  ${primaryKey}=#{id}")
    Map<String, Object> getData(@Param("table") String table, @Param("primaryKey") String primaryKey, @Param("id") String id);

    @Select("select *  from  ${table} where  ${primaryKey}  in (${idStr})")
    List<Map<String, Object>> getDataList(@Param("table") String table, @Param("primaryKey") String primaryKey, @Param("idStr") String idStr);

    @Select("select Con_Is_History from Con_Contract where con_id=#{id}")
    Integer contractHistory(@Param("id") String id);

    String queryDangerousProjectId(@Param("detailId") String detailId);

    List<String> queryDangerousProjectIds(@Param("detailIds") List<String> detailIds);
}
