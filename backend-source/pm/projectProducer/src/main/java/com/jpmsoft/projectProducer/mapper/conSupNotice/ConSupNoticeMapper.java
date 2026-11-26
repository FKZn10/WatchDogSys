package com.jpmsoft.projectProducer.mapper.conSupNotice;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.jpmsoft.common.model.request.ConBop.ConSupNoticeRequest;
import com.jpmsoft.projectProducer.pojo.entity.conSupNotice.ConSupNotice;
import org.apache.ibatis.annotations.Param;


public interface ConSupNoticeMapper extends BaseMapper<ConSupNotice> {

    IPage<ConSupNoticeRequest> getConSupNoticeList(IPage<ConSupNoticeRequest> page, @Param(Constants.WRAPPER) Wrapper<ConSupNoticeRequest> queryWrapper,@Param("authSql")String authSql);

    /**
     * 汇总监理报表子表的问题数量
     *
     * @param wrId 监理报表主键
     * @return 子表 wrd_number 合计
     */
    Integer getQuestionNumByWrId(@Param("wrId") String wrId);
}
