package com.jpmsoft.fileUpload.service;

import com.jpmsoft.fileUpload.pojo.vo.OnlyOfficeCallbackVo;
import com.onlyoffice.model.documenteditor.Callback;
import com.onlyoffice.model.documenteditor.History;
import com.onlyoffice.model.documenteditor.HistoryData;

/**
 * OnlyOffice相关接口
 */
public interface IOnlyOfficeService {
    /**
     * OnlyOffice文档编辑
     *
     * @param callback
     * @param oldId
     * @param uId
     * @return
     */
    OnlyOfficeCallbackVo uploadReplace(Callback callback, String oldId, String uId);

    /**
     * 返回版本列表（给 onRequestHistory 调用）
     *
     * @param fileId
     * @return
     */
    History getHistoryList(String fileId);

    /**
     * 返回某一版本的数据（给 onRequestHistoryData 调用）
     *
     * @param fileId
     * @param version
     * @return
     */
    HistoryData getHistoryData(String fileId, Integer version);
}
