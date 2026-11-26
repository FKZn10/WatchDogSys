package com.jpmsoft.projectProducer.mapper.xz;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.xz.ProjectApplicationVO;
import com.jpmsoft.projectProducer.pojo.entity.xz.ProjectApplication;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 14:10
 * @Description:
 * @Version: 1.0
 */
public interface ProjectApplicationMapper extends BaseMapper<ProjectApplication> {

    List<ProjectApplicationVO> queryPage(IPage<ProjectApplicationVO> page,
                                         @Param("sortFiled") String sortFiled,
                                         @Param("sortType") String sortType,
                                         @Param("projectName") String projectName,
                                         @Param("applicationCompanyId") String applicationCompanyId,
                                         @Param("applicationCompanyName") String applicationCompanyName,
                                         @Param("planYear") String planYear,
                                         @Param("projectType") String projectType,
                                         @Param("projectCategory") String projectCategory,
                                         @Param("projectManager") String projectManager,
                                         @Param("applicationNum") String applicationNum,
                                         @Param("taskStatus") String taskStatus,
                                         @Param("taskStatusList") List<String> taskStatusList,
                                         @Param("pageType") String pageType,
                                         @Param("createId") String createId,
                                         @Param("financeFlag") boolean financeFlag,
                                         @Param("createDepartIdList") List<String> createDepartIdList,
                                         @Param("approveStatus") String approveStatus);

    ProjectApplicationVO queryDetailById(@Param("id") String id);

    List<ProjectApplicationVO> queryListDetailByIds(@Param("ids") List<String> ids);
}
