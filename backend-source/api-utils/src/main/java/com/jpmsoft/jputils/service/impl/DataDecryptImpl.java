package com.jpmsoft.jputils.service.impl;

import com.icbc.bcprov.org.bouncycastle.asn1.*;
import com.icbc.bcprov.org.bouncycastle.util.Arrays;
import com.icbc.hsm.software.apiLib.Signature;
import com.icbc.hsm.software.apiLib.Sm2Cipher;
import com.icbc.hsm.software.external.ClearKeyFunction;
import com.icbc.hsm.software.parms.ClearKeyParameter;
import com.icbc.hsm.utils.AlgorithmConstants;
import com.icbc.hsm.utils.encoders.Hex;
import com.jpmsoft.common.base.BaseResponse;
import com.jpmsoft.common.exception.JPMException;
import com.jpmsoft.common.utils.ValidateUtil;
import com.jpmsoft.jputils.constants.ApiMsg;
import com.jpmsoft.jputils.feign.UserFeign;
import com.jpmsoft.jputils.service.DataDecryptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class DataDecryptImpl implements DataDecryptService {

    @Value("${SM2.privateKey}")
    private String privatekeyString;

    @Value("${SM2.publicKey}")
    private String publickeyString;

    /**
     * 数据传输用解密私钥
     */
    @Value("${SM2.selfPriKey}")
    private String selfPriKey;

    @Value("${SM2.selfPublicKey}")
    private String selfPublicKey;


    @Value("${SM2.dock.signKey}")
    private String signKey;

    @Value("${SM2.dock.encryKey}")
    private String encryKey;

    @Resource
    private UserFeign userFeign;

    /**
     * 读取基础配置中配置的密钥
     * 读取的到优先使用基础配置中的密钥
     * 读取不到则默认使用当前yml配置文件中的密钥
     */
    private String getSm2KeyStr(String confCode) {
        BaseResponse res = userFeign.GetConfigValueByCode(confCode);
        if (res != null && res.getSuccess() && res.getStatusCode() == HttpStatus.SC_OK
                && res.getData() != null && ValidateUtil.isNotEmpty(res.getData().toString()))
            return res.getData().toString();
        switch (confCode) {
            case "SM2_Key_PrivateKey":
                return this.privatekeyString;
            case "SM2_Key_PublicKey":
                return this.publickeyString;
            case "SM2_Key_SelfPriKey":
                return this.selfPriKey;
            case "SM2_Key_SelfPublicKey":
                return this.selfPublicKey;
            case "SM2_Key_SignKey":
                return this.signKey;
            case "SM2_Key_EncryKey":
                return this.encryKey;
            default:
                throw new JPMException(ApiMsg.unknowSm2KeyConf);
        }
    }

    @Override
    public String Sm2Decrypt(String encDataHex) {
        try {
            ClearKeyParameter privateKey = ClearKeyFunction.loadClear(getSm2KeyStr("SM2_Key_PrivateKey"), "SM2private");
            byte[] decData = Sm2Cipher.decipher(privateKey, Hex.decode(encDataHex));
            return new String(decData, StandardCharsets.UTF_8.name());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }



    @Override
    public String Sm2EncryptionGetUrl(String json) {
        try {
            json = URLDecoder.decode(json, "UTF-8");
            ClearKeyParameter pubplicey = ClearKeyFunction.loadClear(getSm2KeyStr("SM2_Key_EncryKey"), "SM2public");
            //加密结果为ans1编码的。需要根据交互双方协商展开为hex string或者base64编码传输
            byte[] encData = Sm2Cipher.encipher(pubplicey, json.getBytes());
            return Hex.toHexString(encData);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    @Override
    public String Sm2Encryption(String json) {
        try {
            ClearKeyParameter pubplicey = ClearKeyFunction.loadClear(getSm2KeyStr("SM2_Key_PublicKey"), "SM2public");
            //加密结果为ans1编码的。需要根据交互双方协商展开为hex string或者base64编码传输
            byte[] encData = Sm2Cipher.encipher(pubplicey, json.getBytes());
            return Hex.toHexString(encData);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 解密
     * @param encDataHex
     * @return
     */
    @Override
    public String SelfSm2Decrypt(String encDataHex) {
        try {
            if (ValidateUtil.isEmpty(encDataHex))
                return null;
            ClearKeyParameter privateKey = ClearKeyParameter.getInstance("SM2", true, Hex.decode(getSm2KeyStr("SM2_Key_SelfPriKey")));
            byte[] decData = Sm2Cipher.decipher(privateKey, C1C3C2ToAns1(hexToByte(encDataHex)));
            return new String(decData, StandardCharsets.UTF_8.name());
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * 加密
     * @param json
     * @param pbKey
     * @return
     */
    private String selfSm2Encryption(String json, String pbKey) {
        try {
            ClearKeyParameter pubplicey = ClearKeyFunction.loadClear(pbKey,"SM2public");
            //加密结果为ans1编码的。需要根据交互双方协商展开为hex string或者base64编码传输
            byte[] encData = Sm2Cipher.encipher(pubplicey,json.getBytes());
            return Hex.toHexString(ans1ToC1C3C2(encData));
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    public String SelfSm2Encryption(String json) {
        try {
            json = URLDecoder.decode(json, "UTF-8");
            return selfSm2Encryption(json, getSm2KeyStr("SM2_Key_SelfPublicKey"));
        } catch (Exception ex) {
            log.error("web传输加密失败");
            return null;
        }
    }

    @Override
    public String Sm2EncryptionFront(String json,String publicKey) {
        try {
            json = URLDecoder.decode(json, "UTF-8");
            if(ValidateUtil.isEmpty(publicKey)){
                publicKey ="04990bdd813a9cc51be19e04c682b404974207d062e8c83ee736ad653056168d659ad16d72ddb0a5d6a992917f1018bdc641da024aaf27f84ff6897337aa999105";
            }
            return selfSm2Encryption(json, publicKey);
        } catch (Exception ex) {
            log.error("web传输加密失败");
            return null;
        }
    }

    @Override
    public List<String> SelfSm2EncryptionBath(List<String> jsons) {
        try {
            String selfPbKey = getSm2KeyStr("SM2_Key_SelfPublicKey");
            List<String> datas = new ArrayList<>();
            for (String json : jsons) {
                json = URLDecoder.decode(json, "UTF-8");
                datas.add(selfSm2Encryption(json, selfPbKey));
            }
            return datas;
        } catch (Exception ex) {
            log.error("web传输加密失败");
            return null;
        }
    }

    @Override
    public String Sm2EncryptionEncode(String json) {
        try {
            json = URLDecoder.decode(json, "UTF-8");
            ClearKeyParameter pubplicey = ClearKeyFunction.loadClear(getSm2KeyStr("SM2_Key_PublicKey"), "SM2public");
            //加密结果为ans1编码的。需要根据交互双方协商展开为hex string或者base64编码传输
            byte[] encData = Sm2Cipher.encipher(pubplicey, json.getBytes());
            return Hex.toHexString(encData);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    public String generateSm2Sign(String data) {
        try {
            data = URLDecoder.decode(data, "UTF-8");
            ClearKeyParameter privateKey = ClearKeyFunction.loadClear(getSm2KeyStr("SM2_Key_PrivateKey"), "SM2private");
            byte[] sigSrcData = data.getBytes(StandardCharsets.UTF_8); //拼装需要签名的完整报文
            Signature dv = Signature.getInstance(AlgorithmConstants.SM3withSM2); //验签
            byte[] signature = dv.sign(privateKey, sigSrcData);  //验签
            return Hex.toHexString(signature);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }


    @Override
    public Boolean verificationSm2Sign(String encDataHex, String data) {
        try {
            data = URLDecoder.decode(data, "UTF-8");
            Signature dv = Signature.getInstance(AlgorithmConstants.SM3withSM2); //验签
            byte[] sigSrcData = data.getBytes(StandardCharsets.UTF_8); //拼装需要签名的完整报文
            byte[] signature_server = Hex.decode(encDataHex);
            ClearKeyParameter privateKey_icbc = ClearKeyFunction.loadClear(getSm2KeyStr("SM2_Key_SignKey"), "SM2public");
            return dv.verify(privateKey_icbc, sigSrcData, signature_server);
        } catch (Exception ex) {
            log.error(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }


    public static byte[] C1C3C2ToAns1(byte[] cipherData) throws Exception {
        byte[] X = Arrays.copyOfRange(cipherData, 1, 33);
        byte[] Y = Arrays.copyOfRange(cipherData, 33, 65);
        byte[] C3 = Arrays.copyOfRange(cipherData, 65, 97);
        byte[] C2 = Arrays.copyOfRange(cipherData, 97, cipherData.length);

        String Xhead = Hex.toHexString(X).substring(0, 3);
        String Yhead = Hex.toHexString(Y).substring(0, 3);
        // 说明X或者Y值是31字节且高位bit不为1
        if (Integer.parseInt(Xhead, 16) < 8 || Integer.parseInt(Yhead, 16) < 8) {
//			System.out.println("inproper data: " + Hex.toHexString(cipherData));
            return null;
        }
        ASN1EncodableVector v = new ASN1EncodableVector();
        v.add(new ASN1Integer(new BigInteger(1, X)));
        v.add(new ASN1Integer(new BigInteger(1, Y)));
        v.add(new DEROctetString(C3));
        v.add(new DEROctetString(C2));
        return new DERSequence(v).getEncoded(ASN1Encoding.DER);
    }

    /**
     *
     * @param cipherData  ASN.1结构的SM2加密输出
     * @return            C1C3C2结构（带X'04'头）
     * @throws Exception  解析异常
     */
    public static byte[] ans1ToC1C3C2(byte[] cipherData) throws Exception{
        ASN1Sequence seq = (ASN1Sequence)ASN1Primitive.fromByteArray(cipherData);
        if (seq.size() == 4)
        {
            byte[] X = ((ASN1Integer)seq.getObjectAt(0)).getValue().toByteArray();
            if(X.length == 33){   //去掉高位X'00'
                X = Arrays.copyOfRange(X, 1, X.length);
            }
            if(X.length == 31){   //补充一位X'00'到32字节
                byte[] Xtemp = new byte[1 + X.length];
                System.arraycopy(Hex.decode("00"), 0, Xtemp, 0, 1);
                System.arraycopy(X, 0, Xtemp, 1, X.length);
                X = Xtemp;
            }
            byte[] Y = ((ASN1Integer)seq.getObjectAt(1)).getValue().toByteArray();
            if(Y.length == 33){   //去掉高位X'00'
                Y = Arrays.copyOfRange(Y, 1, Y.length);
            }
            if(Y.length == 31){   //补充一位X'00'到32字节
                byte[] Ytemp = new byte[1 + Y.length];
                System.arraycopy(Hex.decode("00"), 0, Ytemp, 0, 1);
                System.arraycopy(Y, 0, Ytemp, 1, Y.length);
                Y = Ytemp;
            }
            byte[] C3 = ((DEROctetString)seq.getObjectAt(2)).getOctets();
            byte[] C2 = ((DEROctetString)seq.getObjectAt(3)).getOctets();
            byte[] bcFormat = new byte[1 + X.length + Y.length + C3.length + C2.length];
            System.arraycopy(Hex.decode("04"), 0, bcFormat, 0, 1);
            System.arraycopy(X, 0, bcFormat, 1, X.length);
            System.arraycopy(Y, 0, bcFormat, 1+X.length, Y.length);
            System.arraycopy(C3, 0, bcFormat, 1+X.length+Y.length, C3.length);
            System.arraycopy(C2, 0, bcFormat, 1+X.length+Y.length+C3.length, C2.length);
            return bcFormat;
        }

        throw new Exception("encrypted data error!");
    }

    /**
     * hex转byte数组
     *
     * @param hex
     * @return
     */
    public static byte[] hexToByte(String hex) {
        int m = 0, n = 0;
        int byteLen = hex.length() / 2; // 每两个字符描述一个字节
        byte[] ret = new byte[byteLen];
        for (int i = 0; i < byteLen; i++) {
            m = i * 2 + 1;
            n = m + 1;
            int intVal = Integer.decode("0x" + hex.substring(i * 2, m) + hex.substring(m, n));
            ret[i] = Byte.valueOf((byte) intVal);
        }
        return ret;
    }

}
