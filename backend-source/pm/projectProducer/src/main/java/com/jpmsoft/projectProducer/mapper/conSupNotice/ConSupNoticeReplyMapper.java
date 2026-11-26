package com.jpmsoft.projectProducer.mapper.conSupNotice;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jpmsoft.common.model.request.ConBop.ConSupNoticeRequest;
import com.jpmsoft.projectProducer.pojo.entity.conSupNotice.ConSupNoticeReply;
import org.apache.ibatis.annotations.Param;


public interface ConSupNoticeReplyMapper extends BaseMapper<ConSupNoticeReply> {


    IPage<ConSupNoticeRequest> getConSupNoticeReplyList(IPage<ConSupNoticeRequest> page, @Param(Constants.WRAPPER) Wrapper<ConSupNoticeRequest> queryWrapper,@Param("authSql")String authSql);
}
