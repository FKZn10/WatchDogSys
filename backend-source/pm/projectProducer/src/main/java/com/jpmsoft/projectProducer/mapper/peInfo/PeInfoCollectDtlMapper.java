package com.jpmsoft.projectProducer.mapper.peInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.PeInfoCollectDtlResponse;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfoCollectDtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface PeInfoCollectDtlMapper extends BaseMapper<PeInfoCollectDtl> {

    /**
     * authSql鉴权已取消20221011
     *
     * @param authSql
     * @param pecId
     * @return
     */
    List<PeInfoCollectDtlResponse> getPeInfoCollectDtlListWhenExport(@Param("authSql") String authSql, @Param("pecId") String pecId);

    List<PeInfoCollectDtlResponse> getPeInfoCollectDtlList(@Param("page") IPage<PeInfoCollectDtlResponse> page, @Param("params") Map<String, Object> params);


    List<PeInfoCollectDtlResponse> getPeInfoNotGenerateCollectDtl(@Param("params") Map<String, Object> params);

    Integer getPeInfoNotGenerateCollectDtlCount(@Param("params") Map<String, Object> params);


    void deleteByPecId(@Param("pecId") String pecId);

    void deleteByPeId(@Param("peId") String peId);

    //PeInfoCollectDtlResponse getPeInfoCollectDtlByCpdId(@Param("cpdId") String cpdId);
}
