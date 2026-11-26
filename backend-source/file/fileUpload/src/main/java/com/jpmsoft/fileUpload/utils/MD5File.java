package com.jpmsoft.fileUpload.utils;

import com.jpmsoft.common.exception.JPMException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;

/**
 * MD5验证文件是否为同文件
 *
 * @author: 李文涛 aaron
 * @date: 2022/9/28 9:35
 */
public class MD5File {

    protected static char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    public static String getFileKey(MultipartFile f) {
        File tempFile = null;
        try {
            tempFile = File.createTempFile("ai_temp", f.getOriginalFilename());
            f.transferTo(tempFile);
            return MD5File.getFileMD5Str(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            if (tempFile != null && tempFile.exists()) {
                tempFile.delete();
            }
        }
    }

    /**
     * 从字节数组计算文件MD5（避免临时文件问题）
     * @param fileBytes 文件字节数组
     * @return MD5字符串
     */
    public static String getFileKeyFromBytes(byte[] fileBytes) {
        if (fileBytes == null || fileBytes.length == 0) {
            return null;
        }
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(fileBytes);
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                int val = b & 0xff;
                if (val < 16) sb.append("0");
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 生成MD5唯一指纹
    public static String md5Hash(String str) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] bytes = md.digest(str.getBytes(StandardCharsets.UTF_8));
            StringBuilder sb = new StringBuilder();
            for (byte b : bytes) {
                int val = b & 0xff;
                if (val < 16) sb.append("0");
                sb.append(Integer.toHexString(val));
            }
            return sb.toString();
        } catch (Exception e) {
            return null;
        }
    }

    public static String getFileMD5Str(File file) {
        try {
            String str;
            FileInputStream in = new FileInputStream(file);
            FileChannel ch = in.getChannel();
            //700000000 bytes are about 670M
            int maxSize = 700000000;
            long startPosition = 0L;
            long step = file.length() / maxSize;
            MessageDigest messageDigest = null;
            try {
                messageDigest = MessageDigest.getInstance("MD5");
            } catch (NoSuchAlgorithmException nsaex) {
                nsaex.printStackTrace();
                in.close();
                ch.close();
                throw new JPMException("初始化失败，MessageDigest不支持MD5!");
            }
            if (step == 0) {
                MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0, file.length());
                messageDigest.update(byteBuffer);
                str = bufferToHex(messageDigest.digest());
                closeAll(in, ch, byteBuffer);
                return str;
            }
            for (int i = 0; i < step; i++) {
                MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, startPosition, maxSize);
                messageDigest.update(byteBuffer);
                startPosition += maxSize;
                bufferUnmap(byteBuffer);
            }
            if (startPosition == file.length()) {
                str = bufferToHex(messageDigest.digest());
                ch.close();
                in.close();
                return str;
            }
            MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, startPosition, file.length() - startPosition);
            messageDigest.update(byteBuffer);
            str = bufferToHex(messageDigest.digest());
            closeAll(in, ch, byteBuffer);
            return str;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private static String bufferToHex(byte bytes[]) {
        return bufferToHex(bytes, 0, bytes.length);
    }

    private static String bufferToHex(byte bytes[], int m, int n) {
        StringBuffer stringbuffer = new StringBuffer(2 * n);
        int k = m + n;
        for (int l = m; l < k; l++) {
            appendHexPair(bytes[l], stringbuffer);
        }
        return stringbuffer.toString();
    }

    private static void appendHexPair(byte bt, StringBuffer stringbuffer) {
        char c0 = hexDigits[(bt & 0xf0) >> 4];
        char c1 = hexDigits[bt & 0xf];
        stringbuffer.append(c0);
        stringbuffer.append(c1);
    }

    private static void closeAll(FileInputStream in, FileChannel ch, MappedByteBuffer buffer) throws IOException {
        bufferUnmap(buffer);
        ch.close();
        in.close();
    }

    /**
     * 释放MappedByteBuffer
     *
     * @param buffer
     */
    protected static void bufferUnmap(MappedByteBuffer buffer) {
        AccessController.doPrivileged(new PrivilegedAction() {
            public Object run() {
                try {
                    Method getCleanerMethod = buffer.getClass().getMethod("cleaner", new Class[0]);
                    getCleanerMethod.setAccessible(true);
                    sun.misc.Cleaner cleaner = (sun.misc.Cleaner)
                            getCleanerMethod.invoke(buffer, new Object[0]);
                    cleaner.clean();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }
}
