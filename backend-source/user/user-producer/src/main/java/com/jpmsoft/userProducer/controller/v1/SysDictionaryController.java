package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.annotations.TenantId;
import com.jpmsoft.userProducer.pojo.dto.DictionaryDto;
import com.jpmsoft.userProducer.pojo.dto.SelectDto;
import com.jpmsoft.userProducer.service.ISysDictionaryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@Api(tags = "字典", value = "字典")
@RestController
@RequestMapping("/v1/SystemManage")
public class SysDictionaryController {

    @Resource
    private ISysDictionaryService sysDictionaryService;

    /**
     * 数据字典列表
     *
     * @return
     */
    @ApiOperation(value = "数据字典列表")
    @GetMapping("/getDictionaryTreeList")
    public BaseResponse getDictionaryTreeList(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "dCode", required = false) String dCode,
            @RequestHeader(required = false) String token
    ) {
        log.info("getDictionaryTreeList");
        return sysDictionaryService.getDictionaryTreeList(title, dCode, token);
    }

    /**
     * 数据字典列表
     * parent code筛选
     *
     * @return
     */
    @TenantId
    @ApiOperation(value = "数据字典列表  parent code筛选")
    @PostMapping("/getDictionaryTree")
    public BaseResponse getDictionaryTree(@RequestBody SelectDto request) {
        log.info("getDictionaryTreeList");
        return sysDictionaryService.getDictionaryTree(request);
    }

    /**
     * 新增修改删除字典
     *
     * @param request
     * @return
     */
    @TenantId
    @ApiOperation(value = "新增修改删除字典")
    @PostMapping("/CUDDictionary")
    public BaseResponse CUDDictionary(@RequestBody @Validated DictionaryDto request) {
        log.info("CUDDictionary");
        return sysDictionaryService.CUDDictionary(request);
    }


    @TenantId
    @ApiOperation(value = "获取vue select")
    @PostMapping("/getSelctList")
    public BaseResponse getSelctList(@RequestBody SelectDto request) {
        log.info("getSelctList");
        return sysDictionaryService.getSelctList(request);
    }

    @TenantId
    @ApiOperation(value = "多个code获取数据字典树形")
    @PostMapping("/getDictionaryTreeByCodes")
    public BaseResponse getDictionaryTreeByCodes(@RequestBody SelectDto request) {
        log.info("getDictionaryTreeByCodes");
        return sysDictionaryService.getDictionaryTreeByCodes(request);
    }

    /**
     * 字典节点上下移动
     *
     * @param dId
     * @param action
     * @return
     */
    @ApiOperation(value = "字典节点上下移动")
    @GetMapping("/upOrDownDictionarySort")
    public BaseResponse upOrDownDictionarySort(@RequestParam String dId, @RequestParam String action, @RequestHeader String token) {
        log.info("upOrDownDictionarySort");
        return sysDictionaryService.upOrDownDictionarySort(dId, action, token);
    }

    @ApiOperation("根据code添加字典值（城建投资录入）")
    @PostMapping("/addDictionaryByCodeCj")
    public BaseResponse addDictionaryByCodeCj(@RequestBody DictionaryDto request) {
        log.info("addDictionaryByCodeCj");
        return sysDictionaryService.addDictionaryByCodeCj(request);
    }
}
