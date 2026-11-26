package com.jpmsoft.projectProducer.mapper.xz;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.common.model.response.xz.FundApplicationVO;
import com.jpmsoft.projectProducer.pojo.entity.xz.FundApplication;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 15:50
 * @Description:
 * @Version: 1.0
 */
public interface FundApplicationMapper extends BaseMapper<FundApplication> {

    List<FundApplicationVO> queryPage(IPage<FundApplicationVO> page,
                                      @Param("fileName") String fileName,
                                      @Param("planYear") String planYear,
                                      @Param("approveStatus") String approveStatus,
                                      @Param("batchNum") String batchNum,
                                      @Param("createId") String createId);

    FundApplicationVO queryDetailById(@Param("id") String id);
}
