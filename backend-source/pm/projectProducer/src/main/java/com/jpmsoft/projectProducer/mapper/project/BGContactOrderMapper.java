package com.jpmsoft.projectProducer.mapper.project;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.model.request.project.ProBGRequest;
import com.jpmsoft.projectProducer.pojo.dto.project.ProBGDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.BGContactOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public interface BGContactOrderMapper extends BaseMapper<BGContactOrder> {
    BigDecimal getBGContactOrderMoney(@Param("conId") String conId);

    Page<ProBGDTO> BGContactOrderListByType(Page page, @Param("request") ProBGRequest request);

    /**
     * 联系事项被使用的比例之和
     * @param contactItemId
     * @return
     */
    BigDecimal sumOfRateByContactItemId(@Param("contactItemId") String contactItemId);
}
