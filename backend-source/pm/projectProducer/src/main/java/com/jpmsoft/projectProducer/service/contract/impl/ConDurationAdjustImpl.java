package com.jpmsoft.projectProducer.service.contract.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.contract.ConDurationAdjustMapper;
import com.jpmsoft.projectProducer.pojo.dto.contract.ConDurationAdjustDto;
import com.jpmsoft.projectProducer.pojo.entity.contract.ConDurationAdjust;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConDurationAdjustStatisticsVo;
import com.jpmsoft.projectProducer.pojo.vo.contract.ConDurationAdjustVo;
import com.jpmsoft.projectProducer.service.contract.IConDurationAdjustService;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author 李文涛 aaron
 * @date 2022/11/11 10:29
 */
@Service
public class ConDurationAdjustImpl extends BaseFileImpl<ConDurationAdjustMapper, ConDurationAdjust, ConDurationAdjustDto>
        implements IConDurationAdjustService {
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private ISysUserService sysUserService;

    @Override
    public BaseResponse createOrUpdateConDurationAdjust(ConDurationAdjustDto request) {
        ConDurationAdjust entity = new ConDurationAdjust();
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId()))
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        else
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getConDurationAdjustList(ConDurationAdjustDto request, String uId) {
        String authSql = sysUserService.getAuthSql(null, uId, "Create_Uid", OutUserAuthEnum.Organization.getIndex(), "State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<ConDurationAdjustDto> query = new QueryMap<>(request);
        query.put("authSql", authSql).put("pId").put("conId").put("pName").put("conName").put("daType").put("daReason")
                .put("state").put("createUname").put("conPartyAName").put("conPartyBName").put("daSupervisorName").put("conBName");
        IPage<ConDurationAdjustVo> page = new Page<>(request.getPage(), request.getSize());
        List<ConDurationAdjustVo> datas = baseMapper.getConDurationAdjustList(page, query.getQuery());
        BigDecimal conMoneyTotalSum = baseMapper.getConDurationAdjustConMoneySum(query.getQuery());
        ConDurationAdjustStatisticsVo data = new ConDurationAdjustStatisticsVo();
        data.setData(datas);
        data.setConMoneyTotalSum(conMoneyTotalSum);
        return BaseResponse.ok(data, Long.valueOf(page.getTotal()).intValue());
    }

    @Override
    public BaseResponse getConDurationAdjustById(String id) {
        ConDurationAdjustVo data = baseMapper.getConDurationAdjustById(id);
        return BaseResponse.ok(data);
    }
}
