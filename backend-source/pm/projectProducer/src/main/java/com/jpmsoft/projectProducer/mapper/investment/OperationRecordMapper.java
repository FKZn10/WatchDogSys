package com.jpmsoft.projectProducer.mapper.investment;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.investment.OperationRecordDTO;
import com.jpmsoft.projectProducer.pojo.entity.investment.OperationRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OperationRecordMapper extends BaseMapper<OperationRecord> {
    List<OperationRecordDTO> getOperationRecordList(@Param("entityId") String entityId);
}
