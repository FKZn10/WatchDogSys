package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecDocumentReleaseModel;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDocumentRelease;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.Date;
import java.util.List;
import java.util.Map;


public interface SecDocumentReleaseMapper extends BaseMapper<SecDocumentRelease> {

    List<SecDocumentReleaseModel> getSecDocumentReleaseList(@Param("params") Map<String, Object> params);

    Integer getSecDocumentReleaseListCount(@Param("params") Map<String, Object> params);

    SecDocumentReleaseModel getSecDocumentReleaseById(@Param("sdrId") String sdrId);


    @Update("update  Sec_Document set  D_New_Bid_Time=#{sdrBidOpenTime} where Sec_Id=#{secId}")
    Integer appoval2SyncDocument(@Param("secId") String secId, @Param("sdrBidOpenTime") Date sdrBidOpenTime);
}
