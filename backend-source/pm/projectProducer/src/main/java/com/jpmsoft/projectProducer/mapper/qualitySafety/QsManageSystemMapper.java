package com.jpmsoft.projectProducer.mapper.qualitySafety;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.qualitySafety.QsManageSystemDto;
import com.jpmsoft.projectProducer.pojo.entity.qualitySafety.QsManageSystem;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsNodePerson;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface QsManageSystemMapper extends BaseMapper<QsManageSystem> {


    List<QsManageSystemDto> getQsManageSystemList(@Param("params") Map<String, Object> params, @Param("page") IPage<QsManageSystemDto> page);


    QsManageSystemDto getQsManageSystemById(@Param("id") String id);

    String selectConfigByCode(String postDes);

    String getDIdByPId(String pId);

    List<String> getPositionsByMarkAndDId(String mark, String dId);

    List<QsNodePerson> getQsNodePersonByPosition(List<String> positions);

}
