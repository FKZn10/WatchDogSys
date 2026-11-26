package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecBiddingSchemeUnitDto;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecBiddingSchemeUnitMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecBiddingSchemeUnit;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.secInfo.ISecBiddingSchemeUnitSerivice;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/25 15:11
 */
@Service
public class SecBiddingSchemeUnitImpl extends ServiceImpl<SecBiddingSchemeUnitMapper, SecBiddingSchemeUnit> implements ISecBiddingSchemeUnitSerivice {
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public void createOrUpdateSecBiddingSchemeUnit(List<SecBiddingSchemeUnitDto> datas, String bsId) {
        delSecBiddingSchemeUnitByBsId(bsId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<SecBiddingSchemeUnit> entitys = new ArrayList<>();
            for (SecBiddingSchemeUnitDto data : datas) {
                SecBiddingSchemeUnit entity = new SecBiddingSchemeUnit();
                BeanUtils.copyProperties(data, entity);
                entity.setId(GenerateUUID.getUUID(data.getId()));
                entity.setBsId(bsId);
                entitys.add(entity);
                sysFileAnnexService.refreshRelationFile(data.getBsuFile(), data.getBsuFileIds(), entity.getId());
            }
            if (!CollectionUtils.isEmpty(entitys)) saveBatch(entitys);
        }
    }

    @Override
    public void delSecBiddingSchemeUnitByBsId(String bsId) {
        QueryWrapper<SecBiddingSchemeUnit> wrapper = new QueryWrapper<>();
        wrapper.eq("Bs_Id", bsId);
        baseMapper.delete(wrapper);
    }
}
