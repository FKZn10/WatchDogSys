package com.jpmsoft.projectProducer.service.qualitySafety.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.jpmsoft.common.model.response.Qs.QsRecordResponse;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsPlanTaskMapper;
import com.jpmsoft.projectProducer.mapper.qualitySafety.QsTypeMapper;
import com.jpmsoft.projectProducer.pojo.request.evaluate.qualitySafety.QualitySafetyEvaluateRequest;
import com.jpmsoft.projectProducer.pojo.response.IndicatorDataResponse;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsPlanTaskVo;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QsTypeVO;
import com.jpmsoft.projectProducer.pojo.vo.qualitySafety.QualitySafetyEvaluateVo;
import com.jpmsoft.projectProducer.service.qualitySafety.IQualitySafetyEvaluateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class IQualitySafetyEvaluateServiceImpl implements IQualitySafetyEvaluateService {
    @Autowired
    private QsTypeMapper qsTypeMapper;
    @Autowired
    private QsPlanTaskMapper qsPlanTaskMapper;
    @Override
    //当月施工单位自查质量问题数（条）
    public IndicatorDataResponse getQualitySafetyQuestionNumSG(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumSG");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionNumSG());
    }



    @Override
    public IndicatorDataResponse getQualitySafetyQuestionNumSGImportant(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumSGImportant");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionNumSGImportant());
    }

    @Override
    public IndicatorDataResponse getQualitySafetyQuestionNumJL(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumJL");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionNumJL());
    }

    @Override
    public IndicatorDataResponse getQualitySafetyQuestionNumJLImportant(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumJLImportant");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionNumJLImportant());
    }

    @Override
    public IndicatorDataResponse getQualitySafetyQuestionNumSGJL(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumSGJL");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionNumSGJL());
    }

    @Override
    public IndicatorDataResponse getQualitySafetyQuestionNumSGJLSame(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumSGJLSame");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionNumSGJLSame());
    }

    @Override
    public IndicatorDataResponse getQualitySafetyQuestionNumSGJLSameImportant(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumSGJLSameImportant");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionNumSGJLSameImportant());
    }

    @Override
    public IndicatorDataResponse getQualitySafetyQuestionNumYZ(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZ");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionNumYZ());
    }

    @Override
    public IndicatorDataResponse getQualitySafetyQuestionNumYZImportant(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZImportant");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionNumYZImportant());
    }

    @Override
    public IndicatorDataResponse getQualitySafetyQuestionNumYZSGSame(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZSGSame");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionNumYZSGSame());
    }

    @Override
    public IndicatorDataResponse getQualitySafetyQuestionNumYZSGSameImportant(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZSGSameImportant");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionNumYZSGSameImportant());
    }

    @Override
    public IndicatorDataResponse getQualitySafetyQuestionNumYZJLSame(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZJLSame");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionNumYZJLSame());
    }

    @Override
    public IndicatorDataResponse getQualitySafetyQuestionNumYZJLSameImportant(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumYZJLSameImportant");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionNumYZJLSameImportant());
    }

    @Override
    public IndicatorDataResponse getQualitySafetyQuestionNumZG(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumZG");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionNumZG());
    }

    @Override
    public IndicatorDataResponse getQualitySafetyQuestionNumZGImportant(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionNumZGImportant");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionNumZGImportant());
    }

    @Override
    public IndicatorDataResponse getQualitySafetyQuestionZCRate(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionZCRate");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionZCRate());
    }

    @Override
    public IndicatorDataResponse getQualitySafetyQuestionImportantRate(QualitySafetyEvaluateRequest request) {
        log.info("getQualitySafetyQuestionImportantRate");
        IndicatorDataResponse failResponse = getIndicatorDataResponse(request);
        if (failResponse != null) return failResponse;
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=getQualitySafetyEvaluateVo(request);
        return IndicatorDataResponse.ok(qualitySafetyEvaluateVo.getQuestionImportantRate());
    }

    private static IndicatorDataResponse getIndicatorDataResponse(QualitySafetyEvaluateRequest request) {
        if (request ==null){
            return IndicatorDataResponse.fail("缺失请求信息",0);
        }
        if(StrUtil.isEmpty(request.getProjectId())){
            return IndicatorDataResponse.fail("缺失项目信息",0);
        }
        if(StrUtil.isEmpty(request.getPeCompanyId())){
            return IndicatorDataResponse.fail("缺失单位信息",0);
        }
        if(StrUtil.isEmpty(request.getQsType())){
            return IndicatorDataResponse.fail("缺失质量安全类型信息",0);
        }
        if (request.getEvaluationYear()==null){
            return IndicatorDataResponse.fail("缺失年份时间信息",0);
        }
        if (request.getEvaluationMonth()==null){
            return IndicatorDataResponse.fail("缺失月份时间信息",0);
        }
        return null;
    }

    private QualitySafetyEvaluateVo getQualitySafetyEvaluateVo(QualitySafetyEvaluateRequest request) {
        QualitySafetyEvaluateVo qualitySafetyEvaluateVo=new QualitySafetyEvaluateVo();
        String projectId = request.getProjectId();
        String companyId = request.getPeCompanyId();
        String qsType = request.getQsType();
        String qsTypeId = null;
        if (qsType.equals("质量")){
            qsTypeId = "b5ba6963-3f68-4589-b52c-219ea6b18df4";
        }else if (qsType.equals("安全")) {
            qsTypeId= "dfa906f7-690d-425c-8efb-59762ac5dc4c";
        }
        Integer evaluationYear = request.getEvaluationYear();
        Integer evaluationMonth = request.getEvaluationMonth();
        //根据生成开始时间 结束时间
        ZonedDateTime[] range = generateMonthStartAndEnd(evaluationYear, evaluationMonth);
        Date startTime =request.getEvaluateStartDate();
        Date endTime = request.getEvaluateEndDate();
        List<QsPlanTaskVo> data = qsPlanTaskMapper.getQsPlanTaskLists(projectId,companyId,startTime,endTime);
        //log.info("data:{}", JacksonUtils.toJSONString(data));
        for (QsPlanTaskVo qsPlanTaskVo : data ) {
            qsPlanTaskVo.setQsRecords(qsPlanTaskMapper.getQsRecord2(qsPlanTaskVo.getId(),qsTypeId));
        }
        log.info("data:{}", JacksonUtils.toJSONString(data));
        //当月施工单位自查安全问题数（条）
        Integer questionNumSG = 0;
        List<QsPlanTaskVo> sgdata= data.stream().filter(item -> item.getPtType().equals("08")).collect(Collectors.toList());
        List<QsRecordResponse> qsSGRecords= new ArrayList<>();
        if (CollectionUtil.isNotEmpty(sgdata)){
            for (QsPlanTaskVo item : sgdata){
                qsSGRecords.addAll(item.getQsRecords());
            }
        }
        questionNumSG=qsSGRecords.stream().collect(Collectors.toList()).size();
        qualitySafetyEvaluateVo.setQuestionNumSG(questionNumSG);
        //其中：当月施工单位自查安全问题中重大隐患问题数（条）
        Integer questionNumSGImportant = 0;
        questionNumSGImportant=qsSGRecords.stream().filter(item -> item.getQstIsImportant().equals(1)).collect(Collectors.toList()).size();
        qualitySafetyEvaluateVo.setQuestionNumSGImportant(questionNumSGImportant);
        //当月监理单位巡查安全问题数（条）
        Integer questionNumJL = 0;
        List<QsPlanTaskVo> jldata= data.stream().filter(item -> item.getPtType().equals("02")).collect(Collectors.toList());
        List<QsRecordResponse> qsJLRecords= new ArrayList<>();
        if (CollectionUtil.isNotEmpty(jldata)){
            for (QsPlanTaskVo item : jldata){
                qsJLRecords.addAll(item.getQsRecords());
            }
        }
        questionNumJL=qsJLRecords.stream().collect(Collectors.toList()).size();
        qualitySafetyEvaluateVo.setQuestionNumJL(questionNumJL);
        //其中：当月监理单位巡查安全问题中重大隐患问题数（条）
        Integer questionNumJLImportant = 0;
        questionNumJLImportant=qsJLRecords.stream().filter(item -> item.getQstIsImportant().equals(1)).collect(Collectors.toList()).size();
        qualitySafetyEvaluateVo.setQuestionNumJLImportant(questionNumJLImportant);
        //当月施工监理发现安全问题总数（条）
        Integer questionNumSGJL = 0;
        List<QsRecordResponse> qsSGJLRecords= new ArrayList<>();
        qsSGJLRecords.addAll(qsSGRecords);
        qsSGJLRecords.addAll(qsJLRecords);
        questionNumSGJL=qsSGJLRecords.stream().collect(Collectors.toList()).size();
        qualitySafetyEvaluateVo.setQuestionNumSGJL(questionNumSGJL);
        //当月施工监理同一安全问题重合数（条）
        Integer questionNumSGJLSame = 0;
        List<QsRecordResponse> qsSGJLSameRecords= new ArrayList<>();
        for (QsRecordResponse item : qsSGRecords){
            if (StrUtil.isNotEmpty(item.getQsrReasonType()) && StrUtil.isNotEmpty(item.getQstCode())){
               qsSGJLSameRecords.addAll(qsJLRecords.stream().filter(item1 -> item.getQstCode().equals(item1.getQstCode())
                       && item.getQsrReasonType().equals(item1.getQsrReasonType())).collect(Collectors.toList()));
            }
        }
        questionNumSGJLSame=qsSGJLSameRecords.stream().collect(Collectors.toList()).size();
        qualitySafetyEvaluateVo.setQuestionNumSGJLSame(questionNumSGJLSame);
        //其中：当月施工监理同一安全问题中重大隐患问题重合数（条）
        Integer questionNumSGJLSameImportant = 0;
        questionNumSGJLSameImportant=qsSGJLSameRecords.stream().filter(item -> item.getQstIsImportant().equals(1)).collect(Collectors.toList()).size();
        qualitySafetyEvaluateVo.setQuestionNumSGJLSameImportant(questionNumSGJLSameImportant);
        //当月业主（代建）发现安全问题数（条）
        Integer questionNumYZ = 0;
        List<QsPlanTaskVo> yzdata= data.stream().filter(item -> item.getPtType().equals("03")||item.getPtType().equals("04")).collect(Collectors.toList());
        List<QsRecordResponse> qsYZRecords= new ArrayList<>();
        if (CollectionUtil.isNotEmpty(yzdata)){
            for (QsPlanTaskVo item : yzdata){
                qsYZRecords.addAll(item.getQsRecords());
            }
        }
        questionNumYZ=qsYZRecords.stream().collect(Collectors.toList()).size();
        qualitySafetyEvaluateVo.setQuestionNumYZ(questionNumYZ);
        //其中：当月业主（代建）发现安全问题中重大隐患问题数（条）
        Integer questionNumYZImportant = 0;
        questionNumYZImportant=qsYZRecords.stream().filter(item -> item.getQstIsImportant().equals(1)).collect(Collectors.toList()).size();
        qualitySafetyEvaluateVo.setQuestionNumYZImportant(questionNumYZImportant);
        //当月业主（代建）与施工同一安全问题重合数（条）
        Integer questionNumYZSGSame = 0;
        List<QsRecordResponse> qsYZSGSameRecords= new ArrayList<>();
        for (QsRecordResponse item : qsYZRecords){
            if (StrUtil.isNotEmpty(item.getQsrReasonType()) && StrUtil.isNotEmpty(item.getQstCode())){
                qsYZSGSameRecords.addAll(qsSGRecords.stream().filter(item1 -> item.getQstCode().equals(item1.getQstCode())
                        && item.getQsrReasonType().equals(item1.getQsrReasonType())).collect(Collectors.toList()));
            }
        }
        questionNumYZSGSame=qsYZSGSameRecords.stream().collect(Collectors.toList()).size();
        qualitySafetyEvaluateVo.setQuestionNumYZSGSame(questionNumYZSGSame);
        //其中：当月业主（代建）与施工同一安全问题中重大隐患问题重合数（条）
        Integer questionNumYZSGSameImportant =0;
        questionNumYZSGSameImportant=qsYZSGSameRecords.stream().filter(item -> item.getQstIsImportant().equals(1)).collect(Collectors.toList()).size();
        qualitySafetyEvaluateVo.setQuestionNumYZSGSameImportant(questionNumYZSGSameImportant);
        //当月业主（代建）与监理同一安全问题重合数（条）
        Integer questionNumYZJLSame = 0;
        List<QsRecordResponse> qsYZJLSameRecords= new ArrayList<>();
        for (QsRecordResponse item : qsYZRecords){
            if (StrUtil.isNotEmpty(item.getQsrReasonType()) && StrUtil.isNotEmpty(item.getQstCode())){
                qsYZJLSameRecords.addAll(qsJLRecords.stream().filter(item1 -> item.getQstCode().equals(item1.getQstCode())
                        && item.getQsrReasonType().equals(item1.getQsrReasonType())).collect(Collectors.toList()));
            }
        }
        questionNumYZJLSame=qsYZJLSameRecords.stream().collect(Collectors.toList()).size();
        qualitySafetyEvaluateVo.setQuestionNumYZJLSame(questionNumYZJLSame);
        //其中：当月业主（代建）与监理同一安全问题中重大隐患问题重合数（条）
        Integer questionNumYZJLSameImportant = 0;
        questionNumYZJLSameImportant=qsYZJLSameRecords.stream().filter(item -> item.getQstIsImportant().equals(1)).collect(Collectors.toList()).size();
        qualitySafetyEvaluateVo.setQuestionNumYZJLSameImportant(questionNumYZJLSameImportant);
        //当月安全隐患问题整改数（条）
        Integer questionNumZG = 0;
        List<QsRecordResponse> qsZGAllRecords= new ArrayList<>();
        if (CollectionUtil.isNotEmpty(data)){
            for (QsPlanTaskVo item : data){
                qsZGAllRecords.addAll(item.getQsRecords());
            }
        }
        List<QsRecordResponse> qsZGRecords= new ArrayList<>();
        if (CollectionUtil.isNotEmpty(qsZGAllRecords)){
            for (QsRecordResponse item : qsZGAllRecords){
                if (item.getQsrRectifyStatus().equals("03")){
                    qsZGRecords.add(item);
                }
            }
        }
        questionNumZG=qsZGRecords.stream().collect(Collectors.toList()).size();
        qualitySafetyEvaluateVo.setQuestionNumZG(questionNumZG);
        //其中：当月安全隐患问题整改中重大隐患整改数（条）
        Integer questionNumZGImportant = 0;
        questionNumZGImportant=qsZGRecords.stream().filter(item -> item.getQstIsImportant().equals(1)).collect(Collectors.toList()).size();
        qualitySafetyEvaluateVo.setQuestionNumZGImportant(questionNumZGImportant);
        //当月施工监理发现质量问题总数(去除重合数)
        Integer questionNumZGSGWithoutSame = questionNumSG + questionNumJL - questionNumSGJLSame;
        //当月发现质量问题总数（条）(去除重合数)【不去除施工监理重合数】
        Integer questionNum = questionNumSGJL+questionNumYZ-questionNumYZSGSame-questionNumYZJLSame;
        //自查率
        Double questionZCRate = 0.0;
        if (questionNum!=0){
            questionZCRate=new BigDecimal(questionNumZGSGWithoutSame).divide(new BigDecimal(questionNum),2, RoundingMode.HALF_UP).doubleValue();
        }
        qualitySafetyEvaluateVo.setQuestionZCRate(questionZCRate);
        Integer questionNumImportantWithoutSame =questionNumSGImportant+questionNumJLImportant+questionNumYZImportant
                -questionNumSGJLSameImportant-questionNumYZJLSameImportant-questionNumYZSGSameImportant;
        Integer questionNumWithoutSame =questionNumSG+questionNumJL+questionNumYZ
                -questionNumSGJLSame-questionNumYZJLSame-questionNumYZSGSame;
        Double questionImportantRate = 0.0;
        if (questionNumWithoutSame!=0){
            questionImportantRate=new BigDecimal(questionNumImportantWithoutSame).divide(new BigDecimal(questionNumWithoutSame),2, RoundingMode.HALF_UP).doubleValue();
        }
        qualitySafetyEvaluateVo.setQuestionImportantRate(questionImportantRate);
        log.info("qualitySafetyEvaluateVo:{}", JacksonUtils.toJSONString(qualitySafetyEvaluateVo));
        return qualitySafetyEvaluateVo;
    }

     /**
     * 根据给定的月份生成该月的开始和结束时间。
     *
     * @param year 年份
     * @param month 月份
     * @return 一个包含开始时间和结束时间的数组
     */
    public static ZonedDateTime[] generateMonthStartAndEnd(int year, int month) {
        YearMonth evaluationMonth = YearMonth.of(year, month);

        ZonedDateTime startOfDay = evaluationMonth.minusMonths(1).atDay(21)
                .atStartOfDay()
                .atZone(ZoneId.systemDefault());

        ZonedDateTime endOfDay = evaluationMonth.atDay(20)
                .atTime(23, 59, 59)
                .atZone(ZoneId.systemDefault());

        return new ZonedDateTime[]{startOfDay, endOfDay};
    }

    private List<QsTypeVO> getQsTypeById(String qstId) {
        List<QsTypeVO> qsTypeVos = new ArrayList<>();
        // 初始化用于存储查询条件的map
        Map<String,Object> map=new HashMap<>();
        map.put("qstId",qstId);
        // 根据问题类型名称查询所有同名的问题类型
        List<QsTypeVO> qsTypes = qsTypeMapper.getQsType(map);
        // 如果查询到的问题类型不为空，则递归查询并设置子问题类型
        getQstType(qsTypes,qsTypeVos);
        // 设置查询到的问题类型的子问题类型
        return qsTypeVos;
    }

    private void getQstType(List<QsTypeVO> qsTypes,List<QsTypeVO> qsTypeVos) {
        if(CollectionUtil.isNotEmpty(qsTypes)){
            for (QsTypeVO qsTypeVO1 : qsTypes) {
                qsTypeVos.add(qsTypeVO1);
                // 根据父问题类型ID查询子问题类型
                List<QsTypeVO> qsChildTypes = qsTypeMapper.getQsTypeByParentId(qsTypeVO1.getQstId());
                // 如果存在子问题类型，则递归查询并设置子问题类型的子级
                if (CollectionUtil.isNotEmpty(qsChildTypes)){
                   getQstType(qsChildTypes,qsTypeVos);
                }
            }
        }
    }
}
