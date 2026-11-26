package com.jpmsoft.projectProducer.service.investment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.InvestmentControl.FundSchemeApplyDtlModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.investment.FundSchemeApplyDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.investment.FundSchemeApplyDtl;
import com.jpmsoft.projectProducer.service.investment.IFundSchemeApplyDtlService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class FundSchemeApplyDtlImpl extends ServiceImpl<FundSchemeApplyDtlMapper, FundSchemeApplyDtl> implements IFundSchemeApplyDtlService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    @Override
    public void createOrUpdateFundSchemeApplyDtl(List<FundSchemeApplyDtlModel> datas, String fsaId) {
        deleteByFsaId(fsaId);
        if (datas != null && datas.size() > 0) {
            List<FundSchemeApplyDtl> entitys = new ArrayList<>();
            FundSchemeApplyDtl entity;
            for (FundSchemeApplyDtlModel data : datas) {
                entity = new FundSchemeApplyDtl();
                data.setFsadId(GenerateUUID.getUUID());
                data.setFsaId(fsaId);
                BeanUtils.copyProperties(data, entity);
                entitys.add(entity);
                if (!ValidateUtil.isEmpty(data.getFsadFile()) && !ValidateUtil.isEmpty(data.getFsadFileIds())) {
                    sysFileAnnexService.refreshRelationFile(data.getFsadFile(), data.getFsadFileIds(), entity.getFsadId());
                }
            }
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    private void deleteByFsaId(String fsaId) {
        QueryWrapper<FundSchemeApplyDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Fsa_Id", fsaId);
        baseMapper.delete(wrapper);
    }
}
