package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.requestAndResponse.RemarkInfoDto;
import com.jpmsoft.userProducer.pojo.entity.RemarkInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author yb.Sun
 * @date 2024/10/25 09:16
 */
@Mapper
public interface RemarkInfoMapper extends BaseMapper<RemarkInfo> {
    List<RemarkInfoDto> getRemarkInfoList(@Param("params")Map<String, Object> query);

}
