package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.mapper.SysJiMuReportMapepr;
import com.jpmsoft.fileUpload.oss.crc.CrcOssUtil;
import com.jpmsoft.fileUpload.pojo.dto.JMExportDTO;
import com.jpmsoft.fileUpload.pojo.dto.JimuExportAutoArchiveDTO;
import com.jpmsoft.fileUpload.pojo.dto.OssClientParamsDto;
import com.jpmsoft.fileUpload.pojo.entity.SysFileAnnex;
import com.jpmsoft.fileUpload.pojo.entity.SysFileTemplatePrint;
import com.jpmsoft.fileUpload.pojo.entity.SysJiMuReport;
import com.jpmsoft.fileUpload.pojo.entity.SysUser;
import com.jpmsoft.fileUpload.service.*;
import com.jpmsoft.fileUpload.utils.FileUtil;
import com.jpmsoft.fileUpload.utils.MD5File;
import com.jpmsoft.fileUpload.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2025/3/10 16:47
 */
@Slf4j
@Service
public class SysJiMuReportImpl extends ServiceImpl<SysJiMuReportMapepr, SysJiMuReport> implements ISysJiMuReportService {


    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private ISysFileTemplatePrintService sysFileTemplatePrintService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysFileAnnexService sysFileAnnexService;


    @Override
    public void generateJimuReportPdf() {
        JimuExportAutoArchiveDTO conf = sysConfigService.getJimuExportArchive();
        if (conf != null) {
            List<SysJiMuReport> entities = baseMapper.selectBySize(20);
            if (!CollectionUtils.isEmpty(entities)) {
                QueryWrapper<SysFileTemplatePrint> printQueryWrapper = new QueryWrapper<>();
                printQueryWrapper.in("tp_id", entities.stream().map(SysJiMuReport::getJmId).collect(Collectors.toList()));
                List<SysFileTemplatePrint> prints = sysFileTemplatePrintService.list(printQueryWrapper);
                if (CollectionUtils.isEmpty(prints)) throw new JPMException("缺少文档模板打印配置！");
                String root = sysConfigService.getRootDirectory();
                root = root.endsWith("/") ? new StringBuilder(root).deleteCharAt(root.length() - 1).toString() : root;
                //文件信息
                List<SysFileAnnex> fileAnnexes = new ArrayList<>();
                OssClientParamsDto ossConf = sysConfigService.getOssParams();
                //虚拟访问令牌
                Object rd = redisUtils.get(conf.getCreateUid());
                String token = ValidateUtil.isNull(rd) ? GenerateUUID.getToekn() : rd.toString();
                if (ValidateUtil.isNull(rd) || !token.equals(rd.toString())) {
                    redisUtils.set(conf.getCreateUid(), token);
                    redisUtils.set(token, conf.getCreateUid());
                    getLogInfo(sysUserService.getById(conf.getCreateUid()), token);
                }
                SysUser user = sysUserService.getById(conf.getCreateUid());
                List<String> jmrs=new ArrayList<>();
                for (SysJiMuReport entity : entities) {
                    if (ValidateUtil.isEmpty(conf.getExportPdfUrl()))
                        throw new JPMException("缺少积木报表导出PDF请求地址配置！");
                    List<SysFileTemplatePrint> print = prints.stream().filter(f -> f.getTpId().equals(entity.getJmId())).collect(Collectors.toList());
                    if (CollectionUtils.isEmpty(print)) {
                        log.info("缺少文档模板打印配置[" + entity.getJmId() + "]");
                    } else {
                        SysFileTemplatePrint pt = print.stream().findFirst().get();
                        JMExportDTO param = new JMExportDTO();
                        param.setExcelConfigId(getExcelConfigId(pt.getTpApiUrl()));
                        JMExportDTO.QueryParam qm = new JMExportDTO.QueryParam();
                        qm.setToken(token);
                        qm.setId(entity.getFileId());
                        param.setQueryParam(qm);
                        SysFileAnnex annex = new SysFileAnnex();
                        annex.setFileId(GenerateUUID.getUUID());
                        annex.setFileRelationId(GenerateUUID.getUUID());
                        annex.setTeId(user.getTeId());
                        annex.setUploadStatus(1);
                        annex.setFileCode(entity.getTCode());
                        annex.setFileEntityId(entity.getFileId());
                        annex.setFileCreateUId(user.getuId());
                        annex.setFileCreateUName(user.getuName());
                        annex.setFileName(pt.getTpName() + ".pdf");
                        annex.setFilePath("/jimuReport/unArchive/" + entity.getTCode() + "/" + GenerateUUID.getUUID() + ".pdf");
                        annex.setFileSuffix("pdf");
                        annex.setFileLocation(0);
                        annex.setFileReplace(0);
                        annex.setArchive(0);
                        annex.setFileSourceType("01");
                        annex.setFileUpOss(Boolean.FALSE);
                        annex.setFileJimuReport(Boolean.TRUE);
                        String path = root + annex.getFilePath();
                        //通过jimu报表生成PDF保存到指定位置
                        path = FileUtil.httpPost2File(conf.getExportPdfUrl(), JacksonUtils.toJSONString(param), path, token);
                        if (ValidateUtil.isEmpty(path))
                            continue;
                        File f = new File(path);
                        if (f.exists() && f.isFile()) {
                            annex.setFileSize(f.length());
                            annex.setFileMd5Code(MD5File.getFileMD5Str(f));
                        }
                        fileAnnexes.add(annex);
                        jmrs.add(entity.getId());
                    }
                }
                if (!CollectionUtils.isEmpty(fileAnnexes)) {
                    String finalRoot = root;
                    fileAnnexes.forEach(f -> {
                        String ossDir = f.getFilePath().substring(0, f.getFilePath().lastIndexOf("/") + 1);
                        //文件赋值后上传到OSS归档目录
                        if (ossConf != null && ossConf.isEnableOss()) {
                            String key = CrcOssUtil.upload(ossConf, finalRoot + f.getFilePath(), ossConf.getOssArchiveDir() + ossDir);
                            if (ValidateUtil.isNotEmpty(key)) f.setFileUpOss(Boolean.TRUE);
                        }
                    });
                    //保存附件信息
                    sysFileAnnexService.saveBatch(fileAnnexes);
                    //删除记录
                    baseMapper.deleteBatchIds(jmrs);
                }
                //todo file表fileJmArchive字段是否更新
            } else
                log.info("暂无数据需要生成积木报表PDF！");
        }
    }


    private void getLogInfo(SysUser user, String token) {
        LogUserInfoVo uf = new LogUserInfoVo();
        uf.setId(user.getuId());
        uf.setCode(user.getuCode());
        uf.setUType(user.getuType());
        uf.setTeId(user.getTeId());
        uf.setUFirstLogin(0);
        redisUtils.set("firstLogin_" + token, 0);
        redisUtils.set("userLoginInfo_" + user.getuId(), JacksonUtils.toJSONString(uf));
    }

    private String getExcelConfigId(String apiUrl) {
        if (ValidateUtil.isEmpty(apiUrl)) return null;
        String[] strs = apiUrl.split("/");
        return strs[strs.length - 1];
    }

}
