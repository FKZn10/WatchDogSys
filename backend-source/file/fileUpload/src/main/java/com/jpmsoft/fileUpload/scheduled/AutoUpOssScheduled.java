package com.jpmsoft.fileUpload.scheduled;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.oss.crc.CrcOssUtil;
import com.jpmsoft.fileUpload.pojo.dto.OssClientParamsDto;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.service.ISysFileAnnexService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 自动上传OSS
 * 重新上传历史数据
 * 补偿策略（逻辑遗漏已确认上传但是没有上传到OSS的数据）
 * Created By:李文涛 aaron
 * Created Time:2022/6/19 15:01
 */
@Slf4j
@Component
public class AutoUpOssScheduled {

    @Resource
    private ISysFileAnnexService sysFileAnnexService;

    @Resource
    private ISysConfigService sysConfigService;

    /**
     * 已确认上传但未上传到OSS的文件
     * 上传到OSS
     * 单次轮询默认50条文件记录
     */
    @Scheduled(cron = "${platform.parameters.scheduling.autoUpOss:-}")
    public void autoUpOss() {
        OssClientParamsDto param = sysConfigService.getOssParams();
        if (param != null && param.isEnableOss()) {
            QueryWrapper<SysFileAnnex> wrapper = new QueryWrapper<>();
            wrapper.eq("upload_status", 1);
            wrapper.and(wr -> wr.eq("File_Up_Oss", Boolean.FALSE).or().isNull("File_Up_Oss"));
            wrapper.orderByDesc("file_upload_time");
            wrapper.last(" limit 200");
            List<SysFileAnnex> files = sysFileAnnexService.list(wrapper);
            List<SysFileAnnex> upSuccess = new ArrayList<>();
            if (!CollectionUtils.isEmpty(files)) {
                String root = sysConfigService.getRootDirectory();
                String archive = sysConfigService.getArchiveDirectory();
                root = root.endsWith("/") ? root : root + "/";
                archive = archive.endsWith("/") ? archive : archive + "/";
                for (SysFileAnnex f : files) {
                    File file = new File((f.getFileLocation() == 0 ? root : archive) + f.getFilePath());
                    if (file.exists() && file.isFile()) {
                        String ossDir = f.getFileLocation() == 0 ? param.getOssRootDir() : param.getOssArchiveDir();
                        ossDir = ossDir + f.getFilePath().substring(0, f.getFilePath().lastIndexOf("/") + 1);
                        String key = CrcOssUtil.upload(param, file.getPath(), ossDir);
                        if (ValidateUtil.isNotEmpty(key)) {
                            f.setFileUpOss(Boolean.TRUE);
                            upSuccess.add(f);
                            log.info("成功上传OSS：" + f.getFileId() + " 文件路径" + file.getPath());
                        }
                    } else {
                        log.error("无法上传到OSS找不到实体文件:" + file.getPath());
                    }
                }
                if (!CollectionUtils.isEmpty(upSuccess))
                    sysFileAnnexService.updateBatchById(upSuccess);
            }
        }
    }
}
