package com.jpmsoft.fileUpload.service;

import com.jpmsoft.common.base.BaseResponse;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

public interface IOutlineProjectService {
    void exportOutlineProjectWord(HttpServletResponse response, String opmId, String export, String token);

    BaseResponse importOutlineProjectWord(MultipartFile file, String osmId, String token);
}
