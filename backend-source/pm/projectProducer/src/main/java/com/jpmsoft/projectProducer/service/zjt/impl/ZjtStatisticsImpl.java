package com.jpmsoft.projectProducer.service.zjt.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.zjt.ProjectUpStatisticsDTO;
import com.jpmsoft.common.model.requestAndResponse.zjt.ProjectUpStatisticsParentDTO;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.zjt.ZjtStatisticsMapper;
import com.jpmsoft.projectProducer.pojo.dto.zjt.ZjtProjectExportDTO;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.zjt.ZjtStatisticsService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ZjtStatisticsImpl implements ZjtStatisticsService {


    @Resource
    private ZjtStatisticsMapper zjtStatisticsMapper;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public BaseResponse getProjectUpStatistics(ProjectUpStatisticsDTO request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isNull(request.getYearFilter()) || ValidateUtil.isNull(request.getMonthFilter()))
            return BaseResponse.fail(ResMsg.notReqParam);
        if (request.getMonthFilter() == 1) {
            request.setLastYear(request.getMonthFilter() - 1);
            request.setLastMonth(12);
        } else {
            request.setLastYear(request.getYearFilter());
            request.setLastMonth(request.getMonthFilter());
        }
        if (request.getMonthFilter() < 10) request.setMonthFt("0" + request.getMonthFilter());
        else request.setMonthFt(request.getMonthFilter() + "");
        List<ProjectUpStatisticsDTO> data = zjtStatisticsMapper.getProjectUpStatistics(request, request.getPageStart(), request.getSize());
        List<ProjectUpStatisticsDTO> cpyInfos = zjtStatisticsMapper.getProjectUpCpyInfo(request);
        ProjectUpStatisticsParentDTO total = zjtStatisticsMapper.getProjectUpStatisticsTotal(request);
        if (total != null) {
            if (!CollectionUtils.isEmpty(cpyInfos)) {
                total.setJgTotal(cpyInfos.stream().filter(x -> x.getPConstructionUnitType().equals("1")).collect(Collectors.toList()).size());
                total.setSsTotal(cpyInfos.stream().filter(x -> x.getPConstructionUnitType().equals("2")).collect(Collectors.toList()).size());
            }
            total.setData(data);
        }
        return BaseResponse.ok(total, cpyInfos.size());
    }

    @Override
    public BaseResponse exportProject(ZjtProjectExportDTO request, String token) {
        String authSql = sysUserService.getAuthSql("pre_project", redisUtils.getUid(token), "P_Create_User_ID", OutUserAuthEnum.Organization.getIndex(), "state");
        QueryMap<ZjtProjectExportDTO> params = new QueryMap<>(request);
        params.put("authSql", authSql).put(request.getPCreateTime(), "pCreateTimeStart", "pCreateTimeEnd")
                .put("pCreateUserName").put("pName").put("pProfessionalType").put("pType").put("state");
        return BaseResponse.ok(zjtStatisticsMapper.exportProject(params.getQuery()));
    }
}
