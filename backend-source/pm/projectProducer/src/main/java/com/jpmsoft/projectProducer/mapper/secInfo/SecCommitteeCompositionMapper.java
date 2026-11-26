package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecCommitteeComposition;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecCommitteeCompositionVO;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:44
 */
public interface SecCommitteeCompositionMapper extends BaseMapper<SecCommitteeComposition> {

    SecCommitteeCompositionVO getSecCommitteeCompositionById(@Param("id") String id);
}
