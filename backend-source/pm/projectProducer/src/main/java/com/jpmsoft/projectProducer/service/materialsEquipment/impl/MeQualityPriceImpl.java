package com.jpmsoft.projectProducer.service.materialsEquipment.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.materialsEquipment.MeQualityPriceMapper;
import com.jpmsoft.projectProducer.pojo.dto.materialsEquipment.MeQualityPriceDto;
import com.jpmsoft.projectProducer.pojo.entity.materialsEquipment.MeQualityPrice;
import com.jpmsoft.projectProducer.pojo.vo.materialsEquipment.MeQualityPriceVo;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeQualityPriceDtlService;
import com.jpmsoft.projectProducer.service.materialsEquipment.IMeQualityPriceService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/1/3 13:51
 */
@Service
public class MeQualityPriceImpl extends FileImpl<MeQualityPriceMapper, MeQualityPrice, MeQualityPriceDto> implements IMeQualityPriceService {

    @Resource
    private IMeQualityPriceDtlService meQualityPriceDtlService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    public BaseResponse createOrUpdateMeQualityPrice(MeQualityPriceDto request) {
        MeQualityPrice entity = new MeQualityPrice();
        createOrUpdateFileEntity(entity, request);
        meQualityPriceDtlService.createOrUpdateMeQualityPriceDtl(request.getMeQualityPriceDtls(), entity.getQpId());
        return BaseResponse.ok(entity.getQpId());
    }

    @Override
    public BaseResponse getMeQualityPriceList(MeQualityPriceDto request, String uid) {
        String authSql = sysUserService.getAuthSql("Me_Quality_Price", uid, "Qp_Create_Uid", OutUserAuthEnum.Unit_Auth.getIndex(), "Qp_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<MeQualityPriceDto> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("qpFirstPartyRname").put("qpInitiatorName").put("pId")
                .put("qpTitle").put("qpState").put("qpCreateUname").put("pName").put("conName");
        IPage<MeQualityPriceVo> page = new Page<>(request.getPage(), request.getSize());
        List<MeQualityPriceVo> datas = baseMapper.getMeQualityPriceList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getMeQualityPriceById(String id) {
        return BaseResponse.ok(baseMapper.getMeQualityPriceById(id));
    }
}
