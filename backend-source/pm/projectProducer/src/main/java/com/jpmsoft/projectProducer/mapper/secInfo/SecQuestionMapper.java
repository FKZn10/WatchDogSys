package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecQuestion;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecQuestionVO;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/6 10:47
 */
public interface SecQuestionMapper extends BaseMapper<SecQuestion> {

    SecQuestionVO getSecQuestionById(@Param("id") String id);
}
