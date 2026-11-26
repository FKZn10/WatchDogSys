package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.entity.SysTableColumn;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/9 8:36
 */
public interface SysTableColumnMapper extends BaseMapper<SysTableColumn> {

    @Select("select mn.* from  Sys_TableColumn mn left join  Sys_Table tb on mn.Table_ID=tb.Table_ID where upper(tb.Table_Code)=#{tableCode} order by mn.Column_Number ASC")
    List<SysTableColumn> getColumnListByTableCode(@Param("tableCode") String tableCode);

    void insertBatchSomeColumn(@Param("list") List<SysTableColumn> userStudyList);
}
