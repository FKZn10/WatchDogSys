package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.SettlementAuditChangeMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementAuditChange;
import com.jpmsoft.projectProducer.pojo.entity.investment.SettlementAuditDetailChange;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.vo.investment.SettlementAuditChangeVo;
import com.jpmsoft.projectProducer.pojo.vo.investment.SettlementAuditVo;
import com.jpmsoft.projectProducer.service.investment.SettlementAuditChangeService;
import com.jpmsoft.projectProducer.service.investment.SettlementAuditDetailChangeService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class SettlementAuditChangeServiceImpl extends ServiceImpl<SettlementAuditChangeMapper, SettlementAuditChange> implements SettlementAuditChangeService {

    @Autowired
    private SettlementAuditDetailChangeService settlementAuditDetailChangeService;

    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public BaseResponse changeSettAuditChange(SettlementAuditVo request) {
        String id = request.getId();
        if(ValidateUtil.isEmpty(id)){
            SettlementAuditChange change = new SettlementAuditChange();
            id = GenerateUUID.getUUID();
            BeanUtils.copyProperties(request,change);
            change.setId(id);
            change.setSaId(request.getSaId());
            change.setChangeTime(new Date());
            if(!CollectionUtils.isEmpty(request.getDetailList())){
                change.setAuditContractNum(request.getDetailList().size());
                BigDecimal money = request.getDetailList().stream().filter(e -> ValidateUtil.isNotNull(e.getSettlementAuditPrice())).map(e -> e.getSettlementAuditPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                change.setAuditContractMoney(money);
            }else{
                change.setAuditContractNum(0);
                change.setAuditContractMoney(BigDecimal.ZERO);
            }
//        SysFile fileEntity;
//        fileEntity = sysFileService.getById(id);
            change.setState(ApprovalStatusEnum.Not_Started.getIndex());
            baseMapper.insert(change);
            settlementAuditDetailChangeService.createSettlementAuditDetailChange(request.getDetailList(),change.getId(),0);
            sysFileService.saveFile(change.getId(), request.gettId(), change.getState() + "", request.getCreateUid(), request.getCreateUname()
                    , change.getCreateTime(), request.getProjectId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), change.getId());
        }else{
             SettlementAuditChange change = baseMapper.selectById(request.getId());
             if(ValidateUtil.isNull(change)) return BaseResponse.fail("未查询到该数据");
            if(!CollectionUtils.isEmpty(request.getDetailList())){
                change.setAuditContractNum(request.getDetailList().size());
                BigDecimal money = request.getDetailList().stream().filter(e -> ValidateUtil.isNotNull(e.getSettlementAuditPrice())).map(e -> e.getSettlementAuditPrice()).reduce(BigDecimal.ZERO, BigDecimal::add);
                change.setAuditContractMoney(money);
            }else{
                change.setAuditContractNum(0);
                change.setAuditContractMoney(BigDecimal.ZERO);
            }
            SysFile fileEntity;
            fileEntity = sysFileService.getById(id);
            String fileFlowStatus = fileEntity.getFileflowstatus();
            SettlementAuditChange auditChange = new SettlementAuditChange();
            BeanUtils.copyProperties(request,auditChange);
            auditChange.setState(Integer.valueOf(fileFlowStatus));
            baseMapper.updateById(auditChange);
            settlementAuditDetailChangeService.createSettlementAuditDetailChange(request.getDetailList(),change.getId(),1);
            sysFileService.saveFile(auditChange.getId(), request.gettId(), auditChange.getState() + "", auditChange.getCreateUid(), auditChange.getCreateUname()
                    , auditChange.getCreateTime(), request.getProjectId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), auditChange.getId());
        }


        return BaseResponse.ok(id);
    }

    @Override
    public BaseResponse querySettAuditChangeList(SettlementAuditVo request) {
        Page<SettlementAuditChange> page = new Page<>(request.getPage(),request.getSize());
        LambdaQueryWrapper<SettlementAuditChange> queryWrapper = new LambdaQueryWrapper<>();
        if(ValidateUtil.isNotEmpty(request.getProjectId()))
            queryWrapper.eq(SettlementAuditChange::getProjectId,request.getProjectId());
        if(ValidateUtil.isNotEmpty(request.getProjectName()))
            queryWrapper.like(SettlementAuditChange::getProjectName,request.getProjectName());
        if(ValidateUtil.isNotEmpty(request.getCreateUname()))
            queryWrapper.like(SettlementAuditChange::getCreateUname,request.getCreateUname());
        if(ValidateUtil.isNotNull(request.getState()))
            queryWrapper.eq(SettlementAuditChange::getState,request.getState());
        if(ValidateUtil.isNotNull(request.getAuditContractNum()))
            queryWrapper.eq(SettlementAuditChange::getAuditContractNum,request.getAuditContractNum());
        if(ValidateUtil.isNotNull(request.getAuditContractMoney()))
            queryWrapper.eq(SettlementAuditChange::getAuditContractMoney,request.getAuditContractMoney());
        if(ValidateUtil.isNotNull(request.getCreateUid()))
            queryWrapper.eq(SettlementAuditChange::getCreateUid,request.getCreateUid());
        Page<SettlementAuditChange> pageData = baseMapper.selectPage(page, queryWrapper);
        return BaseResponse.ok(pageData.getRecords(),(int)pageData.getTotal());

    }

    @Override
    public BaseResponse getSettAuditDetailChangeId(String id) {
         SettlementAuditChange change = baseMapper.selectById(id);
        SettlementAuditChangeVo changeVo = new SettlementAuditChangeVo();
        BeanUtils.copyProperties(change,changeVo);
      List<SettlementAuditDetailChange> list =   settlementAuditDetailChangeService.settlementAuditDetailIdList(id);
      if(!CollectionUtils.isEmpty(list)){
          changeVo.setDetailList(list);
      }
        return BaseResponse.ok(changeVo);
    }
}
