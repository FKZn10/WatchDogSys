package com.jpmsoft.projectProducer.controller.v1.qualitySafety;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.requestAndResponse.Qs.QsTypeModel;
import com.jpmsoft.projectProducer.annotations.TenantId;
import com.jpmsoft.projectProducer.pojo.request.qs.QsTypeRecordRequest;
import com.jpmsoft.projectProducer.service.qualitySafety.IQsTypeService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

@Slf4j
@RestController
@RequestMapping("/v1/qsType")
public class QsTypeController {
    @Resource
    private IQsTypeService qsTypeService;

    @Resource
    private RedisUtils redisUtils;

    /**
     * 新增或修改
     *
     * @param request
     * @return
     */
    @TenantId
    @PostMapping("/createOrUpdateQsType")
    public BaseResponse createOrUpdateQsType(@RequestBody QsTypeModel request) {
        log.info("createOrUpdateQsType");
        return qsTypeService.createOrUpdateQsType(request);
    }

    /**
     * 获取组内和当前修改类别相同的其他类别（只比对文字部分）
     * @param request
     * @return
     */
    @PostMapping("/getGroupSameType")
    public BaseResponse getGroupSameType(@RequestBody QsTypeModel request) {
        return qsTypeService.getGroupSameType(request);
    }

    /**
     * ID删除
     *
     * @param qstId
     * @return
     */
    @GetMapping("/deleteQsTypeById")
    public BaseResponse deleteQsTypeById(@RequestParam String qstId) {
        log.info("deleteQsTypeById");
        return qsTypeService.deleteQsTypeById(qstId);
    }

    /**
     * 获取List集合
     *
     * @return
     */
    @TenantId
    @PostMapping("/getQsTypeTreeList")
    public BaseResponse getQsTypeTreeList(@RequestBody QsTypeModel request, @RequestHeader String token) {
        log.info("getQsTypeTreeList");
        return qsTypeService.getQsTypeTreeList(request, redisUtils.getUid(token));
    }

    /**
     * 获取List集合
     *
     * @return
     */
    @TenantId
    @PostMapping("/getQsTypeList")
    public BaseResponse getQsTypeList(@RequestBody QsTypeModel request, @RequestHeader String token) {
        log.info("getQsTypeList");
        return qsTypeService.getQsTypeList(request, redisUtils.getUid(token));
    }

    /**
     * 获取类别详情
     * @param qstId
     * @return
     */
    @GetMapping("/getQsTypeById")
    public BaseResponse getQsTypeById(@RequestParam String qstId) {
        log.info("getQsTypeById");
        return qsTypeService.getQsTypeById(qstId);
    }

    /**
     * 上移或者下移
     *
     * @param qstId
     * @param action
     * @return
     */
    @GetMapping("/upOrDownQsType")
    public BaseResponse upOrDownQsType(@RequestParam String qstId, @RequestParam String action,@RequestHeader String token) {
        log.info("upOrDownQsType");
        return qsTypeService.upOrDownQsType(qstId, action,redisUtils.getTeId(token));
    }

    @GetMapping("/getQsTypeByParentId")
    public BaseResponse getQsTypeByParentId(@RequestParam String parentId) {
        log.info("getQsTypeByParentId");
        return qsTypeService.getQsTypeByParentId(parentId);
    }

    @PostMapping("/getQsTypeTree")
    public BaseResponse getQsTypeTree(@RequestBody QsTypeModel request) {
        log.info("getQsTypeTree");
        return qsTypeService.getQsTypeTree(request);
    }

    @PostMapping("/getQsTypeInfo")
    public BaseResponse getQsTypeInfo(@RequestBody QsTypeRecordRequest request) {
        log.info("getQsTypeInfo");
        return qsTypeService.getQsTypeInfo(request);
    }
    @GetMapping("/generateQsTypePath")
    public BaseResponse generateQsTypePath() {
        log.info("generateQsTypePath");
        return qsTypeService.generateQsTypePath();
    }

    /**
     * 导出
     */
    @PostMapping("/exportQsType")
    public void exportQsType(HttpServletResponse response,
                                     @RequestBody QsTypeModel request,
                                     @RequestHeader String token) {
        try {
            Workbook workbook = qsTypeService.exportQsType(request, redisUtils.getUid(token));
            String fileName = URLEncoder.encode("质安问题类别.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            //编码
            response.setCharacterEncoding("UTF-8");
            //多sheet
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            log.error("质安问题类别EXCEL导出出错{}", e.getMessage(),e);
        }
    }

    @PostMapping("/exportQsTypeAll")
    public void exportQsTypeAll(HttpServletResponse response,
                             @RequestBody QsTypeModel request,
                             @RequestHeader String token) {
        try {
            Workbook workbook = qsTypeService.exportQsTypeAll(request, redisUtils.getUid(token));
            String fileName = URLEncoder.encode("质安问题类别.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            //编码
            response.setCharacterEncoding("UTF-8");
            //多sheet
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            log.error("质安问题类别EXCEL导出出错{}", e.getMessage(),e);
        }
    }

    @PostMapping("/exportQsTypeInfo")
    public void exportQsTypeInfo(HttpServletResponse response,
                             @RequestBody QsTypeRecordRequest request,
                             @RequestHeader String token) {
        try {
            Workbook workbook = qsTypeService.exportQsTypeInfo(request, redisUtils.getUid(token));
            String fileName = URLEncoder.encode("质安问题类别.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            //编码
            response.setCharacterEncoding("UTF-8");
            //多sheet
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            log.error("质安问题类别EXCEL导出出错{}", e.getMessage(),e);
        }
    }

    @PostMapping("/exportQsTypeInfoAll")
    public void exportQsTypeInfoAll(HttpServletResponse response,
                                @RequestBody QsTypeRecordRequest request,
                                @RequestHeader String token) {
        try {
            Workbook workbook = qsTypeService.exportQsTypeInfoAll(request, redisUtils.getUid(token));
            String fileName = URLEncoder.encode("质安问题类别.xls", "UTF-8");
            // 告诉浏览器用什么软件可以打开此文件
            response.setHeader("content-Type", "application/vnd.ms-excel");
            // 下载文件的默认名称
            response.setHeader("Content-Disposition", fileName);
            //编码
            response.setCharacterEncoding("UTF-8");
            //多sheet
            ServletOutputStream outputStream = response.getOutputStream();
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();
        } catch (IOException e) {
            log.error("质安问题类别EXCEL导出出错{}", e.getMessage(),e);
        }
    }

}
