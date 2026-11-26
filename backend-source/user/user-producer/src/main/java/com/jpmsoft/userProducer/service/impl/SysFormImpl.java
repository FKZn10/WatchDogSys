package com.jpmsoft.userProducer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.utils.BeanUtils;
import com.jpmsoft.common.utils.GenerateUUID;
import com.jpmsoft.common.utils.JacksonUtils;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.userProducer.constants.BaseMsg;
import com.jpmsoft.userProducer.mapper.SysFormMapper;
import com.jpmsoft.userProducer.pojo.dto.SysFormDTO;
import com.jpmsoft.userProducer.pojo.entity.SysForm;
import com.jpmsoft.userProducer.service.ISysFormService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


import java.util.Base64;
import java.nio.charset.StandardCharsets;

/**
 * @Author: 李文涛 aaron
 * @date: 2023/4/25 9:08
 */
@Service
public class SysFormImpl extends ServiceImpl<SysFormMapper, SysForm> implements ISysFormService {

    @Override
    public BaseResponse createOrUpdateSysForm(SysFormDTO request) {
        SysForm entity = new SysForm();
        BeanUtils.copyProperties(request, entity);
        if (ValidateUtil.isNotEmpty(entity.getSyId())) {
            SysForm old = baseMapper.selectById(entity.getSyId());
            if (old == null) return BaseResponse.fail(BaseMsg.infoNotExist);
            entity.setSyUpdateTime(new Date());
            entity.setSyCreateId(old.getSyCreateId());
            entity.setSyCreateName(old.getSyCreateName());
            entity.setSyCreateTime(old.getSyCreateTime());
            baseMapper.updateById(entity);
        } else {
            entity.setSyId(GenerateUUID.getUUID());
            entity.setSyCreateTime(new Date());
            baseMapper.insert(entity);
        }
        return BaseResponse.ok(entity.getSyId());
    }

    @Override
    public BaseResponse createOrUpdateSysFormInText(MultipartFile file) {
        try {
            return createOrUpdateSysForm(readMultipartFileJsonOne(file));
        } catch (Exception ex) {
            ex.printStackTrace();
            return BaseResponse.fail(ex.getMessage());
        }
    }

    @Override
    public BaseResponse importSysForm(List<SysFormDTO> request) {
        List<String> mas = new ArrayList<>();
        if (request.size() > 0) {
            List<String> keyIds = request.stream().map(n -> n.getTCode()).collect(Collectors.toList());
            QueryWrapper<SysForm> wrapper = new QueryWrapper<>();
            wrapper.in("T_Code", keyIds);
            List<SysForm> oldList = baseMapper.selectList(wrapper);
            for (SysFormDTO obj : request) {
                List<SysForm> ls = oldList.stream().filter(n -> n.getTCode().equals(obj.getTCode())).collect(Collectors.toList());
                SysForm entity = new SysForm();
                BeanUtils.copyProperties(obj, entity);
                entity.setSyId(GenerateUUID.getUUID());
                entity.setSyCreateTime(new Date());
                if (ls.size() > 0) {
                    entity.setSyState("false");
                }
                baseMapper.insert(entity);
            }
        }
        return BaseResponse.ok(mas.size() > 0 ? mas : "保存成功");

    }

    @Override
    public BaseResponse importSysForm(MultipartFile file) {
        try {
            return importSysForm(readMultipartFileJson(file));
        } catch (Exception ex) {
            return BaseResponse.fail(ex.getMessage());
        }


    }

    public SysFormDTO readMultipartFileJsonOne(MultipartFile file) throws IOException {
        // 读取文件内容
        byte[] fileBytes = file.getBytes();
        // 解析JSON数据
        String  content=new String(fileBytes, StandardCharsets.UTF_8);
        //判断content是否是base64
        if (ValidateUtil.isNotEmpty(content)) {
            if(content.startsWith("jpm64=")){
                String data = content.replace("jpm64=", "");
                byte[] decodedBytes = Base64.getDecoder().decode(data);
                String originalString = new String(decodedBytes, StandardCharsets.UTF_8);
                SysFormDTO requ = JacksonUtils.parseObject(originalString, SysFormDTO.class);
                return requ;
            }else{
                SysFormDTO requ = JacksonUtils.parseObject(content, SysFormDTO.class);
                return requ;
            }
        }
        return null;

        // 处理JSON数据
        // ...
    }


    public List<SysFormDTO> readMultipartFileJson(MultipartFile file) throws IOException {
        // 读取文件内容
        byte[] fileBytes = file.getBytes();
        // 解析JSON数据
        //ObjectMapper objectMapper = new ObjectMapper();
        //JsonNode jsonNode = objectMapper.readTree(fileBytes);

        //return requ;

        String  content=new String(fileBytes);
        //判断content是否是base64
        if (ValidateUtil.isNotEmpty(content)) {
            if(content.startsWith("jpm64=")){
                String data = content.replace("jpm64=", "");
                byte[] decodedBytes = Base64.getDecoder().decode(data);
                String originalString = new String(decodedBytes, StandardCharsets.UTF_8);
                List<SysFormDTO> requ = JacksonUtils.parseArray(originalString, SysFormDTO.class);
                return requ;
            }else{
                List<SysFormDTO> requ = JacksonUtils.parseArray(content, SysFormDTO.class);
                return requ;
            }
        }
        return null;

        // 处理JSON数据
        // ...
    }


    @Override
    public BaseResponse getSysFormList(SysFormDTO request) {
        QueryMap<SysFormDTO> query = new QueryMap<>(request);
        query.put("syName").put("syType").put("syState").put("syVersion")
                .put("syCreateName").put("syUpdateName").put("tCode").put("tId")
                .put("syDataSourceType");
        IPage<SysFormDTO> page = new Page<>(request.getPage(), request.getSize());
        List<SysFormDTO> data = baseMapper.getSysFormList(page, query.getQuery());
        return BaseResponse.ok(data, (int) page.getTotal());
    }

    @Override
    public BaseResponse copySysFormById(String id) {
        SysForm entity = baseMapper.selectById(id);
        entity.setSyId(GenerateUUID.getUUID());
        entity.setSyCreateTime(new Date());
        entity.setSyName(entity.getSyName() + "_" + (new Date()).getTime());
        entity.setSyState("false");
        baseMapper.insert(entity);
        return BaseResponse.ok("复制成功");
    }


    @Override
    public BaseResponse getSysFormById(String id) {
        SysForm entity = baseMapper.selectById(id);
        if (entity == null) return BaseResponse.fail(BaseMsg.infoNotExist);
        return BaseResponse.ok(entity);
    }

    @Override
    public BaseResponse getSysFormByCode(String code, String teId) {
        QueryWrapper<SysForm> wrapper = new QueryWrapper<>();
        wrapper.eq("T_Code", code);
        wrapper.eq("Sy_State", "true");
        if (ValidateUtil.isNotEmpty(teId))
            wrapper.eq("Te_Id", teId);
        return BaseResponse.ok(baseMapper.selectList(wrapper));
    }
}
