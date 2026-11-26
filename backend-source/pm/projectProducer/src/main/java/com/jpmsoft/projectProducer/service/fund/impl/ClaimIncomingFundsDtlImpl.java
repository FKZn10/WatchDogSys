package com.jpmsoft.projectProducer.service.fund.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.fund.ClaimIncomingFundsDtlDTO;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.fund.ClaimIncomingFundsDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.fund.ClaimIncomingFundsDtl;
import com.jpmsoft.projectProducer.service.fund.IClaimIncomingFundsDtlService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 资金管理-来款认领子表 Service实现类
 *
 * @author tianhelin
 * @date 2025-11-24
 */
@Service
public class ClaimIncomingFundsDtlImpl extends ServiceImpl<ClaimIncomingFundsDtlMapper, ClaimIncomingFundsDtl> implements IClaimIncomingFundsDtlService {
    /**
     * 根据来款认领主表ID批量保存或更新来款认领子表数据
     *
     * @param id
     * @param dtlList
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse saveOrUpdateBatchByCifId(String id, List<ClaimIncomingFundsDtlDTO> dtlList) {
        if (ValidateUtil.isEmpty(dtlList)) {
            return BaseResponse.ok();
        }
        // 先删除再新增
        this.remove(Wrappers.<ClaimIncomingFundsDtl>lambdaQuery().eq(ClaimIncomingFundsDtl::getCifId, id));
        List<ClaimIncomingFundsDtl> entityList = dtlList.stream().map(dto -> {
            ClaimIncomingFundsDtl entity = new ClaimIncomingFundsDtl();
            BeanUtils.copyProperties(dto, entity);
            entity.setId(GenerateUUID.getUUID());
            entity.setCifId(id);
            entity.setCreateTime(new Date());
            return entity;
        }).collect(Collectors.toList());
        this.saveBatch(entityList);
        return BaseResponse.ok();
    }

    /**
     * 获取集团已到位资金累计金额
     *
     * @param pId
     * @return
     */
    @Override
    public BigDecimal getGroupFundsCumulative(String pId) {
        if (ValidateUtil.isEmpty(pId)) {
            return BigDecimal.ZERO;
        }
        BigDecimal total = baseMapper.getGroupFundsCumulative(pId);
        return total != null ? total : BigDecimal.ZERO;
    }
}
