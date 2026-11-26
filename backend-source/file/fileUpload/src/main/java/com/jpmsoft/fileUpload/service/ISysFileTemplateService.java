package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.fileUpload.pojo.dto.FileTemplateTableAndPrimaryDto;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.SysFileTemplateDto;
import com.jpmsoft.fileUpload.pojo.entity.SysFileTemplate;

import java.util.List;

public interface ISysFileTemplateService extends IService<SysFileTemplate> {


    List<SysFileTemplate> getByCodes(List<String> codes);

    /**
     * toCode获取文档模板中配置的表明和对应主键名
     *
     * @param tCodes
     * @return
     */
    List<FileTemplateTableAndPrimaryDto> getFileTemplateTableAndPrimaryByPid(List<String> tCodes);


    /**
     * 过滤返回招标的tcode
     *
     * @param codes
     * @return
     */
    List<String> filterSecTcode(List<String> codes);


    /**
     * code获取数据库表名列表
     *
     * @param codes
     * @return
     */
    List<String> getTablesByCodes(List<String> codes);

    List<SysFileTemplateDto> getAllFileTemp();

    SysFileTemplate getByCode(String tCode);
}
