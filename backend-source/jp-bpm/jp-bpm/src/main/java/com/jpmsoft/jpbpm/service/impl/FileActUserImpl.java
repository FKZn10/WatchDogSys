package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.jpbpm.mapper.FileActUserMapper;
import com.jpmsoft.jpbpm.pojo.entity.FileActUser;
import com.jpmsoft.jpbpm.service.FileActUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FileActUserImpl extends ServiceImpl<FileActUserMapper, FileActUser> implements FileActUserService {
}
