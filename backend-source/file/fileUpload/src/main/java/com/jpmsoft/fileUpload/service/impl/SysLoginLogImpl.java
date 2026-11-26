package com.jpmsoft.fileUpload.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.fileUpload.mapper.SysLoginLogMapper;
import com.jpmsoft.fileUpload.pojo.dto.SysLoginLogDto;
import com.jpmsoft.fileUpload.pojo.entity.SysLoginLog;
import com.jpmsoft.fileUpload.pojo.vo.SysLoginLogVo;
import com.jpmsoft.fileUpload.service.ISysLoginLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created By:李文涛 aaron
 * Created Time:2022/6/15 10:26
 */
@Slf4j
@Service
public class SysLoginLogImpl extends ServiceImpl<SysLoginLogMapper, SysLoginLog> implements ISysLoginLogService {


    @Override
    public ResponseEntity<byte[]> ExportUserLoginLog(SysLoginLogDto sysLoginLogDto, HttpServletResponse response) {
        try {
            QueryMap<SysLoginLogDto> queryMap = new QueryMap<>(sysLoginLogDto);
            queryMap.put("uName").put("uAccount").put("sllLoginType").put("sllIp").put("sllAction").put(sysLoginLogDto.getSllLoginTimes(),"sllLoginTimeStart","sllLoginTimeEnd");
            List<SysLoginLogVo> datas = baseMapper.getSysLoginLogList(queryMap.getQuery());
            Integer total = baseMapper.getSysLoginLogListCount(queryMap.getQuery());
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            WriteCellStyle headStyle = new WriteCellStyle();
            WriteFont headFont = new WriteFont();
            headFont.setFontHeightInPoints((short) 12);
            headFont.setFontName("Arial");
            headStyle.setWriteFont(headFont);

            WriteCellStyle contentStyle = new WriteCellStyle();
            WriteFont contentFont = new WriteFont();
            contentFont.setFontHeightInPoints((short) 10);
            contentFont.setFontName("Arial");
            contentStyle.setWriteFont(contentFont);

            HorizontalCellStyleStrategy styleStrategy = new HorizontalCellStyleStrategy(headStyle, contentStyle);
            EasyExcel.write(out, SysLoginLogVo.class)
                    .registerWriteHandler(styleStrategy)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
//                    .registerWriteHandler(new CustomColumnWidthStyleStrategy())
                    .sheet("SysLoginLogVo")
                    .doWrite(datas);

            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=logs.xlsx");
            response.getOutputStream().write(out.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();

            return new ResponseEntity<>(out.toByteArray(), HttpStatus.OK);
        } catch (
                IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
