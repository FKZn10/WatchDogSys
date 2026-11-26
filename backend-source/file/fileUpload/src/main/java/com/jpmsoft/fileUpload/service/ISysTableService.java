package com.jpmsoft.fileUpload.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.pojo.entity.SysTable;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.xml.bind.JAXBException;
import java.util.List;

/**
 * @Author: 李文涛 aaron
 * @date: 2024/1/22 11:27
 */
public interface ISysTableService extends IService<SysTable> {

    void exportTableXml(HttpServletResponse response, List<String> tableCodes);

    BaseResponse importTableXml(MultipartFile file) throws JAXBException;
}
