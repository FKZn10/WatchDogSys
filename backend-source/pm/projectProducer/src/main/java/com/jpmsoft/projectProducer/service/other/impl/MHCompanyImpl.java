package com.jpmsoft.projectProducer.service.other.impl;

import com.jpmsoft.projectProducer.mapper.other.MHCompanyMapper;
import com.jpmsoft.projectProducer.pojo.vo.other.MHCompanyResultVO;
import com.jpmsoft.projectProducer.pojo.vo.other.MHCompanyVO;
import com.jpmsoft.projectProducer.service.other.IMHCompanyService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MHCompanyImpl implements IMHCompanyService {

    @Resource
    private MHCompanyMapper mhCompanyMapper;

    @Override
    public List<MHCompanyResultVO> getCompanyListByApplyType(String applyType, String teId) {
        if (!StringUtils.hasText(applyType)) {
            return new ArrayList<>();
        }
        List<MHCompanyVO> companyList = mhCompanyMapper.getCompanyListByApplyType(applyType);
        
        if (companyList.isEmpty()) {
            return new ArrayList<>();
        }
        
        // 按批次分组
        Map<String, List<MHCompanyVO>> groupByBatch = companyList.stream()
                .collect(Collectors.groupingBy(MHCompanyVO::getBatchCode));
        
        List<MHCompanyResultVO> resultList = new ArrayList<>();
        
        for (Map.Entry<String, List<MHCompanyVO>> entry : groupByBatch.entrySet()) {
            String batchCode = entry.getKey();
            List<MHCompanyVO> batchCompanies = entry.getValue();
            
            MHCompanyResultVO resultVO = new MHCompanyResultVO();
            resultVO.setBatchCode(batchCode);
            
            // 获取单位类型（取第一个）
            if (!batchCompanies.isEmpty()) {
                resultVO.setPartyType(batchCompanies.get(0).getCompanyType());
            }
            
            // 累计合同暂估价（取第一个，同一批次应该相同）
            BigDecimal estimatedTotal = BigDecimal.ZERO;
            if (!batchCompanies.isEmpty() && batchCompanies.get(0).getConEstimatedAmountTotal() != null) {
                estimatedTotal = batchCompanies.get(0).getConEstimatedAmountTotal();
            }
            resultVO.setConEstimatedAmountTotal(estimatedTotal);
            
            // 构建单位列表
            List<MHCompanyResultVO.CompanyItem> companies = new ArrayList<>();
            BigDecimal totalUsedContractMoney = BigDecimal.ZERO;
            
            for (MHCompanyVO vo : batchCompanies) {
                MHCompanyResultVO.CompanyItem item = new MHCompanyResultVO.CompanyItem();
                item.setCompanyId(vo.getCompanyId());
                item.setCompanyName(vo.getCompanyName());
                
                BigDecimal usedContractMoney = vo.getUsedContractMoney();
                if (usedContractMoney == null) {
                    usedContractMoney = BigDecimal.ZERO;
                }
                item.setUsedContractMoney(usedContractMoney);
                totalUsedContractMoney = totalUsedContractMoney.add(usedContractMoney);
                
                companies.add(item);
            }
            
            resultVO.setCompanies(companies);
            
            // 剩余合同暂估价 = 累计合同暂估价 - 总使用合同暂估价
            BigDecimal remainingContractMoney = estimatedTotal.subtract(totalUsedContractMoney);
            resultVO.setRemainingContractMoney(remainingContractMoney);
            
            resultList.add(resultVO);
        }
        
        return resultList;
    }
}