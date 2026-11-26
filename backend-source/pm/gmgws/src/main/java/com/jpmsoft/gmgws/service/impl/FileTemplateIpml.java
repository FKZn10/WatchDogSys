package com.jpmsoft.gmgws.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.SysExtendConfDTO;
import com.jpmsoft.common.model.response.FileTemplateTree;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;

import com.jpmsoft.gmgws.mapper.FileTemplateMapper;
import com.jpmsoft.gmgws.pojo.entity.FileTemplate;
import com.jpmsoft.gmgws.service.IFileTemplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 流程文档模板
 * <p>
 * create by:Aaron
 * create time: 2021-06-14
 */
@Service
public class FileTemplateIpml extends ServiceImpl<FileTemplateMapper, FileTemplate> implements IFileTemplateService {


    @Override
    public FileTemplate getTemplate(String constructionPS) {
        LambdaQueryWrapper<FileTemplate> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(FileTemplate::gettCode,constructionPS);
        return  baseMapper.selectOne(queryWrapper);
    }
}
