package com.jpmsoft.projectProducer.service.other.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.zxing.WriterException;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.other.ReportCdkeyMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.ReportCdkey;
import com.jpmsoft.projectProducer.service.other.IReportCdkeyService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.utils.QRCodeGenerator;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/1/2 10:54
 */
@Service
public class ReportCdkeyImpl extends ServiceImpl<ReportCdkeyMapper, ReportCdkey> implements IReportCdkeyService {

    @Resource
    private ISysConfigService sysConfigService;

    @Override
    public void qrCode(String reportId, HttpServletRequest request, HttpServletResponse response) throws IOException, WriterException {
        QueryWrapper<ReportCdkey> wrapper = new QueryWrapper<>();
        wrapper.eq("report_id", reportId);
        List<ReportCdkey> data = baseMapper.selectList(wrapper);
        String rcKey = GenerateUUID.getUUID();
        if (!CollectionUtils.isEmpty(data)) {
            rcKey = data.stream().findFirst().get().getRcKey();
        } else {
            ReportCdkey entity = new ReportCdkey();
            entity.setRcKey(rcKey);
            entity.setId(GenerateUUID.getUUID());
            entity.setReportId(reportId);
            baseMapper.insert(entity);
        }
        response.setContentType("application/octet-stream");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=" + rcKey + ".png");
        response.getOutputStream().write(QRCodeGenerator.generateQRCodeBytes(rcKey));
        response.getOutputStream().flush();
        response.getOutputStream().close();
    }

    @Override
    public BaseResponse checkPhone(String phone) {
        Integer count = baseMapper.checkPhone(phone);
        if (count == null || count < 1) return BaseResponse.ok(true);
        return BaseResponse.ok(false);
    }

    @Override
    public BaseResponse checkStudentNum(String studentNum) {
        Integer count = baseMapper.checkStudentNum(studentNum);
        if (count == null || count < 1) return BaseResponse.ok(true);
        return BaseResponse.ok(false);
    }

    @Override
    public BaseResponse outMax() {
        Integer size = baseMapper.size();
        String num = sysConfigService.getConfigByCode("questionnaireMaxSize");
        int max = 400;
        try {
            if (ValidateUtil.isNotEmpty(num)) max = Integer.parseInt(num);
        } catch (Exception ex) {
            log.error("基础配置[questionnaireMaxSize]格式错误!");
            max = 400;
        }
        return BaseResponse.ok(size >= max);
    }

}
