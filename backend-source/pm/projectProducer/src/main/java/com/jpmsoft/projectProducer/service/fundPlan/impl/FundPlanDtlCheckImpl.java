package com.jpmsoft.projectProducer.service.fundPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.fundPlan.FundPlanDtlCheckMapper;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundPlanDtlCheckDto;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundPlanDtlCheck;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.FundPlanDtlCheckVo;
import com.jpmsoft.projectProducer.service.fundPlan.IFundPlanDtlCheckService;
import com.jpmsoft.projectProducer.service.fundPlan.IFundPlanDtlService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 12:06
 */
@Service
public class FundPlanDtlCheckImpl extends ServiceImpl<FundPlanDtlCheckMapper, FundPlanDtlCheck> implements IFundPlanDtlCheckService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private IFundPlanDtlService fundPlanDtlService;

    @Override
    public void delFundPlanDtlCheckByFpdIds(List<String> fpdIds) {
        QueryWrapper<FundPlanDtlCheck> wrapper = new QueryWrapper<>();
        wrapper.in("Fpd_Id", fpdIds);
        baseMapper.delete(wrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateFundPlanDtlCheck(FundPlanDtlCheckDto request) {
        FundPlanDtlCheck entity = new FundPlanDtlCheck();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getFpdcId())) {
            entity.setFpdcId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
            sysFileAnnexService.addFormRefreshRelationFile(entity.getFpdcFile(), entity.getFpdcId());
        } else {
            baseMapper.updateById(entity);
            sysFileAnnexService.refreshRelationFile(entity.getFpdcFile(), request.getFpdcFileIds(), entity.getFpdcId());
        }
        fundPlanDtlService.updateFinalInfo(entity);
        return BaseResponse.ok(entity.getFpdcId());
    }

    @Override
    public BaseResponse getFundPlanDtlCheckList(FundPlanDtlCheckDto request) {
        QueryMap<FundPlanDtlCheckDto> queryMap = new QueryMap<>(request);
        queryMap.put("fpdId").put("fpdcNodeName").put("fpdcUname");
        IPage<FundPlanDtlCheckVo> page = new Page<>(request.getPage(), request.getSize());
        List<FundPlanDtlCheckVo> datas = baseMapper.getFundPlanDtlCheckList(page, queryMap.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getFundPlanDtlCheckById(String id) {
        FundPlanDtlCheckVo data = baseMapper.getFundPlanDtlCheckById(id);
        return BaseResponse.ok(data);
    }
}
