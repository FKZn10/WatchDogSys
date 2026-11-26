package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecQeGroupResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQeGroup;
import org.apache.ibatis.annotations.Param;


public interface SecQeGroupMapper extends BaseMapper<SecQeGroup> {

    SecQeGroupResponse getSecQeGroupByQegId(@Param("qegId") String qegId);
}
