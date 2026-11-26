package com.jpmsoft.userProducer.controller.v1;

import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.userProducer.pojo.dto.ColumnSqlDTO;
import com.jpmsoft.userProducer.pojo.dto.TableColumnDto;
import com.jpmsoft.userProducer.service.ISysTableColumnService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "数据表列", value = "数据表列")
@RequestMapping("/v1/SystemManage")
public class SysTableColumnController {
    @Resource
    private ISysTableColumnService sysTableColumnService;
    @Resource
    private RedisUtils redisUtils;

    @ApiOperation(value = "数据表列列表")
    @PostMapping("/GetTableColumnList")
    public BaseResponse GetTableColumnList(@RequestBody TableColumnDto request) {
        log.info("GetTableColumnList");
        return sysTableColumnService.GetTableColumnList(request);
    }

    @ApiOperation(value = "新增修改数据表列")
    @PostMapping("/CreateOrUpdateTableColumn")
    public BaseResponse CreateOrUpdateTableColumn(@RequestBody TableColumnDto request, @RequestHeader String token) {
        log.info("CreateOrUpdateTableColumn");
        return sysTableColumnService.CreateOrUpdateTableColumn(request, redisUtils.getUid(token));
    }

    @ApiOperation(value = "根据字段编码格式化驼峰编码")
    @PostMapping("/formatHump")
    public BaseResponse formatHump() {
        log.info("formatHump");
        return sysTableColumnService.formatHump();
    }

    @ApiOperation(value = "删除数据表列")
    @PostMapping("/DeleteTableColumn")
    public BaseResponse DeleteTableColumn(@RequestBody TableColumnDto request, @RequestHeader String token) {
        log.info("DeleteTableColumn");
        return sysTableColumnService.DeleteTableColumn(request, redisUtils.getUid(token));
    }

    @ApiOperation(value = "获取列表信息")
    @PostMapping("/GetTableColumnListByTableCode")
    public BaseResponse GetTableColumnListByTableCode(@RequestBody List<String> tableCode) {
        log.info("GetTableColumnListByTableCode");
        return sysTableColumnService.GetTableColumnListByTableCode(tableCode);
    }

    @ApiOperation(value = "批量设置指定表字段是否全部导入导出 action:exp|imp")
    @GetMapping("/setColumnExpOrImp")
    public BaseResponse setColumnExpOrImp(@RequestParam String tableCode, @RequestParam String action
            , @RequestParam Boolean enable) {
        log.info("setColumnExpOrImp");
        return sysTableColumnService.setColumnExpOrImp(tableCode, action, enable);
    }

    @ApiOperation(value = "生成字段的sql")
    @PostMapping("/getColumnSql")
    public BaseResponse getColumnSql(@RequestBody ColumnSqlDTO request) {
        log.info("getColumnSql");
        return sysTableColumnService.getColumnSql(request);
    }
}
