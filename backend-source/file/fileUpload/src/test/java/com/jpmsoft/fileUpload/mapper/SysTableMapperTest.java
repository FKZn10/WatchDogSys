package com.jpmsoft.fileUpload.mapper;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.WorkbookUtil;
import com.jpmsoft.fileUpload.FileUploadApplication;
import com.jpmsoft.fileUpload.pojo.entity.SysDictionary;
import com.jpmsoft.fileUpload.pojo.entity.SysFileTemplate;
import com.jpmsoft.fileUpload.pojo.entity.SysTable;
import com.jpmsoft.fileUpload.pojo.entity.SysTableColumn;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

//@SpringBootTest(classes = FileUploadApplication.class)
class SysTableMapperTest {

    @Resource
    SysTableMapper sysTableMapper;


    @Resource
    SysTableColumnMapper sysTableColumnMapper;

    @Resource
    SysFileTemplateMapper sysFileTemplateMapper;

    @Resource
    SysDictionaryMapper sysDictionaryMapper;

//    @Test
    public void generateSysFileTemplateJson() throws IOException {
        List<SysFileTemplate> fileTemplateList = sysFileTemplateMapper.selectList(null);
        String jsonStr = JSONUtil.toJsonStr(fileTemplateList);
        File file = new File("./sys_filetemplate.json");
        FileUtil.writeLines(Collections.singletonList(jsonStr),file, StandardCharsets.UTF_8);
    }

//    @Test
    public void generateSysTableJson() throws IOException {
        List<SysTable> sysTableList = sysTableMapper.selectList(null);
        String jsonStr = JSONUtil.toJsonStr(sysTableList);
        File file = new File("./sys_table.json");
        FileUtil.writeLines(Collections.singletonList(jsonStr),file, StandardCharsets.UTF_8);
    }

//    @Test
    public void generateSysTableColumnJson() throws IOException {
        List<SysTableColumn> sysTableColumnList = sysTableColumnMapper.selectList(null);
        String jsonStr = JSONUtil.toJsonStr(sysTableColumnList);
        File file = new File("./sys_tablecolumn.json");
        FileUtil.writeLines(Collections.singletonList(jsonStr),file, StandardCharsets.UTF_8);
    }

//    @Test
    public void generateSysDictionaryJson() throws IOException {
        List<SysDictionary> sysDictionaryList = sysDictionaryMapper.selectList(null);
        String jsonStr = JSONUtil.toJsonStr(sysDictionaryList);
        File file = new File("./sys_dictionary.json");
        FileUtil.writeLines(Collections.singletonList(jsonStr),file, StandardCharsets.UTF_8);
    }

}
