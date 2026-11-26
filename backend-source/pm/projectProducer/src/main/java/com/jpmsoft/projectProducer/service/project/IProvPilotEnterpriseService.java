package com.jpmsoft.projectProducer.service.project;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseImportRequest;
import com.jpmsoft.common.model.requestAndResponse.Project.ProvPilotEnterpriseModel;
import com.jpmsoft.projectProducer.pojo.entity.project.ProvPilotEnterprise;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;

import java.util.List;

/**
 * 省试点企业 Service
 *
 * @author whjp
 * @since 2026-09-17
 */
public interface IProvPilotEnterpriseService extends IService<ProvPilotEnterprise> {

    /**
     * 编辑（新增/修改）
     */
    BaseResponse createOrUpdate(ProvPilotEnterpriseModel request, LogUserInfoVo user);

    /**
     * 列表查询（分页+排序+筛选）
     */
    BaseResponse getList(ProvPilotEnterpriseModel request, String uid);

    /**
     * 详情
     */
    BaseResponse getById(String id);

    /**
     * 真删除（批量）
     */
    BaseResponse deleteBatch(List<String> ids);

    /**
     * 补充导入（file 服务 Excel 解析后调用，逐行校验并落库）
     */
    BaseResponse importProvPilotEnterprise(ProvPilotEnterpriseImportRequest request, LogUserInfoVo user);

    /**
     * 导出数据
     */
    List<ProvPilotEnterpriseModel> getExportList(ProvPilotEnterpriseModel request, String uid);

    /**
     * 按登录用户的租户范围统计全部年度试点企业。
     *
     * @param user 当前登录用户
     * @return 企业总数及评估、验收可用性
     */
    BaseResponse getEnterpriseStatistics(LogUserInfoVo user);
}