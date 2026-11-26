package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.DeleteLogDto;
import com.jpmsoft.common.model.requestAndResponse.NoticeInfoModel;
import com.jpmsoft.userProducer.pojo.entity.DeleteLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yb.Sun
 * @date 2024/10/23 16:21
 */
@Mapper
public interface DeleteLogMapper extends BaseMapper<DeleteLog> {

    List<DeleteLogDto> getDeleteLogList(@Param("params")Map<String, Object> query, @Param("page") IPage<DeleteLogDto> page);

    DeleteLogDto getDeleteLogById(@Param("id")String id);
}
