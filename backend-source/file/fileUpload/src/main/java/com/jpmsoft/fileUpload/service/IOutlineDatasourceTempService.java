package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.entity.OutlineDatasourceTemp;

import java.util.List;

public interface IOutlineDatasourceTempService extends IService<OutlineDatasourceTemp> {
    void insertBatch(List<String> odIds, List<OutlineDatasourceTemp> tempList);
}
