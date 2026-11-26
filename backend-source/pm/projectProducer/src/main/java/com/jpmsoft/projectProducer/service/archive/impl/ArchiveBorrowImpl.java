package com.jpmsoft.projectProducer.service.archive.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.Archive.ArchiveBorrowRequest;
import com.jpmsoft.common.model.response.Archive.ArchiveBorrowResponse;
import com.jpmsoft.projectProducer.mapper.archive.ArchiveBorrowMapper;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveBorrow;
import com.jpmsoft.projectProducer.service.archive.IArchiveBorrowService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ArchiveBorrowImpl extends FileImpl<ArchiveBorrowMapper, ArchiveBorrow, ArchiveBorrowRequest> implements IArchiveBorrowService {


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateArchiveBorrow(ArchiveBorrowRequest request) {
        ArchiveBorrow entity = new ArchiveBorrow();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getAbId());
    }

    @Override
    public BaseResponse getArchiveBorrowList(ArchiveBorrowRequest request) {
        QueryMap<ArchiveBorrowRequest> params = new QueryMap<>(request);
        params.put("abCreateUname");
        List<ArchiveBorrowResponse> datas = baseMapper.getArchiveBorrowList(params.getQuery());
        Integer total = baseMapper.getArchiveBorrowListCount(params.getQuery());
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getArchiveBorrowById(String abId) {
        ArchiveBorrowResponse data = baseMapper.getArchiveBorrowById(abId);
        return BaseResponse.ok(data);
    }

    @Override
    public void delArchiveBorrowByApId(String apId) {
        List<String> ids = baseMapper.getArchiveBorrowIdByApId(apId);
        if (!CollectionUtils.isEmpty(ids))
            baseMapper.deleteBatchIds(ids);
    }
}
