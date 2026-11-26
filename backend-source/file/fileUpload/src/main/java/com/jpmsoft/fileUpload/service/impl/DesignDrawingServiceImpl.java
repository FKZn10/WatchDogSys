package com.jpmsoft.fileUpload.service.impl;

import com.jpmsoft.common.model.requestAndResponse.Design.DesignDrawingModel;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.feign.PmFeign;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.service.IDesignDrawingService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.service.ISysFileAnnexService;
import com.jpmsoft.fileUpload.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DesignDrawingServiceImpl implements IDesignDrawingService {
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;
    @Resource
    private PmFeign pmFeign;

    @Override
    public void exportDesignDrawing(HttpServletResponse response, DesignDrawingModel request, String token) {
        try {
            String root = sysConfigService.getRootDirectory();
            String archive = sysConfigService.getArchiveDirectory();

            // 获取图纸数据树结构
            List<DesignDrawingModel> treeData = pmFeign.getDesignDrawingExportRW(request, token);
            if (ValidateUtil.isEmpty(treeData)) {
                log.error("获取图纸数据树结构失败或不存在符合条件的图纸数据");
                return;
            }

            // 批量收集所有文件关联ID
            Set<String> allRelationIds = treeData.stream()
                    .flatMap(item -> item.getChildDrawing().stream())
                    .map(DesignDrawingModel::getDdFileRelationId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toSet());

            // 一次性查询所有文件
            List<SysFileAnnex> allFiles = sysFileAnnexService.getFileByRelationIds(new ArrayList<>(allRelationIds));
            Map<String, List<SysFileAnnex>> relationFileMap = allFiles.stream()
                    .collect(Collectors.groupingBy(SysFileAnnex::getFileRelationId));

            // 构造路径 → 附件 文件 映射
            Map<String, SysFileAnnex> pathFileMap = new HashMap<>();
            treeData.forEach(item -> {
                StringBuilder pathBuilder = new StringBuilder();
                pathBuilder.append(item.getPName());
                if (ValidateUtil.isNotEmpty(item.getDdMonomer())) {
                    pathBuilder.append("/").append(item.getDdMonomer());
                }
                if (ValidateUtil.isNotEmpty(item.getDdProfessional())) {
                    pathBuilder.append("/").append(item.getDdProfessional());
                }
                String path = pathBuilder.toString();
                item.getChildDrawing().forEach(child -> {
                    List<SysFileAnnex> files = relationFileMap.getOrDefault(child.getDdFileRelationId(), Collections.emptyList());
                    for (SysFileAnnex file : files) {
                        String finalPath = path + "/" + file.getFileName();
                        int index = 1;
                        while (pathFileMap.containsKey(finalPath)) {
                            finalPath = path + "/" + appendFileNameIndex(file.getFileName(), index);
                            index++;
                        }
                        pathFileMap.put(finalPath, file);
                    }
                });
            });

            FileUtil.downloadZipWithPathMapping(response, "图纸管理.zip", pathFileMap, root, archive);
        } catch (IOException e) {
            log.error("图纸管理文件下载失败{}", e.getMessage(), e);
        }
    }

    private String appendFileNameIndex(String fileName, int index) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex == -1) {
            return fileName + "(" + index + ")";
        } else {
            String namePart = fileName.substring(0, dotIndex);
            String extPart = fileName.substring(dotIndex);
            return namePart + "(" + index + ")" + extPart;
        }
    }
}
