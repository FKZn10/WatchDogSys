package com.jpmsoft.projectProducer.mapper.zdyxy;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.ConstructionCompletionConfirmModel;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.ConstructionCompletionConfirm;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface ConstructionCompletionConfirmMapper extends BaseMapper<ConstructionCompletionConfirm> {
    List<ConstructionCompletionConfirmModel> getList(@Param("params")Map<String, Object> query,
                                                     @Param("page")IPage<ConstructionCompletionConfirmModel> page);

    ConstructionCompletionConfirmModel getDetail(@Param("id")String id);
}
