package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectImportRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotProjectModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProvPilotProject;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.List;

/**
 * 省试点项目 Service
 *
 * @author whjp
 * @since 2026-09-16
 */
public interface IProvPilotProjectService extends IService<ProvPilotProject> {

    /**
     * 编辑（新增/修改）
     */
    BaseResponse createOrUpdate(ProvPilotProjectModel request, LogUserInfoVo user);

    /**
     * 列表查询（分页+排序+筛选）
     */
    BaseResponse getList(ProvPilotProjectModel request, String uid);

    /**
     * 详情
     */
    BaseResponse getById(String id);

    /**
     * 真删除（批量），同时删除 sys_file
     */
    BaseResponse deleteBatch(List<String> ids);

    /**
     * 补充导入（File 服务 Excel 解析后调用，逐行校验并落库，同步建 sys_file）
     */
    BaseResponse importProvPilotProject(ProvPilotProjectImportRequest request, LogUserInfoVo user);

    /**
     * 导出数据（File 服务导出时调用，按筛选条件返回原始列表，字典码在 File 侧转名称）
     */
    List<ProvPilotProjectModel> getExportList(ProvPilotProjectModel request, String uid);
}