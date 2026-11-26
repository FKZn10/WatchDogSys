package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysMessageTempMapper;
import com.jpmsoft.userProducer.pojo.dto.SysMessageTempDto;
import com.jpmsoft.userProducer.pojo.entity.SysMessageTemp;
import com.jpmsoft.userProducer.pojo.vo.SysMessageTempVo;
import com.jpmsoft.userProducer.service.ISysMessageTempService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author: 李文涛 aaron
 * @date: 2022/11/11 13:59
 */
@Service
public class SysMessageTempImpl extends ServiceImpl<SysMessageTempMapper, SysMessageTemp> implements ISysMessageTempService {

    @Override
    public BaseResponse createOrUpdateSysMessageTemp(SysMessageTempDto request, String uId) {
        SysMessageTemp entity = new SysMessageTemp();
        if (codeIsExist(request))
            return BaseResponse.ok(BaseMsg.msgTempCodeExist);
        checkSqllegitimate(request.getSmtSql());
        BeanUtils.copyProperties(request, entity);
        Date now = new Date();
        if (ValidateUtil.isEmpty(entity.getId())) {
            entity.setCreateUid(uId);
            entity.setUpdateUid(uId);
            entity.setCreateTime(now);
            entity.setUpdateTime(now);
            entity.setId(GenerateUUID.getUUID());
            baseMapper.insert(entity);
        } else {
            SysMessageTemp old = baseMapper.selectById(entity.getId());
            if (old == null) return BaseResponse.fail(BaseMsg.infoNotExist);
            entity.setUpdateUid(uId);
            entity.setUpdateTime(now);
            entity.setCreateUid(old.getCreateUid());
            entity.setCreateTime(old.getCreateTime());
            baseMapper.updateById(entity);
        }
        return BaseResponse.ok(entity.getId());
    }

    @Override
    public BaseResponse getSysMessageTempList(SysMessageTempDto request) {
        QueryMap<SysMessageTempDto> query = new QueryMap<>(request);
        query.put("smtName").put("smtCode").put("smtType").put("smtContent")
                .put("createUname").put("updateUname");
        IPage<SysMessageTempVo> page = new Page<>(request.getPage(), request.getSize());
        List<SysMessageTempVo> datas = baseMapper.getSysMessageTempList(page, query.getQuery());
        return BaseResponse.ok(datas, (int) page.getTotal());
    }

    @Override
    public BaseResponse getSysMessageTempById(String id) {
        return BaseResponse.ok(baseMapper.getSysMessageTempById(id));
    }

    @Override
    public SysMessageTempVo getSysMessageTempByCode(String code) {
        return baseMapper.getSysMessageTempByCode(code);
    }

    @Override
    public BaseResponse delSysMessageTempById(String id) {
        SysMessageTemp entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        baseMapper.deleteById(id);
        return BaseResponse.ok();
    }

    /**
     * 验证编码是否重复
     *
     * @param request
     * @return
     */
    private boolean codeIsExist(SysMessageTempDto request) {
        QueryWrapper<SysMessageTemp> wrapper = new QueryWrapper<>();
        wrapper.eq("Smt_Code", request.getSmtCode());
        if (ValidateUtil.isNotEmpty(request.getId()))
            wrapper.ne("id", request.getId());
        return baseMapper.exists(wrapper);
    }

    /**
     * 非法SQL关键词
     */
    private static final List<String> noAuthSql = Arrays.asList("DROP ", "UPDATE ", "DELETE ", "ALTER ", "CREATE ", "INSERT ");

    /**
     * 验证sql是否合法
     *
     * @param sql
     */
    private void checkSqllegitimate(String sql) {
        if (ValidateUtil.isEmpty(sql)) return;
        sql = sql.toUpperCase(Locale.ENGLISH);
        for (String s : noAuthSql) {
            if (sql.contains(s))
                throw new JPMException(BaseMsg.sqlError);
        }
    }
}
