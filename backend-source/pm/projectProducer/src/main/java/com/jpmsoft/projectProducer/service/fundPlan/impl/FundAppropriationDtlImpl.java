package com.jpmsoft.projectProducer.service.fundPlan.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.fundPlan.FundAppropriationDtlMapper;
import com.jpmsoft.projectProducer.pojo.dto.fundPlan.FundAppropriationDtlDto;
import com.jpmsoft.projectProducer.pojo.entity.fundPlan.FundAppropriationDtl;
import com.jpmsoft.projectProducer.pojo.vo.fundPlan.FundAppropriationDtlVo;
import com.jpmsoft.projectProducer.service.fundPlan.IFundAppropriationDtlService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/16 17:31
 */
@Service
public class FundAppropriationDtlImpl extends ServiceImpl<FundAppropriationDtlMapper, FundAppropriationDtl> implements IFundAppropriationDtlService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    @Override
    public void createOrUpdateFundAppropriationDtl(List<FundAppropriationDtlDto> datas, String faId) {
        delFundAppropriationDtlByFaId(faId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<FundAppropriationDtl> entitys = datas.stream().map(data -> {
                FundAppropriationDtl entity = new FundAppropriationDtl();
                BeanUtils.copyProperties(data, entity);
                entity.setFaId(faId);
                entity.setFadId(ValidateUtil.isEmpty(data.getFadId()) ? GenerateUUID.getUUID() : data.getFadId());
                sysFileAnnexService.refreshRelationFile(data.getFadFile(), data.getFadFileIds(), data.getFadId());
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public void delFundAppropriationDtlByFaId(String faId) {
        QueryWrapper<FundAppropriationDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Fa_Id", faId);
        baseMapper.delete(wrapper);
    }

    @Override
    public BaseResponse getFundAppropriationDtlList(FundAppropriationDtlDto request) {
        if (ValidateUtil.isEmpty(request.getConId())) return BaseResponse.fail(ResMsg.notReqParam);
        QueryMap<FundAppropriationDtlDto> params = new QueryMap<>(request);
        params.put("conId").put("pId");
        IPage<FundAppropriationDtlVo> page = new Page<>(request.getPage(), request.getSize());
        List<FundAppropriationDtlVo> datas = baseMapper.getFundAppropriationDtlList(page, params.getQuery());
        return BaseResponse.ok(datas, Long.valueOf(page.getTotal()).intValue());
    }
}
