package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.fileUpload.mapper.AutoImportFileRecordMapper;
import com.jpmsoft.fileUpload.pojo.entity.beijing.AutoImportFileRecord;
import com.jpmsoft.fileUpload.service.IAutoImportFileRecordService;
import org.springframework.stereotype.Service;

@Service
public class AutoImportFileRecordImplService extends ServiceImpl<AutoImportFileRecordMapper, AutoImportFileRecord> implements IAutoImportFileRecordService {
}
