package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.ReplyInfoModel;
import com.jpmsoft.projectProducer.pojo.entity.other.ReplyInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ReplyInfoMapper extends BaseMapper<ReplyInfo> {

    List<ReplyInfoModel> getReplyInfoList(@Param("page") IPage<ReplyInfoModel> page,
                                          @Param("params") Map<String, Object> query);

    ReplyInfoModel getReplyInfoById(@Param("id")String id);
}
