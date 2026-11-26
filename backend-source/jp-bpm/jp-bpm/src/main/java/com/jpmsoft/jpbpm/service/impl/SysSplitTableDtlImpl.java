package com.jpmsoft.jpbpm.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.type.TypeReference;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jpbpm.mapper.SysSplitTableDtlMapper;
import com.jpmsoft.jpbpm.pojo.entity.SysSplitTable;
import com.jpmsoft.jpbpm.pojo.entity.SysSplitTableDtl;
import com.jpmsoft.jpbpm.service.SysConfigEntityService;
import com.jpmsoft.jpbpm.service.SysSplitTableDtlService;
import com.jpmsoft.jpbpm.service.SysSplitTableService;
import com.jpmsoft.jpbpm.util.RedisUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;


import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SysSplitTableDtlImpl extends ServiceImpl<SysSplitTableDtlMapper, SysSplitTableDtl> implements SysSplitTableDtlService {
    @Autowired
    SysConfigEntityService sysConfigEntityService;
    @Autowired
    SysSplitTableService sysSplitTableService;
    @Autowired
    private RedisUtils redisUtils;

    @Override
    public String getTableCodeByTcode(String tCode, String newYear, String tableCode) {
//        if (ValidateUtil.isEmpty(newYear)) {
//            newYear = new Date().getYear() + "";
//        }

        String tableCodeNew = "";
        try {
            //获取获取基础配置信息，判断是否需要进行分表
            String tableConfiguration = "";

            if (ValidateUtil.isEmpty(redisUtils.get("tableConfiguration20250521"))) {
                tableConfiguration = sysConfigEntityService.getConfByCode("tableConfiguration");
                redisUtils.set("tableConfiguration20250521", tableConfiguration, 24 * 60 * 60 * 1000);
            } else {
                tableConfiguration = redisUtils.get("tableConfiguration20250521").toString();
            }
            //解析基础配置信息
            Map<String, Object> map = JacksonUtils.parseObject(tableConfiguration, new TypeReference<Map<String, Object>>() {
            });
            if (map != null) {
                if ("1".equals(map.get("state"))) {
                    //启用

                    if ("01".equals(map.get("type"))) {
                        if (ValidateUtil.isEmpty(tCode)) {
                            return tableCode;
                        }
                        //分表管理里的分表配置
                        if (ValidateUtil.isEmpty(redisUtils.get(tCode + "20250521"))) {
                            QueryWrapper<SysSplitTableDtl> queryWrapper = new QueryWrapper<>();
                            queryWrapper.eq("table_code", tCode);
                            List<SysSplitTableDtl> listData = baseMapper.selectList(queryWrapper);
                            if (!CollectionUtils.isEmpty(listData)) {
                                String tId = listData.get(0).getStId();
                                SysSplitTable obj = sysSplitTableService.selectSysSplitTableBySplitId(tId);
                                if (obj != null) {
                                    tableCodeNew = tableCode + "_" + obj.getStCode();
                                    redisUtils.set(tCode + "20250521", tableCodeNew, 24 * 60 * 60 * 1000);
                                }
                            }
                        } else {
                            tableCodeNew = redisUtils.get(tCode + "20250521").toString();
                        }
                    } else if ("02".equals(map.get("type"))) {

                        //基础配置中的分表配置
                        if (ValidateUtil.isNotEmpty(map.get("page") + "")) {
                            List<Map<String, Object>> page =(List<Map<String, Object>>) map.get("page") ;
                            for (Map<String, Object> obj : page) {
                                if (ValidateUtil.isEmpty(newYear)) {
                                    if (tableCode.toLowerCase().equals((obj.get("code") + "").toLowerCase())) {
                                        tableCodeNew = obj.get("tableCcoding").toString();
                                    }
                                } else {
                                    if (tableCode.toLowerCase().equals((obj.get("code") + "").toLowerCase()) && compareIntTo(newYear, obj.get("year") + "") != -1) {
                                        tableCodeNew = obj.get("tableCcoding").toString();
                                    }
                                }

                            }
                        }
                    }
                }
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return tableCode;
        }
        if (ValidateUtil.isEmpty(tableCodeNew)) {
            tableCodeNew = tableCode;
        }

        return tableCodeNew;
    }


    //比较两个数字格式的字符串，比较大小
    public Integer compareIntTo(String a, String b) {
        int aInt = Integer.parseInt(a);
        int bInt = Integer.parseInt(b);
        Integer result = 0;
        if (aInt == bInt) {
            result = 0;
        } else if (aInt < bInt) {
            result = 1;
        } else if (aInt > bInt) {
            result = -1;
        }
        return result;
    }

}
