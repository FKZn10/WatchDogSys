package com.jpmsoft.jputils.service.impl;

import com.jpmsoft.jputils.service.PasswrodEncryptionService;
import com.jpmsoft.jputils.utils.Sm3Utils;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;

@Service
public class PasswrodEncryptionImpl implements PasswrodEncryptionService {

    @Override
    public String PasswrodEncryptionResult(String passWord, String salt) {
        try {
            passWord = URLDecoder.decode(passWord, "UTF-8");
            salt = URLDecoder.decode(salt, "UTF-8");
            String newPass = Sm3Utils.encrypt(Sm3Utils.encrypt(passWord) + salt + salt);
            return newPass;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
