package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.SecInfo.SecInviteDetailInquiryDocDTO;
import com.jpmsoft.common.model.request.SecInfo.SecWorkSchemaInquiryDocDTO;
import com.jpmsoft.projectProducer.mapper.secInfo.SecWorkSchemaInquiryDocMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecWorkSchemaInquiryDoc;
import com.jpmsoft.projectProducer.service.secInfo.ISecInviteDetailInquiryDocService;
import com.jpmsoft.projectProducer.service.secInfo.ISecWorkSchemaInquiryDocService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

@Service
public class SecWorkSchemaInquiryDocImpl extends FileImpl<SecWorkSchemaInquiryDocMapper, SecWorkSchemaInquiryDoc, SecWorkSchemaInquiryDocDTO> implements ISecWorkSchemaInquiryDocService {

    @Resource
    private ISecInviteDetailInquiryDocService secInviteDetailInquiryDocService;

    @Override
    public BaseResponse createOrUpdateSecWorkSchemaInquiryDoc(SecWorkSchemaInquiryDocDTO request) {
        SecWorkSchemaInquiryDoc entity = new SecWorkSchemaInquiryDoc();
        createOrUpdateFileEntity(entity, request);
        secInviteDetailInquiryDocService.createOrUpdateSecInviteDetailInquiryDoc(request.getSecInviteDetailInquiryDocs(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecWorkSchemaInquiryDocList(SecWorkSchemaInquiryDocDTO request) {
        QueryMap<SecWorkSchemaInquiryDocDTO> queryMap = new QueryMap<>(request);
        queryMap.put("pName").put("secName");
        IPage<SecWorkSchemaInquiryDocDTO> page = new Page<>(request.getPage(), request.getSize());
        List<SecWorkSchemaInquiryDocDTO> data = baseMapper.getSecWorkSchemaInquiryDocList(page, queryMap.getQuery());
        return BaseResponse.ok(data, Integer.parseInt(Long.valueOf(page.getTotal()).toString()));
    }

    @Override
    public BaseResponse getSecWorkSchemaInquiryDocByWsidId(String wsidId) {
        SecWorkSchemaInquiryDocDTO data = baseMapper.getSecWorkSchemaInquiryDocByWsidId(wsidId);
        List<SecInviteDetailInquiryDocDTO> details = secInviteDetailInquiryDocService.getSecInviteDetailInquiryDocByWsidIds(Arrays.asList(wsidId));
        data.setSecInviteDetailInquiryDocs(details);
        return BaseResponse.ok(data, 0);
    }

    @Override
    public BaseResponse getSecWorkSchemaInquiryDocBySecId(String secId) {
        List<SecWorkSchemaInquiryDocDTO> datas = baseMapper.getSecWorkSchemaInquiryDocBySecId(secId);
        return BaseResponse.ok(datas);
    }

}
