package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.NoticeInfoCloseModel;
import com.jpmsoft.projectProducer.pojo.entity.other.NoticeInfoClose;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface NoticeInfoCloseMapper extends BaseMapper<NoticeInfoClose> {
    List<NoticeInfoCloseModel> getNoticeInfoCloseList(@Param("params")Map<String, Object> query,@Param("page") IPage<NoticeInfoCloseModel> page);

    NoticeInfoCloseModel getNoticeInfoCloseById(@Param("id")String id);
}