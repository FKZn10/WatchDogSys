package com.jpmsoft.projectProducer.service.osm.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.requestAndResponse.osm.OsmBreachPenaltyDto;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.osm.OsmBreachPenaltyMapper;
import com.jpmsoft.projectProducer.pojo.entity.osm.OsmBreachPenalty;
import com.jpmsoft.projectProducer.service.osm.IOsmBreachPenaltyService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * 违约处罚(OsmBreachPenalty)表服务实现类
 *
 * @author c
 * @since 2024-12-05 11:54:59
 */
@Service
public class OsmBreachPenaltyServiceImpl extends ServiceImpl<OsmBreachPenaltyMapper, OsmBreachPenalty> implements IOsmBreachPenaltyService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public BaseResponse getBreachPenaltyList(OsmBreachPenaltyDto request) {
        QueryMap<OsmBreachPenaltyDto> params = new QueryMap<>(request);
        params.put("pName").put("pId").put("punishItemName").put("itemType").put("conName").put("unitName")
                .put("usingSeals").put("handlerUserName").put("state").put("createUname").put("conId");
        IPage<OsmBreachPenaltyDto> page = new Page<>(request.getPage(), request.getSize());
        List<OsmBreachPenaltyDto> list = baseMapper.getBreachPenaltyList(params.getQuery(),page);
        return BaseResponse.ok(list, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse updateosmBreachPenalty(OsmBreachPenaltyDto request) {
        if (ValidateUtil.isEmpty(request.getId())) return BaseResponse.fail(ResMsg.notReqParam);
        OsmBreachPenalty op = baseMapper.selectById(request.getId());
        if (op == null) return BaseResponse.fail(ResMsg.infoNotExist);
        op.setObpClms(request.getObpClms());
        op.setHandlerFile(request.getHandlerFile());
        op.setHandlerType(request.getHandlerType());
        op.setActualPaymentAmount(request.getActualPaymentAmount());
        op.setHandlerTime(request.getHandlerTime());
        baseMapper.updateById(op);
        sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getHandlerFile()), Arrays.asList(request.getHandlerFileIds()), op.getId());
        return BaseResponse.ok(op.getId());
    }



}
