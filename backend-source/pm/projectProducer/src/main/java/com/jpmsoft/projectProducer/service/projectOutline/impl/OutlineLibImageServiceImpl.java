package com.jpmsoft.projectProducer.service.projectOutline.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineLibImageMapper;
import com.jpmsoft.projectProducer.pojo.entity.other.SysFileannex;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineLibImage;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectLib;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineSystemLib;
import com.jpmsoft.projectProducer.service.other.ISysFileAnnexService;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineLibImageService;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjectLibService;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineSystemLibService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 目录图片对应接口实现
 *
 * @author tianhelin
 * @date 2025/08/25
 */
@Slf4j
@Service
public class OutlineLibImageServiceImpl extends ServiceImpl<OutlineLibImageMapper, OutlineLibImage> implements IOutlineLibImageService {
    @Resource
    private IOutlineSystemLibService outlineSystemLibService;
    @Resource
    private IOutlineProjectLibService outlineProjectLibService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    /**
     * 初始化
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void imageInitialization() {
        // 查询所有的id和content对应
        List<OutlineSystemLib> outlineSystemLibs = outlineSystemLibService.list(Wrappers.<OutlineSystemLib>lambdaQuery()
                .isNotNull(OutlineSystemLib::getOslContent)
                .select(OutlineSystemLib::getId, OutlineSystemLib::getOslContent)
        );
        if (ValidateUtil.isEmpty(outlineSystemLibs)) {
            return;
        }
        List<OutlineProjectLib> outlineProjectLibs = outlineProjectLibService.list(Wrappers.<OutlineProjectLib>lambdaQuery()
                .isNotNull(OutlineProjectLib::getOplContent)
                .select(OutlineProjectLib::getId, OutlineProjectLib::getOplContent)
        );
        if (ValidateUtil.isEmpty(outlineProjectLibs)) {
            return;
        }

        // 合并成一个Map
        Map<String, String> contentMap = outlineSystemLibs.stream()
                .filter(item -> item.getOslContent().contains("/jpm/downloadAloneFile/"))
                .collect(Collectors.toMap(OutlineSystemLib::getId, OutlineSystemLib::getOslContent));
        contentMap.putAll(outlineProjectLibs.stream()
                .filter(item -> item.getOplContent().contains("/jpm/downloadAloneFile/"))
                .collect(Collectors.toMap(OutlineProjectLib::getId, OutlineProjectLib::getOplContent)));

        // 将Map的内容构建成OutlineLibImage
        List<OutlineLibImage> outlineLibImages = new ArrayList<>();
        contentMap.forEach((key, value) -> {
            // 找到内容里所有的图片ID
            List<String> fileIds = getFileIdsFromHtml(value);
            if (ValidateUtil.isNotEmpty(fileIds)) {
                for (String fileId : fileIds) {
                    OutlineLibImage entity = new OutlineLibImage();
                    entity.setId(GenerateUUID.getUUID());
                    entity.setLibId(key);
                    entity.setFileId(fileId);
                    outlineLibImages.add(entity);
                }
            }
        });
        // 排除已有的libId和fileId组合
        List<OutlineLibImage> existingImages = this.list(Wrappers.<OutlineLibImage>lambdaQuery()
                .in(OutlineLibImage::getLibId, contentMap.keySet())
                .select(OutlineLibImage::getLibId, OutlineLibImage::getFileId)
        );
        List<OutlineLibImage> toBeSavedImages = outlineLibImages;
        if (ValidateUtil.isNotEmpty(existingImages)) {
            List<String> existingCombinations = existingImages.stream()
                    .map(item -> item.getLibId() + "_" + item.getFileId())
                    .collect(Collectors.toList());
            toBeSavedImages = outlineLibImages.stream()
                    .filter(item -> !existingCombinations.contains(item.getLibId() + "_" + item.getFileId()))
                    .collect(Collectors.toList());
        }
        if (ValidateUtil.isNotEmpty(toBeSavedImages)) {
            this.saveBatch(toBeSavedImages);
        }
    }

    /**
     * 从HTML内容中提取所有图片的文件ID
     *
     * @param html HTML内容
     * @return 图片文件ID列表
     */
    public List<String> getFileIdsFromHtml(String html) {
        List<String> fileIds = new ArrayList<>();
        if (html == null || !html.contains("<img")) {
            return fileIds;
        }
        Document doc = Jsoup.parseBodyFragment(html);
        Elements imgElements = doc.select("img");
        for (Element img : imgElements) {
            try {
                String src = img.attr("src");
                String fileId = extractFileId(src);
                if (ValidateUtil.isNotEmpty(fileId) && !fileIds.contains(fileId)) {
                    fileIds.add(fileId);
                }
            } catch (Exception e) {
                log.error("图片ID获取失败: {}", img.attr("src"), e);
            }
        }
        return fileIds;
    }

    /**
     * 提取图片链接中的文件ID
     *
     * @param src 图片链接
     * @return 文件ID
     */
    private String extractFileId(String src) {
        Pattern idPattern = Pattern.compile("/jpm/downloadAloneFile/([a-fA-F0-9\\-]{32,36})/");
        Matcher matcher = idPattern.matcher(src);
        if (matcher.find()) {
            return matcher.group(1);
        }
        Pattern fallbackPattern = Pattern.compile("/jpm/downloadAloneFile/([^/{}]+)");
        Matcher fallbackMatcher = fallbackPattern.matcher(src);
        if (fallbackMatcher.find()) {
            return fallbackMatcher.group(1);
        }
        return null;
    }

    /**
     * 删除图片
     * <p>
     * 删除指定文件
     *
     * @param libId
     * @param fileId
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteFile(String libId, String fileId) {
        if (ValidateUtil.isEmpty(libId) || ValidateUtil.isEmpty(fileId)) {
            return BaseResponse.fail("参数错误");
        }
        // 校验其他id是否引用了该图片
        long count = this.count(Wrappers.<OutlineLibImage>lambdaQuery()
                .eq(OutlineLibImage::getFileId, fileId)
                .ne(OutlineLibImage::getLibId, libId)
        );
        if (count > 0) {
            return BaseResponse.fail("该图片被其他目录引用，无法删除");
        }
        boolean remove1 = sysFileAnnexService.update(Wrappers.<SysFileannex>lambdaUpdate()
                .set(SysFileannex::getUploadStatus, 0)
                .eq(SysFileannex::getFileId, fileId)
        );
        boolean remove2 = this.remove(Wrappers.<OutlineLibImage>lambdaQuery()
                .eq(OutlineLibImage::getLibId, libId)
                .eq(OutlineLibImage::getFileId, fileId)
        );
        if (remove1 && remove2) {
            return BaseResponse.ok("删除成功");
        } else {
            return BaseResponse.fail("删除失败");
        }
    }

    /**
     * 删除图片
     * <p>
     * 批量删除指定文件
     *
     * @param libId
     * @param fileIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteFiles(String libId, List<String> fileIds) {
        if (ValidateUtil.isEmpty(libId) || ValidateUtil.isEmpty(fileIds)) {
            return BaseResponse.fail("参数错误");
        }
        // 查询被其他目录引用的fileId
        List<String> referencedFileIds = this.list(Wrappers.<OutlineLibImage>lambdaQuery()
                        .in(OutlineLibImage::getFileId, fileIds)
                        .ne(OutlineLibImage::getLibId, libId)
                        .select(OutlineLibImage::getFileId))
                .stream().map(OutlineLibImage::getFileId).collect(Collectors.toList());

        // 只删除未被引用的fileId
        List<String> deletableFileIds = fileIds.stream()
                .filter(id -> !referencedFileIds.contains(id))
                .collect(Collectors.toList());

        if (ValidateUtil.isEmpty(deletableFileIds)) {
            return BaseResponse.fail("所有图片均被其他目录引用，无法删除");
        }

        boolean removeAnnex = sysFileAnnexService.update(Wrappers.<SysFileannex>lambdaUpdate()
                .set(SysFileannex::getUploadStatus, 0)
                .in(SysFileannex::getFileId, deletableFileIds));
        boolean removeImages = this.remove(Wrappers.<OutlineLibImage>lambdaQuery()
                .eq(OutlineLibImage::getLibId, libId)
                .in(OutlineLibImage::getFileId, deletableFileIds));

        if (removeAnnex && removeImages) {
            if (ValidateUtil.isNotEmpty(referencedFileIds)) {
                return BaseResponse.ok("部分图片删除成功，部分被其他目录引用未删除: " + String.join(",", referencedFileIds));
            }
            return BaseResponse.ok("批量删除成功");
        } else {
            return BaseResponse.fail("批量删除失败");
        }
    }

    /**
     * 处理图片
     * <p>
     * 按照fileIds与当前libId下的图片的id的差集进行删除和新增
     *
     * @param libId
     * @param fileIds
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse handleOutlineFile(String libId, List<String> fileIds) {
        if (ValidateUtil.isEmpty(libId) || ValidateUtil.isEmpty(fileIds)) {
            return BaseResponse.fail("无需删除");
        }
        // 去掉无效的fileId，如空字符串、URL、Base64等，并去重
        List<String> ids = fileIds.stream()
                .filter(ValidateUtil::isNotEmpty)
                .filter(fileId -> !fileId.contains("http")
                        && !fileId.contains("https")
                        && !fileId.contains("data:")
                        && !fileId.contains("base64")
                        && !fileId.contains("downloadAloneFile")
                )
                .distinct()
                .collect(Collectors.toList());
        // 查询当前libId下的所有图片
        List<OutlineLibImage> existingImages = this.list(Wrappers.<OutlineLibImage>lambdaQuery()
                .eq(OutlineLibImage::getLibId, libId)
                .select(OutlineLibImage::getFileId)
        );
        List<String> existingFileIds = existingImages.stream()
                .map(OutlineLibImage::getFileId)
                .collect(Collectors.toList());

        // 需要新增的fileId
        List<String> toBeAddedFileIds = ids.stream()
                .filter(id -> !existingFileIds.contains(id))
                .collect(Collectors.toList());
        // 需要删除的fileId
        List<String> toBeDeletedFileIds = existingFileIds.stream()
                .filter(id -> !ids.contains(id))
                .collect(Collectors.toList());

        // 新增
        if (ValidateUtil.isNotEmpty(toBeAddedFileIds)) {
            List<OutlineLibImage> saveEntities = new ArrayList<>();
            toBeAddedFileIds.forEach(fileId -> {
                OutlineLibImage entity = new OutlineLibImage();
                entity.setId(GenerateUUID.getUUID());
                entity.setLibId(libId);
                entity.setFileId(fileId);
                saveEntities.add(entity);
            });
            this.saveBatch(saveEntities);
        }

        // 删除
        if (ValidateUtil.isNotEmpty(toBeDeletedFileIds)) {
            return this.deleteFiles(libId, toBeDeletedFileIds);
        }

        return BaseResponse.ok("处理成功");
    }
}
