package com.jpmsoft.userProducer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.userProducer.pojo.entity.SysTPosition;
import com.jpmsoft.userProducer.pojo.vo.SysTPositionVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SysTPositionMapper extends BaseMapper<SysTPosition> {

    List<SysTPositionVo> GetT_PositionList(@Param("tdId") String tdId, @Param("tpName") String tpName,
                                           @Param("sortType") String sortType, @Param("sortFiled") String sortFiled, @Param("toId") String toId);

    /**
     * 查看同模板下是否存在编码相同的模板岗位
     *
     * @param tpCode
     * @param tpId
     * @param tdId
     * @return
     */
    Integer getPositionCountByTOrgan(@Param("tpCode") String tpCode, @Param("tpId") String tpId, @Param("tdId") String tdId);

}
