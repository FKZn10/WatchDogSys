package com.jpmsoft.fileUpload.service.impl;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.easyexcel.CustomColumnWidthStyleStrategy;
import com.jpmsoft.fileUpload.feign.UserFeign;
import com.jpmsoft.fileUpload.mapper.SysLoggerInfoMapper;
import com.jpmsoft.fileUpload.pojo.dto.SysLoggerInfoDto;
import com.jpmsoft.fileUpload.pojo.entity.SysInterface;
import com.jpmsoft.fileUpload.pojo.entity.SysLoggerInfo;
import com.jpmsoft.fileUpload.pojo.vo.VuePowerTreeVo;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.service.ISysLoggerInfoService;
import com.jpmsoft.fileUpload.utils.DateUtil;
import com.jpmsoft.fileUpload.utils.ExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
public class SysLoggerInfoImpl extends ServiceImpl<SysLoggerInfoMapper, SysLoggerInfo> implements ISysLoggerInfoService {

    @Autowired
    private ISysConfigService sysConfigService;

    @Autowired
    private UserFeign userFeign;

    @Override
    public void exportLoggerInfo2Excel() {
        String logFilePath = "";
        String iterationRule = sysConfigService.getSysConfByCode("sysLoggerInfoIterationRule");
        if (iterationRule == null) {
            log.error("迭代删除接口日志，缺少配置参数：sysLoggerInfoIterationRule");
            return;
        }
        Map<String, Object> config;
        Boolean export2Excel;
        Integer maxSize;
        Integer minSize;
        Integer pollNum;
        try {
            config = JacksonUtils.parseObject(iterationRule, Map.class);
            if (!config.containsKey("pollNum") || !config.containsKey("export2Excel") || !config.containsKey("maxSize") || !config.containsKey("minSize"))
                //配置参数格式异常
                throw new JPMException("参数配置异常");
            export2Excel = (Boolean) config.get("export2Excel");
            maxSize = (Integer) config.get("maxSize");
            minSize = (Integer) config.get("minSize");
            pollNum = (Integer) config.get("pollNum");
            if (export2Excel == true) {
                logFilePath = sysConfigService.getSysConfByCode("sysLoggerInfoFilePath");
                //缺少配置参数
                if (ValidateUtil.isEmpty(logFilePath))
                    throw new JPMException(FileMsg.missSysConfig);
            }
        } catch (Exception ex) {
            log.error("迭代删除接口日志，缺少配置或配置参数格式异常！",ex);
            return;
        }

        QueryWrapper<SysLoggerInfo> wrapper = new QueryWrapper<>();
        if (baseMapper.selectCount(wrapper) < minSize) {
            System.out.println("迭代删除接口日志,库内数据少于最小配置，本次不执行！");
            return;
        }
        //每次查询 循环执行
        //Excel单元格最高存储字符长度
        wrapper.lt("length(L_RequestParameter)", 32767);
        wrapper.orderByAsc("L_RequestTime");
        wrapper.last(" limit " + pollNum);
        if (export2Excel == true) {//需要先导出到excel
            String fileName = "interfaceLogs-" + GenerateUUID.getUUID().replaceAll("-", "") + "-"
                    + DateUtil.Date2String(new Date(), "yyyyMMddHHmm") + ".xlsx";
            try {
                File dir = new File(logFilePath);
                if (!dir.exists() || !dir.isDirectory())
                    dir.mkdirs();
                File file = new File(logFilePath, fileName);
                if (!file.exists() || !file.isFile())
                    file.createNewFile();
            } catch (Exception ex) {
                log.error("导出接口日志生成Excel文件异常！" + ex.getMessage(),ex);
            }
            ExcelWriter excelWriter = EasyExcel.write(logFilePath + "/" + fileName, SysLoggerInfo.class).build();
            try {
                for (int i = 0; i < maxSize / pollNum; i++) {
                    List<SysLoggerInfo> datas = baseMapper.selectList(wrapper);
                    if (!CollectionUtils.isEmpty(datas)) {
                        List<String> ids = datas.stream().map(SysLoggerInfo::getLId).collect(Collectors.toList());
                        ExcelUtil.exportInterfaceLog2Excel(i, excelWriter, datas);
                        if (ExcelUtil.exportInterfaceLog2Excel(i, excelWriter, datas))
                            delLoggerInfoByIds(ids);
                    }
                }
                excelWriter.finish();
            } catch (Exception ex) {
                ex.printStackTrace();
                log.error("导出接口日志到Excel异常！" + ex.getMessage(),ex);
                excelWriter.finish();
            }
        } else {//直接删除
            baseMapper.delLoggerInfoByNum(maxSize);
        }
    }

    @Override
    public void delLoggerInfoByIds(List<String> ids) {
        if (ids == null || ids.size() < 1)
            return;
        baseMapper.deleteBatchIds(ids);
    }

    @Override
    public ResponseEntity<byte[]> ExportLoggerInfo(SysLoggerInfoDto sysLoggerInfo, HttpServletResponse response,String token) {
        try {
            LambdaQueryWrapper<SysLoggerInfo> queryWrapper = new LambdaQueryWrapper<>();

            BaseResponse powerTreeList = userFeign.getPowerTreeList(token);
            List<VuePowerTreeVo> list;
            if(powerTreeList.getData() != null){
                JSONArray jsonArray = JSONUtil.parseArray(powerTreeList.getData());
                list = jsonArray.toList(VuePowerTreeVo.class);
            } else {
                list = null;
            }
            if(ValidateUtil.isNotNull(sysLoggerInfo)){
                if(ValidateUtil.isNotEmpty(sysLoggerInfo.getLClientip()))
                    queryWrapper.eq(SysLoggerInfo::getLClientip,sysLoggerInfo.getLClientip());
                if(ValidateUtil.isNotEmpty(sysLoggerInfo.getLServerip()))
                    queryWrapper.eq(SysLoggerInfo::getLServerip,sysLoggerInfo.getLServerip());
                if(ValidateUtil.isNotEmpty(sysLoggerInfo.getLUrl()))
                    queryWrapper.like(SysLoggerInfo::getLUrl,sysLoggerInfo.getLUrl());
                if(ValidateUtil.isNotEmpty(sysLoggerInfo.getLRequestmode()))
                    queryWrapper.eq(SysLoggerInfo::getLRequestmode,sysLoggerInfo.getLRequestmode());
                if(ValidateUtil.isNotEmpty(sysLoggerInfo.getLCreateuName()))
                    queryWrapper.like(SysLoggerInfo::getLCreateuName,sysLoggerInfo.getLCreateuName());
                if(ValidateUtil.isNotEmpty(sysLoggerInfo.getLCreateuName()))
                    queryWrapper.like(SysLoggerInfo::getLCreateuName,sysLoggerInfo.getLCreateuName());
                if(ValidateUtil.isNotEmpty(sysLoggerInfo.getLActionEnd()))
                    queryWrapper.like(SysLoggerInfo::getLActionEnd,sysLoggerInfo.getLActionEnd());
                if (!CollectionUtils.isEmpty(sysLoggerInfo.getLRequesttimes())) {
                    if (sysLoggerInfo.getLRequesttimes().size() == 1)
                        queryWrapper.gt(SysLoggerInfo::getLRequesttime, sysLoggerInfo.getLRequesttimes().get(0));
                    else {
                        if (ValidateUtil.isNotEmpty(sysLoggerInfo.getLRequesttimes().get(0)))
                            queryWrapper.gt(SysLoggerInfo::getLRequesttime, sysLoggerInfo.getLRequesttimes().get(0));
                        if (ValidateUtil.isNotEmpty(sysLoggerInfo.getLRequesttimes().get(1)))
                            queryWrapper.lt(SysLoggerInfo::getLRequesttime, sysLoggerInfo.getLRequesttimes().get(1));
                    }
                }
                //查找是否是1级菜单，如果是就把下面的所有子菜单一起查询出来，否则就只查询指定菜单
                if(ValidateUtil.isNotEmpty(sysLoggerInfo.getLFunModule())){
                    List<String> codes = new ArrayList<>();
                    if(ValidateUtil.isNotEmpty(list)){
                        codes = list.stream().filter( e -> e.getPowerCode().equals(sysLoggerInfo.getLFunModule()) && e.getPowerParentid().equals("0"))
                                .flatMap(  e ->{
                                    Stream<String> firstLevelStream = Stream.of(e.getPowerCode());
                                    Stream<String> secondLevelStream = getSecondLevelPowerCodes(e.getChildren());
                                    return Stream.concat(firstLevelStream, secondLevelStream);
                                }).collect(Collectors.toList());
                    }
                    if(ValidateUtil.isNotEmpty(codes)){
                        queryWrapper.in(SysLoggerInfo::getLFunModule,codes);
                    }else{
                        queryWrapper.eq(SysLoggerInfo::getLFunModule,sysLoggerInfo.getLFunModule());
                    }
                }
            }
            queryWrapper.orderByDesc(SysLoggerInfo::getLRequesttime);

            Page<SysLoggerInfo> page = new Page<>(1, 5000);

            Page<SysLoggerInfo> pageList = baseMapper.selectPage(page, queryWrapper);
            List<SysLoggerInfo> records = pageList.getRecords();
            List<SysLoggerInfoDto> truncatedRecords = records.stream().map(info -> {
                SysLoggerInfoDto dto = new SysLoggerInfoDto();
                BeanUtils.copyProperties(info, dto);
                if (info.getLRequestparameter() != null && info.getLRequestparameter().length() > 32767) {
                    dto.setLRequestparameter(info.getLRequestparameter().substring(0, 32767));
                }
                return dto;
            }).collect(Collectors.toList());

             List<SysInterface> all = userFeign.getAll();
            truncatedRecords.forEach(d -> {
                if (ValidateUtil.isNotEmpty(d.getLFunModule()) && ValidateUtil.isNotNull(list)) {
                    Optional<VuePowerTreeVo> powerOptional = findPowerByCodeInList(d.getLFunModule(), list);
                    powerOptional.ifPresent(p -> d.setPowerName(p.getPowerName()));
                }
                if(ValidateUtil.isNotNull(all)){
                    Optional<SysInterface> matchingInterface = all.stream()
                            .filter(i -> d.getLUrl().replaceAll("//","/").contains(i.getIfUrl()))
                            .findFirst();
                    matchingInterface.ifPresent(i -> d.setInterfaceName(i.getIfName()));
                }
            });

            ByteArrayOutputStream out = new ByteArrayOutputStream();

            WriteCellStyle headStyle = new WriteCellStyle();
//            headStyle.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());

            WriteFont headFont = new WriteFont();
            headFont.setFontHeightInPoints((short) 12);
            headFont.setFontName("Arial");
            headStyle.setWriteFont(headFont);

            WriteCellStyle contentStyle = new WriteCellStyle();
//            contentStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());

            WriteFont contentFont = new WriteFont();
            contentFont.setFontHeightInPoints((short) 10);
            contentFont.setFontName("Arial");
            contentStyle.setWriteFont(contentFont);

            HorizontalCellStyleStrategy styleStrategy = new HorizontalCellStyleStrategy(headStyle, contentStyle);


            EasyExcel.write(out, SysLoggerInfoDto.class)
                    .registerWriteHandler(styleStrategy)
//                    .registerWriteHandler(new LongestMatchColumnWidthStyleStrategy())
                    .registerWriteHandler(new CustomColumnWidthStyleStrategy())
                    .sheet("SysLoggerInfoDto")
                    .doWrite(truncatedRecords );


            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=logs.xlsx");


            response.getOutputStream().write(out.toByteArray());
            response.getOutputStream().flush();
            response.getOutputStream().close();

            return new ResponseEntity<>(out.toByteArray(), HttpStatus.OK);

        } catch (IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private static Stream<String> getSecondLevelPowerCodes(Object children) {
        if (children == null) {
            return Stream.empty();
        }

        List<VuePowerTreeVo> childrenList = JSONUtil.toList(JSONUtil.parseArray(children), VuePowerTreeVo.class);
        if (childrenList == null || childrenList.isEmpty()) {
            return Stream.empty();
        }

        return childrenList.stream().map(child -> child.getPowerCode());
    }



    private Optional<VuePowerTreeVo> findPowerByCode(String powerCode, VuePowerTreeVo node) {
        if (node.getPowerCode().equals(powerCode)) {
            return Optional.of(node);
        }

        if (node.getChildren() != null) {
            List<VuePowerTreeVo> childrenList = JSONUtil.toList(JSONUtil.parseArray(node.getChildren()), VuePowerTreeVo.class);

            for (VuePowerTreeVo child : childrenList) {
                Optional<VuePowerTreeVo> result = findPowerByCode(powerCode, child);
                if (result.isPresent()) {
                    return result;
                }
            }
        }
        return Optional.empty();
    }


    private Optional<VuePowerTreeVo> findPowerByCodeInList(String powerCode, List<VuePowerTreeVo> list) {
        for (VuePowerTreeVo node : list) {
            Optional<VuePowerTreeVo> result = findPowerByCode(powerCode, node);
            if (result.isPresent()) {
                return result;
            }
        }
        return Optional.empty();
    }


}
