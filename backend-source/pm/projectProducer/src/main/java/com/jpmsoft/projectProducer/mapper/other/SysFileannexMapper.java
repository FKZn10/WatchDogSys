package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author zhangl
 * @since 2022-05-31
 */
public interface SysFileannexMapper extends BaseMapper<SysFileannex> {
    @Select("select * from sys_fileannex where file_relation_id = #{supportingDocument}")
    List<SysFileannex> selectByRelationId(String supportingDocument);
}
