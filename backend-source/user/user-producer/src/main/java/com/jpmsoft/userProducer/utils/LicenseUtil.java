package com.jpmsoft.userProducer.utils;

import com.jpmsoft.common.utils.JpmCheckUtil;
import com.jpmsoft.common.utils.ValidateUtil;
import lombok.extern.slf4j.Slf4j;
import org.smartboot.license.client.License;
import org.smartboot.license.client.LicenseEntity;
import org.smartboot.license.client.LicenseException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

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
    private Resource[] path;

    public void check() {
        try {
            JpmCheckUtil.check(path[0].getFile().getAbsolutePath());
        } catch (IOException e) {
            System.out.println("未检测到license！");
            System.exit(1);
        }
    }

    /**
     * license文件校验
     *
     * @param licensePath license文件绝对路径
     * @return 验证是否通过
     */
    @Deprecated
    public boolean checkSoftLicense(String licensePath) {
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
            System.out.println(new String(licenseEntity.getData()));
        } catch (LicenseException e) {
            log.error("---license授权文件异常---", e);
            licensePass = false;
        }
        return licensePass;
    }

}
