package com.jpmsoft.projectProducer.mapper.constructionProcedure;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.proCp.ProjectCpRequest;
import com.jpmsoft.common.model.response.UserProjectCpResponse;
import com.jpmsoft.common.model.response.proCp.ProjectCpResponse;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCp;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface ProjectCpMapper extends BaseMapper<ProjectCp> {

    ProjectCp selectByCpdId(@Param("cpdId") String cpdId);

    List<ProjectCpResponse> getProCpList(@Param("page") IPage<ProjectCpResponse> page,@Param("params") Map<String, Object> params);

    ProjectCpResponse getProjectCpByCpId(@Param("cpId") String cpId);

    List<UserProjectCpResponse> statisticalUserProCp(@Param("params") Map<String, Object> params);

    Page<UserProjectCpResponse> statisticalUserProCpPage(@Param("page") IPage<ProjectCpResponse> page, @Param("params") Map<String, Object> params);


    Integer statisticalUserProCpCount(@Param("params") Map<String, Object> params);


    @Select(" select p.p_id from pre_project p where p.state=7 and p.p_id not in (select cp.P_Id from Project_Cp cp where cp.state!=3)")
    List<String> getUnCpProjectIds();

    List<ProjectCpResponse> getProCpLedgerNJCJ(@Param("page") Page<ProjectCpRequest> page, @Param("params") ProjectCpRequest request);

    Integer getProCpLedgerNJCJCount(@Param("params") ProjectCpRequest request);

    Map<String, Object> getProCpLedgerNJCJStats(@Param("params") ProjectCpRequest request);
}
