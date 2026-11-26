package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.TrainStaffDTO;
import com.jpmsoft.projectProducer.pojo.dto.other.TrainVideoDTO;
import com.jpmsoft.projectProducer.pojo.entity.other.ApplyRole;
import com.jpmsoft.projectProducer.pojo.entity.other.TrainStaff;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface TrainRecordMapper {



    List<TrainStaffDTO> getTrainStaffAllList(@Param("params") TrainStaff trainStaff);

    List<TrainVideoDTO> trainStatistical();

    List<TrainVideoDTO> getTrainVideoAllList(@Param("state") int state);


}
