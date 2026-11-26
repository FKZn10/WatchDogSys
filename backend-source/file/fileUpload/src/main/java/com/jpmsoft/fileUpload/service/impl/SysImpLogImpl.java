package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.mapper.SysImpLogMapper;
import com.jpmsoft.fileUpload.pojo.dto.SysImpLogDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysImpLog;
import com.jpmsoft.fileUpload.service.ISysImpLogService;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/2/20 15:27
 */
@Service
public class SysImpLogImpl extends ServiceImpl<SysImpLogMapper, SysImpLog> implements ISysImpLogService {

    @Override
    public void create(LogUserInfoVo user, File file, String msg, String fileName) {
            create(user,file,msg,fileName,false);
    }

    @Override
    public void create(LogUserInfoVo user, File file, String msg, String fileName, Boolean deleteImportZip) {
        SysImpLog log = new SysImpLog();
        log.setId(GenerateUUID.getUUID());
        if (!deleteImportZip) { //基础配置删除源zip
            log.setImpPath(file.getPath());
            log.setImpFileSize(file.length());
        }
        log.setImpTime(new Date());
        log.setImpUid(user.getId());
        log.setImpUname(user.getName());
        log.setImpResult(msg);
        log.setImpFileName(fileName);
        baseMapper.insert(log);
    }

    @Override
    public BaseResponse page(SysImpLogDTO request) {
        QueryWrapper<SysImpLog> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(request.getImpUid()))
            wrapper.eq("Imp_Uid", request.getImpUid());
        if (ValidateUtil.isNotEmpty(request.getImpUname()))
            wrapper.like("Imp_Uname", request.getImpUname());
        if (ValidateUtil.isNotEmpty(request.getImpFileName()))
            wrapper.like("Imp_File_Name", request.getImpFileName());
        if (!ValidateUtil.isAnObjEmpty(request.getSortType(), request.getSortFiled())) {
            if (request.getSortType().equals("ASC")) wrapper.orderByAsc(request.getSortFiled());
            else wrapper.orderByDesc(request.getSortFiled());
        } else wrapper.orderByDesc("Imp_Time");
        IPage<SysImpLog> iPage = baseMapper.selectPage(new Page<>(request.getPage(), request.getSize()), wrapper);
        Long total = iPage.getTotal();
        List<SysImpLog> data = iPage.getRecords();
        if (!CollectionUtils.isEmpty(data))
            return BaseResponse.ok(data.stream().map(d ->
                    {
                        SysImpLogDTO s = new SysImpLogDTO();
                        BeanUtils.copyProperties(d, s);
                        s.setImpPath(null);
                        return s;
                    }
            ).collect(Collectors.toList()), total.intValue());
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }

}
