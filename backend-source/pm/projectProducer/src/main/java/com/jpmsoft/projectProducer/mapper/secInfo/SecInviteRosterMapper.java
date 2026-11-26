package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.SecInfo.SecInviteRosterVo;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecInviteRoster;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/23 10:31
 */
public interface SecInviteRosterMapper extends BaseMapper<SecInviteRoster> {

    List<SecInviteRosterVo> getSecInviteRosterBydIds(@Param("dIds") List<String> dIds);
}
