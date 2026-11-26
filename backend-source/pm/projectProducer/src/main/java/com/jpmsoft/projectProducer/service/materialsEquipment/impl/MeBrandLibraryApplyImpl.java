package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeBrandLibraryApplyMapper;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandLibraryApplyDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandLibraryApply;
import com.jpmsoft.projectProducer.pojo.vo.materialsEquipment.MeBrandLibraryApplyVo;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeBrandLibraryApplyService;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeBrandLibraryService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:06
 */
@Service
public class MeBrandLibraryApplyImpl extends FileImpl<MeBrandLibraryApplyMapper, MeBrandLibraryApply, MeBrandLibraryApplyDto>
        implements IMeBrandLibraryApplyService {

    @Resource
    private IMeBrandLibraryService meBrandLibraryService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "blaId", codeParam = "blaCode", method = "setBlaCode", codeRule = "MeBrandLibraryApply", beanClass = MeBrandLibraryApplyDto.class)
    public BaseResponse createOrUpdateMeBrandLibraryApply(MeBrandLibraryApplyDto request) {
        MeBrandLibraryApply entity = new MeBrandLibraryApply();
        createOrUpdateFileEntity(entity, request);
        meBrandLibraryService.createOrUpdateMeBrandLibrary(request.getMeBrandLibrarys(), entity.getBlaId(),request.getTeId());
        return BaseResponse.ok(entity.getBlaId());
    }

    @Override
    public BaseResponse getMeBrandLibraryApplyList(MeBrandLibraryApplyDto request, String uId) {
        String authSql = sysUserService.onlyShowCancel(uId, "Create_Uid", "state");
        QueryMap<MeBrandLibraryApplyDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("blaCode").put("blaTitle").put("state").put("createUname");
        IPage<MeBrandLibraryApplyVo> page = new Page<>(request.getPage(), request.getSize());
        List<MeBrandLibraryApplyVo> datas = baseMapper.getMeBrandLibraryApplyList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMeBrandLibraryApplyById(String id) {
        return BaseResponse.ok(baseMapper.getMeBrandLibraryApplyById(id));
    }
}
