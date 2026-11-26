package com.jpmsoft.projectProducer.service.project.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUnit;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.util.DateUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.requestAndResponse.Project.ProjectAcceptanceSettlementModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.easyexcel.EasyExcelData;
import com.jpmsoft.projectProducer.mapper.other.SysCompanyMapper;
import com.jpmsoft.projectProducer.mapper.other.SysDictionaryMapper;
import com.jpmsoft.projectProducer.mapper.other.SysFileMapper;
import com.jpmsoft.projectProducer.mapper.project.ProjectAcceptanceSettlementMapper;
import com.jpmsoft.projectProducer.pojo.dto.project.ProjectAcceptanceSettlementDto;
import com.jpmsoft.projectProducer.pojo.entity.other.SysCompany;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDictionary;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.project.ProjectAcceptanceSettlement;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.project.ProjectAcceptanceSettlementService;
import com.jpmsoft.projectProducer.utils.IdWorker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

@Slf4j
@Service
public class ProjectAcceptanceSettlementServiceImpl extends ServiceImpl<ProjectAcceptanceSettlementMapper, ProjectAcceptanceSettlement> implements ProjectAcceptanceSettlementService {
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Autowired
    private SysFileMapper sysFileMapper;
    @Autowired
    private SysCompanyMapper sysCompanyMapper;
    @Autowired
    private SysDictionaryMapper sysDictionaryMapper;
    @Override
    public BaseResponse saveOrUpdateProjectAcceptanceSettlement(ProjectAcceptanceSettlementModel request) {
        String primaryId = request.getId();
        ProjectAcceptanceSettlement entity ;
        if (primaryId == null || primaryId.isEmpty()) {
            entity = new ProjectAcceptanceSettlement();
            BeanUtil.copyProperties(request,entity);
            primaryId= GenerateUUID.getUUID();
            entity.setId(primaryId);
            entity.setCreateTime(DateTime.now());
            entity.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(Collections.singletonList(request.getFileRelationId()), entity.getId());
            //创建流程
            if(sysFileService.FileHelper(entity,request,"create",request.getFileRelationId())){

            }else{
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
        } else {
            entity = baseMapper.selectById(primaryId);
            BeanUtil.copyProperties(request,entity);
            baseMapper.updateById(entity);
            sysFileAnnexService.addFormRefreshRelationFile(Collections.singletonList(request.getFileRelationId()), entity.getId());
        }
        return BaseResponse.ok(primaryId);
    }

    @Override
    public BaseResponse getProjectAcceptanceSettlementList(ProjectAcceptanceSettlementModel request, String token) {
        log.info("request:{}", JacksonUtils.toJSONString(request));
        IPage<ProjectAcceptanceSettlementModel> page = new Page<>(request.getPage(), request.getSize());
        QueryMap<ProjectAcceptanceSettlementModel> params = new QueryMap<>(request);
        params.put("projectId").put("projectName").put("projectType")
                .put("contractId").put("contractName").put("constructionUnit").put("projectNameTemp");
        log.info("params:{}",JacksonUtils.toJSONString(params));
        List<ProjectAcceptanceSettlementModel> projectAcceptanceSettlements = baseMapper.getProjectAcceptanceSettlementList(params.getQuery(), page);
        log.info("projectAcceptanceSettlements:{}",JacksonUtils.toJSONString(projectAcceptanceSettlements));
        return BaseResponse.ok(projectAcceptanceSettlements,Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getProjectAcceptanceSettlementDetail(String id) {
        ProjectAcceptanceSettlementModel projectAcceptanceSettlement= baseMapper.getProjectAcceptanceSettlementById(id);
        if(projectAcceptanceSettlement==null){
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        log.info("查询信息：{}",projectAcceptanceSettlement);
        return BaseResponse.ok(projectAcceptanceSettlement);
    }

    @Override
    public BaseResponse approvalData(ProjectAcceptanceSettlementModel request) {
        ProjectAcceptanceSettlement entity ;
        SysFile fileEntity;
        String id =request.getId();
        if (StrUtil.isEmpty(id)) {
            return new BaseResponse("缺少请求参数");
        } else {
            entity = baseMapper.selectById(id);
            if (entity == null)
                return new BaseResponse("信息不存在，请刷新重试！");
            fileEntity = sysFileService.getById(id);
            /**
             * 流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动
             * 5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
             */
            String fileFlowStatus = fileEntity.getFileflowstatus();
            entity.setState(Integer.valueOf(fileFlowStatus));
            entity.setStateTime(new Date());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse calculateProjectAcceptanceSettlement(ProjectAcceptanceSettlementModel request) {
        log.info("calculateBefore:{}",JacksonUtils.toJSONString(request));
        Date today =DateTime.now();
        //拟定最迟竣工验收日期
        Date draftLatestCompletionDate = request.getDraftLatestCompletionDate();
        //实际竣工验收组日期
        Date actualCompletionDate = request.getActualCompletionDate();
        //IF(ISBLANK(实际竣工验收组日期),"否","是")
        if (actualCompletionDate==null){
           request.setIsCompleted(0);
        }else{
           request.setIsCompleted(1);
        }
        //合同约定结算送审时限（天）
        BigDecimal standardSettlementTime = request.getStandardSettlementTime();
        //制度规定结算送审时间（天）
        BigDecimal draftSettlementTime = request.getDraftSettlementTime();
        /**
         * 竣工验收超期（天）=MIN(TODAY(),实际竣工验收组日期)-拟定最迟竣工验收日期
         */
        Date minDate = today;
        if (actualCompletionDate!=null &&minDate.compareTo(actualCompletionDate)>0){
            minDate = actualCompletionDate;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(draftLatestCompletionDate);
        cal.add(Calendar.DATE, 1);
        BigDecimal completionOverdueDays = BigDecimal.valueOf(DateUtil.between(cal.getTime(),minDate
                , DateUnit.DAY,false));
        request.setCompletionOverdueDays(completionOverdueDays);
        /**
         * 拟定最迟结算送审日期 =实际竣工验收组日期+合同约定结算送审时限（天）+制度规定结算送审时间（天）
         */
        Date draftLatestSettlementDate
                = DateUtil.offsetDay(actualCompletionDate,(standardSettlementTime!=null?standardSettlementTime.intValue():0)+(draftSettlementTime!=null?
                draftSettlementTime.intValue():0));
        request.setDraftLatestSettlementDate(draftLatestSettlementDate);
        /**
         * 结算送审超期（天）=MIN(TODAY(),实际结算送审日期)-拟定最迟结算送审日期
         */
        Date minDate2= today;
        Date actualSettlementDate = request.getActualSettlementDate();
        //IF(ISBLANK(实际结算送审日期),"否","是")
        if (actualSettlementDate==null){
            request.setIsSettlementApproved(0);
        }else{
            request.setIsSettlementApproved(1);
        }
        if (actualSettlementDate!=null &&minDate2.compareTo(actualSettlementDate)>0){
            minDate2 = actualSettlementDate;
        }
        BigDecimal settlementOverdueDays =BigDecimal.valueOf(DateUtil.between(draftLatestSettlementDate,minDate2, DateUnit.DAY,false));
        request.setSettlementOverdueDays(settlementOverdueDays);
        /**
         * 拟定最迟结算审定日期 =MAX(拟定最迟结算送审日期+制度规定结算审计时间（天）),(实际结算送审日期+制度规定结算审计时间（天）))
         */
        BigDecimal standardAuditTime = request.getStandardAuditTime();
        Date date1 = DateUtil.offsetDay(draftLatestSettlementDate,standardAuditTime!=null?standardAuditTime.intValue():0);
        if (actualSettlementDate!=null){
            Date date2 = DateUtil.offsetDay(actualSettlementDate,standardAuditTime!=null?standardAuditTime.intValue():0);
            request.setDraftLatestAuditDate(date1.compareTo(date2)>0?date1:date2);
        }else{
            request.setDraftLatestAuditDate(date1);
        }
        /**
         * 审计超期（天）=MIN(TODAY(),实际审定日期)-拟定最迟结算审定日期
         */
        Date minDate3= today;
        Date actualAuditDate = request.getActualAuditDate();
        //IF(ISBLANK(实际审定日期),"否","是")
        if (actualAuditDate==null){
            request.setIsAudited(0);
        }else{
            request.setIsAudited(1);
        }
        if (actualAuditDate!=null &&minDate3.compareTo(actualAuditDate)>0){
            minDate3 = actualAuditDate;
        }
        BigDecimal auditOverdueDays =BigDecimal.valueOf(DateUtil.between(request.getDraftLatestAuditDate(),minDate3,DateUnit.DAY,false));
        request.setAuditOverdueDays(auditOverdueDays);
        calculateScore(request, completionOverdueDays);
        log.info("calculateAfter:{}",JacksonUtils.toJSONString(request));
        return BaseResponse.ok(request);
    }

    @Override
    public BaseResponse calculateProjectAcceptanceSettlementScore(ProjectAcceptanceSettlementModel request) {
        log.info("calculateBefore:{}",JacksonUtils.toJSONString(request));
        BigDecimal completionOverdueDays = request.getCompletionOverdueDays();
        calculateScore(request, completionOverdueDays);
        log.info("calculateAfter:{}",JacksonUtils.toJSONString(request));
        return BaseResponse.ok(request);
    }

    @Override
    public BaseResponse importProjectAcceptanceSettlement(MultipartFile file, LogUserInfoVo userInfoByToken) {
        List<ProjectAcceptanceSettlementDto> datas
                = new EasyExcelData<ProjectAcceptanceSettlementDto>().getExcelData(file,ProjectAcceptanceSettlementDto.class,1);
        List<SysCompany> sysCompanys = sysCompanyMapper.selectList(new QueryWrapper<SysCompany>());
        QueryWrapper<SysDictionary> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("d_parentid","8c0d6c7c-8ae7-47a8-8513-f3c554a1a13a").eq("d_status","1");
        List<SysDictionary> sysDictionaries = sysDictionaryMapper.selectList(queryWrapper);
        List<ProjectAcceptanceSettlement> list =new ArrayList<>();
        List<SysFile> fileList =new ArrayList<>();
        if (CollectionUtil.isNotEmpty(datas)) {
            log.info("数据数量：{}",datas.size());
            log.info("数据：{}",JacksonUtils.toJSONString(datas));
            for (ProjectAcceptanceSettlementDto dto:datas){
                ProjectAcceptanceSettlement model = new ProjectAcceptanceSettlement();
                String msg = copyDto(dto,model,sysCompanys,sysDictionaries);
                log.info("msg：{}",msg);
                if (StrUtil.isNotBlank(msg)){
                    return BaseResponse.fail(msg);
                }
                model.setId(IdWorker.getGenerateID());
                model.setCreateUid(userInfoByToken.getId());
                model.setCreateUname(userInfoByToken.getName());
                model.setCreateTime(DateTime.now());
                model.setState(ApprovalStatusEnum.Already_Over.getIndex());
                model.setStateTime(DateTime.now());
                List<ProjectAcceptanceSettlement> projectAcceptanceSettlements =
                        baseMapper.selectList(new QueryWrapper<ProjectAcceptanceSettlement>()
                        .eq("project_name_temp",model.getProjectNameTemp())
                        .eq("construction_unit",model.getConstructionUnit()));
                if (CollectionUtil.isEmpty(projectAcceptanceSettlements)){
                    log.info("新增数据：{}",JacksonUtils.toJSONString(model));
                    //baseMapper.insert(model);
                    list.add(model);
                    SysFile sysFile = getSysFile(model);
                    fileList.add(sysFile);
                    //sysFileMapper.insert(sysFile);
                }
            }
        }
        if (CollectionUtil.isNotEmpty(list)){
            saveBatch(list);
        }
        if  (CollectionUtil.isNotEmpty(fileList)){
            sysFileService.saveBatch(fileList);
        }
        return BaseResponse.ok();
    }

    private static String copyDto(ProjectAcceptanceSettlementDto dto, ProjectAcceptanceSettlement model,List<SysCompany> sysCompanys,List<SysDictionary> sysDictionaries) {
        StringBuilder errorMsg = new StringBuilder();
        if (StrUtil.isEmpty(dto.getProjectType())){
            String msg = "【项目验收及结算审计台账】导入失败，请检查第"+dto.getNum()+"行【合同类别】"
                    +dto.getProjectType()+"是否正确后重新导入！";
            errorMsg.append(msg);
        }
        SysDictionary sysDictionary = sysDictionaries.stream()
                .filter(item->item.getdName().equals(dto.getProjectType())).findFirst().orElse(null);
        if (sysDictionary==null){
            model.setProjectType(null);
        }else {
            model.setProjectType(sysDictionary.getdCode());
        }
        SysCompany sysCompany = sysCompanys.stream()
                .filter(item->item.getcName().equals(dto.getConstructionUnitName())).findFirst().orElse(null);
        if (sysCompany!=null){
            model.setConstructionUnit(sysCompany.getcId());
        }else {
            model.setConstructionUnit(null);
        }
        if(StrUtil.isEmpty(dto.getProjectNameTemp())){
            String msg ="【项目验收及结算审计台账】导入失败，请检查第"+dto.getNum()
                    +"行【项目/合同名称】"+dto.getProjectNameTemp()+"是否正确后重新导入！";
            errorMsg.append(msg);
        }
        model.setProjectNameTemp(dto.getProjectNameTemp());
        //开工日期
        if (StrUtil.isNotEmpty(dto.getStartDate())){
            try {
                model.setStartDate(DateUtils.parseDate(dto.getStartDate(),"yyyy/MM/dd"));
            } catch (ParseException e) {
                model.setStartDate(null);
            }
        }else {
            model.setStartDate(null);
        }
        //初步验收日期
        if (StrUtil.isNotEmpty(dto.getAcceptanceDate())){
            try {
                model.setAcceptanceDate(DateUtils.parseDate(dto.getAcceptanceDate(),"yyyy/MM/dd"));
            } catch (ParseException e) {
                model.setAcceptanceDate(null);
            }
        }else {
            model.setAcceptanceDate(null);
        }
        //制度规定竣工验收时限（天）
        if (StrUtil.isNotEmpty(dto.getStandardCompletionTime())){
            try {
                double standardCompletionTime = Double.parseDouble(dto.getStandardCompletionTime());
                model.setStandardCompletionTime(BigDecimal.valueOf(standardCompletionTime));
            }catch (NumberFormatException e){
                model.setStandardCompletionTime(null);
            }
        }else {
            model.setStandardCompletionTime(null);
        }
        //拟定最迟竣工验收日期
        if (StrUtil.isNotEmpty(dto.getDraftLatestCompletionDate())){
            try {
                model.setDraftLatestCompletionDate(DateUtils.parseDate(dto.getDraftLatestCompletionDate(),"yyyy/MM/dd"));
            }catch (ParseException e){
                model.setDraftLatestCompletionDate(null);
            }
        }else {
            model.setDraftLatestCompletionDate(null);
        }
        //实际竣工验收组日期
        if (StrUtil.isNotEmpty(dto.getActualCompletionDate())){
            try {
                model.setActualCompletionDate(DateUtils.parseDate(dto.getActualCompletionDate(),"yyyy/MM/dd"));
            }catch (ParseException e){
                model.setActualCompletionDate(null);
            }
        }else {
            model.setActualCompletionDate(null);
        }
        //竣工验收超期（天）
        if (StrUtil.isNotEmpty(dto.getCompletionOverdueDays())){
            try {
                double completionOverdueDays = Double.parseDouble(dto.getCompletionOverdueDays());
                model.setCompletionOverdueDays(BigDecimal.valueOf(completionOverdueDays));
            }catch (NumberFormatException e){
                model.setCompletionOverdueDays(null);
            }
        }else {
            model.setCompletionOverdueDays(null);
        }
        if("是".equals(dto.getIsCompleted())){
            model.setIsCompleted(1);
        }else {
            model.setIsCompleted(0);
        }
        //合同约定结算送审时限（天）
        if (StrUtil.isNotEmpty(dto.getStandardSettlementTime())){
            try {
                double standardSettlementTime = Double.parseDouble(dto.getStandardSettlementTime());
                model.setStandardSettlementTime(BigDecimal.valueOf(standardSettlementTime));
            }catch (NumberFormatException e){
                model.setStandardSettlementTime(null);
            }
        }else {
            model.setStandardSettlementTime(null);
        }
        //制度规定结算送审时间（天）
        if (StrUtil.isNotEmpty(dto.getDraftSettlementTime())){
            try {
                double draftSettlementTime = Double.parseDouble(dto.getDraftSettlementTime());
                model.setDraftSettlementTime(BigDecimal.valueOf(draftSettlementTime));
            }catch (NumberFormatException e){
                model.setDraftSettlementTime(null);
            }
        }else {
            model.setDraftSettlementTime(null);
        }
        //拟定最迟结算送审日期
        if (StrUtil.isNotEmpty(dto.getDraftLatestSettlementDate())){
            try {
                model.setDraftLatestSettlementDate(DateUtils.parseDate(dto.getDraftLatestSettlementDate(),"yyyy/MM/dd"));
            }catch (ParseException e){
                model.setDraftLatestSettlementDate(null);
            }
        }else {
            model.setDraftLatestSettlementDate(null);
        }
        //实际结算送审日期
        if (StrUtil.isNotEmpty(dto.getActualSettlementDate())){
            try {
                model.setActualSettlementDate(DateUtils.parseDate(dto.getActualSettlementDate(),"yyyy/MM/dd"));
            }catch (ParseException e){
                model.setActualSettlementDate(null);
            }
        }else {
            model.setActualSettlementDate(null);
        }
        //结算送审超期（天）
        if (StrUtil.isNotEmpty(dto.getSettlementOverdueDays())){
            try {
                double settlementOverdueDays = Double.parseDouble(dto.getSettlementOverdueDays());
                model.setSettlementOverdueDays(BigDecimal.valueOf(settlementOverdueDays));
            }catch (NumberFormatException e){
                model.setSettlementOverdueDays(null);
            }
        }else {
            model.setSettlementOverdueDays(null);
        }
        //是否已结算送审
        if("是".equals(dto.getIsSettlementApproved())){
            model.setIsSettlementApproved(1);
        }else {
            model.setIsSettlementApproved(0);
        }
        //制度规定结算审计时间（天）
        if (StrUtil.isNotEmpty(dto.getStandardAuditTime())){
            try {
                double standardAuditTime = Double.parseDouble(dto.getStandardAuditTime());
                model.setStandardAuditTime(BigDecimal.valueOf(standardAuditTime));
            }catch (NumberFormatException e){
                model.setStandardAuditTime(null);
            }
        }else {
            model.setStandardAuditTime(null);
        }
        //拟定最迟结算审定日期
        if (StrUtil.isNotEmpty(dto.getDraftLatestAuditDate())){
            try {
                model.setDraftLatestAuditDate(DateUtils.parseDate(dto.getDraftLatestAuditDate(),"yyyy/MM/dd"));
            }catch (ParseException e){
                model.setDraftLatestAuditDate(null);
            }
        }else {
            model.setDraftLatestAuditDate(null);
        }
        //实际审定日期
        if (StrUtil.isNotEmpty(dto.getActualAuditDate())){
            try {
                model.setActualAuditDate(DateUtils.parseDate(dto.getActualAuditDate(),"yyyy/MM/dd"));
            }catch (ParseException e){
                model.setActualAuditDate(null);
            }
        }else {
            model.setActualAuditDate(null);
        }
        //审计超期（天）
        if (StrUtil.isNotEmpty(dto.getAuditOverdueDays())){
            try {
                double auditOverdueDays = Double.parseDouble(dto.getAuditOverdueDays());
                model.setAuditOverdueDays(BigDecimal.valueOf(auditOverdueDays));
            }catch (NumberFormatException e){
                model.setAuditOverdueDays(null);
            }
        }else {
            model.setAuditOverdueDays(null);
        }
        //是否已结算审定
        if("是".equals(dto.getIsAudited())){
            model.setIsAudited(1);
        }else {
            model.setIsAudited(0);
        }
        //当前进展
        model.setCurrentProgress(dto.getCurrentProgress());
        //竣工超期扣分
        if (StrUtil.isNotEmpty(dto.getCompletionOverdueDeductScore())){
            try {
                double parseDouble = Double.parseDouble(dto.getCompletionOverdueDeductScore());
                BigDecimal completionOverdueDeductScore = BigDecimal.valueOf(parseDouble);
                model.setCompletionOverdueDeductScore(completionOverdueDeductScore);
            }catch (NumberFormatException e){
                model.setCompletionOverdueDeductScore(BigDecimal.ZERO);
            }
        }else {
            model.setCompletionOverdueDeductScore(BigDecimal.ZERO);
        }
        //结算送审超期扣分
        if (StrUtil.isNotEmpty(dto.getSettlementOverdueDeductScore())){
            try {
                double parseDouble = Double.parseDouble(dto.getSettlementOverdueDeductScore());
                BigDecimal settlementOverdueDeductScore = BigDecimal.valueOf(parseDouble);
                model.setSettlementOverdueDeductScore(settlementOverdueDeductScore);
            }catch (NumberFormatException e){
                model.setSettlementOverdueDeductScore(BigDecimal.ZERO);
            }
        }else {
            model.setSettlementOverdueDeductScore(BigDecimal.ZERO);
        }
        //结算审计超期扣分
        if (StrUtil.isNotEmpty(dto.getAuditOverdueDeductScore())){
            try {
                double parseDouble = Double.parseDouble(dto.getAuditOverdueDeductScore());
                BigDecimal auditOverdueDeductScore = BigDecimal.valueOf(parseDouble);
                model.setAuditOverdueDeductScore(auditOverdueDeductScore);
            }catch (NumberFormatException e){
                model.setAuditOverdueDeductScore(BigDecimal.ZERO);
            }
        }else {
            model.setAuditOverdueDeductScore(BigDecimal.ZERO);
        }
        return errorMsg.toString();
    }

    private static void copy(ProjectAcceptanceSettlement projectAcceptanceSettlement, ProjectAcceptanceSettlement model) {
        projectAcceptanceSettlement.setConstructionUnit(model.getConstructionUnit());
        projectAcceptanceSettlement.setProjectType(model.getProjectType());
        //开工日期
        projectAcceptanceSettlement.setStartDate(model.getStartDate());
        //初步验收日期
        projectAcceptanceSettlement.setAcceptanceDate(model.getAcceptanceDate());
        //制度规定竣工验收时限（天）
        projectAcceptanceSettlement.setStandardCompletionTime(model.getStandardCompletionTime());
        //拟定最迟竣工验收日期
        projectAcceptanceSettlement.setDraftLatestCompletionDate(model.getDraftLatestCompletionDate());
        //实际竣工验收组日期
        projectAcceptanceSettlement.setActualCompletionDate(model.getActualCompletionDate());
        //竣工验收超期（天）
        projectAcceptanceSettlement.setCompletionOverdueDays(model.getCompletionOverdueDays());
        //是否已竣工验收
        projectAcceptanceSettlement.setIsCompleted(model.getIsCompleted());
        //合同约定结算送审时限（天）
        projectAcceptanceSettlement.setStandardSettlementTime(model.getStandardSettlementTime());
        //制度规定结算送审时间（天）
        projectAcceptanceSettlement.setDraftSettlementTime(model.getDraftSettlementTime());
        //拟定最迟结算送审日期
        projectAcceptanceSettlement.setDraftLatestSettlementDate(model.getDraftLatestSettlementDate());
        //实际结算送审日期
        projectAcceptanceSettlement.setActualSettlementDate(model.getActualSettlementDate());
        //结算送审超期（天）
        projectAcceptanceSettlement.setSettlementOverdueDays(model.getSettlementOverdueDays());
        //是否已结算送审
        projectAcceptanceSettlement.setIsSettlementApproved(model.getIsSettlementApproved());
        //制度规定结算审计时间（天）
        projectAcceptanceSettlement.setStandardAuditTime(model.getStandardAuditTime());
        //拟定最迟结算审定日期
        projectAcceptanceSettlement.setDraftLatestAuditDate(model.getDraftLatestAuditDate());
        //实际审定日期
        projectAcceptanceSettlement.setActualAuditDate(model.getActualAuditDate());
        //审计超期（天）
        projectAcceptanceSettlement.setAuditOverdueDays(model.getAuditOverdueDays());
        //是否已结算审定
        projectAcceptanceSettlement.setIsAudited(model.getIsAudited());
        projectAcceptanceSettlement.setCurrentProgress(model.getCurrentProgress());
        projectAcceptanceSettlement.setCompletionOverdueDeductScore(model.getCompletionOverdueDeductScore());
        projectAcceptanceSettlement.setSettlementOverdueDeductScore(model.getSettlementOverdueDeductScore());
        projectAcceptanceSettlement.setAuditOverdueDeductScore(model.getAuditOverdueDeductScore());
    }

    private SysFile getSysFile(ProjectAcceptanceSettlement model) {
        SysFile sysFile = new SysFile();
        sysFile.setFileid(model.getId());
        sysFile.settCode("ProjectAuditRecord");
        sysFile.settId("6d8a7b7e382c48c0ad8df9d588f5897c");
        sysFile.setFilecode("ProjectAuditRecord");
        sysFile.setFiletitle("项目验收及结算审计台账");
        sysFile.setFileproject(model.getProjectId());
        sysFile.setFilecreateuser(model.getCreateUid());
        sysFile.setFilecreateusername(model.getCreateUname());
        sysFile.setFileflowstatus(ApprovalStatusEnum.Already_Over.getIndex().toString());
        return sysFile;
    }

    private static void calculateScore(ProjectAcceptanceSettlementModel request, BigDecimal completionOverdueDays) {
        /**
         * 竣工超期扣分
         * IF(是否已竣工验收="是",0,IF(AND(制度规定竣工验收时限（天）<竣工验收超期（天）<=2*制度规定竣工验收时限（天）),
         * -1,IF(AND(2*制度规定竣工验收时限（天）<竣工验收超期（天）<=3*制度规定竣工验收时限（天）),-2,IF(3*制度规定竣工验收时限（天）<竣工验收超期（天）,-3,0))))
         */
        BigDecimal completionOverdueDeductScore = BigDecimal.ZERO;
        if (request.getCompletionOverdueDays().compareTo(completionOverdueDays)==1 && request.getCompletionOverdueDays().compareTo(completionOverdueDays.multiply(new BigDecimal(2)))==-1){
            completionOverdueDeductScore = BigDecimal.ONE.negate();
        }else if (request.getCompletionOverdueDays().compareTo(completionOverdueDays.multiply(new BigDecimal(2)))==1 && request.getCompletionOverdueDays().compareTo(completionOverdueDays.multiply(new BigDecimal(3)))==-1){
            completionOverdueDeductScore = BigDecimal.valueOf(2).negate();
        }else if (request.getCompletionOverdueDays().compareTo(completionOverdueDays.multiply(new BigDecimal(3)))==1){
            completionOverdueDeductScore = BigDecimal.valueOf(3).negate();
        }
        request.setCompletionOverdueDeductScore(completionOverdueDeductScore);
        /**
         * 结算送审超期扣分  IF(AND(是否已竣工验收="是",是否已结算送审="是"),0,
         * IF(AND(是否已竣工验收="是",是否已结算送审="否"),
         * 合同约定结算送审时限（天）+制度规定结算送审时间（天）<结算送审超期（天）<=合同约定结算送审时限（天）+2*制度规定结算送审时间（天） -1
         * 合同约定结算送审时限（天）+2*制度规定结算送审时间（天）<结算送审超期（天）<=合同约定结算送审时限（天）+3*制度规定结算送审时间（天 ） -2
         * 合同约定结算送审时限（天）+3*制度规定结算送审时间（天）<结算送审超期（天）-3
         *
         */
        BigDecimal settlementOverdueDeductScore = BigDecimal.ZERO;
        if (request.getIsCompleted()==1 && request.getIsSettlementApproved()==0){
            if (request.getStandardSettlementTime().add(request.getDraftSettlementTime()).compareTo(request.getSettlementOverdueDays()) < 0
                    && request.getStandardSettlementTime().add(request.getDraftSettlementTime().multiply(new BigDecimal(2))).compareTo(request.getSettlementOverdueDays()) >= 0){
                settlementOverdueDeductScore = BigDecimal.ONE.negate();
            }else if (request.getStandardSettlementTime().add(request.getDraftSettlementTime().multiply(new BigDecimal(2))).compareTo(request.getSettlementOverdueDays()) < 0
                    && request.getStandardSettlementTime().add(request.getDraftSettlementTime().multiply(new BigDecimal(3))).compareTo(request.getSettlementOverdueDays()) >= 0){
                settlementOverdueDeductScore = BigDecimal.valueOf(2).negate();
            }else if (request.getStandardSettlementTime().add(request.getDraftSettlementTime().multiply(new BigDecimal(3))).compareTo(request.getSettlementOverdueDays()) < 0){
                settlementOverdueDeductScore = BigDecimal.valueOf(3).negate();
            }
        }
        request.setSettlementOverdueDeductScore(settlementOverdueDeductScore);
        /**
         * 结算审计超期扣分
         * K2="是",Q2="是",V2="是",0
         * K2="是",Q2="是",V2="否",
         * 制度规定结算审计时间（天）<审计超期（天）<=2*制度规定结算审计时间（天）,-1,
         * 2*制度规定结算审计时间（天）<审计超期（天）<=3*制度规定结算审计时间（天）,-2
         * 3*制度规定结算审计时间（天）<审计超期（天）,-3
         */
        BigDecimal auditOverdueDeductScore = BigDecimal.ZERO;
        if (request.getIsCompleted()==1 && request.getIsSettlementApproved()==1 && request.getIsAudited()==0){
            if (request.getStandardAuditTime().compareTo(request.getAuditOverdueDays()) < 0
                    && request.getStandardAuditTime().multiply(new BigDecimal(2)).compareTo(request.getAuditOverdueDays()) >= 0){
                auditOverdueDeductScore = BigDecimal.ONE.negate();
            }else if (request.getStandardAuditTime().multiply(new BigDecimal(2)).compareTo(request.getAuditOverdueDays()) < 0
                    && request.getStandardAuditTime().multiply(new BigDecimal(3)).compareTo(request.getAuditOverdueDays()) >= 0){
                auditOverdueDeductScore = BigDecimal.valueOf(2).negate();
            }else if (request.getStandardAuditTime().multiply(new BigDecimal(3)).compareTo(request.getAuditOverdueDays()) < 0){
                auditOverdueDeductScore = BigDecimal.valueOf(3).negate();
            }
        }
        request.setAuditOverdueDeductScore(auditOverdueDeductScore);
    }
}
