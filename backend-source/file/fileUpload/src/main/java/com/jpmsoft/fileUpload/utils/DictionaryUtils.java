package com.jpmsoft.fileUpload.utils;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.fileUpload.feign.DictionaryFeign;
import com.jpmsoft.fileUpload.pojo.dto.DictDto;
import com.jpmsoft.fileUpload.pojo.dto.SelectDto;
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
        return  JSONUtil.parseArray(JSONUtil.toJsonStr(baseResponse.getData()));
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
        JSONArray dictList = JSONUtil.parseArray(JSONUtil.toJsonStr(baseResponse.getData()));
        return processDictList(dictList,value);
    }

    public static JSONArray getTreeList(String code){
        SelectDto selectDto = new SelectDto();
        selectDto.setCode(code);
        BaseResponse baseResponse = dictionaryUtils.dictionaryFeign.getDictionaryTree(selectDto);
        JSONArray dictList = JSONUtil.parseArray(JSONUtil.toJsonStr(baseResponse.getData()));
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
        JSONArray dictList = JSONUtil.parseArray(JSONUtil.toJsonStr(baseResponse.getData()));
        getDictList(dictList, result);
        return result;
    }

    private static void getDictList(JSONArray dictList, List<DictDto> result){
        for (Object object : dictList) {
            JSONObject json = JSONUtil.parseObj(JSONUtil.toJsonStr(object));
            DictDto dto = new DictDto();
            dto.setCode(json.getStr("value"));
            dto.setName(json.getStr("title"));
            String dSortStr = null;
            if (json.containsKey("dSort")) {
                dSortStr = json.getStr("dSort");
            }
            if (StringUtils.isNotBlank(dSortStr)) {
                dto.setSort(Integer.valueOf(dSortStr));
            } else {
                dto.setSort(null);
            }
            result.add(dto);
            JSONArray children = JSONUtil.parseArray(json.getStr("children"));
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
            JSONObject json = JSONUtil.parseObj(JSONUtil.toJsonStr(object));
            if (StringUtils.isNotBlank(value) && value.equals(json.getStr("value"))) {
                title = json.getStr("title");
                break;
            }
            JSONArray children = JSONUtil.parseArray(json.getStr("children"));
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
