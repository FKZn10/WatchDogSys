package com.jpmsoft.projectProducer.controller.v1.contract;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.model.request.Contract.FullProcessConsultServiceProtocolMeterageCreateOrUpdateRequest;
import com.jpmsoft.common.model.request.Contract.FullProcessConsultServiceProtocolMeteragePageRequest;
import com.jpmsoft.projectProducer.pojo.dto.contract.FullProcessConsultServiceProtocolMeterageApplyExportDTO;
import com.jpmsoft.projectProducer.pojo.entity.contract.FullProcessConsultServiceProtocolMeterageApply;
import com.jpmsoft.projectProducer.pojo.vo.contract.FullProcessConsultServiceProtocolMeterageApplyVO;
import com.jpmsoft.projectProducer.pojo.vo.contract.FullProcessConsultServiceProtocolMeterageDetailVo;
import com.jpmsoft.projectProducer.pojo.vo.contract.FullProcessConsultServiceProtocolVo;
import com.jpmsoft.projectProducer.service.contract.IFullProcessConsultServiceProtocolMeterageApplyService;
import com.jpmsoft.projectProducer.utils.ExcelExportMyStyler;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/fullProcessConsultServiceProtocolMeterageApply")
public class FullProcessConsultServiceProtocolMeterageApplyController {

    @Resource
    private IFullProcessConsultServiceProtocolMeterageApplyService fullProcessConsultServiceProtocolMeterageApplyService;


    /**
     * 通过项目id查询服务协议内容及概算金额
     *
     * @param projectId
     * @return
     */
    @GetMapping("/getServiceProtocolContentItemByProjectId")
    private BaseResponse getServiceProtocolContentItemByProjectId(@RequestParam String projectId) {
        if (StrUtil.isBlank(projectId)) {
            return BaseResponse.fail("项目id为空");
        }
        FullProcessConsultServiceProtocolVo fullProcessConsultServiceProtocolVo
                = fullProcessConsultServiceProtocolMeterageApplyService.getServiceProtocolContentItemByProjectId(projectId);
        return BaseResponse.ok(fullProcessConsultServiceProtocolVo);
    }

    /**
     * 分页查询接口
     *
     * @return
     */
    @PostMapping("/page")
    public BaseResponse page(@RequestBody FullProcessConsultServiceProtocolMeteragePageRequest request, @RequestHeader String token) {
        return fullProcessConsultServiceProtocolMeterageApplyService.pageFullProcessConsultServiceProtocolMeterageApply(request, token);
    }

    /**
     * 详情接口
     *
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public BaseResponse getById(String id) {
        FullProcessConsultServiceProtocolMeterageDetailVo fullProcessConsultServiceProtocolMeterageDetailVo
                = fullProcessConsultServiceProtocolMeterageApplyService.getDetailById(id);
        return BaseResponse.ok(fullProcessConsultServiceProtocolMeterageDetailVo);
    }

    /**
     * 创建或更新接口
     *
     * @param request
     * @return
     */
    @PostMapping("/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody FullProcessConsultServiceProtocolMeterageCreateOrUpdateRequest request) {
        FullProcessConsultServiceProtocolMeterageApply entity = fullProcessConsultServiceProtocolMeterageApplyService.createOrUpdate(request);
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 删除接口
     *
     * @param id 主键id
     * @return
     */
    @PostMapping("/deleteById/{id}")
    public BaseResponse deleteById(@PathVariable("id") String id) {
        fullProcessConsultServiceProtocolMeterageApplyService.deleteById(id);
        return BaseResponse.ok();
    }

    /**
     * 导出接口
     *
     * @return
     */
    @PostMapping("/export")
    public void export(@RequestBody FullProcessConsultServiceProtocolMeteragePageRequest request, HttpServletResponse httpServletResponse, @RequestHeader String token) throws IOException {
        List<FullProcessConsultServiceProtocolMeterageApplyVO> list = fullProcessConsultServiceProtocolMeterageApplyService.listFullProcessConsultServiceProtocolMeterageApply(request, token);
        List<FullProcessConsultServiceProtocolMeterageApplyExportDTO> exportDTOList = list.stream().map(e -> {
            FullProcessConsultServiceProtocolMeterageApplyExportDTO exportDTO = new FullProcessConsultServiceProtocolMeterageApplyExportDTO();
            BeanUtil.copyProperties(e, exportDTO);
            return exportDTO;
        }).collect(Collectors.toList());
        ExportParams exportParams = new ExportParams();
        exportParams.setStyle(ExcelExportMyStyler.class);
        Workbook workbook = ExcelExportUtil.exportExcel(exportParams, FullProcessConsultServiceProtocolMeterageApplyExportDTO.class, exportDTOList);
        workbook.write(httpServletResponse.getOutputStream());
        workbook.close();
        httpServletResponse.setContentType("application/octet-stream");
        httpServletResponse.setCharacterEncoding("UTF-8");
        String fileName = System.currentTimeMillis() + "-export.xlsx";
        httpServletResponse.setHeader("Content-disposition", "attachment;filename=" + fileName);
        httpServletResponse.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
    }

    /**
     * 获取合同最新审批通过的计量报审记录
     *
     * @param contractId 合同id
     * @return
     */
    @GetMapping("/getLastFullProcessConsultServiceProtocolMeterageApply")
    public BaseResponse getLastFullProcessConsultServiceProtocolMeterageApply(@RequestParam String contractId) {
        FullProcessConsultServiceProtocolMeterageApply fullProcessConsultServiceProtocolMeterageApply = fullProcessConsultServiceProtocolMeterageApplyService.getOne(new LambdaQueryWrapper<FullProcessConsultServiceProtocolMeterageApply>()
                .eq(FullProcessConsultServiceProtocolMeterageApply::getContractId, contractId)
                .eq(FullProcessConsultServiceProtocolMeterageApply::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .orderByDesc(FullProcessConsultServiceProtocolMeterageApply::getState)
                .last("limit 1"));
        return BaseResponse.ok(fullProcessConsultServiceProtocolMeterageApply);
    }

    @PostMapping("/getTZClassInfo")
    public BaseResponse getTZClassInfo(@RequestBody FullProcessConsultServiceProtocolMeteragePageRequest request, @RequestHeader String token) throws ClassNotFoundException, IllegalAccessException {
        return fullProcessConsultServiceProtocolMeterageApplyService.getTZClassInfo(request.getProjectIds(), token);
    }


    @GetMapping("/getLastDeadline")
    public BaseResponse getLastDeadline(@RequestParam String pId, @RequestParam String conId) {
        return fullProcessConsultServiceProtocolMeterageApplyService.getLastDeadline(pId, conId);
    }

}
