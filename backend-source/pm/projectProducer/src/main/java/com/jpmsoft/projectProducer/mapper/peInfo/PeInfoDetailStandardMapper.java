package com.jpmsoft.projectProducer.mapper.peInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.requestAndResponse.PeInfo.PeInfoDetailStandardModel;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeInfoDetailStandard;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PeInfoDetailStandardMapper extends BaseMapper<PeInfoDetailStandard> {

    void deletePeInfoStandardByPedIds(@Param("pedIds") List<String> pedIds);

    void insertBatchs(@Param("models") List<PeInfoDetailStandardModel> models);

    List<PeInfoDetailStandardModel> getPeInfoDetailStandardByPeId(@Param("peId") String peId);
}
