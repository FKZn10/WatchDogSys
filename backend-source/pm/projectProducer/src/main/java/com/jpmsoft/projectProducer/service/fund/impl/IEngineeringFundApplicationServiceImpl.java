package com.jpmsoft.projectProducer.service.fund.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.FlowStatusEnum;
import com.jpmsoft.common.model.request.Contract.EngineeringFundApplicationRejectRequest;
import com.jpmsoft.common.model.response.Project.PreProjectResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.fund.*;
import com.jpmsoft.projectProducer.mapper.project.PreProjectMapper;
import com.jpmsoft.projectProducer.pojo.dto.fund.EngineeringFundApplicationBankDetailDto;
import com.jpmsoft.projectProducer.pojo.dto.fund.EngineeringFundApplicationDto;
import com.jpmsoft.projectProducer.pojo.dto.fund.EngineeringFundApplicationFundNumberDetailDto;
import com.jpmsoft.projectProducer.pojo.entity.fund.EngineeringFundApplication;
import com.jpmsoft.projectProducer.pojo.entity.fund.EngineeringFundApplicationBankDetail;
import com.jpmsoft.projectProducer.pojo.entity.fund.EngineeringFundApplicationFundNumberDetail;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.request.ReturnInitiatorDto;
import com.jpmsoft.projectProducer.pojo.request.ThirdSystemConfig;
import com.jpmsoft.projectProducer.pojo.request.fund.EngineeringApplyRequest;
import com.jpmsoft.projectProducer.pojo.request.fund.EngineeringApplyYjbgRequest;
import com.jpmsoft.projectProducer.pojo.request.fund.EngineeringFundApplicationRequest;
import com.jpmsoft.projectProducer.pojo.response.EFAInfoResponse;
import com.jpmsoft.projectProducer.pojo.response.EFAResponse;
import com.jpmsoft.projectProducer.pojo.vo.fund.*;
import com.jpmsoft.projectProducer.service.fund.IEngineeringFundApplicationService;
import com.jpmsoft.projectProducer.service.other.IApiUtilsService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.SignatureUtil;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class IEngineeringFundApplicationServiceImpl extends BaseFileImpl<EngineeringFundApplicationMapper, EngineeringFundApplication, EngineeringFundApplicationDto>
        implements IEngineeringFundApplicationService {
    @Autowired
    private EngineeringFundApplicationFundNumberDetailMapper engineeringFundApplicationFundNumberDetailMapper;
    @Autowired
    private EngineeringFundApplicationBankDetailMapper engineeringFundApplicationBankDetailMapper;
    @Autowired
    private FundNumberInfoMapper fundNumberInfoMapper;
    @Autowired
    private GroupFundPlanMapper groupFundPlanMapper;
    @Autowired
    private PreProjectMapper preProjectMapper;
    @Autowired
    private BankAccountInfoMapper bankAccountInfoMapper;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    private ISysConfigService sysConfigService;

    @Resource
    private IApiUtilsService apiUtilsService;


    @Override
    public BaseResponse createOrUpdateEngineeringFundApplication(EngineeringFundApplicationDto dto) {
        String primaryId = dto.getId();
        if (StrUtil.isEmpty(primaryId)) {
            primaryId = GenerateUUID.getUUID();
            EngineeringFundApplication engineeringFundApplication = new EngineeringFundApplication();
            Integer flowStatus = ApprovalStatusEnum.Not_Started.getIndex();
            dto.setEfaOwnerCompanyName("南京市城市建设投资控股（集团）有限责任公司");
            dto.setState(flowStatus);
            BeanUtil.copyProperties(dto, engineeringFundApplication);
            engineeringFundApplication.setId(primaryId);
            getDetails(dto, primaryId);
            baseMapper.insert(engineeringFundApplication);
            //创建流程
            if (sysFileService.FileHelper(engineeringFundApplication, dto, "create", dto.getFileRelationId())) {
                sysFileAnnexService.addFormRefreshRelationFile(dto.getFileRelationId(), engineeringFundApplication.getId());
            } else {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
        } else {
            EngineeringFundApplication engineeringFundApplication = baseMapper.selectById(primaryId);
            BeanUtil.copyProperties(dto, engineeringFundApplication);
            //禁用旧的填报值
            deleteDetailByEfaId(primaryId);
            getDetails(dto, primaryId);
            baseMapper.updateById(engineeringFundApplication);
            sysFileAnnexService.refreshRelationFile(dto.getFileRelationId(), dto.getFileIds(), dto.getId());
        }
        return BaseResponse.ok(primaryId);
    }

    private void getDetails(EngineeringFundApplicationDto dto, String primaryId) {
        List<EngineeringFundApplicationFundNumberDetail> details = new ArrayList<>();
        if (dto.getDetailDtos() != null) {
            List<EngineeringFundApplicationFundNumberDetailDto> dtos = dto.getDetailDtos();
            if (CollectionUtil.isNotEmpty(dtos)) {
                for (EngineeringFundApplicationFundNumberDetailDto detailsDtos : dtos) {
                    detailsDtos.setId(GenerateUUID.getUUID());
                    detailsDtos.setEfaId(primaryId);
                    EngineeringFundApplicationFundNumberDetail detailsObj = new EngineeringFundApplicationFundNumberDetail();
                    BeanUtil.copyProperties(detailsDtos, detailsObj);
                    details.add(detailsObj);
                }
            }
        }
        List<EngineeringFundApplicationBankDetail> bankdetails = new ArrayList<>();
        if (dto.getBankDetailDtos() != null) {
            List<EngineeringFundApplicationBankDetailDto> bankdetailsdtos = dto.getBankDetailDtos();
            if (CollectionUtil.isNotEmpty(bankdetailsdtos)) {
                for (EngineeringFundApplicationBankDetailDto detailsDtos : bankdetailsdtos) {
                    detailsDtos.setId(GenerateUUID.getUUID());
                    detailsDtos.setEfaId(primaryId);
                    EngineeringFundApplicationBankDetail detailsObj = new EngineeringFundApplicationBankDetail();
                    BeanUtil.copyProperties(detailsDtos, detailsObj);
                    bankdetails.add(detailsObj);
                }
            }
        }
        if (CollectionUtil.isNotEmpty(details)) {
            engineeringFundApplicationFundNumberDetailMapper.addBatch(details);
        }
        if (CollectionUtil.isNotEmpty(bankdetails)) {
            engineeringFundApplicationBankDetailMapper.addBatch(bankdetails);
        }
    }

    private void deleteDetailByEfaId(String resultId) {
        List<EngineeringFundApplicationFundNumberDetail> details = engineeringFundApplicationFundNumberDetailMapper.getDetailByEfaId(resultId);
        if (CollectionUtil.isNotEmpty(details)) {
            for (EngineeringFundApplicationFundNumberDetail detail : details) {
                engineeringFundApplicationFundNumberDetailMapper.deleteById(detail.getId());
            }
        }
        List<EngineeringFundApplicationBankDetail> bankdetails = engineeringFundApplicationBankDetailMapper.getDetailByEfaId(resultId);
        if (CollectionUtil.isNotEmpty(bankdetails)) {
            for (EngineeringFundApplicationBankDetail detail : bankdetails) {
                engineeringFundApplicationBankDetailMapper.deleteById(detail.getId());
            }
        }
    }

    @Override
    public BaseResponse approvalData(EngineeringFundApplicationRequest request) {
        String id = request.getId();
        EngineeringFundApplication entity;
        SysFile fileEntity;
        if (StrUtil.isEmpty(id)) {
            return new BaseResponse("信息不存在，请刷新重试！");
        } else {
            entity = baseMapper.selectById(id);
            if (entity == null)
                return new BaseResponse("信息不存在，请刷新重试！");

            fileEntity = sysFileService.getById(id);
            //流程状态 ：0、未发起流程（暂存） 1、审核中 2、退回发起人（审核不通过） 3、流程停止 4、流程已启动 5、流程等待中 6、流程暂停中 7、审核通过 8、流程改变中 9、流程退回
            String fileFlowStatus = fileEntity.getFileflowstatus();
            entity.setState(Integer.valueOf(fileFlowStatus));
            entity.setStateTime(new Date());
            baseMapper.updateById(entity);
        }

        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getEngineeringFundApplicationList(EngineeringFundApplicationRequest request, String uid) {
        QueryMap<EngineeringFundApplicationRequest> params = new QueryMap<>(request);
        params.put("projectName").put("projectId");
        IPage<EngineeringFundApplicationVo> page = new Page<>(request.getPage(), request.getSize());
        List<EngineeringFundApplicationVo> engineeringFundApplicationVos
                = baseMapper.getEngineeringFundApplicationList(params.getQuery(), page);
        if (CollectionUtil.isNotEmpty(engineeringFundApplicationVos)) {
            for (EngineeringFundApplicationVo engineeringFundApplicationVo : engineeringFundApplicationVos) {
                if (engineeringFundApplicationVo.getEfaProjectTotal() == null) {
                    if (engineeringFundApplicationVo.getPInvTotal() != null) {
                        engineeringFundApplicationVo.setEfaProjectTotal(engineeringFundApplicationVo.getPInvTotal());
                    }
                    if (engineeringFundApplicationVo.getPApproveBudget() != null) {
                        engineeringFundApplicationVo.setEfaProjectTotal(engineeringFundApplicationVo.getPApproveBudget());
                    }
                }
            }
        }
        log.info("engineeringFundApplicationVos:{}", JacksonUtils.toJSONString(engineeringFundApplicationVos));
        return BaseResponse.ok(engineeringFundApplicationVos, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getEngineeringFundApplicationById(String id) {
        EngineeringFundApplicationVo engineeringFundApplicationVo = baseMapper.getEngineeringFundApplicationById(id);
        if (engineeringFundApplicationVo.getEfaProjectTotal() == null) {
            if (engineeringFundApplicationVo.getPInvTotal() != null) {
                engineeringFundApplicationVo.setEfaProjectTotal(engineeringFundApplicationVo.getPInvTotal());
            }
            if (engineeringFundApplicationVo.getPApproveBudget() != null) {
                engineeringFundApplicationVo.setEfaProjectTotal(engineeringFundApplicationVo.getPApproveBudget());
            }
        }
        log.info("engineeringFundApplicationVo:{}", JacksonUtils.toJSONString(engineeringFundApplicationVo));
        return BaseResponse.ok(engineeringFundApplicationVo);
    }

    @Override
    public BaseResponse getFundNumberInfo(EngineeringFundApplicationRequest request) {
        if (StrUtil.isEmpty(request.getProjectId())) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        if (request.getEfaApplyAmount() == null || request.getEfaApplyAmount().compareTo(BigDecimal.ZERO) <= 0) {
            return BaseResponse.fail("请填写正确的本次申请金额");
        }
        BigDecimal efaApplyAmount = request.getEfaApplyAmount();
        List<FundNumberInfoVo> list = fundNumberInfoMapper.selectByPId(request.getProjectId());
        list.forEach(fundNumberInfoVo -> {
            BigDecimal fnAmount = fundNumberInfoVo.getFnAmount();
            BigDecimal fnBalance;
            List<EngineeringFundApplicationFundNumberDetail> engineeringFundApplicationFundNumberDetails
                    = engineeringFundApplicationFundNumberDetailMapper.selectByFnId(fundNumberInfoVo.getId());
            if (engineeringFundApplicationFundNumberDetails.isEmpty()) {
                fnBalance = fnAmount;
            } else {
                BigDecimal efaOccupancyAmount = engineeringFundApplicationFundNumberDetails.stream()
                    .map(EngineeringFundApplicationFundNumberDetail::getEfaOccupancyAmount)
                    .filter(Objects::nonNull)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
                if (efaOccupancyAmount.compareTo(fnAmount) >= 0) {
                    fnBalance = BigDecimal.ZERO;
                } else {
                    fnBalance = fnAmount.subtract(efaOccupancyAmount);
                }
            }
            fundNumberInfoVo.setFnBalance(fnBalance);
        });
        list = list.stream()
            .filter(fundNumberInfoVo -> fundNumberInfoVo.getFnBalance() != null && fundNumberInfoVo.getFnBalance().compareTo(BigDecimal.ZERO) > 0)
            .sorted(Comparator.comparing(FundNumberInfoVo::getFnBalance).reversed())
            .collect(Collectors.toList());
        List<EngineeringFundApplicationFundNumberDetailVo> vos = new ArrayList<>();
        for (FundNumberInfoVo vo : list) {
            EngineeringFundApplicationFundNumberDetailVo engineeringFundApplicationFundNumberDetailVo = new EngineeringFundApplicationFundNumberDetailVo();
            BeanUtil.copyProperties(vo, engineeringFundApplicationFundNumberDetailVo);
            if (efaApplyAmount.compareTo(vo.getFnBalance()) >= 0) {
                engineeringFundApplicationFundNumberDetailVo.setEfaOccupancyAmount(vo.getFnBalance());
                efaApplyAmount = efaApplyAmount.subtract(vo.getFnBalance());
            } else {
                engineeringFundApplicationFundNumberDetailVo.setEfaOccupancyAmount(efaApplyAmount);
            }
            vos.add(engineeringFundApplicationFundNumberDetailVo);
        }
        log.info("list:{}", JacksonUtils.toJSONString(vos));
        return BaseResponse.ok(vos);
    }

    @Override
    public BaseResponse getBankInfo(EngineeringFundApplicationRequest request) {
        if (StrUtil.isEmpty(request.getProjectId())) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        String pCompetentDepartment;
        String pCompetentDepartmentName;
        String pConstructionUnit = "";
        String pConstructionUnitName = "";
        List<BankAccountInfoVo> list = bankAccountInfoMapper.selectByPId(request.getProjectId());
        for (BankAccountInfoVo bankAccountInfoVo : list) {
            setCompanyName(bankAccountInfoVo);
        }
        PreProjectResponse pojo = preProjectMapper.getPreProjectById(request.getProjectId());
        if (ValidateUtil.isNull(pojo)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        } else {
            pCompetentDepartment = pojo.getpCompetentDepartment();
            pCompetentDepartmentName = pojo.getpCompetentDepartmentName();
        }
        if ("一分中心（建管）".equals(pCompetentDepartmentName)) {
            pConstructionUnit = "8579d4500aa9480c9a493aad2cbde90e";
            pConstructionUnitName = "南京城市建设管理集团有限公司";
        } else if ("二分中心（隧桥）".equals(pCompetentDepartmentName)) {
            pConstructionUnit = "0ee7cd6cb4ca4a8ab5b9faba69f511c5";
            pConstructionUnitName = "南京城建城市运营集团有限公司";
        } else if ("三分中心（市政）".equals(pCompetentDepartmentName)) {
            pConstructionUnit = "0cd6cc86a51043e09fce3672110f5354";
            pConstructionUnitName = "南京市城建市政工程管理有限公司";
        }
        List<BankAccountInfoVo> list2 = bankAccountInfoMapper.selectByCompetentDepartment(pConstructionUnit, pConstructionUnitName);
        list.addAll(list2);
        log.info("list:{}", JacksonUtils.toJSONString(list));
        return BaseResponse.ok(list);
    }

    private void setCompanyName(BankAccountInfoVo bankAccountInfoVo) {
        String company = bankAccountInfoVo.getCompanyName();
        if ("一分中心（建管）".equals(company)) {
            bankAccountInfoVo.setCompanyID("8579d4500aa9480c9a493aad2cbde90e");
            bankAccountInfoVo.setCompanyName("南京城市建设管理集团有限公司");
        } else if ("二分中心（隧桥）".equals(company)) {
            bankAccountInfoVo.setCompanyID("0ee7cd6cb4ca4a8ab5b9faba69f511c5");
            bankAccountInfoVo.setCompanyName("南京城建城市运营集团有限公司");
        } else if ("三分中心（市政）".equals(company)) {
            bankAccountInfoVo.setCompanyID("0cd6cc86a51043e09fce3672110f5354");
            bankAccountInfoVo.setCompanyName("南京市城建市政工程管理有限公司");
        }
    }

    @Override
    public BaseResponse receiveCallback(EFAResponse response) {
        if (ValidateUtil.isNull(response)) {
            return BaseResponse.fail(404, ResMsg.infoNotExist);
        }

        //0:未支付---指本次支付未开始。 未发送到集团业财系统
        //1:支付成功---指本次支付成功，发送到集团业财系统并返回成功
        //2:退回---退回业务系统。发送到集团业财系统并返回退回
        //3:放弃支付---业财系统不支付。 发送到集团业财系统并放弃支付
        //4::支付中---支付中。已发送到集团业财系统
        ThirdSystemConfig thirdSystemConfig;
        String code = "ThirdSystemConfig";
        String str = sysConfigService.getConfigByCode(code);
        if (StrUtil.isNotEmpty(str)) {
            thirdSystemConfig = JacksonUtils.parseObject(str, ThirdSystemConfig.class);
        } else {
            return BaseResponse.fail(404, "配置信息缺失");
        }

        String AppKey = thirdSystemConfig.getAppKey();
        String decryptMsg = null;
        try {
            decryptMsg = decrypt(response.getEchostr(), AppKey);
        } catch (Exception e) {
            log.error("method receiveCallback error!", e);
            return BaseResponse.fail(500, e.getMessage());
        }
        EFAInfoResponse efaInfoResponse = JacksonUtils.parseObject(decryptMsg, EFAInfoResponse.class);
        if (ValidateUtil.isNull(efaInfoResponse)) {
            return BaseResponse.fail(404, ResMsg.infoNotExist);
        }
        EngineeringFundApplication engineeringFundApplication = baseMapper.selectById(efaInfoResponse.getPayApplyId());
        if (ValidateUtil.isNull(engineeringFundApplication)) {
            return BaseResponse.fail(404, ResMsg.infoNotExist);
        }
        if ("3".equals(efaInfoResponse.getOper())) {
            engineeringFundApplication.setState(ApprovalStatusEnum.Already_Over.getIndex());
            if ("3".equals(efaInfoResponse.getOper())) {
                engineeringFundApplication.setEfaPayState(1);
            }
            baseMapper.updateById(engineeringFundApplication);
        } else if ("9".equals(efaInfoResponse.getOper()) || "1".equals(efaInfoResponse.getOper())) {
            engineeringFundApplication.setEfaPayState(2);
            baseMapper.updateById(engineeringFundApplication);
            // 退回流程
            engineeringFundApplication.setEfaPayState(2);
            baseMapper.updateById(engineeringFundApplication);
            String payApplyId = efaInfoResponse.getPayApplyId();
            ReturnInitiatorDto returnInitiatorDto = new ReturnInitiatorDto();
            returnInitiatorDto.setFileId(payApplyId);
            returnInitiatorDto.setOpinion("第三方系统退回");
            BaseResponse rejectedFlowToInitiatorResponse = apiUtilsService.rejectFlowToInitiator(returnInitiatorDto);
            if (!Boolean.TRUE.equals(rejectedFlowToInitiatorResponse.getSuccess())) {
                log.warn("第三方系统回调接口，退回申请，申请id:{}，退回至发起人失败，原因:{}", payApplyId, rejectedFlowToInitiatorResponse.getMessage());
            }
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse sendData(EngineeringFundApplicationRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(404, ResMsg.infoNotExist);
        }
        if (ValidateUtil.isNull(request.getId())) {
            return BaseResponse.fail(404, ResMsg.infoNotExist);
        }

        EngineeringFundApplication engineeringFundApplication = baseMapper.selectById(request.getId());
        EngineeringFundApplicationVo engineeringFundApplicationVo = baseMapper.getEngineeringFundApplicationById(request.getId());

        ThirdSystemConfig thirdSystemConfig;
        String code = "ThirdSystemConfig";
        String str = sysConfigService.getConfigByCode(code);
        if (StrUtil.isNotEmpty(str)) {
            thirdSystemConfig = JacksonUtils.parseObject(str, ThirdSystemConfig.class);
        } else {
            return BaseResponse.fail(404, "配置信息缺失");
        }
        String IPHost = thirdSystemConfig.getIPHost();
        String AppID = thirdSystemConfig.getAppID();
        String AppKey = thirdSystemConfig.getAppKey();
        String CallBackURL = thirdSystemConfig.getCallBackURL();
        String ApplyTypeId = thirdSystemConfig.getApplyTypeId();
        Map<String, String> AccessTokenMap = GetAccessToken(IPHost, AppID, AppKey);
        String AccessToken = AccessTokenMap.get("AccessToken");
        if (StrUtil.isEmpty(AccessToken)) {
            return BaseResponse.fail(500, "AccessToken获取失败");
        }
        if ("0".equals(AccessTokenMap.get("flag"))) {
            String response = AccessTokenMap.get("response");
            return BaseResponse.fail(500, AccessToken + "---------" + response);
        }
        String url = IPHost + "/bmv6/service/mis/openplatform/api/addpayapply?accessToken=" + AccessToken;
        EngineeringApplyRequest engineeringApplyRequest = new EngineeringApplyRequest();
        //body体赋值
        engineeringApplyRequest.setPayApplyId(request.getId());
        engineeringApplyRequest.setApplyerName(engineeringFundApplicationVo.getEfaUserName());
        engineeringApplyRequest.setApplyTypeId(ApplyTypeId);
        engineeringApplyRequest.setCompanyNo("01");
        engineeringApplyRequest.setCompanyName("南京市城市建设投资控股(集团)有限责任公司(本部)");
        engineeringApplyRequest.setBusiNo(request.getId());
        engineeringApplyRequest.setRemark(engineeringFundApplicationVo.getEfaRemark());
        engineeringApplyRequest.setBusiUrl(null);
        engineeringApplyRequest.setCallBackFun(CallBackURL);
        engineeringApplyRequest.setAppId(AppID);
        engineeringApplyRequest.setPrjName(engineeringFundApplicationVo.getProjectName());
        engineeringApplyRequest.setPrjNo(engineeringFundApplicationVo.getPDhCode());
        //engineeringApplyRequest.setYzdw(null);
        engineeringApplyRequest.setYzdw("00");
//        if (engineeringFundApplicationVo.getPInvTotal()!=null){
//            engineeringApplyRequest.setGcztz(engineeringFundApplicationVo.getPInvTotal().toString());
//        }
//        if (engineeringFundApplicationVo.getPApproveBudget()!=null){
//            engineeringApplyRequest.setGcztz(engineeringFundApplicationVo.getPApproveBudget().toString());
//        }
        engineeringApplyRequest.setGcztz(engineeringFundApplicationVo.getEfaProjectTotal().toString());
        engineeringApplyRequest.setJbbm(engineeringFundApplicationVo.getDDhCode());
        engineeringApplyRequest.setFgld(engineeringFundApplicationVo.getDDhCodeLD());
        engineeringApplyRequest.setBqjhje(engineeringFundApplicationVo.getEfaPlanAmount());
        engineeringApplyRequest.setJtyapje(engineeringFundApplicationVo.getEfaArrangeAmount());
        engineeringApplyRequest.setJtzhye(engineeringFundApplicationVo.getEfaBalanceAmount());
        engineeringApplyRequest.setBcsqje(engineeringFundApplicationVo.getEfaApplyAmount());
        engineeringApplyRequest.setFkyj(engineeringFundApplicationVo.getEfaBasis());
        //获取依据表格
        List<EngineeringApplyYjbgRequest> engineeringApplyYjbgRequests = engineeringFundApplicationVo.getDetailDtos().stream().map(n -> {
            EngineeringApplyYjbgRequest model = new EngineeringApplyYjbgRequest();
            model.setWhmc(n.getFnName());
            model.setWhje(n.getFnAmount());
            model.setWhye(n.getFnBalance());
            model.setBqzy(n.getEfaOccupancyAmount());
            return model;
        }).collect(Collectors.toList());
        engineeringApplyRequest.setYjbg(engineeringApplyYjbgRequests);
        engineeringApplyRequest.setHandlerAccount(engineeringFundApplicationVo.getUDhAccount());
        List<BankAccountInfoVo> bankAccountInfoLists = (List<BankAccountInfoVo>) getBankInfo(request).getData();
        if (ValidateUtil.isNotNull(bankAccountInfoLists)) {
            BankAccountInfoVo infoVo = bankAccountInfoLists.stream().filter(k -> "01".equals(k.getAccountType())).findFirst().orElse(null);
            if (ValidateUtil.isNotNull(infoVo)) {
                engineeringApplyRequest.setGskdw(infoVo.getCompanyName());
                //engineeringApplyRequest.setGskdw("南京力诺建设有限公司2");

                String HLH = baseMapper.getDhBankAccount(engineeringFundApplicationVo.getPId(), infoVo.getCompanyID(), "01", infoVo.getBankAccount());
                engineeringApplyRequest.setGskkhh(HLH);
                engineeringApplyRequest.setGskzh(infoVo.getBankAccount());
            }
            BankAccountInfoVo infoVo2 = bankAccountInfoLists.stream().filter(k -> "02".equals(k.getAccountType())).findFirst().orElse(null);
            if (ValidateUtil.isNotNull(infoVo2)) {
                engineeringApplyRequest.setSskdw(infoVo2.getCompanyName());
                engineeringApplyRequest.setSkhyh(infoVo2.getBankName());
                engineeringApplyRequest.setSskid(infoVo2.getBankAccount());
            }
        }
        Integer AccountType = engineeringFundApplicationVo.getBankDetailDtos().stream().map(EngineeringFundApplicationBankDetailVo::getAccountType).findFirst().orElse(null);
        if (ValidateUtil.isNotNull(AccountType)) {
            if (AccountType.equals(1))
                engineeringApplyRequest.setZhlx("工程账户");
            else if (AccountType.equals(2))
                engineeringApplyRequest.setZhlx("数字货币账户");
            else
                engineeringApplyRequest.setZhlx("");
        } else
            engineeringApplyRequest.setZhlx("");
        String JsonResult = JacksonUtils.toJSONString(engineeringApplyRequest);
        String response = sendPost(url, JsonResult, null);
        try {
            JSONObject result = JSONObject.parseObject(response);
            if (result != null) {
                String status = result.getJSONObject("data").get("status").toString();
                if ("success".equals(status)) {
                    engineeringFundApplication.setEfaPayState(5);
                }
            }
        } catch (Exception e) {
            return BaseResponse.fail(response);
        }
        baseMapper.updateById(engineeringFundApplication);
        return BaseResponse.ok(response + "---------" + JsonResult);
    }

    @Override
    public BaseResponse reject(EngineeringFundApplicationRejectRequest engineeringFundApplicationRejectRequest) {
        String payApplyId = engineeringFundApplicationRejectRequest.getPayApplyId();
        String opinion = engineeringFundApplicationRejectRequest.getOpinion();
        EngineeringFundApplication engineeringFundApplication = getById(payApplyId);
        if (engineeringFundApplication == null) {
            return BaseResponse.fail("数据不存在");
        }
        if (FlowStatusEnum.SendBackStart.getIndex().equals(engineeringFundApplication.getState())) {
            return BaseResponse.ok();
        }
        ReturnInitiatorDto returnInitiatorDto = new ReturnInitiatorDto();
        returnInitiatorDto.setFileId(payApplyId);
        returnInitiatorDto.setOpinion(opinion);
        return apiUtilsService.rejectFlowToInitiator(returnInitiatorDto);
    }

    private Map<String, String> GetAccessToken(String IPHost, String AppID, String AppKey) {
//        AccessTokenRequest accessTokenRequest = new AccessTokenRequest();
//        accessTokenRequest.setAppId(AppID);
//        accessTokenRequest.setTimeStamp(String.valueOf(System.currentTimeMillis()));
        Map<String, String> map = new HashMap<>();
        JSONObject jsonData = new JSONObject();
        jsonData.put("appId", AppID);
        jsonData.put("timeStamp", String.valueOf(System.currentTimeMillis()));
        try {
            String signature = SignatureUtil.signature(AppID, AppKey, String.valueOf(System.currentTimeMillis()));
            jsonData.put("signature", signature);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        String AccessToken = "";
        String AccessTokenUrl = IPHost + "/bmv6/service/mis/openplatform/api/getAccessToken";
        String response = sendPost(AccessTokenUrl, jsonData.toJSONString(), null);
        try {
            JSONObject result = JSONObject.parseObject(response);
            AccessToken = result.getJSONObject("data").get("accessToken").toString();
            map.put("flag", "1");
            map.put("AccessToken", AccessToken);
        } catch (Exception e) {
            AccessToken = response;
            map.put("flag", "0");
            map.put("AccessToken", AccessToken);
            map.put("response", jsonData.toJSONString());
        }
        return map;
    }

    private HttpEntity<String> assemblyRequestParam(Object object) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        log.info("服务调用时:{}", JacksonUtils.toJSONString(object));
        HttpEntity<String> formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }

    public static String decrypt(String base64Content, String AppKey) throws Exception {
        //AES算法 CBC模式  PKCS7Padding填充
        String ALGORITHM = "AES/CBC/PKCS7Padding";
        //固定的向量值,不能动
        byte[] vectorKey = {1, 5, 2, 6, 3, 8, 7, 2, 2, 5, 2, 6, 3, 9, 7, 2};
        //固定的密钥32个字符(节),256位，各业务系统用业财发放的appkey
        String appKey = AppKey;

        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKey secretKey = new SecretKeySpec(appKey.getBytes(), "AES");
        IvParameterSpec iv = new IvParameterSpec(vectorKey);
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        byte[] content = Base64.getUrlDecoder().decode(base64Content);
        byte[] encrypted = cipher.doFinal(content);
        return new String(encrypted);
    }

    public static String sendPost(String url, String data, Map<String, String> headers) {

        BufferedReader in = null;
        String result = "";
        try {
            //创建连接
            URL uri = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) uri.openConnection();
            if (headers != null) {
                for (String key : headers.keySet()) {
                    String value = headers.get(key);
                    connection.addRequestProperty(key, value);
                }
            }
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setRequestProperty("Content-Type", "application/json");
            connection.connect();
            // POST请求
            DataOutputStream out = new DataOutputStream(connection.getOutputStream());
            String json = data;
            out.write(json.getBytes("utf-8"));
            out.flush();
            out.close();
            // 读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String lines;
            StringBuffer sb = new StringBuffer("");
            while ((lines = reader.readLine()) != null) {
                lines = URLDecoder.decode(lines, "utf-8");
                sb.append(lines);
            }
            result = sb.toString();
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (MalformedURLException e) {
            // e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            // e.printStackTrace();
        } catch (IOException e) {
            // e.printStackTrace();
        }
        return result;
    }

}
