package com.jpmsoft.custom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.custom.pojo.entity.SysTableColumn;

import java.util.List;


public interface ISysTableColumnService extends IService<SysTableColumn> {


    BaseResponse GetTableColumnListByTableCode(List<String> TableCode);

    List<SysTableColumn> getColumnListByTableCode(String tableCode);

    List<SysTableColumn> getColumnListByTableId(String tableId);
}
