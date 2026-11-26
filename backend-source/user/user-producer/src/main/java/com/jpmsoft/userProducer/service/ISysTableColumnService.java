package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.ColumnSqlDTO;
import com.jpmsoft.userProducer.pojo.dto.ExlTableColumnDto;
import com.jpmsoft.userProducer.pojo.dto.TableColumnDto;
import com.jpmsoft.userProducer.pojo.entity.SysTableColumn;

import java.util.List;


public interface ISysTableColumnService extends IService<SysTableColumn> {

    BaseResponse GetTableColumnList(TableColumnDto request);

    BaseResponse CreateOrUpdateTableColumn(TableColumnDto requestt, String uId);

    BaseResponse DeleteTableColumn(TableColumnDto request, String uId);

    BaseResponse importProperties(List<ExlTableColumnDto> request);

    BaseResponse GetTableColumnListByTableCode(List<String> TableCode);

    List<SysTableColumn> getColumnListByTableCode(String tableCode);

    /**
     * 设置全部列导入导出
     *
     * @param tableCode
     * @param action    exp导出 imp导入
     * @return
     */
    BaseResponse setColumnExpOrImp(String tableCode, String action, Boolean enable);


    /**
     * 根据字段编码格式化驼峰
     *
     * @return
     */
    BaseResponse formatHump();

    /**
     * 生成字段的sql
     *
     * @param request
     * @return
     */
    BaseResponse getColumnSql(ColumnSqlDTO request);
}
