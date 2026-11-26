package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.SecInfo.SecInviteDetailInquiryDocDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.SecInviteDetailInquiryDocMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInviteDetailInquiryDoc;
import com.jpmsoft.projectProducer.service.secInfo.ISecInviteDetailInquiryDocService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class SecInviteDetailInquiryDocImpl  extends ServiceImpl<SecInviteDetailInquiryDocMapper, SecInviteDetailInquiryDoc> implements ISecInviteDetailInquiryDocService {


    @Override
    public void createOrUpdateSecInviteDetailInquiryDoc(List<SecInviteDetailInquiryDocDTO> datas, String wsidId) {
        delSecInviteDetailInquiryDocByWsidId(wsidId);
        if (!CollectionUtils.isEmpty(datas)) {
            List<SecInviteDetailInquiryDoc> entitys = datas.stream().map(data -> {
                SecInviteDetailInquiryDoc entity = new SecInviteDetailInquiryDoc();
                BeanUtils.copyProperties(data, entity);
                entity.setWsidId(wsidId);
                entity.setId(GenerateUUID.getUUID(data.getId()));
                return entity;
            }).collect(Collectors.toList());
            saveBatch(entitys);
        }
    }

    @Override
    public List<SecInviteDetailInquiryDocDTO> getSecInviteDetailInquiryDocByWsidIds(List<String> wsidIdList) {
        if (CollectionUtils.isEmpty(wsidIdList)) return null;
        return baseMapper.getSecInviteDetailInquiryDocByWsidIds(wsidIdList);
    }

    @Override
    public void delSecInviteDetailInquiryDocByWsidId(String wsidId) {
        QueryWrapper<SecInviteDetailInquiryDoc> wrapper = new QueryWrapper<>();
        wrapper.eq("wsid_id", wsidId);
        baseMapper.delete(wrapper);
    }

    @Override
    public void delBathByWsidIds(List<String> wsidIds) {
        QueryWrapper<SecInviteDetailInquiryDoc> wrapper = new QueryWrapper<>();
        wrapper.in("wsid_id", wsidIds);
        baseMapper.delete(wrapper);
    }

}
