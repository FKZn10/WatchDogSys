package com.jpmsoft.projectProducer.mapper.oa;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Oa.ReportedPersonnelModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ReportedPersonnel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;


public interface ReportedPersonnelMapper extends BaseMapper<ReportedPersonnel> {


    List<ReportedPersonnelModel> getReportedPersonnelList(@Param("page") IPage<ReportedPersonnelModel> page, @Param("params") Map<String, Object> params);

    ReportedPersonnelModel getReportedPersonnelById(@Param("rpId") String rpId);


    @Update("update sys_position_user_p set pp_id=#{newPpid},u_id=#{newUid} where pp_id=#{oldPpid} and u_id=#{oldUid}")
    Integer updatePositionUserP(@Param("oldUid") String oldUid, @Param("oldPpid") String oldPpid, @Param("newUid") String newUid, @Param("newPpid") String newPpid);
}
