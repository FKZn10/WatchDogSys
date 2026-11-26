package com.jpmsoft.userProducer.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseFileDto;
import com.jpmsoft.userProducer.pojo.entity.SysFile;


import java.util.Date;
import java.util.List;

public interface ISysFileService extends IService<SysFile> {


    <T,D extends BaseFileDto> boolean  FileHelper(T t,D request,String action,String fileRelationId) ;
}
