package com.jpmsoft.gmgws.service;



import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.gmgws.pojo.entity.FileTemplate;

import java.util.List;

/**
 * 流程文档模板
 * create by:Aaron
 * create time: 2021-06-14
 */
public interface IFileTemplateService extends IService<FileTemplate> {


    FileTemplate getTemplate(String constructionPS);
}
