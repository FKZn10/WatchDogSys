package com.jpmsoft.projectProducer.service.fund.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.fund.EngineeringFundApplicationFundNumberDetailMapper;
import com.jpmsoft.projectProducer.mapper.fund.FundNumberInfoMapper;
import com.jpmsoft.projectProducer.pojo.dto.fund.FundNumberInfoDto;
import com.jpmsoft.projectProducer.pojo.entity.fund.EngineeringFundApplicationFundNumberDetail;
import com.jpmsoft.projectProducer.pojo.entity.fund.FundNumberInfo;
import com.jpmsoft.projectProducer.pojo.request.fund.FundNumberInfoRequest;
import com.jpmsoft.projectProducer.pojo.vo.fund.FundNumberInfoVo;
import com.jpmsoft.projectProducer.service.fund.IFundNumberInfoService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class IFundNumberInfoServiceImpl extends BaseFileImpl<FundNumberInfoMapper,FundNumberInfo, FundNumberInfoDto> implements IFundNumberInfoService {
    @Autowired
    private EngineeringFundApplicationFundNumberDetailMapper engineeringFundApplicationFundNumberDetailMapper;
    @Autowired
    private ISysFileService sysFileService;
    @Autowired
    private ISysFileAnnexService sysFileAnnexService;
    @Override
    public BaseResponse createOrUpdateFundNumberInfo(FundNumberInfoDto dto) {
        String primaryId = dto.getId();
        if(StrUtil.isEmpty(primaryId)){
            primaryId = GenerateUUID.getUUID();
            FundNumberInfo fundNumberInfo=new FundNumberInfo();
            Integer flowStatus = ApprovalStatusEnum.Not_Started.getIndex();
            dto.setState(flowStatus);
            BeanUtil.copyProperties(dto,fundNumberInfo);
            fundNumberInfo.setId(primaryId);
            baseMapper.insert(fundNumberInfo);
            //创建流程
            if(sysFileService.FileHelper(fundNumberInfo,dto,"create",dto.getFileRelationId())){
                sysFileAnnexService.addFormRefreshRelationFile(dto.getFileRelationId(), fundNumberInfo.getId());
            }else{
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
        }else{
            FundNumberInfo fundNumberInfo = baseMapper.selectById(primaryId);
            BeanUtil.copyProperties(dto,fundNumberInfo);
            baseMapper.updateById(fundNumberInfo);
            sysFileAnnexService.refreshRelationFile(dto.getFileRelationId(),dto.getFileIds(),dto.getId());
        }
        return BaseResponse.ok(primaryId);
    }

    @Override
    public BaseResponse getFundNumberInfoList(FundNumberInfoRequest request,String uid) {
        log.info("request:"+ JacksonUtils.toJSONString(request));
        QueryMap<FundNumberInfoRequest> params = new QueryMap<>(request);
        params.put("pName").put("pId").put("fnName").put("fnCode");
        IPage<FundNumberInfoVo> page = new Page<>(request.getPage(), request.getSize());
        log.info("params:"+JacksonUtils.toJSONString(params));
        List<FundNumberInfoVo> fundNumberInfoVos = baseMapper.getFundNumberInfoList(page, params.getQuery());
        //获取余额
        fundNumberInfoVos.forEach(fundNumberInfoVo -> {
            BigDecimal fnAmount =fundNumberInfoVo.getFnAmount();
            BigDecimal fnBalance;
            List<EngineeringFundApplicationFundNumberDetail> engineeringFundApplicationFundNumberDetails
                    = engineeringFundApplicationFundNumberDetailMapper.selectByFnId(fundNumberInfoVo.getId());
            if (engineeringFundApplicationFundNumberDetails.size()==0){
                fnBalance=fnAmount;
            }else{
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
        log.info("paymentReceiptVos:"+JacksonUtils.toJSONString(fundNumberInfoVos));
        return BaseResponse.ok(fundNumberInfoVos, (int) page.getTotal());
    }

    @Override
    public BaseResponse getFundNumberInfoById(String id) {
        FundNumberInfoVo fundNumberInfoVo= baseMapper.selectFundNumberInfoById(id);
        return BaseResponse.ok(fundNumberInfoVo);
    }
}
