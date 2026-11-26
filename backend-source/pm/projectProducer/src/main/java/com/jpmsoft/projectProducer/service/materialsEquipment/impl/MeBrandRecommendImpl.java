package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeBrandRecommendMapper;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeBrandRecommendDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeBrandRecommend;
import com.jpmsoft.projectProducer.pojo.vo.materialsEquipment.MeBrandRecommendVo;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeBrandRecommendDtlService;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeBrandRecommendService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 9:46
 */
@Service
public class MeBrandRecommendImpl extends FileImpl<MeBrandRecommendMapper, MeBrandRecommend, MeBrandRecommendDto>
        implements IMeBrandRecommendService {
    @Resource
    private IMeBrandRecommendDtlService meBrandRecommendDtlService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "brId", codeParam = "brCode", method = "setBrCode", codeRule = "MeBrandRecommend", beanClass = MeBrandRecommendDto.class)
    public BaseResponse createOrUpdateMeBrandRecommend(MeBrandRecommendDto request) {
        MeBrandRecommend entity = new MeBrandRecommend();
        createOrUpdateFileEntity(entity, request);
        meBrandRecommendDtlService.createOrUpdateMeBrandRecommendDtl(request.getMeBrandRecommendDtls(), entity.getBrId());
        return BaseResponse.ok(entity.getBrId());
    }

    @Override
    public BaseResponse getMeBrandRecommendList(MeBrandRecommendDto request, String uId) {
        String authSql = sysUserService.getAuthSql("Me_Brand_Recommend", uId, "Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<MeBrandRecommendDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("brCode").put("brTitle").put("state")
                .put("createUname").put("pName").put("secName").put("pId");
        IPage<MeBrandRecommendVo> page = new Page<>(request.getPage(), request.getSize());
        List<MeBrandRecommendVo> datas = baseMapper.getMeBrandRecommendList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMeBrandRecommendById(String id) {
        return BaseResponse.ok(baseMapper.getMeBrandRecommendById(id));
    }
}
