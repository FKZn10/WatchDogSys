package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.SysSplitTableDTO;
import com.jpmsoft.userProducer.pojo.entity.SysSplitTable;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/1/3 14:19
 */
public interface ISysSplitTableService extends IService<SysSplitTable> {


    BaseResponse createSysSplitTable(SysSplitTableDTO request);

    @Deprecated
    BaseResponse deleteSysSplitTableById(String id);

    BaseResponse getSysSplitTableList(SysSplitTableDTO request);

    BaseResponse getSysSplitTableById(String id);
}
