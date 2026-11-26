package com.jpmsoft.projectProducer.service.projectOutline.impl;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.TypeReference;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.enums.ApprovalStatusEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectStyleDtlRequest;
import com.jpmsoft.common.model.request.projectOutline.OutlineProjectStyleRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectStyleDtlResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineProjectStyleResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineProjectStyleMapper;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectStyle;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineProjectStyleDtl;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysFileService;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjectStyleDtlService;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineProjectStyleService;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 管理规划子系统 样式
 *
 * @author tianhelin
 * @date 2025/8/18
 */
@Service
public class OutlineProjectStyleServiceImpl extends ServiceImpl<OutlineProjectStyleMapper, OutlineProjectStyle> implements IOutlineProjectStyleService {
    @Resource
    private IOutlineProjectStyleDtlService outlineProjectStyleDtlService;
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private ISysFileService sysFileService;

    /**
     * 获取体系模板样式分页列表
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getOutlineStylePage(OutlineProjectStyleRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        Page<OutlineProjectStyleResponse> page = new Page<>(request.getPage(), request.getSize());
        List<OutlineProjectStyleResponse> list = baseMapper.getOutlineStylePage(page, request);
        return BaseResponse.ok(list, Math.toIntExact(page.getTotal()));
    }

    /**
     * 根据ID获取体系模板样式
     *
     * @param id
     * @return
     */
    @Override
    public BaseResponse getOutlineStyleById(String id) {
        if (ValidateUtil.isEmpty(id)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineProjectStyle outlineProjectStyle = this.getById(id);
        if (ValidateUtil.isNull(outlineProjectStyle)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        OutlineProjectStyleResponse response = new OutlineProjectStyleResponse();
        BeanUtils.copyProperties(outlineProjectStyle, response);
        List<OutlineProjectStyleDtl> styleList = outlineProjectStyleDtlService.list(Wrappers.<OutlineProjectStyleDtl>lambdaQuery().eq(OutlineProjectStyleDtl::getOpsId, id));
        if (ValidateUtil.isNotEmpty(styleList)) {
            List<OutlineProjectStyleDtlResponse> styleDtlList = BeanUtils.copyList(styleList, OutlineProjectStyleDtlResponse.class);
            response.setStyleList(styleDtlList);
        }
        return BaseResponse.ok(response);
    }

    /**
     * 删除体系模板样式
     *
     * @param request
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse deleteOutlineStyleById(OutlineProjectStyleRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        OutlineProjectStyle outlineProjectStyle = this.getById(request.getId());
        if (ValidateUtil.isNull(outlineProjectStyle)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        this.removeById(request.getId());
        outlineProjectStyleDtlService.remove(Wrappers.<OutlineProjectStyleDtl>lambdaQuery().eq(OutlineProjectStyleDtl::getOpsId, request.getId()));
        sysFileService.deleteFileById(request.getId());
        return BaseResponse.ok();
    }

    /**
     * 创建或更新体系模板样式
     *
     * @param request
     * @param token
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateOutlineStyle(OutlineProjectStyleRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (request.getStatus() && validateCategoryEnabled(request.getCategory(), request.getId())) {
            return BaseResponse.fail("该分类下已存在启用的样式，请先禁用后再启用新的样式");
        }
        OutlineProjectStyle entity = new OutlineProjectStyle();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isEmpty(request.getId())) {
            entity.setId(GenerateUUID.getUUID());
            LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
            entity.setCreateTime(new Date());
            entity.setCreateUname(user.getName());
            entity.setCreateUid(user.getId());
            sysFileService.saveFile(entity.getId(), request.gettId(), String.valueOf(entity.getState()),
                    entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(),
                    request.getpId(), request.getFileTitle(), request.getFileCode(), "create",
                    request.getFileRelationId(), null, request.gettCode(), null);
        } else {
            OutlineProjectStyle existingEntity = this.getById(request.getId());
            if (ValidateUtil.isNull(existingEntity)) {
                return BaseResponse.fail(ResMsg.infoNotExist);
            }
            sysFileService.saveFile(entity.getId(), request.gettId(), String.valueOf(entity.getState()),
                    entity.getCreateUid(), entity.getCreateUname(), entity.getCreateTime(),
                    request.getpId(), request.getFileTitle(), request.getFileCode(), "update",
                    request.getFileRelationId(), null, request.gettCode(), null);
        }
        this.saveOrUpdate(entity);
        List<OutlineProjectStyleDtlRequest> styleList = request.getStyleList();
        outlineProjectStyleDtlService.batchCreateOutlineStyleDtl(entity.getId(), styleList);
        clearStyleDtlCache(request.getCategory());
        return BaseResponse.ok(entity.getId());
    }

    /**
     * 验证样式在该分类下是否有启用的
     *
     * @param category
     * @return
     */
    private boolean validateCategoryEnabled(String category, String id) {
        if (ValidateUtil.isEmpty(category)) {
            return false;
        }
        return baseMapper.exists(
                Wrappers.<OutlineProjectStyle>lambdaQuery()
                        .eq(OutlineProjectStyle::getCategory, category)
                        .eq(OutlineProjectStyle::getStatus, 1)
                        .ne(OutlineProjectStyle::getState, ApprovalStatusEnum.Invalid.getIndex())
                        .ne(ValidateUtil.isNotEmpty(id), OutlineProjectStyle::getId, id)
        );
    }

    /**
     * 初始化方法
     *
     * @param token
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initializationOutlineStyle(String token) {
        if (count() > 0) {
            return;
        }
        String style01Json = "{\"id\":\"2978f3903d8442c9ba11fa82102e03d8\",\"createUid\":\"abf2cb0b1c284efb968293b7190096ec\",\"createUname\":\"test\",\"createTime\":\"2025-08-19T02:09:56.459+00:00\",\"state\":7,\"stateTime\":\"2025-08-19T03:11:39.735+00:00\",\"category\":\"01\",\"status\":true,\"styleName\":\"导出样式\",\"styleList\":[{\"id\":\"3395de8103ce4416a46ac40f2377278e\",\"category\":\"10\",\"opsId\":\"2978f3903d8442c9ba11fa82102e03d8\",\"font\":\"方正小标宋_GBK\",\"fontEng\":\"Times New Roman\",\"fontSize\":22,\"paragraphBefore\":0,\"paragraphAfter\":0,\"lineSpacingType\":\"02\",\"lineSpacingValue\":28,\"indentLeft\":0,\"indentRight\":0,\"indentSpecial\":\"99\",\"indentSpecialValue\":0,\"align\":\"02\",\"margins\":null,\"marginsTop\":2.54,\"marginsBottom\":2.54,\"marginsLeft\":3.18,\"marginsRight\":3.18,\"bold\":false,\"italic\":false,\"tableBorderWidth\":1,\"tableAlign\":\"02\",\"headerUpperMargin\":1.5,\"headerUnderline\":true,\"footerBottomMargin\":1.75,\"footerPageNumber\":true,\"footerPageNumberCover\":false},{\"id\":\"1c174afb06864a1ca4f5cb305b4ea783\",\"category\":\"11\",\"opsId\":\"2978f3903d8442c9ba11fa82102e03d8\",\"font\":\"方正黑体_GBK\",\"fontEng\":\"Times New Roman\",\"fontSize\":16,\"paragraphBefore\":0,\"paragraphAfter\":0,\"lineSpacingType\":\"02\",\"lineSpacingValue\":28,\"indentLeft\":0,\"indentRight\":0,\"indentSpecial\":\"99\",\"indentSpecialValue\":0,\"align\":\"01\",\"margins\":null,\"marginsTop\":2.54,\"marginsBottom\":2.54,\"marginsLeft\":3.18,\"marginsRight\":3.18,\"bold\":false,\"italic\":false,\"tableBorderWidth\":1,\"tableAlign\":\"02\",\"headerUpperMargin\":1.5,\"headerUnderline\":true,\"footerBottomMargin\":1.75,\"footerPageNumber\":true,\"footerPageNumberCover\":false},{\"id\":\"884bcd010c244cf99119a5d599c37b7b\",\"category\":\"12\",\"opsId\":\"2978f3903d8442c9ba11fa82102e03d8\",\"font\":\"方正楷体_GBK\",\"fontEng\":\"Times New Roman\",\"fontSize\":16,\"paragraphBefore\":0,\"paragraphAfter\":0,\"lineSpacingType\":\"02\",\"lineSpacingValue\":28,\"indentLeft\":0,\"indentRight\":0,\"indentSpecial\":\"99\",\"indentSpecialValue\":0,\"align\":\"01\",\"margins\":null,\"marginsTop\":2.54,\"marginsBottom\":2.54,\"marginsLeft\":3.18,\"marginsRight\":3.18,\"bold\":false,\"italic\":false,\"tableBorderWidth\":1,\"tableAlign\":\"02\",\"headerUpperMargin\":1.5,\"headerUnderline\":true,\"footerBottomMargin\":1.75,\"footerPageNumber\":true,\"footerPageNumberCover\":false},{\"id\":\"d0320c6bb6e64e538b9c73b710fc138f\",\"category\":\"13\",\"opsId\":\"2978f3903d8442c9ba11fa82102e03d8\",\"font\":\"方正仿宋_GBK\",\"fontEng\":\"Times New Roman\",\"fontSize\":16,\"paragraphBefore\":0,\"paragraphAfter\":0,\"lineSpacingType\":\"02\",\"lineSpacingValue\":28,\"indentLeft\":0,\"indentRight\":0,\"indentSpecial\":\"99\",\"indentSpecialValue\":0,\"align\":\"01\",\"margins\":null,\"marginsTop\":2.54,\"marginsBottom\":2.54,\"marginsLeft\":3.18,\"marginsRight\":3.18,\"bold\":true,\"italic\":false,\"tableBorderWidth\":1,\"tableAlign\":\"02\",\"headerUpperMargin\":1.5,\"headerUnderline\":true,\"footerBottomMargin\":1.75,\"footerPageNumber\":true,\"footerPageNumberCover\":false},{\"id\":\"f94134bbae89462d94d32b23d5db57e7\",\"category\":\"14\",\"opsId\":\"2978f3903d8442c9ba11fa82102e03d8\",\"font\":\"方正仿宋_GBK\",\"fontEng\":\"Times New Roman\",\"fontSize\":16,\"paragraphBefore\":0,\"paragraphAfter\":0,\"lineSpacingType\":\"02\",\"lineSpacingValue\":28,\"indentLeft\":0,\"indentRight\":0,\"indentSpecial\":\"99\",\"indentSpecialValue\":0,\"align\":\"01\",\"margins\":null,\"marginsTop\":2.54,\"marginsBottom\":2.54,\"marginsLeft\":3.18,\"marginsRight\":3.18,\"bold\":false,\"italic\":false,\"tableBorderWidth\":1,\"tableAlign\":\"02\",\"headerUpperMargin\":1.5,\"headerUnderline\":true,\"footerBottomMargin\":1.75,\"footerPageNumber\":true,\"footerPageNumberCover\":false},{\"id\":\"b7913be899854c0eab9820dede8b8eae\",\"category\":\"01\",\"opsId\":\"2978f3903d8442c9ba11fa82102e03d8\",\"font\":\"方正仿宋_GBK\",\"fontEng\":\"Times New Roman\",\"fontSize\":16,\"paragraphBefore\":0,\"paragraphAfter\":0,\"lineSpacingType\":\"02\",\"lineSpacingValue\":28,\"indentLeft\":0,\"indentRight\":0,\"indentSpecial\":\"01\",\"indentSpecialValue\":2,\"align\":\"01\",\"margins\":null,\"marginsTop\":2.54,\"marginsBottom\":2.54,\"marginsLeft\":3.18,\"marginsRight\":3.18,\"bold\":false,\"italic\":false,\"tableBorderWidth\":1,\"tableAlign\":\"02\",\"headerUpperMargin\":1.5,\"headerUnderline\":true,\"footerBottomMargin\":1.75,\"footerPageNumber\":true,\"footerPageNumberCover\":false},{\"id\":\"96ac5b31742e410aa462642954f6b3b9\",\"category\":\"02\",\"opsId\":\"2978f3903d8442c9ba11fa82102e03d8\",\"font\":\"方正仿宋_GBK\",\"fontEng\":\"Times New Roman\",\"fontSize\":16,\"paragraphBefore\":0,\"paragraphAfter\":0,\"lineSpacingType\":\"02\",\"lineSpacingValue\":28,\"indentLeft\":0,\"indentRight\":0,\"indentSpecial\":\"99\",\"indentSpecialValue\":0,\"align\":\"01\",\"margins\":null,\"marginsTop\":2.54,\"marginsBottom\":2.54,\"marginsLeft\":3.18,\"marginsRight\":3.18,\"bold\":false,\"italic\":false,\"tableBorderWidth\":1,\"tableAlign\":\"02\",\"headerUpperMargin\":1.5,\"headerUnderline\":true,\"footerBottomMargin\":1.75,\"footerPageNumber\":true,\"footerPageNumberCover\":false},{\"id\":\"9186658b321a490ba193de6f61ff656c\",\"category\":\"03\",\"opsId\":\"2978f3903d8442c9ba11fa82102e03d8\",\"font\":\"方正仿宋_GBK\",\"fontEng\":\"Times New Roman\",\"fontSize\":16,\"paragraphBefore\":0,\"paragraphAfter\":0,\"lineSpacingType\":\"01\",\"lineSpacingValue\":1,\"indentLeft\":0,\"indentRight\":0,\"indentSpecial\":\"99\",\"indentSpecialValue\":0,\"align\":\"02\",\"margins\":null,\"marginsTop\":2.54,\"marginsBottom\":2.54,\"marginsLeft\":3.18,\"marginsRight\":3.18,\"bold\":false,\"italic\":false,\"tableBorderWidth\":1,\"tableAlign\":\"02\",\"headerUpperMargin\":1.5,\"headerUnderline\":true,\"footerBottomMargin\":1.75,\"footerPageNumber\":true,\"footerPageNumberCover\":false},{\"id\":\"36a97f53381f406094bd17ce5cae6539\",\"category\":\"04\",\"opsId\":\"2978f3903d8442c9ba11fa82102e03d8\",\"font\":\"方正仿宋_GBK\",\"fontEng\":\"Times New Roman\",\"fontSize\":16,\"paragraphBefore\":0,\"paragraphAfter\":0,\"lineSpacingType\":\"02\",\"lineSpacingValue\":28,\"indentLeft\":0,\"indentRight\":0,\"indentSpecial\":\"99\",\"indentSpecialValue\":0,\"align\":\"01\",\"margins\":null,\"marginsTop\":2.54,\"marginsBottom\":2.54,\"marginsLeft\":3.18,\"marginsRight\":3.18,\"bold\":false,\"italic\":false,\"tableBorderWidth\":1,\"tableAlign\":\"02\",\"headerUpperMargin\":1.5,\"headerUnderline\":true,\"footerBottomMargin\":1.75,\"footerPageNumber\":true,\"footerPageNumberCover\":false},{\"id\":\"48a3cb87d9a54659b053dde08fd2f249\",\"category\":\"05\",\"opsId\":\"2978f3903d8442c9ba11fa82102e03d8\",\"font\":\"方正仿宋_GBK\",\"fontEng\":\"Times New Roman\",\"fontSize\":9,\"paragraphBefore\":0,\"paragraphAfter\":0,\"lineSpacingType\":\"01\",\"lineSpacingValue\":1,\"indentLeft\":0,\"indentRight\":0,\"indentSpecial\":\"99\",\"indentSpecialValue\":0,\"align\":\"03\",\"margins\":null,\"marginsTop\":2.54,\"marginsBottom\":2.54,\"marginsLeft\":3.18,\"marginsRight\":3.18,\"bold\":false,\"italic\":false,\"tableBorderWidth\":0.5,\"tableAlign\":\"02\",\"headerUpperMargin\":1.5,\"headerUnderline\":true,\"footerBottomMargin\":1.75,\"footerPageNumber\":true,\"footerPageNumberCover\":false},{\"id\":\"1bd402a520804118ac3df8c5262f6793\",\"category\":\"06\",\"opsId\":\"2978f3903d8442c9ba11fa82102e03d8\",\"font\":\"方正仿宋_GBK\",\"fontEng\":\"Times New Roman\",\"fontSize\":9,\"paragraphBefore\":0,\"paragraphAfter\":0,\"lineSpacingType\":\"01\",\"lineSpacingValue\":1,\"indentLeft\":0,\"indentRight\":0,\"indentSpecial\":\"99\",\"indentSpecialValue\":0,\"align\":\"02\",\"margins\":null,\"marginsTop\":2.54,\"marginsBottom\":2.54,\"marginsLeft\":3.18,\"marginsRight\":3.18,\"bold\":false,\"italic\":false,\"tableBorderWidth\":1,\"tableAlign\":\"02\",\"headerUpperMargin\":1.5,\"headerUnderline\":true,\"footerBottomMargin\":1.75,\"footerPageNumber\":true,\"footerPageNumberCover\":false},{\"id\":\"74ac1171f13b4a03aa46324e698d0872\",\"category\":\"17\",\"opsId\":\"2978f3903d8442c9ba11fa82102e03d8\",\"font\":\"方正仿宋_GBK\",\"fontEng\":\"Times New Roman\",\"fontSize\":\"12\",\"paragraphBefore\":0,\"paragraphAfter\":0,\"lineSpacingType\":\"01\",\"lineSpacingValue\":1,\"indentLeft\":0,\"indentRight\":0,\"indentSpecial\":\"99\",\"indentSpecialValue\":0,\"align\":\"01\",\"margins\":null,\"marginsTop\":2.54,\"marginsBottom\":2.54,\"marginsLeft\":3.18,\"marginsRight\":3.18,\"bold\":false,\"italic\":false,\"tableBorderWidth\":1,\"tableAlign\":\"02\",\"headerUpperMargin\":1.5,\"headerUnderline\":true,\"footerBottomMargin\":1.75,\"footerPageNumber\":false,\"footerPageNumberCover\":false}]}";
        String style02Json = "{\"id\":\"f00af8a12c744c25abf05593c4cdaafb\",\"createUid\":\"abf2cb0b1c284efb968293b7190096ec\",\"createUname\":\"test\",\"createTime\":\"2025-08-20T03:27:49.275+00:00\",\"state\":7,\"stateTime\":\"2025-08-20T03:27:49.350+00:00\",\"category\":\"02\",\"status\":true,\"styleName\":\"预览样式\",\"styleList\":[{\"id\":\"cb3baa2183044deb8d730dab3739be81\",\"category\":\"01\",\"opsId\":\"f00af8a12c744c25abf05593c4cdaafb\",\"font\":\"Segoe UI', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', 'Helvetica Neue', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbo\",\"fontEng\":\"Times New Roman\",\"fontSize\":12,\"paragraphBefore\":0,\"paragraphAfter\":0,\"lineSpacingType\":\"01\",\"lineSpacingValue\":1.6,\"indentLeft\":0,\"indentRight\":0,\"indentSpecial\":\"01\",\"indentSpecialValue\":2,\"align\":\"01\",\"margins\":null,\"marginsTop\":2.54,\"marginsBottom\":2.54,\"marginsLeft\":3.18,\"marginsRight\":3.18,\"bold\":false,\"italic\":false,\"tableBorderWidth\":1,\"tableAlign\":\"02\",\"headerUpperMargin\":1.5,\"headerUnderline\":true,\"footerBottomMargin\":1.75,\"footerPageNumber\":true,\"footerPageNumberCover\":false},{\"id\":\"ce0ca1ea396e43598e5d20b6037744d9\",\"category\":\"02\",\"opsId\":\"f00af8a12c744c25abf05593c4cdaafb\",\"font\":\"Segoe UI', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', 'Helvetica Neue', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbo\",\"fontEng\":\"Times New Roman\",\"fontSize\":12,\"paragraphBefore\":0,\"paragraphAfter\":0,\"lineSpacingType\":\"01\",\"lineSpacingValue\":1.6,\"indentLeft\":0,\"indentRight\":0,\"indentSpecial\":\"99\",\"indentSpecialValue\":0,\"align\":\"01\",\"margins\":null,\"marginsTop\":2.54,\"marginsBottom\":2.54,\"marginsLeft\":3.18,\"marginsRight\":3.18,\"bold\":false,\"italic\":false,\"tableBorderWidth\":1,\"tableAlign\":\"02\",\"headerUpperMargin\":1.5,\"headerUnderline\":true,\"footerBottomMargin\":1.75,\"footerPageNumber\":true,\"footerPageNumberCover\":false},{\"id\":\"a5a0dd7a481d44c5bf1fc9a24639751d\",\"category\":\"03\",\"opsId\":\"f00af8a12c744c25abf05593c4cdaafb\",\"font\":\"Segoe UI', 'PingFang SC', 'Hiragino Sans GB', 'Microsoft YaHei', 'Helvetica Neue', Helvetica, Arial, sans-serif, 'Apple Color Emoji', 'Segoe UI Emoji', 'Segoe UI Symbo\",\"fontEng\":\"Times New Roman\",\"fontSize\":12,\"paragraphBefore\":0,\"paragraphAfter\":0,\"lineSpacingType\":\"01\",\"lineSpacingValue\":1.6,\"indentLeft\":0,\"indentRight\":0,\"indentSpecial\":\"99\",\"indentSpecialValue\":0,\"align\":\"02\",\"margins\":null,\"marginsTop\":2.54,\"marginsBottom\":2.54,\"marginsLeft\":3.18,\"marginsRight\":3.18,\"bold\":false,\"italic\":false,\"tableBorderWidth\":1,\"tableAlign\":\"02\",\"headerUpperMargin\":1.5,\"headerUnderline\":true,\"footerBottomMargin\":1.75,\"footerPageNumber\":true,\"footerPageNumberCover\":false}]}";
        OutlineProjectStyleRequest style01 = JacksonUtils.parseObject(style01Json, OutlineProjectStyleRequest.class);
        OutlineProjectStyleRequest style02 = JacksonUtils.parseObject(style02Json, OutlineProjectStyleRequest.class);

        // 处理
        style01.setId(null);
        style01.setStateTime(new Date());
        style01.getStyleList().forEach(style -> {
            style.setId(null);
            style.setOpsId(null);
        });
        style02.setId(null);
        style02.setStateTime(new Date());
        style02.getStyleList().forEach(style -> {
            style.setId(null);
            style.setOpsId(null);
        });
        // 保存
        this.createOrUpdateOutlineStyle(style01, token);
        this.createOrUpdateOutlineStyle(style02, token);
        // 存入缓存
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                getStyleDtlMapByCategory("01");
                getStyleDtlMapByCategory("02");
            }
        });
    }

    /**
     * 获取样式详情映射
     *
     * @param category
     * @return
     */
    @Override
    public Map<String, OutlineProjectStyleDtl> getStyleDtlMapByCategory(String category) {
        String key = "OutlineProjectStyle" + category;
        Object o = redisUtils.get(key);
        if (ValidateUtil.isNotNull(o)) {
            return JSON.parseObject(o.toString(), new TypeReference<Map<String, OutlineProjectStyleDtl>>() {
            });
        }

        // 样式获取
        OutlineProjectStyle style = this.getOne(Wrappers.<OutlineProjectStyle>lambdaQuery()
                .eq(OutlineProjectStyle::getState, ApprovalStatusEnum.Already_Over.getIndex())
                .eq(OutlineProjectStyle::getStatus, true)
                .eq(OutlineProjectStyle::getCategory, category)
        );
        if (ValidateUtil.isNull(style)) {
            log.error("未找到有效的样式配置");
            throw new JPMException("未找到有效的样式配置");
        }
        // 样式详情获取
        List<OutlineProjectStyleDtl> styleDtlIds = outlineProjectStyleDtlService.list(Wrappers.<OutlineProjectStyleDtl>lambdaQuery()
                .eq(OutlineProjectStyleDtl::getOpsId, style.getId())
        );
        if (ValidateUtil.isEmpty(styleDtlIds)) {
            log.error("未找到样式详情");
            throw new JPMException("未找到样式详情");
        }
        Map<String, OutlineProjectStyleDtl> styleDtlMap = new HashMap<>();
        for (OutlineProjectStyleDtl styleDtl : styleDtlIds) {
            styleDtlMap.put(styleDtl.getCategory(), styleDtl);
        }

        // 将样式详情映射存入Redis
        redisUtils.set(key, JSON.toJSONString(styleDtlMap, JSONWriter.Feature.ReferenceDetection));
        return styleDtlMap;
    }

    public void clearStyleDtlCache(String category) {
        String key = "OutlineProjectStyle" + category;
        redisUtils.delete(key);
    }
}
