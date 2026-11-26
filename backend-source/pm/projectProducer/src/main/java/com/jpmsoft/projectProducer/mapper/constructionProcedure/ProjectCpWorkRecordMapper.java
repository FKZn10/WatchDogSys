package com.jpmsoft.projectProducer.mapper.constructionProcedure;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.proCp.ProCpWorkRecordResponse;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpWorkRecord;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface ProjectCpWorkRecordMapper extends BaseMapper<ProjectCpWorkRecord> {

    List<ProCpWorkRecordResponse> getProCpWorkRecordList(@Param("page") IPage<ProCpWorkRecordResponse> page, @Param("params") Map<String, Object> params);


    Integer getCpWorkRecordCountByCpId(@Param("cpId") String cpId);
}
