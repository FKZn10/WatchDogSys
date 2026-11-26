package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.InvestmentControl.InvMonthVersionRequest;
import com.jpmsoft.common.model.response.InvestmentControl.InvMonthVersionResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.InvMonthVersionMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvCompleteMonth;
import com.jpmsoft.projectProducer.pojo.entity.investment.InvMonthVersion;
import com.jpmsoft.projectProducer.service.investment.IInvCompleteMonthService;
import com.jpmsoft.projectProducer.service.investment.IInvMonthVersionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class InvMonthVersionServiceImpl extends ServiceImpl<InvMonthVersionMapper, InvMonthVersion> implements IInvMonthVersionService {
    @Resource
    private IInvCompleteMonthService invCompleteMonthService;

    /**
     * 根据id集合保存版本
     *
     * @param ids
     */
    @Override
    @Transactional(rollbackFor = Exception.class, propagation = Propagation.REQUIRES_NEW)
    public void saveInvMonthVersionByIcmIds(List<String> ids) {
        if (ValidateUtil.isEmpty(ids)) {
            return;
        }
        List<InvCompleteMonth> invCompleteMonths = invCompleteMonthService.listByIds(ids);
        if (ValidateUtil.isEmpty(invCompleteMonths)) {
            return;
        }
        this.removeBatchByIds(ids);
        List<InvMonthVersion> collect = invCompleteMonths.stream().map(month -> {
            InvMonthVersion version = new InvMonthVersion();
            BeanUtils.copyProperties(month, version);
            version.setVersion(new Date());
            return version;
        }).collect(Collectors.toList());
        this.saveBatch(collect);
    }

    /**
     * 根据id获取版本信息
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse getInvMonthVersionById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        InvMonthVersion version = this.getById(id);
        if (ValidateUtil.isNull(version)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        InvMonthVersionResponse response = new InvMonthVersionResponse();
        BeanUtils.copyProperties(version, response);
        response.setIcmAnnualInvestment(response.getIcmAnnualInvestment() == null ? BigDecimal.ZERO : response.getIcmAnnualInvestment().divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP));
        response.setIcmMonthlyInvestment(response.getIcmMonthlyInvestment() == null ? BigDecimal.ZERO : response.getIcmMonthlyInvestment().divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP));
        response.setMonthCumulativeInv(response.getMonthCumulativeInv() == null ? BigDecimal.ZERO : response.getMonthCumulativeInv().divide(new BigDecimal(10000), 2, RoundingMode.HALF_UP));
        return BaseResponse.ok(response);
    }

    /**
     * 处理版本
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse createInvMonthVersion(InvMonthVersionRequest request) {
        if (ValidateUtil.isNull(request) || ValidateUtil.isEmpty(request.getIcmId())) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        this.removeById(request.getIcmId());
        InvMonthVersion version = new InvMonthVersion();
        BeanUtils.copyProperties(request, version);
        version.setVersion(new Date());
        version.setIcmAnnualInvestment(request.getIcmAnnualInvestment() == null ? BigDecimal.ZERO : request.getIcmAnnualInvestment().multiply(new BigDecimal(10000)));
        version.setIcmMonthlyInvestment(request.getIcmMonthlyInvestment() == null ? BigDecimal.ZERO : request.getIcmMonthlyInvestment().multiply(new BigDecimal(10000)));
        version.setMonthCumulativeInv(request.getMonthCumulativeInv() == null ? BigDecimal.ZERO : request.getMonthCumulativeInv().multiply(new BigDecimal(10000)));
        this.save(version);
        return BaseResponse.ok();
    }
}
