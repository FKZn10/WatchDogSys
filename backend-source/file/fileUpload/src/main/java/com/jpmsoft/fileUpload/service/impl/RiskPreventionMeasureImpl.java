package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.easyexcel.EasyExcelData;
import com.jpmsoft.fileUpload.mapper.RiskPreventionMeasureMapperr;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.RiskPreventionMeasureDTO;
import com.jpmsoft.fileUpload.pojo.entity.RiskPreventionMeasure;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.fileUpload.service.IRiskPreventionMeasureService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.utils.ExcelUtil;
import com.jpmsoft.fileUpload.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/24 8:52
 */
@Service
public class RiskPreventionMeasureImpl extends ServiceImpl<RiskPreventionMeasureMapperr, RiskPreventionMeasure> implements IRiskPreventionMeasureService {

    @Value("${file.upload.dirPath}")
    private String dirPath;

    @Resource
    private ISysConfigService sysConfigService;


    @Override
    public void exportRiskPreventionMeasure(HttpServletResponse response) {
        String root = sysConfigService.getRootDirectory();
        if (ValidateUtil.isEmpty(root))
            throw new JPMException(String.format(FileMsg.notFoundSysConfig, "rootDirectory"));
        QueryWrapper<RiskPreventionMeasure> wrapper = new QueryWrapper<>();
        List<RiskPreventionMeasure> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<RiskPreventionMeasureDTO> models = data.stream().map(d -> {
                RiskPreventionMeasureDTO model = new RiskPreventionMeasureDTO();
                BeanUtils.copyProperties(d, model);
                model.setTemporaryCode(d.getId());
                return model;
            }).collect(Collectors.toList());
            String filePath = FileUtil.temporaryPath(root + "/" + dirPath, "xlsx");
            ExcelUtil.export2File(models, RiskPreventionMeasureDTO.class, filePath, "质量安全类别");
            //endregion
            //region 下载文件后删除临时文件
            FileUtil.downFileAndDelFile(response, filePath, "质量安全类别.xlsx");
        } else throw new JPMException(FileMsg.notData2Export);
    }

    @Override
    public BaseResponse importRiskPreventionMeasure(MultipartFile file, LogUserInfoVo user) {
        List<RiskPreventionMeasureDTO> data = new EasyExcelData<RiskPreventionMeasureDTO>().getExcelData(file, RiskPreventionMeasureDTO.class, 1);
        if (!CollectionUtils.isEmpty(data)) {
            QueryWrapper<RiskPreventionMeasure> wrapper = new QueryWrapper<>();
            if (baseMapper.exists(wrapper)) return BaseResponse.fail(FileMsg.dataExistsUnImport);
            Map<String, String> codeId = new HashMap<>();
            Date now = new Date();
            List<RiskPreventionMeasure> models = data.stream().map(d -> {
                RiskPreventionMeasure model = new RiskPreventionMeasure();
                BeanUtils.copyProperties(d, model);
                model.setCreateTime(now);
                model.setId(GenerateUUID.getUUID());
                model.setCreateUid(user.getId());
                model.setCreateUname(user.getName());
                if (ValidateUtil.isNotEmpty(d.getTemporaryCode()))
                    codeId.put(d.getTemporaryCode(), model.getId());
                return model;
            }).collect(Collectors.toList());
            List<RiskPreventionMeasure> entities = models.stream().map(model -> {
                if (ValidateUtil.isEmpty(model.getRpmParentId()))
                    model.setRpmParentId("0");
                else {
                    if (codeId.containsKey(model.getRpmParentId()))
                        model.setRpmParentId(codeId.get(model.getRpmParentId()));
                }
                return model;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        } else return BaseResponse.fail(FileMsg.excelNotData);
        return BaseResponse.ok();
    }
}
