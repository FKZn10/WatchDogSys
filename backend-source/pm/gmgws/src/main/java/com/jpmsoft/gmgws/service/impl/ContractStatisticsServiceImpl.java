package com.jpmsoft.gmgws.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.Contract.ConContractRequest;
import com.jpmsoft.gmgws.mapper.ContractStatisticsMapper;
import com.jpmsoft.gmgws.pojo.entity.ContractStatistics;
import com.jpmsoft.gmgws.pojo.vo.ContractStatisticsVo;
import com.jpmsoft.gmgws.service.ContractStatisticsService;
import org.springframework.stereotype.Service;

@Service
public class ContractStatisticsServiceImpl extends ServiceImpl<ContractStatisticsMapper, ContractStatistics> implements ContractStatisticsService  {

    @Override
    public BaseResponse getContractStatisticsList(ConContractRequest request) {
        QueryMap<ConContractRequest> params = assembleScreen(request);
        IPage<ContractStatisticsVo> page = new Page<>(request.getPage(), request.getSize());
        IPage<ContractStatisticsVo> list = baseMapper.getContractStatisticsList(page, params.getQuery());
        return BaseResponse.ok(list.getRecords(), Long.valueOf(list.getTotal()).intValue());
    }

    private QueryMap<ConContractRequest> assembleScreen(ConContractRequest request) {
        QueryMap<ConContractRequest> query = new QueryMap<>(request);
        query.put("conId").put("pId").put("conProjectId").put("conName").put("conCode")
                .put("conPartyBId").put("conPartyBName").put("conProjectName");
        return query;
    }
}
