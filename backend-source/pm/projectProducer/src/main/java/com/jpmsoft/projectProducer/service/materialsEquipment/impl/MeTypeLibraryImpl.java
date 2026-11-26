package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.MeEnter.MeTypeLibraryRequest;
import com.jpmsoft.common.model.response.MeEnter.MeTypeLibraryResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeTypeLibraryMapper;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeTypeLibrary;
import com.jpmsoft.projectProducer.pojo.vo.materialsEquipment.MeTypeLibraryWithTreeVO;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeEnterApplyDtlService;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeTypeLibraryService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class MeTypeLibraryImpl extends ServiceImpl<MeTypeLibraryMapper, MeTypeLibrary> implements IMeTypeLibraryService {

    @Resource
    private IMeEnterApplyDtlService meEnterApplyDtlService;

    @Override
    public BaseResponse createOrUpdateMeTypeLibrary(MeTypeLibraryRequest request) {
        MeTypeLibrary entity = new MeTypeLibrary();
        BeanUtils.copyProperties(request, entity);
        QueryWrapper<MeTypeLibrary> wrapper = new QueryWrapper<>();
        wrapper.eq("Met_Code", request.getMetCode());
        if (!ValidateUtil.isEmpty(request.getMetId()))
            wrapper.ne("Met_Id", request.getMetId());
        if (baseMapper.selectCount(wrapper) > 0)
            return BaseResponse.fail(ResMsg.codeIsRepeat);
        if (ValidateUtil.isEmpty(entity.getMetParentId()))
            entity.setMetParentId("0");

        if (ValidateUtil.isEmpty(request.getMetId())) {
            entity.setMetId(GenerateUUID.getUUID());
            entity.setMetSort(getParentTotal(entity.getMetParentId()) + 1);
            entity.setMetCreateTime(new Date());
            baseMapper.insert(entity);
        } else {
            MeTypeLibrary model = baseMapper.selectById(request.getMetId());
            if (model == null)
                return BaseResponse.fail(ResMsg.infoNotExist);
            if (!model.getMetParentId().equals(entity.getMetParentId()))
                entity.setMetSort(getParentTotal(entity.getMetParentId()) + 1);
            else
                entity.setMetSort(model.getMetSort());
            entity.setMetCreateTime(model.getMetCreateTime());
            entity.setMetCreateUid(model.getMetCreateUid());
            entity.setMetCreateUname(model.getMetCreateUname());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getMetId());
    }

    @Override
    public BaseResponse getMeTypeLibraryList(MeTypeLibraryRequest request) {
        QueryMap<MeTypeLibraryRequest> params = new QueryMap<>(request);
        params.put("metParentId").put("metName").put("metCode").put("metState");
        IPage<MeTypeLibraryResponse> page = new Page<>(request.getPage(), request.getSize());
        List<MeTypeLibraryResponse> datas = baseMapper.getMeTypeLibraryList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse upOrDownMeTypeLibrarySort(String metId, String action, String teId) {
        MeTypeLibrary entity = baseMapper.selectById(metId);
        if (entity == null)
            return BaseResponse.fail(ResMsg.infoNotExist);
        QueryWrapper<MeTypeLibrary> wrapper = new QueryWrapper<>();
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        wrapper.eq("Met_Parent_Id", entity.getMetParentId());
        wrapper.orderByAsc("Met_Sort");
        List<MeTypeLibrary> sameLevels = baseMapper.selectList(wrapper);
        if (sameLevels != null && sameLevels.size() > 1) {
            MeTypeLibrary entity1;
            int index = sameLevels.indexOf(entity);
            if (action.equalsIgnoreCase("UP")) {
                if (index == 0)
                    return BaseResponse.fail(ResMsg.actError);
                entity1 = sameLevels.get(index - 1);
            } else if (action.equalsIgnoreCase("DOWN")) {
                if (index == sameLevels.size())
                    return BaseResponse.fail(ResMsg.actError);
                entity1 = sameLevels.get(index + 1);
            } else
                return BaseResponse.fail(ResMsg.actError);
            Integer sort = entity.getMetSort();
            entity.setMetSort(entity1.getMetSort());
            entity1.setMetSort(sort);
            updateBatchById(Arrays.asList(entity, entity1));
        } else
            return BaseResponse.fail(ResMsg.actError);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse delMeTypeLibraryById(String id) {
        QueryWrapper<MeTypeLibrary> wrapper = new QueryWrapper<>();
        wrapper.eq("Met_Parent_Id", id);
        if (baseMapper.exists(wrapper)) return BaseResponse.fail(ResMsg.delDownNodeFirst);
        if (meEnterApplyDtlService.existWithMetId(id)) return BaseResponse.fail(ResMsg.delAssociated);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse getDownPullListWithTree() {
        List<MeTypeLibrary> list = list(new LambdaQueryWrapper<MeTypeLibrary>()
                .select(MeTypeLibrary::getMetId, MeTypeLibrary::getMetName, MeTypeLibrary::getMetParentId));
        List<MeTypeLibraryWithTreeVO> voList = list.stream().map(e -> {
            MeTypeLibraryWithTreeVO vo = new MeTypeLibraryWithTreeVO();
            vo.setMetId(e.getMetId());
            vo.setValue(e.getMetId());
            vo.setLabel(e.getMetName());
            vo.setParentId(e.getMetParentId());
            return vo;
        }).collect(Collectors.toList());
        Map<String, MeTypeLibraryWithTreeVO> voMap = new HashMap<>();
        for (MeTypeLibraryWithTreeVO vo : voList) {
            voMap.put(vo.getMetId(), vo);
        }
        for (MeTypeLibraryWithTreeVO vo : voList) {
            MeTypeLibraryWithTreeVO parentVo = voMap.get(vo.getParentId());
            if (parentVo == null) {
                continue;
            }
            if (parentVo.getChildren() == null){
                parentVo.setChildren(new ArrayList<>());
            }
            parentVo.getChildren().add(vo);
        }
        List<MeTypeLibraryWithTreeVO> rootNodeList = voList.stream().filter(e -> "0".equals(e.getParentId())).collect(Collectors.toList());
        return BaseResponse.ok(rootNodeList);
    }

    public Integer getParentTotal(String parentId) {
        QueryWrapper<MeTypeLibrary> wrapper = new QueryWrapper<>();
        wrapper.eq("Met_Parent_Id", parentId);
        Integer count = baseMapper.selectCount(wrapper).intValue();
        return count;
    }
}
