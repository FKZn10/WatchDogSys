package com.jpmsoft.projectConsumer.feign;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.*;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectManageResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectTempResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Component
@FeignClient(name = "project-producer", contextId = "projectOutline", path = "/v1/projectOutline")
public interface OutlineFeign {
    /*****************************************业务管理规划-体系库**********************************************/
    @PostMapping("/getOutlineSystemLibTree")
    BaseResponse getOutlineSystemLibTree(@RequestBody OutlineSystemLibRequest request);

    @GetMapping("/getOutlineSystemLibById")
    BaseResponse getOutlineSystemLibById(@RequestParam("id") String id);

    @PostMapping("/createOrUpdateOutlineSystemLib")
    BaseResponse createOrUpdateOutlineSystemLib(@RequestBody OutlineSystemLibRequest request, @RequestHeader String token);

    @PostMapping("/autoSaveOutlineSystemLib")
    BaseResponse autoSaveOutlineSystemLib(@RequestBody OutlineSystemLibRequest request, @RequestHeader String token);

    @GetMapping("/deleteOutlineSystemLibById")
    BaseResponse deleteOutlineSystemLibById(@RequestParam("id") String id, @RequestHeader String token);

    @PostMapping("/batchUpdateSystemLibOslStatus")
    BaseResponse batchUpdateSystemLibOslStatus(@RequestBody OutlineSystemLibRequest request, @RequestHeader String token);

    @PostMapping("/updateOutlineSystemLibSort")
    BaseResponse updateOutlineSystemLibSort(@RequestBody OutlineSystemLibRequest request, @RequestHeader String token);

    @PostMapping("/searchOrReplaceOutlineSystemLib")
    BaseResponse searchOrReplaceOutlineSystemLib(@RequestBody OutlineSystemLibRequest request, @RequestHeader String token);

    @GetMapping("/checkEmptyOutlineSystemLib")
    BaseResponse checkEmptyOutlineSystemLib(@RequestParam("osmId") String osmId, @RequestHeader String token);


    /***************************************业务管理规划-体系模板*********************************************/
    @PostMapping("/getOutlineSystemManagePage")
    BaseResponse getOutlineSystemManagePage(@RequestBody OutlineSystemManageRequest request);

    @GetMapping("/getOutlineSystemManageById")
    BaseResponse getOutlineSystemManageById(@RequestParam("id") String id);

    @PostMapping("/createOrUpdateOutlineSystemManage")
    BaseResponse createOrUpdateOutlineSystemManage(@RequestBody OutlineSystemManageRequest request, @RequestHeader String token);

    @PostMapping("/deleteOutlineSystemManageById")
    BaseResponse deleteOutlineSystemManageById(@RequestBody OutlineSystemManageRequest request);

    @PostMapping("/batchUpdateSystemManageStatus")
    BaseResponse batchUpdateSystemManageStatus(@RequestBody OutlineSystemManageRequest request, @RequestHeader String token);

    @GetMapping("/copyOutlineSystemManageById")
    BaseResponse copyOutlineSystemManageById(@RequestParam("id") String id, @RequestHeader String token);


    /***************************************业务管理规划-项目管理*********************************************/
    @PostMapping("/getOutlineProjectManagePage")
    BaseResponse getOutlineProjectManagePage(@RequestBody OutlineProjectManageRequest request);

    @GetMapping("/getOutlineProjectManageById")
    BaseResponse getOutlineProjectManageById(@RequestParam("id") String id);

    @GetMapping("/selectOutlineProjectManageById")
    OutlineProjectManageResponse selectOutlineProjectManageById(@RequestParam("id") String id);

    @PostMapping("/createOrUpdateOutlineProjectManage")
    BaseResponse createOrUpdateOutlineProjectManage(@RequestBody OutlineProjectManageRequest request, @RequestHeader String token);

    @GetMapping("/deleteOutlineProjectManageById")
    BaseResponse deleteOutlineProjectManageById(@RequestParam("id") String id, @RequestHeader String token);

    @PostMapping("/batchUpdateProjectManageStatus")
    BaseResponse batchUpdateProjectManageStatus(@RequestBody OutlineProjectManageRequest request);

    @PostMapping("/batchUpdateProjectManageOpmStatus")
    BaseResponse batchUpdateProjectManageOpmStatus(@RequestBody OutlineProjectManageRequest request);

    @PostMapping("/coverSystemManageToProjectManage")
    BaseResponse coverSystemManageToProjectManage(@RequestBody OutlineProjectManageRequest request, @RequestHeader String token);

    @GetMapping("/getOutlineProjectManageByPid")
    BaseResponse getOutlineProjectManageByPid(@RequestParam("pId") String pId, @RequestParam(value = "opmCode", defaultValue = "01") String opmCode);

    @GetMapping("/getOutlineProjectManageByPidList")
    BaseResponse getOutlineProjectManageByPidList(@RequestParam("pId") String pId);

    @GetMapping("/getPreTypeVerifyIds")
    BaseResponse getPreTypeVerifyIds(@RequestParam("opmId") String opmId);

    @PostMapping("/getOutlineProjectLedgerCount")
    BaseResponse getOutlineProjectLedgerCount(@RequestBody OutlineProjectManageRequest request);

    @PostMapping("/getOutlineProjectLedgerPage")
    BaseResponse getOutlineProjectLedgerPage(@RequestBody OutlineProjectManageRequest request);


    /***********************************业务管理规划-项目管理大纲体系*******************************************/
    @PostMapping("/getOutlineProjectLibTree")
    BaseResponse getOutlineProjectLibTree(@RequestBody OutlineProjectLibRequest request);

    @GetMapping("/getOutlineProjectLibById")
    BaseResponse getOutlineProjectLibById(@RequestParam("id") String id);

    @PostMapping("/createOrUpdateOutlineProjectLib")
    BaseResponse createOrUpdateOutlineProjectLib(@RequestBody OutlineProjectLibRequest request, @RequestHeader String token);

    @PostMapping("/autoSaveOutlineProjectLib")
    BaseResponse autoSaveOutlineProjectLib(@RequestBody OutlineProjectLibRequest request, @RequestHeader String token);

    @GetMapping("/deleteOutlineProjectLibById")
    BaseResponse deleteOutlineProjectLibById(@RequestParam("id") String id, @RequestHeader String token);

    @GetMapping("/getOutlineProjectLibListByOpmId")
    BaseResponse getOutlineProjectLibListByOpmId(@RequestParam("opmId") String opmId);

    @PostMapping("/updateOutlineProjectLibSort")
    BaseResponse updateOutlineProjectLibSort(@RequestBody OutlineProjectLibRequest request, @RequestHeader String token);

    @GetMapping("/getOutlineProjectLibListByPid")
    BaseResponse getOutlineProjectLibListByPid(@RequestParam("pId") String pId, @RequestParam(value = "oplName", required = false) String oplName, @RequestParam(value = "opmCode", required = false) String opmCode);

    @PostMapping("/searchOrReplaceOutlineProjectLib")
    BaseResponse searchOrReplaceOutlineProjectLib(@RequestBody OutlineProjectLibRequest request, @RequestHeader String token);

    @GetMapping("/verifyProjectLibWithProjectName")
    BaseResponse verifyProjectLibWithProjectName(@RequestParam("opmId") String opmId);

    @GetMapping("/getFileIdByOplId")
    BaseResponse getFileIdByOplId(@RequestParam("oplId") String oplId);

    @GetMapping("/getOplIdByPidAndTCode")
    BaseResponse getOplIdByPidAndTCode(@RequestParam("pId") String pId, @RequestParam("tCode") String tCode);

    @GetMapping("/checkEmptyOutlineProjectLib")
    BaseResponse checkEmptyOutlineProjectLib(@RequestParam("opmId") String opmId);


    /*********************************项目管理大纲体系模板-关联业务功能******************************************/
    @PostMapping("/nodeTemp/createOutlineNodeTemp")
    BaseResponse createOutlineNodeTemp(@RequestBody OutlineNodeTempRequest request, @RequestHeader String token);

    @PostMapping("/nodeTemp/getOutlineNodeTempList")
    BaseResponse getOutlineNodeTempList(@RequestBody OutlineNodeTempRequest request);

    @GetMapping("/nodeTemp/deleteOutlineNodeTempById")
    BaseResponse deleteOutlineNodeTempById(@RequestParam String id, @RequestHeader String token);

    @PostMapping("/nodeTemp/updateOutlineNodeTemp")
    BaseResponse updateOutlineNodeTemp(@RequestBody OutlineNodeTempRequest request, @RequestHeader String token);

    @PostMapping("/nodeTemp/autoSaveOutlineNodeTemp")
    BaseResponse autoSaveOutlineNodeTemp(@RequestBody OutlineNodeTempRequest request, @RequestHeader String token);


    /**********************************项目管理大纲体系模板-节点数据源*******************************************/
    @PostMapping("/nodeDatasource/createOutlineNodeDatasource")
    BaseResponse createOutlineNodeDatasource(@RequestBody OutlineNodeDatasourceRequest request, @RequestHeader String token);

    @PostMapping("/nodeDatasource/getOutlineNodeDatasourceList")
    BaseResponse getOutlineNodeDatasourceList(@RequestBody OutlineNodeDatasourceRequest request);

    @GetMapping("/nodeDatasource/deleteOutlineNodeDatasourceById")
    BaseResponse deleteOutlineNodeDatasourceById(@RequestParam String id, @RequestHeader String token);

    @PostMapping("/nodeDatasource/updateOutlineNodeDatasource")
    BaseResponse updateOutlineNodeDatasource(@RequestBody OutlineNodeDatasourceRequest request, @RequestHeader String token);

    @PostMapping("/nodeDatasource/autoSaveOutlineNodeDatasource")
    BaseResponse autoSaveOutlineNodeDatasource(@RequestBody OutlineNodeDatasourceRequest request, @RequestHeader String token);


    /***********************************项目管理大纲体系模板-风险指标********************************************/
    @PostMapping("/libRisk/createOutlineLibRisk")
    BaseResponse createOutlineLibRisk(@RequestBody OutlineLibRiskRequest request, @RequestHeader String token);

    @GetMapping("/libRisk/getOutlineLibRiskByOslId")
    BaseResponse getOutlineLibRiskByOslId(@RequestParam String oslId);

    @GetMapping("/libRisk/getOutlineLibRiskById")
    BaseResponse getOutlineLibRiskById(@RequestParam("id") String id);

    @PostMapping("/libRisk/getOutlineLibRiskPage")
    BaseResponse getOutlineLibRiskPage(@RequestBody OutlineLibRiskRequest request);

    @GetMapping("/libRisk/deleteOutlineLibRiskById")
    BaseResponse deleteOutlineLibRiskById(@RequestParam String id, @RequestHeader String token);

    @PostMapping("/libRisk/updateOutlineLibRisk")
    BaseResponse updateOutlineLibRisk(@RequestBody OutlineLibRiskRequest request, @RequestHeader String token);

    @PostMapping("/libRisk/autoSaveOutlineLibRisk")
    BaseResponse autoSaveOutlineLibRisk(@RequestBody OutlineLibRiskRequest request, @RequestHeader String token);


    /***********************************项目管理大纲体系-关联业务功能*******************************************/
    @PostMapping("/projectTemp/createOutlineProjectTemp")
    BaseResponse createOutlineProjectTemp(@RequestBody OutlineProjectTempRequest request, @RequestHeader String token);

    @GetMapping("/projectTemp/deleteOutlineProjectTempById")
    BaseResponse deleteOutlineProjectTempById(@RequestParam("id") String id, @RequestHeader String token);

    @PostMapping("/projectTemp/updateOutlineProjectTemp")
    BaseResponse updateOutlineProjectTemp(@RequestBody OutlineProjectTempRequest request, @RequestHeader String token);

    @GetMapping("/projectTemp/getOutlineProjectTempById")
    BaseResponse getOutlineProjectTempById(@RequestParam("id") String id);

    @PostMapping("/projectTemp/getOutlineProjectTempPage")
    BaseResponse getOutlineProjectTempPage(@RequestBody OutlineProjectTempRequest request);

    @PostMapping("/projectTemp/autoSaveOutlineProjectTemp")
    BaseResponse autoSaveOutlineProjectTemp(@RequestBody OutlineProjectTempRequest request, @RequestHeader String token);


    /**********************************项目管理大纲体系-节点数据源配置******************************************/
    @PostMapping("/projectDatasource/createProjectDatasource")
    BaseResponse createProjectDatasource(@RequestBody OutlineProjecDatasourceRequest request, @RequestHeader String token);

    @GetMapping("/projectDatasource/deleteProjectDatasourceById")
    BaseResponse deleteProjectDatasourceById(@RequestParam("id") String id, @RequestHeader String token);

    @PostMapping("/projectDatasource/updateProjectDatasource")
    BaseResponse updateProjectDatasource(@RequestBody OutlineProjecDatasourceRequest request, @RequestHeader String token);

    @GetMapping("/projectDatasource/getProjectDatasourceById")
    BaseResponse getProjectDatasourceById(@RequestParam("id") String id);

    @PostMapping("/projectDatasource/getProjectDatasourcePage")
    BaseResponse getProjectDatasourcePage(@RequestBody OutlineProjecDatasourceRequest request);

    @PostMapping("/projectDatasource/autoSaveProjectDatasource")
    BaseResponse autoSaveProjectDatasource(@RequestBody OutlineProjecDatasourceRequest request, @RequestHeader String token);


    /***********************************项目管理大纲体系管理-风险指标*******************************************/
    @PostMapping("/projectRisk/createOutlineProjectRisk")
    BaseResponse createOutlineProjectRisk(@RequestBody OutlineProjectRiskRequest request, @RequestHeader String token);

    @GetMapping("/projectRisk/deleteOutlineProjectRiskById")
    BaseResponse deleteOutlineProjectRiskById(@RequestParam("id") String id, @RequestHeader String token);

    @PostMapping("/projectRisk/updateOutlineProjectRisk")
    BaseResponse updateOutlineProjectRisk(@RequestBody OutlineProjectRiskRequest request, @RequestHeader String token);

    @GetMapping("/projectRisk/getOutlineProjectRiskById")
    BaseResponse getOutlineProjectRiskById(@RequestParam("id") String id);

    @PostMapping("/projectRisk/getOutlineProjectRiskPage")
    BaseResponse getOutlineProjectRiskPage(@RequestBody OutlineProjectRiskRequest request);

    @PostMapping("/projectRisk/autoSaveOutlineProjectRisk")
    BaseResponse autoSaveOutlineProjectRisk(@RequestBody OutlineProjectRiskRequest request, @RequestHeader String token);

    @GetMapping("/projectRisk/getOutlineProjectRiskListByOpmId")
    BaseResponse getOutlineProjectRiskListByOpmId(@RequestParam("opmId") String opmId);

    /********************************************项目管理大纲-样式********************************************/
    @PostMapping("/style/getOutlineStylePage")
    BaseResponse getOutlineStylePage(@RequestBody OutlineProjectStyleRequest request);

    @GetMapping("/style/getOutlineStyleById")
    BaseResponse getOutlineStyleById(@RequestParam String id);

    @PostMapping("/style/deleteOutlineStyleById")
    BaseResponse deleteOutlineStyleById(@RequestBody OutlineProjectStyleRequest request);

    @PostMapping("/style/createOrUpdateOutlineStyle")
    BaseResponse createOrUpdateOutlineStyle(@RequestBody OutlineProjectStyleRequest request, @RequestHeader String token);

    @GetMapping("/style/initializationOutlineStyle")
    BaseResponse initializationOutlineStyle(@RequestHeader String token);


    /********************************************项目管理大纲-延迟渲染*****************************************/
    @PostMapping("/delay/handleDelayRender")
    BaseResponse handleDelayRender(@RequestBody(required = false) List<OutlineProjectTempResponse> result, @RequestParam("pId") String pId);


    /********************************************项目管理大纲-图片********************************************/
    @GetMapping("/image/imageInitialization")
    BaseResponse imageInitialization();


    @PostMapping("/v1/outlineMonitor/insertMonitor")
     BaseResponse insertMonitor(@RequestBody Object data);

    @GetMapping("/v1/outlineMonitor/getMonitor")
    BaseResponse getMonitor(@RequestHeader String token);
}
