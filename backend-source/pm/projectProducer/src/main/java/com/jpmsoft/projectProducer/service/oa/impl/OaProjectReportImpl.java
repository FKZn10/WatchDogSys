package com.jpmsoft.projectProducer.service.oa.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Oa.OaProjectReportModel;
import com.jpmsoft.common.model.response.Oa.OaProReportInvSum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.oa.OaProjectReportMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.ProMonthlyReportDto;
import com.jpmsoft.projectProducer.pojo.entity.oa.OaProjectReport;
import com.jpmsoft.projectProducer.pojo.vo.project.ProjectInvStatisticsVo;
import com.jpmsoft.projectProducer.service.oa.IOaProjectReportService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OaProjectReportImpl extends ServiceImpl<OaProjectReportMapper, OaProjectReport> implements IOaProjectReportService {


    @Override
    public void createOrUpdateOaProjectReport(List<OaProjectReportModel> datas, String wrId, Integer year, Integer mon, String teId) {
        QueryWrapper<OaProjectReport> wrapper = new QueryWrapper<>();
        wrapper.eq("Wr_Id", wrId);
        baseMapper.delete(wrapper);
        if (!CollectionUtils.isEmpty(datas)) {
            List<String> pIds = baseMapper.getCurrentMonthReportPids(year, mon);
            List<OaProjectReport> entitys = new ArrayList<>();
            OaProjectReport entity;
            for (OaProjectReportModel data : datas) {
                if (!ValidateUtil.isEmpty(data.getPrType()) && data.getPrType().equals("02")) {
                    if (pIds.contains(data.getpId()))
                        continue;
                }
                entity = new OaProjectReport();
                BeanUtils.copyProperties(data, entity);
                entity.setPrCreateTime(new Date());
                entity.setPrId(GenerateUUID.getUUID());
                entity.setWrId(wrId);
                entity.setTeId(teId);
                entitys.add(entity);
            }
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public BaseResponse createOrUpdateOaProjectReport(OaProjectReportModel request) {
        OaProjectReport entity = new OaProjectReport();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getPrId())) {
            entity.setPrCreateTime(new Date());
            entity.setPrId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        } else {
            OaProjectReport model = baseMapper.selectById(request.getPrId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            entity.setPrCreateTime(model.getPrCreateTime());
            entity.setPrCreateUid(model.getPrCreateUid());
            entity.setPrCreateUname(model.getPrCreateUname());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getPrId());
    }

    @Override
    public BaseResponse getOaProjectReportList(OaProjectReportModel request) {
        QueryMap<OaProjectReportModel> params = new QueryMap<>(request);
        params.put("wrId").put("prType").put("pName").put("pId")
                .put("prWhetherLag").put("wrTitle").put("prConstructionState")
                .put("prInitiationPlan").put("prIsBudget").put("prAdjustmentType")
                .put("prResponsibleDep").put("prResponsibleLeader")
                .put("prIsStartWork").put("prIsFinish");
        IPage<OaProjectReportModel> page = new Page<>(request.getPage(), request.getSize());
        List<OaProjectReportModel> datas = baseMapper.getOaProjectReportList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getOaProjectReportById(String prId) {
        OaProjectReportModel data = baseMapper.getOaProjectReportById(prId);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getOaProReportInvSum(String wrId) {
        OaProReportInvSum data = baseMapper.getOaProReportInvSum(wrId);
        return BaseResponse.ok(data);
    }

    @Override
    public List<OaProjectReportModel> getOaProjectReportByPidsAndYearMon(List<String> pIds, Integer year, Integer month) {
        String mon = month.toString();
        //转月份
        if (month < 10)
            mon = '0' + mon;
        return baseMapper.getOaProjectReportByPidsAndYearMon(pIds, year, mon);
    }

    @Override
    public List<OaProjectReportModel> getOaProjectQuarterlReportByPidsAndYear(List<String> pIds, Integer year) {
        return baseMapper.getOaProjectQuarterlReportByPidsAndYear(pIds, year);
    }

    @Override
    public ProjectInvStatisticsVo getOaProjectReportInvStatistics(ProMonthlyReportDto request) {
        String month = request.getMonth().toString();
        //转月份
        if (request.getMonth() < 10)
            month = '0' + month;
        QueryMap<ProMonthlyReportDto> query = new QueryMap<>(request);
        query.put("month", month).put("year").put("pType").put("pName").put("pCompetentDepartment").put("pId").put("cId");
        return baseMapper.getOaProjectReportInvStatistics(query.getQuery());
    }

    @Override
    public BaseResponse getLastProjectReportByPid(String pId, String prType) {
        OaProjectReportModel data = baseMapper.getLastProjectReportByPid(pId, prType);
        return BaseResponse.ok(data);
    }
}
