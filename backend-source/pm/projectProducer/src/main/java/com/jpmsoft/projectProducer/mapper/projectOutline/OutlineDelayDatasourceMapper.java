package com.jpmsoft.projectProducer.mapper.projectOutline;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineDatasource;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineDelayDatasource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Collection;
import java.util.List;

/**
 * 延迟数据源
 *
 * @author tianhelin
 * @date 2025/08/15
 */
@Mapper
public interface OutlineDelayDatasourceMapper extends BaseMapper<OutlineDelayDatasource> {
    /**
     * 根据odIds获取odId到alias的映射
     *
     * @param odIds
     * @return
     */
    List<OutlineDatasource> getOdIdToAliasByOdIds(@Param("odIds") Collection<String> odIds);

    /**
     * 根据opmIds获取需要删除的odId
     *
     * @param opmIds
     * @return
     */
    List<String> getRemoveOplIdsByOpmIds(@Param("opmIds") Collection<String> opmIds);
}
