package com.jpmsoft.projectProducer.service.projectKanban.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.mapper.projectKanban.projectFundMapper;
import com.jpmsoft.projectProducer.pojo.vo.projectKanban.ProjectFundVO;
import com.jpmsoft.projectProducer.service.projectKanban.projectFundService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class projectFundImpl implements projectFundService {

    @Autowired
    private projectFundMapper projectFundMapper;
    @Override
    public BaseResponse getProjectFund(String pId) {

        ProjectFundVO projectFundVO = new ProjectFundVO();

        BigDecimal invTotal = Optional.ofNullable(projectFundMapper.getInvesToral(pId))
                .map(BigDecimal::new)
                .orElse(BigDecimal.ZERO)
                .divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP); // 保留6位小数

        BigDecimal contractTotal = Optional.ofNullable(projectFundMapper.getContractTotal(pId))
                .map(BigDecimal::new)
                .orElse(BigDecimal.ZERO)
                .divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP);

        BigDecimal payTotal = Optional.ofNullable(projectFundMapper.getPayTotal(pId))
                .map(BigDecimal::new)
                .orElse(BigDecimal.ZERO)
                .divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP);

        BigDecimal realPatTotal = Optional.ofNullable(projectFundMapper.getRealPatTotal(pId))
                .map(BigDecimal::new)
                .orElse(BigDecimal.ZERO)
                .divide(BigDecimal.valueOf(10000), 6, RoundingMode.HALF_UP);


        projectFundVO.setInvesToral(invTotal);
        projectFundVO.setContractTotal(contractTotal);
        projectFundVO.setPayTotal(payTotal);
        projectFundVO.setRealPatTotal(realPatTotal);

        return BaseResponse.ok(projectFundVO);


    }
}
