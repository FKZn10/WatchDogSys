package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.project.ProProjectRegisterUnit;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目登记参建单位明细持久化接口。
 */
public interface ProProjectRegisterUnitMapper extends BaseMapper<ProProjectRegisterUnit> {

    /**
     * 按主表ID和显示顺序查询参建单位。
     *
     * @param registerId 登记ID
     * @return 参建单位列表
     */
    List<ProProjectRegisterUnit> getByRegisterId(@Param("registerId") String registerId);

    /**
     * 删除指定登记的所有参建单位。
     *
     * @param registerId 登记ID
     * @return 删除条数
     */
    int deleteByRegisterId(@Param("registerId") String registerId);
}
