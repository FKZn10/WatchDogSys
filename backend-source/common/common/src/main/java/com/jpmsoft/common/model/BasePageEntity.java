package com.jpmsoft.common.model;

import com.jpmsoft.common.utils.ValidateUtil;

/**
 * 分页查询入参
 *
 * @author: 李文涛 aaron
 * @date: 2022/8/12 9:49
 */
public class BasePageEntity {

    private String apiName;
    private String version;
    private int page;
    private int size;
    //主表排序字段
    private String sortFiled;
    //查询结果字段排序
    private String sortSideFiled;
    private String sortType;
    private String teId;

    public Integer getPageStart() {
        if (this.page - 1 < 0)
            return 0;
        return (this.page - 1) * this.size;
    }

    public int getPage() {
        if (this.page > 0)
            return page;
        else
            return 0;
    }

    public String getSortType() {
        if (ValidateUtil.isEmpty(sortType))
            return null;
        if (specialChar(sortType))
            return null;
        else return sortType.toUpperCase();
    }

    public String getSortFiled() {
        if (ValidateUtil.isEmpty(sortFiled))
            return null;
        if (specialChar(sortFiled))
            return null;
        return humpTurnDatabase(sortFiled);
    }

    public String getApiName() {
        return apiName;
    }

    public void setApiName(String apiName) {
        this.apiName = apiName;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public void setSortFiled(String sortFiled) {
        this.sortFiled = sortFiled;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public String getSortSideFiled() {
        if (ValidateUtil.isEmpty(sortSideFiled))
            return null;
        if (specialChar(sortSideFiled))
            return null;
        return sortSideFiled;
    }

    public void setSortSideFiled(String sortSideFiled) {
        this.sortSideFiled = sortSideFiled;
    }


    /**
     * 驼峰转数据库字段
     *
     * @param str
     * @return
     */
    private String humpTurnDatabase(String str) {
        if (str == null || str.equals(""))
            return null;
        //cusomer调用producer会调用get set
        str = str.replaceAll("_", "");
        char[] letters = str.toCharArray();
        String filed = "";
        for (char letter : letters) {
            if (letter >= 'A' && letter <= 'Z') {
                filed += "_" + letter;
            } else
                filed += letter;
        }
        return filed;
    }

    public String getTeId() {
        return teId;
    }

    public void setTeId(String teId) {
        this.teId = teId;
    }

    /**
     * 验证ID参数特殊字符 防止注入
     *
     * @param str
     * @return
     */
    private boolean specialChar(String str) {
        if (str.contains("'")) return true;
        if (str.contains("\\\"")) return true;
        if (str.contains("(")) return true;
        if (str.contains(")")) return true;
        if (str.contains("=")) return true;
        if (str.contains("*")) return true;
        return false;
    }
}
