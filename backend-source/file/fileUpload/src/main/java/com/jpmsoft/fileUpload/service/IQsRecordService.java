package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.Qs.QualitySafetyStatisticFowYWRequest;
import com.jpmsoft.fileUpload.pojo.dto.ProSafetyLedgerDtlDTO;
import com.jpmsoft.fileUpload.pojo.dto.QsPlanTaskDto;
import com.jpmsoft.fileUpload.pojo.dto.QsRecordExcelDTO;
import com.jpmsoft.fileUpload.pojo.entity.QsRecord;
import com.jpmsoft.fileUpload.pojo.vo.QsRecordVo;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/4 11:21
 */
public interface IQsRecordService extends IService<QsRecord> {

    List<QsRecordExcelDTO> selectExcelData(QsRecordExcelDTO request);

    void exportQsRecordExcel(HttpServletResponse res,QsRecordExcelDTO request);

    ResponseEntity<byte[]> getQsPlanTaskListExport(QsPlanTaskDto request, String token, HttpServletResponse response);

    ResponseEntity<byte[]> getQualitySafetyListExport(QualitySafetyStatisticFowYWRequest request, HttpServletResponse response);

    ResponseEntity<byte[]> getProSafetyLedgerPageYWExport(ProSafetyLedgerDtlDTO request, String token,HttpServletResponse response);

    ResponseEntity<byte[]> getQsRecordYwExport(QsRecordVo request, HttpServletResponse response);
}
