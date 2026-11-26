package com.jpmsoft.projectProducer.service.zdyxy.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.MaintenancelStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.zdyxy.InvSettlementPayModel;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvSettlementApplyMapper;
import com.jpmsoft.projectProducer.mapper.zdyxy.InvSettlementPayMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementApply;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementApplyDtl;
import com.jpmsoft.projectProducer.pojo.entity.maintenance.MaintenanceRecord;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.zdyxy.InvSettlementPay;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.zdyxy.AnnualPlanService;
import com.jpmsoft.projectProducer.service.zdyxy.IInvSettlementApplyDtlService;
import com.jpmsoft.projectProducer.service.zdyxy.InvSettlementPayService;
import com.jpmsoft.projectProducer.service.zdyxy.ZdyxyMaintenanceRecordService;
import com.jpmsoft.projectProducer.utils.IdWorker;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional(rollbackFor = Exception.class)
public class InvSettlementPayServiceImpl
        extends ServiceImpl<InvSettlementPayMapper, InvSettlementPay>
        implements InvSettlementPayService {
    @Autowired
    private ZdyxyMaintenanceRecordService maintenanceRecordService;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Autowired
    private InvSettlementApplyMapper invSettlementApplyMapper;
    @Resource
    private IInvSettlementApplyDtlService invSettlementApplyDtlService;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private AnnualPlanService annualPlanService;

    @Override
    public BaseResponse saveOrUpdateData(InvSettlementPayModel model) {
        if (model == null) return BaseResponse.fail("缺少请求参数");
        if (model.getInvSettlementApplyId() == null) return BaseResponse.fail("结算申请ID");
        String primaryId = model.getId();
        log.info("primaryId:{}", primaryId);
        InvSettlementPay entity;
        if (StrUtil.isEmpty(primaryId)) {
            List<InvSettlementPay> entities = baseMapper.selectList(
                    new QueryWrapper<InvSettlementPay>()
                            .eq("inv_settlement_apply_id", model.getInvSettlementApplyId())
                            .ne("state", ApprovalStatusEnum.Invalid.getIndex()));
            if (CollectionUtil.isNotEmpty(entities)) {
                return BaseResponse.fail("该报修单的结算送审已存在款项支付");
            }
            primaryId = IdWorker.getGenerateID();
            entity = new InvSettlementPay();
            BeanUtils.copyProperties(model, entity);
            entity.setId(primaryId);
            entity.setCreateTime(DateTime.now());
            Integer flowStatus = ApprovalStatusEnum.Not_Started.getIndex();
            entity.setStateTime(DateTime.now());
            entity.setState(flowStatus);
            //saveFile(entity, documentDtos);
            //创建流程
            InvSettlementApply sa = invSettlementApplyMapper.selectById(model.getInvSettlementApplyId());
            if (sa != null) {
                entity.setConfirmCompleteId(sa.getCompletionConfirmId());
                MaintenanceRecord mr = maintenanceRecordService.getById(sa.getMrId());
                if (mr != null) {
                    entity.setMrId(mr.getMrId());
                    mr.setMrRepairState(MaintenancelStatusEnum.Repair_Project_Application_Approval_Finish.getIndex());
                    maintenanceRecordService.updateById(mr);
                }
            }
            entity.setApId(annualPlanService.getAnnualPlanByTime(entity.getCreateTime()));
            baseMapper.insert(entity);
            if (!sysFileService.FileHelper(entity, model, "create", model.getFileRelationId())) {
                return BaseResponse.fail("新增失败");
            }
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(model.getFileRelationId(), model.getInvoiceAttachmentFile(),
                    model.getOtherAttachmentFile(), model.getSaReportFile(), model.getPayAttachmentFile()), entity.getId());
        } else {
            entity = baseMapper.selectById(primaryId);
            BeanUtils.copyProperties(model, entity, "createUid", "createUname", "createTime");
            //saveFile(entity, documentDtos);
            InvSettlementApply sa = invSettlementApplyMapper.selectById(model.getInvSettlementApplyId());
            if (sa != null) {
                entity.setConfirmCompleteId(sa.getCompletionConfirmId());
                MaintenanceRecord mr = maintenanceRecordService.getById(sa.getMrId());
                if (mr != null) {
                    entity.setMrId(mr.getMrId());
                    mr.setMrRepairState(MaintenancelStatusEnum.Repair_Project_Application_Approval_Finish.getIndex());
                    maintenanceRecordService.updateById(mr);
                }
            }
            baseMapper.updateById(entity);
            sysFileAnnexService.refreshRelationFile(Arrays.asList(model.getFileRelationId(), model.getInvoiceAttachmentFile(),
                            model.getOtherAttachmentFile(), model.getPayAttachmentFile(), model.getSaReportFile())
                    , Arrays.asList(model.getFileIds(), model.getInvoiceAttachmentFileIds(),
                            model.getOtherAttachmentFileIds(), model.getPayAttachmentFileIds(), model.getSaReportFileIds()), entity.getId());
        }
        return BaseResponse.ok(primaryId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse approvalData(InvSettlementPayModel request) {
        String id = request.getId();
        InvSettlementPay entity;
        SysFile fileEntity;
        if (StrUtil.isEmpty(id)) {
            return BaseResponse.fail("缺少请求参数");
        } else {
            entity = baseMapper.selectById(id);
            if (entity == null) {
                return new BaseResponse("信息不存在，请刷新重试！");
            }

            fileEntity = sysFileService.getById(id);
            /*
              流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动
              5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
             */
            String fileFlowStatus = fileEntity.getFileflowstatus();
            entity.setState(Integer.valueOf(fileFlowStatus));
            entity.setStateTime(new Date());
            baseMapper.updateById(entity);

            InvSettlementApply invSettlementApply = invSettlementApplyMapper.selectById(entity.getInvSettlementApplyId());
            if (ValidateUtil.isNull(invSettlementApply)) {
                return BaseResponse.ok();
            }
            List<InvSettlementApplyDtl> applyDtlList = invSettlementApplyDtlService.list(Wrappers.<InvSettlementApplyDtl>lambdaQuery()
                    .eq(InvSettlementApplyDtl::getIsaId, invSettlementApply.getSaId())
            );
            if (ValidateUtil.isEmpty(applyDtlList)) {
                return BaseResponse.ok();
            }
            Set<String> mrIds = applyDtlList.stream()
                    .map(InvSettlementApplyDtl::getMrId)
                    .filter(ValidateUtil::isNotEmpty)
                    .collect(Collectors.toSet());
            if (ValidateUtil.isEmpty(mrIds)) {
                return BaseResponse.ok();
            }
            List<MaintenanceRecord> maintenanceRecords = maintenanceRecordService.list(Wrappers.<MaintenanceRecord>lambdaQuery()
                    .in(MaintenanceRecord::getMrId, mrIds)
            );
            Map<String, MaintenanceRecord> mrMap = maintenanceRecords.stream()
                    .collect(Collectors.toMap(MaintenanceRecord::getMrId, mr -> mr, (mr1, mr2) -> mr1));
            List<MaintenanceRecord> updateMrList = new ArrayList<>();
            for (InvSettlementApplyDtl invSettlementApplyDtl : applyDtlList) {
                if (ValidateUtil.isNotEmpty(invSettlementApplyDtl.getMrId())) {
                    MaintenanceRecord mr = mrMap.get(invSettlementApplyDtl.getMrId());
                    if (mr != null) {
                        if (ApprovalStatusEnum.Already_Over.getIndex().equals(entity.getState())) {
                            mr.setMrRepairState(MaintenancelStatusEnum.Suspending_Payment_Finish.getIndex());
                        } else if (ApprovalStatusEnum.Invalid.getIndex().equals(entity.getState())) {
                            mr.setMrRepairState(MaintenancelStatusEnum.Settlement_Submission_Finish.getIndex());
                        } else {
                            mr.setMrRepairState(MaintenancelStatusEnum.Suspending_Payment_Approval.getIndex());
                        }
                        updateMrList.add(mr);
                    }
                }
            }
            if (ValidateUtil.isNotEmpty(updateMrList)) {
                maintenanceRecordService.updateBatchById(updateMrList);
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getList(InvSettlementPayModel request, String token) {
        String authSql = sysUserService.getAuthSql("inv_settlement_pay", redisUtils.getUid(token), "Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "State", "id");
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        log.info("查询列表:{}", JSON.toJSONString(request, JSONWriter.Feature.ReferenceDetection));
        QueryMap<InvSettlementPayModel> queryMap = new QueryMap<>(request);
        queryMap.put("confirmSerialNumber").put("constructionContractorName")
                .put("invSettlementSerialNumber").put("state").put("createTimeRange")
                .put("saTitle").put("isSelect").put("authSql", authSql);
        IPage<InvSettlementPayModel> page = new Page<>(request.getPage(), request.getSize());
        List<InvSettlementPayModel> list = baseMapper.getList(queryMap.getQuery(), page);
        log.info("查询列表:{}", JSON.toJSONString(list, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getDetail(String id) {
        InvSettlementPayModel model = baseMapper.getDetail(id);
        if (model == null) {
            return new BaseResponse("信息不存在，请刷新重试！");
        }
        log.info("查询详情:{}", JSON.toJSONString(model, JSONWriter.Feature.ReferenceDetection));
        return BaseResponse.ok(model);
    }
}