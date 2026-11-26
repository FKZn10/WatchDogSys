package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.mapper.OutlineDatasourceTempMapper;
import com.jpmsoft.fileUpload.pojo.entity.OutlineDatasourceTemp;
import com.jpmsoft.fileUpload.service.IOutlineDatasourceTempService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OutlineDatasourceTempServiceImpl extends ServiceImpl<OutlineDatasourceTempMapper, OutlineDatasourceTemp> implements IOutlineDatasourceTempService {
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertBatch(List<String> odIds, List<OutlineDatasourceTemp> tempList) {
        if (ValidateUtil.isEmpty(odIds) || ValidateUtil.isEmpty(tempList)) {
            return;
        }
        // 先删除
        lambdaUpdate().in(OutlineDatasourceTemp::getOdId, odIds).remove();
        // 再批量插入
        this.saveBatch(tempList);
    }
}
