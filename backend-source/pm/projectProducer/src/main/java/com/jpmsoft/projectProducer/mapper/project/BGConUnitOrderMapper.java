package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.project.ProBGRequest;
import com.jpmsoft.projectProducer.pojo.dto.project.ProBGDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.BGConUnitOrder;
import com.jpmsoft.projectProducer.pojo.entity.contract.BGDeclaration;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConBG;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface BGConUnitOrderMapper extends BaseMapper<BGConUnitOrder> {
    BigDecimal getBGConUnitOrderMoney(@Param("conId") String conId);

    List<ProBGDTO> BGConUnitOrderListByConId(Page page, @Param("request") ProBGRequest request);

    Page<ProBGDTO> BGConUnitOrderListByType(Page page, @Param("request") ProBGRequest request);
}
