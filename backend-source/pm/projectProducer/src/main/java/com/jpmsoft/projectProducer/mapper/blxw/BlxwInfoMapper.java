package com.jpmsoft.projectProducer.mapper.blxw;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.entity.blxw.BlxwInfo;
import com.jpmsoft.projectProducer.pojo.vo.blxw.BlxwInfoNoticeCloseVo;
import com.jpmsoft.projectProducer.pojo.vo.blxw.BlxwInfoPrintVo;
import com.jpmsoft.projectProducer.pojo.vo.blxw.BlxwInfoNoticeVo;
import com.jpmsoft.projectProducer.pojo.vo.blxw.BlxwInfoVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface BlxwInfoMapper extends BaseMapper<BlxwInfo> {
    List<BlxwInfoVo> getBlxwInfoList(@Param("params") Map<String, Object> query,@Param("page") IPage<BlxwInfoVo> page);

    BlxwInfoVo getBlxwInfoById(@Param("id")String id);

    List<BlxwInfo> selectListByTypeId(@Param("id")String id);

    String getBlxwTypeNameByTypeId(@Param("type")String type);

    BlxwInfoNoticeVo getBlxwInfoNoticeById(@Param("id")String id);

    List<BlxwInfoNoticeVo> getBlxwInfoNoticeList(@Param("params")Map<String, Object> query,@Param("page") IPage<BlxwInfoNoticeVo> page);

    BlxwInfoPrintVo getBlxwInfoNoticePrintById(@Param("id")String id);

    List<BlxwInfoNoticeCloseVo> getBlxwInfoNoticeCloseList(@Param("params")Map<String, Object> query,@Param("page") IPage<BlxwInfoNoticeCloseVo> page);

    BlxwInfoNoticeCloseVo getBlxwInfoNoticeCloseDetail(@Param("id")String id);

    List<BlxwInfoVo> getBlxwInfoListQX();

    List<BlxwInfoVo> getBlxwInfoListWithoutNotice();

    List<BlxwInfo> getBlxwInfoBySourceIds(@Param("list")List<String> sourceIds);
}