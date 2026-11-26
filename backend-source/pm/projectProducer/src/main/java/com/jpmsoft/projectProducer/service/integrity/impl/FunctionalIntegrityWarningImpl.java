package com.jpmsoft.projectProducer.service.integrity.impl;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.model.request.Integrity.FunctionalIntegrityWarningRequest;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.integrity.FunctionalIntegrityWarningMapper;
import com.jpmsoft.projectProducer.pojo.entity.integrity.FunctionalIntegrityWarning;
import com.jpmsoft.projectProducer.pojo.entity.integrity.WarningSettingProgress;
import com.jpmsoft.projectProducer.pojo.vo.integrity.IntegrityWarningChildVo;
import com.jpmsoft.projectProducer.pojo.vo.integrity.IntegrityWarningPositionVo;
import com.jpmsoft.projectProducer.pojo.vo.integrity.IntegrityWarningVo;
import com.jpmsoft.projectProducer.pojo.vo.integrity.WarningSettingProgressJson;
import com.jpmsoft.projectProducer.service.integrity.IFunctionalIntegrityWarningService;
import com.jpmsoft.projectProducer.service.integrity.IWarningSettingProgressService;
import com.jpmsoft.projectProducer.utils.DictionaryUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 功能预警流程记录
 */
@Slf4j
@Service
public class FunctionalIntegrityWarningImpl extends ServiceImpl<FunctionalIntegrityWarningMapper, FunctionalIntegrityWarning> implements IFunctionalIntegrityWarningService {
    @Resource
    private IWarningSettingProgressService warningSettingProgressService;

    /**
     * 高频风险环节看板
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getIntegrityWarningLedger(FunctionalIntegrityWarningRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        QueryMap<FunctionalIntegrityWarningRequest> params = buildQueryMap(request);
        List<IntegrityWarningChildVo> data = baseMapper.getIntegrityWarningLedger(params.getQuery());
        if (ValidateUtil.isEmpty(data)) {
            return BaseResponse.ok();
        }
        // 查询颜色对应
        List<String> tCodes = data.stream()
                .map(IntegrityWarningChildVo::getBusinessCode)
                .distinct()
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toList());
        Map<String, String> colorMap = getColorMap(tCodes);
        if (ValidateUtil.isNull(colorMap)) {
            return BaseResponse.fail(ResMsg.confError);
        }
        Map<String, List<IntegrityWarningChildVo>> groupMap = data.stream()
                .filter(item -> ValidateUtil.isNotEmpty(item.getBusinessCode()))
                .collect(Collectors.groupingBy(IntegrityWarningChildVo::getModelCode)
        );
        JSONArray LZFXMK = DictionaryUtils.dictList("JPMsoft_LZFXMK");
        List<IntegrityWarningVo> result = new ArrayList<>();
        groupMap.forEach((k, v) -> {
            IntegrityWarningVo vo = new IntegrityWarningVo();
            vo.setModelCode(k);
            vo.setModelName(DictionaryUtils.processDictList(LZFXMK, k));
            vo.setColor(colorMap.get(v.get(0).getBusinessCode()));
            vo.setData(v);
            result.add(vo);
        });
        // 验证返回体，如果result数量小于3，则填充数据到3条
        if (result.size() < 3) {
            List<String> existingModelCodes = result.stream()
                    .map(IntegrityWarningVo::getModelCode)
                    .collect(Collectors.toList());
            List<String> allModelCodes = LZFXMK.stream()
                    .map(item -> ((Map<?, ?>) item).get("value"))
                    .filter(item -> !ValidateUtil.isEmpty(item))
                    .map(String::valueOf)
                    .collect(Collectors.toList());

            LambdaQueryWrapper<WarningSettingProgress> wrapper = new LambdaQueryWrapper<>();
            wrapper.select(WarningSettingProgress::getTCode, WarningSettingProgress::getAlertSettings,
                    WarningSettingProgress::getModelCode);
            List<WarningSettingProgress> progressList = warningSettingProgressService.list(wrapper);
            Map<String, String> modelColorMap = getColorMapByList(progressList);
            for (String modelCode : allModelCodes) {
                if (!existingModelCodes.contains(modelCode)) {
                    IntegrityWarningVo vo = new IntegrityWarningVo();
                    vo.setModelCode(modelCode);
                    vo.setModelName(DictionaryUtils.processDictList(LZFXMK, modelCode));
                    if (modelColorMap != null) {
                        String color = modelColorMap.get(modelCode);
                        if (ValidateUtil.isNotEmpty(color)) {
                            vo.setColor(color);
                        } else {
                            vo.setColor(randomColor());
                        }
                    } else {
                        vo.setColor(randomColor());
                    }
                    vo.setData(Collections.emptyList());
                    result.add(vo);
                }
                if (result.size() >= 3) {
                    break;
                }
            }
        }
        return BaseResponse.ok(result);
    }

    /**
     * 高频风险岗位看板
     *
     * @param request
     * @return
     */
    @Override
    public BaseResponse getIntegrityWarningPositionLedger(FunctionalIntegrityWarningRequest request) {
        if (ValidateUtil.isNull(request)) {
            return BaseResponse.fail(ResMsg.notReqParam);
        }
        QueryMap<FunctionalIntegrityWarningRequest> params = buildQueryMap(request);
        List<IntegrityWarningPositionVo> data = baseMapper.getIntegrityWarningPositionLedger(params.getQuery());
        if (ValidateUtil.isEmpty(data)) {
            return BaseResponse.ok();
        }
        // 查询颜色对应
        List<String> tCodes = data.stream()
                .map(IntegrityWarningPositionVo::getBusinessCode)
                .distinct()
                .filter(ValidateUtil::isNotEmpty)
                .collect(Collectors.toList());
        Map<String, String> colorMap = getColorMap(tCodes);
        if (ValidateUtil.isNull(colorMap)) {
            return BaseResponse.fail(ResMsg.confError);
        }
        // 根据岗位编码分组
        Map<String, List<IntegrityWarningPositionVo>> groupMap = data.stream()
                .filter(item -> ValidateUtil.isNotEmpty(item.getPositionCode()))
                .collect(Collectors.groupingBy(IntegrityWarningPositionVo::getPositionCode)
        );
        JSONArray LZFXMK = DictionaryUtils.dictList("JPMsoft_LZFXMK");
        List<IntegrityWarningPositionVo> result = new ArrayList<>();
        groupMap.forEach((k, v) -> {
            IntegrityWarningPositionVo vo = new IntegrityWarningPositionVo();
            vo.setPositionName(v.get(0).getPositionName());
            vo.setPositionCode(k);
            // 构建当前的tCode和颜色的对应
            Map<String, String> tCodeColorMap = new HashMap<>();
            v.forEach(item -> {
                if (ValidateUtil.isNotEmpty(item.getModelCode())) {
                    tCodeColorMap.put(DictionaryUtils.processDictList(LZFXMK, item.getModelCode()), colorMap.get(item.getBusinessCode()));
                }
            });
            vo.setModelCodes(tCodeColorMap);
            vo.setWriIndicator(v.stream().map(IntegrityWarningPositionVo::getWriIndicator).filter(ValidateUtil::isNotEmpty).distinct().collect(Collectors.joining("、")));
            vo.setCount((int) v.stream().filter(item -> ValidateUtil.isNotEmpty(item.getModelCode())).count());
            result.add(vo);
        });
        return BaseResponse.ok(result);
    }

    private QueryMap<FunctionalIntegrityWarningRequest> buildQueryMap(FunctionalIntegrityWarningRequest request) {
        QueryMap<FunctionalIntegrityWarningRequest> params = new QueryMap<>(request);
        params.put("id").put("createUid").put("createUname").put("createTime").put("state")
                .put("stateTime").put("triggerField").put("triggerFieldValue").put("businessCode")
                .put("warningForm").put("situationDescription").put("relatedDocuments").put("auditResults")
                .put("wriId");
        return params;
    }

    private Map<String, String> getColorMap(List<String> tCodes) {
        if (ValidateUtil.isEmpty(tCodes)) {
            return null;
        }
        LambdaQueryWrapper<WarningSettingProgress> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.in(WarningSettingProgress::getTCode, tCodes)
                .select(WarningSettingProgress::getAlertSettings, WarningSettingProgress::getTCode);
        List<WarningSettingProgress> list = warningSettingProgressService.list(queryWrapper);
        return getColorMapByList(list);
    }

    private Map<String, String> getColorMapByList(List<WarningSettingProgress> list) {
        if (ValidateUtil.isEmpty(list)) {
            return null;
        }
        Map<String, String> colorMapOriginal = list.stream().collect(
                Collectors.toMap(WarningSettingProgress::getTCode, WarningSettingProgress::getAlertSettings, (k1, k2) -> k1)
        );
        Map<String, String> colorMap = new HashMap<>();
        try {
            colorMapOriginal.forEach((k, v) -> {
                if (ValidateUtil.isEmpty(v)) {
                    return;
                }
                String jsonString = v;
                if (!JSONUtil.isTypeJSON(v)) {
                    jsonString = JacksonUtils.parseObject(v, String.class);
                    if (ValidateUtil.isEmpty(jsonString)) {
                        return;
                    }
                }
                WarningSettingProgressJson json = JacksonUtils.parseObject(jsonString, WarningSettingProgressJson.class);
                if (ValidateUtil.isNull(json)) {
                    return;
                }
                String color = json.getModuleColor();
                if (ValidateUtil.isEmpty(color)) {
                    return;
                }
                colorMap.put(k, color);
            });
        } catch (Exception e) {
            log.info("预警配置解析异常", e);
            return null;
        }
        if (ValidateUtil.isEmpty(colorMap)) {
            return null;
        }
        return colorMap;
    }

    private String randomColor() {
        Random random = new Random();
        String color;
        do {
            int r = random.nextInt(256);
            int g = random.nextInt(256);
            int b = random.nextInt(256);
            color = String.format("#%02X%02X%02X", r, g, b);
        } while (!isHighContrastWithWhite(color));
        return color;
    }

    private boolean isHighContrastWithWhite(String hex) {
        int r = Integer.parseInt(hex.substring(1, 3), 16);
        int g = Integer.parseInt(hex.substring(3, 5), 16);
        int b = Integer.parseInt(hex.substring(5, 7), 16);
        double[] rgb = {r / 255.0, g / 255.0, b / 255.0};
        for (int i = 0; i < 3; i++) {
            rgb[i] = rgb[i] <= 0.03928 ? rgb[i] / 12.92 : Math.pow((rgb[i] + 0.055) / 1.055, 2.4);
        }
        double luminance = 0.2126 * rgb[0] + 0.7152 * rgb[1] + 0.0722 * rgb[2];
        double contrast = (1.0 + 0.05) / (luminance + 0.05);
        return contrast >= 4.5;
    }
}
