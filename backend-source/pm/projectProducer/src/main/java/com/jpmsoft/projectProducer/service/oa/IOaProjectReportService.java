package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProjectReportModel;
import com.jpmsoft.projectProducer.pojo.dto.project.ProMonthlyReportDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaProjectReport;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectInvStatisticsVo;

import java.util.List;

public interface IOaProjectReportService extends IService<OaProjectReport> {


    void createOrUpdateOaProjectReport(List<OaProjectReportModel> datas, String wrId, Integer year, Integer mon,String teId);

    BaseResponse createOrUpdateOaProjectReport(OaProjectReportModel request);

    BaseResponse getOaProjectReportList(OaProjectReportModel request);

    BaseResponse getOaProjectReportById(String prId);

    BaseResponse getOaProReportInvSum(String wrId);

    List<OaProjectReportModel> getOaProjectReportByPidsAndYearMon(List<String> pIds, Integer year, Integer month);
    List<OaProjectReportModel> getOaProjectQuarterlReportByPidsAndYear(List<String> pIds, Integer year);

    /**
     * 统计工程报告投资金额
     * prCompleteInv，prYearInv，prMonthInv
     *
     * @param request
     * @return
     */
    ProjectInvStatisticsVo getOaProjectReportInvStatistics(ProMonthlyReportDto request);

    /**
     * 获取项目最新的工程报告
     *
     * @param pId
     * @param prType
     * @return
     */
    BaseResponse getLastProjectReportByPid(String pId, String prType);
}
