package com.jpmsoft.jputils.service;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DataDecryptService {

    String Sm2Decrypt(String encDataHex);

    String SelfSm2Decrypt(String encDataHex);

    String Sm2Encryption(String json);

    String SelfSm2Encryption(String json);


    String Sm2EncryptionFront(String json,String publicKey);


    List<String> SelfSm2EncryptionBath(List<String> jsons);

    String Sm2EncryptionEncode(String json);

    String Sm2EncryptionGetUrl(String json);

    String generateSm2Sign(String data);

    /**
     * 验签
     *
     * @param encDataHex sign
     * @param data       sign明文
     * @return
     */
    Boolean verificationSm2Sign(String encDataHex, String data);


}
