package com.jpmsoft.projectProducer.service.fund.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.projectProducer.mapper.fund.BankAccountInfoMapper;
import com.jpmsoft.projectProducer.pojo.dto.fund.BankAccountInfoDto;
import com.jpmsoft.projectProducer.pojo.entity.fund.BankAccountInfo;
import com.jpmsoft.projectProducer.pojo.request.fund.BankAccountInfoRequest;
import com.jpmsoft.projectProducer.pojo.vo.fund.BankAccountInfoVo;
import com.jpmsoft.projectProducer.service.fund.IBankAccountInfoService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class IBankAccountInfoServiceImpl extends FileImpl<BankAccountInfoMapper, BankAccountInfo, BankAccountInfoDto> implements IBankAccountInfoService {
    @Override
    public BaseResponse getBankAccountInfoList(BankAccountInfoRequest request,String uid) {
        log.info("request: {}", JacksonUtils.toJSONString(request));
        QueryMap<BankAccountInfoRequest> params = new QueryMap<>(request);
        params.put("pName").put("pId").put("companyID").put("companyName").put("accountType");
        IPage<BankAccountInfoVo> page = new Page<>(request.getPage(), request.getSize());
        log.info("params:"+JacksonUtils.toJSONString(params));
        List<BankAccountInfoVo> bankAccountInfoVos = baseMapper.getBankAccountInfoList(page, params.getQuery());
        for(BankAccountInfoVo bankAccountInfoVo:bankAccountInfoVos){
            setCompanyName(bankAccountInfoVo);
        }
        return BaseResponse.ok(bankAccountInfoVos, (int) page.getTotal());
    }

    @Override
    public BaseResponse getBankAccountInfoByID(BankAccountInfoRequest request,String uid) {
        log.info("request:{}"+ JacksonUtils.toJSONString(request));
        QueryMap<BankAccountInfoRequest> params = new QueryMap<>(request);
        params.put("id");
        BankAccountInfoVo bankAccountInfoVo= baseMapper.getBankAccountInfo(params.getQuery());
        setCompanyName(bankAccountInfoVo);
        return BaseResponse.ok(bankAccountInfoVo);
    }

    private void setCompanyName(BankAccountInfoVo bankAccountInfoVo) {
        String company=bankAccountInfoVo.getCompanyName();
        if ("一分中心（建管）".equals(company))  {
            bankAccountInfoVo.setCompanyID("8579d4500aa9480c9a493aad2cbde90e");
            bankAccountInfoVo.setCompanyName("南京城市建设管理集团有限公司");
        } else if ("二分中心（隧桥）".equals(company)) {
            bankAccountInfoVo.setCompanyID("0ee7cd6cb4ca4a8ab5b9faba69f511c5");
            bankAccountInfoVo.setCompanyName("南京城建隧桥智慧管理有限公司");
        } else if ("三分中心（市政）".equals(company)) {
            bankAccountInfoVo.setCompanyID("0cd6cc86a51043e09fce3672110f5354");
            bankAccountInfoVo.setCompanyName("南京市城建市政工程管理有限公司");
        }
    }

}
