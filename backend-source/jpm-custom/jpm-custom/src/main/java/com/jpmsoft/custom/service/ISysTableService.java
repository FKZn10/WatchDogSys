package com.jpmsoft.custom.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.custom.pojo.dto.TableActionDTO;
import com.jpmsoft.custom.pojo.entity.SysTable;


public interface ISysTableService extends IService<SysTable> {

    /**
     * 表创建
     *
     * @param request
     * @return
     */
    BaseResponse create(TableActionDTO request);

    /**
     * 添加列
     *
     * @param request
     * @return
     */
    BaseResponse addColumn(TableActionDTO request);

    /**
     * 修改列
     *
     * @param request
     * @return
     */
    BaseResponse modifyColumn(TableActionDTO request);

    /**
     * 修改列
     *
     * @param request
     * @return
     */
    BaseResponse dropColumn(TableActionDTO request);

    /**
     * 重命名列
     *
     * @param request
     * @return
     */
    BaseResponse renameColumn(TableActionDTO request);


    SysTable getByCode(String tableCode);
}
