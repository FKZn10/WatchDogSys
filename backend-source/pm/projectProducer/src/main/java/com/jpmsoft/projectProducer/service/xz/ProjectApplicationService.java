package com.jpmsoft.projectProducer.service.xz;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.model.request.xz.ProjectApplicationCreateRequest;
import com.jpmsoft.common.model.request.xz.ProjectApplicationQueryRequest;
import com.jpmsoft.common.model.response.xz.ProjectApplicationVO;
import com.jpmsoft.projectProducer.config.PageResult;
import com.jpmsoft.projectProducer.pojo.entity.xz.ProjectApplication;
import org.apache.poi.ss.usermodel.Workbook;

import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2024/8/29 14:11
 * @Description:
 * @Version: 1.0
 */
public interface ProjectApplicationService extends IService<ProjectApplication> {

    PageResult<ProjectApplicationVO> page(ProjectApplicationQueryRequest request);

    Workbook exportExcel(ProjectApplicationQueryRequest request);

    String create(ProjectApplicationCreateRequest request);

    String update(ProjectApplicationCreateRequest request);

    ProjectApplicationVO detail(ProjectApplicationCreateRequest request);

    List<ProjectApplicationVO> listProjectApplicationByIds(List<String> ids);

    void syncApproveState(ProjectApplicationCreateRequest request);

    void taskStatus(ProjectApplicationCreateRequest request);

    void remove(ProjectApplicationCreateRequest request, String token);
}
