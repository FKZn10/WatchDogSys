package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecInfoSubjectChangeResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoSubjectChange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SecInfoSubjectChangeMapper extends BaseMapper<SecInfoSubjectChange> {
    void deleteAllOldByBpcId(@Param("bpcId") String bpcId);

    List<SecInfoSubjectChangeResponse> getSecInfoSubjectChangeList(@Param("sicIdList") List<String> sicIdList);
}
