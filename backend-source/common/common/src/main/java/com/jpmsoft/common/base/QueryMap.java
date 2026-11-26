package com.jpmsoft.common.base;

import com.jpmsoft.common.model.BasePageEntity;
import com.jpmsoft.common.utils.DateUtils;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 列表查询拓展
 * 默认填充分页条件进Map
 * 过滤空字符串,链式put
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/11 17:06
 */
@Data
public class QueryMap<D extends BasePageEntity> {
    private Map<String, Object> query;
    private Map<String, Object> dto;

    /**
     * 初始化分页条件
     *
     * @param d
     */
    public QueryMap(D d) {
        query = new HashMap<>();
        dto = JacksonUtils.parseObject(JacksonUtils.toJSONString(d), Map.class);
        this.put("sortSideFiled", d.getSortSideFiled());
        this.put("sortFiled", d.getSortFiled());
        if (ValidateUtil.isNotEmpty(d.getSortType()))
            this.put("sortType", d.getSortType().toUpperCase().contains("DESC") ? "desc" : "asc");
        else
            this.put("sortType", null);
        this.put("start", ValidateUtil.isNotNull(d.getPageStart()) ? d.getPageStart() : 0);
        this.put("size", d.getSize() == 0 ? 10 : d.getSize());
        this.put("teId", d.getTeId());
    }

    /**
     * 普通过滤条件
     *
     * @param key
     */
    public QueryMap<D> put(String key) {
        Object obj = dto.get(key);
        if (obj instanceof String)
            this.put(key, dto.get(key) + "");
        else if (obj instanceof Date)
            this.put(key, DateUtils.DateFormat((Date) obj, "yyyy-MM-dd HH:mm:ss"));
        else
            this.put(key, dto.get(key));
        return this;
    }

    /**
     * 范围型过滤条件 index 0 开始 1 结束
     *
     * @param ranges
     * @param startKey
     * @param endKey
     */
    public QueryMap<D> put(List<String> ranges, String startKey, String endKey) {
        if (ranges != null && ranges.size() == 2) {
            this.put(startKey, ValidateUtil.checkEmpty(ranges.get(0))).put(endKey, ValidateUtil.checkEmpty(ranges.get(1)));
        }
        return this;
    }

    public QueryMap<D> put(String key, String obj) {
        query.put(key, ValidateUtil.checkEmpty(obj));
        return this;
    }

    public void put(String key, Object obj) {
        query.put(key, obj);
    }
}
