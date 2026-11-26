package com.jpmsoft.gmgws.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.gmgws.mapper.ConContractMapper;
import com.jpmsoft.gmgws.pojo.entity.ConContract;
import com.jpmsoft.gmgws.pojo.vo.ContractTotlaVo;
import com.jpmsoft.gmgws.service.ConContractService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConContractServiceImpl  extends ServiceImpl<ConContractMapper, ConContract> implements ConContractService {
    @Override
    public List<ConContract> getConName(List<String> conName, List<String> pId) {
        LambdaQueryWrapper<ConContract> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(ConContract::getConProjectId,pId).in(ConContract::getConName,conName);
        List<ConContract> conContracts = baseMapper.selectList(queryWrapper);

        return conContracts;
    }

    @Override
    public List<ContractTotlaVo> getPayList(List<String> ids) {

        return baseMapper.getPayList(ids);
    }

    @Override
    public BaseResponse selectConContract(ConContractRequest request) {
        IPage<ConContract>  page=new Page<>(request.getPage(),request.getSize());
        List<ConContract> cons=baseMapper.selectConContract(page, request);
        return BaseResponse.ok(cons,Long.valueOf(page.getTotal()).intValue());
    }
}
