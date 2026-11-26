package com.jpmsoft.projectConsumer.controller.v1.conBoq;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConBoqBudgetDto;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConBoqDtlDto;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConBoqDtlMeasureDTO;
import com.jpmsoft.projectConsumer.pojo.dto.contract.ConBoqDto;
import com.jpmsoft.projectConsumer.feign.ConBoqFeign;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "合同工程量清单", value = "合同工程量清单")
@RequestMapping("/v1/conbop")
public class ConBoqController {

    @Autowired
    private ConBoqFeign conBoqFeign;


    //    @Autowired
//    private ExportExcelServiceImpl exportExcelService;
    private static final String[] INFO_EXPORT_ROWNAME = new String[]{"项目编码", "项目名称", "项目特征", "工程内容", "计量单位", "工程量", "综合单价(元)", "总价(元)", " 备注", " 概算"};

    /**
     * 首页查询
     */
    @PostMapping("/getBoqList")
    public BaseResponse getBoqList(@RequestBody Page page) {
        try {
            log.info("getBoqList");
            return conBoqFeign.getBoqList(page);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }

    /**
     * 新建
     */
    @PostMapping("/createBoq")
    public BaseResponse createBoq(@RequestBody RequestBean requestBean) {
        try {
            log.info("createBoq");
            return conBoqFeign.createBoq(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }
    }

    /**
     * 工程清单查看
     */
    @PostMapping("/getBoqById")
    public BaseResponse getBoqById(@RequestBody RequestBean requestBean) {
        try {
            log.info("getBoqById");
            return conBoqFeign.getBoqById(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }
    }

    /**
     * 工程清单修改
     */
    @PostMapping("/updateBop")
    public BaseResponse updateBop(@RequestBody RequestBean requestBean) {
        try {
            log.info("updateBop");
            return conBoqFeign.updateBop(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }

    /**
     * 保存
     */
    @PostMapping("/saveBoqDtl")
    public BaseResponse saveBoqDtl(@RequestBody RequestBean requestBean) {
        try {
            log.info("saveBoqDtl");
            return conBoqFeign.saveBoqDtl(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }

    /**
     * 增删改
     */
    @PostMapping("/createOrUpdateOrdeleteConBoqDtl")
    public BaseResponse createOrUpdateOrdeleteConBoqDtl(@RequestBody RequestBean requestBean) {
        try {
            log.info("createOrUpdateOrdeleteConBoqDtl");
            return conBoqFeign.createOrUpdateOrdeleteConBoqDtl(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }

    /**
     * 清单审核
     */
    @PostMapping("/updateBoqState")
    public BaseResponse updateBoqState(@RequestBody RequestBean requestBean) {
        try {
            log.info("updateBoqState");
            return conBoqFeign.updateBoqState(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }

    /**
     * 监管审核
     */
    @PostMapping("/updateSnState")
    public BaseResponse updateSnState(@RequestBody RequestBean requestBean) {
        try {
            log.info("updateSnState");
            return conBoqFeign.updateSnState(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }

    /**
     *
     */
    @PostMapping("/deleteConBoqDtlTemp")
    public BaseResponse deleteConBoqDtlTemp(@RequestBody RequestBean requestBean) {
        try {
            log.info("deleteConBoqDtlTemp");
            return conBoqFeign.deleteConBoqDtlTemp(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }

    @PostMapping("/getConBoqDtlTempList")
    public BaseResponse getConBoqDtlTempList(@RequestBody RequestBean requestBean) {
        try {
            log.info("getConBoqDtlTempList");
            return conBoqFeign.getConBoqDtlTempList(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }
    }

    @ApiOperation(value = "合同ID+清单号工程量清单")
    @GetMapping("/getConBoqDtlByConIdAndCode")
    public BaseResponse getConBoqDtlByConIdAndCode(@RequestParam String conId, @RequestParam String boqdCode) {
        log.info("getConBoqDtlByConIdAndCode");
        return conBoqFeign.getConBoqDtlByConIdAndCode(conId, boqdCode);
    }

    @ApiOperation(value = "合同ID查询工程量清单详情树")
    @GetMapping("/getConBoqDtlTreeByConId")
    public BaseResponse getConBoqDtlTreeByConId(@RequestParam String conId,@RequestParam(required = false,name = "boqId") String boqId,@RequestParam(required = false,name = "parentId") String parentId) {
        log.info("getConBoqDtlTreeByConId");
        return conBoqFeign.getConBoqDtlTreeByConId(conId,boqId,parentId);
    }

    @ApiOperation(value = "清单ID查询其上级清单详情")
    @PostMapping("/getConBoqDtlParentByIds")
    public BaseResponse getConBoqDtlParentByIds(@RequestBody List<String> boqdIds) {
        log.info("getConBoqDtlParentByIds");
        return conBoqFeign.getConBoqDtlParentByIds(boqdIds);
    }

    @ApiOperation(value = "概算清单ID分页查询所有子项关联工程量清单")
    @PostMapping("/getConBoqDtlBudgetList")
    public BaseResponse getConBoqDtlBudgetList(@RequestBody ConBoqBudgetDto request) {
        log.info("getConBoqDtlBudgetList");
        return conBoqFeign.getConBoqDtlBudgetList(request);
    }

    @ApiOperation(value = "查询合同工程量清单树（可区分分部分项清单类型，或者关联查询计量报审下计量单信息）")
    @PostMapping("/getConBoqDtlAndMeasureTree")
    public BaseResponse getConBoqDtlAndMeasureTree(@RequestBody ConBoqDtlMeasureDTO request) {
        log.info("getConBoqDtlAndMeasureTree");
        return conBoqFeign.getConBoqDtlAndMeasureTree(request);
    }

    @ApiOperation(value = "根据上级清单ID获取下级清单列表")
    @GetMapping("/getConBoqDtlByBoqIdAndParent")
    public BaseResponse getConBoqDtlByBoqIdAndParent(@RequestParam String boqId, @RequestParam String parentId)
    {
        log.info("getConBoqDtlByBoqIdAndParent");
        return conBoqFeign.getConBoqDtlByBoqIdAndParent(boqId,parentId);
    }

    @PostMapping("/importExcel")
    public BaseResponse importExcel(@RequestBody RequestBean requestBean) {
        try {
            log.info("importExcel");
            return conBoqFeign.importExcel(requestBean);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            return new BaseResponse("服务内部错误！");
        }

    }

//    @PostMapping(value = "/exportExcel")
//    public BaseResponse exportExcel(HttpServletResponse response) {
//        try {
//            log.info("exportExcel");
//            ExportExcel ee = new ExportExcel();
//            String title = "导入清单模板";
//            ee.setTitle(title);
//            ee.setRowName(INFO_EXPORT_ROWNAME);
//            exportExcelService.export(response, ee);
//            return new BaseResponse();
//        } catch (Exception ex) {
//            log.error(ex.getMessage());
//            return new BaseResponse("服务内部错误！");
//        }
//
//    }


    @ApiOperation(value = "新增修改合同工程量清单")
    @PostMapping("/createOrUpdateConBoq")
    public BaseResponse createOrUpdateConBoq(@RequestBody ConBoqDto request) {
        log.info("createOrUpdateConBoq");
        return conBoqFeign.createOrUpdateConBoq(request);
    }

    @ApiOperation(value = "分页查询合同工程量清单")
    @PostMapping("/getConBoqList")
    public BaseResponse getConBoqList(@RequestBody ConBoqDto request, @RequestHeader String token) {
        log.info("getConBoqList");
        return conBoqFeign.getConBoqList(request, token);
    }

    @ApiOperation(value = "ID查询合同工程量清单")
    @GetMapping("/getConBoqById")
    public BaseResponse getConBoqById(@RequestParam String boqId,@RequestParam(required = false) Boolean queryDtl) {
        log.info("getConBoqById");
        return conBoqFeign.getConBoqById(boqId,queryDtl);
    }

    @ApiOperation(value = "新增清单单条")
    @PostMapping("/createOne")
    public   BaseResponse createOne(@RequestBody ConBoqDtlDto request) {
        log.info("createOne");
        return conBoqFeign.createOne(request);
    }

    @ApiOperation(value = "更新清单单条")
    @PostMapping("/updateOne")
    public   BaseResponse updateOne(@RequestBody ConBoqDtlDto request) {
        log.info("updateOne");
        return conBoqFeign.updateOne(request);
    }

    @ApiOperation(value = "删除清单单条")
    @GetMapping("/deleteOne")
    public BaseResponse deleteOne(@RequestParam String boqdId) {
        log.info("deleteOne");
        return conBoqFeign.deleteOne(boqdId);
    }
    @ApiOperation(value = "批量删除清单")
    @PostMapping("/deleteBath")
    public BaseResponse deleteBath(@RequestBody List<String> ids)
    {
        log.info("deleteBath");
        return conBoqFeign.deleteBath(ids);
    }
}
