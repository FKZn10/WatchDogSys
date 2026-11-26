package com.jpmsoft.userProducer.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.enums.SequenceTypeEnum;
import com.jpmsoft.userProducer.mapper.SysSequenceFactoryMapper;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceSetDtlDto;
import com.jpmsoft.userProducer.pojo.dto.SysSequenceSetDto;
import com.jpmsoft.userProducer.pojo.entity.SysSequenceCurrentCode;
import com.jpmsoft.userProducer.pojo.entity.SysSequenceSet;
import com.jpmsoft.userProducer.service.IApiUtilsService;
import com.jpmsoft.userProducer.service.ISysSequenceCurrentCodeService;
import com.jpmsoft.userProducer.service.ISysSequenceFactory;
import com.jpmsoft.userProducer.service.ISysSequenceSetService;
import com.jpmsoft.userProducer.utils.CheckNullUtil;
import com.jpmsoft.userProducer.utils.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class SysSequenceFactoryImpl implements ISysSequenceFactory {

    @Resource
    private SysSequenceFactoryMapper sysSequenceFactoryMapper;
    @Resource
    private ISysSequenceSetService sysSequenceSetService;
    @Resource
    private ISysSequenceCurrentCodeService sysSequenceCurrentCodeService;
    @Resource
    private IApiUtilsService apiUtilsService;

    //region 规则方法
    @Override
    public String timeRuleGenerate(String pattern) {
        return DateUtil.Date2String(new Date(), pattern);
    }

    @Override
    public String uuidRuleGenerate() {
        return UUID.randomUUID().toString();
    }

    @Override
    public String sqlRuleGenerate(String sql) {
        if (ValidateUtil.isEmpty(sql))
            return null;
        try {
            return sysSequenceFactoryMapper.sqlRuleGenerate(sql);
        } catch (Exception ex) {
            //异常SQL
            return null;
        }
    }

    @Override
    public String constRuleGenerate(String con, String tableField, String fieldValue, Object request) {
        try {
            //两个规则缺一不可
            if (ValidateUtil.isEmpty(tableField) || ValidateUtil.isEmpty(fieldValue))
                return con;
            //规则配置但是表单数据为空
            if (request == null)
                return null;
            Map<String, Object> map = JacksonUtils.parseObject(JacksonUtils.toJSONString(request), Map.class);
            //表单数据中没有配置的字段值
            if (!map.containsKey(tableField))
                return null;
            if (map.get(tableField).toString().equals(fieldValue))
                return con;
            else
                return null;
        } catch (Exception ex) {
            log.error("拼接常量规则失败");
            log.error(ex.getMessage(),ex);
            return null;
        }
    }

    @Override
    public String numberRuleGenerate(Integer sNo, Integer sStep, String side, Integer width, String charFiled) {
        if (ValidateUtil.isEmpty(charFiled))
            charFiled = "";
        if (sNo == null)
            sNo = 0;
        if (sStep == null) //步长默认1
            sStep = 1;
        if (side == null || !side.equals("right"))//除了右补齐则左补齐
            side = "left";
        if (width == null)
            width = 0;
        String numberStr = sNo + sStep + "";
        String data = numberStr;
        if (numberStr.length() < width) {
            for (int i = 0; i < width - numberStr.length(); i++) {
                if (side.equals("left"))
                    data = charFiled + data;
                else if (side.equals("right"))
                    data = data + charFiled;
            }
        }
        return data;
    }

    @Override
    public String classRuleGenerate(SysSequenceSetDto model, String className) {
        try {
            Class clz = Class.forName(className);
            Object obj = clz.newInstance();
            //获取方法
            Method m = obj.getClass().getDeclaredMethod("next", String.class);
            //调用方法
            String result = (String) m.invoke(obj, model);
            return result;
        } catch (Exception ex) {
            log.error("反射调用指定类异常");
            log.error(ex.getMessage(),ex);
            return null;
        }
    }

    private String getProjectInfo(String pId, String filed) {
        try {
            if (ValidateUtil.isEmpty(pId))
                return null;
            return sysSequenceFactoryMapper.getProjectInfo(pId, filed);
        } catch (Exception ex) {
            log.error("查询项目Code失败");
            log.error(ex.getMessage(),ex);
            return null;
        }
    }

    private String getContractInfo(String conId, String filed) {
        try {
            if (ValidateUtil.isEmpty(conId))
                return null;
            return sysSequenceFactoryMapper.getContractInfo(conId, filed);
        } catch (Exception ex) {
            log.error("查询项目Code失败");
            log.error(ex.getMessage(),ex);
            return null;
        }
    }

    private String getSecInfo(String secId, String filed) {
        try {
            if (ValidateUtil.isEmpty(secId))
                return null;
            return sysSequenceFactoryMapper.getSecInfo(secId, filed);
        } catch (Exception ex) {
            log.error("查询项目Code失败");
            log.error(ex.getMessage(),ex);
            return null;
        }
    }


    /**
     * 获取当前表单记录的某个字段值
     *
     * @param request 当前提交的表单数据
     * @param filed   规则配置的指定字段值
     * @return
     */
    private String getCurrentInfoFiled(Object request, String filed,String startIndex,String endIndex) {
        try {
            if (request == null)
                return null;
            Map<String, Object> map = JacksonUtils.parseObject(JacksonUtils.toJSONString(request), Map.class);
            if (ValidateUtil.isEmpty(filed) || map == null)
                return null;
            if (!map.containsKey(filed))
                return null;
            String str=map.get(filed) + "";
            if (!ValidateUtil.isAnObjEmpty(startIndex,endIndex,str))
            {
                try {
                    Integer start=Integer.parseInt(startIndex);
                    if (start<1) start=1; //所以最小值
                    Integer end=Integer.parseInt(endIndex);
                    if (end>str.length()) end=str.length();//索引最大值
                    if (end<start) return str; //结束索引大于开始索引则不截取
                    return str.substring(start-1,end);
                }catch (Exception ex)
                {
                    log.error("取当前表单值并配置截取字符长度报错",ex);
                    return str;
                }
            }
            return str;
        } catch (Exception ex) {
            log.error("查询项目Code失败");
            log.error(ex.getMessage(),ex);
            return null;
        }
    }

    private String getLinkedTableValue(String table, String cloumn, String id, String key) {
        try {
            //任意缺少一个参数即无法获取
            if (CheckNullUtil.checkString(table, cloumn, id, key))
                return null;
            String str = sysSequenceFactoryMapper.getLinkedTableValue(table, cloumn, id, key);
            return str;
        } catch (Exception ex) {
            log.error("查询链表信息失败！");
            log.error(ex.getMessage(),ex);
            return null;
        }
    }
    //endregion


    @Override
    public SysSequenceSetDto sequenceRest(SysSequenceSetDto model, String pId, String conId, String secId, List<SysSequenceSetDtlDto> data, Object request, boolean refershNo) {
        //sReset 值格式[[序号，规则编码],[序号，规则编码]]/&
        List<SysSequenceSetDtlDto> autoType = data.stream().filter(n -> n.getSdRuleType().equals(SequenceTypeEnum.AUTO.getkey())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(autoType))
            return model;
        if (!ValidateUtil.isEmpty(model.getsReset())) {
            String[] restL = model.getsReset().split("/");
            if (restL.length != 2)
                return model;
            String reset = restL[0];
            String rule = restL[1];
            List<List> sReset = JacksonUtils.parseObject(reset, new TypeReference<List<List>>() {
            });
            if (rule.equals("&") || rule.equals("|")) {
                Map<String, Object> map = new HashMap<>();
                for (List list : sReset) {
                    map.put(list.get(0).toString(), list.get(1));
                }
                //Map按序号升序
                map = sortByKey(map);
                String ccReset = "";
                for (Map.Entry<String, Object> entry : map.entrySet()) {
                    List<SysSequenceSetDtlDto> dtlModels = data.stream().filter(n -> n.getSdRuleOrder() == Integer.parseInt(entry.getKey())).collect(Collectors.toList());
                    if (!CollectionUtils.isEmpty(dtlModels)) {
                        SysSequenceSetDtlDto dtlModel = dtlModels.stream().findFirst().get();
                        String value = getValue(dtlModel.getSdRuleType(), model, dtlModel, pId, conId, secId, request, refershNo);
                        if (ValidateUtil.isEmpty(ccReset))
                            ccReset = value;
                        else {
                            if (!ValidateUtil.isEmpty(value))
                                ccReset = ccReset + "," + value;
                        }
                    }
                }
                if (ValidateUtil.isEmpty(ccReset))
                    return model;
                //先搜索是否有完全一致的数据
                SysSequenceCurrentCode pojo = sysSequenceCurrentCodeService.getSysSequenceCurrentCode(model.getsId(), ccReset + "," + rule);
                if (pojo != null) {
                    //完全一致则直接返回
                    model.setsNo(pojo.getCcNo());
                    pojo.setCcNo(pojo.getCcNo() + model.getsStep());
                    if (refershNo)
                        sysSequenceCurrentCodeService.updateById(pojo);
                } else {
                    //不存在完全一致
                    if (rule.equals("|")) {
                        //或者的规则时直接新建数据（或者即不满足任意一个规则则重置）
                        pojo = new SysSequenceCurrentCode();
                        pojo.setCcNo(model.getsStep());
                        pojo.setSId(model.getsId());
                        pojo.setCcId(GenerateUUID.getUUID());
                        pojo.setCcReset(ccReset + "," + rule);
                        if (refershNo)
                            sysSequenceCurrentCodeService.save(pojo);
                        model.setsNo(0);
                    } else if (rule.equals("&")) {
                        //并且，满足任意一个规则则表示满足
                        List<SysSequenceCurrentCode> codes = sysSequenceCurrentCodeService.getSysSequenceCurrentCodes(model.getsId(), rule);
                        if (codes == null) {
                            pojo = new SysSequenceCurrentCode();
                            pojo.setCcNo(model.getsStep());
                            pojo.setSId(model.getsId());
                            pojo.setCcId(GenerateUUID.getUUID());
                            pojo.setCcReset(ccReset + "," + rule);
                            if (refershNo)
                                sysSequenceCurrentCodeService.save(pojo);
                            model.setsNo(0);
                        } else {
                            List<String> values = Arrays.asList(ccReset.split(","));
                            SysSequenceCurrentCode entity = null;
                            for (SysSequenceCurrentCode code : codes) {
                                String[] rest = code.getCcReset().split(",");
                                if (rest.length - 1 != values.size())
                                    //规则长度不同
                                    continue;
                                for (int i = 0; i < rest.length - 1; i++) {
                                    if (rest[i].equals(values.get(i))) {
                                        entity = code;
                                        break;
                                    }
                                }
                            }
                            if (entity != null) {
                                model.setsNo(entity.getCcNo());
                                entity.setCcNo(entity.getCcNo() + model.getsStep());
                                entity.setCcReset(ccReset + "," + rule);
                                if (refershNo)
                                    sysSequenceCurrentCodeService.updateById(entity);
                            } else {
                                entity = new SysSequenceCurrentCode();
                                entity.setCcNo(model.getsStep());
                                entity.setSId(model.getsId());
                                entity.setCcId(GenerateUUID.getUUID());
                                entity.setCcReset(ccReset + "," + rule);
                                if (refershNo)
                                    sysSequenceCurrentCodeService.save(entity);
                                model.setsNo(0);
                            }
                        }
                    }
                }
                return model;
            } else
                return model;
        }
        return model;
    }

    @Override
    public String getCode(SysSequenceSetDto model, String pId, String conId, String secId, Object request, boolean refershNo) {
        //检测是否需要重置序号
        List<SysSequenceSetDtlDto> data = model.getSysSequenceSetDtls();
        if (CollectionUtils.isEmpty(data))
            return null;
        model = sequenceRest(model, pId, conId, secId, data, request, refershNo);
        String code = makeCode(model, pId, conId, secId, request, false,refershNo);
        if (ValidateUtil.isEmpty(code))
            return null;
        else {
            if (refershNo) {
                SysSequenceSet entity = new SysSequenceSet();
                BeanUtils.copyProperties(model, entity);
                //缺省值
                if (entity.getSNo() == null)
                    entity.setSNo(0);
                if (entity.getSStep() == null)
                    entity.setSStep(1);
                entity.setSNo(entity.getSNo() + entity.getSStep());
                entity.setSCode(code);
                sysSequenceSetService.updateEntity(entity);
            }
            return code;
        }
    }


    /**
     * 生成编号
     *
     * @param model
     * @param pId
     * @param conId
     * @param secId
     * @param request
     * @param isRest
     * @return
     */
    private String makeCode(SysSequenceSetDto model, String pId, String conId, String secId, Object request, boolean isRest, boolean refershNo) {
        String code = "";
        for (SysSequenceSetDtlDto sd : model.getSysSequenceSetDtls()) {
            String value = getValue(sd.getSdRuleType(), model, sd, pId, conId, secId, request,refershNo);
            if (!ValidateUtil.isEmpty(value)) {
//                if (isRest) {
//                    //重置编码排除自增值
//                    if (!sd.getSdRuleType().equals(SequenceTypeEnum.AUTO.getkey()))
//                        code = code + "," + value;
//                } else {
                if (!ValidateUtil.isEmpty(model.getsDelimiter()) && !ValidateUtil.isEmpty(code))
                    code = code + model.getsDelimiter() + value;
                else
                    code += value;
//                }
            }
        }
        return code;
    }

    private String getInterfaceValue(String url, Object data, boolean refreshNo) {
        BaseResponse res = apiUtilsService.sendPost(url, data,refreshNo);
        if (res != null && res.getSuccess() && res.getStatusCode() == HttpStatus.SC_OK)
            return res.getData().toString();
        return null;
    }

    /**
     * 获取单个规则生成的值
     *
     * @param ruleType
     * @param model
     * @param sd
     * @param pId
     * @param conId
     * @param secId
     * @param request
     * @return
     */
    private String getValue(String ruleType, SysSequenceSetDto model, SysSequenceSetDtlDto sd
            , String pId, String conId, String secId, Object request, boolean refreshNo) {
        if (ValidateUtil.isEmpty(ruleType)) return null;
        String value = null;
        if (ruleType.equals(SequenceTypeEnum.TIME.getkey()))
            value = timeRuleGenerate(sd.getSdRuleValue());
        if (ruleType.equals(SequenceTypeEnum.UUID.getkey()))
            value = uuidRuleGenerate();
        if (ruleType.equals(SequenceTypeEnum.SQL.getkey()))
            value = sqlRuleGenerate(sd.getSdRuleValue());
        if (ruleType.equals(SequenceTypeEnum.CONST.getkey()))
            value = constRuleGenerate(sd.getSdRuleValue(), sd.getSdTableField(), sd.getSdFieldValue(), request);
        if (ruleType.equals(SequenceTypeEnum.AUTO.getkey()))
            value = numberRuleGenerate(model.getsNo(), model.getsStep(), sd.getSdPaddingSide(), sd.getSdPaddingWidth(), sd.getSdPaddingChar().toString());
        if (ruleType.equals(SequenceTypeEnum.CLASS.getkey()))
            value = classRuleGenerate(model, sd.getSdRuleValue());
        if (ruleType.equals(SequenceTypeEnum.PROJECT.getkey()))
            value = getProjectInfo(pId, sd.getSdRuleValue());
        if (ruleType.equals(SequenceTypeEnum.CONTRACT.getkey()))
            value = getContractInfo(conId, sd.getSdRuleValue());
        if (ruleType.equals(SequenceTypeEnum.SECINFO.getkey()))
            value = getSecInfo(secId, sd.getSdRuleValue());
        if (ruleType.equals(SequenceTypeEnum.CURRENT.getkey()))
            value = getCurrentInfoFiled(request, sd.getSdRuleValue(),sd.getSdTableField(),sd.getSdFieldValue());
        if (ruleType.equals(SequenceTypeEnum.LINKED.getkey()))
            value = getLinkedTableValue(sd.getSdRuleValue(), sd.getSdTableField(), getCurrentInfoFiled(request, sd.getSdFieldValue(),null,null), sd.getSdTableKey());
        if (ruleType.equals(SequenceTypeEnum.INTERFACE.getkey()))
            value = getInterfaceValue(sd.getSdRuleValue(), request,refreshNo);
        log.info("Code getValue:" + value);
        return value;
    }

    /**
     * map 按 key 升序排序
     */
    private Map<String, Object> sortByKey(Map<String, Object> map) {
        List<Integer> keys = map.keySet().stream().map(Integer::parseInt).sorted().collect(Collectors.toList());
        Map<String, Object> sortMap = new LinkedHashMap<>();
        for (Integer key : keys)
            sortMap.put(String.valueOf(key), map.get(String.valueOf(key)));
        return sortMap;
    }
}
