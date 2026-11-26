package com.jpmsoft.fileUpload.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.alibaba.excel.write.style.column.LongestMatchColumnWidthStyleStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.mapper.SysDownloadLogMapper;
import com.jpmsoft.fileUpload.pojo.SysDownloadLogExportDto;
import com.jpmsoft.fileUpload.pojo.dto.SysDownloadLogDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysDownloadLog;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.service.IApiUtilsService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.service.ISysDownloadLogService;
import com.jpmsoft.fileUpload.service.ISysFileAnnexService;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/9/28 9:29
 */
@Service
public class SysDownloadLogImpl extends ServiceImpl<SysDownloadLogMapper, SysDownloadLog> implements ISysDownloadLogService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private IApiUtilsService apiUtilsService;

    @Override
    public void create(String uid, String uname, String fileId, String fileName, String formId) {
        save(getEntity(uid, uname, fileId, fileName, formId, sysConfigService.getInterfaceEncDec()));
    }

    @Override
    public void create(LogUserInfoVo user, String fileId, String fileName, String formId) {
        if (user == null) return;
        if (ValidateUtil.isAnObjEmpty(fileId, fileName)) return;
        save(getEntity(user.getId(), user.getName(), fileId, fileName, formId, sysConfigService.getInterfaceEncDec()));
    }

    @Override
    public void create(LogUserInfoVo user, SysFileAnnex file, Boolean down) {
        if (down == null || !down) return;
        if (user == null) return;
        if (file == null) return;
        save(getEntity(user.getId(), user.getName(), file.getFileId(), file.getFileName(), file.getFileEntityId(), sysConfigService.getInterfaceEncDec()));
    }

    @Override
    public void create(LogUserInfoVo user, String fileIds, Boolean down) {
        if (down == null || !down) return;
        if (user == null) return;
        if (ValidateUtil.isEmpty(fileIds)) return;
        List<SysFileAnnex> files = sysFileAnnexService.getFileListByFileIds(fileIds);
        if (!CollectionUtils.isEmpty(files)) {
            InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
            List<SysDownloadLog> entities = files.stream().map(f -> getEntity(user.getId(), user.getName(), f.getFileId(), f.getFileName(), f.getFileEntityId(), ied)).collect(Collectors.toList());
            saveBatch(entities);
        }
    }

    @Override
    public void create(LogUserInfoVo user, List<SysFileAnnex> files) {
        if (user == null) return;
        if (CollectionUtils.isEmpty(files)) return;
        InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
        List<SysDownloadLog> entities = files.stream().map(f -> getEntity(user.getId(), user.getName(), f.getFileId(), f.getFileName(), f.getFileEntityId(), ied)).collect(Collectors.toList());
        saveBatch(entities);
    }

    @Override
    public SysDownloadLog getEntity(String uid, String uname, String fileId, String fileName, String formId, InterfaceEncDecDTO ied) {
        SysDownloadLog log = new SysDownloadLog();
        log.setId(GenerateUUID.getUUID());
        log.setDlUid(uid);
        log.setDlUname(uname);
        log.setDlTime(new Date());
        log.setDlFileId(fileId);
        log.setDlFileName(fileName);
        log.setDlFormId(formId);
        log.setSign(apiUtilsService.sign(ied, ied.getDownloadSign(), log));
        return log;
    }

    @Override
    public BaseResponse getDownloadLogList(SysDownloadLogDTO request) {
        QueryMap<SysDownloadLogDTO> queryMap = new QueryMap<>(request);
        queryMap.put("dlUid").put("dlUname").put("dlFileName")
                .put(request.getDlTimes(), "dllTimeStart", "dlTimeEnd");
        IPage<SysDownloadLog> page = new Page<>(request.getPage(), request.getSize());
        List<SysDownloadLog> data = baseMapper.getDownloadLogList(page, queryMap.getQuery());
        if (!CollectionUtils.isEmpty(data)) {
            InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
            data.forEach(d -> {
                apiUtilsService.signVerify(ied, ied.getDownloadListVerify(), d, d.getSign());
            });
        }
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public ResponseEntity<byte[]> ExportDownloadLog(SysDownloadLogDTO request, HttpServletResponse response) {
        try {
            QueryMap<SysDownloadLogDTO> queryMap = new QueryMap<>(request);
            queryMap.put("dlUid").put("dlUname").put("dlFileName")
                    .put(request.getDlTimes(), "dllTimeStart", "dlTimeEnd");
            IPage<SysDownloadLog> page = new Page<>(1, 5000);
            List<SysDownloadLog> data = baseMapper.getDownloadLogList(page, queryMap.getQuery());
            InterfaceEncDecDTO ied = sysConfigService.getInterfaceEncDec();
            List<SysDownloadLogExportDto> truncatedRecords = data.stream().map(info -> {
                apiUtilsService.signVerify(ied, ied.getDownloadListVerify(), info, info.getSign());
                SysDownloadLogExportDto dto = new SysDownloadLogExportDto();
                BeanUtils.copyProperties(info, dto);
                return dto;
            }).collect(Collectors.toList());

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
            EasyExcel.write(out, SysDownloadLogExportDto.class)
                    .registerWriteHandler(styleStrategy)
                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
//                    .registerWriteHandler(new CustomColumnWidthStyleStrategy())
                    .sheet("SysDownloadLogDTO")
                    .doWrite(truncatedRecords);

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
