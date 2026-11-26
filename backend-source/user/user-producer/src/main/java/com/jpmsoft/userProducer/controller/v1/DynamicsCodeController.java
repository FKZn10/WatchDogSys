package com.jpmsoft.userProducer.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.DynamicCodeDTO;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.pojo.dto.DynamicsCodeDTO;
import com.jpmsoft.userProducer.pojo.entity.DynamicsCode;
import com.jpmsoft.userProducer.service.IDynamicsCodeService;
import com.jpmsoft.userProducer.utils.SpringContextUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.janino.SimpleCompiler;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "动态代码管理", value = "动态代码管理")
@RequestMapping("/v1/dynamicCode")
public class DynamicsCodeController {

    @Resource
    private IDynamicsCodeService dynamicsCodeService;

    @ApiOperation(value = "ID查询")
    @GetMapping(value = "/getById")
    public BaseResponse getById(@RequestParam String id) {
        return BaseResponse.ok(dynamicsCodeService.getById(id));
    }

    @ApiOperation(value = "ID删除")
    @GetMapping(value = "/removeById")
    public BaseResponse removeById(@RequestParam String id) {
        dynamicsCodeService.removeById(id);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "批量删除")
    @PostMapping(value = "/removeBatchByIds")
    public BaseResponse removeBatchByIds(@RequestBody List<String> ids) {
        dynamicsCodeService.removeBatchByIds(ids);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "新增修改")
    @PostMapping(value = "/createOrUpdate")
    public BaseResponse createOrUpdate(@RequestBody DynamicsCode code) {
        if (ValidateUtil.isEmpty(code.getId())) code.setId(GenerateUUID.getUUID());
        dynamicsCodeService.saveOrUpdate(code);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/getDynamicsCodePage")
    public Object getDynamicsCodePage(@RequestBody DynamicsCodeDTO code) {
        LambdaQueryWrapper<DynamicsCode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ValidateUtil.isNotEmpty(code.getDcCategory()), DynamicsCode::getDcCategory, code.getDcCategory());
        wrapper.eq(ValidateUtil.isNotEmpty(code.getDcType()), DynamicsCode::getDcType, code.getDcType());
        wrapper.eq(ValidateUtil.isNotEmpty(code.getStatus()), DynamicsCode::getStatus, code.getStatus());
        wrapper.eq(ValidateUtil.isNotEmpty(code.getCreateUid()), DynamicsCode::getCreateUid, code.getCreateUid());
        wrapper.like(ValidateUtil.isNotEmpty(code.getCreateUname()), DynamicsCode::getCreateUname, code.getCreateUname());
        wrapper.like(ValidateUtil.isNotEmpty(code.getDcName()), DynamicsCode::getDcName, code.getDcName());
        wrapper.like(ValidateUtil.isNotEmpty(code.getClassName()), DynamicsCode::getClassName, code.getClassName());
        wrapper.like(ValidateUtil.isNotEmpty(code.getMethodName()), DynamicsCode::getMethodName, code.getMethodName());
        IPage<DynamicsCode> iPage = dynamicsCodeService.page(new Page<>(code.getPage(), code.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }


    @ApiOperation(value = "动态代码执行")
    @PostMapping(value = "/executeById")
    public BaseResponse executeById(@RequestBody DynamicCodeDTO request) throws Exception {
        if (ValidateUtil.isEmpty(request.getDcId())) return BaseResponse.fail(BaseMsg.notReqParam);
        DynamicsCode code = dynamicsCodeService.getById(request.getDcId());
        if (code == null) return BaseResponse.fail(BaseMsg.unknownAction);
        // 1. 编译代码
        SimpleCompiler compiler = new SimpleCompiler();
        compiler.cook(code.getDcCode());
        // 2. 加载类
        Class<?> clazz = compiler.getClassLoader().loadClass(code.getClassName());
        // 获取上下文
        ApplicationContext context = SpringContextUtil.getApplicationContext();
        Object instance = context.getAutowireCapableBeanFactory().createBean(clazz);
        // 3. 执行 run 方法（可传参）
        Method method = clazz.getMethod(code.getMethodName(), List.class);
        log.info("动态代码执行executeById，类名: {}, 方法名: {}, 参数: {}", code.getClassName(), code.getMethodName(), request.getParams());
        return BaseResponse.ok(method.invoke(instance, request.getParams()));
    }

    /**
     * 执行动态Java代码，并传入参数
     *
     * @return
     * @throws Exception
     */
    @ApiIgnore
    @ApiOperation(value = "动态代码执行")
    @PostMapping(value = "/execute")
    public Object execute(@RequestBody DynamicCodeDTO request) throws Exception {
        // 1. 编译代码
        SimpleCompiler compiler = new SimpleCompiler();
        compiler.cook(request.getCode());
        // 2. 加载类
        Class<?> clazz = compiler.getClassLoader().loadClass(request.getClassName());
        // 获取上下文
        ApplicationContext context = SpringContextUtil.getApplicationContext();
        Object instance = context.getAutowireCapableBeanFactory().createBean(clazz);
        // 3. 执行 run 方法（可传参）
        Method method = clazz.getMethod(request.getMethodName(), List.class);
        log.info("动态代码执行execute，类名: {}, 方法名: {}, 参数: {}", request.getClassName(), request.getMethodName(), request.getParams());
        return BaseResponse.ok(method.invoke(instance, request.getParams()));
    }

    @ApiIgnore
    @ApiOperation(value = "动态代码执行(固定种类/类型)")
    @PostMapping(value = "/executeByCategoryType")
    public Object executeByCategoryType(@RequestBody DynamicCodeDTO request) throws Exception {
        LambdaQueryWrapper<DynamicsCode> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DynamicsCode::getDcCategory, request.getDcCategory())
                .eq(DynamicsCode::getDcType, request.getDcType())
                .eq(DynamicsCode::getStatus, "1");
        List<DynamicsCode> list = dynamicsCodeService.list(wrapper);
        if (list.isEmpty()) return BaseResponse.fail(BaseMsg.unknownAction);
        DynamicsCode code = list.get(0);
        request.setDcId(code.getId());
        request.setDcCategory(code.getDcCategory());
        request.setDcType(code.getDcType());
        request.setCode(code.getDcCode());
        request.setClassName(code.getClassName());
        request.setMethodName(code.getMethodName());
        return execute(request);
    }

    /**
     * 从上传文件中读取代码并执行
     *
     * @param file       包含Java代码的文件
     * @param className  类名
     * @param methodName 方法名
     * @param params     方法参数
     * @return 执行结果
     * @throws Exception 异常
     */
    @ApiIgnore
    @ApiOperation(value = "从文件执行动态代码")
    @PostMapping(value = "/executeFromFile", consumes = "multipart/form-data")
    public Object executeFromFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("className") String className,
            @RequestParam("methodName") String methodName,
            @RequestParam(value = "params", required = false) List<Object> params) throws Exception {

        // 1. 从文件中读取代码
        StringBuilder codeBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            while ((line = reader.readLine()) != null) {
                codeBuilder.append(line).append("\n");
            }
        }
        String code = codeBuilder.toString();
        log.info("从文件读取代码成功，文件大小: {} bytes", file.getSize());

        // 1. 编译代码
        SimpleCompiler compiler = new SimpleCompiler();
        compiler.cook(code);
        // 2. 加载类
        Class<?> clazz = compiler.getClassLoader().loadClass(className);
        // 获取上下文
        ApplicationContext context = SpringContextUtil.getApplicationContext();
        Object instance = context.getAutowireCapableBeanFactory().createBean(clazz);
        // 3. 执行 run 方法（可传参）
        Method method = clazz.getMethod(methodName, List.class);
        log.info("从文件执行动态代码成功，类名: {}, 方法名: {}, 参数: {}", className, methodName, params);
        return method.invoke(instance, params);
    }
}
