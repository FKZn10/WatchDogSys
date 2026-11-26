package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.investment.FundPlanModifyMapper;
import com.jpmsoft.projectProducer.mapper.investment.InvFinalAccountsApplyMapper;
import com.jpmsoft.projectProducer.mapper.investment.MeasurementMapper;
import com.jpmsoft.projectProducer.pojo.dto.investment.FundPlanModifyDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPay;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConPayApplyFlow;
import com.jpmsoft.projectProducer.pojo.entity.investment.*;
import com.jpmsoft.projectProducer.pojo.entity.other.SysDictionary;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFile;
import com.jpmsoft.projectProducer.pojo.vo.investment.FundPlanModifyHandleVo;
import com.jpmsoft.projectProducer.pojo.vo.investment.InvestmentPlanVo;
import com.jpmsoft.projectProducer.service.contract.IConPayApplyFlowService;
import com.jpmsoft.projectProducer.service.contract.IConPayService;
import com.jpmsoft.projectProducer.service.investment.*;
import com.jpmsoft.projectProducer.service.other.ISysDictionaryService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MeasurementImpl implements IMeasurementService {

    @Resource
    private MeasurementMapper measurementMapper;
    @Override
    public BaseResponse getCalculationTenderPriceBySecId(String pId, String secId) {
        return BaseResponse.ok(measurementMapper.getCalculationTenderPriceBySecId(pId,secId));
    }

}
