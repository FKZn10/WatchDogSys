package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.request.SecInfo.SecInviteDetailDTO;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInviteDetail;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/23 9:32
 */
public interface SecInviteDetailMapper extends BaseMapper<SecInviteDetail> {

    List<SecInviteDetailDTO> getSecInviteDetailByWsIds(@Param("wsIds") List<String> wsIds);

    List<SecInviteDetailDTO> getSecInviteDetailBySirIds(@Param("sirIds") List<String> sirIds);
}
