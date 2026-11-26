package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.fileUpload.mapper.SecSelectionRecordMapper;
import com.jpmsoft.fileUpload.pojo.entity.SecSelectionRecord;
import com.jpmsoft.fileUpload.service.ISecSelectionRecordService;
import org.springframework.stereotype.Service;

@Service
public class SecSelectionRecordImpl extends ServiceImpl<SecSelectionRecordMapper, SecSelectionRecord> implements ISecSelectionRecordService {
}
