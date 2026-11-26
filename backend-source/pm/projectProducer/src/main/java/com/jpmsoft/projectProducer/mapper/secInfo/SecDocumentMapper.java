package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecModularsGroupByProjectModel;
import com.jpmsoft.common.model.response.SecInfo.SecDocumentResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDocument;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecDocumentMapper extends BaseMapper<SecDocument> {

    List<SecDocumentResponse> getSecDocumentList(@Param("params") Map<String, Object> params);

    List<SecDocumentResponse> getSecDocumentListNJCJ(@Param("params") Map<String, Object> params, @Param("page") IPage<SecDocumentResponse> page);

    Integer getSecDocumentListCount(@Param("params") Map<String, Object> params);

    SecDocumentResponse getSecDocumentBydId(@Param("dId") String dId);

    List<SecDocumentResponse>  getSecDocumentBySecId(@Param("secId") String secId);

    List<SecModularsGroupByProjectModel> getSecDocumentProjectList(@Param("params") Map<String, Object> params);

    Integer getSecDocumentProjectListCount(@Param("params") Map<String, Object> params);

    List<SecDocumentResponse> getSecDocumentListByPids(@Param("pIds") List<String> pIds, @Param("params") Map<String, Object> params);


    List<SecDocumentResponse> getSecDocumentListOne2Many(@Param("params") Map<String, Object> params);

    Integer getSecDocumentListOne2ManyCount(@Param("params") Map<String, Object> params);


    List<SecDocumentResponse> getSecDocumentListClass(@Param("authSql") String authSql, @Param("params") Map<String, Object> params);

    List<Map<String, Object>> getAllValues(@Param("authSql") String authSql, @Param("selectClause") String selectClause, @Param("params") Map<String, Object> params);

    Integer getSecDocumentListClassCount(@Param("authSql") String authSql, @Param("params") Map<String, Object> params);
}
