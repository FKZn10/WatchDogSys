package com.jpmsoft.projectProducer.mapper.secInfo;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSolicitationNotice;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecSolicitationNoticeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/25 15:28
 */
public interface SecSolicitationNoticeMapper extends BaseMapper<SecSolicitationNotice> {

    List<SecSolicitationNoticeVo> getSecSolicitationNoticeList(@Param("page") IPage<SecSolicitationNoticeVo> page
            , @Param("params") Map<String, Object> params);

    SecSolicitationNoticeVo getSecSolicitationNoticeById(@Param("id") String id);

    SecSolicitationNoticeVo getSecSolicitationNoticeLastApprovedBySecId(@Param("secId") String secId);

}
