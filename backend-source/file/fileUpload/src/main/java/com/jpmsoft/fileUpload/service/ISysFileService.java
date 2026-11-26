package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.fileUpload.pojo.dto.FormDataExpDTO;
import com.jpmsoft.fileUpload.pojo.entity.PreProject;
import com.jpmsoft.fileUpload.pojo.entity.SysFile;
import com.jpmsoft.fileUpload.pojo.entity.SysFileTemplate;
import com.jpmsoft.fileUpload.pojo.entity.SysTableColumn;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface ISysFileService extends IService<SysFile> {

    String executeSql(String sql);

    /**
     * 单词最多查询500条
     * 默认100条
     * 查询未导出积木报表归档的数据
     *
     * @param size
     * @return
     */
    List<SysFile> getUnJmArchive(int size);

    SysFile file(Map<String, String> data, SysFileTemplate temp, LogUserInfoVo user,String projectKey);

    void inertSql(List<String> sql);

    Map<String, String> selectByImportSQL(String sql, List<String> codes);

    List<Map> selectByTableColumn(List<SysTableColumn> columns, String tableCode, FormDataExpDTO req);

    /**
     * 查询唯一列
     *
     * @return
     */
    List<Map> selectOnlyColumn(List<SysTableColumn> columns, SysFileTemplate temp);

    void batchSaveFile(List<String> ids, List<String> tIds, String flowStatus, List<String> createUids, List<String> createUnames, List<Date> createTimes, List<String> pIds, String title, Object code, String status, List<String> relationIds, Object sectionId, String tCode, List<String> conIds);
}
