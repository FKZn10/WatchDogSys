package com.jpmsoft.gmgws.utils;

import com.jpmsoft.common.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.smartboot.license.client.License;
import org.smartboot.license.client.LicenseEntity;
import org.smartboot.license.client.LicenseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;

/**
 * license验证
 *
 * @Author: 李文涛 aaron
 * @date: 2024/7/5 10:29
 */
@Slf4j
@Component
public class LicenseUtil {

    @Value("${license.path}")
    private  String path;

    public boolean check() {
        return checkSoftLicense(path);
    }

    /**
     * license文件校验
     *
     * @param licensePath license文件绝对路径
     * @return 验证是否通过
     */
    public  boolean checkSoftLicense(String licensePath) {
        if (ValidateUtil.isEmpty(licensePath)) {
            log.error("license文件地址异常");
            return false;
        }
        boolean licensePass = true;
        File file = new File(licensePath);
        License license = new License();
        try {
            LicenseEntity licenseEntity = license.loadLicense(file);
            log.debug("-------license加密文件选的内容：{}", new String(licenseEntity.getData()));
            //System.out.println(new String(licenseEntity.getData()));
        } catch (LicenseException e) {
            log.error("---license授权文件异常---");
            licensePass = false;
        }
        return licensePass;
    }

}
