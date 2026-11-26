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
import com.jpmsoft.fileUpload.mapper.ProjectCpLibraryMapper;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.ProjectCpLibraryDTO;
import com.jpmsoft.fileUpload.pojo.entity.ProjectCpLibrary;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.fileUpload.service.IProjectCpLibraryService;
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
 * @date: 2023/5/22 14:41
 */
@Service
public class ProjectCpLibraryImpl extends ServiceImpl<ProjectCpLibraryMapper, ProjectCpLibrary> implements IProjectCpLibraryService {
    @Value("${file.upload.dirPath}")
    private String dirPath;

    @Resource
    private ISysConfigService sysConfigService;

    @Override
    public void exportProjectCpLibrary(HttpServletResponse response) {
        String root = sysConfigService.getRootDirectory();
        if (ValidateUtil.isEmpty(root))
            throw new JPMException(String.format(FileMsg.notFoundSysConfig, "rootDirectory"));
        QueryWrapper<ProjectCpLibrary> wrapper = new QueryWrapper<>();
        List<ProjectCpLibrary> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<ProjectCpLibraryDTO> models = data.stream().map(d -> {
                ProjectCpLibraryDTO model = new ProjectCpLibraryDTO();
                BeanUtils.copyProperties(d, model);
                if (ValidateUtil.isNotEmpty(model.getCplParentId()) && !model.getCplParentId().equals("0")) {
                    List<ProjectCpLibrary> parents = data.stream().filter(n -> n.getCplId().equals(model.getCplParentId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(parents))
                        model.setCplParentId(parents.stream().findFirst().get().getCplCode());
                }
                if (ValidateUtil.isNotEmpty(model.getCplProId()) && !model.getCplProId().equals("0")) {
                    List<ProjectCpLibrary> parents = data.stream().filter(n -> n.getCplId().equals(model.getCplProId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(parents))
                        model.setCplProId(parents.stream().findFirst().get().getCplCode());
                }
                return model;
            }).collect(Collectors.toList());
            String filePath = FileUtil.temporaryPath(root + "/" + dirPath, "xlsx");
            ExcelUtil.export2File(models, ProjectCpLibraryDTO.class, filePath, "项目建设程序库");
            //endregion
            //region 下载文件后删除临时文件
            FileUtil.downFileAndDelFile(response, filePath, "项目建设程序库.xlsx");
        } else throw new JPMException(FileMsg.notData2Export);
    }

    @Override
    public BaseResponse importProjectCpLibrary(MultipartFile file, LogUserInfoVo user) {
        List<ProjectCpLibraryDTO> data = new EasyExcelData<ProjectCpLibraryDTO>().getExcelData(file, ProjectCpLibraryDTO.class, 1);
        if (!CollectionUtils.isEmpty(data)) {
            QueryWrapper<ProjectCpLibrary> wrapper = new QueryWrapper<>();
            if (baseMapper.exists(wrapper)) return BaseResponse.fail(FileMsg.dataExistsUnImport);
            Map<String, String> codeId = new HashMap<>();
            Date now = new Date();
            List<ProjectCpLibrary> models = data.stream().map(d -> {
                ProjectCpLibrary model = new ProjectCpLibrary();
                BeanUtils.copyProperties(d, model);
                model.setCplCreateTime(now);
                model.setCplId(GenerateUUID.getUUID());
                model.setCplCreateUid(user.getId());
                model.setCplCreateUname(user.getName());
                if (ValidateUtil.isNotEmpty(model.getCplCode()))
                    codeId.put(model.getCplCode(), model.getCplId());
                return model;
            }).collect(Collectors.toList());
            List<ProjectCpLibrary> entities = models.stream().map(model -> {
                if (ValidateUtil.isEmpty(model.getCplParentId()))
                    model.setCplParentId("0");
                else {
                    if (codeId.containsKey(model.getCplParentId()))
                        model.setCplParentId(codeId.get(model.getCplParentId()));
                }
                if (ValidateUtil.isNotEmpty(model.getCplProId()) && codeId.containsKey(model.getCplProId()))
                    model.setCplProId(codeId.get(model.getCplProId()));
                return model;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        } else return BaseResponse.fail(FileMsg.excelNotData);
        return BaseResponse.ok();
    }


}
