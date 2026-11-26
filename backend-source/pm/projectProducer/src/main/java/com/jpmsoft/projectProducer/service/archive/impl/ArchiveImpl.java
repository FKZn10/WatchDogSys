package com.jpmsoft.projectProducer.service.archive.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.model.request.Archive.ArchiveRequest;
import com.jpmsoft.common.model.response.Archive.ArchiveFileResponse;
import com.jpmsoft.common.model.response.Archive.ArchiveResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.archive.ArchiveMapper;
import com.jpmsoft.projectProducer.pojo.entity.archive.Archive;
import com.jpmsoft.projectProducer.service.archive.IArchiveFileService;
import com.jpmsoft.projectProducer.service.archive.IArchiveService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.JudgeUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;

@Service
public class ArchiveImpl extends ServiceImpl<ArchiveMapper, Archive> implements IArchiveService {

    @Resource
    private IArchiveFileService archiveFileService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateArchive(ArchiveRequest request) {
        Archive entity = new Archive();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getApId())) {
            entity.setApId(GenerateUUID.getUUID());
            entity.setApDescCreateTime(new Date());
            baseMapper.insert(entity);
        } else {
            Archive model = baseMapper.selectById(request.getApId());
            if (model == null) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            if (JudgeUtil.nonModifyState(model.getApState())) {
                return BaseResponse.fail(ResMsg.stateNotModify);
            }
            // 判断是否还有文件，没有就不执行更新，后面删除
            if (ValidateUtil.isNotEmpty(request.getArchiveFiles())) {
                entity.setApDescCreateTime(model.getApDescCreateTime());
                entity.setApDescCreateUid(model.getApDescCreateUid());
                entity.setApDescCreateUname(model.getApDescCreateUname());
                baseMapper.updateById(entity);
            }
        }
        archiveFileService.createOrUpdateArchiveFile(request.getArchiveFiles(), entity.getApId(), entity.getApState(), request.getFileCode(), entity.getApDescCreateUid()
                , entity.getApTransferCreateUname(),request.getTeId());
        if (ValidateUtil.isEmpty(request.getArchiveFiles())) {
            this.removeById(entity);
        }
        return BaseResponse.ok(entity.getApId());
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteArchiveByApId(String apId) {
        Archive model = baseMapper.selectById(apId);
        if (model == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        if (ValidateUtil.isNotNull(model.getApState()) && model.getApState() == 1)
            return BaseResponse.fail(ResMsg.archiveNotDel);
        archiveFileService.deleteAllArchiveFileByApId(apId);
        baseMapper.deleteById(apId);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getArchiveList(ArchiveRequest request, String uId) {
        String authSql = sysUserService.getAuthSql("Archive", uId, "Ap_Desc_Create_Uid", OutUserAuthEnum.NOT.getIndex());
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ArchiveRequest> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("adId").put("apKeyword").put("apTitle")
                .put("apAuthor").put("apDocumentNo").put("apReceiptNo").put("pName").put("apState");
        IPage<ArchiveResponse> page = new Page<>(request.getPage(), request.getSize());
        List<ArchiveResponse> datas = baseMapper.getArchiveList(page, params.getQuery());
        List<String> apIds = new ArrayList<>();
        datas.forEach(data -> {
            apIds.add(data.getApId());
        });
        List<ArchiveFileResponse> files = archiveFileService.getArchiveFileByApIds(apIds);
        Map<String, Object> data = new HashMap<>();
        data.put("archive", datas);
        data.put("archiveFile", files);
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public boolean directoryHavingArchiveFile(List<String> adIds) {
        QueryWrapper<Archive> wrapper = new QueryWrapper<>();
        wrapper.in("Ad_Id", adIds);
        if (baseMapper.exists(wrapper)) return true;
        return false;
    }


}
