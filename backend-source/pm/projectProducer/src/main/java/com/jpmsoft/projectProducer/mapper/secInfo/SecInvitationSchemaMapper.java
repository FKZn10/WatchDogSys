package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInvitationSchemaDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInvitationSchema;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 邀标工作方案
 */
@Mapper
public interface SecInvitationSchemaMapper extends BaseMapper<SecInvitationSchema> {
    /**
     * 获取邀请招标列表
     *
     * @param page
     * @param query
     * @return
     */
    List<SecInvitationSchemaDTO> getSecInvitationSchemaList(@Param("page") Page<SecInvitationSchemaDTO> page, @Param("params") Map<String, Object> query);

    /**
     * 多条件筛选
     *
     * @param selectClause
     * @param query
     * @return
     */
    List<Map<String, Object>> getAllValues(@Param("selectClause") String selectClause, @Param("params") Map<String, Object> query);
}
