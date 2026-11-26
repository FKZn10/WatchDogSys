package com.jpmsoft.projectProducer.easyexcel;

import com.alibaba.excel.metadata.Head;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.write.handler.CellWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteTableHolder;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Code
 * com.jpmsoft.jpintelligentevalue.easyexcel
 * Description:
 * excel自适应列宽
 * @author: Dell-Daizhiyang
 * @date: 2024/1/25 19:34
 */

public class CustomCellWriteHandler implements CellWriteHandler {
    @Override
    public int order() {
        return Integer.MAX_VALUE;
    }

	/**
	 * 创建单元格之前的操作
	 */
	@Override
	public void beforeCellCreate(WriteSheetHolder writeSheetHolder, WriteTableHolder writeTableHolder, Row row,
	                             Head head, Integer integer, Integer integer1, Boolean aBoolean) {

	}

	/**
	 * 创建单元格之后的操作
	 */
	@Override
	public void afterCellCreate(WriteSheetHolder writeSheetHolder,WriteTableHolder writeTableHolder,Cell cell,
                                Head head,Integer integer,Boolean aBoolean) {

	}

    /**
	 * 单元格处理后(已写入值)的操作
	 */
	@Override
	public void afterCellDispose(WriteSheetHolder writeSheetHolder,WriteTableHolder writeTableHolder,
                                 List<WriteCellData<?>> list,Cell cell,Head head,Integer integer,Boolean isHead) {

		// 设置超链接
//		if (isHead && cell.getRowIndex() == 0 && cell.getColumnIndex() == 0) {
//			CreationHelper helper = writeSheetHolder.getSheet().getWorkbook().getCreationHelper();
//			Hyperlink hyperlink = helper.createHyperlink(HyperlinkType.URL);
//			hyperlink.setAddress("https://github.com/alibaba/easyexcel");
//			cell.setHyperlink(hyperlink);
//		}

		// 精确设置单元格格式
		Workbook workbook = writeSheetHolder.getSheet().getWorkbook();
		Map cellStyleMap = new HashMap<String, CellStyle>();
		if ((cell.getColumnIndex() == 5 ||cell.getColumnIndex() == 6)&&cell.getRowIndex() != 0 ) {
			if (cellStyleMap.get("缓存样式")!=null){
				CellStyle cellStyle = (CellStyle) cellStyleMap.get("缓存样式");
				cell.setCellStyle(cellStyle);
			}else {
				CellStyle cellStyle = workbook.createCellStyle();
				cellStyle.cloneStyleFrom(cell.getCellStyle());
				/* !! 注意：这行就是解锁单元格的代码，locked == true为锁定，locked == false为不锁定 */
				cellStyle.setLocked(false);
				cell.setCellStyle(cellStyle);
				cellStyleMap.put("缓存样式",cellStyle);
			}


		}
	}
}
