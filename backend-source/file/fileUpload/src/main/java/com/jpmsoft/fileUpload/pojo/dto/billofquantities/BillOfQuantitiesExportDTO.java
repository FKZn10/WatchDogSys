package com.jpmsoft.fileUpload.pojo.dto.billofquantities;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class BillOfQuantitiesExportDTO {

    @ExcelProperty(index = 1 - 1, value = "清单类别", converter = ListTypeConverter.class)
    private String category;

    @ExcelProperty(index = 2 - 1, value = "序号")
    private String index;

    @ExcelProperty(index = 3 - 1, value = "项目编码")
    private String projectCode;

    @ExcelProperty(index = 4 - 1, value = "项目名称")
    private String projectName;

    @ExcelProperty(index = 5 - 1, value = "项目特征")
    private String projectFeature;

    @ExcelProperty(index = 6 - 1, value = "计量单位")
    private String measureUnit;

    @ExcelProperty(index = 7 - 1, value = "工程量")
    private String engineeringQuantity;

    @ExcelProperty(index = 8 - 1, value = "综合单价(元)")
    private String comprehensiveUnitPrice;

    @ExcelProperty(index = 9 - 1, value = "总价(元)")
    private String totalPrice;

    @ExcelProperty(index = 10 - 1, value = "措施费率(%)")
    private String measureFeeRate;

    @ExcelProperty(index = 11 - 1, value = "规费费率(%)")
    private String regulatoryFeeRate;

    @ExcelProperty(index = 12 - 1, value = "其他项目费率(%)")
    private String otherProjectFeeRate;

    @ExcelProperty(index = 13 - 1, value = "税金费率(%)")
    private String taxFeeRate;

    @ExcelProperty(index = 14 - 1, value = "计算公式")
    private String formula;

    @ExcelProperty(index = 15 - 1, value = "备注")
    private String remark;

//    @ExcelProperty(index = 16 - 1, value = "规费(元)")
//    private String regulatoryFee;
//
//    @ExcelProperty(index = 17 - 1, value = "税金(元)")
//    private String taxFee;

    public static class ListTypeConverter implements Converter<String> {

        public ListTypeConverter() {
        }

        @Override
        public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
            String v = "";
            if ("01".equals(value)) {
                v = "单项工程";
            } else if ("02".equals(value)) {
                v = "单位工程";
            } else if ("03".equals(value)) {
                v = "分部分项";
            } else if ("04".equals(value)) {
                v = "单位措施费";
            } else if ("05".equals(value)) {
                v = "其他项目";
            } else if ("06".equals(value)) {
                v = "分部分项清单";
            } else if ("07".equals(value)) {
                v = "其他";
            }
            return new WriteCellData<>(v);
        }
    }

}
