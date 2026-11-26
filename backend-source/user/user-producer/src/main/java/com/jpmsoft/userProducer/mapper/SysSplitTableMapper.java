package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysSplitTable;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/1/3 14:18
 */
public interface SysSplitTableMapper extends BaseMapper<SysSplitTable> {

    void copyTable(@Param("tableCode") String tableCode,@Param("stCode") String stCode);
}
