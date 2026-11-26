package com.jpmsoft.projectConsumer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsTypeModel;
import com.jpmsoft.projectConsumer.feign.BaseFeign;
import com.jpmsoft.projectConsumer.request.qs.QsTypeRecordRequest;
import feign.Response;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;


@Slf4j
@RestController
@Api(tags = "质量安全类别", value = "质量安全类别")
@RequestMapping("/safety/v1/qsType")
public class QsTypeController {
    @Autowired
    private BaseFeign baseFeign;

    /**
     * 新增或修改
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "创建或修改质量安全类别")
    @PostMapping("/createOrUpdateQsType")
    public BaseResponse createOrUpdateQsType(@RequestBody QsTypeModel request) {
        log.info("createOrUpdateQsType");
        return baseFeign.createOrUpdateQsType(request);
    }



    @ApiOperation(value = "获取组内和当前修改类别相同的其他类别")
    @PostMapping("/getGroupSameType")
    public BaseResponse getGroupSameType(@RequestBody QsTypeModel request) {
        log.info("getGroupSameType");
        return baseFeign.getGroupSameType(request);
    }

    /**
     * ID删除
     *
     * @param qstId
     * @return
     */
    @ApiOperation(value = "ID删除质量安全类别")
    @GetMapping("/deleteQsTypeById")
    public BaseResponse deleteQsTypeById(@RequestParam String qstId) {
        log.info("deleteQsTypeById");
        return baseFeign.deleteQsTypeById(qstId);
    }

    /**
     * 获取List集合
     *
     * @return
     */
    @ApiOperation(value = "获取质量安全类别树形集合")
    @PostMapping("/getQsTypeTreeList")
    public BaseResponse getQsTypeTreeList(@RequestBody QsTypeModel request, @RequestHeader String token) {
        log.info("getQsTypeTreeList");
        return baseFeign.getQsTypeTreeList(request, token);
    }

    /**
     * 获取List集合
     *
     * @return
     */
    @ApiOperation(value = "获取质量安全类别集合")
    @PostMapping("/getQsTypeList")
    public BaseResponse getQsTypeList(@RequestBody QsTypeModel request, @RequestHeader String token) {
        log.info("getQsTypeList");
        return baseFeign.getQsTypeList(request, token);
    }

    /**
     * 获取类别详情
     * @param qstId
     * @return
     */
    @ApiOperation(value = "获取质量安全类别详情")
    @GetMapping("/getQsTypeById")
    public BaseResponse getQsTypeById(@RequestParam String qstId) {
        log.info("getQsTypeById");
        return baseFeign.getQsTypeById(qstId);
    }
    @PostMapping("/getQsTypeInfo")
    public BaseResponse getQsTypeInfo(@RequestBody QsTypeRecordRequest request) {
        log.info("getQsTypeInfo");
        return baseFeign.getQsTypeInfo(request);
    }
    /**
     * 上移或者下移
     *
     * @param qstId
     * @param action
     * @return
     */
    @ApiOperation(value = "上移或者下移质量安全类别")
    @GetMapping("/upOrDownQsType")
    public BaseResponse upOrDownQsType(@RequestParam String qstId, @RequestParam String action) {
        log.info("upOrDownQsType");
        return baseFeign.upOrDownQsType(qstId, action);
    }

    @ApiOperation(value = "父ID查询集合")
    @GetMapping("/getQsTypeByParentId")
    public BaseResponse getQsTypeByParentId(@RequestParam String parentId) {
        log.info("getQsTypeByParentId");
        return baseFeign.getQsTypeByParentId(parentId);
    }

    @ApiOperation(value = "质量安全类别树")
    @PostMapping("/getQsTypeTree")
    public BaseResponse getQsTypeTree(@RequestBody QsTypeModel request) {
        log.info("getQsTypeTree");
        return baseFeign.getQsTypeTree(request);
    }

    @ApiOperation(value = "生成path")
    @GetMapping("/generateQsTypePath")
    public BaseResponse generateQsTypePath() {
        log.info("generateQsTypePath");
        return baseFeign.generateQsTypePath();
    }

    @ApiOperation(value = "导出质安问题类别")
    @PostMapping("/exportQsType")
    public void exportQsType(HttpServletResponse response, @RequestBody QsTypeModel request, @RequestHeader String token) {
        log.info("exportQsType");
        Response feignResponse = baseFeign.exportQsType(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
            OutputStream outputStream = response.getOutputStream()) {
            String fileName = URLEncoder.encode("质安问题类别.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            //编码
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("exportQsType  error");
        }
    }
    @ApiOperation(value = "导出质安问题类别")
    @PostMapping("/exportQsTypeAll")
    public void exportQsTypeAll(HttpServletResponse response, @RequestBody QsTypeModel request, @RequestHeader String token) {
        log.info("exportQsTypeAll");
        Response feignResponse = baseFeign.exportQsTypeAll(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
            OutputStream outputStream = response.getOutputStream()) {
            String fileName = URLEncoder.encode("质安问题类别.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            //编码
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("exportQsType  error");
        }
    }

    @ApiOperation(value = "导出质安问题类别")
    @PostMapping("/exportQsTypeInfo")
    public void exportQsTypeInfo(HttpServletResponse response, @RequestBody QsTypeRecordRequest request, @RequestHeader String token) {
        log.info("exportQsTypeInfo");
        Response feignResponse = baseFeign.exportQsTypeInfo(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            String fileName = URLEncoder.encode("质安问题类别.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            //编码
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("exportQsType  error");
        }
    }
    @ApiOperation(value = "导出质安问题类别")
    @PostMapping("/exportQsTypeInfoAll")
    public void exportQsTypeInfoAll(HttpServletResponse response, @RequestBody QsTypeRecordRequest request, @RequestHeader String token) {
        log.info("exportQsTypeInfoAll");
        Response feignResponse = baseFeign.exportQsTypeInfoAll(request, token);
        Response.Body body = feignResponse.body();
        try (InputStream inputStream = body.asInputStream();
             OutputStream outputStream = response.getOutputStream()) {
            String fileName = URLEncoder.encode("质安问题类别.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            //编码
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            log.error("exportQsType  error");
        }
    }
}
