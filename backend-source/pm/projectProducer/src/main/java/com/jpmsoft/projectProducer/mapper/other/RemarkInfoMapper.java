package com.jpmsoft.projectProducer.mapper.other;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.RemarkInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author yb.Sun
 * @date 2024/10/25 09:16
 */
@Mapper
public interface RemarkInfoMapper extends BaseMapper<RemarkInfo> {

    void addBatch(@Param("list") List<RemarkInfo> remarkInfos);

    List<RemarkInfo> getRemarkInfoByGroupFundPlanSummaryId(@Param("primaryId")String primaryId);
}
