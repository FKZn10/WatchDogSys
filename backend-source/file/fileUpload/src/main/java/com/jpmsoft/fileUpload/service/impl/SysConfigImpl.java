package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.model.request.InterfaceEncDecDTO;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.mapper.SysConfigMapper;
import com.jpmsoft.fileUpload.pojo.dto.*;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.SysConfigDto;
import com.jpmsoft.fileUpload.pojo.entity.SysConfig;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class SysConfigImpl extends ServiceImpl<SysConfigMapper, SysConfig> implements ISysConfigService {

    @Resource
    private RedisUtils redisUtils;

    @Override
    public String getValueByCode(String code) {
        if (ValidateUtil.isEmpty(code)) return null;
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", code);
        wrapper.eq("Con_Status", 1);
        SysConfig pojo = baseMapper.selectOne(wrapper);
        if (pojo != null)
            return pojo.getConValue();
        else
            return null;
    }

    @Override
    public String getSysConfByCode(String code) {
        if (ValidateUtil.isEmpty(code)) return null;
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", code);
        wrapper.eq("Con_Status", 1);
        //wrapper.eq("Con_Mold", 1); 系统级基础配置默认为一
        List<SysConfig> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.get(0).getConValue();
        else
            return null;
    }

    @Override
    public String getRootDirectory() {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", "rootDirectory");
        wrapper.eq("Con_Status", 1);
        //wrapper.eq("Con_Mold", 1); 系统参数 唯一
        List<SysConfig> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.get(0).getConValue();
        else
            return null;
    }

    @Override
    public String getArchiveDirectory() {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", "archiveDirectory");
        wrapper.eq("Con_Status", 1);
        //wrapper.eq("Con_Mold", 1);
        List<SysConfig> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.get(0).getConValue();
        else
            return null;
    }

    @Override
    public String getDownloadUrl() {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", "downloadUrl");
        wrapper.eq("Con_Status", 1);
        //wrapper.eq("Con_Mold", 1);
        List<SysConfig> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.get(0).getConValue();
        else
            return null;
    }

    @Override
    public Boolean deleteImportZip() {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", "deleteTyImportZipFile");
        wrapper.eq("Con_Status", 1);
        //wrapper.eq("Con_Mold", 1);
        List<SysConfig> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.get(0).getConValue().equals("1");
        else
            return false;
    }

    @Override
    public String showHistroyReplaceFile() {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
        wrapper.eq("Con_Code", "histroyReplaceFile");
        wrapper.eq("Con_Status", 1);
        //wrapper.eq("Con_Mold", 1);
        List<SysConfig> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data))
            return data.get(0).getConValue();
        else
            return "0";
    }

    @Override
    public Map<String, String> getValueByCodes(List<String> codes, boolean sys) {
        QueryWrapper<SysConfig> wrapper = new QueryWrapper<>();
//        if (sys)
//            wrapper.eq("Con_Mold", 1);
        wrapper.eq("Con_Status", 1);
        wrapper.eq("Con_Code", codes.get(0));
        for (int i = 1; i < codes.size(); i++)
            wrapper.or().eq("Con_Code", codes.get(i));
        List<SysConfig> configs = baseMapper.selectList(wrapper);
        Map<String, String> data = new HashMap<>();
        if (!CollectionUtils.isEmpty(codes)) {
            for (String code : codes) {
                List<SysConfig> config = configs.stream().filter(n -> n.getConCode().equals(code)).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(config))
                    data.put(code, ValidateUtil.checkEmpty(config.stream().findFirst().get().getConValue()));
                else
                    data.put(code, null);
            }
        }
        return data;
    }

    @Override
    public Map<String, String> getSysValueByCodes(List<String> codes) {
        return getValueByCodes(codes, true);
    }

    @Override
    public WatermarkControlDto getWatermarkControlConf() {
        String conf = getSysConfByCode("systemWatermark");
        if (ValidateUtil.isNotEmpty(conf))
            return JacksonUtils.parseObject(conf, WatermarkControlDto.class);
        return new WatermarkControlDto();
    }

    @Override
    public OutlineWatermarkControlDto getOutlineWatermarkControlConf() {
        String conf = getSysConfByCode("outlineWatermark");
        if (ValidateUtil.isNotEmpty(conf)) {
            return JacksonUtils.parseObject(conf, OutlineWatermarkControlDto.class);
        }
        return new OutlineWatermarkControlDto();
    }

    @Override
    public List<SysConfigDto> getAllConfig() {
        return baseMapper.getAllConfig();
    }

    @Override
    public List<String> getAllConCodes() {
        return baseMapper.getAllConfigCode();
    }

    @Override
    public OssClientParamsDto getOssParams() {
        String json = getSysConfByCode("crcOssClientParams");
        if (ValidateUtil.isNotEmpty(json))
            return JacksonUtils.parseObject(json, OssClientParamsDto.class);
        else
            return null;
    }

    @Override
    public OcrConfigDTO getOcrConfig() {
        String json = getSysConfByCode("ocrConfigInfo");
        if (ValidateUtil.isNotEmpty(json))
            return JacksonUtils.parseObject(json, OcrConfigDTO.class);
        else
            return null;
    }

    @Override
    public JimuExportAutoArchiveDTO getJimuExportArchive() {
        String json = getSysConfByCode("jimuExportAutoArchive");
        if (ValidateUtil.isNotEmpty(json))
            return JacksonUtils.parseObject(json, JimuExportAutoArchiveDTO.class);
        else
            return null;
    }

    @Override
    public CompressImgDTO getCompressImg() {
        String json = getSysConfByCode("jpmCompressImgCcConf");
        CompressImgDTO data = new CompressImgDTO();
        if (ValidateUtil.isNotEmpty(json))
            data = JacksonUtils.parseObject(json, CompressImgDTO.class);
        if (data == null) return new CompressImgDTO();
        return data;
    }


    @Override
    public InterfaceEncDecDTO getInterfaceEncDec() {
        String conf = getSysConfByCode("interfaceEncDecConfig");
        if (ValidateUtil.isNotEmpty(conf)) {
            return JacksonUtils.parseObject(conf, InterfaceEncDecDTO.class);
        }
        return new InterfaceEncDecDTO();
    }

    @Override
    public FileInfoExtractionByAI getFileInfoExtractionByAI() {
        String conf = getSysConfByCode("fileInfoExtractionByAI");
        if (ValidateUtil.isNotEmpty(conf)) {
            return JacksonUtils.parseObject(conf, FileInfoExtractionByAI.class);
        }
        return new FileInfoExtractionByAI();
    }

    private LogUserInfoVo userInfo() {
        //获取用户登录信息
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String token = request.getHeader("token");
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        return user;
    }
}
