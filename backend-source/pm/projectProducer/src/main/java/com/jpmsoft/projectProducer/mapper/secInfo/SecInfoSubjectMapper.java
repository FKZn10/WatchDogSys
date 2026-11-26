package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecInfoSubjectResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInfoSubject;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

@Mapper
public interface SecInfoSubjectMapper extends BaseMapper<SecInfoSubject> {
    void deleteAllOldByBpId(@Param("bpId") String bpId);

    List<SecInfoSubjectResponse> getSecInfoSubjectsBySecId(@Param("secIds") Collection<String> secIds);
}
