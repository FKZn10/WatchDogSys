package com.jpmsoft.projectProducer.service.design.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.enums.OutUserAuthEnum;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.model.requestAndResponse.Design.DesignDrawingModel;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.design.DesignDrawingMapper;
import com.jpmsoft.projectProducer.pojo.dto.system.DictDto;
import com.jpmsoft.projectProducer.pojo.entity.design.DesignDrawing;
import com.jpmsoft.projectProducer.service.design.IDesignDrawingService;
import com.jpmsoft.projectProducer.service.other.ISysConfigService;
import com.jpmsoft.projectProducer.service.other.ISysUserService;
import com.jpmsoft.projectProducer.utils.DictionaryUtils;
import com.jpmsoft.projectProducer.utils.extension.FileImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DesignDrawingImpl extends FileImpl<DesignDrawingMapper, DesignDrawing, DesignDrawingModel> implements IDesignDrawingService {

    @Resource
    private ISysUserService sysUserService;
    @Resource
    private ISysConfigService sysConfigService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResponse createOrUpdateDesignDrawing(DesignDrawingModel request) {
        DesignDrawing entity = new DesignDrawing();
        return BaseResponse.ok(createOrUpdateFileEntity(entity, request));
    }

    @Override
    public BaseResponse getDesignDrawingList(DesignDrawingModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Design_Drawing", uId, "Dd_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Dd_State");
        if (ValidateUtil.isEmpty(authSql))
            return BaseResponse.fail(ResMsg.noPermission);
        QueryMap<DesignDrawingModel> params = getQueryMap(request, authSql);
        IPage<DesignDrawingModel> page = new Page<>(request.getPage(), request.getSize());
        List<DesignDrawingModel> datas = baseMapper.getDesignDrawingList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getDesignDrawingListNoAuth(DesignDrawingModel request) {
        QueryMap<DesignDrawingModel> params = getQueryMap(request, null);
        IPage<DesignDrawingModel> page = new Page<>(request.getPage(), request.getSize());
        List<DesignDrawingModel> datas = baseMapper.getDesignDrawingList(page, params.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    private QueryMap<DesignDrawingModel> getQueryMap(DesignDrawingModel request, String authSql) {
        QueryMap<DesignDrawingModel> params = new QueryMap<>(request);
        params.put("authSql", authSql).put("pId").put("pName").put("pNameHistory")
                .put("ddName").put("ddType").put("ddVersionNumber").put("ddRemark")
                .put("ddCreateUname").put("ddState").put("ddCategory")
                .put(request.getDdCreateTimes(), "ddCreateTimeStart", "ddCreateTimeEnd")
                .put("ddMonomer").put("ddProfessional").put("ddFileFormat");
        return params;
    }


    @Override
    public BaseResponse getDesignDrawingById(String ddId) {
        return BaseResponse.ok(baseMapper.getDesignDrawingById(ddId));
    }

    @Override
    public BaseResponse getDesignDrawingListRW(DesignDrawingModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Design_Drawing", uId, "Dd_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Dd_State");
        if (ValidateUtil.isEmpty(authSql)) {
            return BaseResponse.fail(ResMsg.noPermission);
        }

        QueryMap<DesignDrawingModel> params = getQueryMap(request, authSql);
        List<DesignDrawingModel> datas = baseMapper.getDesignDrawingList(null, params.getQuery());
        if (ValidateUtil.isEmpty(datas)) {
            return BaseResponse.ok(Collections.emptyList());
        }

        // 图纸分类
        LinkedHashMap<String, String> tzflDictMap = getSortedDictMap("JPMsoft_TZFL");
        // 图纸专业
        LinkedHashMap<String, String> tzzyDictMap = getSortedDictMap("JPMsoft_TZZY_ZW");
        // 单体
        LinkedHashMap<String, String> xcdgjzDictMap = getSortedDictMap("JPMsoft_XCDGJZ");

        // 提取所有出现的PName和DdType编码
        Set<String> allPNames = datas.stream()
                .map(DesignDrawingModel::getPName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        List<String> allDdCategorys = new ArrayList<>(tzflDictMap.keySet());

        // 读取配置，默认值01
        String designDrawingTreeLevel = sysConfigService.getConfigByCode("DesignDrawingTreeLevel");
        Set<String> threeLayerCodes = Optional.ofNullable(designDrawingTreeLevel)
                .map(str -> Arrays.stream(str.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toSet()))
                .orElse(new HashSet<>(Collections.singletonList("01")));

        // 构造两个分组map
        Map<String, List<DesignDrawingModel>> twoLayerMap = new LinkedHashMap<>();
        Map<String, Map<String, Map<String, List<DesignDrawingModel>>>> threeLayerMap = new LinkedHashMap<>();

        for (DesignDrawingModel d : datas) {
            String rawDdCategory = Optional.ofNullable(d.getDdCategory()).orElse("").trim();
            String ddCategory = rawDdCategory.isEmpty() ? "未分类" : rawDdCategory;
            if (!rawDdCategory.isEmpty() && !tzflDictMap.containsKey(ddCategory)) {
                continue;
            }
            String key = d.getPName() + "|" + ddCategory;
            if (threeLayerCodes.contains(ddCategory)) {
                threeLayerMap
                        .computeIfAbsent(key, k -> new LinkedHashMap<>())
                        .computeIfAbsent(Optional.ofNullable(d.getDdMonomer()).filter(s -> !s.isEmpty()).orElse("未分类"), k -> new LinkedHashMap<>())
                        .computeIfAbsent(Optional.ofNullable(d.getDdProfessional()).filter(s -> !s.isEmpty()).orElse("未分类"), k -> new ArrayList<>())
                        .add(d);
            } else {
                twoLayerMap.computeIfAbsent(key, k -> new ArrayList<>()).add(d);
            }
        }

        // 拼接树形结构
        List<DesignDrawingModel> result = new ArrayList<>();
        for (String pName : allPNames) {
            for (String ddCategoryCode : allDdCategorys) {
                String ddTypeName = tzflDictMap.getOrDefault(ddCategoryCode, "未分类");
                String key = pName + "|" + ddCategoryCode;

                DesignDrawingModel projectNode = new DesignDrawingModel();
                projectNode.setDdId(GenerateUUID.getUUID());
                projectNode.setPName(pName + "（" + ddTypeName + "）");

                // 判断三层或两层逻辑
                if (threeLayerCodes.contains(ddCategoryCode)) {
                    Map<String, Map<String, List<DesignDrawingModel>>> monomerMap = threeLayerMap.getOrDefault(key, new LinkedHashMap<>());
                    List<DesignDrawingModel> monomerList = new ArrayList<>();

                    // 先按字典顺序处理单体
                    xcdgjzDictMap.keySet().forEach(monomerKey -> {
                        if (!monomerMap.containsKey(monomerKey)) return;
                        Map<String, List<DesignDrawingModel>> professionalMap = monomerMap.get(monomerKey);
                        monomerList.add(buildMonomerNode(monomerKey, professionalMap, xcdgjzDictMap, tzzyDictMap));
                    });

                    // 处理单体中非字典数据
                    monomerMap.keySet().stream()
                            .filter(monomerKey -> !xcdgjzDictMap.containsKey(monomerKey) && !"未分类".equals(monomerKey))
                            .sorted()
                            .forEach(monomerKey -> {
                                Map<String, List<DesignDrawingModel>> professionalMap = monomerMap.get(monomerKey);
                                monomerList.add(buildMonomerNode(monomerKey, professionalMap, xcdgjzDictMap, tzzyDictMap));
                            });

                    // 处理单体未分类
                    if (monomerMap.containsKey("未分类")) {
                        Map<String, List<DesignDrawingModel>> professionalMap = monomerMap.get("未分类");
                        monomerList.add(buildMonomerNode("未分类", professionalMap, xcdgjzDictMap, tzzyDictMap));
                    }

                    projectNode.setChildDrawing(monomerList);
                } else {
                    List<DesignDrawingModel> leafList = twoLayerMap.getOrDefault(key, new ArrayList<>());
                    projectNode.setChildDrawing(leafList);
                }
                result.add(projectNode);
            }
        }

        return BaseResponse.ok(result);
    }

    private DesignDrawingModel buildMonomerNode(String monomerKey, Map<String, List<DesignDrawingModel>> professionalMap,
                                                LinkedHashMap<String, String> xcdgjzDictMap,
                                                LinkedHashMap<String, String> tzzyDictMap) {
        DesignDrawingModel monomerNode = new DesignDrawingModel();
        monomerNode.setDdId(GenerateUUID.getUUID());
        monomerNode.setPName(xcdgjzDictMap.getOrDefault(monomerKey, monomerKey));
        List<DesignDrawingModel> professionalList = new ArrayList<>();

        // 先按字典顺序处理专业
        tzzyDictMap.keySet().forEach(professionalKey -> {
            if (!professionalMap.containsKey(professionalKey)) return;
            List<DesignDrawingModel> drawings = professionalMap.get(professionalKey);
            professionalList.add(buildProfessionalNode(professionalKey, drawings, tzzyDictMap));
        });

        // 处理专业中非字典数据
        professionalMap.keySet().stream()
                .filter(professionalKey -> !tzzyDictMap.containsKey(professionalKey) && !"未分类".equals(professionalKey))
                .sorted()
                .forEach(professionalKey -> {
                    List<DesignDrawingModel> drawings = professionalMap.get(professionalKey);
                    professionalList.add(buildProfessionalNode(professionalKey, drawings, tzzyDictMap));
                });

        // 处理专业未分类
        if (professionalMap.containsKey("未分类")) {
            List<DesignDrawingModel> drawings = professionalMap.get("未分类");
            professionalList.add(buildProfessionalNode("未分类", drawings, tzzyDictMap));
        }

        monomerNode.setChildDrawing(professionalList);
        return monomerNode;
    }

    private DesignDrawingModel buildProfessionalNode(String professionalKey, List<DesignDrawingModel> drawings,
                                                     LinkedHashMap<String, String> tzzyDictMap) {
        DesignDrawingModel professionalNode = new DesignDrawingModel();
        professionalNode.setDdId(GenerateUUID.getUUID());
        String professionalName = tzzyDictMap.getOrDefault(professionalKey, professionalKey);
        professionalNode.setPName(ValidateUtil.isNotEmpty(professionalName) ? professionalName : "未分类");
        professionalNode.setChildDrawing(drawings);
        return professionalNode;
    }

    @Override
    public List<DesignDrawingModel> getDesignDrawingExportRW(DesignDrawingModel request, String uId) {
        String authSql = sysUserService.getAuthSql("Design_Drawing", uId, "Dd_Create_Uid", OutUserAuthEnum.Organization.getIndex(), "Dd_State");
        if (ValidateUtil.isEmpty(authSql)) {
            throw new JPMException(ResMsg.noPermission);
        }

        QueryMap<DesignDrawingModel> params = getQueryMap(request, authSql);
        List<DesignDrawingModel> datas = baseMapper.getDesignDrawingList(null, params.getQuery());

        if (ValidateUtil.isEmpty(datas)) {
            return Collections.emptyList();
        }

        // 图纸分类
        LinkedHashMap<String, String> tzflDictMap = getSortedDictMap("JPMsoft_TZFL");
        // 图纸专业
        LinkedHashMap<String, String> tzzyDictMap = getSortedDictMap("JPMsoft_TZZY_ZW");
        // 单体
        LinkedHashMap<String, String> xcdgjzDictMap = getSortedDictMap("JPMsoft_XCDGJZ");

        // 提取所有出现的PName和DdType编码
        Set<String> allPNames = datas.stream()
                .map(DesignDrawingModel::getPName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        List<String> allDdCategorys = tzflDictMap.keySet().stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());

        // 读取配置，默认值01
        String designDrawingTreeLevel = sysConfigService.getConfigByCode("DesignDrawingTreeLevel");
        Set<String> threeLayerCodes = Optional.ofNullable(designDrawingTreeLevel)
                .map(str -> Arrays.stream(str.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toSet()))
                .orElse(new HashSet<>(Collections.singletonList("01")));

        // 构造两个分组map
        Map<String, List<DesignDrawingModel>> twoLayerMap = new LinkedHashMap<>();
        Map<String, Map<String, Map<String, List<DesignDrawingModel>>>> threeLayerMap = new LinkedHashMap<>();

        for (DesignDrawingModel d : datas) {
            String rawDdCategory = Optional.ofNullable(d.getDdCategory()).orElse("").trim();
            String ddCategory = rawDdCategory.isEmpty() ? "未分类" : rawDdCategory;
            if (!rawDdCategory.isEmpty() && !tzflDictMap.containsKey(ddCategory)) {
                continue;
            }
            String key = d.getPName() + "|" + ddCategory;
            if (threeLayerCodes.contains(ddCategory)) {
                threeLayerMap
                        .computeIfAbsent(key, k -> new LinkedHashMap<>())
                        .computeIfAbsent(Optional.ofNullable(d.getDdMonomer()).filter(s -> !s.isEmpty()).orElse("未分类"), k -> new LinkedHashMap<>())
                        .computeIfAbsent(Optional.ofNullable(d.getDdProfessional()).filter(s -> !s.isEmpty()).orElse("未分类"), k -> new ArrayList<>())
                        .add(d);
            } else {
                twoLayerMap.computeIfAbsent(key, k -> new ArrayList<>()).add(d);
            }
        }

        List<DesignDrawingModel> result = new ArrayList<>();

        for (String pName : allPNames) {
            for (String ddCategoryCode : allDdCategorys) {
                String ddTypeName = tzflDictMap.getOrDefault(ddCategoryCode, "未分类");
                String key = pName + "|" + ddCategoryCode;
                if (threeLayerCodes.contains(ddCategoryCode)) {
                    Map<String, Map<String, List<DesignDrawingModel>>> monomerMap = threeLayerMap.getOrDefault(key, new LinkedHashMap<>());
                    for (Map.Entry<String, Map<String, List<DesignDrawingModel>>> monomerEntry : monomerMap.entrySet()) {
                        String monomerKey = monomerEntry.getKey();
                        Map<String, List<DesignDrawingModel>> professionalMap = monomerEntry.getValue();
                        for (Map.Entry<String, List<DesignDrawingModel>> professionalEntry : professionalMap.entrySet()) {
                            String professionalKey = professionalEntry.getKey();
                            List<DesignDrawingModel> drawings = professionalEntry.getValue();
                            DesignDrawingModel node = new DesignDrawingModel();
                            node.setPName(pName + "（" + ddTypeName + "）");
                            node.setDdMonomer(xcdgjzDictMap.getOrDefault(monomerKey, monomerKey));
                            node.setDdProfessional(tzzyDictMap.getOrDefault(professionalKey, professionalKey));
                            node.setChildDrawing(drawings);
                            result.add(node);
                        }
                    }
                } else {
                    List<DesignDrawingModel> leafList = twoLayerMap.getOrDefault(key, new ArrayList<>());
                    if (!leafList.isEmpty()) {
                        DesignDrawingModel node = new DesignDrawingModel();
                        node.setPName(pName + "（" + ddTypeName + "）");
                        node.setChildDrawing(leafList);
                        result.add(node);
                    }
                }
            }
        }

        return result;
    }

    private LinkedHashMap<String, String> getSortedDictMap(String dictCode) {
        return DictionaryUtils.getDictList(dictCode).stream()
                .sorted(Comparator.comparing(DictDto::getSort))
                .collect(Collectors.toMap(
                        DictDto::getCode,
                        DictDto::getName,
                        (v1, v2) -> v1,
                        LinkedHashMap::new
                ));
    }
}
