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
import com.jpmsoft.fileUpload.mapper.QsTypeMapper;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.QsTypeDTO;
import com.jpmsoft.fileUpload.pojo.entity.QsType;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.fileUpload.service.IQsTypeService;
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
 * @date: 2023/5/22 15:41
 */
@Service
public class QsTypeImpl extends ServiceImpl<QsTypeMapper, QsType> implements IQsTypeService {

    @Value("${file.upload.dirPath}")
    private String dirPath;

    @Resource
    private ISysConfigService sysConfigService;

    @Override
    public void exportQsType(HttpServletResponse response) {
        String root = sysConfigService.getRootDirectory();
        if (ValidateUtil.isEmpty(root))
            throw new JPMException(String.format(FileMsg.notFoundSysConfig, "rootDirectory"));
        QueryWrapper<QsType> wrapper = new QueryWrapper<>();
        List<QsType> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<QsTypeDTO> models = data.stream().map(d -> {
                QsTypeDTO model = new QsTypeDTO();
                BeanUtils.copyProperties(d, model);
                if (ValidateUtil.isNotEmpty(model.getQstParentId()) && !model.getQstParentId().equals("0")) {
                    List<QsType> parents = data.stream().filter(n -> n.getQstId().equals(model.getQstParentId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(parents))
                        model.setQstParentId(parents.stream().findFirst().get().getQstCode());
                }
                return model;
            }).collect(Collectors.toList());
            String filePath = FileUtil.temporaryPath(root + "/" + dirPath, "xlsx");
            ExcelUtil.export2File(models, QsTypeDTO.class, filePath, "质量安全类别");
            //endregion
            //region 下载文件后删除临时文件
            FileUtil.downFileAndDelFile(response, filePath, "质量安全类别.xlsx");
        } else throw new JPMException(FileMsg.notData2Export);
    }

    @Override
    public BaseResponse importQsType(MultipartFile file, LogUserInfoVo user) {
        List<QsTypeDTO> data = new EasyExcelData<QsTypeDTO>().getExcelData(file, QsTypeDTO.class, 1);
        if (!CollectionUtils.isEmpty(data)) {
            QueryWrapper<QsType> wrapper = new QueryWrapper<>();
            if (baseMapper.exists(wrapper)) return BaseResponse.fail(FileMsg.dataExistsUnImport);
            Map<String, String> codeId = new HashMap<>();
            Date now = new Date();
            List<QsType> models = data.stream().map(d -> {
                QsType model = new QsType();
                BeanUtils.copyProperties(d, model);
                model.setQstCreateTime(now);
                model.setQstId(GenerateUUID.getUUID());
                model.setQstCreateUid(user.getId());
                model.setQstCreateUname(user.getName());
                if (ValidateUtil.isNotEmpty(model.getQstCode()))
                    codeId.put(model.getQstCode(), model.getQstId());
                return model;
            }).collect(Collectors.toList());
            List<QsType> entities = models.stream().map(model -> {
                if (ValidateUtil.isEmpty(model.getQstParentId()))
                    model.setQstParentId("0");
                else {
                    if (codeId.containsKey(model.getQstParentId()))
                        model.setQstParentId(codeId.get(model.getQstParentId()));
                }
                return model;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        } else return BaseResponse.fail(FileMsg.excelNotData);
        return BaseResponse.ok();
    }
}
