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
import com.jpmsoft.fileUpload.mapper.ArchiveDirectoryMapper;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.ArchiveDirectoryDTO;
import com.jpmsoft.fileUpload.pojo.entity.ArchiveDirectory;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.fileUpload.service.IArchiveDirectoryService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.utils.ExcelUtil;
import com.jpmsoft.fileUpload.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/5/22 15:51
 */
@Service
public class ArchiveDirectoryImpl extends ServiceImpl<ArchiveDirectoryMapper, ArchiveDirectory> implements IArchiveDirectoryService {


    @Value("${file.upload.dirPath}")
    private String dirPath;

    @Resource
    private ISysConfigService sysConfigService;

    @Override
    public void exportArchiveDirectory(HttpServletResponse response) {
        String root = sysConfigService.getRootDirectory();
        if (ValidateUtil.isEmpty(root))
            throw new JPMException(String.format(FileMsg.notFoundSysConfig, "rootDirectory"));
        QueryWrapper<ArchiveDirectory> wrapper = new QueryWrapper<>();
        List<ArchiveDirectory> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) {
            List<ArchiveDirectoryDTO> models = data.stream().map(d -> {
                ArchiveDirectoryDTO model = new ArchiveDirectoryDTO();
                BeanUtils.copyProperties(d, model);
                if (ValidateUtil.isNotEmpty(model.getAdParentId()) && !model.getAdParentId().equals("0")) {
                    List<ArchiveDirectory> parents = data.stream().filter(n -> n.getAdId().equals(model.getAdParentId())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(parents))
                        model.setAdParentId(parents.stream().findFirst().get().getAdCode());
                }
                return model;
            }).collect(Collectors.toList());
            String filePath = FileUtil.temporaryPath(root + "/" + dirPath, "xlsx");
            ExcelUtil.export2File(models, ArchiveDirectoryDTO.class, filePath, "质量安全类别");
            //endregion
            //region 下载文件后删除临时文件
            FileUtil.downFileAndDelFile(response, filePath, "质量安全类别.xlsx");
        } else throw new JPMException(FileMsg.notData2Export);
    }

    @Override
    public BaseResponse importArchiveDirectory(MultipartFile file, LogUserInfoVo user) {
        List<ArchiveDirectoryDTO> data = new EasyExcelData<ArchiveDirectoryDTO>().getExcelData(file, ArchiveDirectoryDTO.class, 1);
        if (!CollectionUtils.isEmpty(data)) {
            QueryWrapper<ArchiveDirectory> wrapper = new QueryWrapper<>();
            if (baseMapper.exists(wrapper)) return BaseResponse.fail(FileMsg.dataExistsUnImport);
            Map<String, String> codeId = new HashMap<>();
            Date now = new Date();
            List<ArchiveDirectory> models = data.stream().map(d -> {
                ArchiveDirectory model = new ArchiveDirectory();
                BeanUtils.copyProperties(d, model);
                model.setAdCreateTime(now);
                model.setAdId(GenerateUUID.getUUID());
                model.setAdCreateUid(user.getId());
                model.setAdCreateUname(user.getName());
                if (ValidateUtil.isNotEmpty(model.getAdCode()))
                    codeId.put(model.getAdCode(), model.getAdId());
                return model;
            }).collect(Collectors.toList());
            List<ArchiveDirectory> entities = models.stream().map(model -> {
                if (ValidateUtil.isEmpty(model.getAdParentId()))
                    model.setAdParentId("0");
                else {
                    if (codeId.containsKey(model.getAdParentId()))
                        model.setAdParentId(codeId.get(model.getAdParentId()));
                }
                return model;
            }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(entities)) saveBatch(entities);
        } else return BaseResponse.fail(FileMsg.excelNotData);
        return BaseResponse.ok();
    }

    @Override
    public List<ArchiveDirectory> getDirByCodes(List<String> codes) {
        if (CollectionUtils.isEmpty(codes)) return null;
        QueryWrapper<ArchiveDirectory> wrapper=new QueryWrapper<>();
        wrapper.in("Ad_Code",codes);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<ArchiveDirectory> getDirByIds(List<String> Ids) {
        if (CollectionUtils.isEmpty(Ids)) return null;
        QueryWrapper<ArchiveDirectory> wrapper=new QueryWrapper<>();
        wrapper.in("ad_id",Ids);
        return baseMapper.selectList(wrapper);
    }
}
