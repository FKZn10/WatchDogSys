package com.jpmsoft.projectProducer.service.investment.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.FinalAccountingReportMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.FinalAccountingReport;
import com.jpmsoft.projectProducer.pojo.entity.investment.FinalAccountingReportDetail;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvestmentBudgetBreakdown;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.request.investment.FinalAccountingReporRequest;
import com.jpmsoft.projectProducer.pojo.vo.investment.FinalAccountingReportDetailVo;
import com.jpmsoft.projectProducer.pojo.vo.investment.FinalAccountingReportVo;
import com.jpmsoft.projectProducer.service.investment.FinalAccountingReportDetailService;
import com.jpmsoft.projectProducer.service.investment.FinalAccountingReportService;
import com.jpmsoft.projectProducer.service.investment.IInvestmentBudgetBreakdownService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FinalAccountingReportServiceImpl extends ServiceImpl<FinalAccountingReportMapper, FinalAccountingReport> implements FinalAccountingReportService {

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private ISysFileService sysFileService;

    @Resource
    private IInvestmentBudgetBreakdownService investmentBudgetBreakdownService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;



    @Autowired
    private FinalAccountingReportDetailService finalAccountingReportDetailService;
    @Override
    public BaseResponse getFinalAccountsList(FinalAccountingReportVo request, String uId) {
        Page<FinalAccountingReport> page = new Page<>(request.getPage(),request.getSize());
//        LambdaQueryWrapper<FinalAccountingReport> queryWrapper = new LambdaQueryWrapper<>();
//        if(ValidateUtil.isNotEmpty(request.getPId()))  queryWrapper.eq(FinalAccountingReport::getPId,request.getPId());
//        if(ValidateUtil.isNotEmpty(request.getPName()))  queryWrapper.like(FinalAccountingReport::getPName,request.getPName());
//        if(ValidateUtil.isNotEmpty(request.getCreateUid()))  queryWrapper.eq(FinalAccountingReport::getCreateUid,request.getCreateUid());
//        if(ValidateUtil.isNotEmpty(request.getCreateUname()))  queryWrapper.like(FinalAccountingReport::getCreateUname,request.getCreateUname());
//        if(ValidateUtil.isNotNull(request.getAmountSubmitted()))  queryWrapper.eq(FinalAccountingReport::getAmountSubmitted,request.getAmountSubmitted());
//        if(ValidateUtil.isNotNull(request.getApprovedBudgetAmount()))  queryWrapper.eq(FinalAccountingReport::getApprovedBudgetAmount,request.getApprovedBudgetAmount());
         IPage<FinalAccountingReportVo> data = baseMapper.getFinalAccountsList(page, request);
        return BaseResponse.ok(data.getRecords(),(int)data.getTotal());
    }

    @Override
    public BaseResponse createOrUpdateFinalAccounts(FinalAccountingReportVo request, String uId) {
        String id  = request.getId();
        if(ValidateUtil.isEmpty(id)){
            id = GenerateUUID.getUUID();
            FinalAccountingReport report = new FinalAccountingReport();
            BeanUtils.copyProperties(request,report);
            report.setId(id);
            report.setCreateTime(new Date());
             SysUser user = iSysUserService.getById(uId);
             report.setCreateUid(user.getUId());
             report.setCreateUname(user.getUName());
            if(!CollectionUtils.isEmpty(request.getDetailList())){
                 BigDecimal fardAmountSubmitted = request.getDetailList().stream().filter(e -> ValidateUtil.isNotNull(e.getFardAmountSubmitted())).map(e -> e.getFardAmountSubmitted()).reduce(BigDecimal.ZERO, BigDecimal::add);
                report.setAmountSubmitted(fardAmountSubmitted);
                BigDecimal fardApprovedBudgetAmount = request.getDetailList().stream().filter(e -> ValidateUtil.isNotNull(e.getFardApprovedBudgetAmount())).map(e -> e.getFardApprovedBudgetAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                report.setApprovedBudgetAmount(fardApprovedBudgetAmount);
            }else{
                report.setAmountSubmitted(BigDecimal.ZERO);
                report.setApprovedBudgetAmount(BigDecimal.ZERO);
            }
            report.setState(ApprovalStatusEnum.Not_Started.getIndex());
            report.setStatus(1);
            baseMapper.insert(report);
            finalAccountingReportDetailService.saveReportDetail(request.getDetailList(),report.getId(),0);
            sysFileService.saveFile(report.getId(), request.gettId(), report.getState() + "", request.getCreateUid(), request.getCreateUname()
                    , report.getCreateTime(), request.getPId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), report.getId());
        }else{
             FinalAccountingReport finala = baseMapper.selectById(request.getId());
            if(ValidateUtil.isNull(finala)) return BaseResponse.fail("未查询到该数据");
            if(!CollectionUtils.isEmpty(request.getDetailList())){
                BigDecimal fardAmountSubmitted = request.getDetailList().stream().filter(e -> ValidateUtil.isNotNull(e.getFardAmountSubmitted())).map(e -> e.getFardAmountSubmitted()).reduce(BigDecimal.ZERO, BigDecimal::add);
                request.setAmountSubmitted(fardAmountSubmitted);
                BigDecimal fardApprovedBudgetAmount = request.getDetailList().stream().filter(e -> ValidateUtil.isNotNull(e.getFardApprovedBudgetAmount())).map(e -> e.getFardApprovedBudgetAmount()).reduce(BigDecimal.ZERO, BigDecimal::add);
                request.setApprovedBudgetAmount(fardApprovedBudgetAmount);
            }else{
                request.setAmountSubmitted(BigDecimal.ZERO);
                request.setApprovedBudgetAmount(BigDecimal.ZERO);
            }
            FinalAccountingReport report = new FinalAccountingReport();
            BeanUtils.copyProperties(request,report);
            SysFile fileEntity;
            fileEntity = sysFileService.getById(id);
            String fileFlowStatus = fileEntity.getFileflowstatus();
            report.setState(Integer.valueOf(fileFlowStatus));
            baseMapper.updateById(report);
            finalAccountingReportDetailService.saveReportDetail(request.getDetailList(),report.getId(),1);
            sysFileService.saveFile(report.getId(), request.gettId(), report.getState() + "", report.getCreateUid(), report.getCreateUname()
                    , report.getCreateTime(), request.getPId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), report.getId());
        }
        return BaseResponse.ok(id);
    }

    @Override
    public BaseResponse getFinalAccountsById(String id) {
         FinalAccountingReport report = baseMapper.selectById(id);
         if(ValidateUtil.isNull(report)) return BaseResponse.fail("未查询到该数据");
        FinalAccountingReportVo vo = new FinalAccountingReportVo();
        BeanUtils.copyProperties(report,vo);
       List<FinalAccountingReportDetail> reportDetailList  =  finalAccountingReportDetailService.getReportDetailId(id);
       List<FinalAccountingReportDetailVo> detailVoList =  reportDetailList.stream().map(e->{
           FinalAccountingReportDetailVo detailVo = new FinalAccountingReportDetailVo();
           BeanUtils.copyProperties(e,detailVo);
           return detailVo;
        }).collect(Collectors.toList());
       if(!CollectionUtils.isEmpty(detailVoList)){
           vo.setDetailList(detailVoList);
       }
        return BaseResponse.ok(vo);
    }

    @Override
    public BaseResponse getInvestmentBudgetByPId(String pId) {
        InvestmentBudgetBreakdown breakdown =  investmentBudgetBreakdownService.getInvestmentBudgetBreakdownByPIdData(pId);
        if(ValidateUtil.isNull(breakdown))return  BaseResponse.fail("未查询到该项目的分解数据");
        return BaseResponse.ok(investmentBudgetBreakdownService.getInvestmentBudgetBreakdownById(breakdown.getId()));
    }

    @Override
    public BaseResponse deleteFinalAccountsById(String id) {
        baseMapper.deleteById(id);
        finalAccountingReportDetailService.deleteReportId(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse queryFinalAccountsClassInfo(FinalAccountingReportVo request) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(FinalAccountingReporRequest.class.getName());
            for (ClassInfoAnnotation classInfoAnnotation : classInfoAnnotationList) {
                if(!classInfoAnnotation.getType().equals("date")){
                    if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())){
                        if (classInfoAnnotation.getValue().equals("pNameList")){
                            List<Object> dictionaryValues =baseMapper.getprojectValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(1);
                        }else if (classInfoAnnotation.getValue().equals("stateList")){
                            List<Object> dictionaryValues =baseMapper.getValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(2);
                        }else if (classInfoAnnotation.getValue().equals("approvedBudgetAmountList")){
                            List<Object> dictionaryValues =baseMapper.getValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(3);
                        }else if (classInfoAnnotation.getValue().equals("auditContractMoneyList")){
                            List<Object> dictionaryValues =baseMapper.getValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(4);
                        }else if (classInfoAnnotation.getValue().equals("createUnameList")){
                            List<Object> dictionaryValues =baseMapper.getValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(5);
                        }else if (classInfoAnnotation.getValue().equals("cNameList")){
                            List<Object> dictionaryValues =baseMapper.getValueCnameByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(6);
                        }else{
                            List<Object> dictionaryValues =baseMapper.getValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                            classInfoAnnotation.setNo(7);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("queryFinalAccountsClassInfo error:{}",e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        classInfoAnnotationList=classInfoAnnotationList.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());

        return BaseResponse.ok(classInfoAnnotationList);
    }
}
