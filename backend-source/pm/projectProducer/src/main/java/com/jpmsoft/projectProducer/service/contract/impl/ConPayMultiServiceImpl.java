package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.Contract.ConPayMultiRequest;
import com.jpmsoft.common.model.request.pay.ConFundNoDto;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.common.model.response.Contract.ConPayMultiFundingResponse;
import com.jpmsoft.common.model.response.Contract.ConPayMultiProjectResponse;
import com.jpmsoft.common.model.response.Contract.ConPayMultiResponse;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ConPayMultiMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayMulti;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayMultiFunding;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayMultiProject;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import com.jpmsoft.projectProducer.service.contract.IConPayMultiFundingService;
import com.jpmsoft.projectProducer.service.contract.IConPayMultiProjectService;
import com.jpmsoft.projectProducer.service.contract.IConPayMultiService;
import com.jpmsoft.projectProducer.service.contract.IConPayService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 多项目合同支付
 *
 * @author tianhelin
 * @date 2025/9/10
 */
@Service
public class ConPayMultiServiceImpl extends ServiceImpl<ConPayMultiMapper, ConPayMulti> implements IConPayMultiService {
    @Resource
    private IConPayMultiProjectService conPayMultiProjectService;
    @Resource
    private IConPayMultiFundingService conPayMultiFundingService;
    @Resource
    private IConPayService conPayService;
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    /**
     * 同步支付信息到合同支付表
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse syncToConPay(ConPayMultiRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        String id = request.getId();
        ConPayMulti payMulti = getById(id);
        if (ValidateUtil.isNull(payMulti)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (!JudgeUtil.isApprovedState(payMulti.getState())) {
            return BaseResponse.fail(ResMsg.stateCannotAct);
        }
        // 子表信息
        List<ConPayMultiProjectResponse> multiProjects = conPayMultiProjectService.getConPayMultiProjectByCpmId(id);
        if (ValidateUtil.isEmpty(multiProjects)) {
            return BaseResponse.fail("多项目合同支付未配置项目信息，无法同步至合同支付");
        }
        List<ConPayMultiProject> projectUpdateList = new ArrayList<>();
        List<ConPayMultiFunding> multiFundings = conPayMultiFundingService.list(Wrappers.<ConPayMultiFunding>lambdaQuery().eq(ConPayMultiFunding::getCpmId, id));

        // List<ConPay> existingConPays = conPayService.list(Wrappers.<ConPay>lambdaQuery().eq(ConPay::getCpmId, id));
        // // 按支付合同分组
        // Map<String, ConPay> existingConPayMap = existingConPays.stream()
        //         .collect(Collectors.toMap(ConPay::getPayId, contract -> contract, (a, b) -> a));

        // 多项目合同表单附件
        String fileRelationId = sysFileService.fixFileRelationId(id);

        for (ConPayMultiProjectResponse multiProject : multiProjects) {
            ConPayRequest conPay = new ConPayRequest();
            // 判断是否已经存在支付合同，存在则更新
            String existingPayId = multiProject.getPayId();
            String conFileRelationId = "";
            if (ValidateUtil.isNotEmpty(existingPayId)) {
                conPay.setPayId(existingPayId);
                conFileRelationId = sysFileService.fixFileRelationId(existingPayId);
                // 维护时先删除已经同步过去的文件
                if (ValidateUtil.isNotEmpty(conFileRelationId)) {
                    sysFileAnnexService.remove(Wrappers.<SysFileannex>lambdaQuery()
                            .eq(SysFileannex::getFileRelationId, conFileRelationId));
                }
            }
            conPay.setPId(multiProject.getPId());
            conPay.setPayCode(multiProject.getPayCode());
            conPay.setPayCreateTime(payMulti.getCreateTime());
            conPay.setPayCreateUid(payMulti.getCreateUid());
            conPay.setPayCreateUname(payMulti.getCreateUname());
            conPay.setPayState(ApprovalStatusEnum.Already_Over.getIndex());
            conPay.setPayStateTime(payMulti.getStateTime());
            conPay.setPayCategory("01");
            // 合同ID
            conPay.setConId(multiProject.getConId());
            // 多项目合同支付ID
            conPay.setCpmId(payMulti.getId());
            // 款项类别
            conPay.setPayCategoryPayment(payMulti.getPaymentType());
            // 款项名称
            conPay.setPayType(payMulti.getPaymentName());
            // 收款单位
            conPay.setPayPayee(payMulti.getPayeeUnit());
            // 支付时间
            conPay.setPayApplicationDate(payMulti.getPayTime());
            // 付款期数
            conPay.setPayNumberStr(String.valueOf(payMulti.getPayPeriod()));
            // 付款总期数
            conPay.setPayNumberSum(payMulti.getPayTotalPeriods());
            // 已支付
            conPay.setPayTotalLast(multiProject.getPaidAmount());
            // 工程款金额
            conPay.setPayAomount(multiProject.getProjectAmount());
            // 本次付款总金额
            conPay.setPayApply(multiProject.getCurrentPayAmount());
            // 工人工资金额
            conPay.setPayFarmersExpenditure(multiProject.getWorkerWageAmount());
            // 工人工资累计已支付金额
            conPay.setPayFarmersTotal(multiProject.getWorkerWagePaidAmount());
            // 工程款累计已支付金额
            conPay.setPayWorkAmountTotal(multiProject.getProjectPaidAmount());
            // 已支付占合同价比例
            conPay.setPayTotalLastP(ValidateUtil.isNull(multiProject.getPaidRatio()) ? 0 : multiProject.getPaidRatio().floatValue());
            // 安全文明措施费累计支付金额
            conPay.setPayDeductScThis(multiProject.getSafetyFeePaidAmount());
            // 变更签证累计已支付金额
            conPay.setPayChangePayTotalThis(multiProject.getChangeVisaPaidAmount());
            // 签证金额占暂列金比例
            conPay.setPayProportion(ValidateUtil.isNull(multiProject.getVisaTempFundRatio()) ? 0 : multiProject.getVisaTempFundRatio().floatValue());
            // 付款依据
            conPay.setPayRemark(payMulti.getPayBasis());

            conPay.setPayPbTakeDate(payMulti.getDocumentPreparationdata());

            // 项目经费号子表
            List<ConFundNoDto> models = multiFundings.stream().map(funding -> {
                ConFundNoDto model = new ConFundNoDto();
                model.setId(GenerateUUID.getUUID());
                model.setFnCode(funding.getFundingNo());
                model.setFnMoney(funding.getAmount());
                model.setFnSort(funding.getOrderNo());
                return model;
            }).collect(Collectors.toList());
            conPay.setConFundNos(models);

            // 流程表字段补充
            FileTemplateTree fileTemplate = sysFileService.getFileTemplateByTCode(request.gettCode());
            conPay.settId(fileTemplate.gettId());
            conPay.setFileTitle(fileTemplate.gettName());
            conPay.setFileCode(fileTemplate.gettCode());
            conPay.settCode(fileTemplate.gettCode());

            BaseResponse response = conPayService.createOrUpdateConPay(conPay, true);
            if (response.getSuccess() && ValidateUtil.isNotNull(response.getData())) {
                String payId = (String) response.getData();
                if (ValidateUtil.isEmpty(existingPayId)) {
                    ConPayMultiProject payMultiProject = new ConPayMultiProject();
                    BeanUtils.copyProperties(multiProject, payMultiProject);
                    payMultiProject.setPayId(payId);
                    projectUpdateList.add(payMultiProject);
                }
                if (ValidateUtil.isEmpty(conFileRelationId)) {
                    conFileRelationId = sysFileService.fixFileRelationId(payId);
                }
                if (ValidateUtil.isNotEmpty(conFileRelationId) && ValidateUtil.isNotEmpty(fileRelationId)) {
                    sysFileAnnexService.copyFileAnnex2NewRelation(
                            Arrays.asList(fileRelationId, multiProject.getPayMultiFile()),
                            Arrays.asList(conFileRelationId, conFileRelationId),
                            payId,
                            true
                    );
                }
            } else {
                throw new RuntimeException("多项目合同支付同步失败");
            }
        }
        if (ValidateUtil.isNotEmpty(projectUpdateList)) {
            conPayMultiProjectService.updateBatchById(projectUpdateList);
        }
        return BaseResponse.ok();
    }

    /**
     * 根据ID获取多项目合同支付信息
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse getConPayMultiById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ConPayMulti payMulti = getById(id);
        if (ValidateUtil.isNull(payMulti)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        ConPayMultiResponse response = new ConPayMultiResponse();
        BeanUtils.copyProperties(payMulti, response);
        // 子表信息
        List<ConPayMultiProjectResponse> multiProjects = conPayMultiProjectService.getConPayMultiProjectByCpmId(id);
        response.setConPayMultiProject(multiProjects);
        List<ConPayMultiFunding> multiFundings = conPayMultiFundingService.list(Wrappers.<ConPayMultiFunding>lambdaQuery().eq(ConPayMultiFunding::getCpmId, id));
        response.setConPayMultiFunding(BeanUtils.copyList(multiFundings, ConPayMultiFundingResponse.class));
        return BaseResponse.ok(response);
    }

    /**
     * 多项目合同支付分页列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getConPayMultiPage(ConPayMultiRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        QueryMap<ConPayMultiRequest> map = new QueryMap<>(request);
        map.put("name").put("code").put("paymentType").put("paymentName").put("payeeUnit")
                .put("payeeUnitName").put("state").put("createUname");
        Page<ConPayMultiResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConPayMultiResponse> list = baseMapper.getContractMultiPage(page, map.getQuery());
        if (ValidateUtil.isEmpty(list)) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }
}
