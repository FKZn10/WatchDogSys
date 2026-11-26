package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.feign.UserFeign;
import com.jpmsoft.fileUpload.mapper.SysUserManualMapper;
import com.jpmsoft.fileUpload.pojo.dto.SysUserManualDTO;
import com.jpmsoft.fileUpload.pojo.entity.SysUserManual;
import com.jpmsoft.fileUpload.service.IFileUploadService;
import com.jpmsoft.fileUpload.service.ISysFileAnnexService;
import com.jpmsoft.fileUpload.service.ISysUserManualService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/12/26 9:08
 */
@Service
public class SysUserManualImpl extends ServiceImpl<SysUserManualMapper, SysUserManual> implements ISysUserManualService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private IFileUploadService fileUploadService;
    @Resource
    private UserFeign userFeign;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateManual(SysUserManualDTO request, String token) {
        SysUserManual entity = new SysUserManual();
        BeanUtils.copyProperties(request, entity);
        String codeRule = null;
        if (userFeign.sequenceRestEnable("SysUserManual", token) && ValidateUtil.isEmpty(request.getId())) {
            entity.setUmCode(userFeign.generateCodeByTCode("SysUserManual", request, token));
            if (ValidateUtil.isEmpty(entity.getUmCode())) return new BaseResponse("生成编码失败");
            codeRule = "SysUserManual";
        }
        if (ValidateUtil.isEmpty(request.getId())) {
            entity.setId(GenerateUUID.getUUID());
            entity.setCreateTime(new Date());
            baseMapper.insert(entity);
            if (ValidateUtil.isNotEmpty(codeRule)) userFeign.refershAutoNo(codeRule, entity, token);
        } else {
            SysUserManual data = baseMapper.selectById(request.getId());
            if (data == null) return BaseResponse.fail(FileMsg.infoNotExist);
            entity.setCreateTime(data.getCreateTime());
            entity.setCreateUname(data.getCreateUname());
            entity.setCreateUid(data.getCreateUid());
            baseMapper.updateById(entity);
        }
        if (ValidateUtil.isNotEmpty(request.getUmContent())) {
            //富文本文件新增时无法确认用户是否删除文件，所以最终也要根据IDS去比对
            List<Map<String, String>> params = new ArrayList<>();
            Map<String, String> param = new HashMap<>();
            param.put("fileRelationId", request.getRteFileRelationId());
            param.put("fileIds", request.getRteFileIds());
            params.add(param);
            sysFileAnnexService.refreshRelationFile(params, entity.getId());
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getUserManualTree(SysUserManualDTO request) {
        List<SysUserManualDTO> data = baseMapper.selectAll();
        if (!CollectionUtils.isEmpty(data)) {
            List<SysUserManualDTO> res = new ArrayList<>();
            List<SysUserManualDTO> root = data.stream().filter(f -> ValidateUtil.isEmpty(f.getUmParentId()) || f.getUmParentId().equals("0")).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(root)) {
                for (SysUserManualDTO r : root) {
                    List<SysUserManualDTO> child = data.stream().filter(f -> ValidateUtil.isNotEmpty(f.getUmParentId()) && f.getUmParentId().equals(r.getId())).collect(Collectors.toList());
                    setTree(child, data);
                    r.setChildNodes(child);
                    res.add(r);
                }
                return BaseResponse.ok(res);
            }
        }
        return BaseResponse.ok();
    }

    private void setTree(List<SysUserManualDTO> root, List<SysUserManualDTO> data) {
        if (!CollectionUtils.isEmpty(root)) {
            for (SysUserManualDTO r : root) {
                List<SysUserManualDTO> child = data.stream().filter(f -> ValidateUtil.isNotEmpty(f.getUmParentId()) && f.getUmParentId().equals(r.getId())).collect(Collectors.toList());
                setTree(child, data);
                r.setChildNodes(child);
            }
        }
    }


    @Override
    public BaseResponse getUserManualById(String id) {
        SysUserManual data = baseMapper.selectById(id);
        if (data == null) return BaseResponse.fail(FileMsg.infoNotExist);
        SysUserManualDTO res = new SysUserManualDTO();
        BeanUtils.copyProperties(data, res);
        return BaseResponse.ok(res);
    }

    @Override
    public BaseResponse getUserManualByKey(String key) {
        QueryWrapper<SysUserManual> wrapper = new QueryWrapper<>();
        wrapper.like("Um_Keys", key);
        List<SysUserManual> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) return BaseResponse.ok(data.stream().map(d -> {
            SysUserManualDTO r = new SysUserManualDTO();
            BeanUtils.copyProperties(d, r);
            return r;
        }).collect(Collectors.toList()));
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getUserManualByCode(String code) {
        QueryWrapper<SysUserManual> wrapper = new QueryWrapper<>();
        wrapper.eq("Um_Keys", code);
        List<SysUserManual> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            SysUserManualDTO r = new SysUserManualDTO();
            BeanUtils.copyProperties(data.get(0), r);
            return BaseResponse.ok(r);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse deleteUserManualById(SysUserManualDTO request) {
        if (ValidateUtil.isEmpty(request.getId())) return BaseResponse.fail(FileMsg.noReqParam);
        SysUserManual data = baseMapper.selectById(request.getId());
        if (data == null) return BaseResponse.fail(FileMsg.infoNotExist);
        if (haveChild(request.getId())) return BaseResponse.fail(FileMsg.childCannotDel);
        baseMapper.deleteById(request.getId());
        if (ValidateUtil.isNotEmpty(request.getRteFileRelationId()))
            fileUploadService.removeFileByIds(sysFileAnnexService.getFileIdsByRelationIds(Arrays.asList(request.getRteFileRelationId())));
        return BaseResponse.ok();
    }


    private boolean haveChild(String id) {
        QueryWrapper<SysUserManual> wrapper = new QueryWrapper<>();
        wrapper.eq("Um_Parent_Id", id);
        return baseMapper.exists(wrapper);
    }
}
