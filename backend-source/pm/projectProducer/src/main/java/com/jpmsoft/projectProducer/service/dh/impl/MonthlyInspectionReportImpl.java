package com.jpmsoft.projectProducer.service.dh.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.model.requestAndResponse.dh.MonthlyInspectionReportDTO;
import com.jpmsoft.common.model.requestAndResponse.dh.MonthlyInspectionReportQdDTO;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.dh.MonthlyInspectionReportMapper;
import com.jpmsoft.projectProducer.pojo.entity.dh.MonthlyInspectionReport;
import com.jpmsoft.projectProducer.pojo.entity.dh.MonthlyInspectionReportQd;
import com.jpmsoft.projectProducer.service.dh.IMonthlyInspectionReportQdService;
import com.jpmsoft.projectProducer.service.dh.IMonthlyInspectionReportService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MonthlyInspectionReportImpl extends ServiceImpl<MonthlyInspectionReportMapper, MonthlyInspectionReport> implements IMonthlyInspectionReportService {
    @Resource
    private IMonthlyInspectionReportQdService monthlyInspectionReportQdService;

    @Override
    public BaseResponse getMonthlyInspectionReportList(MonthlyInspectionReportDTO request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        QueryMap<MonthlyInspectionReportDTO> queryMap = new QueryMap<>(request);
        queryMap.put("pNameList").put("secNameList").put("conNameList").put(request.getFillDateList(), "fillDateStart", "fillDateEnd");
        List<MonthlyInspectionReportDTO> list = baseMapper.getMonthlyInspectionReportList(queryMap.getQuery());
        if (ValidateUtil.isNotEmpty(list)) {
            Set<String> collect = list.stream().map(MonthlyInspectionReportDTO::getId).collect(Collectors.toSet());
            List<MonthlyInspectionReportQd> reportQds = monthlyInspectionReportQdService.list(Wrappers.<MonthlyInspectionReportQd>lambdaQuery().in(MonthlyInspectionReportQd::getMirId, collect));
            List<MonthlyInspectionReportQdDTO> monthlyInspectionReportQdDTOS = BeanUtil.copyToList(reportQds, MonthlyInspectionReportQdDTO.class);
            list.forEach(item -> {
                List<MonthlyInspectionReportQdDTO> qdList = monthlyInspectionReportQdDTOS.stream().filter(qd -> qd.getMirId().equals(item.getId())).collect(Collectors.toList());
                item.setQdList(qdList);
            });
        }
        return BaseResponse.ok(list);
    }

    @Override
    public BaseResponse getMonthlyInspectionReportListClassInfo(MonthlyInspectionReportDTO request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<ClassInfoAnnotation>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(MonthlyInspectionReportDTO.class.getName());
            String selectClause = ClassInfoUtils.getSelectClauseWithClassInfo(classInfoAnnotationList);
            QueryMap<MonthlyInspectionReportDTO> queryMap = new QueryMap<>(request);
            queryMap.put("pNameList").put("secNameList").put("conNameList").put(request.getFillDateList(), "fillDateStart", "fillDateEnd");

            List<Map<String, Object>> result = baseMapper.getAllValues(selectClause, queryMap.getQuery());
            classInfoAnnotationList = ClassInfoUtils.resultsHandle(result, classInfoAnnotationList);
        } catch (Exception e) {
            log.error("getSecPrequalificationReportClassInfo error:{}", e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        return BaseResponse.ok(classInfoAnnotationList);
    }
}
