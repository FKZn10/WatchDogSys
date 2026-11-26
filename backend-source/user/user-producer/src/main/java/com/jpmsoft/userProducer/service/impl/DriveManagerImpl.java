package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.response.FileResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.DriveManagerMapper;
import com.jpmsoft.userProducer.pojo.entity.DriveManager;
import com.jpmsoft.userProducer.pojo.vo.VueTreeVo;
import com.jpmsoft.userProducer.service.IDriveManagerService;
import com.jpmsoft.userProducer.service.ISysDictionaryService;
import com.jpmsoft.userProducer.service.ISysFileAnnexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DriveManagerImpl extends ServiceImpl<DriveManagerMapper, DriveManager> implements IDriveManagerService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysDictionaryService sysDictionaryService;

    @Override
    public BaseResponse createOrUpdateDriveManager(DriveManager request) {
        Boolean create = false;
        if (ValidateUtil.isEmpty(request.getId())) {
            create = true;
            request.setId(GenerateUUID.getUUID());
        }
        saveOrUpdate(request);
        if (create) {
            sysFileAnnexService.addFormRefreshRelationFile(request.getDriveFile(), request.getId());
        } else
            sysFileAnnexService.refreshRelationFile(request.getDriveFile(), request.getDriveFileIds(), request.getId());
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getDriveManagerById(String id) {
        return BaseResponse.ok(baseMapper.selectById(id));
    }

    @Override
    public BaseResponse deleteById(String id) {
        DriveManager entity = baseMapper.selectById(id);
        if (entity == null)
            return BaseResponse.fail(BaseMsg.infoIsExist);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getDriveManagerList(DriveManager request) {
        LambdaQueryWrapper<DriveManager> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ValidateUtil.isNotEmpty(request.getDriveType()), DriveManager::getDriveType, request.getDriveType())
                .eq(ValidateUtil.isNotEmpty(request.getDriveStatus()), DriveManager::getDriveStatus, request.getDriveStatus())
                .like(ValidateUtil.isNotEmpty(request.getDriveName()), DriveManager::getDriveName, request.getDriveName())
                .like(ValidateUtil.isNotEmpty(request.getCreateUname()), DriveManager::getCreateUname, request.getCreateUname());
        IPage<DriveManager> page = new Page<>(request.getPage(), request.getSize());
        IPage<DriveManager> data = baseMapper.selectPage(page, wrapper);
        return BaseResponse.ok(data.getRecords(), (int) data.getTotal());
    }

    @Override
    public BaseResponse getDriveDownload() {
        LambdaQueryWrapper<DriveManager> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DriveManager::getDriveStatus, "1");
        List<DriveManager> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            Set<String> names = new HashSet<>();
            List<VueTreeVo> dictionaries = sysDictionaryService.getDictionaryByParentCode("JPMsoft_QDLX", null);
            data.forEach(f -> {
                if (ValidateUtil.isNotEmpty(f.getDriveFile())) {
                    List<FileResponse> files = sysFileAnnexService.getFileByFileRelationId(f.getDriveFile());
                    if (!CollectionUtils.isEmpty(files)) {
                        FileResponse fr = JacksonUtils.parseObject(JacksonUtils.toJSONString(files.get(0)), FileResponse.class);
                        if (fr != null) {
                            f.setUrl(fr.getOpenUrl());
                        }
                    }
                    if (!CollectionUtils.isEmpty(dictionaries)) {
                        List<VueTreeVo> dics = dictionaries.stream().filter(e -> e.getValue().equals(f.getDriveType())).collect(Collectors.toList());
                        if (!CollectionUtils.isEmpty(dics)) {
                            f.setDriveTypeName(dics.get(0).getTitle());
                            names.add(f.getDriveTypeName());
                        }
                    } else
                        log.info("字典JPMsoft_QDLX未检索到数据！");
                }
            });
            Map<String, List<DriveManager>> res = new HashMap<>();
            names.forEach(f -> {
                List<DriveManager> r = data.stream().filter(d -> d.getDriveTypeName().equals(f) && ValidateUtil.isNotEmpty(d.getUrl())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(r))
                    res.put(f, r);
            });
            return BaseResponse.ok(res);
        }
        return BaseResponse.ok();
    }
}
