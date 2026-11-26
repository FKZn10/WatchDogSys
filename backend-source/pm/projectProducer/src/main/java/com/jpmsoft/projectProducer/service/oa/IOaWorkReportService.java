package com.jpmsoft.projectProducer.service.oa;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Oa.OaWorkReportRequest;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaWorkReport;

import java.util.List;

public interface IOaWorkReportService extends IService<OaWorkReport> {

    BaseResponse createOrUpdateOaWorkReport(OaWorkReportRequest request);

    BaseResponse getOaWorkReportList(OaWorkReportRequest request, String uId);

    /**
     * 分页查询工作报告，附带子表 OA_Work_Report_Detail 的 Wrd_Number 数量合计（项目鉴权）
     *
     * @param request 复用 OaWorkReportRequest，查询条件与 getOaWorkReportList 一致
     * @param uId     当前登录用户id，用于生成数据权限 SQL
     * @return 列表数据与总数，每条记录含 wrdNumberTotal
     */
    BaseResponse getOaWorkReportSumList(OaWorkReportRequest request, String uId);

    /**
     * 仅可查看当前组织机构和下级组织机构数据
     *
     * @param request
     * @param uId
     * @return
     */
    BaseResponse getOaWorkReportListOnlyDep(OaWorkReportRequest request, String uId);

    BaseResponse getOaWorkReportListNoAuth(OaWorkReportRequest request);

    BaseResponse getOaWorkReportById(String wrId);

    BaseResponse getOaWorkReportStandingbook(RequestBean requestBean, String uId);

    BaseResponse getOaWorkReportNeverSub(RequestBean requestBean);

    BaseResponse countOaWorkReport(List<String> bizIds,String type);
}
