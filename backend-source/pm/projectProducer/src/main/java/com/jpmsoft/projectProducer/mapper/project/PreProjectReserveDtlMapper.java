package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.PreProjectReserveChangeDTO;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProjectReserveDtl;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PreProjectReserveDtlMapper extends BaseMapper<PreProjectReserveDtl> {
    PreProjectReserveChangeDTO getpreProjectReservePprByIdData(@Param("id") String id);
}
