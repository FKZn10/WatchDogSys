package com.jpmsoft.fileUpload.controller;

import com.jpmsoft.fileUpload.pojo.vo.OnlyOfficeCallbackVo;
import com.jpmsoft.fileUpload.service.IOnlyOfficeService;
import com.onlyoffice.model.documenteditor.Callback;
import com.onlyoffice.model.documenteditor.History;
import com.onlyoffice.model.documenteditor.HistoryData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * OnlyOffice对接
 */
@Slf4j
@RestController
@Api(tags = "OnlyOffice相关接口", value = "OnlyOffice相关接口")
@RequiredArgsConstructor
@RequestMapping("/onlyOffice")
public class OnlyOfficeController {
    private final IOnlyOfficeService onlyOfficeService;

    /**
     * OnlyOffice文档编辑
     *
     * @param callback
     * @param oldId
     * @param uId
     * @return
     */
    @ApiOperation("OnlyOffice文档编辑")
    @PostMapping("/uploadReplace")
    public OnlyOfficeCallbackVo uploadReplace(@RequestBody Callback callback,
                                              @RequestParam("oldId") String oldId,
                                              @RequestParam("uId") String uId) {
        log.info("OnlyOffice uploadReplace status={}", callback.getStatus());
        return onlyOfficeService.uploadReplace(callback, oldId, uId);
    }

    /**
     * 返回版本列表（给 onRequestHistory 调用）
     *
     * @param fileId
     * @return
     */
    @ApiOperation("获取历史版本列表")
    @GetMapping("/getHistoryList")
    public History getHistoryList(@RequestParam("fileId") String fileId) {
        log.info("OnlyOffice getHistoryList");
        return onlyOfficeService.getHistoryList(fileId);
    }

    /**
     * 返回某一版本的数据（给 onRequestHistoryData 调用）
     *
     * @param fileId
     * @param version
     * @return
     */
    @ApiOperation("获取历史版本数据")
    @GetMapping("/getHistoryData")
    public HistoryData getHistoryData(@RequestParam("fileId") String fileId, @RequestParam("version") Integer version) {
        log.info("OnlyOffice getHistoryData");
        return onlyOfficeService.getHistoryData(fileId, version);
    }
}
