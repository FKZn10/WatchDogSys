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
import com.jpmsoft.fileUpload.mapper.MeTypeLibraryMapper;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.MeTypeLibraryDTO;
import com.jpmsoft.fileUpload.pojo.entity.MeTypeLibrary;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.fileUpload.service.IMeTypeLibraryService;
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
 * @date: 2023/5/22 15:32
 */
@Service
public class MeTypeLibraryImpl extends ServiceImpl<MeTypeLibraryMapper, MeTypeLibrary> implements IMeTypeLibraryService {

    @Value("${file.upload.dirPath}")
    private String dirPath;

    @Resource
    private ISysConfigService sysConfigService;


    @Override
    public void exportMeTypeLibrary(HttpServletResponse response) {
        String root = sysConfigService.getRootDirectory();
        if (ValidateUtil.isEmpty(root))
            throw new JPMException(String.format(FileMsg.notFoundSysConfig, "rootDirectory"));
        QueryWrapper<MeTypeLibrary> wrapper = new QueryWrapper<>();
        List<MeTypeLibrary> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<MeTypeLibraryDTO> models = data.stream().map(d -> {
                MeTypeLibraryDTO model = new MeTypeLibraryDTO();
                BeanUtils.copyProperties(d, model);
                if (ValidateUtil.isNotEmpty(model.getMetParentId()) && !model.getMetParentId().equals("0")) {
                    List<MeTypeLibrary> parents = data.stream().filter(n -> n.getMetId().equals(model.getMetParentId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(parents))
                        model.setMetParentId(parents.stream().findFirst().get().getMetCode());
                }
                return model;
            }).collect(Collectors.toList());
            String filePath = FileUtil.temporaryPath(root + "/" + dirPath, "xlsx");
            ExcelUtil.export2File(models, MeTypeLibraryDTO.class, filePath, "材料设备品种库");
            //endregion
            //region 下载文件后删除临时文件
            FileUtil.downFileAndDelFile(response, filePath, "材料设备品种库.xlsx");
        } else throw new JPMException(FileMsg.notData2Export);
    }

    @Override
    public BaseResponse importMeTypeLibrary(MultipartFile file, LogUserInfoVo user) {
        List<MeTypeLibraryDTO> data = new EasyExcelData<MeTypeLibraryDTO>().getExcelData(file, MeTypeLibraryDTO.class, 1);
        if (!CollectionUtils.isEmpty(data)) {
            QueryWrapper<MeTypeLibrary> wrapper = new QueryWrapper<>();
            if (baseMapper.exists(wrapper)) return BaseResponse.fail(FileMsg.dataExistsUnImport);
            Map<String, String> codeId = new HashMap<>();
            Date now = new Date();
            List<MeTypeLibrary> models = data.stream().map(d -> {
                MeTypeLibrary model = new MeTypeLibrary();
                BeanUtils.copyProperties(d, model);
                model.setMetCreateTime(now);
                model.setMetId(GenerateUUID.getUUID());
                model.setMetCreateUid(user.getId());
                model.setMetCreateUname(user.getName());
                if (ValidateUtil.isNotEmpty(model.getMetCode()))
                    codeId.put(model.getMetCode(), model.getMetId());
                return model;
            }).collect(Collectors.toList());
            List<MeTypeLibrary> entities = models.stream().map(model -> {
                if (ValidateUtil.isEmpty(model.getMetParentId()))
                    model.setMetParentId("0");
                else {
                    if (codeId.containsKey(model.getMetParentId()))
                        model.setMetParentId(codeId.get(model.getMetParentId()));
                }
                return model;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        } else return BaseResponse.fail(FileMsg.excelNotData);
        return BaseResponse.ok();
    }
}
