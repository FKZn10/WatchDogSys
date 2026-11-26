package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.ExlTableColumnDto;
import com.jpmsoft.userProducer.pojo.entity.TableColumnInfo;
import com.jpmsoft.userProducer.pojo.vo.TabcolumsVo;

import java.util.List;

public interface ITableColumnManagementService extends IService<TableColumnInfo> {
    BaseResponse getTableColumnInfo(String tableName, String userid);

    BaseResponse importProperties(List<ExlTableColumnDto> request, String userid);
    List<TabcolumsVo>  getTableColumnList(String owner, String  tablecode);
}
