package com.jpmsoft.userProducer.controller.v1;


import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.ExlTableColumnDto;
import com.jpmsoft.userProducer.pojo.dto.SelectTableDto;
import com.jpmsoft.userProducer.pojo.dto.TableDto;
import com.jpmsoft.userProducer.pojo.dto.TableSqlDTO;
import com.jpmsoft.userProducer.service.ISysTableService;
import com.jpmsoft.userProducer.service.ITableColumnManagementService;
import com.jpmsoft.userProducer.service.ITableManagementService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@Api(tags = "数据表", value = "数据表")
@RequestMapping("/v1/SystemManage")
public class SysTableController {
    @Resource
    private ISysTableService sysTableService;
    @Resource
    private ITableManagementService tableManagementService;
    @Resource
    private ITableColumnManagementService tableColumnManagementService;
    @Resource
    private RedisUtils redisUtils;


    @ApiOperation(value = "数据表列表")
    @PostMapping("/GetTableList")
    public BaseResponse GetTableList(@RequestBody TableDto request) {
        log.info("GetTableList");
        return sysTableService.GetTableList(request);
    }

    @ApiOperation(value = "新增修改数据表")
    @PostMapping("/CreateOrUpdateTable")
    public BaseResponse CreateOrUpdateTable(@RequestBody TableDto request, @RequestHeader String token) {
        log.info("CreateOrUpdateTable");
        return sysTableService.CreateOrUpdateTable(request,redisUtils.getUid(token));
    }

    @ApiOperation(value = "删除数据表")
    @PostMapping("/DeleteTable")
    public BaseResponse DeleteTable(@RequestBody @Validated TableDto request, @RequestHeader String token) {
        log.info("DeleteTable");
        return sysTableService.DeleteTable(request,redisUtils.getUid(token));
    }


    @ApiOperation(value = "获取表信息")
    @GetMapping("/getTableInfo")
    public BaseResponse getTableInfo(@RequestParam String tableName, @RequestHeader String token) {
        log.info("获取所有的表信息");
        return tableManagementService.getTableInfo(tableName, redisUtils.getUid(token));
    }


    @ApiOperation(value = "获取表数据")
    @PostMapping("/gettabledata")
    public BaseResponse gettabledata(@RequestBody SelectTableDto request, @RequestHeader String token) {
        log.info("gettabledata");
        return tableManagementService.gettabledata(request, redisUtils.getUid(token));
    }

    @ApiOperation(value = "获取表字段信息")
    @GetMapping("/getTableColumnInfo")
    public BaseResponse getTableColumnInfo(@RequestParam String tableName, @RequestHeader String token) {
        log.info("获取所有的表字段信息信息");
        return tableColumnManagementService.getTableColumnInfo(tableName, redisUtils.getUid(token));
    }


    @ApiOperation(value = "属性同步")
    @GetMapping("/synchronizationpProperties")
    public BaseResponse synchronizationpProperties(@RequestParam String owner,@RequestHeader String token) {
        log.info("同步属性");
        return tableManagementService.synchronizationpProperties(owner,redisUtils.getUid(token));
    }


    @ApiOperation(value = "表属性导入")
    @PostMapping("/importProperties")
    public BaseResponse importProperties(@RequestBody List<ExlTableColumnDto> request, @RequestHeader String token) {
        log.info("导入属性");
        return tableColumnManagementService.importProperties(request, redisUtils.getUid(token));
    }

    @ApiOperation(value = "表结构检查")
    @PostMapping("/datacomparison")
    public BaseResponse datacomparison(@RequestBody List<ExlTableColumnDto> request,@RequestParam String owner, @RequestParam String exlstate, @RequestHeader String token) {
        log.info("校验表结构");
        return tableManagementService.datacomparison(request,owner, exlstate, redisUtils.getUid(token));
    }


    @ApiOperation(value = "根据id获取表信息")
    @GetMapping("/getSysTableinfo")
    public BaseResponse getSysTableinfo(@RequestParam String tableId) {
        log.info("getSysTableinfo");
        return sysTableService.getSysTableinfo(tableId);
    }

    @ApiOperation(value = "同步表结构")
    @GetMapping("/syncTable/{id}")
    public BaseResponse syncTable(@PathVariable("id") String id) {
        log.info("syncTable");
        return sysTableService.syncTableAndColumn(id,0);
    }

    @ApiOperation(value = "同步表结构(先删除后新建)")
    @GetMapping("/syncDropTable/{id}")
    public BaseResponse syncDropTable(@PathVariable("id") String id) {
        log.info("syncDropTable");
        return sysTableService.syncTableAndColumn(id,1);
    }


    @ApiOperation(value = "获取所有的模式名")
    @GetMapping("/getPatternName")
    public BaseResponse getPatternName(){
        log.info("getPatternName");
        return tableManagementService.getPatternName();
    }

    @ApiOperation(value = "获取数据名称")
    @GetMapping("/getDbName")
    public BaseResponse getDbName(){
        log.info("getDbName");
        return tableManagementService.getDbName();
    }

    @ApiOperation(value = "修改数据表信息")
    @PostMapping("/updateTableDataById")
    public  BaseResponse updateTableDataById(@RequestBody Map<String,Object> request, @RequestHeader String token){
        return sysTableService.updateTableDataById(request,redisUtils.getUid(token));
    }

    @ApiOperation(value = "删除数据表信息")
    @GetMapping("/deleteTableDataById")
    public  BaseResponse  deleteTableDataById(@RequestParam String tableId,@RequestParam String id, @RequestHeader String token){
        return sysTableService.deleteTableDataById(tableId,id,redisUtils.getUid(token));
    }

    @ApiOperation(value = "根据表获取表信息集合")
    @PostMapping("/tableManagementService")
    BaseResponse  getTableInfoAndColumnByTableColde(@RequestBody List<String> codeList){
        return  tableManagementService.getTableInfoAndColumnByTableColde(codeList);
    }

    /**
     * 获取表的SQL
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "获取表的SQL")
    @PostMapping("/getTableSql")
    public BaseResponse getTableSql(@RequestBody TableSqlDTO request) {
        log.info("getTableSql");
        return sysTableService.getTableSql(request);
    }
}
