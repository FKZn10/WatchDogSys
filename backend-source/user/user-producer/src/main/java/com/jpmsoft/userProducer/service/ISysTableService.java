package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.TableDto;
import com.jpmsoft.userProducer.pojo.dto.TableSqlDTO;
import com.jpmsoft.userProducer.pojo.entity.SysTable;

import java.util.List;
import java.util.Map;


public interface ISysTableService extends IService<SysTable> {

    BaseResponse GetTableList(TableDto request);

    BaseResponse CreateOrUpdateTable(TableDto requestt,String uId);

    BaseResponse DeleteTable(TableDto request,String uId);

    BaseResponse getSysTableinfo(String tableId);

    /**
     * 同步表结构到数据库
     *
     * @param id   tableId
     * @param drop 1：建表前先执行drop表操作
     * @return
     */
    BaseResponse syncTableAndColumn(String id, int drop);

    List<String> getTableIdByCode(String code);


    BaseResponse updateTableDataById(Map<String,Object> request,String uId);



    BaseResponse  deleteTableDataById(String tableId,String id,String uId);



    void createTableColumn(String tableDb, String tableCode, String columnCode, Integer columnLength, String columnType);
    void updateTableColumnName(String tableDb, String tableCode, String oldColumnName, String newColumnName);
    void updateTableColumnType(String tableDb, String tableCode, String columnCode, String columnType, Integer columnLength);

    /**
     * 获取表的SQL
     *
     * @param request
     * @return
     */
    BaseResponse getTableSql(TableSqlDTO request);
}
