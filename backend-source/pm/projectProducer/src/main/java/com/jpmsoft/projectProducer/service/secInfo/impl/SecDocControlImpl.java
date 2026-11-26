package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.secInfo.SecDocControlMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.SecDocControlDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.SecDocControl;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.SecDocControlVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.secInfo.ISecDocControlService;
import com.jpmsoft.projectProducer.utils.extension.BaseFileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author: 李文涛 aaron
 * @date: 2022/8/4 11:17
 */
@Service
public class SecDocControlImpl extends BaseFileImpl<SecDocControlMapper, SecDocControl, SecDocControlDto>
        implements ISecDocControlService {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateSecDocControl(SecDocControlDto request) {
        SecDocControl sdc = new SecDocControl();
        sdc = createOrUpdateEntity(sdc, request);
        if (ValidateUtil.isEmpty(request.getId())) {
            sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getDcBoqDoc(), request.getDcConsultUnitDoc(), request.getDcFilingReceipt()), sdc.getId());
        } else {
            sysFileAnnexService.refreshRelationFile(Arrays.asList(request.getFileRelationId(), request.getDcBoqDoc(), request.getDcConsultUnitDoc(), request.getDcFilingReceipt()),
                    Arrays.asList(request.getFileIds(), request.getDcBoqDocIds(), request.getDcConsultUnitDocIds(), request.getDcFilingReceiptIds()), sdc.getId());
        }
        return BaseResponse.ok(sdc.getId());
    }

    @Override
    public BaseResponse getSecDocControlList(SecDocControlDto request) {
        Map<String, Object> params = new HashMap<>();
        params.put("sortFiled", ValidateUtil.isEmpty(request.getSortFiled()) ? "Create_Time" : request.getSortFiled());
        params.put("sortType", ValidateUtil.isEmpty(request.getSortType()) ? "DESC" : request.getSortType());
        params.put("start", ValidateUtil.isNotNull(request.getPageStart()) ? request.getPageStart() : 0);
        params.put("size", ValidateUtil.isNotNull(request.getSize()) ? request.getSize() == 0 ? 10 : request.getSize() : 10);

        params.put("dcDeterminType", ValidateUtil.isEmpty(request.getDcDeterminType()) ? null : request.getDcDeterminType());
        params.put("dcPriceMode", ValidateUtil.isEmpty(request.getDcPriceMode()) ? null : request.getDcPriceMode());
        params.put("state", ValidateUtil.isEmpty(request.getState()) ? null : request.getState());
        params.put("secName", ValidateUtil.isEmpty(request.getSecName()) ? null : request.getSecName());
        params.put("createUname", ValidateUtil.isEmpty(request.getCreateUname()) ? null : request.getCreateUname());
        params.put("dcPerformanceGuaranteeType", ValidateUtil.isEmpty(request.getDcPerformanceGuaranteeType()) ? null : request.getDcPerformanceGuaranteeType());
        params.put("teId", ValidateUtil.isEmpty(request.getTeId()) ? null : request.getTeId());
        List<SecDocControlVo> datas = baseMapper.getSecDocControlList(params);
        Integer total = baseMapper.getSecDocControlListCount(params);
        return BaseResponse.ok(datas, total);
    }

    @Override
    public BaseResponse getSecDocControlById(String id) {
        SecDocControlVo data = baseMapper.getSecDocControlById(id);
        return BaseResponse.ok(data);
    }
}
