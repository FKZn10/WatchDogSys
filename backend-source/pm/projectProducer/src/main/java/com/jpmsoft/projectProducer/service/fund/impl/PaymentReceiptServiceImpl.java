package com.jpmsoft.projectProducer.service.fund.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.requestAndResponse.fund.PaymentReceiptHistoryDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.enums.IssuedStateEnum;
import com.jpmsoft.projectProducer.enums.VerifyStateEnum;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.mapper.fund.PaymentReceiptMapper;
import com.jpmsoft.projectProducer.pojo.dto.fund.PaymentReceiptDto;
import com.jpmsoft.projectProducer.pojo.dto.other.SysCcSendDto;
import com.jpmsoft.projectProducer.pojo.entity.fund.ClaimIncomingFundsDtl;
import com.jpmsoft.projectProducer.pojo.entity.fund.PaymentReceipt;
import com.jpmsoft.projectProducer.pojo.entity.fund.PaymentReceiptHistory;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.TaskProcessin;
import com.jpmsoft.projectProducer.pojo.request.fund.PaymentReceiptRequest;
import com.jpmsoft.projectProducer.pojo.vo.fund.PaymentReceiptVo;
import com.jpmsoft.projectProducer.service.fund.IClaimIncomingFundsDtlService;
import com.jpmsoft.projectProducer.service.fund.IPaymentReceiptHistoryService;
import com.jpmsoft.projectProducer.service.fund.IPaymentReceiptService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.other.TaskProcessinService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import dm.jdbc.util.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PaymentReceiptServiceImpl extends BaseFileImpl<PaymentReceiptMapper, PaymentReceipt, PaymentReceiptDto> implements IPaymentReceiptService  {
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private BpmFeign bpmFeign;
    @Autowired
    private TaskProcessinService taskProcessinService;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IClaimIncomingFundsDtlService claimIncomingFundsDtlService;
    @Resource
    private IPaymentReceiptHistoryService paymentReceiptHistoryService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdatePaymentReceipt(PaymentReceiptDto request) {
        String primaryId = request.getId();
        PaymentReceipt entity;
        if (StrUtil.isEmpty(primaryId)){
            entity = new PaymentReceipt();
            BeanUtil.copyProperties(request,entity);
            entity.setCreateTime(DateTime.now());
            primaryId=GenerateUUID.getUUID();
            entity.setId(primaryId);
            request.setPId(request.getProjectId());
            entity.setPrCName("南京城市建设投资控股（集团）有限公司");
            String seqCode = "PaymentReceipt";
            String code = generateCodeByTCode(seqCode,request);
            if (ValidateUtil.isEmpty(code))
                 return new BaseResponse("支付编号生成失败");
            entity.setPrCode(code);
                            //收据流程已提交，未审批完成。
            entity.setPrIssuedState(IssuedStateEnum.Not_Started.getIndex());
            entity.setPrVerifyState(VerifyStateEnum.Not_Started.getIndex());
            //“是否需要开具线下收据”选择“否”的时候，“线下开具状态”默认展示为“无需开具”。
            // if(entity.getIsPayIssued()==0){
            //     entity.setPrIssuedState(IssuedStateEnum.Ingored.getIndex());
            // }
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
            //创建流程
            if(sysFileService.FileHelper(entity,request,"create",request.getFileRelationId())){

            }else{
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
        }else{
            entity = baseMapper.selectById(primaryId);
            BeanUtil.copyProperties(request,entity);
            // if(entity.getIsPayIssued()==0){
            //     entity.setPrIssuedState(IssuedStateEnum.Ingored.getIndex());
            // }
            baseMapper.updateById(entity);
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        }
        //createOrUpdateFileEntity(entity, request);
        // 处理子表
        paymentReceiptHistoryService.saveOrUpdateBatchByDtoList(primaryId, request.getHistoryList(), request.getPrIsClaim());
        return BaseResponse.ok(primaryId);
    }
    @Override
    public BaseResponse getPaymentReceiptById(String id) {
        PaymentReceiptVo paymentReceiptVo= baseMapper.selectPaymentReceiptById(id);
        List<PaymentReceiptHistoryDTO> receiptHistories = paymentReceiptHistoryService.getByPrId(id);
        if (ValidateUtil.isNotEmpty(receiptHistories)) {
            paymentReceiptVo.setHistoryList(receiptHistories);
        }
        return BaseResponse.ok(paymentReceiptVo);
    }

    @Override
    public BaseResponse getPaymentReceiptList(PaymentReceiptRequest request, String uId) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<PaymentReceiptVo> page = new Page<>(request.getPage(), request.getSize());
        String authSql = sysUserService.getAuthSql("Payment_Receipt", uId, "Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "State", "id");
        QueryMap<PaymentReceiptRequest> params = new QueryMap<>(request);
        params.put("projectId").put("pName").put("prVerifyState").put("prIssuedState").put("state")
                .put("prVerifyStates").put("hasUnwrittenAmount").put("authSql", authSql);

        List<PaymentReceiptVo> paymentReceiptVos = baseMapper.getPaymentReceiptList(page, params.getQuery());
        if (CollectionUtil.isEmpty(paymentReceiptVos)) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }
        List<String> prIds = paymentReceiptVos.stream()
                .map(PaymentReceiptVo::getId)
                .collect(Collectors.toList());
        List<PaymentReceiptHistory> list = paymentReceiptHistoryService.list(Wrappers.<PaymentReceiptHistory>lambdaQuery().in(PaymentReceiptHistory::getPrId, prIds));
        Map<String, List<PaymentReceiptHistory>> historyMap = list.stream()
                .collect(Collectors.groupingBy(PaymentReceiptHistory::getPrId));

        for (PaymentReceiptVo paymentReceiptVo : paymentReceiptVos) {
            if (paymentReceiptVo.getState().equals(ApprovalStatusEnum.Already_Over.getIndex())) {
                List<PaymentReceiptHistory> paymentReceiptHistories = historyMap.get(paymentReceiptVo.getId());
                // 构建一个\n分隔的时间字符串
                if (CollectionUtil.isNotEmpty(paymentReceiptHistories)) {
                    StringBuilder historyTimes = new StringBuilder();
                    for (PaymentReceiptHistory history : paymentReceiptHistories) {
                        if (historyTimes.length() > 0) {
                            historyTimes.append("<br>");
                        }
                        historyTimes.append(DateTime.of(history.getCreateTime()).toString("yyyy-MM-dd"));
                    }
                    paymentReceiptVo.setPrVerifyTimes(historyTimes.toString());
                }
            }
        }
        return BaseResponse.ok(paymentReceiptVos, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse approvePaymentReceipt(PaymentReceiptRequest request) {
        PaymentReceipt entity ;
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
            if(fileFlowStatus.equals(ApprovalStatusEnum.Already_Over.getIndex().toString())){
                //收据流程审批完成，未确认开具、未处理核销。
                entity.setPrIssuedState(IssuedStateEnum.Started.getIndex());
                entity.setPrVerifyState(VerifyStateEnum.Started.getIndex());
            } else if(fileFlowStatus.equals(ApprovalStatusEnum.Invalid.getIndex().toString())){
                entity.setPrIssuedState(IssuedStateEnum.Stop.getIndex());
                entity.setPrVerifyState(VerifyStateEnum.Stop.getIndex());
            }else {
                //收据流程已提交，未审批完成。
                entity.setPrIssuedState(IssuedStateEnum.Not_Started.getIndex());
                entity.setPrVerifyState(VerifyStateEnum.Not_Started.getIndex());
            }
            // if(entity.getIsPayIssued()==0){
            //     entity.setPrIssuedState(IssuedStateEnum.Ingored.getIndex());
            // }
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok();
    }
    /***
     * 核销
     * @param request
     * @return
     */
    @Override
    public BaseResponse setPaymentReceiptVerify(PaymentReceiptRequest request,String token) {
        PaymentReceipt entity ; String id =request.getId();
        if (StrUtil.isEmpty(id)) {
            return new BaseResponse("缺少请求参数");
        } else {
            entity = baseMapper.selectById(id);
            if (!Objects.equals(entity.getState(), ApprovalStatusEnum.Already_Over.getIndex())) {
                return BaseResponse.fail(ResMsg.stateCannotAct);
            }
            entity.setPrVerifyState(request.getPrVerifyState());
            entity.setPrRollbackReason(request.getPrRollbackReason());
            entity.setPrVerifyTime(DateTime.now());
            if (ValidateUtil.isNotEmpty(request.getPrIsClaim())) {
                entity.setPrIsClaim(request.getPrIsClaim());
            }
            if (ValidateUtil.isNotNull(request.getCifId())) {
                entity.setCifId(request.getCifId());
            }
            QueryWrapper<TaskProcessin> wrapper = new QueryWrapper<>();
            wrapper.eq("tp_entityid", id);
            wrapper.eq("tp_acttype", "userTask");
            wrapper.eq("tp_type", "提交");
            wrapper.orderByDesc("tp_dotime");
            List<TaskProcessin> taskProcessins = taskProcessinService.list(wrapper);
            SysCcSendDto sysCcSendDto = getSysCcSendDto(entity,taskProcessins);
            bpmFeign.cctransactionSend(sysCcSendDto,token);
            if(entity.getPrVerifyState()==VerifyStateEnum.RollBack.getIndex()
                    && entity.getPrIssuedState()==IssuedStateEnum.Already.getIndex()){
                entity.setPrIssuedState(IssuedStateEnum.Invalid.getIndex());
                entity.setPrCancelTime(DateTime.now());
            }
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse setPaymentReceiptVerifys(List<PaymentReceiptRequest> requests, String token) {
        if (CollectionUtil.isEmpty(requests)) {
            return new BaseResponse("缺少请求参数");
        }
        List<PaymentReceiptRequest> validRequests = requests.stream()
                .filter(Objects::nonNull)
                .filter(r -> StrUtil.isNotEmpty(r.getId()))
                .collect(Collectors.toList());
        if (CollectionUtil.isEmpty(validRequests)) {
            return BaseResponse.ok();
        }
        Set<String> ids = validRequests.stream().map(PaymentReceiptRequest::getId).collect(Collectors.toSet());
        List<PaymentReceipt> receipts = baseMapper.selectBatchIds(ids);
        if (CollectionUtil.isEmpty(receipts)) {
            return BaseResponse.ok();
        }
        Map<String, PaymentReceipt> entityMap = receipts.stream()
                .filter(entity -> Objects.equals(entity.getState(), ApprovalStatusEnum.Already_Over.getIndex()))
                .collect(Collectors.toMap(PaymentReceipt::getId, Function.identity()));
        if (CollectionUtil.isEmpty(entityMap)) {
            return BaseResponse.ok();
        }

        LambdaQueryWrapper<TaskProcessin> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(TaskProcessin::getTpEntityid, entityMap.keySet());
        wrapper.eq(TaskProcessin::getTpActtype, "userTask");
        wrapper.eq(TaskProcessin::getTpType, "提交");
        wrapper.orderByDesc(TaskProcessin::getTpDotime);
        List<TaskProcessin> taskProcessins = taskProcessinService.list(wrapper);
        Map<String, List<TaskProcessin>> taskProcessinMap = taskProcessins.stream()
                .collect(Collectors.groupingBy(TaskProcessin::getTpEntityid));
        taskProcessinMap.values().forEach(list ->
                list.sort(Comparator.comparing(TaskProcessin::getTpDotime,
                        Comparator.nullsLast(Date::compareTo)).reversed()));

        List<PaymentReceipt> toUpdateEntities = new ArrayList<>();
        for (PaymentReceiptRequest request : validRequests) {
            PaymentReceipt entity = entityMap.get(request.getId());
            if (entity == null) {
                continue;
            }
            entity.setPrVerifyState(request.getPrVerifyState());
            entity.setPrRollbackReason(request.getPrRollbackReason());
            entity.setPrVerifyTime(DateTime.now());
            if (ValidateUtil.isNotEmpty(request.getPrIsClaim())) {
                entity.setPrIsClaim(request.getPrIsClaim());
            }
            if (ValidateUtil.isNotNull(request.getCifId())) {
                entity.setCifId(request.getCifId());
            }
            List<TaskProcessin> currentTaskProcessins =
                    taskProcessinMap.getOrDefault(entity.getId(), Collections.emptyList());
            SysCcSendDto sysCcSendDto = getSysCcSendDto(entity, currentTaskProcessins);
            bpmFeign.cctransactionSend(sysCcSendDto, token);
            if (Objects.equals(entity.getPrVerifyState(), VerifyStateEnum.RollBack.getIndex())
                    && Objects.equals(entity.getPrIssuedState(), IssuedStateEnum.Already.getIndex())) {
                entity.setPrIssuedState(IssuedStateEnum.Invalid.getIndex());
                entity.setPrCancelTime(DateTime.now());
            }
            toUpdateEntities.add(entity);
        }
        if (ValidateUtil.isNotEmpty(toUpdateEntities)) {
            this.updateBatchById(toUpdateEntities);
        }
        return BaseResponse.ok();
    }

    private SysCcSendDto getSysCcSendDto(PaymentReceipt entity,List<TaskProcessin> taskProcessins){
        SysCcSendDto sysCcSendDto = new SysCcSendDto();
        sysCcSendDto.setTaskname("收据核销");
        sysCcSendDto.setFileId(entity.getId());
        sysCcSendDto.setUserId(entity.getCreateUid());
        List<String> ccids = new ArrayList<>();
        ccids.add(entity.getCreateUid());
        if(entity.getPrVerifyState()==VerifyStateEnum.Already.getIndex()){
            //核销成功，核销经办人操作后，抄送给收据发起人、集团工程部项目经办人。
            sysCcSendDto.setExplain("核销成功");
            taskProcessins.stream().filter(n->n.getActname().equals("集团经办人")).findFirst().ifPresent(n->{
                ccids.add(n.getTpUser());
            });

        }else if(entity.getPrVerifyState()==VerifyStateEnum.RollBack.getIndex()){
            //核销退回，单据为已未开具状态，核销经办人操作后，抄送给收据发起人；
            //单据为已开具状态，抄送给收据发起人、阅办财务经办人。
            sysCcSendDto.setExplain("核销退回");
            if(entity.getPrIssuedState()==IssuedStateEnum.Already.getIndex()){
                //单据为已开具状态，抄送给收据发起人、阅办财务经办人
                taskProcessins.stream().filter(n->n.getActname().equals("财务经办人")).findFirst().ifPresent(n->{
                    ccids.add(n.getTpUser());
                });
            }
        }
        sysCcSendDto.setCcIds(ccids);
        return sysCcSendDto;
    }

    /***
     * 开具
     * @param request
     * @return
     */
    @Override
    public BaseResponse setPaymentReceiptIssued(PaymentReceiptRequest request) {
        PaymentReceipt entity ; String id =request.getId();
        if (StrUtil.isEmpty(id)) {
            return new BaseResponse("缺少请求参数");
        } else {
            entity = baseMapper.selectById(id);
            entity.setPrIssuedTime(DateTime.now());
            entity.setPrIssuedState(IssuedStateEnum.Already.getIndex());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok();
    }
    /***
     * 回收
     * @param request
     * @return
     */
    @Override
    public BaseResponse setPaymentReceiptRecovery(PaymentReceiptRequest request) {
        PaymentReceipt entity ; String id =request.getId();
        if (StrUtil.isEmpty(id)) {
            return new BaseResponse("缺少请求参数");
        } else {
            entity = baseMapper.selectById(id);
            entity.setPrRecoveryTime(DateTime.now());
            entity.setPrIssuedState(IssuedStateEnum.RollBack.getIndex());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getFundPaymentReceiptList(PaymentReceiptRequest request,String uid) {
        List<PaymentReceiptVo> paymentReceiptVos =new ArrayList<>();
        IPage<PaymentReceiptVo> page = new Page<>(request.getPage(), request.getSize());
        log.info("request:"+JacksonUtils.toJSONString(request));
        if(StringUtil.isNotEmpty(request.getpId())){
            QueryMap<PaymentReceiptRequest> params = new QueryMap<>(request);
            params.put("pId").put("pName").put("prVerifyState").put("prIssuedState");
            log.info("params:"+JacksonUtils.toJSONString(params));
            paymentReceiptVos =baseMapper.getPaymentReceiptList2(page, params.getQuery());
        }
        log.info("paymentReceiptVos:"+JacksonUtils.toJSONString(paymentReceiptVos));
        return BaseResponse.ok(paymentReceiptVos, (int) page.getTotal());
    }

    public String generateCodeByTCode(String tCode, Object request) {
        try {
            HttpEntity<String> formEntity = assemblyRequestParam(request);
            String url = "http://user-producer/v1/seqSet/generateCodeByTCode?tCode=" + tCode;
            String res = restTemplate.postForObject(url, formEntity, String.class);
            return res;
        } catch (Exception ex) {
            return null;
        }
    }
    private HttpEntity<String> assemblyRequestParam(Object object) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        log.info("服务调用时:" + JacksonUtils.toJSONString(object));
        HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }

    /**
     * 同步到来款认领
     *
     * @param id
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncToClaimIncomingFunds(String id, String token) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        PaymentReceipt entity = this.getById(id);
        if (ValidateUtil.isNull(entity)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!Objects.equals(entity.getState(), ApprovalStatusEnum.Already_Over.getIndex())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        if ("1".equals(entity.getPrIsClaim())) {
            // 查询历史记录表
            List<PaymentReceiptHistory> histories = paymentReceiptHistoryService.list(Wrappers.<PaymentReceiptHistory>lambdaQuery()
                    .eq(PaymentReceiptHistory::getPrId, id)
            );
            if (ValidateUtil.isNotEmpty(histories)) {
                // 查询全部匹配的来款
                List<ClaimIncomingFundsDtl> fundsDtls = claimIncomingFundsDtlService.list(Wrappers.<ClaimIncomingFundsDtl>lambdaQuery()
                        .in(ClaimIncomingFundsDtl::getId, histories.stream()
                                .map(PaymentReceiptHistory::getCifdId)
                                .collect(Collectors.toList())
                        )
                );
                Map<String, ClaimIncomingFundsDtl> fundsDtlMap = fundsDtls.stream()
                        .collect(Collectors.toMap(ClaimIncomingFundsDtl::getId, Function.identity()));
                List<ClaimIncomingFundsDtl> toUpdateFundsDtls = new ArrayList<>();
                histories.forEach(history -> {
                    ClaimIncomingFundsDtl fundsDtl = fundsDtlMap.get(history.getCifdId());
                    if (ValidateUtil.isNull(fundsDtl)) {
                        return;
                    }
                    fundsDtl.setCifdIsReceipt("1");
                    fundsDtl.setCifdReceipt(id);
                    fundsDtl.setCifdVerifyState("2");
                    toUpdateFundsDtls.add(fundsDtl);
                    history.setVerifyState(2);
                    history.setCreateTime(new Date());
                });
                if (ValidateUtil.isNotEmpty(toUpdateFundsDtls)) {
                    claimIncomingFundsDtlService.updateBatchById(toUpdateFundsDtls);
                }
                paymentReceiptHistoryService.updateBatchById(histories);
                return BaseResponse.ok();
            }
        }
        return BaseResponse.ok("无需处理");
    }
}
