package com.jpmsoft.projectProducer.mapper.purchase;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jpmsoft.projectProducer.pojo.dto.purchase.ProPurchaseDto;
import com.jpmsoft.projectProducer.pojo.entity.purchase.ProPurchase;
import com.jpmsoft.projectProducer.pojo.vo.purchase.ProPurchaseVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

public interface ProPurchaseMapper extends BaseMapper<ProPurchase> {

    List<ProPurchaseVo> getPropurchaseList(@Param("page") IPage<ProPurchaseVo> page, @Param("params") Map<String, Object> params);


    ProPurchaseVo getPropurchaseById(@Param("id") String id);

}
