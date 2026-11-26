package com.jpmsoft.projectProducer.utils;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.JSONWriter;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.projectProducer.feign.DictionaryFeign;
import com.jpmsoft.projectProducer.pojo.dto.system.DictDto;
import com.jpmsoft.projectProducer.pojo.dto.system.SelectDto;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: 沈金安 Shen Jinan
 * @Date: 2023/10/27 11:12
 * @Description:
 * @Version: 1.0
 */
@Component
public class DictionaryUtils {

    @Autowired
    private DictionaryFeign dictionaryFeign;

    private static DictionaryUtils dictionaryUtils;

    @PostConstruct
    public void init(){
        dictionaryUtils = this;
    }

    public static JSONArray dictList(String code){
        SelectDto selectDto = new SelectDto();
        selectDto.setCode(code);
        BaseResponse baseResponse = dictionaryUtils.dictionaryFeign.getDictionaryTree(selectDto);
        return  JSONArray.parseArray(JSON.toJSONString(baseResponse.getData(), JSONWriter.Feature.ReferenceDetection));
    }

    /**
     * 获取字典描述
     * @param code 字典编码
     * @param value 字典key值
     * @return
     */
    public static String getTitle(String code, String value){
        SelectDto selectDto = new SelectDto();
        selectDto.setCode(code);
        BaseResponse baseResponse = dictionaryUtils.dictionaryFeign.getDictionaryTree(selectDto);
        JSONArray dictList = JSONArray.parseArray(JSON.toJSONString(baseResponse.getData(), JSONWriter.Feature.ReferenceDetection));
        return processDictList(dictList,value);
    }

    public static JSONArray getTreeList(String code){
        SelectDto selectDto = new SelectDto();
        selectDto.setCode(code);
        BaseResponse baseResponse = dictionaryUtils.dictionaryFeign.getDictionaryTree(selectDto);
        JSONArray dictList = JSONArray.parseArray(JSON.toJSONString(baseResponse.getData(), JSONWriter.Feature.ReferenceDetection));
        return dictList;
    }

    /**
     * 获取字典列表
     * @param code 典编码
     * @return
     */
    public static List<DictDto> getDictList(String code){
        List<DictDto> result = new ArrayList<>();
        SelectDto selectDto = new SelectDto();
        selectDto.setCode(code);
        BaseResponse baseResponse = dictionaryUtils.dictionaryFeign.getDictionaryTree(selectDto);
        JSONArray dictList = JSONArray.parseArray(JSON.toJSONString(baseResponse.getData(), JSONWriter.Feature.ReferenceDetection));
        getDictList(dictList, result);
        return result;
    }

    private static void getDictList(JSONArray dictList, List<DictDto> result){
        for (Object object : dictList) {
            JSONObject json = JSONObject.parseObject(JSON.toJSONString(object, JSONWriter.Feature.ReferenceDetection));
            DictDto dto = new DictDto();
            dto.setCode(json.getString("value"));
            dto.setName(json.getString("title"));
            String dSortStr = null;
            if (json.containsKey("dSort")) {
                dSortStr = json.getString("dSort");
            }
            if (StringUtils.isNotBlank(dSortStr)) {
                dto.setSort(Integer.valueOf(dSortStr));
            } else {
                dto.setSort(null);
            }
            result.add(dto);
            JSONArray children = JSONArray.parseArray(json.getString("children"));
            if (CollectionUtils.isNotEmpty(children)){
                getDictList(children, result);
            }
        }
    }
    public static String processDictList(JSONArray dictList, String[] value)
    {
        List<String> str=new ArrayList<>();
        for (String s : value) {
            str.add(processDictList(dictList,s));
        }
        return String.join(",",str);
    }
    public static String processDictList(JSONArray dictList, String value){
        String title = null;
        for (Object object : dictList) {
            JSONObject json = JSONObject.parseObject(JSON.toJSONString(object, JSONWriter.Feature.ReferenceDetection));
            if (StringUtils.isNotBlank(value) && value.equals(json.getString("value"))) {
                title = json.getString("title");
                break;
            }
            JSONArray children = JSONArray.parseArray(json.getString("children"));
            if (CollectionUtils.isNotEmpty(children)){
                title = processDictList(children,value);
                if (title != null){
                    break;
                }
            }
        }
        return title;
    }
}
