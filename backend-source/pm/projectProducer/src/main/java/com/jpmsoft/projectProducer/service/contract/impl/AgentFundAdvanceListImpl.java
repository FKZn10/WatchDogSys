package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Contract.AgentFundAdvanceListModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.AgentFundAdvanceListMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.AgentFundAdvanceList;
import com.jpmsoft.projectProducer.service.contract.IAgentFundAdvanceListService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class AgentFundAdvanceListImpl extends ServiceImpl<AgentFundAdvanceListMapper, AgentFundAdvanceList> implements IAgentFundAdvanceListService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public void createOrUpdateAgentFundAdvanceList(List<AgentFundAdvanceListModel> datas, String afaId) {
        deleteByAfaId(afaId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<AgentFundAdvanceList> entitys = new ArrayList<>();
            AgentFundAdvanceList entity;
            for (AgentFundAdvanceListModel data : datas) {
                entity = new AgentFundAdvanceList();
                data.setAfalId(GenerateUUID.getUUID());
                data.setAfaId(afaId);
                BeanUtils.copyProperties(data, entity);
                entitys.add(entity);
                if (!ValidateUtil.isEmpty(data.getAfalFile()) && !ValidateUtil.isEmpty(data.getAfalFileIds())) {
                    sysFileAnnexService.refreshRelationFile(data.getAfalFile(), data.getAfalFileIds(), entity.getAfalId());
                }
            }
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public BaseResponse getAfalThisMoneyByAfalConId(String afalConId) {
        BigDecimal money = baseMapper.getAfalThisMoneyByAfalConId(afalConId);
        return BaseResponse.ok(money == null ? 0 : money);
    }

    private void deleteByAfaId(String afaId) {
        QueryWrapper<AgentFundAdvanceList> wrapper = new QueryWrapper<>();
        wrapper.eq("Afa_Id", afaId);
        baseMapper.delete(wrapper);
    }
}
