package com.jpmsoft.jputils.controller;

import com.icbc.hsm.software.external.ClearKeyFunction;
import com.jpmsoft.jputils.service.DataDecryptService;
import com.jpmsoft.jputils.utils.Sm3Utils;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "数据加解密处理", value = "数据加解密处理")
@RequestMapping("/v1/decrypt")
public class DataDecryptController {

    @Autowired
    private DataDecryptService dataDecryptService;

    /**
     * 对接时解密（对方使用公钥，我方持有私钥）
     *
     * @param encDataHex
     * @return
     */
    @GetMapping("/Sm2Decrypt")
    public String Sm2Decrypt(@RequestParam String encDataHex) {
        return dataDecryptService.Sm2Decrypt(encDataHex);
    }

    /**
     * 自用解密 数据传输时（前端web使用公钥，我方持有私钥）
     *
     * @param encDataHex
     * @return
     */
    @GetMapping("/SelfSm2Decrypt")
    public String SelfSm2Decrypt(@RequestParam String encDataHex) {
        return dataDecryptService.SelfSm2Decrypt(encDataHex);
    }

    /**
     * 自用加密 数据传输时（前端web使用公钥，我方持有私钥）
     *
     * @param json
     * @return
     */
    @GetMapping("/SelfSm2Encryption")
    public String SelfSm2Encryption(@RequestParam String json) {
        return dataDecryptService.SelfSm2Encryption(json);
    }


    /**
     * 实现和前端一样的加密
     * @param json
     * @param publicKey
     * @return
     */
    @GetMapping("/Sm2EncryptionFront")
    public String Sm2EncryptionFront(@RequestParam String json, @RequestParam(required = false) String publicKey) {
        return dataDecryptService.Sm2EncryptionFront(json,publicKey);
    }

    @PostMapping("/SelfSm2EncryptionBath")
    public List<String> SelfSm2EncryptionBath(@RequestBody List<String> jsons) {
        return dataDecryptService.SelfSm2EncryptionBath(jsons);
    }

    /**
     * 对接时使用 我方持有公钥对方持有私钥
     *
     * @param json
     * @return
     */
    @GetMapping("/Sm2Encryption")
    public String Sm2Encryption(@RequestParam String json) {
        return dataDecryptService.Sm2Encryption(json);
    }

    @GetMapping("/Sm2EncryptionEncode")
    public String Sm2EncryptionEncode(@RequestParam String json) {
        return dataDecryptService.Sm2EncryptionEncode(json);
    }

    /**
     * 对接时使用 我方持有公钥对方持有私钥
     * Get接口传递对象
     *
     * @param json
     * @return
     */
    @GetMapping("/Sm2EncryptionGetUrl")
    public String Sm2EncryptionGetUrl(@RequestParam String json) {
        return dataDecryptService.Sm2EncryptionGetUrl(json);
    }


    //生成签名
    @GetMapping("/generateSm2Sign")
    public String generateSm2Sign(@RequestParam String data) {
        return dataDecryptService.generateSm2Sign(data);
    }

    /**
     * 验签
     *
     * @param encDataHex sign
     * @param data       sign明文
     * @return
     */
    @GetMapping("/verificationSm2Sign")
    public Boolean verificationSm2Sign(@RequestParam String encDataHex, @RequestParam String data) {
        return dataDecryptService.verificationSm2Sign(encDataHex, data);
    }

    /**
     * SM3加密
     *
     * @param str
     * @return
     */
    @GetMapping("/sm3Encryption")
    public String sm3Encryption(@RequestParam String str) {
        try {
            str = URLDecoder.decode(str, "UTF-8");
            String newPass = Sm3Utils.encrypt(str);
            return newPass;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    @GetMapping("/makeKey")
    public String makeKey() {
        try {
            //步骤1. 客户端生成自己的公私钥对并存储，将公钥发送给工行
            HashMap<String, String> keypair = ClearKeyFunction.generateSM2KeyPair();
            String privatekeyString = keypair.get("privateKey");
            //步骤2.将自己的公钥线下发送给工行;
            String publickeyString = keypair.get("publicKey");
            System.out.println("privatekeyString: " + privatekeyString);
            System.out.println("publickeyString: " + publickeyString);
        } catch (Exception ex) {
            return null;
        }
        return null;
    }
}
