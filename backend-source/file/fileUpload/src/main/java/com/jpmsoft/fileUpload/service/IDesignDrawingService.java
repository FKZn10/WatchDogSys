package com.jpmsoft.fileUpload.service;

import com.jpmsoft.common.model.requestAndResponse.Design.DesignDrawingModel;

import javax.servlet.http.HttpServletResponse;

public interface IDesignDrawingService {
    void exportDesignDrawing(HttpServletResponse response, DesignDrawingModel request, String token);
}
