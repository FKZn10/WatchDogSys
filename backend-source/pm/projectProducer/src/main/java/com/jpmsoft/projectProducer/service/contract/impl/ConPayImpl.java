package com.jpmsoft.projectProducer.service.contract.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.FlowStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.TaskProcessPrintResponse;
import com.jpmsoft.common.model.request.Contract.*;
import com.jpmsoft.common.model.request.pay.ConPayRequest;
import com.jpmsoft.common.model.requestAndResponse.Contract.*;
import com.jpmsoft.common.model.requestAndResponse.fund.ClaimIncomingFundsDtlDTO;
import com.jpmsoft.common.model.response.Contract.ConContractResponse;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.model.response.InvestmentControl.InvPlanYearResponse;
import com.jpmsoft.common.model.response.SKApi.SKApiBodyDataResponse;
import com.jpmsoft.common.model.response.SKApi.SKApiBodyDataT0004Response;
import com.jpmsoft.common.model.response.SKApi.SKApiCmscloudResponse;
import com.jpmsoft.common.model.response.SKApi.SKApiResponse;
import com.jpmsoft.common.model.response.pay.ConFundNoVo;
import com.jpmsoft.common.model.response.pay.ConPayIntervalResponse;
import com.jpmsoft.common.model.response.pay.ConPayPrintResponse;
import com.jpmsoft.common.model.response.pay.ConPayResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.AtTypeEnum;
import com.jpmsoft.projectProducer.constants.MarkStateEnum;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.enums.ConPayEnum;
import com.jpmsoft.projectProducer.enums.GlsTypeEnum;
import com.jpmsoft.projectProducer.feign.BpmFeign;
import com.jpmsoft.projectProducer.feign.SKApiFeign;
import com.jpmsoft.projectProducer.feign.UserProducerFeign;
import com.jpmsoft.projectProducer.mapper.contract.ConContractMapper;
import com.jpmsoft.projectProducer.mapper.contract.ConPayMapper;
import com.jpmsoft.projectProducer.mapper.contract.ConPayfundsDtlMapper;
import com.jpmsoft.projectProducer.mapper.fund.ClaimIncomingFundsDtlMapper;
import com.jpmsoft.projectProducer.mapper.fund.EngineeringFundApplicationMapper;
import com.jpmsoft.projectProducer.mapper.fund.PayAmountInitMapper;
import com.jpmsoft.projectProducer.mapper.fund.PaymentReceiptMapper;
import com.jpmsoft.projectProducer.mapper.fundPlan.ProgressDeclarationMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvCompleteMonthMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvPlanYearMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvSettlementApplyMapper;
import com.jpmsoft.projectProducer.mapper.project.PreProjectMapper;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskDto;
import com.jpmsoft.projectProducer.pojo.dto.base.BatchhandlingTaskListDto;
import com.jpmsoft.projectProducer.pojo.dto.contract.*;
import com.jpmsoft.projectProducer.pojo.entity.contract.*;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvCompleteMonth;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvProjectPlan;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvSettlementApply;
import com.jpmsoft.projectProducer.pojo.entity.other.SysCompany;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.entity.other.SysUser;
import com.jpmsoft.projectProducer.pojo.entity.performanceClaim.PcAwardTicket;
import com.jpmsoft.projectProducer.pojo.vo.contract.*;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.pojo.vo.sys.SysUserVo;
import com.jpmsoft.projectProducer.service.contract.*;
import com.jpmsoft.projectProducer.service.investment.IFundPlanModifyService;
import com.jpmsoft.projectProducer.service.investment.IInvBudgetDeclareDtlService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysCompanyService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.service.performanceClaim.IPcAwardTicketService;
import com.jpmsoft.projectProducer.utils.DateUtil;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import com.jpmsoft.projectProducer.utils.MathUtil;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.jpmsoft.projectProducer.service.fund.impl.IEngineeringFundApplicationServiceImpl.sendPost;
@Slf4j
@Service
public class ConPayImpl extends FileImpl<ConPayMapper, ConPay, ConPayRequest> implements IConPayService {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private IGuaranteeLetterSignService guaranteeLetterSignService;
    @Resource
    private IGuaranteeLetterBackService guaranteeLetterBackService;
    @Resource
    private IConFundNoService conFundNoService;
    @Resource
    private IConPayApplyFlowService conPayApplyFlowService;
    @Resource
    private IInvBudgetDeclareDtlService invBudgetDeclareDtlService;
    @Resource
    private IConContractService contractService;
    @Resource
    private IAgentFundAdvanceService agentFundAdvanceService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private IPcAwardTicketService pcAwardTicketService;
    @Resource
    private IConPayWitnessService conPayWitnessService;
    @Resource
    private IConMeasureService conMeasureService;
    @Resource
    private IFundPlanModifyService fundPlanModify;

    @Resource
    private UserProducerFeign userProducerFeign;
    @Resource
    private PreProjectMapper preProjectMapper;

    @Resource
    private ConContractMapper conContractMapper;

    @Resource
    private ISysCompanyService sysCompanyService;

    @Resource
    private PayAmountInitMapper payAmountInitMapper;

    @Resource
    private PaymentReceiptMapper paymentReceiptMapper;

    @Resource
    private RedisUtils redisUtils;
    @Resource
    private EngineeringFundApplicationMapper engineeringFundApplicationMapper;

    @Resource
    private InvPlanYearMapper invPlanYearMapper;

    @Resource
    private InvCompleteMonthMapper invCompleteMonthMapper;

    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IConPayMeasureService conPayMeasureService;

    @Resource
    private IConConsortiumDtlService conConsortiumDtlService;

    @Resource
    private ProgressDeclarationMapper progressDeclarationMapper;

    @Resource
    private IConPayfundsDtlService conPayfundsDtlService;

    @Resource
    private BpmFeign bpmFeign;
    @Autowired
    private ConPayfundsDtlMapper conPayfundsDtlMapper;
    @Resource
    private IPayChangeAssociationService payChangeAssociationService;
    @Resource
    private SKApiFeign skApiFeign;

    @Resource
    private InvSettlementApplyMapper invSettlementApplyMapper;
    @Resource
    private ClaimIncomingFundsDtlMapper claimIncomingFundsDtlMapper;

    @Resource
    private IMarketInvestmentPaymentService  marketInvestmentPaymentService;

    @Resource
    private IConPayPathDtlService conPayPathDtlService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    //@CodeGeneration(key = "payId", codeParam = "payCode", method = "setPayCode", codeRule = "ConPay", beanClass = ConPayRequest.class)
    public BaseResponse createOrUpdateConPay(ConPayRequest request) {
        //region 编码规则 合同支付 与项目支付走不同的编码规则
        String codeRule = null;
        String type = "";
        if (ValidateUtil.isEmpty(request.getPayId())) {
            if (ValidateUtil.isNotEmpty(request.getPayCategory()) && request.getPayCategory().equals("02"))
                //项目费用支付
                codeRule = "ProjectPay";
            else//合同支付
                codeRule = "ConPay";
            //编码生成
            if (ValidateUtil.isNotEmpty(codeRule) && apiUtilsService.sequenceRestEnable(codeRule)) {
                request.setPayCode(apiUtilsService.generateCodeByCustomized(codeRule, request.getPId(), request.getConId(), request.getSecId(), request));
                if (ValidateUtil.isEmpty(request.getPayCode())) throw new JPMException(ResMsg.codeGenerError);
            } else codeRule = null;
            type = "Create";
        } else {
            type = "Edit";
        }
        //endregion
        //region 流程表单创建
        ConPay entity = new ConPay();
        if (ValidateUtil.isEmpty(request.getPayCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);

        request.setContractPayType("1");
        if (ValidateUtil.isEmpty(request.getpId())) {
            request.setpId(request.getPId());
        }
        createOrUpdateFileEntity(entity, request);
//        payFieldDtlService.createOrUpdatePayFieldDtl(request.getPayFieldDtls(), entity);
        conFundNoService.createOrUpdateConFundNo(request.getConFundNos(), entity.getPayId());
        pcAwardTicketService.refreshConPayId(request.getAtIds(), entity.getPayId());
        conPayWitnessService.createOrUpdate(request.getConPayWitnesses(), entity.getPayId());
        conPayMeasureService.createOrUpdate(request.getMeaIds(), entity.getConId(), entity.getPayId());


        if ("Create".equals(type)) {
//            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPayMentRequestFile(), request.getPayMentCertificateFile(), request.getPayAuditOpinionFile(), request.getPayCollectionStatementFile(), request.getPayFinalPaymentStatementFile(), request.getPayNoticeOfAwardFile(), request.getPayContractFile(),request.getPayPromiseFile()), entity.getPayId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getWarrantyPaymentFile(), request.getWarrantyApplicationFile(), request.getWarrantyRefundFile()), entity.getPayId());
        } else if ("Edit".equals(type)) {
            //流程撤回时，可能会修改项目名
            SysFile sysFile = new SysFile();
            sysFile.setFileid(request.getPayId());
            sysFile.setFileproject(request.getPId());
            sysFileService.updatePId(sysFile);
//            sysFileService.update(sysFile,new LambdaQueryWrapper<>());
//            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPayMentRequestFile(), request.getPayMentCertificateFile(), request.getPayAuditOpinionFile(), request.getPayCollectionStatementFile(), request.getPayFinalPaymentStatementFile(), request.getPayNoticeOfAwardFile(), request.getPayContractFile(),request.getPayPromiseFile()),
//                    Arrays.asList(request.getFileIds(), request.getPayMentRequestFileIds(), request.getPayMentCertificateFileIds(), request.getPayAuditOpinionFileIds(), request.getPayCollectionStatementFileIds(), request.getPayFinalPaymentStatementFileIds(), request.getPayNoticeOfAwardFileIds(), request.getPayContractFileIds(),request.getPayPromiseFileIds()), entity.getPayId());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getWarrantyPaymentFile(), request.getWarrantyApplicationFile(), request.getWarrantyRefundFile()),
                    Arrays.asList(request.getWarrantyPaymentFileId(), request.getWarrantyApplicationFileId(), request.getWarrantyRefundFileId()), entity.getPayId());
        }
        //endregion
        //region 刷新编码序号索引
        if (ValidateUtil.isNotEmpty(codeRule))
            apiUtilsService.refershAutoNoByCustomized(codeRule, request.getPId(), request.getConId(), request.getSecId(), request);
        //endregion
        if (ValidateUtil.isNotEmpty(request.getPayChangeAssociationList())) {
            request.setPayId(entity.getPayId());
            payChangeAssociationService.createPayChangeAssociationList(request);
        }
        if (ValidateUtil.isNotEmpty(request.getConPayPathDtls())) {
            conPayPathDtlService.createOrUpdateConPayPathDtl(request.getConPayPathDtls(), entity.getPayId());
        }
        return BaseResponse.ok(entity.getPayId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConPay(ConPayRequest request, Boolean skipApproval) {
        //region 编码规则 合同支付 与项目支付走不同的编码规则
        String codeRule = null;
        String type = "";
        if (ValidateUtil.isEmpty(request.getPayId())) {
            if (ValidateUtil.isNotEmpty(request.getPayCategory()) && request.getPayCategory().equals("02"))
                //项目费用支付
                codeRule = "ProjectPay";
            else//合同支付
                codeRule = "ConPay";
            //编码生成
            if (ValidateUtil.isNotEmpty(codeRule) && apiUtilsService.sequenceRestEnable(codeRule)) {
                request.setPayCode(apiUtilsService.generateCodeByCustomized(codeRule, request.getPId(), request.getConId(), request.getSecId(), request));
                if (ValidateUtil.isEmpty(request.getPayCode())) throw new JPMException(ResMsg.codeGenerError);
            } else codeRule = null;
            type = "Create";
        } else {
            type = "Edit";
        }
        //endregion
        //region 流程表单创建
        ConPay entity = new ConPay();
        if (ValidateUtil.isEmpty(request.getPayCode()))
            return BaseResponse.fail(ResMsg.codeIsEmpty);

        request.setPayId(ValidateUtil.isEmpty(request.getPayId()) ? GenerateUUID.getUUID() : request.getPayId());
        BeanUtils.copyProperties(request, entity);
        this.saveOrUpdate(entity);
//        payFieldDtlService.createOrUpdatePayFieldDtl(request.getPayFieldDtls(), entity);
        conFundNoService.createOrUpdateConFundNo(request.getConFundNos(), entity.getPayId());
        pcAwardTicketService.refreshConPayId(request.getAtIds(), entity.getPayId());
        conPayWitnessService.createOrUpdate(request.getConPayWitnesses(), entity.getPayId());
        conPayMeasureService.createOrUpdate(request.getMeaIds(), entity.getConId(), entity.getPayId());

        if ("Create".equals(type)) {
            sysFileService.saveFile(request.getPayId(), request.gettId(), String.valueOf(entity.getPayState()),
                    entity.getPayCreateUid(), entity.getPayCreateUname(), entity.getPayCreateTime(),
                    request.getPId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), request.getSecId(), request.gettCode(), request.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPayMentRequestFile(), request.getPayMentCertificateFile(), request.getPayAuditOpinionFile(), request.getPayCollectionStatementFile(), request.getPayFinalPaymentStatementFile(), request.getPayNoticeOfAwardFile(), request.getPayContractFile(), request.getPayPromiseFile()), entity.getPayId());
        } else if ("Edit".equals(type)) {
            //流程撤回时，可能会修改项目名
            SysFile sysFile = new SysFile();
            sysFile.setFileid(request.getPayId());
            sysFile.setFileproject(request.getPId());
            sysFileService.updatePId(sysFile);
            // sysFileService.update(sysFile,new LambdaQueryWrapper<>());
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getPayMentRequestFile(), request.getPayMentCertificateFile(), request.getPayAuditOpinionFile(), request.getPayCollectionStatementFile(), request.getPayFinalPaymentStatementFile(), request.getPayNoticeOfAwardFile(), request.getPayContractFile(), request.getPayPromiseFile()),
                    Arrays.asList(request.getFileIds(), request.getPayMentRequestFileIds(), request.getPayMentCertificateFileIds(), request.getPayAuditOpinionFileIds(), request.getPayCollectionStatementFileIds(), request.getPayFinalPaymentStatementFileIds(), request.getPayNoticeOfAwardFileIds(), request.getPayContractFileIds(), request.getPayPromiseFileIds()), entity.getPayId());
        }
        //endregion
        //region 刷新编码序号索引
        if (ValidateUtil.isNotEmpty(codeRule))
            apiUtilsService.refershAutoNoByCustomized(codeRule, request.getPId(), request.getConId(), request.getSecId(), request);
        //endregion
        return BaseResponse.ok(entity.getPayId());
    }

    @Override
    public BaseResponse updateConPayInFlow(ConPayRequest request) {
        ConPay entity = new ConPay();
        BeanUtils.copyProperties(request, entity);
        ConPay model = baseMapper.selectById(request.getPayId());
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (JudgeUtil.nonModifyState(model.getPayState()))
            return BaseResponse.fail(ResMsg.stateNotModify);
        entity.setPayCreateTime(model.getPayCreateTime());
        entity.setPayCreateUid(model.getPayCreateUid());
        entity.setPayCreateUname(model.getPayCreateUname());
        entity.setPayState(model.getPayState());
        entity.setPayStateTime(model.getPayStateTime());
        baseMapper.updateById(entity);
        return BaseResponse.ok(entity.getPayId());
    }

    @Override
    public BaseResponse getConPayFundStatistics(ConPayFundStatisticsDTO request) {
        if (ValidateUtil.isEmpty(request.getConId())) return BaseResponse.fail(ResMsg.notReqParam);
        QueryMap<ConPayFundStatisticsDTO> queryMap = new QueryMap<>(request);
        queryMap.put("conId");
        IPage<ConPayFundStatisticsVO> page = new Page<>(request.getPage(), request.getSize());
        List<ConPayFundStatisticsVO> data = baseMapper.getConPayFundStatistics(page, queryMap.getQuery());
        ConPayFundStatisticsSumVO sum;
        if (CollectionUtils.isEmpty(data)) sum = new ConPayFundStatisticsSumVO();
        else sum = baseMapper.getConPayFundStatisticsSum(queryMap.getQuery());
        sum.setData(data);
        return BaseResponse.ok(sum, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public ConPay getLastApproveConPayByConId(String conId, String payType) {
        QueryWrapper<ConPay> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        wrapper.eq("Pay_Type", payType);
        wrapper.eq("Pay_State", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper.orderByDesc("Pay_State_Time");
        wrapper.last("limit 1");
        return baseMapper.selectOne(wrapper);
    }

    @Override
    public BaseResponse canToCreateConPay(String conId) {
        return BaseResponse.ok(canToCreateOrUpdateConPay(conId, null));
    }

    @Override
    public BaseResponse getConPayList(ConPayRequest request, String uId) {
        QueryMap<ConPayRequest> params = getListFilter(request, uId);
        if (params == null) return BaseResponse.ok();
        IPage<ConPayResponse> page = new Page<>(request.getPage(), request.getSize());


        BaseResponse response = this.userProducerFeign.GetConfigValueByCode("filterProjectLists");
        Object data = response.getData();
        List<String> codeValue = null;
        if (Objects.nonNull(data) && Objects.nonNull(response.getData())) {
            codeValue = Arrays.asList(data.toString().split(","));
        }

        List<ConPayResponse> datas = baseMapper.getConPayList(page, params.getQuery(), codeValue);
//        if (!CollectionUtils.isEmpty(datas)) {
//            //增加判断 -----是否进行过滤
//            if ("1".equals(request.getPayIsFilter()))
//            {
//                BaseResponse response = this.userProducerFeign.GetConfigValueByCode("filterProjectLists");
//                Object data = response.getData();
//                //判断是否包含特殊项目
//                if("1".equals(request.getPayIsContain()))
//                {
//                    //包含
//                    //获取配置得的过滤项目
//                    if (Objects.nonNull(data) && Objects.nonNull(response.getData())) {
//                        List<String> codeValue = Arrays.asList(data.toString().split(","));
//                        datas = datas.stream().filter(n -> !ValidateUtil.isEmpty(n.getpId()) && codeValue.contains(n.getpId())).collect(Collectors.toList());
//
//                    }
//                }
//                else
//                {
//                    //不包含
//                    //获取配置得的过滤项目
//                    if (Objects.nonNull(data) && Objects.nonNull(response.getData())) {
//                        List<String> codeValue = Arrays.asList(data.toString().split(","));
//                        datas = datas.stream().filter(n -> !ValidateUtil.isEmpty(n.getpId()) && !codeValue.contains(n.getpId())).collect(Collectors.toList());
//
//                    }
//                }
//            }
        List<String> payIds = datas.stream().map(ConPayResponse::getPayId).collect(Collectors.toList());
/*        if (CollectionUtil.isNotEmpty(payIds)){
            QueryWrapper<ConPayApplyFlow> queryWrapper = new QueryWrapper<>();
            queryWrapper.select("DISTINCT pay_id"); // 只需要判断存在性
            queryWrapper.in("pay_id", payIds);
            queryWrapper.and(wrapper ->
                    wrapper.ne("state", 7)
                            .ne("state", 3)
            );
            List<Object> validPayIds = conPayApplyFlowService.listObjs(queryWrapper);
            datas.forEach(d ->
                    d.setCanPush(validPayIds.contains(d.getPayId()))
            );
        }*/
        if (CollectionUtil.isNotEmpty(payIds)) {
            LambdaQueryWrapper<ConPayApplyFlow> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.select(ConPayApplyFlow::getPayId)
                    .in(ConPayApplyFlow::getPayId, payIds)
                    .ne(ConPayApplyFlow::getState, 7)
                    .ne(ConPayApplyFlow::getState, 3);

            List<Object> validPayIds = conPayApplyFlowService.listObjs(queryWrapper);

            datas.forEach(d ->
                    d.setCanPush(validPayIds.contains(d.getPayId()))
            );
        }
        List<ConFundNoVo> cfds = conFundNoService.getConFundByConIds(payIds);
        if (!CollectionUtils.isEmpty(cfds))
            datas.forEach(d -> {
                d.setConFundNos(cfds.stream().filter(f -> f.getPayId().equals(d.getPayId())).collect(Collectors.toList()));
            });
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public Workbook exportConPayList(ConPayRequest request, String uId) {
        QueryMap<ConPayRequest> params = getListFilter(request, uId);
        List<ConPayResponse> datas = new ArrayList<>();
        if (params != null) {
            BaseResponse response = this.userProducerFeign.GetConfigValueByCode("filterProjectLists");
            Object data = response.getData();
            List<String> projectIds = null;
            if (Objects.nonNull(data)) {
                projectIds = Arrays.asList(data.toString().split(","));
            }

            // 单次查询全量数据，不分页（复杂 authSql + 无索引排序导致每条查询都慢，分页会乘 N 倍）
            datas = baseMapper.getConPayListForExport(params.getQuery(), projectIds);

            // 批量查询已支付金额汇总
            if (!datas.isEmpty()) {
                List<String> payIds = datas.stream().map(ConPayResponse::getPayId).collect(Collectors.toList());
                Map<String, BigDecimal> sumMap = conPayApplyFlowService.list(
                        new LambdaQueryWrapper<ConPayApplyFlow>()
                                .select(ConPayApplyFlow::getPayId, ConPayApplyFlow::getCpafApplication)
                                .in(ConPayApplyFlow::getPayId, payIds)
                                .eq(ConPayApplyFlow::getState, 7)
                ).stream().collect(Collectors.groupingBy(ConPayApplyFlow::getPayId,
                        Collectors.reducing(BigDecimal.ZERO, ConPayApplyFlow::getCpafApplication, BigDecimal::add)));
                datas.forEach(d -> d.setCpafApplicationSum(sumMap.get(d.getPayId())));
            }
        }
        return buildConPayExportWorkbook(datas);
    }

    private Workbook buildConPayExportWorkbook(List<ConPayResponse> datas) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("合同支付");
        String[] titles = new String[]{
                "序号", "项目名称", "合同名称", "乙方单位", "支付类型",
                "本期核准金额（元）", "支付状态", "创建时间", "创建人", "流程状态"
        };

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        CellStyle bodyStyle = workbook.createCellStyle();
        bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        bodyStyle.setWrapText(true);

        CellStyle amountStyle = workbook.createCellStyle();
        amountStyle.cloneStyleFrom(bodyStyle);
        amountStyle.setAlignment(HorizontalAlignment.CENTER);
        amountStyle.setDataFormat(workbook.createDataFormat().getFormat("#,##0.00"));

        Row header = sheet.createRow(0);
        for (int i = 0; i < titles.length; i++) {
            Cell cell = header.createCell(i);
            cell.setCellValue(titles[i]);
            cell.setCellStyle(headerStyle);
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < datas.size(); i++) {
            ConPayResponse data = datas.get(i);
            Row row = sheet.createRow(i + 1);
            setCell(row, 0, i + 1, bodyStyle);
            setCell(row, 1, data.getPName(), bodyStyle);
            setCell(row, 2, data.getConName(), bodyStyle);
            setCell(row, 3, data.getConPartyBName(), bodyStyle);
            String payType = ConPayEnum.getName(data.getPayType());
            setCell(row, 4, payType == null ? data.getPayType() : payType, bodyStyle);

            Cell amountCell = row.createCell(5);
            BigDecimal payApproval = data.getPayApproval() != null ? data.getPayApproval() : data.getPayApply();
            if (payApproval != null) {
                amountCell.setCellValue(payApproval.doubleValue());
            }
            amountCell.setCellStyle(amountStyle);

            setCell(row, 6, getPaymentStatus(data), bodyStyle);
            setCell(row, 7, data.getPayCreateTime() == null ? "" : dateFormat.format(data.getPayCreateTime()), bodyStyle);
            setCell(row, 8, data.getPayCreateUname(), bodyStyle);
            String flowStatus = data.getPayState() == null ? "" : ApprovalStatusEnum.getName(data.getPayState());
            setCell(row, 9, flowStatus == null ? "" : flowStatus.replace("流程", ""), bodyStyle);
        }

        int[] widths = new int[]{8, 30, 35, 25, 12, 18, 15, 15, 15, 15};
        for (int i = 0; i < widths.length; i++) {
            sheet.setColumnWidth(i, widths[i] * 256);
        }
        sheet.createFreezePane(0, 1);
        return workbook;
    }

    private String getPaymentStatus(ConPayResponse data) {
        BigDecimal paid = data.getCpafApplicationSum() == null ? BigDecimal.ZERO : data.getCpafApplicationSum();
        BigDecimal approved = data.getPayApproval() != null ? data.getPayApproval()
                : (data.getPayApply() != null ? data.getPayApply() : BigDecimal.ZERO);
        BigDecimal rate = BigDecimal.ZERO;
        if (approved.compareTo(BigDecimal.ZERO) > 0) {
            rate = paid.multiply(BigDecimal.valueOf(100))
                    .divide(approved, 2, RoundingMode.HALF_UP)
                    .stripTrailingZeros();
        }
        return "已支付" + rate.toPlainString() + "%";
    }

    private void setCell(Row row, int index, Object value, CellStyle style) {
        Cell cell = row.createCell(index);
        if (value instanceof Number) {
            cell.setCellValue(((Number) value).doubleValue());
        } else {
            cell.setCellValue(value == null ? "" : value.toString());
        }
        cell.setCellStyle(style);
    }

    @Override
    public BaseResponse getConPayStandingBook(ConPayRequest request, String uId) {
        QueryMap<ConPayRequest> params = getListFilter(request, uId);
        if (params == null) return BaseResponse.ok();
        ConPayStandingBookVo data = baseMapper.getConPayStandingBook(params.getQuery());
        IPage<ConPayResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConPayResponse> datas = baseMapper.getConPayList(page, params.getQuery(), new ArrayList<>());
        if (ValidateUtil.isNull(data)) data = new ConPayStandingBookVo();
        data.setData(datas);
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConPayGroupByContract(ConPayRequest request, String uId) {
        QueryMap<ConPayRequest> params = getListFilter(request, uId);
        if (params == null) return BaseResponse.ok();
        ConPayGroupByContractVo data = baseMapper.getConPayGroupByContractSum(params.getQuery());
        IPage<ConPayResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConPayResponse> datas = baseMapper.getConPayGroupByContract(page, params.getQuery());
        if (ValidateUtil.isNull(data)) data = new ConPayGroupByContractVo();
        data.setData(datas);
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse getConPayFundAllocation(ConPayRequest request, String uId) {
        List<PayFundAllocationDTO> data = baseMapper.getConPayFundAllocation(request);
        data.forEach(d -> {
            if (d.getFadActualPaymentSum().compareTo(BigDecimal.ZERO) > 0) d.setFirstAllocation(Boolean.TRUE);

        });
        return BaseResponse.ok(data);
    }

    private QueryMap<ConPayRequest> getListFilter(ConPayRequest request, String uId) {
        String authSql = sysUserService.onlyCreateorAndApprover("Con_Pay", uId, "Pay_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Pay_State", "Pay_Id");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        QueryMap<ConPayRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("conName").put("pName").put("conType").put("conPartyBName")
                .put("payNumber").put("payType").put("payApply").put("payCreateUname").put("payIsWages")
                .put("payState").put("dId").put("payCode").put("pId").put("conId").put("thisProject").put("payTypeDetailed")
                .put("payApplicantName").put("payPayeeName").put("payDraweeName").put("payPayeeCa").put("ctdId")
                .put("payPayeeCaName").put("payPayeeBank").put("payRemark").put("payCategory").put("ctdCode").put("paymentPaths")
                .put("payExpenseType").put("payApplicationDepartmentId").put("conCode").put("payFaCode").put("queryCpafApplicationSum")
                .put(request.getPayNumbers(), "payNumberStart", "payNumberEnd").put("conPartyAId").put("conPartyAName")
                .put(request.getPayApplys(), "payApplyStart", "payApplyEnd").put("conDepName").put("conDepartment")
                .put(request.getPayCreateTimes(), "conCreateTimeStart", "conCreateTimeEnd")
                .put(request.getConMoneyTotals(), "conMoneyTotalStart", "conMoneyTotalEnd")
                .put(request.getPayApplicationDates(), "payApplicationDateStart", "payApplicationDateEnd")
                .put("payIsFilter").put("payIsContain").put("payNumberStr").put("contractName").put("projectName").put("payYear").put("payMonth")
                .put("payCategoryPayment").put("fnCode").put("paymentPath").put("payApplicationType").put("secId");
        if (ValidateUtil.isNotEmpty(request.getBddId())) {
            List<String> bddIds = invBudgetDeclareDtlService.getAllChildIdByBddId(request.getBddId());
            if (CollectionUtils.isEmpty(bddIds)) return null;
            params.put("bddIds", bddIds);
        }
        //城建外部用户只能看到各自发起/审批的数据
        Boolean isType = false;
        if (!ValidateUtil.isEmpty(request.getWhether()) && request.getWhether().equals("1")) {
            params.put("isType", isType);
            SysUser user = sysUserService.getById(uId);
            if (user.getUType().equals("2") || user.getUType().equals("02")) {
                isType = true;
                params.put("isType", isType);
                params.put("uId", uId);
            }
        } else {
            params.put("isType", isType);
            params.put("uId", uId);
        }

        return params;
    }

    @Override
    public BaseResponse getConPayBypayId(String payId) {
        ConPayResponse data = baseMapper.getConPayBypayId(payId);
        // 初始化所有字段为0
        BigDecimal payTotalLastSum = BigDecimal.ZERO; // 已支付
        BigDecimal payFarmersTotalSum = BigDecimal.ZERO; // 工人工资累计已支付金额
        Integer payNumberSum = 0; // 付款总期数
        BigDecimal payWorkAmountTotalSum = BigDecimal.ZERO; // 工程款累计已支付金额
        BigDecimal payDeductScThisSum = BigDecimal.ZERO; // 安全文明措施费累计支付金额
        BigDecimal payChangePayTotalThisSum = BigDecimal.ZERO; // 变更签证累计已支付金额
        BigDecimal payApplyTotal = BigDecimal.ZERO; // 本次
        Float payTotalLastP = 0.0f; // 已支付占合同价比例
        Float payProportion = 0.0f; // 签证金额占暂列金比例
        BigDecimal payAomount = BigDecimal.ZERO;
        BigDecimal payFarmersExpenditure = BigDecimal.ZERO;

        BigDecimal payFarmersTotal = BigDecimal.ZERO;
        BigDecimal payWorkAmountTotal = BigDecimal.ZERO;
        if (data != null) {
            // 初始化所有字段为0
            data.setPayTotalLast(data.getPayTotalLast() != null ? data.getPayTotalLast() : BigDecimal.ZERO); // 已支付
            data.setPayFarmersTotal(data.getPayFarmersTotal() != null ? data.getPayFarmersTotal() : BigDecimal.ZERO); // 工人工资累计已支付金额
            data.setPayNumberSum(data.getPayNumberSum() != null ? data.getPayNumberSum() : 0); // 付款总期数
            data.setPayWorkAmountTotal(data.getPayWorkAmountTotal() != null ? data.getPayWorkAmountTotal() : BigDecimal.ZERO); // 工程款累计已支付金额
            data.setPayDeductScThis(data.getPayDeductScThis() != null ? data.getPayDeductScThis() : BigDecimal.ZERO); // 安全文明措施费累计支付金额
            data.setPayChangePayTotalThis(data.getPayChangePayTotalThis() != null ? data.getPayChangePayTotalThis() : BigDecimal.ZERO); // 变更签证累计已支付金额
            data.setPayApply(data.getPayApply() != null ? data.getPayApply() : BigDecimal.ZERO); // 本次付款总金额
            data.setPayTotalP(data.getPayTotalP() != null ? data.getPayTotalP() : 0.0f); // 已支付占合同价比例
            data.setPayProportion(data.getPayProportion() != null ? data.getPayProportion() : 0.0f); // 签证金额占暂列金比例
            data.setPayAomount(data.getPayAomount() != null ? data.getPayAomount() : BigDecimal.ZERO); // 工程款金额
            data.setPayFarmersExpenditure(data.getPayFarmersExpenditure() != null ? data.getPayFarmersExpenditure() : BigDecimal.ZERO); // 工人工资金额
            data.setPayFarmersTotal(data.getPayFarmersTotal() != null ? data.getPayFarmersTotal() : BigDecimal.ZERO); // 工人工资累计已支付金额
            data.setPayWorkAmountTotal(data.getPayWorkAmountTotal() != null ? data.getPayWorkAmountTotal() : BigDecimal.ZERO); // 工程款累计已支付金额
            data.setPayTotalLastP(data.getPayTotalLastP() != null ? data.getPayTotalLastP() : 0.0f);
            if (data.getConId() != null) {
                List<GuaranteeLetterSign> glss = guaranteeLetterSignService.getGuaranteeLetterSignByConId(data.getConId());
                List<GuaranteeLetterBack> glbs = guaranteeLetterBackService.getGuaranteeLetterBackByConId(data.getConId());
                setConPayGuaranteeInfo(glss, glbs, data, GlsTypeEnum.Performance.getkey());
                setConPayGuaranteeInfo(glss, glbs, data, GlsTypeEnum.Advance_Charge.getkey());
            }
//            data.setPayFieldDtls(payFieldDtlService.getPayFieldDtlByPayId(payId));
            if (!ValidateUtil.isEmpty(data.getConIdSup())) {
                String[] conIds = data.getConIdSup().split(",");
                QueryWrapper<ConContract> wrapper = new QueryWrapper<>();
                wrapper.in("Con_Id", conIds);
                List<ConContract> conList = contractService.list(wrapper);
                if (conList.size() > 0) {
                    List<Map<String, Object>> dataList = new ArrayList<>();
                    conList.forEach(n -> {
                        Map<String, Object> datas = new HashMap<>();
                        datas.put("conId", n.getConId());
                        datas.put("conName", n.getConName());
                        datas.put("conMoneyTotal", n.getConMoneyTotal());
                        datas.put("conSupIsChangeMoney", n.getConSupIsChangeMoney());
                        datas.put("conSupChangeMonType", n.getConSupChangeMonType());
                        datas.put("conSupChangeMoney", n.getConSupChangeMoney());
                        datas.put("conApprovalTime", n.getConApprovalTime());
                        datas.put("conType", n.getConType());
                        dataList.add(datas);
                    });
                    data.setConList(dataList);
                }
            }
            data.setPcAwardTickets(pcAwardTicketService.getPcAwardTicketByConPay(data.getPayId()));
            data.setConPayWitnesses(conPayWitnessService.getConPayWitnessByPayId(data.getPayId()));


            if (ValidateUtil.isNotNull(data.getCcdId())) {
                ConConsortiumDtl ccObj = conConsortiumDtlService.getById(data.getCcdId());
                if (ccObj != null) {
                    data.setCcdAmt(ccObj.getCcdAmt());
                    data.setCcdBusiness(ccObj.getCcdBusiness());
                    data.setCcdPlan(ccObj.getCcdPlan());
                    data.setCcdRemark(ccObj.getCcdRemark());
                }
            }

            data.setConPayPathDtls(conPayPathDtlService.getConPayPathDtlByPayId(data.getPayId()));
        }
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getAccumulatedFeesByCId(String cId, String payCategory) {
        Map<String, BigDecimal> data = (Map<String, BigDecimal>) baseMapper.getAccumulatedFeesByCId(cId, payCategory);
        return BaseResponse.ok(data);
    }

    private void setConPayGuaranteeInfo(List<GuaranteeLetterSign> glss, List<GuaranteeLetterBack> glbs, ConPayResponse data, String glsType) {
        List<GuaranteeLetterSign> pref = glss.stream().filter(n -> !ValidateUtil.isEmpty(n.getGlsType()) && n.getGlsType().equals(glsType)).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(pref)) {
            GuaranteeLetterSign prfGls = pref.stream().findFirst().get();
            if (glsType.equals(GlsTypeEnum.Performance.getkey()))
                data.setPerfValidity(prfGls.getGlsDueDate());
            else if (glsType.equals(GlsTypeEnum.Advance_Charge.getkey()))
                data.setPrePayValidity(prfGls.getGlsDueDate());
            List<GuaranteeLetterBack> prePays = glbs.stream().filter(n -> n.getGlsId().equals(prfGls.getGlsId())).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(prePays)) {
                if (glsType.equals(GlsTypeEnum.Performance.getkey()))
                    data.setPerfRefund(Boolean.TRUE);
                else if (glsType.equals(GlsTypeEnum.Advance_Charge.getkey()))
                    data.setPrePayRefund(Boolean.TRUE);
            }
        } else {
            if (glsType.equals(GlsTypeEnum.Performance.getkey()))
                data.setPerfRefund(Boolean.FALSE);
            else if (glsType.equals(GlsTypeEnum.Advance_Charge.getkey()))
                data.setPrePayRefund(Boolean.FALSE);
        }
    }

    @Override
    public BaseResponse getLastApprovedConPay(ConPayRequest request) {
        QueryMap<ConPayRequest> params = new QueryMap<>(request);
        params.put("conId").put("payTypeDetailed").put("payType");
        ConPayResponse datas = baseMapper.getLastApprovedConPay(params.getQuery());
        return BaseResponse.ok(datas);
    }

    @Override
    public BigDecimal getConPayWHByConId(ConPayRequest request) {
        BigDecimal total = baseMapper.getConPayWHByConId(request.getConId()) == null ? new BigDecimal(0.0) : baseMapper.getConPayWHByConId(request.getConId());

        return total;
    }

    @Override
    public BaseResponse getConPayStatisticsOverview(String teId) {
        Map<String, Object> datas = baseMapper.getConPayStatisticsOverview(teId);
        if (!datas.containsKey("payAmt"))
            datas.put("payAmt", 0);
        if (!datas.containsKey("payMonthAmt"))
            datas.put("payMonthAmt", 0);
        if (!datas.containsKey("planAmt"))
            datas.put("planAmt", 0);
        datas.put("completeRate", MathUtil.DecimalDivisionInt(datas.get("payAmt"), datas.get("planAmt")));
        return BaseResponse.ok(datas);
    }

    /**
     * 获取支付总览列表
     *
     * @param request
     * @param teId
     * @return
     */
    @Override
    public BaseResponse getConPayStatisticsOverviewList(ConPayRequest request, String teId) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<ConPayResponse> page = new Page<>(request.getPage(), request.getSize());
        request.setTeId(teId);
        List<ConPayResponse> data = baseMapper.getConPayStatisticsOverviewList(page, request);
        return BaseResponse.ok(data, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getConPayStatisticsGroupByDep(String teId) {
        return BaseResponse.ok(baseMapper.getConPayStatisticsGroupByDep(teId));
    }

    @Override
    public BaseResponse getConPayStatisticsByMonth(String teId) {
        List<Map<String, Object>> datas = baseMapper.getConPayStatisticsByMonth(teId);
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH) + 1;
        String[] payMonth = new String[month];
        BigDecimal[] payCount = new BigDecimal[month];
        if (datas != null && datas.size() > 1) {
            datas.forEach(data -> {
                if (data.containsKey("payMonth")) {
                    if (Integer.parseInt(data.get("payMonth").toString()) <= month)
                        payCount[Integer.parseInt(data.get("payMonth").toString()) - 1] = new BigDecimal(ValidateUtil.isEmpty(data.get("payCount")) ? "0" : data.get("payCount").toString());
                }
            });
        }

        for (int i = 0; i < month; i++) {
            payMonth[i] = i + 1 + "";
            if (payCount[i] == null)
                payCount[i] = new BigDecimal(0);
        }
        Map<String, Object> result = new HashMap<>();
        result.put("payMonth", payMonth);
        result.put("payCount", payCount);
        return BaseResponse.ok(result);
    }

    @Override
    public BaseResponse getConPayStatisticsRanking(Integer size, String teId) {
        List<Map<String, Object>> datas = baseMapper.getConPayStatisticsRanking(size, teId);
        return BaseResponse.ok(datas);
    }

    @Override
    public BaseResponse getPayCumulativeStatistics(ConPayRequest request) {
        if (ValidateUtil.isAnObjEmpty(request.getConId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        PayCumulativeStatisticsVO data = baseMapper.getPayCumulativeStatistics(request.getConId(), request.getPayType(), request.getTeId(), request.getWithoutDetailType());
        if (ValidateUtil.isNull(data)) data = new PayCumulativeStatisticsVO();
        if (ValidateUtil.isEmpty(request.getPayType())) return BaseResponse.ok(data);
        ConPay last = getLastApproveConPayByConId(request.getConId(), request.getPayType());
        ConMeasure mea = conMeasureService.getLastApproveMeasureByConId(request.getConId());
        if (mea != null) {
            data.setMeaCompleted(mea.getMeaCompleted());
            data.setMeaPeriodAmt(mea.getMeaPeriodAmt());
            data.setMeaCurrentlyAmt(mea.getMeaCurrentlyAmt());
        }
        if (last != null) {
            data.setPayThisChangeAmount(MathUtil.decSubtract(data.getPayThisChangeAmount(), data.getPayThisChangeAmountCount()));
            data.setPayBonus(MathUtil.decSubtract(data.getPayBonus(), last.getPayBonus()));
            data.setPayDeductFine(MathUtil.decSubtract(data.getPayDeductFine(), last.getPayDeductFine()));
        }
        //不计算扣金 提前return
        if (ValidateUtil.isNull(request.getPayDone())) return BaseResponse.ok(data);
        ConContract con = contractService.getById(request.getConId());
        BigDecimal hundred = new BigDecimal(100);
        con.setConAcBackStart(MathUtil.decimalDivide(con.getConAcBackStart(), hundred));
        con.setConAcBackEnd(MathUtil.decimalDivide(con.getConAcBackEnd(), hundred));
        if (ValidateUtil.isNotNull(con.getConProgressRatio()))
            con.setConProgressRatio(Float.valueOf(MathUtil.decimalDivide(new BigDecimal(String.valueOf(con.getConProgressRatio())), hundred).toString()));
        //region 计算预付款扣回金额
        if (ValidateUtil.isNotEmpty(con.getConAcBackWay())) {
            if (con.getConAcBackWay().equals("01")) {
                //region 按比例计算逻辑
                //累计支付
                BigDecimal bfPay = last == null ? BigDecimal.ZERO : last.getPayDoneTotal();
                BigDecimal payBl;//累计已支付比例
                if (bfPay.compareTo(BigDecimal.ZERO) == 0)//首次 累计完成比例0
                    payBl = BigDecimal.ZERO;
                else {
                    payBl = MathUtil.decimalDivide(bfPay, con.getConMoneyTotal());
                }
                //本次完成合同价格比例
                BigDecimal bcBl = MathUtil.decimalDivide(request.getPayDone(), con.getConMoneyTotal());
                //总完成合同价格比例
                BigDecimal zBl = MathUtil.decAdd(payBl, bcBl);
                //region 计算本次扣点比例
                BigDecimal kdBl = BigDecimal.ZERO;
                //首次支付进度款 且比例超出起扣点
                if (payBl.compareTo(BigDecimal.ZERO) == 0 && bcBl.compareTo(con.getConAcBackStart()) == 1) {
                    if (bcBl.compareTo(con.getConAcBackEnd()) == -1) {//小于扣结点
                        kdBl = MathUtil.decSubtract(bcBl, con.getConAcBackStart());
                    } else {//大于等于扣结点
                        kdBl = MathUtil.decSubtract(con.getConAcBackEnd(), con.getConAcBackStart());
                    }
                } else if (payBl.compareTo(BigDecimal.ZERO) == 1) //非首次支付
                {
                    if (payBl.compareTo(con.getConAcBackStart()) <= 0)//往期比例未达到起扣点
                    {
                        if (zBl.compareTo(con.getConAcBackStart()) >= 0) {
                            if (zBl.compareTo(con.getConAcBackEnd()) == -1) {//小于扣结点
                                kdBl = MathUtil.decSubtract(zBl, con.getConAcBackStart());
                            } else {//大于等于扣结点
                                kdBl = MathUtil.decSubtract(con.getConAcBackEnd(), con.getConAcBackStart());
                            }
                        }
                    } else {//往期比例已超过起扣点
                        if (zBl.compareTo(con.getConAcBackEnd()) == -1) //小于扣结点
                            kdBl = bcBl;
                        else
                            kdBl = MathUtil.decSubtract(con.getConAcBackEnd(), payBl);
                    }
                }
                //endregion
                //扣回金额
                BigDecimal khAmt = MathUtil.decMultiply(MathUtil.decMultiply(MathUtil.decMultiplyFloat(kdBl, con.getConAcBackRate()), con.getConAcMoney()), hundred);
                data.setPayDeductYfk(khAmt);
                //endregion
            } else {//按次数
                if (data.getBatchNo() >= con.getConAcBackStartNu()) {
                    //已扣期数
                    int bathNo = data.getBatchNo() - con.getConAcBackStartNu();
                    if (bathNo + 1 <= con.getConAcBackTimes() - 1)//未超出扣款次数
                    {
                        //每期应扣金额（每期均值）
                        BigDecimal jkAmt = MathUtil.decimalDivide(con.getConAcMoney(), new BigDecimal(con.getConAcBackTimes()));
                        //往期遗留金额（应扣未扣）
                        BigDecimal wqAmt = MathUtil.decSubtract(MathUtil.decMultiply(jkAmt, new BigDecimal(bathNo)), data.getPayDeductYfkLast());
                        //本期应该扣金额（均值+应扣未扣）
                        BigDecimal bqAmt = MathUtil.decAdd(jkAmt, wqAmt);
                        if (request.getPayDone().compareTo(bqAmt) >= 0)
                            data.setPayDeductYfk(bqAmt);
                        else data.setPayDeductYfk(request.getPayDone());
                    } else if (bathNo == con.getConAcBackTimes()) {//最后一次扣款 防止除不尽
                        data.setPayDeductYfk(MathUtil.decSubtract(con.getConAcMoney(), data.getPayDeductYfkLast()));
                    }
                }
            }
        }
        //endregion
        //region 计算安全文明措施费扣回金额
        if (ValidateUtil.isNotEmpty(con.getConScBackWay())) {
            if (con.getConScBackWay().equals("01")) {
                //region 按比例计算逻辑
                //累计支付
                BigDecimal bfPay = last == null ? BigDecimal.ZERO : last.getPayDoneTotal();
                BigDecimal payBl;//累计已支付比例
                if (bfPay.compareTo(BigDecimal.ZERO) == 0)//首次 累计完成比例0
                    payBl = BigDecimal.ZERO;
                else {
                    payBl = MathUtil.decimalDivide(bfPay, con.getConMoneyTotal());
                }
                //本次完成合同价格比例
                BigDecimal bcBl = MathUtil.decimalDivide(request.getPayDone(), con.getConMoneyTotal());
                //总完成合同价格比例
                BigDecimal zBl = MathUtil.decAdd(payBl, bcBl);
                //region 计算本次扣点比例
                BigDecimal kdBl = BigDecimal.ZERO;
                //首次支付安全文明措施费 且比例超出起扣点
                if (payBl.compareTo(BigDecimal.ZERO) == 0 && bcBl.compareTo(con.getConScBackStart()) == 1) {
                    if (bcBl.compareTo(con.getConScBackEnd()) == -1) {//小于扣结点
                        kdBl = MathUtil.decSubtract(bcBl, con.getConScBackStart());
                    } else {//大于等于扣结点
                        kdBl = MathUtil.decSubtract(con.getConScBackEnd(), con.getConScBackStart());
                    }
                } else if (payBl.compareTo(BigDecimal.ZERO) == 1) //非首次支付
                {
                    if (payBl.compareTo(con.getConScBackStart()) <= 0)//往期比例未达到起扣点
                    {
                        if (zBl.compareTo(con.getConScBackStart()) >= 0) {
                            if (zBl.compareTo(con.getConScBackEnd()) == -1) {//小于扣结点
                                kdBl = MathUtil.decSubtract(zBl, con.getConScBackStart());
                            } else {//大于等于扣结点
                                kdBl = MathUtil.decSubtract(con.getConScBackEnd(), con.getConScBackStart());
                            }
                        }
                    } else {//往期比例已超过起扣点
                        if (zBl.compareTo(con.getConScBackEnd()) == -1) //小于扣结点
                            kdBl = bcBl;
                        else
                            kdBl = MathUtil.decSubtract(con.getConScBackEnd(), payBl);
                    }
                }
                //endregion
                //扣回金额
                BigDecimal khAmt = MathUtil.decMultiply(MathUtil.decMultiply(MathUtil.decMultiplyFloat(kdBl, con.getConScBackRate()), con.getConScMoney()), hundred);
                data.setPayDeductSc(khAmt);
                //endregion
            } else {//按次数
                if (data.getBatchNo() >= con.getConScBackStartNu()) {
                    //已扣期数
                    int bathNo = data.getBatchNo() - con.getConScBackStartNu();
                    if (bathNo + 1 <= con.getConScBackTimes() - 1)//未超出扣款次数
                    {
                        //每期应扣金额（每期均值）
                        BigDecimal jkAmt = MathUtil.decimalDivide(con.getConScMoney(), new BigDecimal(con.getConScBackTimes()));
                        //往期遗留金额（应扣未扣）
                        BigDecimal wqAmt = MathUtil.decSubtract(MathUtil.decMultiply(jkAmt, new BigDecimal(bathNo)), data.getPayDeductScLast());
                        //本期应该扣金额（均值+应扣未扣）
                        BigDecimal bqAmt = MathUtil.decAdd(jkAmt, wqAmt);
                        if (request.getPayDone().compareTo(bqAmt) >= 0)
                            data.setPayDeductSc(bqAmt);
                        else data.setPayDeductSc(request.getPayDone());
                    } else if (bathNo == con.getConScBackTimes()) {//最后一次扣款 防止除不尽
                        data.setPayDeductSc(MathUtil.decSubtract(con.getConScMoney(), data.getPayDeductScLast()));
                    }
                }
            }
        }
        //endregion
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getPayCumulativeStatisticsCH(ConPayRequest request) {
        if (ValidateUtil.isAnObjEmpty(request.getConId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        PayCumulativeStatisticsVO data = baseMapper.getPayCumulativeStatisticsCH(request.getConId(), request.getPayType(), request.getTeId(), request.getPayTypeDetailed(), request.getPayPayee());
        if (ValidateUtil.isNull(data)) data = new PayCumulativeStatisticsVO();
        if (ValidateUtil.isEmpty(request.getPayType())) return BaseResponse.ok(data);
        ConPay last = getLastApproveConPayByConId(request.getConId(), request.getPayType());
        ConMeasure mea = conMeasureService.getLastApproveMeasureByConId(request.getConId());
        if (mea != null) {
            data.setMeaCompleted(mea.getMeaCompleted());
            data.setMeaPeriodAmt(mea.getMeaPeriodAmt());
            data.setMeaCurrentlyAmt(mea.getMeaCurrentlyAmt());
        }
        if (last != null) {
            data.setPayThisChangeAmount(MathUtil.decSubtract(data.getPayThisChangeAmount(), data.getPayThisChangeAmountCount()));
            data.setPayBonus(MathUtil.decSubtract(data.getPayBonus(), last.getPayBonus()));
            data.setPayDeductFine(MathUtil.decSubtract(data.getPayDeductFine(), last.getPayDeductFine()));
        }
        //不计算扣金
        return BaseResponse.ok(data);
    }

    @Override
    public BigDecimal getPayCountByProAndYear(String pId, String year) {
        BigDecimal data = baseMapper.getPayCountByProAndYear(pId, year);
        return data;
    }

    @Override
    public BaseResponse getConPayListByConId(ConPayRequest request) {
        if (ValidateUtil.isEmpty(request.getConId()))
            return BaseResponse.fail(ResMsg.notReqParam);
        QueryMap<ConPayRequest> params = new QueryMap<>(request);
        params.put("conId").put("conName").put("conType").put("conPartyBName").put("payNumber")
                .put("payType").put("payApply").put("payCreateUname").put("payState").put("dId")
                .put("payCode").put("payApplicantName").put("payPayeeName").put("payDraweeName")
                .put("payPayeeCa").put("payPayeeCaName").put("payPayeeBank").put("payRemark")
                .put("payCategory").put("payExpenseType").put("payIsWages");
        List<ConPayResponse> datas = baseMapper.getConPayListByConId(params.getQuery());
        Integer total = baseMapper.getConPayListByConIdCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse bjPushConPayData(String payId) {
        ConPayResponse data = baseMapper.getConPayBypayId(payId);
        //TODO:北京请款推送
        return null;
    }


    private Boolean canToCreateOrUpdateConPay(String conId, String payId) {
        QueryWrapper<ConPay> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        wrapper.ne("Pay_State", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper.ne("Pay_State", ApprovalStatusEnum.Invalid.getIndex());
        if (!ValidateUtil.isEmpty(payId))
            wrapper.ne("Pay_Id", payId);
        if (baseMapper.selectCount(wrapper) > 0)
            return false;
        else return true;
    }

    @Override
    public List<ConPayResponse> getConPayBypayNotConPayApply(String teId) {
        return baseMapper.getConPayBypayNotConPayApply(teId);
    }


    //获取“资金计划剩余额度”
    @Override
    public BaseResponse getConPayByPid(String pId) {
        QueryWrapper<ConPay> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        List<ConPay> conPayList = baseMapper.selectList(wrapper);
        if (conPayList.size() > 0) {
            conPayList = conPayList.stream().filter(n -> n.getPayStateTime() != null && n.getPayFundPlanSurplus() != null && n.getPayProjectPaymentSurplus() != null).collect(Collectors.toList());
        }
        return BaseResponse.ok(conPayList, conPayList.size());
    }


    //获取“截至本次本项目未支付额度”
    @Override
    public BaseResponse getPayProjectPaymentSurplus(String pId, String year) {
        //获取所有审核通过的支付信息
        QueryWrapper<ConPay> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        wrapper.eq("Pay_State", "7");
        wrapper.ne("pay_Application_Type", "01");
        List<ConPay> conPayList = baseMapper.selectList(wrapper);
        List<BigDecimal> conPayTotals = new ArrayList<>();
        if (conPayList.size() > 0) {
            conPayList.forEach(n -> {
                if (n.getPayStateTime() != null && DateUtil.getTimeYear(n.getPayStateTime()) == Integer.parseInt(year)) {
                    if (!ValidateUtil.isEmpty(n.getPayApplyOwner())) {
                        conPayTotals.add(n.getPayApplyOwner());
                    } else {
                        conPayTotals.add(n.getPayApply());
                    }
                }
            });
        }
        //获取所有的资金申请信息（）
        QueryWrapper<ConPayApplyFlow> conPayFlowWrapper = new QueryWrapper<>();
        conPayFlowWrapper.eq("P_Id", pId);
        conPayFlowWrapper.eq("State", "7");
        List<ConPayApplyFlow> ConPayApplyFlowList = conPayApplyFlowService.list(conPayFlowWrapper);
        List<BigDecimal> ActualPaymentTotals = new ArrayList<>();
        if (ConPayApplyFlowList.size() > 0) {
            ConPayApplyFlowList.forEach(n -> {
                if (n.getStateTime() != null && DateUtil.getTimeYear(n.getStateTime()) == Integer.parseInt(year)) {
                    ActualPaymentTotals.add(n.getCpafApplication());
                }
            });
        }

        //获取当前项目下所有有效的代建预备信息
        QueryWrapper<AgentFundAdvance> agentFundAdvanceWrapper = new QueryWrapper<>();
        agentFundAdvanceWrapper.eq("P_Id", pId);
        agentFundAdvanceWrapper.ne("Afa_State", "3");
        agentFundAdvanceWrapper.ne("Afa_State", "0");
        List<AgentFundAdvance> agentList = agentFundAdvanceService.list(agentFundAdvanceWrapper);
        List<AgentFundAdvance> PayOehalf = new ArrayList<>();
        List<BigDecimal> PayOehalfO = new ArrayList<>();
        if (agentList.size() > 0) {
            agentList.forEach(n -> {
                if (DateUtil.getTimeYear(n.getAfaStateTime()) == Integer.parseInt(year)) {
                    if (n.getAfaState() == 7) {
                        PayOehalfO.add(n.getAfaThisMoney());
                    } else if (!ValidateUtil.isEmpty(n.getAfaCapitalSurplus()) && !ValidateUtil.isEmpty(n.getAfaUnpaid())) {
                        PayOehalf.add(n);
                    }
                }
            });
        }

        LambdaQueryWrapper<MarketInvestmentPayment> query = Wrappers.lambdaQuery(MarketInvestmentPayment.class)
                .eq(MarketInvestmentPayment::getPId, pId)
                .notIn(MarketInvestmentPayment::getState, Arrays.asList(
                        ApprovalStatusEnum.Not_Started.getIndex(),
                        ApprovalStatusEnum.Invalid.getIndex()
                ));

        List<MarketInvestmentPayment> list = marketInvestmentPaymentService.list(query);

        BigDecimal marketInvestmentPaymentTotal = list.stream()
                .map(item -> item.getMipAppliedAmount() == null ? BigDecimal.ZERO : item.getMipAppliedAmount())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> datas = new HashMap<>();
        datas.put("conPayTotals", conPayTotals.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        datas.put("ActualPaymentTotal", ActualPaymentTotals.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        datas.put("PayOehalf", PayOehalf);
        datas.put("PayOehalfO", PayOehalfO.stream().reduce(BigDecimal.ZERO, BigDecimal::add));
        datas.put("marketInvestmentPaymentTotal", marketInvestmentPaymentTotal);
        return BaseResponse.ok(datas, 1);
    }

    //计算合同支付实际支付金额，并判断是否超年度计划金额
    @Override
    public BaseResponse contractPayCount(ConPayCountDto request) {
        String conId = request.getConId();//合同id
        Integer typeStr = request.getTypeStr();//支付类型
        String payId = request.getPayId();
        String pId = request.getpId();
        String year = request.getYear();
        BigDecimal payApplyNew = request.getPayApplyNew();
        QueryWrapper<ConPay> wrapper = new QueryWrapper<>();
        wrapper.eq("P_Id", pId);
        wrapper.ne("Pay_State", "3");


        BigDecimal advanceTotal = BigDecimal.ZERO;
        BigDecimal deductionTotal = BigDecimal.ZERO;
        BigDecimal payDoneTotalLast = BigDecimal.ZERO;
        // BigDecimal payPrepaymentBalance = BigDecimal.ZERO;
        List<ConPay> conPayList = baseMapper.selectList(wrapper);
        if (conPayList != null && conPayList.size() > 0) {

            List<ConPay> payApprovalList = new ArrayList<>();
            if (typeStr != null) {
                payApprovalList = conPayList.stream().filter(n -> ValidateUtil.isNotEmpty(n.getConId()) && n.getConId().equals(conId) && n.getPayState() != null && n.getPayState() == typeStr).collect(Collectors.toList());
            } else {
                payApprovalList = conPayList.stream().filter(n -> ValidateUtil.isNotEmpty(n.getConId()) && n.getConId().equals(conId)).collect(Collectors.toList());
            }

            for (ConPay item : payApprovalList
            ) {
                if (!item.getPayId().equals(payId)) {
                    if ("01".equals(item.getPayType()) && "0101".equals(item.getPayType()) && item.getPayState() != null && item.getPayState() == typeStr) {
                        //累计预付款信息
                        advanceTotal = advanceTotal.add(item.getPayApply());//累计预付款信息

                    } else if ("02".equals(item.getPayType()) && "0201".equals(item.getPayType()) && item.getPayState() != null && item.getPayState() != 3 && item.getPayState() != 0) {
                        deductionTotal = deductionTotal.add(item.getPayDeductYfkOwner());//预付款扣回
                        payDoneTotalLast = payDoneTotalLast.add(item.getPayDoneOwner());
                    }
                }
            }
        }
        // payPrepaymentBalance = advanceTotal.subtract(deductionTotal);//剩余未扣回预付款


        BigDecimal remainingAmount = BigDecimal.ZERO;
        //获取资金计划信息
        BaseResponse funPlanList = fundPlanModify.getFundPlanModifyBypPid(pId);
        Map<String, Object> data = (Map<String, Object>) funPlanList.getData();
        //   List<FundPlanModifyDto> funModifyList = (List<FundPlanModifyDto>) data.get("fundPlanModifyList");
        List<InvProjectPlan> invProjectPlanList = (List<InvProjectPlan>) data.get("invProjectPlanList");
        List<ConPayApplyFlow> ConPayApplyFlowList = (List<ConPayApplyFlow>) data.get("ConPayApplyFlowList");
        if (invProjectPlanList != null && invProjectPlanList.size() > 0) {
            invProjectPlanList = invProjectPlanList.stream().sorted(Comparator.comparing(InvProjectPlan::getIppCreateTime)).collect(Collectors.toList());
            List<InvProjectPlan> increaseList = invProjectPlanList.stream().filter(n -> ValidateUtil.isNotEmpty(n.getIppReleaseType()) && ("新增".equals(n.getIppReleaseType()) || "增加".equals(n.getIppReleaseType()))).collect(Collectors.toList());
            if (increaseList != null && increaseList.size() > 0) {
                for (InvProjectPlan item : increaseList
                ) {
                    BigDecimal reduceMoney = BigDecimal.ZERO;
                    List<InvProjectPlan> reduceList = invProjectPlanList.stream().filter(n -> ValidateUtil.isNotEmpty(n.getIppReleaseType()) && ("减少".equals(n.getIppReleaseType()) && "增加".equals(n.getIppReleaseType()))).collect(Collectors.toList());
                    if (reduceList != null && reduceList.size() > 0) {
                        reduceMoney = reduceList.stream().map(k -> k.getIppReleaseFunds()).reduce(BigDecimal.ZERO, BigDecimal::add);

                    }
                    BigDecimal CpafApplys = BigDecimal.ZERO;
                    if (ConPayApplyFlowList != null && ConPayApplyFlowList.size() > 0) {
                        for (ConPayApplyFlow atem : ConPayApplyFlowList) {
                            String newApplyDetails = atem.getCpafApplyDetails();
                            if (ValidateUtil.isNotEmpty(newApplyDetails)) {
                                List<Map<String, Object>> obj = JacksonUtils.parseObject(newApplyDetails, new TypeReference<List<Map<String, Object>>>() {
                                });
                                for (Map<String, Object> ite : obj
                                ) {

                                    String code = ite.get("code").toString();
                                    if (ValidateUtil.isNotEmpty(code)) {
                                        if (code.equals(item.getIppDocNumber())) {
                                            if (ite.get("cpafApplication") != null) {
                                                String application = ite.get("cpafApplication").toString();
                                                BigDecimal cpafApplication = new BigDecimal(application);
                                                CpafApplys = CpafApplys.add(cpafApplication);
                                            }
                                        }
                                    }
                                }


                            }
                        }
                    }
                    BigDecimal ippReleaseFunds = item.getIppReleaseFunds().subtract(reduceMoney);
                    remainingAmount = remainingAmount.add(ippReleaseFunds).subtract(CpafApplys);//累计剩余金额
                }
            }
        }


        ////截至本次本项目未支付额度
        BaseResponse payProject = getPayProjectPaymentSurplus(pId, year);
        Map<String, Object> dataPay = (Map<String, Object>) payProject.getData();
        BigDecimal conPayTotals = (BigDecimal) dataPay.get("conPayTotals");
        BigDecimal ActualPaymentTotal = (BigDecimal) dataPay.get("ActualPaymentTotal");
        BigDecimal PayOehalfO = (BigDecimal) dataPay.get("PayOehalfO");
        List<AgentFundAdvance> PayOehalf = (List<AgentFundAdvance>) dataPay.get("PayOehalf");
        BigDecimal PayOehalfTotal = BigDecimal.ZERO;

        BigDecimal cumulativeApplication = BigDecimal.ZERO;
        if (PayOehalf != null && PayOehalf.size() > 0) {
            PayOehalfTotal = PayOehalf.stream().map(k -> k.getAfaThisMoney()).reduce(BigDecimal.ZERO, BigDecimal::add);
        }

        if (conPayList != null && conPayList.size() > 0) {
            for (ConPay atem : conPayList) {
                if (atem.getPayState() != 0 && atem.getPayState() != 7 && atem.getPayState() != 2) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
                    String stateYear = sdf.format(atem.getPayStateTime());
                    if (stateYear.equals(year) && !atem.getPayId().equals(payId) && atem.getPayIsHistory() != null && "1".equals(atem.getPayIsHistory()) && atem.getPayFundPlanSurplus() != null && atem.getPayProjectPaymentSurplus() != null) {
                        if (atem.getPayApplyOwner() != null && atem.getPayApplyOwner().compareTo(BigDecimal.ZERO) > 0) {
                            cumulativeApplication = cumulativeApplication.add(atem.getPayApplyOwner());
                        } else {
                            cumulativeApplication = cumulativeApplication.add(atem.getPayApply());
                        }
                    }
                }
            }
        }
        if (conPayTotals == null) {
            conPayTotals = BigDecimal.ZERO;
        }
        if (ActualPaymentTotal == null) {
            ActualPaymentTotal = BigDecimal.ZERO;
        }
        if (PayOehalfTotal == null) {
            PayOehalfTotal = BigDecimal.ZERO;
        }
        if (PayOehalfO == null) {
            PayOehalfO = BigDecimal.ZERO;
        }
        if (cumulativeApplication == null) {
            cumulativeApplication = BigDecimal.ZERO;
        }
        BigDecimal payProjectPaymentSurplus = conPayTotals.subtract(ActualPaymentTotal).add(PayOehalfTotal).add(PayOehalfO).add(cumulativeApplication);
        BigDecimal PayTotal = payProjectPaymentSurplus.add(payApplyNew);
        Boolean fundingStatus = false;
        if (payProjectPaymentSurplus.compareTo(remainingAmount) <= 0) {
            fundingStatus = true;
        }
        Map<String, Object> datas = new HashMap<>();
        datas.put("PayTotal", PayTotal);
        datas.put("payProjectPaymentSurplus", payProjectPaymentSurplus);
        datas.put("conPayTotals", conPayTotals);
        datas.put("ActualPaymentTotal", ActualPaymentTotal);
        datas.put("PayOehalfTotal", PayOehalfTotal);
        datas.put("PayOehalfO", PayOehalfO);
        datas.put("remainingAmount", remainingAmount);
        datas.put("cumulativeApplication", cumulativeApplication);
        datas.put("fundingStatus", fundingStatus);
        return BaseResponse.ok(datas);

    }


    @Override
    public BigDecimal getPayDoneTotalByConId(String conId, String payType) {
        BigDecimal data = baseMapper.getPayDoneTotalByConId(conId, payType);
        return data;
    }

    @Override
    public BaseResponse getContractListByPIdAndConId(String pId, String conId) {
        return BaseResponse.ok(baseMapper.getContractListByPIdAndConId(pId, conId));
    }

    @Override
    public BaseResponse getPayDefaultTotalByPIdAndConId(String pId, String conId, String type) {
        return BaseResponse.ok(baseMapper.getPayDefaultTotalByPIdAndConId(pId, conId, type));
    }

    @Override
    public void flowSubmitCallback(String payId) {
        if (StrUtil.isBlank(payId)) {
            throw new JPMException("支付id不允许为空");
        }
        ConPay conPay = getById(payId);
        if (conPay == null) {
            throw new JPMException("未查询到支付信息");
        }
        if (FlowStatusEnum.Finish.getIndex().equals(conPay.getPayState())) {
            // 在审批结束后，绑定的所有合同违约数据，登记状态变更为已登记。
            pcAwardTicketService.update(new LambdaUpdateWrapper<PcAwardTicket>()
                    .eq(PcAwardTicket::getPayId, payId)
                    .eq(PcAwardTicket::getAtType, AtTypeEnum.PUNISH.getCode())
                    .eq(PcAwardTicket::getAtState, FlowStatusEnum.Finish.getIndex())
                    .set(PcAwardTicket::getMarkState, MarkStateEnum.REGISTERED.getCode()));

        } else if (Arrays.asList(FlowStatusEnum.SendBackStart.getIndex()
                        , FlowStatusEnum.Stop.getIndex(), FlowStatusEnum.SendBack.getIndex())
                .contains(conPay.getPayState())) {
            // 合同违约解绑支付
            pcAwardTicketService.update(new LambdaUpdateWrapper<PcAwardTicket>()
                    .eq(PcAwardTicket::getPayId, payId)
                    .eq(PcAwardTicket::getAtType, AtTypeEnum.PUNISH.getCode())
                    .set(PcAwardTicket::getPayId, null));
        }
    }

    @Override
    public BaseResponse contractPayListForCj(ContractPayListForCj request, String uId) {
        String authSql = getAuthSql(OutUserAuthEnum.Unit_Auth.getIndex(), uId);

        List<ContractPayListForCjModel> preProjectList = baseMapper.getcontractPayListForCj(request.getPName(), request.getPage(), request.getSize(), authSql);
        List<String> Pids = preProjectList.stream().map(ContractPayListForCjModel::getProjectId).distinct().collect(Collectors.toList());
        List<ContractPayListForCj> datas = Pids.stream().map(n -> {
            ContractPayListForCj model = new ContractPayListForCj();
            model.setPId(n);
            String pName = preProjectList.stream().filter(k -> n.equals(k.getProjectId()) && ValidateUtil.isNotNull(k.getPName())).map(ContractPayListForCjModel::getPName).findFirst().orElse(null);
            if (ValidateUtil.isNotNull(pName))
                model.setPName(pName);
            String PCompetentDepartment = preProjectList.stream().filter(k -> n.equals(k.getProjectId()) && ValidateUtil.isNotNull(k.getPCompetentDepartment())).map(ContractPayListForCjModel::getPCompetentDepartment).findFirst().orElse(null);
            if (ValidateUtil.isNotNull(PCompetentDepartment))
                model.setPCompetentDepartment(PCompetentDepartment);
            //查询合同数量
            List<String> conIds = preProjectList.stream().filter(k -> n.equals(k.getProjectId()) && ValidateUtil.isNotNull(k.getConId())).map(ContractPayListForCjModel::getConId).distinct().collect(Collectors.toList());

            model.setCount(conIds.size());
            BigDecimal amount = BigDecimal.ZERO;
            if (ValidateUtil.isNotNull(conIds)) {
                for (String item : conIds) {
                    BigDecimal ConMoneyTotal = preProjectList.stream().filter(k -> item.equals(k.getConId()) && ValidateUtil.isNotNull(k.getConMoneyTotal())).map(ContractPayListForCjModel::getConMoneyTotal).findFirst().orElse(null);
                    if (ValidateUtil.isNotNull(ConMoneyTotal))
                        amount = amount.add(ConMoneyTotal);
                }
            }
            model.setConTotalAmount(amount);
            //判断合同累计金额----建管、其他
            if ("7943b1b1-e2fb-4563-b485-4321146ecc39".equals(PCompetentDepartment)) {
                //该项目所有合同的【本期实际支付】的合计值
                BigDecimal payAmount = preProjectList.stream().filter(m -> ValidateUtil.isNotNull(m.getPayApproval()) && n.equals(m.getProjectId()))
                        .map(ContractPayListForCjModel::getPayApproval)
                        .reduce(BigDecimal.ZERO, NumberUtil::add);
                model.setPayTotalAmount(payAmount);
            } else {
                List<ContractPayListForCjModel> as = preProjectList.stream().filter(m -> n.equals(m.getProjectId())).collect(Collectors.toList());
                //该项目所有“已支付数据维护~本期已付款金额”的合计值。（填写合同名称的相关记录）
                BigDecimal payAmount = preProjectList.stream().filter(m -> ValidateUtil.isNotNull(m.getPayPayable()) && n.equals(m.getProjectId()))
                        .map(ContractPayListForCjModel::getPayPayable)
                        .reduce(BigDecimal.ZERO, NumberUtil::add);
                model.setPayTotalAmount(payAmount);
            }
            //计算比例
            if (!BigDecimal.ZERO.equals(model.getConTotalAmount())) {
                BigDecimal rate = BigDecimal.ZERO;
                try {
                    rate = NumberUtil.div(model.getPayTotalAmount(), model.getConTotalAmount(), 4).multiply(new BigDecimal(100));
                } catch (ArithmeticException exception) {
                    log.error("计算比例异常:" + JacksonUtils.toJSONString(exception) + ";payTotalAmount:" + model.getPayTotalAmount() + ";conTotalAmount:" + model.getConTotalAmount());
                }
                model.setPayTotalRate(rate);
            }
            return model;
        }).collect(Collectors.toList());

        return BaseResponse.ok(datas);
    }

    private String getAuthSql(Integer OutUserAuthEnum, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "P_Create_User_ID", OutUserAuthEnum, "State");
        if (ValidateUtil.isEmpty(authSql))
            throw new JPMException(ResMsg.noPermission);
        String code = "projectDateLimit";
        String str = sysConfigService.getConfigByCode(code);
        if (StringUtils.isNotEmpty(str)) {
            StringBuffer sb = new StringBuffer();
            sb.append(authSql);
            sb.append("and fpro.P_Create_Time >='" + str + "'");
            authSql = sb.toString();
        }
        return authSql;
    }

    @Override
    public BaseResponse contractPayListForCjByPId(ContractPayListForCjDTO request) {

        QueryWrapper<ConContract> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("Con_Approval_State", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper2.eq("Con_Project_Id", request.getPId());
        List<ConContract> contractList = conContractMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper2).getRecords();

        QueryWrapper<ConPay> wrapper3 = new QueryWrapper<>();
        wrapper3.eq("Pay_Category", "01");
        wrapper3.eq("Pay_State", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper3.eq("P_Id", request.getPId());
        List<ConPay> conPayList = baseMapper.selectList(wrapper3);

        List<SysCompany> sysCompanyList = sysCompanyService.list();

        List<ContractPayListForCjDTO> datas = contractList.stream().map(n -> {
            ContractPayListForCjDTO model = new ContractPayListForCjDTO();
            model.setPId(n.getConProjectId());
            model.setConId(n.getConId());
            model.setConName(n.getConName());
            model.setPCompetentDepartment(request.getPCompetentDepartment());
            // 获取乙方单位名称
            if (ValidateUtil.isNotNull(n.getConPartyBId())) {
                SysCompany company = sysCompanyList.stream().filter(k -> ValidateUtil.isNotNull(n.getConPartyBId()) && n.getConPartyBId().equals(k.getcId())).findFirst().orElse(null);
                if (ValidateUtil.isNotNull(company)) {
                    model.setConSecondCompanyName(company.getcName());
                }
            } else if (ValidateUtil.isNotNull(n.getConPartyB1()) && ValidateUtil.isEmpty(model.getConSecondCompanyName())) {
                SysCompany company = sysCompanyList.stream().filter(k -> ValidateUtil.isNotNull(n.getConPartyB1()) && n.getConPartyB1().equals(k.getcId())).findFirst().orElse(null);
                if (ValidateUtil.isNotNull(company)) {
                    model.setConSecondCompanyName(company.getcName());
                }
            }
            //判断合同累计金额----建管、其他
            if ("7943b1b1-e2fb-4563-b485-4321146ecc39".equals(request.getPCompetentDepartment())) {
                //该项目所有合同的【本期实际支付】的合计值
                List<ConPay> conPayList1 = conPayList.stream().filter(k -> n.getConId().equals(k.getConId())).collect(Collectors.toList());
                BigDecimal payAmount = conPayList1.stream().filter(m -> m.getPayApproval() != null)
                        .map(ConPay::getPayApproval)
                        .reduce(BigDecimal.ZERO, NumberUtil::add);
                model.setPayTotalAmount(payAmount);
            } else {
                //该项目所有“已支付数据维护~本期已付款金额”的合计值。（填写合同名称的相关记录）
                List<ConPay> conPayList1 = conPayList.stream().filter(k -> n.getConId().equals(k.getConId())).collect(Collectors.toList());
                BigDecimal payAmount = conPayList1.stream().filter(m -> m.getPayPayable() != null)
                        .map(ConPay::getPayPayable)
                        .reduce(BigDecimal.ZERO, NumberUtil::add);
                model.setPayTotalAmount(payAmount);
            }
            //计算比例
            if (!BigDecimal.ZERO.equals(n.getConMoneyTotal()) && ValidateUtil.isNotNull(n.getConMoneyTotal())) {
                if (n.getConMoneyTotal().intValue() != 0) {
                    BigDecimal rate = NumberUtil.div(model.getPayTotalAmount(), n.getConMoneyTotal(), 4).multiply(new BigDecimal(100));
                    model.setPayTotalRate(rate);
                }
            }
            model.setConAmount(n.getConMoneyTotal());
            return model;
        }).collect(Collectors.toList());

        return BaseResponse.ok(datas);
    }

    @Override
    public BaseResponse contractPayListForCjByConId(ContractPayListForCjSub request) {

        QueryWrapper<ConPay> wrapper = new QueryWrapper<>();
        wrapper.eq("Pay_State", ApprovalStatusEnum.Already_Over.getIndex());
        wrapper.eq("P_Id", request.getPId());
        wrapper.eq("Pay_Category", "01");
        wrapper.eq("Con_Id", request.getConId());
        wrapper.orderByAsc("Pay_State_Time");
        List<ConPay> payList = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper).getRecords();

        List<ContractPayListForCjSub> datas = payList.stream().map(n -> {
            ContractPayListForCjSub model = new ContractPayListForCjSub();
            model.setPId(n.getPId());
            model.setConId(n.getConId());
            model.setPayId(n.getPayId());
            model.setPayDate(n.getPayStateTime());
            //判断合同累计金额----建管、其他
            if ("7943b1b1-e2fb-4563-b485-4321146ecc39".equals(request.getPCompetentDepartment())) {
                //该项目所有合同的【本期实际支付】的合计值
                model.setPayAmount(n.getPayApproval());
            } else {
                //该项目所有“已支付数据维护~本期已付款金额”的合计值。（填写合同名称的相关记录）
                model.setPayAmount(n.getPayPayable());
            }
            //计算比例
            if (!BigDecimal.ZERO.equals(request.getConAmount()) && ValidateUtil.isNotNull(request.getConAmount())) {
                if (request.getConAmount().intValue() != 0 && ValidateUtil.isNotNull(model.getPayAmount())) {
                    BigDecimal rate = NumberUtil.div(model.getPayAmount(), request.getConAmount(), 4).multiply(new BigDecimal(100));
                    model.setPayRate(rate);
                }
            }
            return model;
        }).collect(Collectors.toList());

        return BaseResponse.ok(datas);
    }

    @Override
    public BaseResponse getEngineeringSpecialAccount(EngineeringSpecialAccount request, String uId) {
        QueryMap<EngineeringSpecialAccount> params = new QueryMap<>(request);
        String authSql = getAuthSql(OutUserAuthEnum.Unit_Auth.getIndex(), uId);
        params.put("pName").put("pCompetentDepartment").put("pBuildModel").put("authSql", authSql)
                .put("projectId");
        // 获取初始项目列表
        List<EngineeringSpecialAccount> preProjectList = baseMapper.getEngineeringSpecialAccount(params.getQuery());
        if (CollectionUtil.isEmpty(preProjectList)) {
            return BaseResponse.ok(new ArrayList<>());
        }

        // 提取项目ID并按PID对数据进行分组
        List<String> pIds = preProjectList.stream().map(EngineeringSpecialAccount::getProjectId).distinct().collect(Collectors.toList());
        Map<String, List<EngineeringSpecialAccount>> projectAccountMap = preProjectList.stream()
                .collect(Collectors.groupingBy(EngineeringSpecialAccount::getProjectId));

        // 准备认领资金数据
        List<ClaimIncomingFundsDtlDTO> claimFunds = claimIncomingFundsDtlMapper.getClaimIncomingFundsDtlsLedgerByPIds(pIds);

        Map<String, List<ClaimIncomingFundsDtlDTO>> claimFundsMap = claimFunds.stream()
                .collect(Collectors.groupingBy(ClaimIncomingFundsDtlDTO::getPId));

        // 合同支付数据
        List<ConPay> conPays = this.list(Wrappers.<ConPay>lambdaQuery()
                .eq(ConPay::getContractPayType, '4')
                .eq(ConPay::getPayState, ApprovalStatusEnum.Already_Over.getIndex())
                .in(ConPay::getPId, pIds)
        );
        Map<String, List<ConPay>> conPayMap = conPays.stream().collect(Collectors.groupingBy(ConPay::getPId));

        // 资金计划与进度数据
        List<InvPlanYearResponse> invPlanYearGroup = invPlanYearMapper.getInvPlanYearGroup();
        Map<String, List<InvPlanYearResponse>> planYearMap = invPlanYearGroup.stream()
                .filter(i -> pIds.contains(i.getpId()))
                .collect(Collectors.groupingBy(InvPlanYearResponse::getpId));

        List<InvCompleteMonth> invCompleteMonthList = invCompleteMonthMapper.getInvCompleteMonthGroup();
        Map<String, List<InvCompleteMonth>> completeMonthMap = invCompleteMonthList.stream()
                .filter(i -> pIds.contains(i.getPId()))
                .collect(Collectors.groupingBy(InvCompleteMonth::getPId));

        // 构建结果
        int currentYear = LocalDate.now().getYear();

        List<EngineeringSpecialAccount> datas = pIds.stream().map(pid -> {
            List<EngineeringSpecialAccount> accounts = projectAccountMap.get(pid);
            if (CollectionUtil.isEmpty(accounts)) {
                return null;
            }
            EngineeringSpecialAccount n = accounts.get(0);

            EngineeringSpecialAccount model = new EngineeringSpecialAccount();
            model.setProjectId(n.getProjectId());
            model.setPName(n.getPName());
            model.setPCompetentDepartment(n.getPCompetentDepartment());
            model.setPCompetentDepartmentName(n.getPCompetentDepartmentName());
            model.setPBuildModel(n.getPBuildModel());

            // 集团账户信息
            List<ClaimIncomingFundsDtlDTO> incomingFunds = claimFundsMap.getOrDefault(pid, Collections.emptyList());
            BigDecimal initGroupFunds = BigDecimal.ZERO;
            BigDecimal skje = BigDecimal.ZERO;

            if (CollectionUtil.isNotEmpty(incomingFunds)) {
                // 找出最早的cifdGroupFunds有值的记录
                initGroupFunds = incomingFunds.stream()
                        .filter(k -> ValidateUtil.isNotNull(k.getCifdGroupFunds()))
                        .min(Comparator.comparing(ClaimIncomingFundsDtlDTO::getCreateTime, Comparator.nullsLast(Comparator.naturalOrder())))
                        .map(ClaimIncomingFundsDtlDTO::getCifdGroupFunds)
                        .orElse(BigDecimal.ZERO);

                // 计算收款金额总和
                skje = incomingFunds.stream()
                        .map(ClaimIncomingFundsDtlDTO::getCifdAccount)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, NumberUtil::add);
            }
            model.setEsaJtTotalIncome(skje.add(initGroupFunds));

            // 支出合计
            List<ConPay> payList = conPayMap.getOrDefault(pid, Collections.emptyList());
            BigDecimal gcyksq = payList.stream()
                    .map(ConPay::getPayApply)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, NumberUtil::add);
            model.setEsaJtTotalExpenditure(gcyksq);

            // 账户余额
            model.setEsaJtAccountBalance(model.getEsaJtTotalIncome().subtract(model.getEsaJtTotalExpenditure()));

            // 收入占比
            if (ValidateUtil.isNotNull(n.getPCalculateRoughlyAmount()) && n.getPCalculateRoughlyAmount().compareTo(BigDecimal.ZERO) != 0) {
                try {
                    BigDecimal rate = NumberUtil.div(model.getEsaJtTotalIncome(), n.getPCalculateRoughlyAmount(), 4).multiply(new BigDecimal(100));
                    model.setEsaJtRevenueShare(rate);
                } catch (Exception e) {
                    model.setEsaJtRevenueShare(BigDecimal.ZERO);
                }
            } else {
                model.setEsaJtRevenueShare(BigDecimal.ZERO);
            }

            // 投资进度
            List<InvPlanYearResponse> planRefs = planYearMap.getOrDefault(pid, Collections.emptyList());
            BigDecimal ipvDoneLastYear = planRefs.stream()
                    .filter(k -> NumberUtil.isNumber(k.getIpvDeclareYear()) && currentYear >= Integer.parseInt(k.getIpvDeclareYear()))
                    .map(InvPlanYearResponse::getIpvDoneLastYear)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, NumberUtil::add);

            List<InvCompleteMonth> monthRefs = completeMonthMap.getOrDefault(pid, Collections.emptyList());
            BigDecimal invCompleteMonth = monthRefs.stream()
                    .filter(k -> NumberUtil.isNumber(k.getIcmReportingYear()) && currentYear >= Integer.parseInt(k.getIcmReportingYear()))
                    .map(InvCompleteMonth::getIcmAnnualInvestment)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, NumberUtil::add);

            if (ValidateUtil.isNotNull(n.getPCalculateRoughlyAmount()) && n.getPCalculateRoughlyAmount().compareTo(BigDecimal.ZERO) != 0) {
                try {
                    BigDecimal rate = NumberUtil.div(ipvDoneLastYear.add(invCompleteMonth), n.getPCalculateRoughlyAmount(), 4).multiply(new BigDecimal(100));
                    model.setEsaJtInvestmentProgress(rate);
                } catch (Exception e) {
                    model.setEsaJtInvestmentProgress(BigDecimal.ZERO);
                }
            } else {
                model.setEsaJtInvestmentProgress(BigDecimal.ZERO);
            }

            // 企业账户收入
            model.setEsaQyTotalIncome(model.getEsaJtTotalExpenditure());

            // 企业账户支出
            BigDecimal totalExpenditure;
            if ("7943b1b1-e2fb-4563-b485-4321146ecc39".equals(n.getPCompetentDepartment())) {
                BigDecimal p1 = accounts.stream().filter(m -> "01".equals(m.getPayCategory()))
                        .map(EngineeringSpecialAccount::getPayPayable)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, NumberUtil::add);
                BigDecimal p2 = accounts.stream().filter(m -> "02".equals(m.getPayCategory()))
                        .map(EngineeringSpecialAccount::getPayApproval)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, NumberUtil::add);
                totalExpenditure = p1.add(p2);
            } else {
                totalExpenditure = accounts.stream()
                        .map(EngineeringSpecialAccount::getPayPayable)
                        .filter(Objects::nonNull)
                        .reduce(BigDecimal.ZERO, NumberUtil::add);
            }
            model.setEsaQyTotalExpenditure(totalExpenditure);
            model.setEsaQyAccountBalance(model.getEsaQyTotalIncome().subtract(totalExpenditure));

            // 支付占比
            if (ValidateUtil.isNotNull(n.getPCalculateRoughlyAmount()) && n.getPCalculateRoughlyAmount().compareTo(BigDecimal.ZERO) != 0) {
                try {
                    BigDecimal rate = NumberUtil.div(model.getEsaQyTotalExpenditure(), n.getPCalculateRoughlyAmount(), 4).multiply(new BigDecimal(100));
                    model.setEsaQyPaymentProportion(rate);
                } catch (Exception e) {
                    model.setEsaQyPaymentProportion(BigDecimal.ZERO);
                }
            } else {
                model.setEsaQyPaymentProportion(BigDecimal.ZERO);
            }

            return model;
        }).filter(Objects::nonNull).collect(Collectors.toList());

        return BaseResponse.ok(datas);
    }

    @Override
    public BaseResponse getEngineeringSpecialAccountForJt(EngineeringSpecialAccountForJT request) {
        // 获取收入明细
        List<ClaimIncomingFundsDtlDTO> claimIncomingFundsDtls = claimIncomingFundsDtlMapper.getClaimIncomingFundsDtlsLedgerByPId(request.getProjectId());

        List<EngineeringSpecialAccountForJT> result = new ArrayList<>();

        // 处理初始余额
        ClaimIncomingFundsDtlDTO initialFundRecord = claimIncomingFundsDtls.stream()
                .filter(k -> ValidateUtil.isNotNull(k.getCifdGroupFunds()))
                .min(Comparator.comparing(ClaimIncomingFundsDtlDTO::getCreateTime, Comparator.nullsLast(Comparator.naturalOrder())))
                .orElse(null);

        BigDecimal initialBalance = BigDecimal.ZERO;
        if (initialFundRecord != null) {
            initialBalance = initialFundRecord.getCifdGroupFunds() != null ? initialFundRecord.getCifdGroupFunds() : BigDecimal.ZERO;
        }

        // 处理收入
        if (ValidateUtil.isNotEmpty(claimIncomingFundsDtls)) {
            List<EngineeringSpecialAccountForJT> incomeEntries = claimIncomingFundsDtls.stream().map(mn -> {
                EngineeringSpecialAccountForJT model = new EngineeringSpecialAccountForJT();
                model.setProjectId(request.getProjectId());
                model.setPName(request.getPName());
                model.setEsaJtType("收入");
                model.setEsaJtDate(mn.getCifDate());
                model.setEsaJtUnit(mn.getCifCa());
                BigDecimal amount = mn.getCifdAccount() != null ? mn.getCifdAccount() : BigDecimal.ZERO;
                model.setEsaJtAmount("+" + amount.toPlainString());
                model.setAmount(amount);
                return model;
            }).collect(Collectors.toList());
            result.addAll(incomeEntries);
        }

        // 处理支出
        List<ConPayReceiptDTO> receiptList = baseMapper.getConPayReceiptListByPIdJT(request.getProjectId());
        if (ValidateUtil.isNotEmpty(receiptList)) {
            List<EngineeringSpecialAccountForJT> expendEntries = receiptList.stream().map(mn -> {
                EngineeringSpecialAccountForJT model = new EngineeringSpecialAccountForJT();
                model.setProjectId(request.getProjectId());
                model.setPName(request.getPName());
                model.setEsaJtType("支出");
                model.setEsaJtDate(mn.getPayCreateTime());
                model.setEsaJtUnit(mn.getAccountName());
                BigDecimal amount = mn.getReceiptAmount() != null ? mn.getReceiptAmount() : BigDecimal.ZERO;
                model.setEsaJtAmount("-" + amount.toPlainString());
                model.setAmount(amount.negate());
                return model;
            }).collect(Collectors.toList());
            result.addAll(expendEntries);
        }

        if (ValidateUtil.isEmpty(result)) {
            return BaseResponse.ok(result);
        }

        // 排序
        result.sort(Comparator.comparing(
                EngineeringSpecialAccountForJT::getEsaJtDate,
                Comparator.nullsFirst(Comparator.naturalOrder())
        ));

        // 计算累计余额
        BigDecimal balance = BigDecimal.ZERO;
        for (EngineeringSpecialAccountForJT item : result) {
            if (item.getAmount() != null) {
                balance = balance.add(item.getAmount());
            }
            item.setEsaJtAccountBalance(balance.add(initialBalance));
        }

        // 过滤结果
        List<EngineeringSpecialAccountForJT> filteredResult = result.stream()
                .filter(k -> (ValidateUtil.isNull(request.getEsaJtType()) || Objects.equals(request.getEsaJtType(), k.getEsaJtType())) &&
                        (ValidateUtil.isNull(request.getEsaJtDate()) || Objects.equals(request.getEsaJtDate(), k.getEsaJtDate())) &&
                        (ValidateUtil.isNull(request.getEsaJtUnit()) || Objects.equals(request.getEsaJtUnit(), k.getEsaJtUnit())) &&
                        (ValidateUtil.isNull(request.getEsaJtAmount()) || Objects.equals(request.getEsaJtAmount(), k.getEsaJtAmount())))
                .collect(Collectors.toList());

        return BaseResponse.ok(filteredResult);
    }

    @Override
    public BaseResponse getEngineeringSpecialAccountForQy(EngineeringSpecialAccountForQY request) {
        // 特殊主管部门ID
        final String SPECIAL_DEPT_ID = "7943b1b1-e2fb-4563-b485-4321146ecc39";

        // 获取数据源
        // 收入
        List<ConPayReceiptDTO> receiptList = baseMapper.getConPayReceiptListByPIdJT(request.getProjectId());

        // 支出
        List<ConPayResponse> expenseList = baseMapper.getConPayListByPId(request.getProjectId());

        // 处理收入数据
        List<EngineeringSpecialAccountForQY> allRecords = receiptList.stream().map(mn -> {
            EngineeringSpecialAccountForQY model = new EngineeringSpecialAccountForQY();
            model.setProjectId(request.getProjectId());
            model.setPName(request.getPName());
            model.setEsaQyType("收入");
            model.setEsaQyDate(mn.getPayCreateTime());
            model.setEsaQyUnit(mn.getAccountName());
            BigDecimal amount = mn.getReceiptAmount() != null ? mn.getReceiptAmount() : BigDecimal.ZERO;
            model.setAmount(amount);
            model.setEsaQyAmount("+" + amount.toPlainString());
            return model;
        }).collect(Collectors.toList());

        // 处理支出数据
        boolean isSpecialDept = SPECIAL_DEPT_ID.equals(request.getPCompetentDepartment());
        List<EngineeringSpecialAccountForQY> expenseRecords = expenseList.stream().map(mn -> {
            EngineeringSpecialAccountForQY model = new EngineeringSpecialAccountForQY();
            model.setProjectId(request.getProjectId());
            model.setPName(request.getPName());
            model.setEsaQyType("支出");

            BigDecimal payAmount;
            if (isSpecialDept) {
                // 特殊部门逻辑
                model.setEsaQyDate(mn.getPayStateTime());
                if ("02".equals(mn.getPayCategory())) {
                    model.setEsaQyUnit(mn.getPayPayeeNamePro());
                    payAmount = mn.getPayApproval();
                } else {
                    model.setEsaQyUnit(mn.getPayPayeeName());
                    payAmount = mn.getPayPayable();
                }
            } else {
                // 普通部门逻辑
                model.setEsaQyDate(mn.getPayApplicationDate());
                model.setEsaQyUnit(mn.getPayPayeeCaName());
                payAmount = mn.getPayPayable();
            }

            BigDecimal finalAmount = payAmount != null ? payAmount : BigDecimal.ZERO;
            model.setAmount(finalAmount.negate());
            model.setEsaQyAmount("-" + finalAmount.toPlainString());
            return model;
        }).collect(Collectors.toList());
        allRecords.addAll(expenseRecords);

        // 排序
        allRecords.sort(Comparator.comparing(
                EngineeringSpecialAccountForQY::getEsaQyDate,
                Comparator.nullsLast(Comparator.naturalOrder())
        ));

        // 计算账户实时余额
        BigDecimal runningBalance = BigDecimal.ZERO;
        for (EngineeringSpecialAccountForQY record : allRecords) {
            if (record.getAmount() != null) {
                runningBalance = runningBalance.add(record.getAmount());
            }
            record.setEsaQyAccountBalance(runningBalance);
        }

        // 应用筛选条件
        Stream<EngineeringSpecialAccountForQY> stream = allRecords.stream();

        if (ValidateUtil.isNotNull(request.getEsaQyType())) {
            stream = stream.filter(k -> request.getEsaQyType().equals(k.getEsaQyType()));
        }
        if (ValidateUtil.isNotNull(request.getEsaQyDate())) {
            stream = stream.filter(k -> Objects.equals(request.getEsaQyDate(), k.getEsaQyDate()));
        }
        if (ValidateUtil.isNotNull(request.getEsaQyUnit())) {
            stream = stream.filter(k -> Objects.equals(request.getEsaQyUnit(), k.getEsaQyUnit()));
        }
        if (ValidateUtil.isNotNull(request.getEsaQyAmount())) {
            stream = stream.filter(k -> Objects.equals(request.getEsaQyAmount(), k.getEsaQyAmount()));
        }

        List<EngineeringSpecialAccountForQY> result = stream.collect(Collectors.toList());
        return BaseResponse.ok(result);
    }

    @Override
    public BaseResponse getConPayPrintByPayId(String id) {
        ConPayPrintResponse data = baseMapper.getConPayPrintByPayId(id);
        if (data.getPayRemark() == null) {
            data.setPayRemark("");
        }
        if (ValidateUtil.isNull(data))
            return BaseResponse.fail("未找到相关数据");
        List<ConPayfundsDtl> conPayfundsDtlList = conPayfundsDtlMapper.getConPayfundsDtlListByPayId(id);
        List<ConPayFundsDetails> conPayfundsDetailsList = conPayfundsDtlMapper.getConPayfundsDetailsListByPayId(id);
        List<Object> conPayfundsDtls = new ArrayList<>();
        conPayfundsDtls.addAll(conPayfundsDtlList);
        conPayfundsDtls.addAll(conPayfundsDetailsList);
        data.setConPayFundsDetails(conPayfundsDtls);
        Map<String, Object> params = new HashMap<>();
        getTpInfo(id, params, data);
        log.info("data:{}", JacksonUtils.toJSONString(data));
        return BaseResponse.ok(data);
    }

    private void getTpInfo(String id, Map<String, Object> params, ConPayPrintResponse data) {
        params.put("fileId", !ValidateUtil.isEmpty(id) ? id : null);
        List<TaskProcessPrintResponse> taskProcessList = baseMapper.getTaskProcessList(params);
        //实施部门经办人
        TaskProcessPrintResponse tp9 = taskProcessList.stream().filter(n -> "实施部门经办人".equals(n.getActName())).findFirst().orElse(null);
        if (tp9 != null) {
            data.setSysTaskProcessin9FileID(tp9.getUSignature());
            data.setSysTaskProcessin9FileUname(tp9.getUName());
            data.setSysTaskProcessin9File(tp9.getImgUrl());
        }
        //造价管理部工程师
        TaskProcessPrintResponse tp10 = taskProcessList.stream().filter(n -> "造价管理部工程师".equals(n.getActName())).findFirst().orElse(null);
        if (tp10 != null) {
            data.setSysTaskProcessin10FileID(tp10.getUSignature());
            data.setSysTaskProcessin10FileUname(tp10.getUName());
            data.setSysTaskProcessin10File(tp10.getImgUrl());
        }
        TaskProcessPrintResponse tp11 = taskProcessList.stream().filter(n -> "工程管理部现场负责人".equals(n.getActName())).findFirst().orElse(null);
        if (tp11 != null) {
            data.setSysTaskProcessin11FileID(tp11.getUSignature());
            data.setSysTaskProcessin11FileUname(tp11.getUName());
            data.setSysTaskProcessin11File(tp11.getImgUrl());
        }
        //造价管理部工程师
        TaskProcessPrintResponse tp12 = taskProcessList.stream().filter(n -> "造价管理部项目负责人".equals(n.getActName())).findFirst().orElse(null);
        if (tp12 != null) {
            data.setSysTaskProcessin12FileID(tp12.getUSignature());
            data.setSysTaskProcessin12FileUname(tp12.getUName());
            data.setSysTaskProcessin12File(tp12.getImgUrl());
        }
        //实施部门部长意见
        TaskProcessPrintResponse tp1 = taskProcessList.stream().filter(n -> "实施部门部长".equals(n.getActName())).findFirst().orElse(null);
        if (tp1 != null) {
            data.setSysTaskProcessin1FileID(tp1.getUSignature());
            data.setSysTaskProcessin1TpExplain(tp1.getTpExplain());
            data.setSysTaskProcessin1TpDoTime(tp1.getTpDoTime());
            data.setSysTaskProcessin1FileUname(tp1.getUName());
            data.setSysTaskProcessin1File(tp1.getImgUrl());

        } else {
            data.setSysTaskProcessin1FileID("");
            data.setSysTaskProcessin1TpExplain("");
            data.setSysTaskProcessin1FileUname("");
        }
        //造价管理部部长意见
        TaskProcessPrintResponse tp2 = taskProcessList.stream().filter(n -> "造价管理部部长".equals(n.getActName())).findFirst().orElse(null);
        if (tp2 != null) {
            data.setSysTaskProcessin2FileID(tp2.getUSignature());
            data.setSysTaskProcessin2TpExplain(tp2.getTpExplain());
            data.setSysTaskProcessin2TpDoTime(tp2.getTpDoTime());
            data.setSysTaskProcessin2FileUname(tp2.getUName());
            data.setSysTaskProcessin2File(tp2.getImgUrl());
        } else {
            data.setSysTaskProcessin2FileID("");
            data.setSysTaskProcessin2TpExplain("");
            data.setSysTaskProcessin2FileUname("");
        }
        //造价管理部分管副指挥长意见
        TaskProcessPrintResponse tp3 = taskProcessList.stream().filter(n -> "造价管理部分管副指挥长".equals(n.getActName())).findFirst().orElse(null);
        if (tp3 != null) {
            data.setSysTaskProcessin3FileID(tp3.getUSignature());
            data.setSysTaskProcessin3TpExplain(tp3.getTpExplain());
            data.setSysTaskProcessin3TpDoTime(tp3.getTpDoTime());
            data.setSysTaskProcessin3FileUname(tp3.getUName());
            data.setSysTaskProcessin3File(tp3.getImgUrl());
        } else {
            data.setSysTaskProcessin3FileID("");
            data.setSysTaskProcessin3TpExplain("");
            data.setSysTaskProcessin3FileUname("");
        }
        //实施部门分管副指挥长意见
        TaskProcessPrintResponse tp4 = taskProcessList.stream().filter(n -> "实施部门分管副指挥长".equals(n.getActName())).findFirst().orElse(null);
        if (tp4 != null) {
            data.setSysTaskProcessin4FileID(tp4.getUSignature());
            data.setSysTaskProcessin4TpExplain(tp4.getTpExplain());
            data.setSysTaskProcessin4TpDoTime(tp4.getTpDoTime());
            data.setSysTaskProcessin4FileUname(tp4.getUName());
            data.setSysTaskProcessin4File(tp4.getImgUrl());
        } else {
            data.setSysTaskProcessin4FileID("");
            data.setSysTaskProcessin4TpExplain("");
            data.setSysTaskProcessin4FileUname("");
        }
        //常务副指挥长意见
        TaskProcessPrintResponse tp5 = taskProcessList.stream().filter(n -> "常务副指挥长".equals(n.getActName())).findFirst().orElse(null);
        if (tp5 != null) {
            data.setSysTaskProcessin5FileID(tp5.getUSignature());
            data.setSysTaskProcessin5TpExplain(tp5.getTpExplain());
            data.setSysTaskProcessin5TpDoTime(tp5.getTpDoTime());
            data.setSysTaskProcessin5FileUname(tp5.getUName());
            data.setSysTaskProcessin5File(tp5.getImgUrl());
        } else {
            data.setSysTaskProcessin5FileID("");
            data.setSysTaskProcessin5TpExplain("");
            data.setSysTaskProcessin5FileUname("");
        }
        //审计处负责人意见
        TaskProcessPrintResponse tp6 = taskProcessList.stream().filter(n -> "审计处负责人".equals(n.getActName())).findFirst().orElse(null);
        if (tp6 != null) {
            data.setSysTaskProcessin6FileID(tp6.getUSignature());
            data.setSysTaskProcessin6TpExplain(tp6.getTpExplain());
            data.setSysTaskProcessin6TpDoTime(tp6.getTpDoTime());
            data.setSysTaskProcessin6FileUname(tp6.getUName());
            data.setSysTaskProcessin6File(tp6.getImgUrl());
        } else {
            data.setSysTaskProcessin6FileID("");
            data.setSysTaskProcessin6TpExplain("");
            data.setSysTaskProcessin6FileUname("");
        }
        //财务处负责人意见
        TaskProcessPrintResponse tp7 = taskProcessList.stream().filter(n -> "财务处负责人".equals(n.getActName())).findFirst().orElse(null);
        if (tp7 != null) {
            data.setSysTaskProcessin7FileID(tp7.getUSignature());
            data.setSysTaskProcessin7TpExplain(tp7.getTpExplain());
            data.setSysTaskProcessin7TpDoTime(tp7.getTpDoTime());
            data.setSysTaskProcessin7FileUname(tp7.getUName());
            data.setSysTaskProcessin7File(tp7.getImgUrl());
        } else {
            data.setSysTaskProcessin7FileID("");
            data.setSysTaskProcessin7TpExplain("");
            data.setSysTaskProcessin7FileUname("");
        }
        //指挥长意见
        TaskProcessPrintResponse tp8 = taskProcessList.stream().filter(n -> "指挥长".equals(n.getActName())).findFirst().orElse(null);
        if (tp8 != null) {
            data.setSysTaskProcessin8FileID(tp8.getUSignature());
            data.setSysTaskProcessin8TpExplain(tp8.getTpExplain());
            data.setSysTaskProcessin8TpDoTime(tp8.getTpDoTime());
            data.setSysTaskProcessin8FileUname(tp8.getUName());
            data.setSysTaskProcessin8File(tp8.getImgUrl());
        } else {
            data.setSysTaskProcessin8FileID("");
            data.setSysTaskProcessin8TpExplain("");
            data.setSysTaskProcessin8FileUname("");
        }
    }


    @Override
    public BaseResponse postConpayForSKApi(ConPayRequest request) {
        //获取支付信息
        ConPayResponse conPay = baseMapper.getConPayBypayId(request.getPayId());
        if (conPay == null) return BaseResponse.fail(ResMsg.infoNotExist);
        if (conPay.getPayState() != ApprovalStatusEnum.Already_Over.getIndex())
            return BaseResponse.fail(ResMsg.stateCannotAct);
        SKThirdSystemConfig skthirdSystemConfig;
        String code = "SKThirdSystemConfig";
        String configByCode = sysConfigService.getConfigByCode(code);
        if (StrUtil.isNotEmpty(configByCode)) {
            skthirdSystemConfig = JacksonUtils.parseObject(configByCode, SKThirdSystemConfig.class);
        } else {
            return BaseResponse.fail(404, "配置信息缺失");
        }

        String IPHost = skthirdSystemConfig.getIPHost();
        String TenantId = skthirdSystemConfig.getTenantId();
        long Timestamp = System.currentTimeMillis();
        String SystemNo = skthirdSystemConfig.getSystemNo();
        String SystemName = skthirdSystemConfig.getSystemName();
        String RequestNo = GenerateUUID.getUUID();
        String AppUser = skthirdSystemConfig.getAppUser();
        String AppPwd = skthirdSystemConfig.getAppPwd();
        SKApiRequestDto skApiRequestDto = new SKApiRequestDto();
        SKApiCmscloud cmscloud = new SKApiCmscloud();
        SKApiHead skApiHead = new SKApiHead();
        skApiHead.setTenantId(TenantId);
        skApiHead.setTimestamp("" + Timestamp + "");
        skApiHead.setSystemNo(SystemNo);
        skApiHead.setSystemName(SystemName);
        skApiHead.setRequestNo(RequestNo);
        skApiHead.setMesgNo("T0002");
        skApiHead.setAppUser(AppUser);
        skApiHead.setAppPwd(AppPwd);
        cmscloud.setHead(skApiHead);
        SKApiBody skApiBody = new SKApiBody();
        SKApiBodyData skApiBodyData = new SKApiBodyData();
        SKConPayRequestDto skConPayRequestDto = new SKConPayRequestDto();
        skConPayRequestDto.setPayAccountNo(skthirdSystemConfig.getPayAccountNo());
        skConPayRequestDto.setBillsPayOrderNo(conPay.getPayCode());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = "";
        if (ValidateUtil.isNotNull(conPay.getPayCreateTime()))
            formattedDate = sdf.format(conPay.getPayCreateTime());
        else
            formattedDate = sdf.format(new Date());
        skConPayRequestDto.setPaymentDate(formattedDate);
        skConPayRequestDto.setPayType("1");
        skConPayRequestDto.setBusinessTypeCode("YW24072410204546000001");
        skConPayRequestDto.setBusinessTypeName("银行付款");
        skConPayRequestDto.setPayOrgCode("C102");
        skConPayRequestDto.setPayOrgName("南京城市建设管理集团有限公司");
        skConPayRequestDto.setPayChannel("1");
        if ("02".equals(conPay.getPayCategory())) {
            skConPayRequestDto.setPayAmount(conPay.getPayApproval().toString());
        } else {
            skConPayRequestDto.setPayAmount(conPay.getPayPayable().toString());
        }
        skConPayRequestDto.setPayUse(conPay.getPayContent());
        skConPayRequestDto.setSerialNo(conPay.getPayCode());
        PaymentRec PaymentRec = new PaymentRec();
        if ("02".equals(conPay.getPayCategory())) {
            PaymentRec.setReceiveOrgName(conPay.getPayPayeeNamePro());
        } else {
            PaymentRec.setReceiveOrgName(conPay.getPayPayeeName());
        }
        PaymentRec.setReceiveAccountNo(conPay.getPayPayeeCa());
        PaymentRec.setReceiveAccountName(conPay.getPayPayeeCaName());
        PaymentRec.setReceiveBranchName(conPay.getPayPayeeBank());
        skConPayRequestDto.setPaymentRec(PaymentRec);
        List<BudgetUsedList> BudgetUsedList = new ArrayList<>();
        List<InstitutionList> InstitutionList = new ArrayList<>();
        List<CashFlowItemList> CashFlowItemList = new ArrayList<>();
//        skConPayRequestDto.setBudgetUsedList(BudgetUsedList);
//        skConPayRequestDto.setInstitutionList(InstitutionList);
//        skConPayRequestDto.setCashFlowItemList(CashFlowItemList);
        List<Object> skConPayRequestDtoList = new ArrayList<>();
        skConPayRequestDtoList.add(skConPayRequestDto);
        skApiBodyData.setParamSet(skConPayRequestDtoList);
        skApiBody.setData(skApiBodyData);
        cmscloud.setBody(skApiBody);
        skApiRequestDto.setCmscloud(cmscloud);
        String JsonResult = JacksonUtils.toJSONString(skApiRequestDto);
        JsonResult = JsonResult.replace("null", "\"\"");
        String response = sendPost(IPHost, JsonResult, null);
        JSONObject result = JSONObject.parseObject(response);
        return BaseResponse.ok();
    }

    @Override
    public void updateContractRetentionMoney(String conId) {
        List<ConPay> conPayList = list(new LambdaQueryWrapper<ConPay>()
                .eq(ConPay::getConId, conId)
                .eq(ConPay::getPayType, "03")
                .eq(ConPay::getPayState, FlowStatusEnum.Finish.getIndex()));
        BigDecimal sum = BigDecimal.ZERO;
        for (ConPay conPay : conPayList) {
            BigDecimal thisTimeRetentionMoney = conPay.getThisTimeRetentionMoney();
            sum = NumberUtil.add(sum, thisTimeRetentionMoney);
        }
        LambdaUpdateWrapper<ConContract> updateWrapper = new LambdaUpdateWrapper<ConContract>()
                .eq(ConContract::getConId, conId)
                .set(ConContract::getConRmMoney, sum);
        conContractMapper.update(null, updateWrapper);
    }

    @Override
    public BaseResponse getConPayAmt(String conId) {
        if (ValidateUtil.isEmpty(conId)) return BaseResponse.fail("缺少必要参数");

        LambdaQueryWrapper<ConPay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConPay::getPayState, 7);
        queryWrapper.eq(ConPay::getPayCategory, "01");
        queryWrapper.eq(ConPay::getConId, conId);

        queryWrapper.select(ConPay::getPayApproval, ConPay::getPayApply, ConPay::getPayInvoice);
        List<ConPay> conPays = baseMapper.selectList(queryWrapper);
        Map<String, Object> data = new HashMap<>();

        BigDecimal payApprovalSum = conPays.stream().filter(e -> ValidateUtil.isNotNull(e.getPayApproval())).map(ConPay::getPayApproval).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal payApplySum = conPays.stream().filter(e -> ValidateUtil.isNotNull(e.getPayApply())).map(ConPay::getPayApply).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal payInvoiceSum = conPays.stream().filter(e -> ValidateUtil.isNotNull(e.getPayInvoice())).map(ConPay::getPayInvoice).reduce(BigDecimal.ZERO, BigDecimal::add);

        data.put("payApprovalSum", payApprovalSum);
        data.put("payApplySum", payApplySum);
        data.put("payInvoiceSum", payInvoiceSum);

        return BaseResponse.ok(data);
    }


    @Override
    public BaseResponse automaticallyFundsPayment(ConPayRequest request, String token) {
        String userConf = sysConfigService.getConfigByCode("automaticallyFundsPaymentUserId");
        if (ValidateUtil.isEmpty(userConf))
            return BaseResponse.fail(String.format(ResMsg.notConf, "automaticallyFundsPaymentUserId"));
        if (ValidateUtil.isEmpty(request.getPayId())) return BaseResponse.fail(ResMsg.notReqParam);
        FileTemplateTree temp = userProducerFeign.getFileTemplateByCode("ConPayApplyFlow");
        if (ValidateUtil.isNull(temp))
            return BaseResponse.fail(String.format(ResMsg.notFoundFileTmp, "ConPayApplyFlow"));
        LambdaQueryWrapper<ConPay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConPay::getPayId, request.getPayId());
        queryWrapper.eq(ConPay::getPayState, 7);
        ConPay conPay = baseMapper.selectOne(queryWrapper);
        LambdaQueryWrapper<SysUser> query = new LambdaQueryWrapper<>();
        String uId = redisUtils.getUid(token);
        query.eq(SysUser::getUId, uId);
        SysUser sysUser = sysUserService.getBaseMapper().selectOne(query);
        //保存数据到
        if (ValidateUtil.isNotNull(conPay)) {
            List<ConPayApplyFlow> applyflow = conPayApplyFlowService.getConPayById(conPay.getPayId());
//             if(!CollectionUtils.isEmpty(applyflow)){
//                 return BaseResponse.fail("该合同支付已存在资金支付申请");
//             }
            ConContract moneys = conContractMapper.getConMoneyTotal(conPay.getConId());
            SysCompany comPany = sysCompanyService.getComPanyName(conPay.getPayPayee());
            ConPayApplyFlow flow = new ConPayApplyFlow();
            ConPayApplyFlowDto flowDto = new ConPayApplyFlowDto();
            flow.setConId(conPay.getConId());
            flow.setPId(conPay.getPId());
            flow.setCpafId(GenerateUUID.getUUID());
//                 flow.setCpaId(conPay.getPayId());
            flow.setPayId(conPay.getPayId());

            if (ValidateUtil.isNotNull(comPany)) {
                flow.setCpafPayee(comPany.getcName());
            }
            flow.setCpafDepositBank(conPay.getPayPayeeBank());
            flow.setCpafBankAccount(conPay.getPayPayeeCa());
            flow.setCpafApply(conPay.getPayPayable() == null ? BigDecimal.ZERO : conPay.getPayPayable());
            flow.setState(0);
            flow.setCpafCreateTime(new Date());
            flow.setCpafCreateUid(uId);
            ConPayApplyFlowSumDto dto = new ConPayApplyFlowSumDto();
            dto.setConId(conPay.getConId());
            dto.setCpaId(conPay.getPayId());
            dto.setpId(conPay.getPId());
            if (ValidateUtil.isNotNull(sysUser)) {
                flow.setCpafCreateUname(sysUser.getUName());
            }
            BaseResponse conPayApplyFlowSumById = conPayApplyFlowService.getConPayApplyFlowSumById(dto);
            List<Map> hashMaps = JacksonUtils.parseArray(JacksonUtils.toJSONString(conPayApplyFlowSumById.getData()), Map.class);
            if (ValidateUtil.isNotEmpty(hashMaps)) {
                Map<String, Object> map = new HashMap<>(hashMaps.get(0));
                flow.setCpafPaidThisPeriod(new BigDecimal(String.valueOf(map.get("applicationTotal"))));
                flow.setCpafTotalLast(new BigDecimal(String.valueOf(map.get("conApplicationTotal"))));
                flow.setCpafTotalPayment(new BigDecimal(String.valueOf(map.get("contractMoneyTotal"))));
                flow.setCpafRemainingAmount(flow.getCpafApply().subtract(flow.getCpafPaidThisPeriod() == null ? BigDecimal.ZERO : flow.getCpafPaidThisPeriod()));

                BigDecimal a = BigDecimal.ZERO;
                if (ValidateUtil.isNotNull(moneys)) {
                    a = moneys.getConMoneyTotal();
                }

                BigDecimal moy = MathUtil.decimalDivide(flow.getCpafTotalPayment(), a);
                flow.setCpafApplyProportion(MathUtil.DecimalMultiplicationInt(moy, 100));
            }
            BeanUtils.copyProperties(flow, flowDto);
            conPayApplyFlowService.getBaseMapper().insert(flow);
//             createOrUpdateFileEntity(flowDto, flow);
            sysFileService.saveFile(flow.getCpafId(), temp.gettId(), flow.getState() + "", flow.getCpafCreateUid(), flow.getCpafCreateUname(), flow.getCpafCreateTime(), conPay.getPId(), request.getFileTitle(), request.getFileCode(), "create", GenerateUUID.getUUID(), null, temp.gettCode(), conPay.getConId());
            String conPaymentAgent = sysConfigService.getConfigByCode("conPaymentAgent");
            JSONArray jsonArray = JSONUtil.parseArray(conPaymentAgent);
            List<String> dName = new ArrayList<>();
            List<String> pName = new ArrayList<>();
            for (Object obj : jsonArray) {
                cn.hutool.json.JSONObject jsonObject = (cn.hutool.json.JSONObject) obj;
                dName.add(jsonObject.getStr("key"));
                pName.add(jsonObject.getStr("value"));
            }
            //审批人根据基础配置获取，如没有就使用固定默认审批人
            if (ValidateUtil.isEmpty(dName)) dName.add("战投财务部");
            if (ValidateUtil.isEmpty(pName)) pName.add("财务会计");
            //如果为空就给默认值，确保流程有人审批，如还是为空就给当前用户
            List<SysUserVo> list = sysUserService.getComPanyUser(dName, pName);
            String spUid = "";
            if (!CollectionUtils.isEmpty(list)) {
                SysUserVo vo = list.get(0);
                spUid = vo.getuId();
            } else {
                dName.removeAll(dName);
                pName.removeAll(pName);
                dName.add("战投财务部");
                pName.add("财务会计");
                List<SysUserVo> list1 = sysUserService.getComPanyUser(dName, pName);
                if (!CollectionUtils.isEmpty(list1)) {
                    SysUserVo vo = list1.get(0);
                    spUid = vo.getuId();
                }
            }
            spUid = spUid.equals("") ? uId : spUid;
            //生成代办
            BatchhandlingTaskDto task = new BatchhandlingTaskDto();
            task.setCurrentNode("createNode");
            task.setOnlineConditions("false");
            task.settCode("ConPayApplyFlow");
            LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
            task.setTeId(ValidateUtil.isEmpty(user.getTeId()) ? "All" : user.getTeId());
            BatchhandlingTaskListDto tkl = new BatchhandlingTaskListDto();
            tkl.setFileId(flow.getCpafId()).setUId(spUid).setProcessed(userConf).setOperator(conPay.getPayCreateUid());
            task.setSendList(Arrays.asList(tkl));
            BaseResponse taskRes = bpmFeign.batchSendTask(task, token);
            if (taskRes == null || !taskRes.getSuccess()) throw new JPMException(ResMsg.taskCreateFail);
        } else {
            return BaseResponse.fail("没有查询到相关数据");
        }
        return BaseResponse.ok();
    }

    @Override
    public void uploadInvoice(UploadPayInvoiceRequest request) {
        String payId = request.getPayId();
        ConPay conPay = getById(payId);
        if (conPay == null) {
            throw new JPMException("不存在的支付申请");
        }
        int UPLOADED = 1;
        if (Integer.valueOf(UPLOADED).equals(conPay.getUploadedInvoice())) {
            return;
        }
        conPay.setInvoiceFileRelationId(request.getInvoiceFileRelationId());
        conPay.setUploadedInvoice(UPLOADED);
        updateById(conPay);
        sysFileService.saveFile(request.getInvoiceFileRelationId(), null, conPay.getPayState() + "", conPay.getPayCreateUid(), conPay.getPayCreateUname()
                , conPay.getPayCreateTime(), conPay.getPId(), request.getFileTittle(), request.getFileCode(),
                "create", request.getInvoiceFileRelationId(), null, request.gettCode(), conPay.getConId());
        sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getInvoiceFileRelationId()), payId);
    }

    @Override
    public BaseResponse getConCompayPayTotalById(String conId) {
        //
        LambdaQueryWrapper<ConPay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConPay::getCcdId, conId);
        queryWrapper.eq(ConPay::getPayState, 7);
        queryWrapper.select(ConPay::getPayApproval, ConPay::getPayApply, ConPay::getPayInvoice);
        List<ConPay> conPays = baseMapper.selectList(queryWrapper);
        BigDecimal payApprovalSum = conPays.stream().filter(e -> ValidateUtil.isNotNull(e.getPayApproval())).map(ConPay::getPayApproval).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal payApplySum = conPays.stream().filter(e -> ValidateUtil.isNotNull(e.getPayApply())).map(ConPay::getPayApply).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal payInvoiceSum = conPays.stream().filter(e -> ValidateUtil.isNotNull(e.getPayInvoice())).map(ConPay::getPayInvoice).reduce(BigDecimal.ZERO, BigDecimal::add);
        Map<String, Object> data = new HashMap<>();

        data.put("payApprovalSum", payApprovalSum);
        data.put("payApplySum", payApplySum);
        data.put("payInvoiceSum", payInvoiceSum);
        return BaseResponse.ok(data);

    }

    //查询所有审批通过的支付数据，节省字段提升速度
    @Override
    public List<ConPayResponse> getPayList(List<String> conIds) {

        return baseMapper.getPayList(conIds);
    }


    @Override
    public BaseResponse lastPeriodDeadline(String conId) {
        if (ValidateUtil.isEmpty(conId)) return BaseResponse.fail("请求参数为空");
        LambdaQueryWrapper<ConPay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConPay::getConId, conId);
        queryWrapper.eq(ConPay::getPayState, 7);
        queryWrapper.orderByDesc(ConPay::getPayStateTime);
        queryWrapper.last(" limit 1 ");
        ConPay conPay = baseMapper.selectOne(queryWrapper);
        return BaseResponse.ok(conPay);
    }

    @Override
    public BaseResponse getpreUnderPayment(String pId) {
        if (ValidateUtil.isEmpty(pId)) return BaseResponse.fail("缺少必填参数");
        LambdaQueryWrapper<ConContract> conQuery = new LambdaQueryWrapper<>();
        conQuery.eq(ConContract::getConProjectId, pId);
        List<ConContract> conIds = conContractMapper.selectList(conQuery);
        BigDecimal accumulatedSum = BigDecimal.ZERO;  //合同支付和
        BigDecimal feePayment = BigDecimal.ZERO;  //规费
        Map<String, Object> data = new HashMap<>();

        //所有合同最后一次支付只和
        if (ValidateUtil.isNotEmpty(conIds)) {
            for (ConContract con : conIds) {
                LambdaQueryWrapper<ConPay> queryWrapper = new LambdaQueryWrapper<>();
                queryWrapper.eq(ConPay::getConId, con.getConId())
                        .eq(ConPay::getPayState, 7)
                        .orderByDesc(ConPay::getPayStateTime) // 假设根据支付时间排序
                        .last(" limit  1 "); // 只取最后一笔支付记录
                ConPay conPay = baseMapper.selectOne(queryWrapper);
                if (conPay != null) {
                    if (ValidateUtil.isNotNull(conPay.getPayTotal())) {
                        accumulatedSum = accumulatedSum.add(conPay.getPayTotal());
                    }
                }
            }
        }
        data.put("accumulatedSum", accumulatedSum);

        //规费支付
        LambdaQueryWrapper<ConPay> payQueryWrapper = new LambdaQueryWrapper<>();
        payQueryWrapper.eq(ConPay::getPId, pId);
        payQueryWrapper.isNull(ConPay::getConId);
        payQueryWrapper.eq(ConPay::getPayState, 7);
        payQueryWrapper.orderByDesc(ConPay::getPayStateTime).last(" limit  1 ");
        ConPay gfconPay = baseMapper.selectOne(payQueryWrapper);
        if (ValidateUtil.isNotNull(gfconPay)) {
            feePayment = feePayment.add(ValidateUtil.isEmpty(gfconPay.getPayApproval()) ? BigDecimal.ZERO : gfconPay.getPayApproval()).add(ValidateUtil.isEmpty(gfconPay.getPayActualPayTotal()) ? BigDecimal.ZERO : gfconPay.getPayActualPayTotal());
        }
        data.put("feePayment", feePayment);

        return BaseResponse.ok(data);
    }

    @Override
    public List<ConPay> getConAndPayApplicationType(String conId, String type) {
        LambdaQueryWrapper<ConPay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConPay::getConId, conId);
        queryWrapper.eq(ConPay::getPayApplicationType, type);
        return baseMapper.selectList(queryWrapper);
    }

    @Override
    public BaseResponse getConPayDataListTc(ConPayRequest request, String uId) {
        String authSql = getAuthSql(uId, null);
        Page<ConPayResponse> data = baseMapper.getConPayDataListTc(new Page<>(request.getPage(), request.getSize()), request, authSql);
        List<ConPayResponse> dataSum = baseMapper.getConPayDataListSumMoneyTc(request, authSql);
        BigDecimal conMoneyTotalSum = dataSum.stream().filter(e -> ValidateUtil.isNotNull(e.getConMoneyTotal())).map(e -> {
            return new BigDecimal(e.getConMoneyTotal());
        }).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal payApplySum = dataSum.stream().filter(e -> ValidateUtil.isNotNull(e.getPayApply())).map(e -> e.getPayApply()).reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> map = new HashMap<>();
        if (data.getTotal() > 0) {
            map.put("data", data.getRecords());
            map.put("total", data.getTotal());
            map.put("payApplySum", payApplySum);
            map.put("conMoneyTotalSum", conMoneyTotalSum);
        }
        return BaseResponse.ok(map);
    }

    @Override
    public BaseResponse getConPayDatadetailsTc(ConPayRequest request, String token) {
        List<ConPayResponse> data = baseMapper.getConPayDatadetailsTc(request);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse deleteConPayById(String payId) {
        baseMapper.deleteById(payId);
        sysFileService.removeById(payId);
        progressDeclarationMapper.deleteSysTaskEntityId(payId);
        progressDeclarationMapper.deleteSysTaskProcessinEntityId(payId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse chectConPayByid(String conId) {
//        ConContract byId = contractService.getById(payId);
        Boolean complete = contractService.getCompleteById(conId);
        return BaseResponse.ok(complete);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse flashbackConPay(String size) {
        Integer num = Integer.parseInt(size);
        int pageSize = num == null ? 10 : num; // 默认每次刷 10 条
        LambdaQueryWrapper<ConPay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConPay::getIsProcessed, 0)
                .last(" limit " + pageSize);
        List<ConPay> records = baseMapper.selectList(queryWrapper);

        if (CollectionUtils.isEmpty(records)) {
            return BaseResponse.ok("没有需要数据");
        }
        List<ConPayfundsDtl> conPayfundsDtls = new ArrayList<>();

        for (ConPay conPay : records) {
            ConPayfundsDtl conPayfundsDtl = new ConPayfundsDtl();
            conPayfundsDtl.setId(UUID.randomUUID().toString());
            conPayfundsDtl.setPayfundaccount(conPay.getPayFundAccount());
            conPayfundsDtl.setPayfundaccountnum(conPay.getPayFundAccountNum());
            conPayfundsDtl.setPayDtlId(conPay.getPayId());
            conPayfundsDtls.add(conPayfundsDtl);
        }
        conPayfundsDtlService.saveBatch(conPayfundsDtls);
        // 获取这批记录的 ID 列表
        List<String> idsToMark = records.stream()
                .map(ConPay::getPayId)
                .collect(Collectors.toList());

        // 更新 is_processed = 1
        LambdaUpdateWrapper<ConPay> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(ConPay::getPayId, idsToMark)
                .set(ConPay::getIsProcessed, 1);

        baseMapper.update(null, updateWrapper);

        return BaseResponse.ok("成功处理 " + records.size() + " 条数据");
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse getFlashback() {
        // 获取所有未处理的数据
        List<ConPay> allRecords = baseMapper.selectList(new LambdaQueryWrapper<ConPay>()
                .eq(ConPay::getIsProcessed, 0));

        if (CollectionUtils.isEmpty(allRecords)) {
            return BaseResponse.ok("没有需要数据");
        }

        List<ConPayfundsDtl> conPayfundsDtls = new ArrayList<>();

        for (ConPay conPay : allRecords) {
            ConPayfundsDtl conPayfundsDtl = new ConPayfundsDtl();
            conPayfundsDtl.setId(UUID.randomUUID().toString());
            conPayfundsDtl.setPayfundaccount(conPay.getPayFundAccount());
            conPayfundsDtl.setPayfundaccountnum(conPay.getPayFundAccountNum());
            conPayfundsDtl.setPayDtlId(conPay.getPayId());
            conPayfundsDtls.add(conPayfundsDtl);
        }

        // 批量保存
        conPayfundsDtlService.saveBatch(conPayfundsDtls);

        // 获取所有记录的 ID 列表
        List<String> idsToMark = allRecords.stream()
                .map(ConPay::getPayId)
                .collect(Collectors.toList());

        // 更新 is_processed = 1
        LambdaUpdateWrapper<ConPay> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.in(ConPay::getPayId, idsToMark)
                .set(ConPay::getIsProcessed, 1);

        baseMapper.update(null, updateWrapper);

        return BaseResponse.ok("成功处理 " + allRecords.size() + " 条数据");
    }


    private String getAuthSql(String uId, String conCategory) {
        if (ValidateUtil.isNotEmpty(conCategory) && conCategory.equals("6")) {
            //年度合同
            return sysUserService.onlyInnerAndPartyB("Con_Contract", uId, "Con_Create_User_Id", "Con_Approval_State");
        } else if (ValidateUtil.isNotEmpty(conCategory) && conCategory.equals("7")) {
            //经费合同 没有项目的数据按合同部门鉴权 有项目的数据按正常项目鉴权
            String authsql = sysUserService.currentDepAuth(uId, "Con_Department", "Con_Create_User_Id", "Con_Approval_State");
            authsql = authsql.replaceFirst(" where ", " where  (( mn.Con_Project_Id is null and ") + ")  or ( mn.Con_Project_Id is not null and ";
            String projectAuth = sysUserService.getAuthSql("Con_Contract", uId, "Con_Create_User_Id", OutUserAuthEnum.Unit_Auth.getIndex(), "Con_Approval_State");
            projectAuth = projectAuth.replaceFirst(" where ", authsql) + ") )";
            return projectAuth;
            // return sysUserService.currentDepAuth(uId, "Con_Department", "Con_Create_User_Id", "Con_Approval_State");
        } else {//其他合同
            String authSql = sysUserService.onlyCreateorAndApprover("Con_Contract", uId, "Con_Create_User_Id", OutUserAuthEnum.Unit_Auth.getIndex(), "Con_Approval_State", "Con_Id");
            if (ValidateUtil.isEmpty(authSql)) throw new JPMException(ResMsg.noPermission);
            return authSql;
        }
    }


    @Override
    public BaseResponse getConPayApproveByPid(String pId) {
        LambdaQueryWrapper<ConPay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConPay::getPId, pId);
        queryWrapper.eq(ConPay::getPayState, ApprovalStatusEnum.Already_Over.getIndex());
        queryWrapper.orderByDesc(ConPay::getPayStateTime);
        queryWrapper.last("limit 1");
        ConPay conPay = baseMapper.selectOne(queryWrapper);
        if (ValidateUtil.isEmpty(conPay)) {
            conPay = new ConPay();
        }
        return BaseResponse.ok(conPay);
    }

    @Override
    public BaseResponse getConPayApproveByConId(String conId) {
        LambdaQueryWrapper<ConPay> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ConPay::getConId, conId);
        queryWrapper.eq(ConPay::getPayState, ApprovalStatusEnum.Already_Over.getIndex());
        queryWrapper.orderByDesc(ConPay::getPayStateTime);
        queryWrapper.last("limit 1");
        ConPay conPay = baseMapper.selectOne(queryWrapper);
        if (ValidateUtil.isEmpty(conPay)) {
            conPay = new ConPay();
        }
        return BaseResponse.ok(conPay);
    }

    @Override
    public List<ConPay> getConPayListByPidHB(List<String> conIds) {
        if (ValidateUtil.isEmpty(conIds)) {
            return Collections.emptyList();
        }
        return baseMapper.getConPayListByPidHB(conIds);
    }

    @Override
    public BaseResponse getConPayListNJCJ2(ConPayRequest request, String uId) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (ValidateUtil.isEmpty(uId)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        String authSql = sysUserService.getAuthSql("Con_Pay", uId, "Pay_Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "Pay_State", "Pay_Id");
        QueryMap<ConPayRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("conName").put("pName").put("conType").put("conPartyBName")
                .put("payNumber").put("payType").put("payApply").put("payCreateUname").put("payIsWages")
                .put("payState").put("dId").put("payCode").put("pId").put("conId").put("thisProject").put("payTypeDetailed")
                .put("payApplicantName").put("payPayeeName").put("payDraweeName").put("payPayeeCa").put("ctdId")
                .put("payPayeeCaName").put("payPayeeBank").put("payRemark").put("payCategory").put("ctdCode")
                .put("payExpenseType").put("payApplicationDepartmentId").put("conCode").put("payFaCode").put("queryCpafApplicationSum")
                .put(request.getPayNumbers(), "payNumberStart", "payNumberEnd").put("conPartyAId").put("conPartyAName")
                .put(request.getPayApplys(), "payApplyStart", "payApplyEnd").put("conDepName").put("conDepartment")
                .put(request.getPayCreateTimes(), "conCreateTimeStart", "conCreateTimeEnd")
                .put(request.getConMoneyTotals(), "conMoneyTotalStart", "conMoneyTotalEnd")
                .put(request.getPayApplicationDates(), "payApplicationDateStart", "payApplicationDateEnd")
                .put("payIsFilter").put("payIsContain").put("payNumberStr").put("contractName").put("projectName")
                .put("payYear").put("payMonth").put("payCategoryPayment").put("fnCode").put("contractPayType");
        Page<ConPayResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConPayResponse> data = baseMapper.getConPayListNJCJ2(page, params.getQuery());
        return BaseResponse.ok(data, Math.toIntExact(page.getTotal()));
    }

    @Override
    public BaseResponse getLastPayBalanceByPId(String pId, String contractPayType, String id) {
        if (ValidateUtil.isEmpty(pId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        BigDecimal lastPayBalance = baseMapper.getLastPayBalanceByPId(pId, contractPayType, id);
        boolean exists = baseMapper.exists(Wrappers.<ConPay>lambdaQuery()
                .eq(ConPay::getPId, pId)
                .eq(ConPay::getContractPayType, contractPayType)
                .ne(ValidateUtil.isNotEmpty(id), ConPay::getPayId, id)
                .ne(ConPay::getPayState, ApprovalStatusEnum.Already_Over.getIndex())
                .ne(ConPay::getPayState, ApprovalStatusEnum.Invalid.getIndex())
        );
        Map<String, Object> data = new HashMap<>();
        data.put("lastPayBalance", lastPayBalance == null ? BigDecimal.ZERO : lastPayBalance);
        data.put("hasUnfinishedPay", exists);
        return BaseResponse.ok(data);
    }

    /**
     * 更新支付状态
     *
     * @param payId
     * @return
     */
    @Override
    public BaseResponse refreshPaymentStatus(String payId) {
        if (ValidateUtil.isEmpty(payId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ConPay conPay = this.getById(payId);
        if (ValidateUtil.isNull(conPay)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (conPay.getPayState().equals(ApprovalStatusEnum.Already_Over.getIndex())) {
            // 调用司库接口查询支付状态
            BaseResponse response = null;
            try {
                response = skApiFeign.selectBillsPayOrderNo(conPay.getPayCode());
            } catch (Exception e) {
                conPay.setPaymentStatus("03");
                this.updateById(conPay);
                return BaseResponse.fail("查询支付状态失败");
            }
            if (response.getSuccess()) {
                SKApiResponse<SKApiBodyDataT0004Response> skApiResponse = JacksonUtils.parseObject(JacksonUtils.toJSONString(response.getData()),
                        new TypeReference<SKApiResponse<SKApiBodyDataT0004Response>>() {
                        });
                if (ValidateUtil.isNull(skApiResponse)) {
                    return BaseResponse.fail("查询支付状态失败");
                }
                SKApiCmscloudResponse<SKApiBodyDataT0004Response> cmscloud = skApiResponse.getCmscloud();
                if (ValidateUtil.isNull(cmscloud)) {
                    return BaseResponse.fail("查询支付状态失败");
                }
                SKApiBodyDataResponse<SKApiBodyDataT0004Response> data = cmscloud.getBody().getData();
                if (ValidateUtil.isNull(data) || !"0000".equals(data.getResultCode())) {
                    return BaseResponse.fail("查询支付状态失败");
                }
                List<SKApiBodyDataT0004Response> resultSet = data.getResultSet();
                if (CollectionUtils.isEmpty(resultSet)) {
                    return BaseResponse.fail("查询支付状态失败");
                }
                SKApiBodyDataT0004Response responseData = resultSet.get(0);
                if (responseData.getPayStatus().equals(2)) {
                    conPay.setPaymentStatus("02");
                } else {
                    conPay.setPaymentStatus("03");
                }
            } else {
                conPay.setPaymentStatus("03");
                return BaseResponse.fail("查询支付状态失败");
            }
        } else {
            conPay.setPaymentStatus("01");
        }
        this.updateById(conPay);
        return BaseResponse.ok();
    }





    @Override
    public BaseResponse getConPayListWJT(ConContractRequest request, String uId) {

        if (ValidateUtil.isEmpty(request.getpId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }

        QueryMap<ConContractRequest> query = new QueryMap<>(request);
        query.put("pId").put("conName").put("conCode").put("conType").put("conPartyBName")
                .put("conPartyAId").put("conCreateTime")
                .put("conApprovalTime").put("conApprovalState").put("conApprovalUserId")
                .put("conApprovalUserName").put("conApprovalUserDepartmentId")
                .put("conApprovalUserDepartmentName").put("conApprovalUserDepartmentCode")
                .put("conCode").put("conState");
        // 查询合同数据
        List<ConContractResponse> conContracts = conContractMapper.getConContractByConProjectId(query.getQuery());
        List<String> conIds = conContracts.stream().map(ConContractResponse::getConId).collect(Collectors.toList());

        if (conIds.isEmpty()) {
            return BaseResponse.ok(Collections.emptyList(), 0);
        }

        // 获取投资控制结算定案模块的审定金额
        Map<String, BigDecimal> approveSumMap = getApproveSumMap(request.getpId());

        // 更新合同的审批金额
        conContracts.forEach(contract -> updateContractApprovalAmount(contract, approveSumMap));

        // 获取合同下审批通过的合同支付本期核准金额之和
        Map<String, BigDecimal> payApprovalSumMap = getPayApprovalSumMap(conIds);

        // 更新合同的支付金额
        conContracts.forEach(contract -> updateContractPayAmount(contract, payApprovalSumMap));

        // 计算 paidRatio 并更新合同
        conContracts.forEach(this::calculateAndSetPaidRatio);

        // 按时间排序支付记录
        Map<String, List<ConPay>> conPaysGroupedByConId = groupConPaysByConId(conIds);

        // 聚合支付数据并构建返回结果
        conContracts.forEach(contract -> aggregateAndSetPayDetails(contract, conPaysGroupedByConId));

        return BaseResponse.ok(conContracts, conContracts.size());
    }

    private Map<String, BigDecimal> getApproveSumMap(String pId) {
        LambdaQueryWrapper<InvSettlementApply> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(InvSettlementApply::getPId, pId)
                .eq(InvSettlementApply::getSaState, ApprovalStatusEnum.Already_Over.getIndex());
        List<InvSettlementApply> invSettlementApplies = invSettlementApplyMapper.selectList(wrapper);

        Map<String, BigDecimal> approveSumMap = new HashMap<>(invSettlementApplies.size());
        invSettlementApplies.forEach(invSettlementApply -> {
            String conId = invSettlementApply.getConId();
            if (conId != null) {
                BigDecimal amt = Optional.ofNullable(invSettlementApply.getSaApprovedAmt()).orElse(BigDecimal.ZERO);
                approveSumMap.merge(conId, amt, BigDecimal::add);
            }
        });
        return approveSumMap;
    }

    private void updateContractApprovalAmount(ConContractResponse contract, Map<String, BigDecimal> approveSumMap) {
        if (contract.getConApprovalState() != null && contract.getConApprovalState().equals(ApprovalStatusEnum.Already_Over.getIndex())) {
            contract.setSaApprovedAmtSum(approveSumMap.getOrDefault(contract.getConId(), BigDecimal.ZERO));
        } else {
            contract.setSaApprovedAmtSum(BigDecimal.ZERO);
        }
    }

    private Map<String, BigDecimal> getPayApprovalSumMap(List<String> conIds) {
        LambdaQueryWrapper<ConPay> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ConPay::getConId, conIds)
                .ne(ConPay::getPayState, ApprovalStatusEnum.Not_Started.getIndex())
                .ne(ConPay::getPayState, ApprovalStatusEnum.Invalid.getIndex());
        List<ConPay> conPays = baseMapper.selectList(wrapper);

        Map<String, BigDecimal> payApprovalSumMap = new HashMap<>(conPays.size());
        conPays.forEach(pay -> {
            if (pay.getConId() != null && pay.getPayState() != null && pay.getPayState().equals(ApprovalStatusEnum.Already_Over.getIndex())) {
                BigDecimal amt = Optional.ofNullable(pay.getPayApproval()).orElse(BigDecimal.ZERO);
                payApprovalSumMap.merge(pay.getConId(), amt, BigDecimal::add);
            }
        });
        return payApprovalSumMap;
    }

    private void updateContractPayAmount(ConContractResponse contract, Map<String, BigDecimal> payApprovalSumMap) {
        if (contract.getConApprovalState() != null && contract.getConApprovalState().equals(ApprovalStatusEnum.Already_Over.getIndex())) {
            contract.setPayApprovalSum(payApprovalSumMap.getOrDefault(contract.getConId(), BigDecimal.ZERO));
        } else {
            contract.setPayApprovalSum(BigDecimal.ZERO);
        }
    }

    private void calculateAndSetPaidRatio(ConContractResponse contract) {
        BigDecimal payApprovalSum = Optional.ofNullable(contract.getPayApprovalSum()).orElse(BigDecimal.ZERO);
        BigDecimal denom = determineDenom(contract);

        BigDecimal paidRatio = (denom == null || denom.compareTo(BigDecimal.ZERO) == 0)
                ? BigDecimal.ZERO
                : payApprovalSum.divide(denom, 6, RoundingMode.HALF_UP);

        contract.setPaidRatio(paidRatio.compareTo(BigDecimal.ONE) > 0 ? BigDecimal.ONE : paidRatio);
    }

    private BigDecimal determineDenom(ConContractResponse contract) {
        BigDecimal saSum = Optional.ofNullable(contract.getSaApprovedAmtSum()).orElse(BigDecimal.ZERO);
        if (saSum.compareTo(BigDecimal.ZERO) > 0) {
            return saSum;
        } else if ("费率".equals(contract.getConMoneyRate())) {
            return contract.getConMoneyTentative();
        } else {
            return contract.getConMoneyTotal();
        }
    }

    private Map<String, List<ConPay>> groupConPaysByConId(List<String> conIds) {
        Map<String, List<ConPay>> conPaysGroupedByConId = new HashMap<>(conIds.size());
        LambdaQueryWrapper<ConPay> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(ConPay::getConId, conIds)
                .ne(ConPay::getPayState, ApprovalStatusEnum.Not_Started.getIndex())
                .ne(ConPay::getPayState, ApprovalStatusEnum.Invalid.getIndex());
        List<ConPay> conPays = baseMapper.selectList(wrapper);
        conPays.forEach(p -> conPaysGroupedByConId
                .computeIfAbsent(p.getConId(), k -> new ArrayList<>())
                .add(p));
        return conPaysGroupedByConId;
    }


    private void aggregateAndSetPayDetails(ConContractResponse contract, Map<String, List<ConPay>> conPaysGroupedByConId) {
        String cid = contract.getConId();
        List<ConPay> allPays = conPaysGroupedByConId.getOrDefault(cid, Collections.emptyList());

        // 1. 检查并获取所有有效支付记录
        if (allPays.isEmpty()) {
            // 如果没有支付记录，返回空的 ConPayIntervalResponse 实例
            contract.setConPayIntervalResponseClosedList(new ConPayIntervalResponse());
            contract.setConPayIntervalResponseOpenList(new ConPayIntervalResponse());
            return;
        }

        // 2. 区分 Closed 和 Open 支付记录，并计算所有记录的最小年份（minNper）
        List<ConPay> closedPays = new ArrayList<>();
        List<ConPay> openPays = new ArrayList<>();
        Integer minYear = null;

        for (ConPay p : allPays) {
            if (p.getPayCreateTime() == null) continue;

            // 确定最小年份
            int year = Instant.ofEpochMilli(p.getPayCreateTime().getTime()).atZone(ZoneId.systemDefault())
                    .toLocalDate().getYear();
            if (minYear == null || year < minYear) {
                minYear = year;
            }

            boolean isClosed = ApprovalStatusEnum.Already_Over.getIndex().equals(p.getPayState());
            boolean isOpen = !isClosed && !ApprovalStatusEnum.Not_Started.getIndex().equals(p.getPayState())
                    && !ApprovalStatusEnum.Invalid.getIndex().equals(p.getPayState());

            if (isClosed) {
                closedPays.add(p);
            } else if (isOpen) {
                openPays.add(p);
            }
        }

        // 3. 构建两个状态的 ConPayIntervalResponse
        // 如果 minYear 仍然是 null，则传递 null，在构建方法中处理
        Integer finalMinYear = minYear;

        contract.setConPayIntervalResponseClosedList(
                createPayIntervalResponse(closedPays, finalMinYear)
        );
        contract.setConPayIntervalResponseOpenList(
                createPayIntervalResponse(openPays, finalMinYear)
        );
    }

    private ConPayIntervalResponse createPayIntervalResponse(List<ConPay> pays, Integer globalMinYear) {
        ConPayIntervalResponse response = new ConPayIntervalResponse();

        // 如果没有支付记录或最小年份无法确定，返回一个空 Map 的响应
        if (pays.isEmpty() || globalMinYear == null) {
            response.setMinNper(null); // 或根据实际业务要求设置为 0
            response.setNperPayData(new LinkedHashMap<>());
            return response;
        }

        response.setMinNper(globalMinYear);

        DateTimeFormatter ymFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");

        // 1. 聚合所有月份的实际总额
        // Map 的 Key 是 yyyy-MM，Value 是该月 PayApproval 总额
        Map<String, BigDecimal> monthAgg = new HashMap<>();

        // 2. 临时聚合年份总额
        Map<String, BigDecimal> yearTotalMap = new HashMap<>();

        pays.forEach(p -> {
            // 必须有支付时间才能聚合
            if (p.getPayCreateTime() == null) return;

            LocalDate ld = Instant.ofEpochMilli(p.getPayCreateTime().getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
            String yearMonth = ld.format(ymFormatter);
            String year = ld.format(yearFormatter);

            // 获取本期核准金额，如果为 null 则视为 0
            BigDecimal amt = Optional.ofNullable(p.getPayApproval()).orElse(BigDecimal.ZERO);

            // 仅处理金额大于 0 的记录（如果业务要求 $0$ 也要显示，则移除此处的过滤逻辑）
            if (amt.compareTo(BigDecimal.ZERO) <= 0) return;

            // 累加年月总额
            monthAgg.merge(yearMonth, amt, BigDecimal::add);
            // 累加年总额 (用于后续填充年份 Key)
            yearTotalMap.merge(year, amt, BigDecimal::add);
        });

        // 3. 构建最终的有序 Map：仅包含有数据的年和月
        Map<String, BigDecimal> resultNperPayData = new LinkedHashMap<>();

        // a. 获取并排序所有涉及的年份
        List<String> sortedYears = yearTotalMap.keySet().stream()
                .sorted()
                .collect(Collectors.toList());

        // b. 获取并排序所有涉及的年月
        List<String> sortedMonths = monthAgg.keySet().stream()
                .sorted()
                .collect(Collectors.toList());


        // 4. 遍历排序后的年份，并在其下插入对应的月份数据
        for (String yearKey : sortedYears) {
            // 插入年份汇总值
            resultNperPayData.put(yearKey, yearTotalMap.get(yearKey));

            // 插入该年份下的所有月份数据
            for (String monthKey : sortedMonths) {
                if (monthKey.startsWith(yearKey + "-")) {
                    resultNperPayData.put(monthKey, monthAgg.get(monthKey));
                }
            }
        }

        response.setNperPayData(resultNperPayData);
        return response;
    }

    /**
     * 根据合同id获取累计付款金额
     *
     * @param conId
     * @return
     */
    @Override
    public BaseResponse getPayActualPayTotalByConId(String conId) {
        if (ValidateUtil.isEmpty(conId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ConPay conPay = this.getOne(Wrappers.<ConPay>lambdaQuery()
                .eq(ConPay::getConId, conId)
                .eq(ConPay::getPayState, ApprovalStatusEnum.Already_Over.getIndex())
                .eq(ConPay::getContractPayType, "2")
                .orderByDesc(ConPay::getPayCreateTime)
                .last(" limit 1 ")
        );
        if (ValidateUtil.isNotNull(conPay)) {
            return BaseResponse.ok(conPay.getPayActualPayTotal().add(conPay.getPayApply()));
        } else {
            return BaseResponse.ok();
        }
    }

    /**
     * 支付编码重复性校验
     *
     * @param payCode
     * @param payId
     * @return
     */
    @Override
    public BaseResponse judgmentPayCodeRepeat(String payCode, String payId) {
        if (ValidateUtil.isEmpty(payCode)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        boolean exists = baseMapper.exists(Wrappers.<ConPay>lambdaQuery()
                .eq(ConPay::getPayCode, payCode)
                .ne(ValidateUtil.isNotEmpty(payId), ConPay::getPayId, payId)
        );
        if (exists) {
            return BaseResponse.fail(ResMsg.codeIsRepeat);
        } else {
            return BaseResponse.ok();
        }
    }

    /**
     * 获取截止上期累计支付
     *
     * @param pId
     * @param payCategory
     * @param payPayeeNamePro
     * @return
     */
    @Override
    public BaseResponse getLastPayActualPayTotal(String pId, String payCategory, String payPayeeNamePro) {
        if (ValidateUtil.isAnObjEmpty(pId, payCategory, payPayeeNamePro)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        BigDecimal lastPayActualPayTotal = baseMapper.getLastPayActualPayTotal(pId, payCategory, payPayeeNamePro);
        return BaseResponse.ok(lastPayActualPayTotal);
    }
}
