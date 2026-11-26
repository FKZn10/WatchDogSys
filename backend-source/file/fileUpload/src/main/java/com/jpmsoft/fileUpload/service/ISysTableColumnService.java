package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.dto.jaxbxml.SysTableColumnXmlDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysTableColumn;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/9 8:36
 */
public interface ISysTableColumnService extends IService<SysTableColumn> {


    List<SysTableColumn> getColumnListByTableCode(String tableCode);

    /**
     *
     * @param tableCode
     * @param action exp 导出 imp导入
     * @return
     */
    List<SysTableColumn> getColumnListByTableCode(String tableCode,String action);

    /**
     * 返回列code集合 默认全大写
     *
     * @param tableCode
     * @return
     */
    List<String> getColumnCodeByTableCode(String tableCode);


    List<SysTableColumnXmlDTO> getColumns(List<String> tableIds);


    void deleteByTbCodes(List<String> tbCodes);


    void insertBatchSomeColumn(List<SysTableColumn> columns);

    String selectPk(String tableCode);
}
