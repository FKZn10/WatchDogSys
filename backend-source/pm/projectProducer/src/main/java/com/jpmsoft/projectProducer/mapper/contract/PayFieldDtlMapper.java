package com.jpmsoft.projectProducer.mapper.contract;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.common.model.response.pay.PayFieldDtlVo;
import com.jpmsoft.projectProducer.pojo.entity.contract.PayFieldDtl;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/7/5 14:53
 */

@Deprecated
public interface PayFieldDtlMapper extends BaseMapper<PayFieldDtl> {


    List<PayFieldDtlVo> getPayFieldDtlByPayId(@Param("payId") String payId);
}
