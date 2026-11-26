package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Contract.ConMeasurePriceRequest;
import com.jpmsoft.common.model.response.Contract.ConMeasurePriceResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ConMeasurePriceMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConContract;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConMeasurePrice;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConMeasurePriceInfoVo;
import com.jpmsoft.projectProducer.service.contract.IConContractService;
import com.jpmsoft.projectProducer.service.contract.IConMeasurePriceService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * 合同计量计价 Service 实现类
 *
 * @author tianhelin
 * @date 2025/10/21
 */
@Service
public class ConMeasurePriceImpl extends ServiceImpl<ConMeasurePriceMapper, ConMeasurePrice> implements IConMeasurePriceService {
    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IConContractService conContractService;
    @Resource
    private ISysUserService sysUserService;

    /**
     * 创建或更新合同计量计价
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateConMeasurePrice(ConMeasurePriceRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        // 限制同时只能有一个审批中的合同计量计价
        if (ValidateUtil.isNotNull(request.getState())
                && !ApprovalStatusEnum.Not_Started.getIndex().equals(request.getState())
                && !ApprovalStatusEnum.Invalid.getIndex().equals(request.getState())) {
            boolean pending = baseMapper.exists(Wrappers.<ConMeasurePrice>lambdaQuery()
                    .eq(ConMeasurePrice::getConId, request.getConId())
                    // .eq(ConMeasurePrice::getCmpType, request.getCmpType())
                    .ne(ValidateUtil.isNotEmpty(request.getId()), ConMeasurePrice::getId, request.getId())
                    .isNotNull(ConMeasurePrice::getState)
                    .notIn(ConMeasurePrice::getState,
                            Arrays.asList(ApprovalStatusEnum.Already_Over.getIndex(),
                                    ApprovalStatusEnum.Not_Started.getIndex(),
                                    ApprovalStatusEnum.Invalid.getIndex()))
            );
            if (pending) {
                return BaseResponse.fail("当前合同存在审批中的合同计量计价，无法提交新的审批！");
            }
        }
        ConMeasurePrice entity = new ConMeasurePrice();
        BeanUtils.copyProperties(request, entity);
        boolean isNew = ValidateUtil.isEmpty(request.getId());
        if (ValidateUtil.isEmpty(request.getpId())) {
            request.setpId(request.getPId());
        }
        if (isNew) {
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(),
                    entity.getCreateUname(), entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "create", request.getFileRelationId(), request.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        } else {
            sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(),
                    entity.getCreateUname(), entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(),
                    "update", request.getFileRelationId(), request.getSecId(), request.gettCode(), entity.getConId());
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        }
        this.saveOrUpdate(entity);
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 获取合同计量计价列表
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    public BaseResponse getConMeasurePriceList(ConMeasurePriceRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        String uId = redisUtils.getUid(token);
        if (ValidateUtil.isEmpty(uId)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }
        String authSql = sysUserService.getAuthSql("Con_Measure_Price", uId, "Create_Uid", OutUserAuthEnum.Approver_Creator.getIndex(), "State", "id");
        QueryMap<ConMeasurePriceRequest> queryMap = new QueryMap<>(request);
        queryMap.put("createUname").put("createTime").put("state").put("stateTime").put("pId")
                .put("pName").put("conId").put("conName").put("cmpPeriod").put("cmpDeclareStartTime")
                .put("cmpDeclareEndTime").put("cmpCurrentAmount").put("cmpAccumulatedAmount").put("cmpPayableAmount")
                .put("cmpDeductibleAmount").put("cmpActualPayment").put("cmpRemark").put("cmpType")
                .put("authSql", authSql);
        Page<ConMeasurePriceResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConMeasurePriceResponse> list = baseMapper.getConMeasurePriceList(page, queryMap.getQuery());
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }

    /**
     * 获取合同计量计价列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getConMeasurePriceListNoAuth(ConMeasurePriceRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        QueryMap<ConMeasurePriceRequest> queryMap = new QueryMap<>(request);
        queryMap.put("createUname").put("createTime").put("state").put("stateTime").put("pId")
                .put("pName").put("conId").put("conName").put("cmpPeriod").put("cmpDeclareStartTime")
                .put("cmpDeclareEndTime").put("cmpCurrentAmount").put("cmpAccumulatedAmount").put("cmpPayableAmount")
                .put("cmpDeductibleAmount").put("cmpActualPayment").put("cmpRemark").put("cmpType")
                .put("filterUsed").put("filterUsedGfp").put("payId").put("gfpId");
        Page<ConMeasurePriceResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ConMeasurePriceResponse> list = baseMapper.getConMeasurePriceList(page, queryMap.getQuery());
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }

    /**
     * 获取合同计量计价详情
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse getConMeasurePriceById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        ConMeasurePriceResponse response = baseMapper.getConMeasurePriceById(id);
        if (ValidateUtil.isNull(response)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        return BaseResponse.ok(response);
    }

    /**
     * 通过合同ID获取合同计量计价信息
     *
     * @param conId
     * @param id
     * @return
     */
    @Override
    public BaseResponse getConMeasurePriceInfoByConId(String conId, String id) {
        if (ValidateUtil.isEmpty(conId)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        long contract = conContractService.count(Wrappers.<ConContract>lambdaQuery().eq(ConContract::getConId, conId));
        if (contract <= 0) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        ConMeasurePriceInfoVo infoVo = new ConMeasurePriceInfoVo();

        // 是否存在审批未结束的数据
        boolean pending = baseMapper.exists(Wrappers.<ConMeasurePrice>lambdaQuery()
                .eq(ConMeasurePrice::getConId, conId)
                // .eq(ConMeasurePrice::getCmpType, cmpType)
                .isNotNull(ConMeasurePrice::getState)
                .ne(ValidateUtil.isNotEmpty(id), ConMeasurePrice::getId, id)
                .notIn(ConMeasurePrice::getState,
                        Arrays.asList(ApprovalStatusEnum.Already_Over.getIndex(),
                                ApprovalStatusEnum.Not_Started.getIndex(),
                                ApprovalStatusEnum.Invalid.getIndex()))
        );
        // 设置是否允许提交
        infoVo.setAllowSubmit(!pending);

        // 取最后一期的已完成数据
        ConMeasurePrice last = this.getOne(Wrappers.<ConMeasurePrice>lambdaQuery()
                .eq(ConMeasurePrice::getConId, conId)
                .eq(ConMeasurePrice::getState, ApprovalStatusEnum.Already_Over.getIndex())
                // .eq(ConMeasurePrice::getCmpType, cmpType)
                .ne(ValidateUtil.isNotEmpty(id), ConMeasurePrice::getId, id)
                .select(ConMeasurePrice::getCmpPeriod,
                        ConMeasurePrice::getCmpAccumulatedAmount,
                        ConMeasurePrice::getCmpCurrentAmount,
                        ConMeasurePrice::getCmpPayableAmount)
                .orderByDesc(ConMeasurePrice::getCmpPeriod)
                .last("limit 1")
        );

        if (ValidateUtil.isNull(last)) {
            infoVo.setCurrentCmpPeriod(1);
            infoVo.setCurrentCmpAccumulatedAmount(BigDecimal.ZERO);
            infoVo.setLatestCmpAccumulatedAmount(BigDecimal.ZERO);
            return BaseResponse.ok(infoVo);
        }

        int lastPeriod = ValidateUtil.isNull(last.getCmpPeriod()) ? 0 : last.getCmpPeriod();
        BigDecimal acc = ValidateUtil.isNull(last.getCmpAccumulatedAmount()) ? BigDecimal.ZERO : last.getCmpAccumulatedAmount();
        BigDecimal cur = ValidateUtil.isNull(last.getCmpCurrentAmount()) ? BigDecimal.ZERO : last.getCmpCurrentAmount();
        BigDecimal payable = ValidateUtil.isNull(last.getCmpPayableAmount()) ? BigDecimal.ZERO : last.getCmpPayableAmount();

        infoVo.setCurrentCmpPeriod(lastPeriod + 1);
        infoVo.setCurrentCmpAccumulatedAmount(acc.add(cur));
        infoVo.setLatestCmpAccumulatedAmount(payable);
        return BaseResponse.ok(infoVo);
    }
}
