package com.jpmsoft.projectProducer.service.function.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.utils.RequestBean;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.projectProducer.config.SnowflakeNew;
import com.jpmsoft.projectProducer.constants.ResMsg;
import com.jpmsoft.projectProducer.mapper.function.FunctionTimeConfigMapper;
import com.jpmsoft.projectProducer.pojo.function.FunctionTimeConfig;
import com.jpmsoft.projectProducer.service.function.IFunctionTimeConfigService;
import com.jpmsoft.projectProducer.utils.BaseHzq;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * <p>
 * 功能时间配置 服务实现类
 * </p>
 *
 * @author zhangl
 * @since 2022-08-01
 */
@Service
public class FunctionTimeConfigServiceImpl extends ServiceImpl<FunctionTimeConfigMapper, FunctionTimeConfig> implements IFunctionTimeConfigService {

    @Resource
    private FunctionTimeConfigServiceImpl service;


    @Resource
    @Qualifier("mySnowFlake")
    private SnowflakeNew snowflakeNew;


    /**
     * 单个新增
     * 2020-2-21
     *
     * @param requestBean
     * @return
     */
    public BaseResponse createFunctionTimeConfig(RequestBean requestBean) {
        FunctionTimeConfig info = BaseHzq.convertValue(requestBean.getInfo(), FunctionTimeConfig.class);
        String projectId = info.getProjectId();
        LambdaQueryWrapper<FunctionTimeConfig> eq = new LambdaQueryWrapper<FunctionTimeConfig>()
                .eq(FunctionTimeConfig::getProjectId, projectId)
                .eq(FunctionTimeConfig::getFunctionCode, info.getFunctionCode());
        List<FunctionTimeConfig> list = this.list(eq);
        if (list.size() > 0) {
            return BaseResponse.fail(ResMsg.recordExist);
        }
        info.setObjectId(snowflakeNew.nextUUID());
        this.save(info);
        return BaseResponse.ok(info.getObjectId());
    }


    /**
     * 更新单条数据所有字段
     * 2020-2-21
     *
     * @param requestBean
     * @return
     */
    public BaseResponse updateFunctionTimeConfigAllField(RequestBean requestBean) {
        this.updateById(BaseHzq.convertValue(requestBean.getInfo(), FunctionTimeConfig.class));
        return BaseResponse.ok();
    }


    /**
     * 单条逻辑删除
     * 2020-2-21
     *
     * @param requestBean
     * @return
     */
    public BaseResponse deleteFunctionTimeConfigLogicalSingle(RequestBean requestBean) {
        this.removeById((String) requestBean.getInfo());
        return BaseResponse.ok();
    }


    /**
     * 根据主键获取单条数据
     * 2020-2-21
     *
     * @param requestBean
     * @return
     */
    public BaseResponse getFunctionTimeConfigInfoById(RequestBean requestBean) {
        return BaseResponse.ok(this.getById((String) requestBean.getInfo()));
    }


    /**
     * 获取分页数据和条件查询
     *
     * @param requestBean
     * @return
     */
    public BaseResponse getFunctionTimeConfigPageList(RequestBean requestBean) {
        Page page = BaseHzq.convertValue(requestBean.getInfo(), Page.class);
        if (ValidateUtil.isNull(page)) {
            page = new Page();
        }
        QueryWrapper<FunctionTimeConfig> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_time");
        // TODO 添加查询条件
        Map<String, String> queryMap = page.getRecords().size() > 0 ? (HashMap) page.getRecords().get(0) : null;
        if (ValidateUtil.isNotEmpty(queryMap.get("projectName"))) {
            queryWrapper.likeRight("project_name", queryMap.get("projectName"));
        }
        //更新时间查询
        IPage page1 = this.page(page, queryWrapper);
        return BaseResponse.ok(page1);
    }
}
