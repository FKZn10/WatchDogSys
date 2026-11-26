package com.jpmsoft.projectProducer.easyexcel;

import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.*;

/**
 * Code
 * com.jpmsoft.jpintelligentevalue.easyexcel
 * Description:
 *
 * @author: Dell-Daizhiyang
 * @date: 2024/1/25 19:50
 */
public class CommonCellStyleStrategy {
	public static HorizontalCellStyleStrategy getHorizontalCellStyleStrategy(Short fontHeightInPoints) {
		// 内容的策略
		WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
		// 设置边框
		contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
		contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
		contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
		contentWriteCellStyle.setBorderTop(BorderStyle.NONE);

		// 配置字体
		WriteFont contentWriteFont = new WriteFont();
		// 字体
		contentWriteFont.setFontName("宋体");
		// 字体大小
		contentWriteFont.setFontHeightInPoints(fontHeightInPoints);
		// 设置加粗
		contentWriteFont.setBold(false);
		contentWriteCellStyle.setWriteFont(contentWriteFont);

		// 设置文字左右居中
		contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		// 设置文字上下居中
//        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.LEFT);
		// 设置 自动换行
		contentWriteCellStyle.setWrapped(true);

		// 样式策略
		return new HorizontalCellStyleStrategy(null, contentWriteCellStyle);
	}
	/**
	 * 设置单元格样式(仅用于示例)
	 *
	 * @return 样式策略
	 */
	public static HorizontalCellStyleStrategy getHorizontalCellStyleStrategy() {
		// 表头策略
		WriteCellStyle headerCellStyle = new WriteCellStyle();
		// 表头水平对齐居中
		headerCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
		headerCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// 背景色
		headerCellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
		WriteFont headerFont = new WriteFont();
		headerFont.setFontHeightInPoints((short) 15);
		headerCellStyle.setWriteFont(headerFont);
		// 自动换行
		headerCellStyle.setWrapped(Boolean.FALSE);

		// 内容策略
		WriteCellStyle contentCellStyle = new WriteCellStyle();
		// 设置背景色: 需要指定 FillPatternType 为FillPatternType.SOLID_FOREGROUND 不然无法显示背景颜色.头默认了 FillPatternType所以可以不指定
		contentCellStyle.setFillPatternType(FillPatternType.SOLID_FOREGROUND);
		contentCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
		// 设置内容靠左对齐
		contentCellStyle.setHorizontalAlignment(HorizontalAlignment.LEFT);
		contentCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		// 设置字体
		WriteFont contentFont = new WriteFont();
		contentFont.setFontHeightInPoints((short) 12);
		contentCellStyle.setWriteFont(contentFont);
		// 设置自动换行
		contentCellStyle.setWrapped(Boolean.TRUE);
		// 设置边框样式和颜色
		contentCellStyle.setBorderLeft(BorderStyle.MEDIUM);
		contentCellStyle.setBorderTop(BorderStyle.MEDIUM);
		contentCellStyle.setBorderRight(BorderStyle.MEDIUM);
		contentCellStyle.setBorderBottom(BorderStyle.MEDIUM);
		contentCellStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
		contentCellStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
		contentCellStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
		contentCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());

		// 将格式加入单元格样式策略
		return new HorizontalCellStyleStrategy(headerCellStyle, contentCellStyle);
	}
}

