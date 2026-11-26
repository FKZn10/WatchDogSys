package com.jpmsoft.projectProducer.easyexcel;

/**
 * Code
 * com.jpmsoft.jpintelligentevalue.easyexcel
 * Description:
 *
 * @author: Dell-Daizhiyang
 * @date: 2024/1/25 11:04
 */
import com.alibaba.excel.write.handler.SheetWriteHandler;
import com.alibaba.excel.write.metadata.holder.WriteSheetHolder;
import com.alibaba.excel.write.metadata.holder.WriteWorkbookHolder;
import org.apache.poi.xssf.streaming.SXSSFSheet;

/**
 * @author by liangzj
 * @since 2022/9/17 16:08
 */
public class CustomSheetWriteHandler implements SheetWriteHandler {
    @Override
    public int order() {
        return Integer.MAX_VALUE-1;
    }
    @Override
    public void afterSheetCreate(WriteWorkbookHolder writeWorkbookHolder, WriteSheetHolder writeSheetHolder) {
        // 设置保护密码
        writeSheetHolder.getSheet().protectSheet("^A>.Sw=y4wsxZEHj");
        // 锁定单元格不可选中(防止别人直接复制内容到其他excel修改)
        ((SXSSFSheet) writeSheetHolder.getSheet()).lockSelectLockedCells(true);
    }
}