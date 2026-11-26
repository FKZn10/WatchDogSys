package com.jpmsoft.projectProducer.service.investment.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.ClassInfoAnnotation;
import com.jpmsoft.common.utils.ClassInfoUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.SettlementAuditMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementAudit;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementAuditChange;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementAuditDetail;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementAuditDetailChange;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.request.investment.SettlementAuditRequest;
import com.jpmsoft.projectProducer.pojo.vo.investment.SettlementAuditDetailVo;
import com.jpmsoft.projectProducer.pojo.vo.investment.SettlementAuditVo;
import com.jpmsoft.projectProducer.service.investment.*;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SettlementAuditServiceImpl extends ServiceImpl<SettlementAuditMapper, SettlementAudit> implements SettlementAuditService {

    @Autowired
    private ISysUserService iSysUserService;

    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;

    @Autowired
    private SettlementAuditDetailService settlementAuditDetailService;

    @Autowired
    private ISettlementFirstAuditService iSettlementFirstAuditService;

    @Autowired
    private SettlementAuditChangeService settlementAuditChangeService;

    @Autowired
    private SettlementAuditDetailChangeService settlementAuditDetailChangeService;



    @Override
    public BaseResponse createOrUpdateSettAudit(SettlementAuditVo request, String uId) {
        String id = request.getId();
        //保存
        if(ValidateUtil.isEmpty(id)){
            id = GenerateUUID.getUUID();
            SettlementAudit  audit = new SettlementAudit();
            BeanUtils.copyProperties(request,audit);
            audit.setId(id);
            audit.setDelFlag(1);
            audit.setStatus(1);
            audit.setCreateUid(uId);
            audit.setCreateTime(DateTime.now());
            if(!CollectionUtils.isEmpty(request.getDetailList())){
                audit.setAuditContractNum(request.getDetailList().size());
                 BigDecimal money = request.getDetailList().stream().filter(e -> ValidateUtil.isNotNull(e.getSettlementAuditPrice())).map(e -> e.getSettlementAuditPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                audit.setAuditContractMoney(money);
            }else{
                audit.setAuditContractNum(0);
                audit.setAuditContractMoney(BigDecimal.ZERO);
            }
            audit.setState(ApprovalStatusEnum.Not_Started.getIndex());
             SysUser user = iSysUserService.getById(uId);
             if(ValidateUtil.isNotNull(user)){
                 audit.setCreateUname(user.getUName());
             }
            baseMapper.insert(audit);
            settlementAuditDetailService.createSettlementAuditDetail(request.getDetailList(),audit.getId(),0);
            sysFileService.saveFile(audit.getId(), request.gettId(), audit.getState() + "", request.getCreateUid(), request.getCreateUname()
                    , audit.getCreateTime(), request.getProjectId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), audit.getId());
        }else{  //修改
             SettlementAudit settl = baseMapper.selectById(id);
             if(ValidateUtil.isNull(settl)){
                 return BaseResponse.fail("未查询到数据");
             }
            if(!CollectionUtils.isEmpty(request.getDetailList())){
                request.setAuditContractNum(request.getDetailList().size());
                BigDecimal money = request.getDetailList().stream().filter(e -> ValidateUtil.isNotNull(e.getSettlementAuditPrice())).map(e -> e.getSettlementAuditPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                request.setAuditContractMoney(money);
            }else{
                request.setAuditContractNum(0);
                request.setAuditContractMoney(BigDecimal.ZERO);
            }
            SettlementAudit audit = new SettlementAudit();
            BeanUtils.copyProperties(request,audit);
            SysFile fileEntity;
            fileEntity = sysFileService.getById(id);
            String fileFlowStatus = fileEntity.getFileflowstatus();
            audit.setState(Integer.valueOf(fileFlowStatus));
            baseMapper.updateById(audit);
            settlementAuditDetailService.createSettlementAuditDetail(request.getDetailList(),audit.getId(),1);
            sysFileService.saveFile(audit.getId(), request.gettId(), audit.getState() + "", audit.getCreateUid(), audit.getCreateUname()
                    , audit.getCreateTime(), request.getProjectId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), audit.getId());
        }
        return BaseResponse.ok(id);
    }

    @Override
    public BaseResponse deleteSettAuditById(String id) {
        baseMapper.deleteById(id);
        settlementAuditDetailService.deleteSettlementAuditId(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse querySettAuditList(SettlementAuditVo request) {
        Page<SettlementAudit> page = new Page<>(request.getPage(),request.getSize());
         Page<SettlementAuditVo> pageData = baseMapper.querySettAuditList(page, request);
        return BaseResponse.ok(pageData.getRecords(),(int)pageData.getTotal());
    }


    @Override
    public BaseResponse getSettAuditDetailId(String id) {
         SettlementAudit audit = baseMapper.selectById(id);
        SettlementAuditVo vo = new SettlementAuditVo();
        BeanUtils.copyProperties(audit,vo);
         List<SettlementAuditDetail> settAuditDetailId = settlementAuditDetailService.getSettAuditDetailId(id);
        List<SettlementAuditDetailVo> voList = settAuditDetailId.stream().map(e->{
            SettlementAuditDetailVo detailVo = new SettlementAuditDetailVo();
            BeanUtils.copyProperties(e,detailVo);
            return detailVo;
        }).collect(Collectors.toList());
        vo.setDetailList(voList);
        return BaseResponse.ok(vo);
    }

    @Override
    public BaseResponse getConReviewAmount(String conId) {
       Map<String,Object> map =  iSettlementFirstAuditService.getConReviewAmount(conId);
        return BaseResponse.ok(map);
    }

    @Override
    public BaseResponse querySettAuditListClassInfo(SettlementAuditVo request) {
        List<ClassInfoAnnotation> classInfoAnnotationList = new ArrayList<>();
        try {
            classInfoAnnotationList = ClassInfoUtils.getClassInfo(SettlementAuditRequest.class.getName());
            for (ClassInfoAnnotation classInfoAnnotation : classInfoAnnotationList) {
                if(!classInfoAnnotation.getType().equals("date")){
                    if (StrUtil.isNotEmpty(classInfoAnnotation.getDbValue())){
                        if (classInfoAnnotation.getValue().equals("projectNameList")){
                            List<Object> dictionaryValues =baseMapper.getprojectValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                        }else if (classInfoAnnotation.getValue().equals("stateList")){
                            List<Object> dictionaryValues =baseMapper.getStateByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                        }else if (classInfoAnnotation.getValue().equals("auditContractNumList")){
                            List<Object> dictionaryValues =baseMapper.getAuditContractNumByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                        }else if (classInfoAnnotation.getValue().equals("auditContractMoneyList")){
                            List<Object> dictionaryValues =baseMapper.getAuditContractMoneyByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                        }else if (classInfoAnnotation.getValue().equals("createUnameList")){
                            List<Object> dictionaryValues =baseMapper.getCreateUnameByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                        }else{
                            List<Object> dictionaryValues =baseMapper.getValueByCode(classInfoAnnotation.getDbValue());
                            dictionaryValues=dictionaryValues.stream().filter(Objects::nonNull).distinct().collect(Collectors.toList());
                            classInfoAnnotation.setDictionaryValues(dictionaryValues);
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error("getClassInfoAnnotations error:{}",e);
            return BaseResponse.ok(classInfoAnnotationList);
        }
        classInfoAnnotationList=classInfoAnnotationList.stream().sorted(Comparator.comparing(ClassInfoAnnotation::getNo)).collect(Collectors.toList());

        return BaseResponse.ok(classInfoAnnotationList);
    }

    @Override
    public BaseResponse changeSettAuditChange(SettlementAuditVo request) {

        return settlementAuditChangeService.changeSettAuditChange(request);
    }

    @Override
    public BaseResponse updateChangeSettAudit(String id) {
         SettlementAuditChange change = settlementAuditChangeService.getById(id);
         if(ValidateUtil.isNull(change)) return BaseResponse.fail("未查询到该数据");
         if(!change.getState().equals(ApprovalStatusEnum.Already_Over.getIndex()))return BaseResponse.fail("流程未结束，不允许变更");

        SettlementAudit audit = new SettlementAudit();
        BeanUtils.copyProperties(change,audit);
        audit.setId(change.getSaId());
        updateById(audit);

      List<SettlementAuditDetailChange> detailChanges = settlementAuditDetailChangeService.settlementAuditDetailIdList(change.getId());

        List<SettlementAuditDetailVo> detailList = detailChanges.stream().map(e->{
            SettlementAuditDetailVo vo = new SettlementAuditDetailVo();
            vo.setContractId(e.getContractId());
            vo.setContractName(e.getContractName());
            vo.setStatus(e.getStatus());
            vo.setDelFlag(e.getDelFlag());
            vo.setSettlementAuditPrice(e.getSettlementAuditPrice());
            vo.setSfaSettlementFirstPrice(e.getSfaSettlementFirstPrice());
            vo.setContractTotalMoney(e.getContractTotalMoney());
            vo.setSettlementAuditId(audit.getId());
            return vo;
        }).collect(Collectors.toList());

        settlementAuditDetailService.createSettlementAuditDetail(detailList,audit.getId(),1);
        return BaseResponse.ok();
    }
}
