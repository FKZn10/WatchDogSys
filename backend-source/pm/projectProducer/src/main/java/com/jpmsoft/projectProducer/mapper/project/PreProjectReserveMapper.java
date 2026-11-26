package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.project.PreProjectReserveDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectReserve;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/6 14:21
 */
public interface PreProjectReserveMapper extends BaseMapper<PreProjectReserve> {

    List<PreProjectReserveDTO> getProjectReserveList(@Param("page") IPage<PreProjectReserveDTO> page, @Param("params") PreProjectReserveDTO params,@Param("authSql") String authSql);

    PreProjectReserveDTO getProjectReserveById(@Param("id") String  id);

    @Select("select  count(*) from pre_project where ppr_id=#{id} and state!=3")
    Integer projectUser(@Param("id")  String id);


    List<Object> getpprNameListByCode(@Param("value") String dbValue,@Param("params") PreProjectReserveDTO params,@Param("uId")String uId,@Param("authSql") String authSql);

    List<Object> getPprModelListByCode(@Param("value")String dbValue ,@Param("params") PreProjectReserveDTO params,@Param("uId")String uId,@Param("authSql") String authSql);

    List<Object> getPprRoughListByCode(@Param("value")String dbValue,@Param("params") PreProjectReserveDTO params,@Param("uId")String uId,@Param("authSql") String authSql);

    List<Object> getPprReserveCpyNameListByCode(@Param("value")String dbValue,@Param("params") PreProjectReserveDTO params,@Param("uId")String uId,@Param("authSql") String authSql);

    List<Object> gePprManagerUnitNameListByCode(@Param("value")String dbValue,@Param("params") PreProjectReserveDTO params,@Param("uId")String uId,@Param("authSql") String authSql);

    List<Object> getPprManagerDepNameListByCode(@Param("value")String dbValue,@Param("params") PreProjectReserveDTO params,@Param("uId")String uId,@Param("authSql") String authSql);

    List<Object> getPprReserveStageListByCode(@Param("value")String dbValue,@Param("params") PreProjectReserveDTO params,@Param("uId")String uId,@Param("authSql") String authSql);

    List<Object> getPprReserveStatusListByCode(@Param("value")String dbValue,@Param("params") PreProjectReserveDTO params,@Param("uId")String uId,@Param("authSql") String authSql);

    List<Object> getPprPlanTimeListByCode(@Param("value")String dbValue,@Param("params") PreProjectReserveDTO params,@Param("uId")String uId,@Param("authSql") String authSql);

    List<Object> getCreateUnameListByCode(@Param("value")String dbValue,@Param("params") PreProjectReserveDTO params,@Param("uId")String uId,@Param("authSql") String authSql);

    List<Object> getCreateTimeListByCode(@Param("value")String dbValue,@Param("params") PreProjectReserveDTO params,@Param("uId")String uId,@Param("authSql") String authSql);

    List<Object> getStateByCode(@Param("value")String dbValue,@Param("params") PreProjectReserveDTO params,@Param("uId")String uId,@Param("authSql") String authSql);

    List<Object> getValueByCode(@Param("value")String dbValue,@Param("params") PreProjectReserveDTO params,@Param("uId")String uId,@Param("authSql") String authSql);
}
