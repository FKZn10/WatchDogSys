package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.mapper.SysFileDownloadMapper;
import com.jpmsoft.fileUpload.pojo.entity.SysFileDownload;
import com.jpmsoft.fileUpload.service.ISysFileDownloadService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SysFileDownloadImpl extends ServiceImpl<SysFileDownloadMapper, SysFileDownload> implements ISysFileDownloadService {


    @Override
    public void insertSysFileDownload(SysFileDownload sysFileDownloadPojo) {
        String[] fileIds = sysFileDownloadPojo.getFileIds().split(",");
        Arrays.sort(fileIds);
        String ids = "";
        for (String id : fileIds) {
            ids += id;
        }
        sysFileDownloadPojo.setFileIds(ids);
        baseMapper.insert(sysFileDownloadPojo);
    }

    @Override
    public SysFileDownload getSysFileDownload(String fileIds) {
        String[] fileId = fileIds.split(",");
        Arrays.sort(fileId);
        String ids = StringUtils.join(fileId);
        List<SysFileDownload> datas = baseMapper.getSysFileDownload(ids);
        if (datas != null && datas.size() > 0)
            return datas.get(0);
        return null;
    }

    @Override
    public List<SysFileDownload> getSysFileDownloadBath(List<String> fileIds) {
        if (ValidateUtil.isEmpty(fileIds)) return null;
        List<String> ids = new ArrayList<>();
        fileIds.forEach(fileId -> {
            String[] id = fileId.split(",");
            Arrays.sort(id);
            String idStr = StringUtils.join(id);
            ids.add(idStr);
        });
        if (ids.size() > 1) return baseMapper.getSysFileDownloadBath(ids);
        else if (ids.size() == 1) return baseMapper.getSysFileDownload(ids.get(0));
        return new ArrayList<>();
    }

    @Override
    public void deleteFileDownLoadByFileId(String fileId) {
        QueryWrapper<SysFileDownload> wrapper = new QueryWrapper<>();
        wrapper.like("File_Ids", fileId);
        baseMapper.delete(wrapper);
    }
}
