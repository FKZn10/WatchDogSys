package com.jpmsoft.projectProducer.easyexcel.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;

import java.math.BigDecimal;

/**
 * 万元转元  金额*10000
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/2 15:06
 */
public class WanYuan2YuanConverter implements Converter<BigDecimal> {
    private static final BigDecimal wan = new BigDecimal(10000);

    @Override
    public BigDecimal convertToJavaData(ReadConverterContext<?> context) throws Exception {
        BigDecimal money = context.getReadCellData().getNumberValue();
        if (money == null || money.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        return money.multiply(wan);
    }
}
