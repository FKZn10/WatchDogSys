package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.userProducer.pojo.entity.File;
import org.springframework.stereotype.Service;

@Service
public interface FileService extends IService<File> {

    boolean exists(String flowId,String tCode);
}
