package com.jpmsoft.projectProducer.service.archive.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.Archive.ArchiveFiletemplateRequest;
import com.jpmsoft.common.model.response.Archive.ArchiveFiletemplateResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.archive.ArchiveFiletemplateMapper;
import com.jpmsoft.projectProducer.pojo.entity.archive.ArchiveFiletemplate;
import com.jpmsoft.projectProducer.service.archive.IArchiveFiletemplateService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ArchiveFiletemplateImpl extends ServiceImpl<ArchiveFiletemplateMapper, ArchiveFiletemplate> implements IArchiveFiletemplateService {

    @Override
    public void createOrUpdateArchiveFiletemplate(List<ArchiveFiletemplateRequest> datas, String adId) {
        deleteArchiveFiletemplateByAdId(adId);
        List<ArchiveFiletemplate> entitys = new ArrayList<>();
        if (datas != null && datas.size() > 0) {
            datas.forEach(model -> {
                ArchiveFiletemplate entity = new ArchiveFiletemplate();
                BeanUtils.copyProperties(model, entity);
                entity.setAtId(GenerateUUID.getUUID());
                entity.setAdId(adId);
                entity.setAtCreateTime(model.getAtCreateTime() == null ? new Date() : model.getAtCreateTime());
                entitys.add(entity);
            });
            if (entitys.size() > 0)
                saveBatch(entitys);
        }
    }

    @Override
    public BaseResponse getArchiveFiletemplateByAdId(String adId) {
        List<ArchiveFiletemplateResponse> data = baseMapper.getArchiveFiletemplateByAdId(adId);
        return BaseResponse.ok(data);
    }

    @Override
    public void checkAlreadyInDiDirectory(List<ArchiveFiletemplateRequest> datas, String adId) {
        if (CollectionUtils.isEmpty(datas)) return;
        List<String> tCodes = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getAtTCode())).map(ArchiveFiletemplateRequest::getAtTCode).collect(Collectors.toList());
        List<ArchiveFiletemplateResponse> ress = baseMapper.getArchiveFiletemplateByTCodes(tCodes, adId);
        if (!CollectionUtils.isEmpty(ress)) {
            ArchiveFiletemplateResponse res = ress.get(0);
            String adName = ValidateUtil.isEmpty(res.getAdLink()) ? res.getAdName() : res.getAdLink();
            throw new JPMException(String.format(ResMsg.archiveTempExists, res.gettName(), adName));
        }
    }

    @Override
    public boolean directoryHavingTemplate(List<String> adIds) {
        QueryWrapper<ArchiveFiletemplate> wrapper = new QueryWrapper<>();
        wrapper.in("Ad_Id", adIds);
        if (baseMapper.exists(wrapper)) return true;
        return false;
    }


    private void deleteArchiveFiletemplateByAdId(String adId) {
        QueryWrapper<ArchiveFiletemplate> wrapper = new QueryWrapper<>();
        wrapper.eq("Ad_Id", adId);
        baseMapper.delete(wrapper);
    }
}
