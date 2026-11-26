package com.jpmsoft.userProducer.controller.v1;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.pojo.dto.WebComponentCacheDTO;
import com.jpmsoft.userProducer.pojo.entity.WebComponentCache;
import com.jpmsoft.userProducer.pojo.vo.LogUserInfoVo;
import com.jpmsoft.userProducer.service.IWebComponentCacheService;
import com.jpmsoft.userProducer.utils.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@Api(tags = "前端组件用户级别缓存", value = "前端组件用户级别缓存")
@RequestMapping("/v1/webComponentCache")
public class WebComponentCacheController {

    @Resource
    private IWebComponentCacheService WebComponentCacheService;
    @Resource
    private RedisUtils redisUtils;

    @ApiOperation(value = "ID查询")
    @GetMapping(value = "/getById")
    public BaseResponse getById(@RequestParam String id) {
        return BaseResponse.ok(WebComponentCacheService.getById(id));
    }

    @ApiOperation(value = "ID删除")
    @GetMapping(value = "/removeById")
    public BaseResponse removeById(@RequestParam String id) {
        WebComponentCacheService.removeById(id);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "批量删除")
    @PostMapping(value = "/removeBatchByIds")
    public BaseResponse removeBatchByIds(@RequestBody List<String> ids) {
        WebComponentCacheService.removeBatchByIds(ids);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "清除用户所有缓存")
    @GetMapping(value = "/removeByUid")
    public BaseResponse removeByUid(@RequestParam String uid) {
        LambdaQueryWrapper<WebComponentCache> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WebComponentCache::getCreateUid, uid);
        WebComponentCacheService.remove(wrapper);
        return BaseResponse.ok();
    }

    @ApiOperation(value = "清除所有缓存")
    @GetMapping(value = "/removeAllCache")
    public BaseResponse removeAllCache(@RequestParam String uid) {
        WebComponentCacheService.remove(new QueryWrapper<>());
        return BaseResponse.ok();
    }

    @ApiOperation(value = "新增修改")
    @PostMapping(value = "/refreshCache")
    public BaseResponse refreshCache(@RequestBody WebComponentCache cache, @RequestHeader String token) {
        if (ValidateUtil.isAnObjEmpty(cache.getWccCode(), cache.getWccField()))
            return BaseResponse.fail(BaseMsg.notReqParam);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        LambdaQueryWrapper<WebComponentCache> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WebComponentCache::getWccCode, cache.getWccCode());
        wrapper.eq(WebComponentCache::getWccField, cache.getWccField());
        wrapper.eq(WebComponentCache::getCreateUid, user.getId());
        WebComponentCacheService.remove(wrapper);
        cache.setId(GenerateUUID.getUUID());
        cache.setCreateUid(user.getId());
        cache.setCreateUname(user.getName());
        cache.setWccKey(cache.getWccCode() + ":" + cache.getWccField());
        cache.setCreateTime(new Date());
        WebComponentCacheService.save(cache);
        return BaseResponse.ok();
    }


    @ApiOperation(value = "code+field查询")
    @GetMapping(value = "/getByCodeAndField")
    public BaseResponse getById(@RequestParam String code, @RequestParam String field, @RequestHeader String token) {
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        LambdaQueryWrapper<WebComponentCache> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(WebComponentCache::getWccCode, code);
        wrapper.eq(WebComponentCache::getWccField, field);
        wrapper.eq(WebComponentCache::getCreateUid, user.getId());
        List<WebComponentCache> list = WebComponentCacheService.list(wrapper);
        if (!CollectionUtils.isEmpty(list)) return BaseResponse.ok(list.get(0).getWccValue());
        return BaseResponse.ok();
    }


    @ApiOperation(value = "批量code+field查询,入参code+\":\"+field格式字符串集合")
    @PostMapping(value = "/getBathByCodeAndField")
    public BaseResponse getById(@RequestBody List<String> keys, @RequestHeader String token) {
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        LambdaQueryWrapper<WebComponentCache> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(WebComponentCache::getWccKey, keys);
        wrapper.eq(WebComponentCache::getCreateUid, user.getId());
        List<WebComponentCache> list = WebComponentCacheService.list(wrapper);
        if (!CollectionUtils.isEmpty(list)) {
            return BaseResponse.ok(list.stream().map(d -> {
                WebComponentCacheDTO dto = new WebComponentCacheDTO();
                dto.setWccCode(d.getWccCode());
                dto.setWccField(d.getWccField());
                dto.setWccKey(d.getWccKey());
                dto.setWccValue(d.getWccValue());
                return dto;
            }).collect(Collectors.toList()));
        }
        return BaseResponse.ok();
    }

    @ApiOperation(value = "分页查询")
    @PostMapping(value = "/getWebComponentCachePage")
    public Object getWebComponentCachePage(@RequestBody WebComponentCacheDTO code) {
        LambdaQueryWrapper<WebComponentCache> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(ValidateUtil.isNotEmpty(code.getCreateUid()), WebComponentCache::getCreateUid, code.getCreateUid());
        wrapper.like(ValidateUtil.isNotEmpty(code.getWccCode()), WebComponentCache::getWccCode, code.getWccCode());
        wrapper.like(ValidateUtil.isNotEmpty(code.getWccField()), WebComponentCache::getWccField, code.getWccField());
        wrapper.like(ValidateUtil.isNotEmpty(code.getCreateUname()), WebComponentCache::getCreateUname, code.getCreateUname());
        IPage<WebComponentCache> iPage = WebComponentCacheService.page(new Page<>(code.getPage(), code.getSize()), wrapper);
        Long total = iPage.getTotal();
        return BaseResponse.ok(iPage.getRecords(), total.intValue());
    }
}
