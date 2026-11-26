package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecSolicitationNoticeMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecSolicitationNoticeDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecSolicitationNotice;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecSolicitationNoticeVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.secInfo.ISecSolicitationNoticeService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author: 李文涛 aaron
 * @date: 2022/10/25 15:31
 */
@Service
public class SecSolicitationNoticeImpl
        extends BaseFileImpl<SecSolicitationNoticeMapper, SecSolicitationNotice, SecSolicitationNoticeDto>
        implements ISecSolicitationNoticeService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public BaseResponse createOrUpdateSecSolicitationNotice(SecSolicitationNoticeDto request) {
        SecSolicitationNotice entity = new SecSolicitationNotice();
        entity = createOrUpdateEntity(entity, request);
        if (ValidateUtil.isEmpty(request.getId())) {
            sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        } else {
            sysFileAnnexService.refreshRelationFile(request.getFileRelationId(), request.getFileIds(), entity.getId());
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSecSolicitationNoticeList(SecSolicitationNoticeDto request) {
        QueryMap<SecSolicitationNoticeDto> query = new QueryMap<>(request);
        query.put("secName").put("snQuotationMethod").put("snSettlementMethod").put("snCalibration")
                .put("createUname").put("state").put("secId").put("snAgencyCpy")
                .put("snProcureCpyName").put("snProcurePerson").put("snProcurePhone");
        IPage<SecSolicitationNoticeVo> page = new Page<>(request.getPage(), request.getSize());
        List<SecSolicitationNoticeVo> datas = baseMapper.getSecSolicitationNoticeList(page, query.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSecSolicitationNoticeById(String id) {
        return BaseResponse.ok(baseMapper.getSecSolicitationNoticeById(id));
    }

    @Override
    public BaseResponse getSecSolicitationNoticeLastApprovedBySecId(String secId) {
        return BaseResponse.ok(baseMapper.getSecSolicitationNoticeLastApprovedBySecId(secId));
    }
}
