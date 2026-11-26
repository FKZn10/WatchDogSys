package com.jpmsoft.fileUpload.service.impl;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.easyexcel.EasyExcelData;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.OaProjectReportQuarterDto;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.SysConfigDto;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.SysFileTemplateDto;
import com.jpmsoft.fileUpload.pojo.entity.PreProject;
import com.jpmsoft.fileUpload.pojo.entity.SysConfig;
import com.jpmsoft.fileUpload.pojo.entity.SysFileTemplate;
import com.jpmsoft.common.model.response.LogUserInfoVo;
import com.jpmsoft.fileUpload.service.IEasyExcelService;
import com.jpmsoft.fileUpload.service.IPreProjectService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.service.ISysFileTemplateService;
import com.jpmsoft.fileUpload.utils.ExcelUtil;
import com.jpmsoft.fileUpload.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @Author: 李文涛 aaron
 * @date: 2022/12/12 10:47
 */
@Service
public class EasyExcelImpl implements IEasyExcelService {
    @Value("${file.upload.dirPath}")
    private String dirPath;

    @Resource
    private ISysConfigService sysConfigService;

    @Resource
    private ISysFileTemplateService sysFileTemplateService;

    @Resource
    private IPreProjectService preProjectService;


    @Override
    public void exportConf(HttpServletResponse response) {
        //region 导出成excel
        List<SysConfigDto> confs = sysConfigService.getAllConfig();
        List<SysConfigDto> rootDir = confs.stream().filter(n -> ValidateUtil.isNotEmpty(n.getConCode()) && n.getConCode().equals("rootDirectory")).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(rootDir))
            throw new JPMException(String.format(FileMsg.notFoundSysConfig, "rootDirectory"));
        String root = rootDir.stream().findFirst().get().getConValue();
        String filePath = FileUtil.temporaryPath(root + "/" + dirPath, "xlsx");
        ExcelUtil.export2File(confs, SysConfigDto.class, filePath, "基础配置信息");
        //endregion
        //region 下载文件后删除临时文件
        FileUtil.downFileAndDelFile(response, filePath, "基础配置信息.xlsx");
        //endregion
    }

    @Override
    public BaseResponse importSysConf(MultipartFile file, LogUserInfoVo user) {
        List<SysConfigDto> datas = new EasyExcelData<SysConfigDto>().getExcelData(file, SysConfigDto.class, 1);
        if (!CollectionUtils.isEmpty(datas)) {
            List<String> codes = sysConfigService.getAllConCodes();
            Date now = new Date();
            List<SysConfig> adds = datas.stream().filter(n -> ValidateUtil.isNotEmpty(n.getConCode()) && !codes.contains(n.getConCode()))
                    .map(data -> {
                        SysConfig conf = new SysConfig();
                        BeanUtils.copyProperties(data, conf);
                        conf.setConId(GenerateUUID.getUUID());
                        conf.setConCreatetime(now);
                        conf.setConCreateuId(user.getId());
                        conf.setConCreateuName(user.getName());
                        return conf;
                    }).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(adds)) sysConfigService.saveBatch(adds);
        }
        return BaseResponse.ok();
    }

    @Override
    public BaseResponse importOaQuarterlyReport(MultipartFile file, Integer year, Integer quarter) {
        if (preProjectService.havingOaQuarterlyReport(year, quarter))
            return BaseResponse.fail(FileMsg.oaQuarterlyReportExist);
        List<OaProjectReportQuarterDto> datas = new EasyExcelData<OaProjectReportQuarterDto>().getExcelData(file, OaProjectReportQuarterDto.class, 1);
        if (!CollectionUtils.isEmpty(datas)) {
            List<String> codes = datas.stream().map(OaProjectReportQuarterDto::getPCode).collect(Collectors.toList());
            List<PreProject> pros = preProjectService.getPreProjectByCodes(codes);
            List<OaProjectReportQuarterDto> models = new ArrayList<>();
            for (OaProjectReportQuarterDto data : datas) {
                List<PreProject> sameCodePro = pros.stream().filter(n -> n.getpCode().equals(data.getPCode())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(sameCodePro)) {
                    PreProject pro = sameCodePro.stream().findFirst().get();
                    data.setPId(pro.getpId());
                    models.add(data);
                } else
                    return BaseResponse.fail(String.format(FileMsg.notFoundProjByCode, data.getPCode()));
            }
            return BaseResponse.ok(models);
        }
        return BaseResponse.ok();
    }

    @Override
    public void exportSysFileTemp(HttpServletResponse response) {
        String root = sysConfigService.getRootDirectory();
        if (ValidateUtil.isEmpty(root))
            throw new JPMException(String.format(FileMsg.notFoundSysConfig, "rootDirectory"));
        String filePath = FileUtil.temporaryPath(root + "/" + dirPath, "xlsx");
        List<SysFileTemplateDto> datas = sysFileTemplateService.getAllFileTemp();
        //上级ID替换成模板编码
        List<SysFileTemplateDto> outs = datas.stream().map(data -> {
            if (ValidateUtil.isNotEmpty(data.getTParentid()) && !data.getTParentid().equals("0")) {
                List<SysFileTemplateDto> parents = datas.stream().filter(n -> n.getTId().equals(data.getTParentid())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(parents)) data.setTParentid(parents.stream().findFirst().get().getTCode());
                else data.setTParentid(null);
            } else
                data.setTParentid("0");
            return data;
        }).collect(Collectors.toList());
        ExcelUtil.export2File(outs, SysFileTemplateDto.class, filePath, "文档模板信息");
        //region 下载文件后删除临时文件
        FileUtil.downFileAndDelFile(response, filePath, "文档模板信息.xlsx");
        //endregion
    }

    @Override
    public BaseResponse importSysFileTemp(MultipartFile file, LogUserInfoVo user) {
        List<SysFileTemplateDto> datas = new EasyExcelData<SysFileTemplateDto>().getExcelData(file, SysFileTemplateDto.class, 1);
        if (!CollectionUtils.isEmpty(datas)) {
            List<SysFileTemplateDto> allTemps = sysFileTemplateService.getAllFileTemp();
            List<String> allTCodes = allTemps.stream().filter(n -> ValidateUtil.isNotEmpty(n.getTCode())).map(SysFileTemplateDto::getTCode).collect(Collectors.toList());
            Date now = new Date();
            List<SysFileTemplateDto> tops = datas.stream().filter(n -> !allTCodes.contains(n.getTCode()) && n.getTParentid().equals("0")).collect(Collectors.toList());
            List<SysFileTemplate> adds = new ArrayList<>();
            importFileTmpDatas(allTemps, datas, adds, tops, allTCodes, user, now);
            if (!CollectionUtils.isEmpty(adds))
                sysFileTemplateService.saveBatch(adds);
        }
        return BaseResponse.ok();
    }

    private void importFileTmpDatas(List<SysFileTemplateDto> allTemps, List<SysFileTemplateDto> datas,
                                    List<SysFileTemplate> adds, List<SysFileTemplateDto> tops, List<String> allTCodes, LogUserInfoVo user, Date now) {
        for (SysFileTemplateDto data : tops) {
            SysFileTemplate temp = new SysFileTemplate();
            BeanUtils.copyProperties(data, temp);
            temp.settId(GenerateUUID.getUUID());
            temp.settCreatetime(now);
            temp.settCreateuser(user.getId());
            temp.settCreateusername(user.getName());
            //region 获取排序号
            int sort = 0;
            if (data.getTParentid().equals("0")) {
                //第一层
                Optional<SysFileTemplateDto> maxOptional = allTemps.stream().filter(n -> n.getTParentid().equals("0"))
                        .max(Comparator.comparing(SysFileTemplateDto::getTSort));
                if (maxOptional.isPresent())
                    sort = maxOptional.get().getTSort();
            } else {
                List<SysFileTemplateDto> parents = allTemps.stream().filter(n -> n.getTCode().equals(data.getTParentid())).collect(Collectors.toList());
                if (!CollectionUtils.isEmpty(parents)) {
                    SysFileTemplateDto parent = parents.stream().findFirst().get();
                    temp.settParentid(parent.getTId());
                    Optional<SysFileTemplateDto> maxOptional = allTemps.stream().filter(n -> n.getTParentid().equals(parent.getTId()))
                            .max(Comparator.comparing(SysFileTemplateDto::getTSort));
                    if (maxOptional.isPresent())
                        sort = maxOptional.get().getTSort();
                } else
                    throw new JPMException(String.format(FileMsg.notFoundParentTemp, data.getTCode()));
            }
            //endregion
            temp.settSort(sort + 1);
            //加入到所有集合内 下次获取序号可能用到
            data.setTId(temp.gettId());
            allTemps.add(data);
            adds.add(temp);
            allTCodes.add(temp.gettCode());
        }
        List<SysFileTemplateDto> childs = datas.stream().filter(n -> !allTCodes.contains(n.getTCode()) && allTCodes.contains(n.getTParentid())).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(childs))
            importFileTmpDatas(allTemps, datas, adds, childs, allTCodes, user, now);
    }

}
