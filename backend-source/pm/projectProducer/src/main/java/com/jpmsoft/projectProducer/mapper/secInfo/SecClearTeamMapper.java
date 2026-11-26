package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecClearTeam;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecClearTeamVO;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:43
 */
public interface SecClearTeamMapper extends BaseMapper<SecClearTeam> {

    SecClearTeamVO getSecClearTeamById(@Param("id") String id);
}
