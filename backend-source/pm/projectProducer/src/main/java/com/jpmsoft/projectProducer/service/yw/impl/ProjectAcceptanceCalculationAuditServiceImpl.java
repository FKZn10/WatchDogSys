package com.jpmsoft.projectProducer.service.yw.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.yw.ProjectAcceptanceCalculationAuditModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMapper;
import com.jpmsoft.projectProducer.mapper.evaluate.ZNPJEvaluateBasisInformationQuarterMapper;
import com.jpmsoft.projectProducer.mapper.evaluate.ZNPJIndicatorMapper;
import com.jpmsoft.projectProducer.mapper.other.SysFileMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectMapper;
import com.jpmsoft.projectProducer.mapper.yw.ProjectAcceptanceCalculationAuditMapper;
import com.jpmsoft.projectProducer.pojo.dto.other.SupportDocumentDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.evaluate.ZNPJEvaluateBasisInformationQuarter;
import com.jpmsoft.projectProducer.pojo.entity.evaluate.ZNPJIndicator;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.project.PreProject;
import com.jpmsoft.projectProducer.pojo.entity.yw.ProjectAcceptanceCalculationAudit;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.yw.ProjectAcceptanceCalculationAuditService;
import com.jpmsoft.projectProducer.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Month;
import java.time.YearMonth;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProjectAcceptanceCalculationAuditServiceImpl
        extends ServiceImpl<ProjectAcceptanceCalculationAuditMapper, ProjectAcceptanceCalculationAudit>
        implements ProjectAcceptanceCalculationAuditService {
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Autowired
    private SysFileMapper sysFileMapper;
    @Autowired
    private ConContractMapper conContractMapper;
    @Autowired
    private PreProjectMapper preProjectMapper;
    @Autowired
    private ZNPJEvaluateBasisInformationQuarterMapper znpjEvaluateBasisInformationQuarterMapper;
    @Autowired
    private ZNPJIndicatorMapper znpjIndicatorMapper;
    @Override
    public BaseResponse saveOrUpdateData(ProjectAcceptanceCalculationAuditModel dto) {
        log.info("saveOrUpdateData:{}", JacksonUtils.toJSONString(dto));
        ProjectAcceptanceCalculationAudit entity;
        List<SupportDocumentDto> documentDtos = new ArrayList<>();
        SupportDocumentDto supportDocumentDto;
        if(StrUtil.isNotEmpty(dto.getDoneFile())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(dto.getDoneFile());
            supportDocumentDto.setSupportDocumentFileIDs(dto.getDoneFileIds());
            documentDtos.add(supportDocumentDto);
        }
        if(StrUtil.isNotEmpty(dto.getSignInTableFile())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(dto.getSignInTableFile());
            supportDocumentDto.setSupportDocumentFileIDs(dto.getSignInTableFileIds());
            documentDtos.add(supportDocumentDto);
        }
        if(StrUtil.isNotEmpty(dto.getMeetingSummaryFile())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(dto.getMeetingSummaryFile());
            supportDocumentDto.setSupportDocumentFileIDs(dto.getMeetingSummaryFileIds());
            documentDtos.add(supportDocumentDto);
        }
        if(StrUtil.isNotEmpty(dto.getExcludedResponsibilityFile())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(dto.getExcludedResponsibilityFile());
            supportDocumentDto.setSupportDocumentFileIDs(dto.getExcludedResponsibilityFileIds());
            documentDtos.add(supportDocumentDto);
        }
        if(StrUtil.isNotEmpty(dto.getCompleteSignInTableFile())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(dto.getCompleteSignInTableFile());
            supportDocumentDto.setSupportDocumentFileIDs(dto.getCompleteSignInTableFileIds());
            documentDtos.add(supportDocumentDto);
        }
        if(StrUtil.isNotEmpty(dto.getCompleteMeetingSummaryFile())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(dto.getCompleteMeetingSummaryFile());
            supportDocumentDto.setSupportDocumentFileIDs(dto.getCompleteMeetingSummaryFileIds());
            documentDtos.add(supportDocumentDto);
        }
        if(StrUtil.isNotEmpty(dto.getSubmitBasicTableFile())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(dto.getSubmitBasicTableFile());
            supportDocumentDto.setSupportDocumentFileIDs(dto.getSubmitBasicTableFileIds());
            documentDtos.add(supportDocumentDto);
        }
        if(StrUtil.isNotEmpty(dto.getAuditFile())){
            supportDocumentDto =new SupportDocumentDto();
            supportDocumentDto.setSupportDocument(dto.getAuditFile());
            supportDocumentDto.setSupportDocumentFileIDs(dto.getAuditFileIds());
            documentDtos.add(supportDocumentDto);
        }
        String primaryId = dto.getId();
        log.info("primaryId:{}", primaryId);
        if(StrUtil.isEmpty(primaryId)){
            primaryId = IdWorker.getGenerateID();
            entity = new ProjectAcceptanceCalculationAudit();
            BeanUtils.copyProperties(dto,entity);
            entity.setId(primaryId);
            entity.setCreateTime(DateTime.now());
            Integer flowStatus = ApprovalStatusEnum.Not_Started.getIndex();
            entity.setStateTime(DateTime.now());
            entity.setState(flowStatus);
            saveFile(entity, documentDtos);
            if (StrUtil.isNotEmpty(dto.getProjectType())){
                PreProject preProject = preProjectMapper.selectById(dto.getProjectId());
                if(preProject != null){
                    preProject.setpType(dto.getProjectType());
                    preProjectMapper.updateById(preProject);
                }
            }
            //创建流程
            baseMapper.insert(entity);
            if(!sysFileService.FileHelper(entity,dto,"create",dto.getFileRelationId())){
                return BaseResponse.fail("新增失败");
            }
        }else {
            entity = baseMapper.selectById(primaryId);
            BeanUtils.copyProperties(dto,entity);
            saveFile(entity, documentDtos);
            if (StrUtil.isNotEmpty(dto.getProjectType())){
                PreProject preProject = preProjectMapper.selectById(dto.getProjectId());
                if(preProject != null){
                    preProject.setpType(dto.getProjectType());
                    preProjectMapper.updateById(preProject);
                }
            }
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(primaryId);
    }
    private void saveFile(ProjectAcceptanceCalculationAudit entity, List<SupportDocumentDto> documentDtos) {
        log.info("saveFile:{}", JacksonUtils.toJSONString(documentDtos));
        for (SupportDocumentDto supportDocumentDto:documentDtos){
            sysFileAnnexService.addFormCopyAndRefreshRelationFile(Collections.singletonList(supportDocumentDto.getSupportDocument()),
                    Collections.singletonList(supportDocumentDto.getSupportDocumentFileIDs()), entity.getId());
        }
    }
    @Override
    public BaseResponse approvalData(ProjectAcceptanceCalculationAuditModel request) {
        String id = request.getId();
        ProjectAcceptanceCalculationAudit entity;
        if (StrUtil.isEmpty(id)) {
            return BaseResponse.fail("缺少请求参数");
        } else {
            entity = baseMapper.selectById(id);
            if (entity == null) {
                return new BaseResponse("信息不存在，请刷新重试！");
            }
            entity.setState(ApprovalStatusEnum.Already_Over.getIndex());
            entity.setStateTime(new Date());
            baseMapper.updateById(entity);
            QueryWrapper<ZNPJEvaluateBasisInformationQuarter>
                    znpjevaluateBasisInformationQuarterQueryWrapper = new QueryWrapper<>();
            znpjevaluateBasisInformationQuarterQueryWrapper.eq("del_flag", 1).eq("status", 1)
                    .eq("flow_status", ApprovalStatusEnum.Already_Over.getIndex()).eq("source_id", id);
            List<ZNPJEvaluateBasisInformationQuarter> znpjevaluateBasisInformationQuarterList =znpjEvaluateBasisInformationQuarterMapper.selectList(znpjevaluateBasisInformationQuarterQueryWrapper);
            if (CollectionUtil.isNotEmpty(znpjevaluateBasisInformationQuarterList)){
                for (ZNPJEvaluateBasisInformationQuarter n:znpjevaluateBasisInformationQuarterList){
                    znpjEvaluateBasisInformationQuarterMapper.deleteById(n);
                }
            }
            generatePACA(entity);
        }
        return BaseResponse.ok();
    }
    @Override
    public BaseResponse getList(ProjectAcceptanceCalculationAuditModel request, String uid) {
        log.info("查询列表:{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        QueryMap<ProjectAcceptanceCalculationAuditModel> queryMap = new QueryMap<>(request);
        queryMap.put("projectName").put("projectType").put("contractName").put("contractSecondCompanyName")
                .put("status");
        IPage<ProjectAcceptanceCalculationAuditModel> page = new Page<>(request.getPage(), request.getSize());
        List<ProjectAcceptanceCalculationAuditModel> list = baseMapper.getList(queryMap.getQuery(),page);
        log.info("查询列表:{}", JSON.toJSONString(list, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getDetail(String id) {
        ProjectAcceptanceCalculationAuditModel model = baseMapper.getDetail(id);
        if (model == null) {
            return new BaseResponse("信息不存在，请刷新重试！");
        }
        log.info("查询详情:{}", JSON.toJSONString(model, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(model);
    }
    @Override
    public void generatePACA(ProjectAcceptanceCalculationAudit entity) {
        //合同段加减分：
        //企业加减分：
        QueryWrapper<ZNPJEvaluateBasisInformationQuarter>
                znpjevaluateBasisInformationQuarterQueryWrapper = new QueryWrapper<>();
        znpjevaluateBasisInformationQuarterQueryWrapper.eq("del_flag", 1).eq("status", 1)
                .eq("flow_status", ApprovalStatusEnum.Already_Over.getIndex());
        List<ZNPJEvaluateBasisInformationQuarter> znpjevaluateBasisInformationQuarterList
                = znpjEvaluateBasisInformationQuarterMapper.selectList(znpjevaluateBasisInformationQuarterQueryWrapper);
        QueryWrapper<ZNPJIndicator> znpjIndicatorQueryWrapper = new QueryWrapper<>();
        znpjIndicatorQueryWrapper.eq("del_flag", 1).eq("status", 1);
        List<ZNPJIndicator> znpjIndicatorList = znpjIndicatorMapper.selectList(znpjIndicatorQueryWrapper);
        String status =entity.getStatus();
        List<ZNPJEvaluateBasisInformationQuarter> generateZNPJEvaluateBasisInformationQuarterList = new ArrayList<>();
        //已审定
        ZNPJIndicator znpjIndicator = znpjIndicatorList.stream().
                filter(n -> "【指标】-20240828-00000094".equals(n.getCode())).findFirst().orElse(null);
        if (znpjIndicator != null) {
            generateCompletedNotVerified(entity, znpjIndicator
                    ,znpjevaluateBasisInformationQuarterList, generateZNPJEvaluateBasisInformationQuarterList);
        }else {
            log.info("未找到指标:{}", entity.getId());
        }
        //已初验未竣工
        generateVerifiedNotCompleted(entity
                , znpjIndicatorList, znpjevaluateBasisInformationQuarterList
                , generateZNPJEvaluateBasisInformationQuarterList);
        //已竣工未送审
        generateCompletedNotSent(entity
                , znpjIndicatorList, znpjevaluateBasisInformationQuarterList
                , generateZNPJEvaluateBasisInformationQuarterList);
        //已送审未审定
        generateSentNotApproved(entity
                , znpjIndicatorList, znpjevaluateBasisInformationQuarterList
                , generateZNPJEvaluateBasisInformationQuarterList);
        if (CollectionUtil.isNotEmpty(generateZNPJEvaluateBasisInformationQuarterList)){
            log.info("{}生成数量:{}",  entity.getId(),generateZNPJEvaluateBasisInformationQuarterList.size());
            for (ZNPJEvaluateBasisInformationQuarter n:generateZNPJEvaluateBasisInformationQuarterList){
                generate(n);
            }
        }else {
            log.info("{}生成数量:{}", entity.getId(),0);
        }
    }

    @Override
    public BaseResponse approvalData2(ProjectAcceptanceCalculationAuditModel request) {
        String id = request.getId();
        ProjectAcceptanceCalculationAudit entity;
        if (StrUtil.isEmpty(id)) {
            return BaseResponse.fail("缺少请求参数");
        } else {
            entity = baseMapper.selectById(id);
            if (entity == null) {
                return new BaseResponse("信息不存在，请刷新重试！");
            }
            entity.setState(ApprovalStatusEnum.Invalid.getIndex());
            entity.setStateTime(new Date());
            baseMapper.updateById(entity);
            QueryWrapper<ZNPJEvaluateBasisInformationQuarter>
                    znpjevaluateBasisInformationQuarterQueryWrapper = new QueryWrapper<>();
            znpjevaluateBasisInformationQuarterQueryWrapper.eq("del_flag", 1).eq("status", 1)
                    .eq("flow_status", ApprovalStatusEnum.Already_Over.getIndex()).eq("source_id", id);
            List<ZNPJEvaluateBasisInformationQuarter> znpjevaluateBasisInformationQuarterList =znpjEvaluateBasisInformationQuarterMapper.selectList(znpjevaluateBasisInformationQuarterQueryWrapper);
            if (CollectionUtil.isNotEmpty(znpjevaluateBasisInformationQuarterList)){
                for (ZNPJEvaluateBasisInformationQuarter n:znpjevaluateBasisInformationQuarterList){
                    n.setDelFlag(2);
                    znpjEvaluateBasisInformationQuarterMapper.updateById(n);
                }
            }
            //generatePACA(entity);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse refreshAll(ProjectAcceptanceCalculationAuditModel request) {
        QueryWrapper<ProjectAcceptanceCalculationAudit> queryWrapper = new QueryWrapper<>();
        List<ProjectAcceptanceCalculationAudit> list = baseMapper.selectList(queryWrapper);
        List<String> idList = list.stream().map(ProjectAcceptanceCalculationAudit::getId).filter(Objects::nonNull).collect(Collectors.toList());
        QueryWrapper<ZNPJEvaluateBasisInformationQuarter>
                znpjevaluateBasisInformationQuarterQueryWrapper = new QueryWrapper<>();
        znpjevaluateBasisInformationQuarterQueryWrapper.eq("del_flag", 1).eq("status", 1)
                .eq("flow_status", ApprovalStatusEnum.Already_Over.getIndex()).in("source_id", idList);
        List<ZNPJEvaluateBasisInformationQuarter> znpjevaluateBasisInformationQuarterList =
                znpjEvaluateBasisInformationQuarterMapper.selectList(znpjevaluateBasisInformationQuarterQueryWrapper);
        if (CollectionUtil.isNotEmpty(znpjevaluateBasisInformationQuarterList)){
            znpjEvaluateBasisInformationQuarterMapper.deleteBatchIds(znpjevaluateBasisInformationQuarterList);
        }
        for (ProjectAcceptanceCalculationAudit n:list){
            n.setState(ApprovalStatusEnum.Already_Over.getIndex());
            n.setStateTime(new Date());
            SysFile sysFile = sysFileService.getById(n.getId());
            sysFile.setFileflowstatus(ApprovalStatusEnum.Already_Over.getIndex().toString());
            sysFileService.updateById(sysFile);
            baseMapper.updateById(n);
            generatePACA(n);
        }
        return BaseResponse.ok();
    }


    private void generateCompletedNotVerified(ProjectAcceptanceCalculationAudit entity,
                                              ZNPJIndicator znpjIndicator, List<ZNPJEvaluateBasisInformationQuarter> znpjevaluateBasisInformationQuarterList
            , List<ZNPJEvaluateBasisInformationQuarter> generateZNPJEvaluateBasisInformationQuarterList) {
        if (entity.getDraftDeadlineTime()!=null){
            if (entity.getFirstCheckTime()!= null){
                //拟定最迟初验日期
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(entity.getDraftDeadlineTime());

                //初步验收日
                Calendar firstCheckTimeCalendar = Calendar.getInstance();
                firstCheckTimeCalendar.setTime(entity.getFirstCheckTime());
                YearMonth firstCheckTime =YearMonth.of(firstCheckTimeCalendar.get(Calendar.YEAR),
                        firstCheckTimeCalendar.get(Calendar.MONTH)+1);
                YearMonth draftDeadlineTime =YearMonth.of(calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH)+1);
                log.info("firstCheckTime:{} draftDeadlineTime:{}", firstCheckTime, draftDeadlineTime);
                int overMonths = Math.toIntExact(draftDeadlineTime.until(firstCheckTime, ChronoUnit.MONTHS))-1;
                log.info("overMonths:{}", overMonths);
                if (overMonths>0){
                    for (int i=0;i<overMonths;i++){
                        YearMonth yearMonth =firstCheckTime.plusMonths(i);
                        Calendar calendar1 = Calendar.getInstance();
                        calendar1.set(yearMonth.getYear(), yearMonth.getMonthValue()-1, yearMonth.lengthOfMonth());
                        Date date = calendar1.getTime();
                        ZNPJEvaluateBasisInformationQuarter basisInformationQuarter
                                =  generateZNPJEvaluateBasisInformationQuarterInfo(entity, "07",date,
                                entity.getOverDraftDeadlineStartTime(),entity.getOverDraftDeadlineEndTime(), znpjIndicator);
                        log.info("basisInformationQuarter:{}", JacksonUtils.toJSONString(basisInformationQuarter));
                        if (basisInformationQuarter!=null){
                            List<ZNPJEvaluateBasisInformationQuarter> list = znpjevaluateBasisInformationQuarterList.stream()
                                    .filter(n ->
                                            n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                    && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                    && n.getMonth()!=null && n.getMonth().equals(basisInformationQuarter.getMonth())
                                                    && StrUtil.isNotEmpty(n.getInfoType()) && n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                    && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                    .collect(Collectors.toList());
                            if (CollectionUtil.isNotEmpty(generateZNPJEvaluateBasisInformationQuarterList)){
                                List<ZNPJEvaluateBasisInformationQuarter> list1 = generateZNPJEvaluateBasisInformationQuarterList.stream()
                                        .filter(n ->
                                                n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                        && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                        && n.getMonth()!=null && n.getMonth().equals(basisInformationQuarter.getMonth())
                                                        && StrUtil.isNotEmpty(n.getInfoType()) && n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                        && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                        .collect(Collectors.toList());
                                list.addAll(list1);
                            }
                            if (CollectionUtil.isEmpty(list)){
                                generateZNPJEvaluateBasisInformationQuarterList.add(basisInformationQuarter);
                            }
                        }
                    }
                }

            }else{
                Date now =DateTime.now();
                Calendar calendarNow = Calendar.getInstance();
                calendarNow.setTime(now);
                YearMonth yearMonthNow =YearMonth.of(calendarNow.get(Calendar.YEAR),
                        calendarNow.get(Calendar.MONTH)+1);
                //没有超过拟定最迟初验日天数,当前时间大于拟定最迟初验日期 生成当月
                if(entity.getDraftDeadlineTime()!=null && entity.getDraftDeadlineTime().compareTo(DateTime.now())<0){
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(yearMonthNow.getYear(), yearMonthNow.getMonthValue()-1, yearMonthNow.lengthOfMonth());
                    Date date = calendar1.getTime();
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(entity.getDraftDeadlineTime());
                    calendar2.add(Calendar.DATE, 1);
                    Date date1 = calendar2.getTime();
                    ZNPJEvaluateBasisInformationQuarter basisInformationQuarter
                            =  generateZNPJEvaluateBasisInformationQuarterInfo(entity, "08",date,
                            date1,date, znpjIndicator);
                    log.info("basisInformationQuarter:{}", JacksonUtils.toJSONString(basisInformationQuarter));
                    if (basisInformationQuarter!=null){
                        List<ZNPJEvaluateBasisInformationQuarter> list = znpjevaluateBasisInformationQuarterList.stream()
                                .filter(n ->
                                        n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                && n.getYear().equals(basisInformationQuarter.getYear())
                                                && n.getMonth().equals(basisInformationQuarter.getMonth())
                                                && n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                && n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                .collect(Collectors.toList());
                        if (CollectionUtil.isNotEmpty(generateZNPJEvaluateBasisInformationQuarterList)){
                            List<ZNPJEvaluateBasisInformationQuarter> list1 = generateZNPJEvaluateBasisInformationQuarterList.stream()
                                    .filter(n ->
                                            n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                    && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                    && n.getMonth()!=null && n.getMonth().equals(basisInformationQuarter.getMonth())
                                                    && StrUtil.isNotEmpty(n.getInfoType()) && n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                    && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                    .collect(Collectors.toList());
                            list.addAll(list1);
                        }
                        if (CollectionUtil.isEmpty(list)){
                            generateZNPJEvaluateBasisInformationQuarterList.add(basisInformationQuarter);
                        }
                    }
                }
            }
        }

    }
    private void generateVerifiedNotCompleted(ProjectAcceptanceCalculationAudit entity,
                                               List<ZNPJIndicator> znpjIndicatorList,
                                               List<ZNPJEvaluateBasisInformationQuarter> znpjevaluateBasisInformationQuarterList,
                                              List<ZNPJEvaluateBasisInformationQuarter> generateZNPJEvaluateBasisInformationQuarterList) {
        //拟定最迟竣工验收日期
        if (CollectionUtil.isEmpty(znpjIndicatorList)){
            return;
        }
        if (entity.getDraftCompleteDeadlineTime()==null){
            return;
        }
        if (StrUtil.isNotEmpty(entity.getExcludedResponsibility())){
            return;
        }
        Calendar calendar = Calendar.getInstance();
        //拟定最迟竣工验收日期
        calendar.setTime(entity.getDraftCompleteDeadlineTime());
        if (entity.getCompleteTime()!= null){
            //竣工日期
            Calendar completeTimeCalendar = Calendar.getInstance();
            completeTimeCalendar.setTime(entity.getCompleteTime());
            YearMonth completeTime =YearMonth.of(completeTimeCalendar.get(Calendar.YEAR),
                    completeTimeCalendar.get(Calendar.MONTH)+1);
            YearMonth draftCompleteDeadlineTime =YearMonth.of(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH)+1);
            log.info("completeTime:{} draftCompleteDeadlineTime:{}", completeTime, draftCompleteDeadlineTime);
            int overMonths = Math.toIntExact(draftCompleteDeadlineTime.until(completeTime, ChronoUnit.MONTHS))-1;
            log.info("VerifiedNotCompletedOverMonths:{}", overMonths);
            if (overMonths>0){
                for (int i=0;i<overMonths;i++){
                    YearMonth yearMonth =draftCompleteDeadlineTime.plusMonths(i);
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(yearMonth.getYear(), yearMonth.getMonthValue()-1, yearMonth.lengthOfMonth());
                    Date date = calendar1.getTime();
                    int days = Math.toIntExact(DateUtil.between(date, calendar.getTime(), DateUnit.DAY, true));
                    days=days+(entity.getStandardCompleteDays()!=null?entity.getStandardCompleteDays().intValue():0);
                    String infoType = "";
                    ZNPJIndicator znpjIndicator = null;
                    log.info("verifiedNotCompletedDays:{}", days);
                    if (days>270){
                         znpjIndicator = znpjIndicatorList.stream().
                                filter(n -> "【指标】-20250821-00000243".equals(n.getCode()))
                                 .findFirst().orElse(null);
                        if (znpjIndicator != null) {
                             infoType ="08";
                        }else {
                            log.info("未找到指标:{}", entity.getId());
                        }
                    }else if (days>180){
                         znpjIndicator = znpjIndicatorList.stream().
                                 filter(n -> "【指标】-20250821-00000244".equals(n.getCode()))
                                 .findFirst().orElse(null);
                         if (znpjIndicator != null) {
                             infoType ="20";
                         }else {
                             log.info("未找到指标:{}", entity.getId());
                         }
                     }else if (days>90){
                        znpjIndicator = znpjIndicatorList.stream().
                                filter(n -> "【指标】-20250821-00000245".equals(n.getCode()))
                                .findFirst().orElse(null);
                        if (znpjIndicator != null) {
                            infoType ="19";
                        }else {
                            log.info("未找到指标:{}", entity.getId());
                        }
                    }

                    if (StrUtil.isNotEmpty(infoType)&& znpjIndicator != null){
                        ZNPJEvaluateBasisInformationQuarter basisInformationQuarter
                                =  generateZNPJEvaluateBasisInformationQuarterInfo(entity,infoType, date,
                                entity.getOverDraftCompleteDeadlineStartTime(),entity.getOverDraftCompleteDeadlineEndTime(), znpjIndicator);
                        log.info("basisInformationQuarter:{}", JacksonUtils.toJSONString(basisInformationQuarter));
                        if (basisInformationQuarter!=null){
                            List<ZNPJEvaluateBasisInformationQuarter> list = znpjevaluateBasisInformationQuarterList.stream()
                                    .filter(n ->
                                            n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                    && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                    && n.getMonth()!=null && n.getMonth().equals(basisInformationQuarter.getMonth())
                                                    && StrUtil.isNotEmpty(n.getInfoType()) && n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                    && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                    .collect(Collectors.toList());
                            if (CollectionUtil.isNotEmpty(generateZNPJEvaluateBasisInformationQuarterList)){
                                List<ZNPJEvaluateBasisInformationQuarter> list1 = generateZNPJEvaluateBasisInformationQuarterList.stream()
                                        .filter(n ->
                                                n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                        && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                        && n.getMonth()!=null && n.getMonth().equals(basisInformationQuarter.getMonth())
                                                        && StrUtil.isNotEmpty(n.getInfoType()) && n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                        && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                        .collect(Collectors.toList());
                                list.addAll(list1);
                            }
                            if (CollectionUtil.isEmpty(list)){
                                generateZNPJEvaluateBasisInformationQuarterList.add(basisInformationQuarter);
                            }
                        }
                    }
                }
            }

        }else{
            //没有超过拟定最迟初验日天数,当前时间大于拟定最迟初验日期 生成当月
            Date now =DateTime.now();
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(now);
            YearMonth yearMonthNow =YearMonth.of(calendarNow.get(Calendar.YEAR),
                    calendarNow.get(Calendar.MONTH)+1);
            if(entity.getDraftCompleteDeadlineTime()!=null&&entity.getDraftCompleteDeadlineTime().compareTo(now)<0){
                String infoType = "";
                ZNPJIndicator znpjIndicator = null;
                int days = Math.toIntExact(DateUtil.between(now, calendar.getTime(), DateUnit.DAY, true));
                days=days+(entity.getStandardCompleteDays()!=null?entity.getStandardCompleteDays().intValue():0);
                log.info("verifiedNotCompletedDays:{}", days);
                if (days>270){
                    znpjIndicator = znpjIndicatorList.stream().
                            filter(n -> "【指标】-20250821-00000243".equals(n.getCode()))
                            .findFirst().orElse(null);
                    if (znpjIndicator != null) {
                        infoType ="08";
                    }else {
                        log.info("未找到指标:{}", entity.getId());
                    }
                }else if (days>180){
                    znpjIndicator = znpjIndicatorList.stream().
                            filter(n -> "【指标】-20250821-00000244".equals(n.getCode()))
                            .findFirst().orElse(null);
                    if (znpjIndicator != null) {
                        infoType ="20";
                    }else {
                        log.info("未找到指标:{}", entity.getId());
                    }
                }else if (days>90){
                    znpjIndicator = znpjIndicatorList.stream().
                            filter(n -> "【指标】-20250821-00000245".equals(n.getCode()))
                            .findFirst().orElse(null);
                    if (znpjIndicator != null) {
                        infoType ="19";
                    }else {
                        log.info("未找到指标:{}", entity.getId());
                    }
                }
                if (StrUtil.isNotEmpty(infoType) && znpjIndicator != null){
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(yearMonthNow.getYear(), yearMonthNow.getMonthValue()-1, yearMonthNow.lengthOfMonth());
                    Date date = calendar1.getTime();
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(entity.getDraftCompleteDeadlineTime());
                    calendar2.add(Calendar.DATE, 1);
                    Date date1 = calendar2.getTime();
                    ZNPJEvaluateBasisInformationQuarter basisInformationQuarter
                            =  generateZNPJEvaluateBasisInformationQuarterInfoQuarter(entity,infoType, date,
                            date1, date,  znpjIndicator);
                    log.info("basisInformationQuarter:{}", JacksonUtils.toJSONString(basisInformationQuarter));
                    if (basisInformationQuarter!=null){
                        List<ZNPJEvaluateBasisInformationQuarter> list = znpjevaluateBasisInformationQuarterList.stream()
                                .filter(n ->
                                        n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                && n.getMonth()!=null && n.getMonth().equals(basisInformationQuarter.getMonth())
                                                && StrUtil.isNotEmpty(n.getInfoType()) && n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                .collect(Collectors.toList());
                        if (CollectionUtil.isNotEmpty(generateZNPJEvaluateBasisInformationQuarterList)){
                            List<ZNPJEvaluateBasisInformationQuarter> list1 = generateZNPJEvaluateBasisInformationQuarterList.stream()
                                    .filter(n ->
                                            n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                    && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                    && n.getMonth()!=null && n.getMonth().equals(basisInformationQuarter.getMonth())
                                                    && StrUtil.isNotEmpty(n.getInfoType()) && n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                    && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                    .collect(Collectors.toList());
                            list.addAll(list1);
                        }
                        if (CollectionUtil.isEmpty(list)){
                            generateZNPJEvaluateBasisInformationQuarterList.add(basisInformationQuarter);
                        }
                    }
                }
            }
        }
    }
    private void generateCompletedNotSent(ProjectAcceptanceCalculationAudit entity,
                                          List<ZNPJIndicator> znpjIndicatorList,
                                          List<ZNPJEvaluateBasisInformationQuarter> znpjevaluateBasisInformationQuarterList,
                                          List<ZNPJEvaluateBasisInformationQuarter> generateZNPJEvaluateBasisInformationQuarterList) {
        // 已竣工未送审    施工市政 +28/  园林绿化+90
        //天数181-360 每季度扣2分
        //天数361-540 每季度扣3分
        //天数541以上 每季度扣4分
        if (CollectionUtil.isEmpty(znpjIndicatorList)){
            return;
        }
        if (entity.getDraftSettlementDeadlineTime()==null){
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(entity.getDraftSettlementDeadlineTime());
        if (entity.getSubmitTime()!=null){
            Calendar submitTimeCalendar = Calendar.getInstance();
            submitTimeCalendar.setTime(entity.getSubmitTime());
            YearMonth submitTime =YearMonth.of(submitTimeCalendar.get(Calendar.YEAR),
                    submitTimeCalendar.get(Calendar.MONTH)+1);
            YearMonth draftSettlementDeadlineTime =YearMonth.of(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH)+1);
            log.info("submitTime:{} draftCompleteDeadlineTime:{}", submitTime, draftSettlementDeadlineTime);
            int overMonths = Math.toIntExact(draftSettlementDeadlineTime.until(submitTime, ChronoUnit.MONTHS))-1;
            log.info("completedNotSentOverMonths:{}", overMonths);
            if (overMonths>0){
                for (int i=0;i<overMonths;i++){
                    String infoType = "";
                    ZNPJIndicator znpjIndicator = null;
                    YearMonth yearMonth =draftSettlementDeadlineTime.plusMonths(i);
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(yearMonth.getYear(), yearMonth.getMonthValue()-1, yearMonth.lengthOfMonth());
                    Date date = calendar1.getTime();
                    int days = Math.toIntExact(DateUtil.between(date, calendar.getTime(), DateUnit.DAY, true));
                    days=days+(entity.getStandardSettlementDays()!=null?entity.getStandardSettlementDays().intValue():0)
                            +(entity.getContractAgreementDays()!=null?entity.getContractAgreementDays().intValue():0);
                    log.info("completedNotSentDays:{}", days);
                    if (days>540){
                        znpjIndicator = znpjIndicatorList.stream().
                                filter(n -> "【指标】-20250821-00000257".equals(n.getCode()))
                                .findFirst().orElse(null);
                        if (znpjIndicator != null) {
                            infoType ="50";
                        }else {
                            log.info("未找到指标:{}", entity.getId());
                        }
                    }else if (days>360){
                        znpjIndicator = znpjIndicatorList.stream().
                                filter(n -> "【指标】-20250821-00000256".equals(n.getCode()))
                                .findFirst().orElse(null);
                        if (znpjIndicator != null) {
                            infoType ="49";
                        }else {
                            log.info("未找到指标:{}", entity.getId());
                        }
                    }else if (days>180){
                        znpjIndicator = znpjIndicatorList.stream().
                                filter(n -> "【指标】-20250821-00000255".equals(n.getCode()))
                                .findFirst().orElse(null);
                        if (znpjIndicator != null) {
                            infoType ="48";
                        }else {
                            log.info("未找到指标:{}", entity.getId());
                        }
                    }
                    if (StrUtil.isNotEmpty(infoType)&& znpjIndicator != null){
                        ZNPJEvaluateBasisInformationQuarter basisInformationQuarter
                                =  generateZNPJEvaluateBasisInformationQuarterInfoQuarter(entity,infoType, date,
                                entity.getOverDraftSettlementDeadlineStartTime(), entity.getOverDraftSettlementDeadlineEndTime(), znpjIndicator);
                        log.info("basisInformationQuarter:{}", JacksonUtils.toJSONString(basisInformationQuarter));
                        if (basisInformationQuarter!=null){
                            List<ZNPJEvaluateBasisInformationQuarter> list = znpjevaluateBasisInformationQuarterList.stream()
                                    .filter(n ->
                                            n.getQuarter()!=null &&  n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                    && n.getYear()!=null &&  n.getYear().equals(basisInformationQuarter.getYear())
                                                    && StrUtil.isNotEmpty(n.getInfoType()) &&  n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                    && StrUtil.isNotEmpty(n.getSourceId()) &&  n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                    .collect(Collectors.toList());
                            if (CollectionUtil.isNotEmpty(generateZNPJEvaluateBasisInformationQuarterList)){
                                List<ZNPJEvaluateBasisInformationQuarter> list1 = generateZNPJEvaluateBasisInformationQuarterList.stream()
                                        .filter(n ->
                                                n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                        && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                        && StrUtil.isNotEmpty(n.getInfoType()) && n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                        && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                        .collect(Collectors.toList());
                                list.addAll(list1);
                            }
                            if (CollectionUtil.isEmpty(list)){
                                ZNPJEvaluateBasisInformationQuarter same = generateZNPJEvaluateBasisInformationQuarterList.stream().filter(n ->
                                        n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId())).findFirst().orElse(null);
                                if (same!=null){
                                    generateZNPJEvaluateBasisInformationQuarterList.remove(same);
                                }
                                generateZNPJEvaluateBasisInformationQuarterList.add(basisInformationQuarter);
                            }
                        }
                    }
                }
            }
        }else {
            Date now =DateTime.now();
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(now);
            YearMonth yearMonthNow =YearMonth.of(calendarNow.get(Calendar.YEAR),
                    calendarNow.get(Calendar.MONTH)+1);
            if(entity.getDraftSettlementDeadlineTime()!=null && entity.getDraftSettlementDeadlineTime().compareTo(now)<0){
                String infoType = "";
                ZNPJIndicator znpjIndicator = null;
                int days = Math.toIntExact(DateUtil.between(now, calendar.getTime(), DateUnit.DAY, true));
                days=days+(entity.getStandardSettlementDays()!=null?entity.getStandardSettlementDays().intValue():0)
                        +(entity.getContractAgreementDays()!=null?entity.getContractAgreementDays().intValue():0);
                log.info("completedNotSentDays:{}", days);
                if (days>540){
                    znpjIndicator = znpjIndicatorList.stream().
                            filter(n -> "【指标】-20250821-00000257".equals(n.getCode()))
                            .findFirst().orElse(null);
                    if (znpjIndicator != null) {
                        infoType ="50";
                    }else {
                        log.info("未找到指标:{}", entity.getId());
                    }
                }else if (days>360){
                    znpjIndicator = znpjIndicatorList.stream().
                            filter(n -> "【指标】-20250821-00000256".equals(n.getCode()))
                            .findFirst().orElse(null);
                    if (znpjIndicator != null) {
                        infoType ="49";
                    }else {
                        log.info("未找到指标:{}", entity.getId());
                    }
                }else if (days>180){
                    znpjIndicator = znpjIndicatorList.stream().
                            filter(n -> "【指标】-20250821-00000255".equals(n.getCode()))
                            .findFirst().orElse(null);
                    if (znpjIndicator != null) {
                        infoType ="48";
                    }else {
                        log.info("未找到指标:{}", entity.getId());
                    }
                }
                if (StrUtil.isNotEmpty(infoType)&& znpjIndicator != null){
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(yearMonthNow.getYear(), yearMonthNow.getMonthValue()-1, yearMonthNow.lengthOfMonth());
                    Date date = calendar1.getTime();
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(entity.getDraftSettlementDeadlineTime());
                    calendar2.add(Calendar.DATE, 1);
                    Date date1 = calendar2.getTime();
                    ZNPJEvaluateBasisInformationQuarter basisInformationQuarter
                            =  generateZNPJEvaluateBasisInformationQuarterInfoQuarter(entity,infoType, date,
                            date1, date,  znpjIndicator);
                    log.info("basisInformationQuarter:{}", JacksonUtils.toJSONString(basisInformationQuarter));
                    if (basisInformationQuarter!=null){
                        List<ZNPJEvaluateBasisInformationQuarter> list = znpjevaluateBasisInformationQuarterList.stream()
                                .filter(n ->
                                        n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                && StrUtil.isNotEmpty(n.getInfoType()) && n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                .collect(Collectors.toList());
                        if (CollectionUtil.isNotEmpty(generateZNPJEvaluateBasisInformationQuarterList)){
                            List<ZNPJEvaluateBasisInformationQuarter> list1 = generateZNPJEvaluateBasisInformationQuarterList.stream()
                                    .filter(n ->
                                            n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                    && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                    && StrUtil.isNotEmpty(n.getInfoType()) && n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                    && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                    .collect(Collectors.toList());
                            list.addAll(list1);
                        }
                        if (CollectionUtil.isEmpty(list)){
                            ZNPJEvaluateBasisInformationQuarter same = generateZNPJEvaluateBasisInformationQuarterList.stream().filter(n ->
                                    n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                            && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                            && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId())).findFirst().orElse(null);
                            if (same!=null){
                                generateZNPJEvaluateBasisInformationQuarterList.remove(same);
                            }
                            generateZNPJEvaluateBasisInformationQuarterList.add(basisInformationQuarter);
                        }
                    }
                }
            }
        }
    }
    private void generateSentNotApproved(ProjectAcceptanceCalculationAudit entity,
                                         List<ZNPJIndicator> znpjIndicatorList,
                                         List<ZNPJEvaluateBasisInformationQuarter> znpjevaluateBasisInformationQuarterList,
                                         List<ZNPJEvaluateBasisInformationQuarter> generateZNPJEvaluateBasisInformationQuarterList) {
        /*
            **
             * 拟定最迟结算审核日期
             *
            private Date draftAuditDeadlineTime;

            **
             * 审定日期
             *
            private Date auditTime;
         */
        //天数361-720 每季度扣2分
        //天数721-1080 每季度扣3分
        //天数1081以上 每季度扣4分
        if (CollectionUtil.isEmpty(znpjIndicatorList)){
            return;
        }
        if (entity.getDraftAuditDeadlineTime()==null){
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(entity.getDraftAuditDeadlineTime());
        if (entity.getAuditTime()!=null){
            Calendar auditTimeCalendar = Calendar.getInstance();
            auditTimeCalendar.setTime(entity.getAuditTime());
            YearMonth auditTime =YearMonth.of(auditTimeCalendar.get(Calendar.YEAR),
                    auditTimeCalendar.get(Calendar.MONTH)+1);
            YearMonth draftAuditDeadlineTime =YearMonth.of(calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH)+1);
            log.info("auditTime:{} draftCompleteDeadlineTime:{}", auditTime, draftAuditDeadlineTime);
            int overMonths = Math.toIntExact(draftAuditDeadlineTime.until(auditTime, ChronoUnit.MONTHS))-1;
            log.info("sentNotApprovedOverMonths:{}", overMonths);
            if (overMonths>0){
                for (int i=0;i<overMonths;i++){
                    String infoType = "";
                    ZNPJIndicator znpjIndicator = null;
                    YearMonth yearMonth =draftAuditDeadlineTime.plusMonths(i);
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(yearMonth.getYear(), yearMonth.getMonthValue()-1, yearMonth.lengthOfMonth());
                    Date date = calendar1.getTime();
                    int days = Math.toIntExact(DateUtil.between(date, calendar.getTime(), DateUnit.DAY, true));
                    days=days+(entity.getStandardAuditDays()!=null?entity.getStandardAuditDays().intValue():0);
                    log.info("sentNotApprovedDays:{}", days);
                    if (days>1080){
                        znpjIndicator = znpjIndicatorList.stream().
                                filter(n -> "【指标】-20250821-00000260".equals(n.getCode()))
                                .findFirst().orElse(null);
                        if (znpjIndicator != null) {
                            infoType ="53";
                        }else {
                            log.info("未找到指标:{}", entity.getId());
                        }
                    }else if (days>720){
                        znpjIndicator = znpjIndicatorList.stream().
                                filter(n -> "【指标】-20250821-00000259".equals(n.getCode()))
                                .findFirst().orElse(null);
                        if (znpjIndicator != null) {
                            infoType ="52";
                        }else {
                            log.info("未找到指标:{}", entity.getId());
                        }
                    }else if (days>360){
                        znpjIndicator = znpjIndicatorList.stream().
                                filter(n -> "【指标】-20250821-00000258".equals(n.getCode()))
                                .findFirst().orElse(null);
                        if (znpjIndicator != null) {
                            infoType ="51";
                        }else {
                            log.info("未找到指标:{}", entity.getId());
                        }
                    }
                    if (StrUtil.isNotEmpty(infoType) && znpjIndicator != null){
                        ZNPJEvaluateBasisInformationQuarter basisInformationQuarter
                                =  generateZNPJEvaluateBasisInformationQuarterInfoQuarter(entity,infoType, date,
                                entity.getOverDraftAuditDeadlineStartTime(),entity.getOverDraftAuditDeadlineEndTime(), znpjIndicator);
                        log.info("basisInformationQuarter:{}", JacksonUtils.toJSONString(basisInformationQuarter));
                        if (basisInformationQuarter!=null){
                            List<ZNPJEvaluateBasisInformationQuarter> list = znpjevaluateBasisInformationQuarterList.stream()
                                    .filter(n ->
                                           n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                    && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                    && StrUtil.isNotEmpty(n.getInfoType()) && n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                    && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                    .collect(Collectors.toList());
                            if (CollectionUtil.isNotEmpty(generateZNPJEvaluateBasisInformationQuarterList)){
                                List<ZNPJEvaluateBasisInformationQuarter> list1 = generateZNPJEvaluateBasisInformationQuarterList.stream()
                                        .filter(n ->
                                                n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                        && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                        && StrUtil.isNotEmpty(n.getInfoType()) && n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                        && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                        .collect(Collectors.toList());
                                list.addAll(list1);
                            }
                            if (CollectionUtil.isEmpty(list)){
                                ZNPJEvaluateBasisInformationQuarter same = generateZNPJEvaluateBasisInformationQuarterList.stream().filter(n ->
                                        n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId())).findFirst().orElse(null);
                                if (same!=null){
                                    generateZNPJEvaluateBasisInformationQuarterList.remove(same);
                                }
                                generateZNPJEvaluateBasisInformationQuarterList.add(basisInformationQuarter);
                            }
                        }
                    }
                }
            }
        }else {
            Date now =DateTime.now();
            Calendar calendarNow = Calendar.getInstance();
            calendarNow.setTime(now);
            YearMonth yearMonthNow =YearMonth.of(calendarNow.get(Calendar.YEAR),
                    calendarNow.get(Calendar.MONTH)+1);
            if(entity.getDraftAuditDeadlineTime()!=null && entity.getDraftAuditDeadlineTime().compareTo(now)<0){
                String infoType = "";
                ZNPJIndicator znpjIndicator = null;
                int days = Math.toIntExact(DateUtil.between(now, calendar.getTime(), DateUnit.DAY, true));
                days=days+(entity.getStandardAuditDays()!=null?entity.getStandardAuditDays().intValue():0);
                log.info("sentNotApprovedDays:{}", days);
                if (days>1080){
                    znpjIndicator = znpjIndicatorList.stream().
                            filter(n -> "【指标】-20250821-00000260".equals(n.getCode()))
                            .findFirst().orElse(null);
                    if (znpjIndicator != null) {
                        infoType ="53";
                    }else {
                        log.info("未找到指标:{}", entity.getId());
                    }
                }else if (days>720){
                    znpjIndicator = znpjIndicatorList.stream().
                            filter(n -> "【指标】-20250821-00000259".equals(n.getCode()))
                            .findFirst().orElse(null);
                    if (znpjIndicator != null) {
                        infoType ="52";
                    }else {
                        log.info("未找到指标:{}", entity.getId());
                    }
                }else if (days>360){
                    znpjIndicator = znpjIndicatorList.stream().
                            filter(n -> "【指标】-20250821-00000258".equals(n.getCode()))
                            .findFirst().orElse(null);
                    if (znpjIndicator != null) {
                        infoType ="51";
                    }else {
                        log.info("未找到指标:{}", entity.getId());
                    }
                }
                if (StrUtil.isNotEmpty(infoType) && znpjIndicator != null){
                    Calendar calendar1 = Calendar.getInstance();
                    calendar1.set(yearMonthNow.getYear(), yearMonthNow.getMonthValue()-1, yearMonthNow.lengthOfMonth());
                    Date date = calendar1.getTime();
                    Calendar calendar2 = Calendar.getInstance();
                    calendar2.setTime(entity.getDraftAuditDeadlineTime());
                    calendar2.add(Calendar.DATE, 1);
                    Date date1 = calendar2.getTime();
                    ZNPJEvaluateBasisInformationQuarter basisInformationQuarter
                            =  generateZNPJEvaluateBasisInformationQuarterInfoQuarter(entity,infoType, date,
                            date1,date,znpjIndicator);
                    log.info("basisInformationQuarter:{}", JacksonUtils.toJSONString(basisInformationQuarter));
                    if (basisInformationQuarter!=null){
                        List<ZNPJEvaluateBasisInformationQuarter> list = znpjevaluateBasisInformationQuarterList.stream()
                                .filter(n ->
                                        n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                && StrUtil.isNotEmpty(n.getInfoType()) && n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                .collect(Collectors.toList());
                        if (CollectionUtil.isNotEmpty(generateZNPJEvaluateBasisInformationQuarterList)){
                            List<ZNPJEvaluateBasisInformationQuarter> list1 = generateZNPJEvaluateBasisInformationQuarterList.stream()
                                    .filter(n ->
                                            n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                                    && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                                    && StrUtil.isNotEmpty(n.getInfoType()) && n.getInfoType().equals(basisInformationQuarter.getInfoType())
                                                    && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId()))
                                    .collect(Collectors.toList());
                            list.addAll(list1);
                        }
                        if (CollectionUtil.isEmpty(list)){
                            ZNPJEvaluateBasisInformationQuarter same = generateZNPJEvaluateBasisInformationQuarterList.stream().filter(n ->
                                    n.getQuarter()!=null && n.getQuarter().equals(basisInformationQuarter.getQuarter())
                                            && n.getYear()!=null && n.getYear().equals(basisInformationQuarter.getYear())
                                            && StrUtil.isNotEmpty(n.getSourceId()) && n.getSourceId().equals(basisInformationQuarter.getSourceId())).findFirst().orElse(null);
                            if (same!=null){
                                generateZNPJEvaluateBasisInformationQuarterList.remove(same);
                            }
                            generateZNPJEvaluateBasisInformationQuarterList.add(basisInformationQuarter);
                        }
                    }
                }
            }
        }
    }


    private ZNPJEvaluateBasisInformationQuarter generateZNPJEvaluateBasisInformationQuarterInfo(ProjectAcceptanceCalculationAudit entity,
                                                                                  String infoType, Date time, Date startTime, Date endTime,
                                                                                  ZNPJIndicator znpjIndicator) {
        ZNPJEvaluateBasisInformationQuarter znpjevaluateBasisInformationQuarter = new ZNPJEvaluateBasisInformationQuarter();
        znpjevaluateBasisInformationQuarter.setId(IdWorker.getGenerateID());
        znpjevaluateBasisInformationQuarter.setType("01");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        znpjevaluateBasisInformationQuarter.setYear(calendar.get(Calendar.YEAR));
        znpjevaluateBasisInformationQuarter.setQuarter(calendar.get(Calendar.MONTH) / 3 + 1);
        znpjevaluateBasisInformationQuarter.setMonth(calendar.get(Calendar.MONTH) + 1);
        znpjevaluateBasisInformationQuarter.setProjectId(entity.getProjectId());
        PreProject preProject = preProjectMapper.selectById(entity.getProjectId());
        if (preProject == null){
            log.info("未找到项目id:{}", entity.getProjectId());
            return null;
        }
        znpjevaluateBasisInformationQuarter.setBasisInformationProjectType(preProject.getpType());
        znpjevaluateBasisInformationQuarter.setContractUnitType("07");
        znpjevaluateBasisInformationQuarter.setContractId(entity.getContractId());
        ConContract conContract = conContractMapper.selectById(entity.getContractId());
        if (conContract == null) {
            log.info("未找到合同id:{}", entity.getContractId());
            return null;
        }else {
            if (StrUtil.isNotEmpty(conContract.getConPartyBId())) {
                znpjevaluateBasisInformationQuarter.setCompanyId(conContract.getConPartyBId());
                znpjevaluateBasisInformationQuarter.setIsContainsSg(1);
            }else {
                log.info("未找到单位id:{}", entity.getContractId());
                return null;
            }
        }
        znpjevaluateBasisInformationQuarter.setIndicatorId(znpjIndicator.getId());
        znpjevaluateBasisInformationQuarter.setScoreType(String.valueOf(znpjIndicator.getScoreType()));
        znpjevaluateBasisInformationQuarter.setIndicatorVersionId(znpjIndicator.getIndicatorVersionId());
        znpjevaluateBasisInformationQuarter.setScore(Double.valueOf(znpjIndicator.getScore()));
        znpjevaluateBasisInformationQuarter.setEffectiveStartTime(startTime);
        znpjevaluateBasisInformationQuarter.setEffectiveEndTime(endTime);
        setEffective(znpjevaluateBasisInformationQuarter);
        znpjevaluateBasisInformationQuarter.setInfoType(infoType);
        znpjevaluateBasisInformationQuarter.setSourceCode("ProjectAuditRecordYw");
        znpjevaluateBasisInformationQuarter.setSourceId(entity.getId());
        znpjevaluateBasisInformationQuarter.setPlusMinusScoreType(znpjIndicator.getPlusMinusScoreType());
        znpjevaluateBasisInformationQuarter.setDelFlag(1);
        znpjevaluateBasisInformationQuarter.setStatus(1);
        znpjevaluateBasisInformationQuarter.setFlowStatus(ApprovalStatusEnum.Already_Over.getIndex());
        znpjevaluateBasisInformationQuarter.setReviewTime(DateTime.now());
        znpjevaluateBasisInformationQuarter.setCreateBy(entity.getCreateUid());
        znpjevaluateBasisInformationQuarter.setCreateName(entity.getCreateUname());
        znpjevaluateBasisInformationQuarter.setCreateTime(DateTime.now());
        znpjevaluateBasisInformationQuarter.setUpdateBy(entity.getCreateUid());
        znpjevaluateBasisInformationQuarter.setUpdateTime(DateTime.now());
        return znpjevaluateBasisInformationQuarter;
    }
    private ZNPJEvaluateBasisInformationQuarter generateZNPJEvaluateBasisInformationQuarterInfoQuarter(ProjectAcceptanceCalculationAudit entity,
                                                                                                String infoType, Date time,Date startTime, Date endTime,
                                                                                                ZNPJIndicator znpjIndicator) {
        ZNPJEvaluateBasisInformationQuarter znpjevaluateBasisInformationQuarter = new ZNPJEvaluateBasisInformationQuarter();
        znpjevaluateBasisInformationQuarter.setId(IdWorker.getGenerateID());
        znpjevaluateBasisInformationQuarter.setType("02");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(time);
        znpjevaluateBasisInformationQuarter.setYear(calendar.get(Calendar.YEAR));
        znpjevaluateBasisInformationQuarter.setQuarter(calendar.get(Calendar.MONTH) / 3 + 1);
        znpjevaluateBasisInformationQuarter.setProjectId(entity.getProjectId());
        PreProject preProject = preProjectMapper.selectById(entity.getProjectId());
        if (preProject == null){
            log.info("未找到项目id:{}", entity.getProjectId());
            return null;
        }
        znpjevaluateBasisInformationQuarter.setBasisInformationProjectType(preProject.getpType());
        znpjevaluateBasisInformationQuarter.setContractUnitType("07");
        znpjevaluateBasisInformationQuarter.setContractId(entity.getContractId());
        ConContract conContract = conContractMapper.selectById(entity.getContractId());
        if (conContract == null) {
            log.info("未找到合同id:{}", entity.getContractId());
            return null;
        }else {
            if (StrUtil.isNotEmpty(conContract.getConPartyBId())) {
                znpjevaluateBasisInformationQuarter.setCompanyId(conContract.getConPartyBId());
                znpjevaluateBasisInformationQuarter.setIsContainsSg(1);
            }else {
                log.info("未找到单位id:{}", entity.getContractId());
                return null;
            }
        }
        znpjevaluateBasisInformationQuarter.setIndicatorId(znpjIndicator.getId());
        znpjevaluateBasisInformationQuarter.setScoreType(String.valueOf(znpjIndicator.getScoreType()));
        znpjevaluateBasisInformationQuarter.setIndicatorVersionId(znpjIndicator.getIndicatorVersionId());
        znpjevaluateBasisInformationQuarter.setScore(Double.valueOf(znpjIndicator.getScore()));
        znpjevaluateBasisInformationQuarter.setEffectiveStartTime(startTime);
        znpjevaluateBasisInformationQuarter.setEffectiveEndTime(endTime);
        setEffective(znpjevaluateBasisInformationQuarter);
        znpjevaluateBasisInformationQuarter.setInfoType(infoType);
        znpjevaluateBasisInformationQuarter.setSourceCode("ProjectAuditRecordYw");
        znpjevaluateBasisInformationQuarter.setSourceId(entity.getId());
        znpjevaluateBasisInformationQuarter.setPlusMinusScoreType(znpjIndicator.getPlusMinusScoreType());
        znpjevaluateBasisInformationQuarter.setDelFlag(1);
        znpjevaluateBasisInformationQuarter.setStatus(1);
        znpjevaluateBasisInformationQuarter.setFlowStatus(ApprovalStatusEnum.Already_Over.getIndex());
        znpjevaluateBasisInformationQuarter.setReviewTime(DateTime.now());
        znpjevaluateBasisInformationQuarter.setCreateBy(entity.getCreateUid());
        znpjevaluateBasisInformationQuarter.setCreateName(entity.getCreateUname());
        znpjevaluateBasisInformationQuarter.setCreateTime(DateTime.now());
        znpjevaluateBasisInformationQuarter.setUpdateBy(entity.getCreateUid());
        znpjevaluateBasisInformationQuarter.setUpdateTime(DateTime.now());
        return znpjevaluateBasisInformationQuarter;
    }

    private void generate(ZNPJEvaluateBasisInformationQuarter znpjEvaluateBasisInformationQuarter) {
        if (znpjEvaluateBasisInformationQuarter != null){
            znpjEvaluateBasisInformationQuarterMapper.insert(znpjEvaluateBasisInformationQuarter);
            if (znpjEvaluateBasisInformationQuarter.getType().equals("01")){
                SysFile sysFile = getSysFileBasisInformation(znpjEvaluateBasisInformationQuarter);
                sysFileMapper.insert(sysFile);
            }else if (znpjEvaluateBasisInformationQuarter.getType().equals("02")){
                SysFile sysFile = getSysFileBasisInformationQuarter(znpjEvaluateBasisInformationQuarter);
                sysFileMapper.insert(sysFile);
            }
        }
    }
    private Date getEndTimeOfQuarter(Integer summaryYear, Integer summaryQuarter) {
        YearMonth endMonth = getEndMonthOfQuarter(summaryYear, summaryQuarter);
        log.info("endMonth{}", endMonth);
        Calendar calendar = Calendar.getInstance();
        calendar.set(endMonth.getYear(),endMonth.getMonthValue() - 1,30);
        return calendar.getTime();
    }

    private Date getStartTimeOfQuarter(Integer summaryYear, Integer summaryQuarter) {
        YearMonth startMonth = getStartMonthOfQuarter(summaryYear, summaryQuarter);
        log.info("startMonth{}", startMonth);
        Calendar calendar = Calendar.getInstance();
        calendar.set(startMonth.getYear(),startMonth.getMonthValue() - 1,1);
        return calendar.getTime();
    }
    /**
     * 根据季度获取开始月份
     *
     * @param quarter 季度编号（1-4）
     * @return 开始月份的 YearMonth 对象
     */
    public static YearMonth getStartMonthOfQuarter(int year, int quarter) {
        Month startMonth = Month.of((quarter - 1) * 3 + 1);
        return YearMonth.of(year, startMonth);
    }

    /**
     * 根据季度获取结束月份
     *
     * @param quarter 季度编号（1-4）
     * @return 结束月份的 YearMonth 对象
     */
    public static YearMonth getEndMonthOfQuarter(int year,int quarter) {
        Month endMonth = Month.of(quarter * 3);
        return YearMonth.of(year, endMonth);
    }

    public static int getCurrentQuarter(int month) {
        return (month - 1) / 3 + 1;
    }
    private void setEffective(ZNPJEvaluateBasisInformationQuarter evaluateBasisInformation) {
        if (evaluateBasisInformation.getEffectiveStartTime() != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(evaluateBasisInformation.getEffectiveStartTime());
            evaluateBasisInformation.setEffectiveStartYear(calendar.get(Calendar.YEAR));
            evaluateBasisInformation.setEffectiveStartQuarter(getCurrentQuarter(calendar.get(Calendar.MONTH)+1));
        }
        if (evaluateBasisInformation.getEffectiveEndTime() != null){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(evaluateBasisInformation.getEffectiveEndTime());
            evaluateBasisInformation.setEffectiveEndYear(calendar.get(Calendar.YEAR));
            evaluateBasisInformation.setEffectiveEndQuarter(getCurrentQuarter(calendar.get(Calendar.MONTH)+1));
        }
    }
    private static SysFile getSysFileBasisInformation(ZNPJEvaluateBasisInformationQuarter basisInformationQuarter) {
        SysFile sysFile = new SysFile();
        sysFile.setFileid(basisInformationQuarter.getId());
        sysFile.settCode("IndicatorInputs");
        sysFile.settId("a91c994c0996436bafc87b03d6ba6152");
        sysFile.setFilecode("IndicatorInputs");
        sysFile.setFiletitle("合同季度加扣分");
        sysFile.setFileproject(basisInformationQuarter.getProjectId());
        sysFile.setFilecreateuser(basisInformationQuarter.getCreateBy());
        sysFile.setFilecreateusername(basisInformationQuarter.getCreateName());
        sysFile.setFileflowstatus(ApprovalStatusEnum.Already_Over.getIndex().toString());
        return sysFile;
    }
    private static SysFile getSysFileBasisInformationQuarter(ZNPJEvaluateBasisInformationQuarter basisInformationQuarter) {
        SysFile sysFile = new SysFile();
        sysFile.setFileid(basisInformationQuarter.getId());
        sysFile.settCode("OtherFunctions");
        sysFile.settId("895f26d0bead4b9c83c76cd9129bebba");
        sysFile.setFilecode("OtherFunctions");
        sysFile.setFiletitle("季度履约加减分");
        sysFile.setFileproject(basisInformationQuarter.getProjectId());
        sysFile.setFilecreateuser(basisInformationQuarter.getCreateBy());
        sysFile.setFilecreateusername(basisInformationQuarter.getCreateName());
        sysFile.setFileflowstatus(ApprovalStatusEnum.Already_Over.getIndex().toString());
        return sysFile;
    }

}