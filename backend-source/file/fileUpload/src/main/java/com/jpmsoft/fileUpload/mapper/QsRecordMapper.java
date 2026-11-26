package com.jpmsoft.fileUpload.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jpmsoft.fileUpload.pojo.dto.QsRecordExcelDTO;
import com.jpmsoft.fileUpload.pojo.entity.QsRecord;
import com.jpmsoft.fileUpload.pojo.vo.QsRecordVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/4 11:20
 */
public interface QsRecordMapper extends BaseMapper<QsRecord> {


    List<QsRecordExcelDTO> selectExcelData(@Param("params") Map<String, Object> params);

    List<QsRecordVo> getQsRecordYwExport(@Param("params")  QsRecordVo request);
}
