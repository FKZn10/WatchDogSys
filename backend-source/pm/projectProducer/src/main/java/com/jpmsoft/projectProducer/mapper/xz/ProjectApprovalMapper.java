package com.jpmsoft.projectProducer.mapper.xz;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.xz.ProjectApprovalVO;
import com.jpmsoft.projectProducer.pojo.entity.xz.ProjectApproval;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/9/12 10:47
 * @Description:
 * @Version: 1.0
 */
public interface ProjectApprovalMapper extends BaseMapper<ProjectApproval> {

    List<ProjectApprovalVO> queryPage(IPage<ProjectApprovalVO> page,
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
                                         @Param("projectAmountType") String projectAmountType);
}
