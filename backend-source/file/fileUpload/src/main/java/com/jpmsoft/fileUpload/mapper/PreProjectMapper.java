package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.dto.ProProjectCodeDto;
import com.jpmsoft.fileUpload.pojo.entity.PreProject;
import com.jpmsoft.fileUpload.pojo.entity.SysTableColumn;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;
import java.util.Set;


public interface PreProjectMapper extends BaseMapper<PreProject> {

    List<ProProjectCodeDto> getPidByProjectCodes(@Param("pProjectCodes") List<String> pProjectCodes,@Param("pCodes") List<String> pCodes);


    @Select("select count(*) from OA_Work_Report where Wr_Year=#{year} and Wr_Quarter=#{quarter} and Wr_State!=3")
    Integer  havingOaQuarterlyReport(@Param("year") Integer year,@Param("quarter") Integer quarter);

    List<Map> getPreProjectByIds(@Param("sql") String sql,@Param("ids") List<String> ids);

    /**
     * 根据项目名称忽略空格查询项目
     * @param projectName
     * @return
     */
    PreProject getProjectByProjectNameIgnoreSpace(@Param("projectName") String projectName);

    /**
     * 根据项目名称忽略空格批量查询项目
     * @param projectNames
     * @return
     */
    List<PreProject> getProjectsByNamesIgnoreSpace(@Param("projectNames") Set<String> projectNames);

    /**
     * 根据项目名称模糊查询候选项目，兼容中英文括号和空格差异。
     * @param projectName
     * @return
     */
    List<PreProject> selectProjectCandidatesByName(@Param("projectName") String projectName);

    /**
     * 根据单位名称获取单位id
     * @param unitName
     * @return
     */
    String getCompanyIdByCompanyName(@Param("unitName") String unitName);

    /**
     * 根据单位名称忽略空格获取单位id
     * @param unitName
     * @return
     */
    String getCompanyIdByCompanyNameIgnoreSpace(@Param("unitName") String unitName);

    /**
     * 根据单位名称忽略空格批量获取单位id
     * @param unitNames
     * @return
     */
    List<Map> getCompanyIdsByNamesIgnoreSpace(@Param("unitNames") Set<String> unitNames);

    /**
     * 根据单位名称模糊查询候选单位，兼容中英文括号和空格差异。
     * @param unitName
     * @return
     */
    List<Map> selectCompanyCandidatesByName(@Param("unitName") String unitName);

    /**
     * 查询项目下所有合同。
     * @param projectId
     * @return
     */
    List<Map> selectContractsByProjectId(@Param("projectId") String projectId);

    /**
     * 查询单位作为乙方的所有合同。
     * @param unitId
     * @return
     */
    List<Map> selectContractsByPartyBId(@Param("unitId") String unitId);

    /**
     * 根据名称模糊查询合同。
     * @param contractName
     * @return
     */
    List<Map> selectContractsByName(@Param("contractName") String contractName);

    /**
     * 插入一条单位记录
     * @param companyId
     * @param unitName
     * @return
     */
    Integer insertCompany(@Param("companyId") String companyId, @Param("unitName") String unitName);

    /**
     * 根据合同名称查询合同id
     * @param contractName
     * @return
     */
    String getContractIdByContractName(@Param("contractName") String contractName);
}
