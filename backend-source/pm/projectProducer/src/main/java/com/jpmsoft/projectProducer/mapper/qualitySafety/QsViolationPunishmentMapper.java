package com.jpmsoft.projectProducer.mapper.qualitySafety;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.Qs.QsViolationPunishmentResponse;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsViolationPunishment;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface QsViolationPunishmentMapper extends BaseMapper<QsViolationPunishment> {

    List<QsViolationPunishmentResponse> getQsViolationPunishmentList(@Param("page")  IPage<QsViolationPunishmentResponse> page,@Param("params") Map<String, Object> params);


    QsViolationPunishmentResponse getQsViolationPunishmentById(@Param("vpId") String vpId);
}
