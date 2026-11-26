package com.jpmsoft.fileUpload.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jpmsoft.common.base.QueryMap;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.mapper.SysUserMapper;
import com.jpmsoft.fileUpload.pojo.dto.UserDTO;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.DepartmentPSysUserDTO;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.DepartmentSysUserDTO;
import com.jpmsoft.fileUpload.pojo.dto.easyexcel.SysUserVo;
import com.jpmsoft.fileUpload.pojo.entity.SysUser;
import com.jpmsoft.fileUpload.service.IApiUtilsService;
import com.jpmsoft.fileUpload.service.ISysConfigService;
import com.jpmsoft.fileUpload.service.ISysDepartmentService;
import com.jpmsoft.fileUpload.service.ISysUserService;
import com.jpmsoft.fileUpload.utils.ExcelUtil;
import com.jpmsoft.fileUpload.utils.FileUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class SysUserImpl extends ServiceImpl<SysUserMapper, SysUser> implements ISysUserService {
    @Value("${file.upload.dirPath}")
    private String dirPath;
    @Resource
    private ISysDepartmentService sysDepartmentService;
    @Resource
    private IApiUtilsService apiUtilsService;
    @Resource
    private ISysConfigService sysConfigService;

    @Override
    public String getUserNameByUId(String uId) {
        SysUser user = baseMapper.selectById(uId);
        if (user == null)
            return null;
        else
            return user.getuName();
    }

    @Override
    public SysUser getUserByAccount(String account) {
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("U_Account", account);
        List<SysUser> data = baseMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(data)) return data.get(0);
        return null;
    }

    @Override
    public void exportUserWithDepartment(UserDTO user, HttpServletResponse response) {
        String root = sysConfigService.getRootDirectory();
        if (ValidateUtil.isEmpty(root))
            throw new JPMException(String.format(FileMsg.notFoundSysConfig, "rootDirectory"));
        String filePath = FileUtil.temporaryPath(root + "/" + dirPath, "xlsx");
        QueryMap<UserDTO> params = new QueryMap<>(user);
        params.put("dId").put("uState").put("uName").put("pName").put("uType").put("cId").put("cName").put("plId").put("uAccount").put("uCode").put("uMtel").put("uId").put("all");
        if (ValidateUtil.isNotEmpty(user.getDepCid())) {
            params.put("depIds", sysDepartmentService.getAllChildDepartmentByCid(user.getDepCid()));
        }
        List<DepartmentSysUserDTO> data = baseMapper.getUserListByMoreCondition(params.getQuery());
        data = userIdCardHandle(data);
        ExcelUtil.export2File(data, DepartmentSysUserDTO.class, filePath, "用户信息");
        //region 下载文件后删除临时文件
        FileUtil.downFileAndDelFile(response, filePath, "用户信息.xlsx");
        //endregion
    }

    @Override
    public void exportUserWithDepartmentP(UserDTO user, HttpServletResponse response) {
        String root = sysConfigService.getRootDirectory();
        if (ValidateUtil.isEmpty(root))
            throw new JPMException(String.format(FileMsg.notFoundSysConfig, "rootDirectory"));
        String filePath = FileUtil.temporaryPath(root + "/" + dirPath, "xlsx");
        QueryMap<UserDTO> params = new QueryMap<>(user);
        params.put("pdId").put("filterPosition").put("pdName").put("projectName").put("pId").put("uName").put("uAccount").put("uCode").put("uMtel").put("cName").put("cId").put("uState").put("uType").put("ppName").put("ppId").put("ppCode").put("pName");
        List<DepartmentPSysUserDTO> data = baseMapper.GetUserListByDPId(params.getQuery());
        data = userPIdCardHandle(data);
        ExcelUtil.export2File(data, DepartmentPSysUserDTO.class, filePath, "用户信息");
        //region 下载文件后删除临时文件
        FileUtil.downFileAndDelFile(response, filePath, "用户信息.xlsx");
        //endregion
    }

    /**
     * 响应结果身份证转掩码
     */
    private List<DepartmentSysUserDTO> userIdCardHandle(List<DepartmentSysUserDTO> models) {
        List<DepartmentSysUserDTO> datas = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(models)) {
            AtomicReference<Integer> i = new AtomicReference<>(1);
            datas = models.stream().map(n -> {
                n.setSort(i.get());
                i.getAndSet(i.get() + 1);
                //n.setUIdcard(apiUtilsService.SelfSm2Decrypt(n.getUIdcard()));
                return n;
            }).collect(Collectors.toList());
        }
        return datas;
    }

    private List<DepartmentPSysUserDTO> userPIdCardHandle(List<DepartmentPSysUserDTO> models) {
        List<DepartmentPSysUserDTO> datas = new ArrayList<>();
        if (ValidateUtil.isNotEmpty(models)) {
            AtomicReference<Integer> i = new AtomicReference<>(1);
            datas = models.stream().map(n -> {
                n.setSort(i.get());
                i.getAndSet(i.get() + 1);
                //n.setUIdcard(apiUtilsService.SelfSm2Decrypt(n.getUIdcard()));
                return n;
            }).collect(Collectors.toList());
        }
        return datas;
    }

    @Override
    public void exportUserList(UserDTO user, String uId, HttpServletResponse response) {
        QueryMap<UserDTO> queryMap = new QueryMap<>(user);
        queryMap.put("uState").put("uName").put("uType").put("cId").put("cName")
                .put("uCode").put("uMtel");
        List<SysUserVo> datas = baseMapper.GetUserList(queryMap.getQuery());
        if (ValidateUtil.isEmpty(datas)) {
            throw new JPMException(FileMsg.notData2Export);
        }

        for (int i = 0; i < datas.size(); i++) {
            datas.get(i).setIndex(i + 1);
        }

        String root = sysConfigService.getRootDirectory();
        if (ValidateUtil.isEmpty(root)) {
            throw new JPMException(String.format(FileMsg.notFoundSysConfig, "rootDirectory"));
        }
        String filePath = FileUtil.temporaryPath(root + "/" + dirPath, "xlsx");
        ExcelUtil.export2File(datas, SysUserVo.class, filePath, "用户信息");
        FileUtil.downFileAndDelFile(response, filePath, "用户信息.xlsx");
    }
}
