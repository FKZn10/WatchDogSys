package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecSelectionRecordModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSelectionRecord;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertExtractVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecSelectionRecordMapper extends BaseMapper<SecSelectionRecord> {


    List<Map<String, Object>> getAllValues(@Param("selectClause") String selectClause,@Param("params") Map<String, Object> query);

    List<SecSelectionRecordModel> getSecSelectionRecordList(@Param("page") IPage<SecSelectionRecordModel> page,@Param("params") Map<String, Object> params);

    SecSelectionRecordModel getSecSelectionRecordById(@Param("srId") String srId);

    SecExpertExtractVo getSecExpertExtractById(@Param("srId") String srId);

    SecExpertExtractVo getSecExpertExtractByEsId(@Param("esId") String esId);

    List<SecExpertExtractVo> getSecExpertExtractList(@Param("page") IPage<SecExpertExtractVo> page,
                                                     @Param("params") Map<String, Object> params);


    List<SecSelectionRecordDtlModel>  getSecSelectionRecordDtlModelById(@Param("srId") String srId);

    long querySecDoumentCount(@Param("secId") String secId,
                              @Param("templateCode") String templateCode,
                              @Param("fileTemplateCodeNew") String fileTemplateCodeNew);

    //获取专家抽取里面的开标数据
    List<SecSelectionRecord> getSecExpertBidTimeList(@Param("params") Map<String, Object> params);

    void updateStateById(@Param("state") Integer state, @Param("srId") String srId);

    List<String> getInvitationSchemaCompanyIds(@Param("invitationSchemaId") String invitationSchemaId);

}
