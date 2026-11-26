package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.requestAndResponse.Contract.conConsortiumModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.contract.ConConsortiumDtlMapper;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConConsortiumDtl;
import com.jpmsoft.projectProducer.service.contract.IConConsortiumDtlService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ConConsortiumDtlImpl extends ServiceImpl<ConConsortiumDtlMapper, ConConsortiumDtl> implements IConConsortiumDtlService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createOrUpdateConConsortiumDtl(List<conConsortiumModel> data, String conId) {
        //获取当前合同是否已经存在
        QueryWrapper<ConConsortiumDtl> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Id", conId);
        List<ConConsortiumDtl> list = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(list)) {
            //没有直接新增
          baseMapper.deleteBatchIds(list.stream().map(n->n.getId()).collect(Collectors.toList()));
        }
        for (conConsortiumModel dat : data
        ) {
            ConConsortiumDtl pojo = new ConConsortiumDtl();
            BeanUtils.copyProperties(dat, pojo);
            if(ValidateUtil.isEmpty(pojo.getId())){
                pojo.setId(GenerateUUID.getUUID());
            }
            pojo.setConId(conId);
            baseMapper.insert(pojo);
        }
    }


    @Override
    public   List<conConsortiumModel>  slectConConsortiumDtlListByConId(String   conId){
        return   baseMapper.slectConConsortiumDtlListByConId(conId);
    }

}
