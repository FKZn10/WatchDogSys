package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.request.SecInfo.SecWorkSchemaDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecWorkSchema;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/23 9:31
 */
public interface SecWorkSchemaMapper extends BaseMapper<SecWorkSchema> {


    List<SecWorkSchemaDTO> getSecWorkSchemaList(@Param("page")  IPage<SecWorkSchemaDTO> page,@Param("params") Map<String, Object> params);

    SecWorkSchemaDTO getSecWorkSchemaByWsId(@Param("wsId") String wsId);

    List<SecWorkSchemaDTO>  getSecWorkSchemaBySecId(@Param("secId") String secId);

    List<SecWorkSchemaDTO> getSecWorkSchemaListClass(@Param("authSql") String authSql, @Param("page") IPage<SecWorkSchemaDTO> page,@Param("params") Map<String, Object> query);

    List<Map<String, Object>> getAllValues(@Param("authSql") String authSql, @Param("selectClause") String selectClause,@Param("params") Map<String, Object> query);
}
