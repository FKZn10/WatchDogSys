package com.jpmsoft.projectConsumer.controller.v1.constructionProcedure;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.AchievementsRequest;
import com.jpmsoft.common.model.request.BatchUpdatePerson;
import com.jpmsoft.common.model.request.TimeFrameRequest;
import com.jpmsoft.common.model.request.proCp.ProCpDtlTemporaryRequest;
import com.jpmsoft.common.model.request.proCp.ProjectCpDtlRequest;
import com.jpmsoft.projectConsumer.feign.CpFeign;
import com.jpmsoft.projectConsumer.pojo.ProjectCpDtl;
import com.jpmsoft.projectConsumer.pojo.dto.construction.LibraryFileTypeDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@Api(tags = "项目建设程序详情", value = "项目建设程序详情")
@RequestMapping("/v1/ConstructionProcedure/proCpDtl")
public class ProjectCpDtlController {
    @Autowired
    private CpFeign cpFeign;


    /**
     * 根据项目建设程序ID获取树形数据
     *
     * @param cpId
     * @return
     */
    @ApiOperation(value = "根据项目建设程序ID获取树形数据")
    @GetMapping("/getProCpDtlTreeByCpId")
    public BaseResponse getProCpDtlTreeByCpId(@RequestParam String cpId) {
        log.info("getProCpDtlTreeByCpId");
        return cpFeign.getProCpDtlTreeByCpId(cpId);
    }

    /**
     * 城建 根据项目建设程序ID获取数据
     *
     * @param cpId
     * @return
     */
    @ApiOperation(value = "城建 根据项目建设程序ID获取数据")
    @GetMapping("/getProCpDtlTreeByCpIdNJCJ")
    public BaseResponse getProCpDtlTreeByCpIdNJCJ(@RequestParam String cpId) {
        log.info("getProCpDtlTreeByCpIdNJCJ");
        return cpFeign.getProCpDtlTreeByCpIdNJCJ(cpId);
    }

    /**
     * 提交办理结果
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "提交办理结果", httpMethod = "POST")
    @PostMapping("/submitProcessingResults")
    public BaseResponse submitProcessingResults(@RequestBody ProjectCpDtlRequest request) {
        log.info("submitProcessingResults");
        return cpFeign.submitProcessingResults(request);
    }

    @ApiOperation(value = "判断是否已经存在File表 用于识别当前成果上传是新增操作还是修改操作")
    @GetMapping("/proCpDtlHaveSysFile")
    public BaseResponse proCpDtlHaveSysFile(@RequestParam String cpdId) {
        log.info("proCpDtlHaveSysFile");
        return cpFeign.proCpDtlHaveSysFile(cpdId);
    }

    @ApiOperation(value = "根据项目建设程序ID获取ID集合")
    @GetMapping("/getProCpDtlIdListByCpId")
    public BaseResponse getProCpDtlIdListByCpId(@RequestParam String cpId) {
        log.info("getProCpDtlIdListByCpId");
        return cpFeign.getProCpDtlIdListByCpId(cpId);
    }


    /**
     * 根据项目获取其已勾选的程序
     * ID列表
     *
     * @param pId
     * @return
     */
    @ApiOperation(value = "根据项目获取其已勾选的程序ID列表")
    @GetMapping("/getProCpDtlIdListByPid")
    public BaseResponse getProCpDtlIdListByPid(@RequestParam String pId) {
        log.info("getProCpDtlIdListByPid");
        return cpFeign.getProCpDtlIdListByPid(pId);
    }

    @ApiOperation(value = "统计->项目建设程序ID获取集合 statisticalType 1：手续总数 2：已完成 3：未完成 4：超时未完成")
    @PostMapping("/getProCpDtlListByCpId")
    public BaseResponse getProCpDtlListByCpId(@RequestBody ProjectCpDtlRequest request) {
        log.info("getProCpDtlListByCpId");
        return cpFeign.getProCpDtlListByCpId(request);
    }

    @ApiOperation(value = "queryType=1 调用getProCpDtlByUser queryType=2调用getProCpDtlListByCpId")
    @PostMapping("/getProCpDtlListByStatistics")
    public BaseResponse getProCpDtlListByStatistics(@RequestBody ProjectCpDtlRequest request) {
        log.info("getProCpDtlListByStatistics");
        return cpFeign.getProCpDtlListByStatistics(request);
    }

    /**
     * 办理统计
     * 所有手续
     * 已完成 未完成
     * 超时未完成
     * 分页查询
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "统计->用户ID获取集合 statisticalType 1：手续总数 2：已完成 3：未完成 4：超时未完成")
    @PostMapping("/getProCpDtlByUser")
    public BaseResponse getProCpDtlByUser(@RequestBody ProjectCpDtlRequest request) {
        log.info("getProCpDtlByUser");
        return cpFeign.getProCpDtlByUser(request);
    }

    /**
     * 项目ID获取建设项目详情
     *
     * @param pId
     * @return
     */
    @ApiOperation(value = "项目ID获取建设项目详情")
    @GetMapping("/getProCpDtlByPid")
    public BaseResponse getProCpDtlByPid(@RequestParam String pId) {
        log.info("getProCpDtlByPid");
        return cpFeign.getProCpDtlByPid(pId);
    }


    /**
     * 项目ID获取建设项目详情 带文件信息
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "项目ID获取建设项目详情 树结构 带文件信息")
    @PostMapping("/getProCpDtlTreeByPid")
    public BaseResponse getProCpDtlTreeByPid(@RequestBody ProjectCpDtlRequest request) {
        log.info("getProCpDtlTreeByPid");
        return cpFeign.getProCpDtlTreeByPid(request);
    }


    /**
     * ID删除建设项目详情
     *
     * @param cpDtlId
     * @return
     */
    @ApiOperation(value = "ID删除建设项目详情")
    @GetMapping("/deleteProCpDtlByCpDtlId")
    public BaseResponse deleteProCpDtlByCpDtlId(@RequestParam String cpDtlId) {
        log.info("deleteProCpDtlByCpDtlId");
        return cpFeign.deleteProCpDtlByCpDtlId(cpDtlId);
    }

    /**
     * 修改单条信息
     *
     * @return
     */
    @ApiOperation(value = "修改单条建设项目详情")
    @PostMapping("/updateProCpDtlByCpDtlId")
    public BaseResponse updateProCpDtlByCpDtlId(@RequestBody ProjectCpDtlRequest request) {
        log.info("updateProCpDtlByCpDtlId");
        return cpFeign.updateProCpDtlByCpDtlId(request);
    }

    /**
     * 获取成果文件列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取成果文件列表")
    @PostMapping("/getAchievementsList")
    public BaseResponse getAchievementsList(@RequestBody AchievementsRequest request) {
        log.info("getAchievementsList");
        return cpFeign.getAchievementsList(request);
    }

    /**
     * 项目程序与详情数量统计
     *
     * @return
     */
    @ApiOperation(value = "项目程序与详情数量统计")
    @PostMapping("/statisticalProCpAndDtl")
    public BaseResponse statisticalProCpAndDtl(@RequestBody(required = false) TimeFrameRequest request) {
        log.info("statisticalProCpAndDtl");
        return cpFeign.statisticalProCpAndDtl(request);
    }

    @ApiOperation(value = "时间范围统计预计完成和已完成程序数量")
    @PostMapping("/statisticalProCpDtlByTimeFrame")
    public BaseResponse statisticalProCpDtlByTimeFrame(@RequestBody TimeFrameRequest request) {
        log.info("statisticalProCpAndDtl");
        return cpFeign.statisticalProCpDtlByTimeFrame(request);
    }

    /**
     * 项目建设程序完成情况列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "项目建设程序完成情况列表")
    @PostMapping("/getProCpDtlComplateList")
    public BaseResponse getProCpDtlComplateList(@RequestBody ProjectCpDtlRequest request) {
        log.info("getProCpDtlComplateList");
        return cpFeign.getProCpDtlComplateList(request);
    }

    /**
     * 项目建设程序临期情况列表
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "项目建设程序临期情况列表")
    @PostMapping("/getProCpDtlTemporaryListByuid")
    public BaseResponse getProCpDtlTemporaryListByuid(@RequestBody ProCpDtlTemporaryRequest request, @RequestHeader String token) {
        log.info("getProCpDtlTemporaryListByuid");
        return cpFeign.getProCpDtlTemporaryListByuid(request, token);
    }

    @ApiOperation(value = "批量修改责任人")
    @PostMapping("/batchUpdateCpDtlPerson")
    public BaseResponse batchUpdateCpDtlPerson(@RequestBody BatchUpdatePerson request) {
        log.info("batchUpdateCpDtlPerson");
        return cpFeign.batchUpdateCpDtlPerson(request);
    }


    @ApiOperation(value = "批量修改办理人")
    @PostMapping("/batchUpdateCpDtlCreateUser")
    public BaseResponse batchUpdateCpDtlCreateUser(@RequestBody BatchUpdatePerson request) {
        log.info("batchUpdateCpDtlCreateUser");
        return cpFeign.batchUpdateCpDtlCreateUser(request);
    }

    @ApiOperation(value = "批量修改责任人为项目部门项目经理")
    @PostMapping("/batchUpdateCpDtlPersonWithDepPm")
    public BaseResponse batchUpdateCpDtlPersonWithDepPm(@RequestBody BatchUpdatePerson request, @RequestHeader String token) {
        log.info("batchUpdateCpDtlPersonWithDepPm");
        return cpFeign.batchUpdateCpDtlPersonWithDepPm(request, token);
    }

    @ApiOperation(value = "批量修改办理人为项目部门项目经理")
    @PostMapping("/batchUpdateCpDtlCreateUserWithDepPm")
    public BaseResponse batchUpdateCpDtlCreateUserWithDepPm(@RequestBody BatchUpdatePerson request, @RequestHeader String token) {
        log.info("batchUpdateCpDtlCreateUserWithDepPm");
        return cpFeign.batchUpdateCpDtlCreateUserWithDepPm(request, token);
    }


    @ApiOperation(value = "ID查询项目建设程序详情")
    @GetMapping("/getCpDtlById")
    public BaseResponse getCpDtlById(@RequestParam String cpdId) {
        log.info("getCpDtlById");
        return cpFeign.getCpDtlById(cpdId);
    }

    @ApiOperation(value = "设建设程序父级节点状态")
    @GetMapping("/setCpDtlStatus")
    public BaseResponse setCpDtlStatus(@RequestParam String cpdId) {
        log.info("setCpDtlStatus");
        return cpFeign.setCpDtlStatus(cpdId);
    }

    @ApiOperation(value = "批量修改建设程序成果上传附件类型")
    @PostMapping("/updateCpdFileTypeBath")
    public BaseResponse updateCpdFileTypeBath(@RequestBody LibraryFileTypeDTO request){
        log.info("updateCpdFileTypeBath");
        return cpFeign.updateCpdFileTypeBath(request);
    }
    @ApiOperation(value = "批量保存建设程序节点")
    @PostMapping("/createProjectCpDtlBath")
    public BaseResponse createProjectCpDtlBath(@RequestBody List<ProjectCpDtl> request){
        log.info("createProjectCpDtlBath");
        return cpFeign.createProjectCpDtlBath(request);
    }
    @ApiOperation(value = "根据程序id获取办理记录")
    @GetMapping("/selectProjectCpRprByCpdId")
    public BaseResponse selectProjectCpRprByCpdId(@RequestParam String cpdId, @RequestHeader String token) {
        log.info("selectProjectCpRprByCpdId");
        return cpFeign.selectProjectCpRprByCpdId(cpdId,token);
    }

    @ApiOperation(value = "根据pId获取标段列表")
    @GetMapping("/getGantSectionListByPid")
    public BaseResponse getGantSectionListByPid(@RequestParam("pId") String pId) {
        log.info("getGantSectionListByPid");
        return cpFeign.getGantSectionListByPid(pId);
    }

    @ApiOperation(value = "建设程序办理，回写时间字段")
    @PostMapping("/updateCpdTimeByCprId")
    public BaseResponse updateCpdTimeByCprId(@RequestBody ProjectCpDtlRequest request) {
        log.info("updateCpdTimeByCprId");
        return cpFeign.updateCpdTimeByCprId(request);
    }

    @ApiOperation("所有建设程序详情列表")
    @PostMapping("/getAllCpDtlList")
    public BaseResponse getAllCpDtlList(@RequestBody TimeFrameRequest request) {
        log.info("getAllCpDtlList");
        return cpFeign.getAllCpDtlList(request);
    }
}
