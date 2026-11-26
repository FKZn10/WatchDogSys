package com.jpmsoft.projectProducer.mapper.conBoq;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.requestAndResponse.ConBoq.ConBoqChangeDto;
import com.jpmsoft.projectProducer.pojo.entity.conBoq.ConBoqChange;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 合同工程量清单变更主表 Mapper
 */
public interface ConBoqChangeMapper extends BaseMapper<ConBoqChange> {
    /**
     * 分页查询变更清单并关联项目、合同名称。
     *
     * @param page  分页信息
     * @param query 查询条件
     * @return 变更清单集合
     */
    List<ConBoqChangeDto> getConBoqChangeList(@Param("page") Page<ConBoqChangeDto> page, @Param("params") Map<String, Object> query);

    /**
     * 查询变更主表及项目、合同名称。
     *
     * @param id 变更主表ID
     * @return 变更主表，不存在时返回null
     */
    ConBoqChangeDto getConBoqChangeById(@Param("id") String id);
}

