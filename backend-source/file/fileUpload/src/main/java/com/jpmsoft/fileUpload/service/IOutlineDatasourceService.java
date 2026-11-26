package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.entity.OutlineDatasource;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface IOutlineDatasourceService extends IService<OutlineDatasource> {
    /**
     * 导出大纲数据源配置
     *
     * @param response
     * @param ids
     */
    void exportOutlineDatasourceByIds(HttpServletResponse response, List<String> ids);

    /**
     * 导入大纲数据源配置
     *
     * @param file
     * @param token
     * @return
     */
    BaseResponse importOutlineDatasource(MultipartFile file, String token);
}
