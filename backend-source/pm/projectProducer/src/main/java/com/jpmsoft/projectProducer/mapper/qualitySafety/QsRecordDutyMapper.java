package com.jpmsoft.projectProducer.mapper.qualitySafety;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.Qs.QsRecordDutyDTO;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsRecordDuty;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/20 13:42
 */
public interface QsRecordDutyMapper extends BaseMapper<QsRecordDuty> {


    List<QsRecordDutyDTO> getQsRecordDutyByQsrIds(@Param("ids") List<String> ids);
}
