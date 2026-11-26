package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectRegisterInfoModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectRegisterInfo;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectRegisterStatisticsVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectRegisterInfoMapper extends BaseMapper<ProjectRegisterInfo> {
    ProjectRegisterInfoModel getPreProjectRegisterInfoById(@Param("id") String id);

    List<ProjectRegisterInfoModel> getPreProjectRegisterInfoList(@Param("params") Map<String, Object> query,
                                                                 @Param("page") IPage<ProjectRegisterInfoModel> page);

    ProjectRegisterInfo selectByProjectId(@Param("projectId") String projectId);

    List<ProjectRegisterInfoModel> getPreProjectRegisterInfoModifyInfo(@Param("params") Map<String, Object> query,
                                                                       @Param("page") IPage<ProjectRegisterInfoModel> page);

    /**
     * 查询流程已结束但未同步到项目表 Pre_Project 的项目申报记录，用于定时任务补偿同步
     *
     * @param state 流程结束状态
     */
    List<ProjectRegisterInfo> selectUnSyncPreProjectRegisterInfoList(@Param("state") Integer state);

    /**
     * 统计授权范围内的最新申报或论证状态，已有申报的登记不重复计入论证。
     *
     * @param params 后端组装的单位权限及业务类型参数
     * @return 状态统计，空表返回零
     */
    ProjectRegisterStatisticsVO countDeclarationStatistics(@Param("params") Map<String, Object> params);
}