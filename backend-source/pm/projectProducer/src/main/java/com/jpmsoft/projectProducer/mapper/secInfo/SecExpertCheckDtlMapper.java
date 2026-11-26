package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecExpertCheckDtl;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecExpertCheckDtlVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/8/1 10:00
 */
public interface SecExpertCheckDtlMapper extends BaseMapper<SecExpertCheckDtl> {

    List<SecExpertCheckDtlVO> getExpertCheckDtlList(@Param("secId") String secId, @Param("uPlaintextNum") String uPlaintextNum
            , @Param("latestTime") String latestTime);


   Integer  updateResultByIds(@Param("ids") List<String> ids, @Param("res")  Integer result);
}
