package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecProjectResponse;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecProject;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;


public interface SecProjectMapper extends BaseMapper<SecProject> {

    @Delete("delete from  Sec_Project where Sec_Id in (select Sec_Id from  Sec_Info where Bp_Id=#{bpId})")
    Integer deleteAllOldByBpId(@Param("bpId") String bpId);


    List<Map<String, String>> getSecInfoListGroupByProject(@Param("params") Map<String, Object> params);

    Integer getSecInfoListGroupByProjectCount(@Param("params") Map<String, Object> params);

    Integer checkSecProjectCp(@Param("secId") String secId, @Param("cpdCodes") List<String> cpdCodes);

    int havingSecProjectCp(@Param("secId") String secId, @Param("cpdCodes") List<String> cpdCodes);

    List<SecProjectResponse> getSecInfoProject(@Param("secId") String secId);
}
