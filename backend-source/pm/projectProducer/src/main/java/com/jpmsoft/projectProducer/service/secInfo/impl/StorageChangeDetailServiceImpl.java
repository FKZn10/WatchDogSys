package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.projectProducer.mapper.secInfo.StorageChangeDetailMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.StorageChangeDetail;
import com.jpmsoft.projectProducer.service.secInfo.StorageChangeDetailService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

/**
 * @author Administrator
 * @description 针对表【storage_change_detail(入库变更详情表)】的数据库操作Service实现
 * @createDate 2025-04-24 18:08:54
 */
@Service
public class StorageChangeDetailServiceImpl extends ServiceImpl<StorageChangeDetailMapper, StorageChangeDetail>
        implements StorageChangeDetailService {

    @Override
    public void createOrUpdare(List<StorageChangeDetail> data, String id) {
        deleteByScId(id);
        if (!CollectionUtils.isEmpty(data)) {
            data.forEach(d -> {
                d.setId(GenerateUUID.getUUID(d.getId()));
                d.setScId(id);
            });
            saveOrUpdateBatch(data);
        }
    }

    @Override
    public List<StorageChangeDetail> selectByScId(String scId) {
        return  baseMapper.selectByScId(scId);
    }

    public void deleteByScId(String id) {
        LambdaQueryWrapper<StorageChangeDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(StorageChangeDetail::getScId, id);
        baseMapper.delete(wrapper);
    }
}




