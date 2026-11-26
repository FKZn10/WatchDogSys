package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.FileFlowActMapper;
import com.jpmsoft.jpbpm.pojo.entity.FileFlowAct;
import com.jpmsoft.jpbpm.service.FileFlowActService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileFlowActImpl extends ServiceImpl<FileFlowActMapper, FileFlowAct> implements FileFlowActService {
}
