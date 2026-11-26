package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeBrandLibraryMapper;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandLibraryDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandLibrary;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandLibraryApply;
import com.jpmsoft.projectProducer.pojo.vo.materialsEquipment.MeBrandLibraryVo;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeBrandLibraryApplyService;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeBrandLibraryService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/23 9:53
 */
@Service
public class MeBrandLibraryImpl extends FileImpl<MeBrandLibraryMapper, MeBrandLibrary, MeBrandLibraryDto>
        implements IMeBrandLibraryService {

    @Resource
    private IMeBrandLibraryApplyService meBrandLibraryApplyService;
    @Resource
    private ISysUserService sysUserService;


    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "id", codeParam = "brandCode", method = "setBrandCode", codeRule = "MeBrandLibrary", beanClass = MeBrandLibraryDto.class)
    public BaseResponse createOrUpdateMeBrandLibrary(MeBrandLibraryDto request) {
        MeBrandLibrary entity = new MeBrandLibrary();
        createOrUpdateFileEntity(entity, request);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public void createOrUpdateMeBrandLibrary(List<MeBrandLibraryDto> datas, String blaId, String teId) {
        if (!CollectionUtils.isEmpty(datas)) {
            List<String> allIds = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getId())).map(MeBrandLibraryDto::getId).collect(Collectors.toList());
            List<String> oldIds = baseMapper.getAllIdByBlaId(blaId);
            if (!CollectionUtils.isEmpty(oldIds)) {
                oldIds.removeAll(allIds);
                if (!CollectionUtils.isEmpty(oldIds)) {
                    //需要删除的流程数据
                    sysFileService.deleteByFileIds(oldIds);
                    baseMapper.deleteBatchIds(oldIds);
                }
            }

            for (MeBrandLibraryDto data : datas) {
                data.setBlaId(blaId);
                data.setTeId(teId);
                createOrUpdateMeBrandLibrary(data);
            }
        }
    }

    @Override
    public BaseResponse syncMeBrandLibraryApplyState(String blaId) {
        MeBrandLibraryApply apply = meBrandLibraryApplyService.getById(blaId);
        if (apply == null) return BaseResponse.fail(ResMsg.infoNotExist);
        List<MeBrandLibrary> entities = getMeBrandLibraryByBlaId(blaId);
        if (!CollectionUtils.isEmpty(entities)) {
            List<String> allIds = new ArrayList<>();
            List<MeBrandLibrary> news = entities.stream().map(entity -> {
                entity.setState(apply.getState());
                entity.setStateTime(apply.getStateTime());
                allIds.add(entity.getId());
                return entity;
            }).collect(Collectors.toList());
            updateBatchById(news);
            sysFileService.updateFileStateBatchIds(allIds, apply.getState());
        }
        return null;
    }

    @Override
    public List<MeBrandLibrary> getMeBrandLibraryByBlaId(String blaId) {
        QueryWrapper<MeBrandLibrary> wrapper = new QueryWrapper<>();
        wrapper.eq("Bla_Id", blaId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public BaseResponse getMeBrandLibraryList(MeBrandLibraryDto request, String uId) {
        String authSql = sysUserService.onlyShowCancel(uId, "Create_Uid", "state");
        QueryMap<MeBrandLibraryDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("brandName").put("brandCode").put("createUname")
                .put("brandEnable").put("brandLevel").put("metId").put("state");
        IPage<MeBrandLibraryVo> page = new Page<>(request.getPage(), request.getSize());
        List<MeBrandLibraryVo> datas = baseMapper.getMeBrandLibraryList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMeBrandLibraryById(String id) {
        return BaseResponse.ok(baseMapper.getMeBrandLibraryById(id));
    }

    @Override
    public BaseResponse getMeBrandLibraryByMetId(String metId) {
        return BaseResponse.ok(baseMapper.getMeBrandLibraryByMetId(metId));
    }
}
