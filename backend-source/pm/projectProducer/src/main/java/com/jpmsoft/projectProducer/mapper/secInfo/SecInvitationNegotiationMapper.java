package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.requestAndResponse.SecInfo.SecInvitationNegotiationDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInvitationNegotiation;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 邀标控制价
 */
@Mapper
public interface SecInvitationNegotiationMapper extends BaseMapper<SecInvitationNegotiation> {
    /**
     * 分页查询邀标控制价
     *
     * @param page
     * @param query
     * @return
     */
    List<SecInvitationNegotiationDTO> getSecInvitationNegotiationList(@Param("page") Page<SecInvitationNegotiationDTO> page, @Param("params") Map<String, Object> query);

    /**
     * 获取筛选信息
     *
     * @param selectClause
     * @param query
     * @return
     */
    List<Map<String, Object>> getAllValues(@Param("selectClause") String selectClause, @Param("params") Map<String, Object> query);
}
