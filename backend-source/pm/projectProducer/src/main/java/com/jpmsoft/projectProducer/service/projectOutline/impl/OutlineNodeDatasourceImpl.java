package com.jpmsoft.projectProducer.service.projectOutline.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.model.request.projectOutline.OutlineNodeDatasourceRequest;
import com.jpmsoft.common.model.response.projectOutline.OutlineDatasourceTempResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineNodeDatasourceResponse;
import com.jpmsoft.common.model.response.projectOutline.OutlineSystemLibResponse;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.projectOutline.OutlineNodeDatasourceMapper;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineDatasource;
import com.jpmsoft.projectProducer.pojo.entity.projectOutline.OutlineNodeDatasource;
import com.jpmsoft.projectProducer.pojo.vo.sys.LogUserInfoVo;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineNodeDatasourceService;
import com.jpmsoft.projectProducer.service.projectOutline.IOutlineSystemLibService;
import com.jpmsoft.projectProducer.utils.FreemarkerUtils;
import com.jpmsoft.projectProducer.utils.RedisUtils;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author tianhelin
 * @date 2025/6/11
 */
@Service
public class OutlineNodeDatasourceImpl extends ServiceImpl<OutlineNodeDatasourceMapper, OutlineNodeDatasource> implements IOutlineNodeDatasourceService {
    @Resource
    private RedisUtils redisUtils;
    @Resource
    private IOutlineSystemLibService outlineSystemLibService;
    @Resource
    private ISysConfigService sysConfigService;
    @Resource
    private RestTemplate restTemplate;

    /**
     * 体系模板-目录节点ID获取体系库节点数据源
     * 用于复制
     *
     * @param oslIds
     * @return
     */
    @Override
    public List<OutlineNodeDatasourceResponse> getAllOutlineNodeDatasourceByOslIds(List<String> oslIds) {
        if (ValidateUtil.isNotEmpty(oslIds)) {
            List<OutlineNodeDatasourceResponse> list = baseMapper.getAllOutlineNodeDatasourceByOslIds(oslIds);
            if (ValidateUtil.isNotEmpty(list)) {
                return list;
            }
        }
        return Collections.emptyList();
    }

    @Override
    public BaseResponse createOutlineNodeDatasource(OutlineNodeDatasourceRequest request, String token) {
        if (datasourceExist(request.getOdId(), request.getOslId())) return BaseResponse.fail(ResMsg.recordExist);
        OutlineNodeDatasource entity = new OutlineNodeDatasource();
        BeanUtils.copyProperties(request, entity);
        entity.setId(GenerateUUID.getUUID());
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        entity.setCreateTime(new Date());
        entity.setCreateUname(user.getName());
        entity.setCreateUid(user.getId());
        baseMapper.insert(entity);
        outlineSystemLibService.refreshSystemManageLastUpdateTime(request.getOslId(), user);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getOutlineNodeDatasourceList(OutlineNodeDatasourceRequest request) {
        IPage<OutlineNodeDatasourceResponse> page = new Page<>(request.getPage(), request.getSize());
        List<OutlineNodeDatasourceResponse> data = baseMapper.getOutlineNodeDatasourceList(page, request);
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public Boolean datasourceExist(String odId, String oslId) {
        LambdaQueryWrapper<OutlineNodeDatasource> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(OutlineNodeDatasource::getOdId, odId).eq(OutlineNodeDatasource::getOslId, oslId);
        return baseMapper.exists(wrapper);
    }

    @Override
    public void deleteByOslId(String oslId) {
        baseMapper.deleteByOslId(oslId);
    }

    @Override
    public void deleteByOsmId(String osmId) {
        baseMapper.deleteByOsmId(osmId);
    }

    @Override
    public BaseResponse updateOutlineNodeDatasource(OutlineNodeDatasourceRequest request, String token) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        if (request.getId() == null || !datasourceExist(request.getOdId(), request.getOslId())) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        OutlineNodeDatasource entity = new OutlineNodeDatasource();
        BeanUtils.copyProperties(request, entity);
        baseMapper.insert(entity);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineSystemLibService.refreshSystemManageLastUpdateTime(request.getOslId(), user);
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse deleteOutlineNodeDatasourceById(String id, String token) {
        OutlineNodeDatasource datasource = this.getById(id);
        if (ValidateUtil.isNull(datasource)) {
            return BaseResponse.fail(ResMsg.infoNotExist);
        }
        this.removeById(id);
        LogUserInfoVo user = redisUtils.getUserInfoByToken(token);
        outlineSystemLibService.refreshSystemManageLastUpdateTime(datasource.getOslId(), user);
        return BaseResponse.ok();
    }

    /**
     * 处理数据源渲染
     *
     * @param osl
     * @param pId
     * @return
     */
    @Override
    public String handleDatasourceRender(OutlineSystemLibResponse osl, String pId) {
        if (ValidateUtil.isNull(osl) || ValidateUtil.isEmpty(osl.getOslContent())) {
            return null;
        }
        // 根据oslId获取对应的项目大纲体系节点数据源配置
        List<OutlineNodeDatasourceResponse> datasourceList = this.getAllOutlineNodeDatasourceByOslIds(Collections.singletonList(osl.getId()));
        // 获取模板
        String html = osl.getOslContent();
        return handleDatasourceRender(html, datasourceList, pId, Collections.emptyList());
    }

    /**
     * 处理数据源渲染
     *
     * @param osl
     * @param pId
     * @param aliasesMap
     * @return
     */
    @Override
    public String handleDatasourceRender(OutlineSystemLibResponse osl, String pId, Map<String, List<String>> aliasesMap) {
        if (ValidateUtil.isNull(osl) || ValidateUtil.isEmpty(osl.getOslContent())) {
            return null;
        }
        // 根据oslId获取对应的项目大纲体系节点数据源配置
        List<OutlineNodeDatasourceResponse> datasourceList = this.getAllOutlineNodeDatasourceByOslIds(Collections.singletonList(osl.getId()));
        // 获取模板
        String html = osl.getOslContent();
        return handleDatasourceRender(html, datasourceList, pId, aliasesMap.get(osl.getId()));
    }

    /**
     * 处理数据源渲染
     *
     * @param osl
     * @param pId
     * @param datasourceList
     * @param aliasesMap
     * @return
     */
    @Override
    public String handleDatasourceRender(OutlineSystemLibResponse osl, String pId, List<OutlineNodeDatasourceResponse> datasourceList, Map<String, List<String>> aliasesMap) {
        if (ValidateUtil.isNull(osl) || ValidateUtil.isEmpty(osl.getOslContent())) {
            return null;
        }
        // 获取模板
        String html = osl.getOslContent();
        return handleDatasourceRender(html, datasourceList, pId, aliasesMap.get(osl.getId()));
    }

    /**
     * 处理数据源渲染
     *
     * @param html
     * @param datasourceList
     * @param pId
     * @param aliases
     * @return
     */
    @Override
    public String handleDatasourceRender(String html, List<OutlineNodeDatasourceResponse> datasourceList, String pId, List<String> aliases) {
        // 如果没有数据源配置，则直接返回原始HTML
        if (ValidateUtil.isEmpty(datasourceList)) {
            return html;
        }
        // 处理数据源
        Map<String, Object> model = this.handleDatasource(datasourceList, pId);
        // 去除空的数据源的占位
        // html = FreemarkerUtils.cleanByMissingDataSource(html, model);
        // 处理模板
        html = FreemarkerUtils.autoWrapMultipleListTemplates(html);

        html = FreemarkerUtils.protectMissingPlaceholders(html, model);
        // 渲染内容
        String rendered = FreemarkerUtils.renderContent(html, model);
        if (ValidateUtil.isNotEmpty(aliases)) {
            // 删除指定的模板
            rendered = FreemarkerUtils.cleanByAliases(rendered, aliases);
        }
        // 返回处理后的内容
        return rendered;
    }

    /**
     * 处理数据源渲染
     *
     * @param htmls
     * @param datasourceList
     * @param pId
     * @return
     */
    @Override
    public List<String> batchHandleDatasourceRender(List<String> htmls, List<OutlineNodeDatasourceResponse> datasourceList, String pId) {
        final String empty = "<!--empty-->";
        List<String> safeHtmls = htmls.stream()
                .map(s -> (s == null || s.isEmpty()) ? empty : s)
                .collect(Collectors.toList());

        if (ValidateUtil.isEmpty(datasourceList)) {
            // 还原占位符
            return safeHtmls.stream()
                    .map(s -> empty.equals(s) ? "" : s)
                    .collect(Collectors.toList());
        }

        Map<String, Object> model = this.handleDatasource(datasourceList, pId);
        if (ValidateUtil.isEmpty(model)) {
            return safeHtmls.stream()
                    .map(s -> empty.equals(s) ? "" : s)
                    .collect(Collectors.toList());
        }

        String html = String.join("<!--split-->", safeHtmls);
        html = FreemarkerUtils.autoWrapMultipleListTemplates(html);

        html = FreemarkerUtils.protectMissingPlaceholders(html, model);
        // 渲染内容
        String rendered = FreemarkerUtils.renderContent(html, model);
        String[] splitHtmls = rendered.split("<!--split-->", -1);

        // 还原占位符
        return Arrays.stream(splitHtmls)
                .map(s -> empty.equals(s) ? "" : s)
                .collect(Collectors.toList());
    }

    private Map<String, Object> handleDatasource(List<OutlineNodeDatasourceResponse> datasourceList, String pId) {
        // 获取接口地址前缀
        String prefixApiUrl = sysConfigService.getConfigByCode("apiUrl");
        if (ValidateUtil.isEmpty(prefixApiUrl)) {
            // 默认值
            prefixApiUrl = "http://localhost:8085";
        }
        // 数据源
        Map<String, Object> model = new HashMap<>();
        // 将数据源按照类型分成三类
        for (OutlineNodeDatasourceResponse datasource : datasourceList) {
            String odType = datasource.getOdType();
            switch (odType) {
                case "01":
                    // 处理SQL数据源
                    handleSqlDatasource(datasource, pId, model);
                    break;
                case "02":
                    // 处理JSON数据源
                    handleJsonDatasource(datasource, model);
                    break;
                case "03":
                    // 处理接口数据源
                    handleApiDatasource(datasource, prefixApiUrl, pId, model);
                    break;
                default:
                    // 其他类型不处理
                    break;
            }
        }
        // 如果模板中有存在这个key但是值为null或空的话，将这些数据源剔除
        // model = model.entrySet().stream()
        //         .filter(e -> e.getValue() != null && !(e.getValue() instanceof Collection && ((Collection<?>) e.getValue()).isEmpty()))
        //         .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        // 返回处理后的数据源
        return model;
    }

    /**
     * 处理SQL数据源
     *
     * @param ds
     * @param pId
     * @param model
     */
    private void handleSqlDatasource(OutlineNodeDatasourceResponse ds, String pId, Map<String, Object> model) {
        String sql = ds.getOdSource();
        if (ValidateUtil.isEmpty(sql)) {
            return;
        }
        // 判断sql中是否有pId的占位符
        if (ValidateUtil.isNotEmpty(pId) && sql.contains("#{pId}")) {
            sql = sql.replace("#{pId}", pId);
        } else if (sql.contains("#{pId}")) {
            // 没有pId，但是sql中有#{pId}的占位符，跳过这个数据源
            return;
        }
        // 执行SQL查询
        List<Map<String, Object>> maps = baseMapper.executeSqlQuery(sql);
        if (ValidateUtil.isEmpty(maps)) {
            return;
        }
        // 将查询结果放入数据源
        model.put(ds.getOdAlias(), maps);
    }

    /**
     * 处理JSON数据源
     *
     * @param ds
     * @param model
     */
    private void handleJsonDatasource(OutlineNodeDatasourceResponse ds, Map<String, Object> model) {
        String json = ds.getOdSource();
        if (ValidateUtil.isEmpty(json)) {
            return;
        }
        // 将JSON字符串转换为Map
        if (JSON.isValidArray(json)) {
            // 如果是JSON数组，则转换为List<Map<String, Object>>
            List<Map> jsonList = JSON.parseArray(json, Map.class);
            if (ValidateUtil.isEmpty(jsonList)) {
                return;
            }
            // 将JSON数据源放入数据源
            model.put(ds.getOdAlias(), jsonList);
            return;
        }
        if (JSON.isValid(json)) {
            Map<String, Object> jsonMap = JSON.parseObject(json, Map.class);
            if (ValidateUtil.isEmpty(jsonMap)) {
                return;
            }
            // 将JSON数据源放入数据源
            model.put(ds.getOdAlias(), jsonMap);
        }
    }

    /**
     * 处理接口数据源
     *
     * @param ds
     * @param prefixApiUrl
     * @param pId
     * @param model
     */
    private void handleApiDatasource(OutlineNodeDatasourceResponse ds, String prefixApiUrl, String pId, Map<String, Object> model) {
        String url = ds.getOdAddress();
        String method = ds.getOdMethod();
        String body = ds.getOdBody();
        if (ValidateUtil.isEmpty(url) || ValidateUtil.isEmpty(method)) {
            return;
        }
        // 如果apiUrl不是以http开头，则拼接前缀
        if (!url.startsWith("http")) {
            url = prefixApiUrl + url;
        }
        String res = null;
        // body中的pId预处理
        if (ValidateUtil.isNotEmpty(body) && ValidateUtil.isNotEmpty(pId) && body.contains("#{pId}")) {
            body = body.replace("#{pId}", pId);
        } else if (body.contains("#{pId}") && ValidateUtil.isEmpty(pId)) {
            // 没有pId的话，暂时跳过这个数据源
            return;
        }
        // 调用接口获取数据
        if ("GET".equalsIgnoreCase(method)) {
            // 处理GET请求
            if (ValidateUtil.isNotEmpty(body)) {
                // res = HttpUtil.get(url, JSON.parseObject(body, Map.class));
                // res = HttpRequest.get(url)
                //         .body(body)
                //         .timeout(20000)
                //         .execute()
                //         .body();
                // 拼接到url后面
                if (url.contains("?")) {
                    url += body;
                } else {
                    url += "?" + body;
                }
                res = restTemplate.getForObject(url, String.class);
            } else {
                // res = HttpRequest.get(url)
                //         .timeout(20000)
                //         .execute()
                //         .body();
                res = restTemplate.getForObject(url, String.class);
            }
        } else if ("POST".equalsIgnoreCase(method)) {
            // 处理POST请求
            // res = HttpRequest.post(url)
            //         .body(body)
            //         .timeout(20000)
            //         .execute()
            //         .body();
            res = restTemplate.postForObject(url, assemblyRequestParam(JSON.parseObject(body, Map.class)), String.class);
        }
        if (ValidateUtil.isNotEmpty(res)) {
            Map<String, Object> map = JSON.parseObject(res, Map.class);
            Boolean success = (Boolean) map.get("success");
            if (success) {
                Object data = map.get("data");
                if (ValidateUtil.isNotNull(data)) {
                    if (JSON.isValidArray(data.toString())) {
                        model.put(ds.getOdAlias(), JSON.parseArray(data.toString(), Map.class));
                    } else {
                        model.put(ds.getOdAlias(), JSON.parseObject(data.toString(), Map.class));
                    }
                }
            }
        }
    }

    /**
     * 获取所有数据源ID和别名的映射
     *
     * @return
     */
    @Override
    public Map<String, String> getOdIdAndAliasMap() {
        // 查询所有的数据源
        List<OutlineDatasource> list = baseMapper.getAllOutlineDatasource();
        if (ValidateUtil.isNotEmpty(list)) {
            return list.stream().collect(Collectors.toMap(OutlineDatasource::getOdAlias, OutlineDatasource::getId, (v1, v2) -> v1));
        }
        return Collections.emptyMap();
    }

    /**
     * 根据odIds获取对应的odId和tcode
     *
     * @param odIds
     * @return
     */
    @Override
    public Map<String, Set<String>> getTCodesByOdIds(List<String> odIds) {
        if (ValidateUtil.isNotEmpty(odIds)) {
            List<OutlineDatasourceTempResponse> list = baseMapper.getTCodesByOdIds(odIds);
            if (ValidateUtil.isNotEmpty(list)) {
                return list.stream()
                        .collect(Collectors.groupingBy(OutlineDatasourceTempResponse::getOdId, Collectors.mapping(OutlineDatasourceTempResponse::getTCode, Collectors.toSet())));
            }
        }
        return Collections.emptyMap();
    }

    private HttpEntity<String> assemblyRequestParam(Object object) {
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity;
        if (object == null) formEntity = new HttpEntity<>(headers);
        else
            formEntity = new HttpEntity<>(JacksonUtils.toJSONString(object), headers);
        return formEntity;
    }
}
