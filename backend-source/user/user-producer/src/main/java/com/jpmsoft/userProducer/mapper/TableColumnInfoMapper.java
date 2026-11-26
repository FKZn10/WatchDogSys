package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.TableColumnInfo;
import com.jpmsoft.userProducer.pojo.vo.TabcolumsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface TableColumnInfoMapper extends BaseMapper<TableColumnInfo> {
    List<TabcolumsVo> getTableColumn(@Param("tableName") String tableName);
}
