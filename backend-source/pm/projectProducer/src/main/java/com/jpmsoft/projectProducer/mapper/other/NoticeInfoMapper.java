package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.NoticeInfoModel;
import com.jpmsoft.projectProducer.pojo.entity.other.NoticeInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeInfoMapper extends BaseMapper<NoticeInfo> {
    List<NoticeInfoModel> getNoticeInfoList(@Param("params")Map<String, Object> query,@Param("page") IPage<NoticeInfoModel> page);

    NoticeInfoModel getNoticeInfoById(@Param("id")String id);

    NoticeInfo selectBySourceId(@Param("sourceId")String sourceId);

    List<NoticeInfo> selectList2();
}