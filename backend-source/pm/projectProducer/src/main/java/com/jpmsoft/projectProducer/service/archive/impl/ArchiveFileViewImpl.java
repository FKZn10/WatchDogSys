package com.jpmsoft.projectProducer.service.archive.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.Archive.ArchiveFileViewModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.archive.ArchiveFileViewMapper;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveFileView;
import com.jpmsoft.projectProducer.service.archive.IArchiveFileViewService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class ArchiveFileViewImpl extends ServiceImpl<ArchiveFileViewMapper, ArchiveFileView> implements IArchiveFileViewService {


    @Override
    public BaseResponse createOrUpdateArchiveFileView(ArchiveFileViewModel request) {
        ArchiveFileView entity = new ArchiveFileView();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(entity.getAfvId())) {
            entity.setAfvId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        } else
            baseMapper.updateById(entity);
        return BaseResponse.ok(entity.getAfvId());
    }

    @Override
    public BaseResponse getArchiveFileViewList(ArchiveFileViewModel request) {
        QueryMap<ArchiveFileViewModel> params = new QueryMap<>(request);
        params.put("afvUname");
        IPage<ArchiveFileViewModel> page = new Page<>(request.getPage(), request.getSize());
        List<ArchiveFileViewModel> datas = baseMapper.getArchiveFileViewList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public void delArchiveFileViewByApId(String apId) {
        List<String> ids = baseMapper.getArchiveFileViewIdByApId(apId);
        if (!CollectionUtils.isEmpty(ids))
            baseMapper.deleteBatchIds(ids);
    }
}
