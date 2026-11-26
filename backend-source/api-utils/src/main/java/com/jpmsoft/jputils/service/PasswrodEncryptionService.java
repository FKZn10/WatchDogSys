package com.jpmsoft.jputils.service;

import org.springframework.stereotype.Service;

@Service
public interface PasswrodEncryptionService {

    /**
     * 获取密码加密结果
     *
     * @param passWord
     * @param salt
     * @return
     */
    String PasswrodEncryptionResult(String passWord, String salt);
}
