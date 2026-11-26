package com.jpmsoft.projectProducer.mapper.peInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.PeInfo.PeFormDetailStandardRequest;
import com.jpmsoft.projectProducer.pojo.entity.peInfo.PeFormDetailStandard;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface PeFormDetailStandardMapper extends BaseMapper<PeFormDetailStandard> {

    void deletePeFormDetailStandardByfdId(@Param("fdId") String fdId);

    void deletePeFormToDeleteStandard(@Param("fdIds") List<String> fdIds);

    List<PeFormDetailStandardRequest> getPeFormDetailStandardByFid(@Param("fId") String fId);
}
