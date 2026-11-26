package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.fileUpload.pojo.dto.*;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.SysConfigDto;
import com.jpmsoft.fileUpload.pojo.entity.SysConfig;

import java.util.List;
import java.util.Map;

public interface ISysConfigService extends IService<SysConfig> {

    String getValueByCode(String code);

    String getSysConfByCode(String code);

    String getRootDirectory();

    String getArchiveDirectory();

    String getDownloadUrl();

    /**
     * 通用导入方法是否删除上传的zip源文件
     *
     * @return true/false
     */
    Boolean deleteImportZip();

    /**
     * 是否显示历史被替换文件
     * 1 显示  0 不显示
     * 缺省值 0
     *
     * @return
     */
    String showHistroyReplaceFile();

    Map<String, String> getValueByCodes(List<String> codes, boolean sys);

    Map<String, String> getSysValueByCodes(List<String> codes);

    WatermarkControlDto getWatermarkControlConf();

    OutlineWatermarkControlDto getOutlineWatermarkControlConf();

    /**
     * 查询所有基础配置信息
     *
     * @return
     */
    List<SysConfigDto> getAllConfig();

    /**
     * 获取所有基础配置编码
     */
    List<String> getAllConCodes();

    /**
     * 获取对象存储基础配置
     *
     * @return
     */
    OssClientParamsDto getOssParams();

    /**
     * 获取OCR基础配置信息
     *
     * @return
     */
    OcrConfigDTO getOcrConfig();

    JimuExportAutoArchiveDTO getJimuExportArchive();

    /**
     * 图片压缩尺寸配置
     *
     * @return
     */
    CompressImgDTO getCompressImg();


    InterfaceEncDecDTO getInterfaceEncDec();

    /**
     * AI提取附件信息配置项
     * 用于上传附件填充表单
     *
     * @return
     */
    FileInfoExtractionByAI getFileInfoExtractionByAI();
}
