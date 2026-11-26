package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordDtlModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordDtlModelPrintVo;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordModel;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecJGSelectionRecordPrintVo;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecJGSelectionRecord;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertExtractVo;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecJGExpertExtractVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface SecJGSelectionRecordMapper extends BaseMapper<SecJGSelectionRecord> {


    long querySecDoumentCount(@Param("secId") String secId,
                              @Param("templateCode") String templateCode,
                              @Param("fileTemplateCodeNew") String fileTemplateCodeNew
    );


    List<SecJGSelectionRecordModel> getSecSelectionRecordList(@Param("page") IPage<SecJGSelectionRecordModel> page,@Param("params") Map<String, Object> params);


    SecJGSelectionRecordModel getSecSelectionRecordById(@Param("srId") String srId);


    SecJGExpertExtractVo getSecExpertExtractById(@Param("srId") String srId);


    SecJGExpertExtractVo getSecExpertExtractByEsId(@Param("esId") String esId);

    List<SecJGExpertExtractVo> getSecExpertExtractList(@Param("page") IPage<SecJGExpertExtractVo> page,@Param("params") Map<String, Object> params);


    List<SecJGSelectionRecordDtlModel> getSecSelectionRecordDtlModelById(@Param("srId") String srId);


    void updateStateById(@Param("state") Integer state, @Param("srId") String srId);

    SecJGSelectionRecordPrintVo getSecJGSelectionRecordPrintVoById(@Param("srId") String srId);

    List<SecJGSelectionRecordDtlModelPrintVo> getSecJGSelectionRecordPrintInsecDtlModelsById(@Param("srId")String srId);

    List<SecJGSelectionRecordDtlModelPrintVo> getSecJGSelectionRecordPrintOutsecDtlModelsById(@Param("srId") String srId);
}
