package com.jpmsoft.projectProducer.service.secInfo.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.annotations.CodeGeneration;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.secInfo.CalibrationReportMapper;
import com.jpmsoft.projectProducer.pojo.dto.secInfo.CalibrationReportDto;
import com.jpmsoft.projectProducer.pojo.entity.secInfo.CalibrationReport;
import com.jpmsoft.projectProducer.pojo.vo.secInfo.CalibrationReportVo;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.secInfo.ICalibrationReportService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


@Service
public class CalibrationReportServiceImpl extends ServiceImpl<CalibrationReportMapper, CalibrationReport>
        implements ICalibrationReportService {

    @Resource
    private ISysFileService sysFileService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Override
    public BaseResponse createOrUpdateCalibrationReport(CalibrationReportDto request) {
        if (ValidateUtil.isNotEmpty(request.getId()))
            return updateCalibrationReport(request);
        else
            return createCalibrationReport(request);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    @CodeGeneration(key = "id", codeParam = "crCode", method = "setCrCode", codeRule = "CalibrationReport", beanClass = CalibrationReportDto.class)
    public BaseResponse createCalibrationReport(CalibrationReportDto request) {
        CalibrationReport entity = new CalibrationReport();
        BeanUtils.copyProperties(request, entity);
        entity.setId(GenerateUUID.getUUID());
        entity.setCreateTime(new Date());
        baseMapper.insert(entity);
        sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                request.getFileRelationId(), request.getSecId(), request.gettCode(), null);
//        sysFileAnnexService.addFormRefreshRelationFile(request.getFileRelationId(), entity.getId());
        sysFileAnnexService.addFormRefreshRelationFile(Arrays.asList(request.getFileRelationId(),
                request.getCrClarificationFile(),
                request.getCrProfileOne(),
                request.getCrProfileTwo(),
                request.getCrProfileThree()), entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse updateCalibrationReport(CalibrationReportDto request) {
        CalibrationReport entity = baseMapper.selectById(request.getId());
        if (ValidateUtil.isNull(entity)) return BaseResponse.fail(ResMsg.infoNotExist);
        BeanUtils.copyProperties(request, entity);
        baseMapper.updateById(entity);
        sysFileService.saveFile(entity.getId(), request.gettId(), entity.getState() + "", entity.getCreateUid(), entity.getCreateUname()
                , entity.getCreateTime(), request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                request.getFileRelationId(), entity.getSecId(), request.gettCode(), null);
        List<String> id = new ArrayList<String>() {{
            add(request.getFileRelationId());
            add(request.getCrClarificationFile());
            add(request.getCrProfileOne());
            add(request.getCrProfileTwo());
            add(request.getCrProfileThree());
        }};

        List<String> ids = new ArrayList<String>() {{
            add(request.getFileIds());
            add(request.getCrClarificationFileIds());
            add(request.getCrProfileIdsOne());
            add(request.getCrProfileIdsTwo());
            add(request.getCrProfileIdsThree());
        }};

        sysFileAnnexService.refreshRelationFile(id,ids, entity.getId());
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getCalibrationReportList(CalibrationReportDto request) {
        QueryMap<CalibrationReportDto> params = new QueryMap<>(request);
        params.put("crTitle").put("crOriginator").put("state").put("pName").put("crCode")
                .put("crBidWinner").put("crPricingMethod").put("crOtherValuation").put("pId").put("crBacmApprove");
        IPage<CalibrationReportVo> page = new Page<>(request.getPage(), request.getSize());
        List<CalibrationReportVo> datas = baseMapper.getCalibrationReportList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getCalibrationReportById(String id) {
        CalibrationReportVo data = baseMapper.getCalibrationReportById(id);
        return BaseResponse.ok(data);
    }

    @Override
    public BaseResponse getCalibrationReportBySecId(String secId) {
        CalibrationReportVo data = baseMapper.getCalibrationReportBySecId(secId);
        return BaseResponse.ok(data);
    }
}




