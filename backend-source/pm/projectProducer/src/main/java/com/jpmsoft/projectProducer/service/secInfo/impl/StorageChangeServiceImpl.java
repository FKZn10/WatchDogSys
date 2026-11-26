package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.mapper.secInfo.StorageChangeMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.StorageChangeRequest;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.StorageChange;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.StorageChangeXctVO;
import com.jpmsoft.projectProducer.service.secInfo.StorageChangeDetailService;
import com.jpmsoft.projectProducer.service.secInfo.StorageChangeService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【storage_change(入库变更表)】的数据库操作Service实现
 * @createDate 2025-04-24 18:08:54
 */
@Service
public class StorageChangeServiceImpl extends FileImpl<StorageChangeMapper, StorageChange, StorageChangeRequest>
        implements StorageChangeService {

    @Resource
    private StorageChangeDetailService storageChangeDetailService;

    @Override
    @CodeGeneration(key = "id", codeParam = "scCode", method = "setScCode", codeRule = "StorageChangeXct", beanClass = StorageChangeRequest.class)
    public BaseResponse createOrUpdateStorageChange(StorageChangeRequest request) {
        StorageChange entity = new StorageChange();
        createOrUpdateFileEntity(entity, request);
        storageChangeDetailService.createOrUpdare(request.getStorageChangeDetailList(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getStorageChangeList(StorageChangeRequest request) {
        IPage<StorageChangeXctVO> page = new Page<>(request.getPage(), request.getSize());
        List<StorageChangeXctVO> data = baseMapper.getStorageChangeList(page, request);
        return BaseResponse.ok(data, (int) page.getTotal());
    }


}




