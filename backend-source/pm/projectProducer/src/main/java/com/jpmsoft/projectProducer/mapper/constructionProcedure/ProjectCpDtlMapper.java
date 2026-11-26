package com.jpmsoft.projectProducer.mapper.constructionProcedure;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.BatchUpdatePerson;
import com.jpmsoft.common.model.request.Plan.GantSectionDTO;
import com.jpmsoft.common.model.request.TimeFrameRequest;
import com.jpmsoft.common.model.response.proCp.ProCpDtlTemporaryResponse;
import com.jpmsoft.common.model.response.proCp.ProjectCpDtlResponse;
import com.jpmsoft.projectProducer.pojo.dto.other.ProjectInfoDTO;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpDtl;
import com.jpmsoft.projectProducer.pojo.entity.constructionProcedure.ProjectCpRpr;
import com.jpmsoft.projectProducer.pojo.entity.multiproject.ProjectCpDtlVo;
import com.jpmsoft.projectProducer.pojo.request.ProjectCpInfoRequest;
import com.jpmsoft.projectProducer.pojo.vo.constructionProcedure.StatisticalProCpAndDtlVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;


public interface ProjectCpDtlMapper extends BaseMapper<ProjectCpDtl> {

    List<ProjectCpDtl> getProCpDtlByPid(@Param("pId") String pId);

    List<ProjectCpDtlResponse> getProCpDtlComplateList(@Param("page") IPage<ProjectCpDtlResponse> page,@Param("params") Map<String, Object> params);

    ProjectCpDtlResponse getCpDtlById(@Param("cpdId") String cpdId);

    /**
     * 统计项目程序详情等数量
     *
     * @return
     */
    StatisticalProCpAndDtlVo statisticalProCpAndDtl(@Param("params") Map<String, Object> params);


    List<ProjectCpDtlResponse> getProCpDtlByCpId(@Param("cpId") String cpId);

    List<String> getProCpDtlIdListByPid(@Param("pId") String pId);

    /**
     * 根据用户查询起详情完成情况
     *
     * @return
     */
    List<ProjectCpDtlResponse> getProCpDtlByUser(@Param("page") IPage<ProjectCpDtlResponse> page,@Param("params") Map<String, Object> params);


    /**
     * 根据用户查询起详情完成情况
     *
     * @return
     */
    List<ProjectCpDtlResponse> getProCpDtlListByCpId(@Param("page") IPage<ProjectCpDtlResponse> page, @Param("params") Map<String, Object> params);


    /**
     * 根据用户查询起详情临期情况
     *
     * @return
     */
    List<ProCpDtlTemporaryResponse> getProCpDtlTemporaryListByuid(@Param("page") IPage<ProCpDtlTemporaryResponse> page, @Param("params") Map<String, Object> params);

    Integer batchUpdateCpDtlPerson(@Param("model") BatchUpdatePerson model);

    Integer batchUpdateCpDtlCreateUser(@Param("model") BatchUpdatePerson model);

    Map<String, Integer> statisticalProCpDtlByTimeFrame(@Param("params") Map<String, Object> params);

    List<ProjectCpDtlResponse> getProjectCpDtlByCpIds(@Param("cpIds") List<String> cpIds);

    /**
     * 定时任务，查询
     *
     * @param codes
     * @return
     */
    List<ProjectCpDtlVo> qryProjectCpDtlByCpIds(@Param("codes") List<String> codes);

    List<String> notPersonIdData(@Param("cpId")  String cpId);
    List<String> notCreateUidData(@Param("cpId")  String cpId);

    Integer updateFileTypeBath(@Param("cpdIds") List<String> cpdIds,@Param("type") String type);

    IPage<ProjectInfoDTO> getProjectInfo(Page<ProjectInfoDTO> page,@Param("request") ProjectCpInfoRequest request);

    List<ProjectCpRpr>  selectProjectCpRprByCpdId(@Param("authSql") String authSql, @Param("cpdId") String cpdId);

     @Select("select *  from ${tableCode} where   ${key}=#{id}")
    Map<String,Object> selectInfoInTable(@Param("tableCode") String tableCode,@Param("key") String key,@Param("id") String id);

    List<GantSectionDTO> getGantSectionListByPid(String pId);

    List<ProjectCpDtlResponse> getAllCpDtlList(@Param("page") Page<ProjectCpDtlResponse> page, @Param("params") TimeFrameRequest request);
}
