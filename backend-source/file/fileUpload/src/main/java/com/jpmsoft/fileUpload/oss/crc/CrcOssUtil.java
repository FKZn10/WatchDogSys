package com.jpmsoft.fileUpload.oss.crc;

import com.hitachivantara.common.ex.HSCException;
import com.hitachivantara.core.http.Protocol;
import com.hitachivantara.core.http.client.ClientConfiguration;
import com.hitachivantara.hcp.build.HCPClientBuilder;
import com.hitachivantara.hcp.build.HCPNamespaceClientBuilder;
import com.hitachivantara.hcp.common.auth.LocalCredentials;
import com.hitachivantara.hcp.standard.api.HCPNamespace;
import com.hitachivantara.hcp.standard.api.MultipartUpload;
import com.hitachivantara.hcp.standard.api.event.PartialHandlingListener;
import com.hitachivantara.hcp.standard.define.ACLDefines;
import com.hitachivantara.hcp.standard.internal.FileWriteHandler;
import com.hitachivantara.hcp.standard.model.HCPObject;
import com.hitachivantara.hcp.standard.model.PartETag;
import com.hitachivantara.hcp.standard.model.metadata.AccessControlList;
import com.hitachivantara.hcp.standard.model.metadata.PermissionGrant;
import com.hitachivantara.hcp.standard.model.request.impl.DeleteDirectoryRequest;
import com.hitachivantara.hcp.standard.model.request.impl.MultipartDownloadRequest;
import com.hitachivantara.hcp.standard.model.request.impl.MultipartUploadRequest;
import com.jpmsoft.fileUpload.constants.FileMsg;
import com.jpmsoft.fileUpload.pojo.dto.OssClientParamsDto;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;
import java.util.concurrent.CountDownLatch;

/**
 * oss对象存储
 * 1.oss key为文件在OSS内的绝对路径
 * 2.本地记录filePath等于OSS的Key（本地文件与OSS内文件仅根目录可能不一样）
 * 3.文件仅在保存表单刷新文件上传状态时上传OSS 普通上传不保存表单仅本地存储
 *
 * @Author: 李文涛 aaron
 * @date: 2022/12/16 17:36
 */
@Slf4j
public class CrcOssUtil {

    private static HCPNamespace hcpClient = null;


    /**
     * 创建对象存储客户端
     *
     * @return hcpClient
     */
    public static HCPNamespace getClient(OssClientParamsDto ossParams) {
        if (ossParams == null || !ossParams.isEnableOss()) return null;
        if (hcpClient != null) return hcpClient;
        ClientConfiguration clientConfig = new ClientConfiguration();
        clientConfig.setSupportedProtocols(new String[]{"TLSv1.2", "TLSv1.3"});
        clientConfig.setProtocol(Protocol.HTTPS);
        HCPNamespaceClientBuilder builder = HCPClientBuilder.defaultHCPClient();
        try {
            hcpClient = builder.withClientConfiguration(clientConfig)
                    .withCredentials(new LocalCredentials(ossParams.getAccessKey(), ossParams.getSecretKey()))
                    .withEndpoint(ossParams.getEndpoint())
                    .withNamespace(ossParams.getNamespace())
                    .bulid();
        } catch (HSCException e) {
            log.error("创建对象存储客户端失败!" + e.getMessage(), e);
            e.printStackTrace();
        }
        return hcpClient;
    }

    /**
     * 判断文件是否存在
     *
     * @param ossParams
     * @param key
     * @return
     */
    public static Boolean fileExist(OssClientParamsDto ossParams, String key) {
        if (ossParams == null) return false;
        try {
            HCPNamespace hcpClient = getClient(ossParams);
            if (hcpClient == null) return false;
            else return hcpClient.doesObjectExist(key);
        } catch (Exception ex) {
            ex.printStackTrace();
            log.error("OSS校验文件是否存在失败！" + ex.getMessage(), ex);
            return false;
        }
    }


    /**
     * oss附件上传
     *
     * @param ossParams 配置信息
     * @param from      文件绝对路径
     * @param to        桶内目录
     * @return oss key
     */
    public static String upload(OssClientParamsDto ossParams, String from, String to) {
        if (ossParams == null) return null;
        HCPNamespace hcpClient = getClient(ossParams);
        if (hcpClient == null) return null;
        try {
            from = removeXg(from);
            to = removeXg(to);
            createDir(hcpClient, to);
            File file = new File(from);
            if (file.exists() && file.isFile()) {
                long size = file.length();
                if (size / (1024 * 1024 * 1024) >= 5) return multipartUpload(hcpClient, from, to);
                else return uploadFile(hcpClient, from, to);
            } else {
                log.error(FileMsg.noneFile);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
            log.error(FileMsg.ossUploadError, e);
            return null;
        }
    }

    /**
     * 删除文件后重新上传
     *
     * @param ossParams 配置信息
     * @param from
     * @param to
     * @return
     * @throws HSCException
     */
    public static String del2Upload(OssClientParamsDto ossParams, String from, String to) throws HSCException {
        HCPNamespace hcpClient = getClient(ossParams);
        if (hcpClient == null) return null;
        File file = new File(from);
        String key = to + file.getName();
        if (hcpClient.doesObjectExist(key)) hcpClient.deleteObject(key);
        return upload(ossParams, from, to);
    }

    /**
     * 上传文件方法
     *
     * @param hcpClient
     * @param fileAbsolutePath 需要上传文件的绝对路径
     * @param directoryPath    需要上传到桶中的目录
     * @return key 文件key（唯一标识）
     */
    public static String uploadFile(HCPNamespace hcpClient, String fileAbsolutePath, String directoryPath) throws HSCException {
        log.info("文件上传OSS—>Method:uploadFile");
        File file = new File(fileAbsolutePath);
        String key = directoryPath + file.getName();
        log.info("上传OSS->key:" + key);
        if (!hcpClient.doesObjectExist(key)) {
            try {
                hcpClient.putObject(key, file);
                log.info("上传完成!");
                return key;
            } catch (HSCException e) {
                log.error("oss上传失败" + e.getMessage(), e);
                e.printStackTrace();
            }
        } else {
            log.info("OSS文件已存在!");
            return key;
        }
        return null;
    }

    /**
     * 移除路径双斜杠
     *
     * @param path
     * @return
     */
    private static String removeXg(String path) {
        if (path.contains("//")) {
            path = path.replaceAll("//", "/");
            removeXg(path);
        }
        return path;
    }


    /**
     * 下载文件
     * 有限判断本地文件是否存在，不存在则尝试从oss拉取文件
     *
     * @param ossParams    配置信息
     * @param path         桶内路径（无根目录前缀,带文件名的路径）
     * @param local        保存到本地的根目录
     * @param fileLocation 文件位置 0普通root根目录 1 归档根目录
     */
    public static void downLocalNotExistFile(OssClientParamsDto ossParams, String path, String local, int fileLocation) {
        if (ossParams == null) return;
        local = local.endsWith("/") ? local : local + "/";
        File file = new File(local + path);
        if (!file.exists()) {
            HCPNamespace hcpClient = getClient(ossParams);
            if (hcpClient == null) return;
            String key;
            if (fileLocation == 0) key = ossParams.getOssRootDir() + path;
            else key = ossParams.getOssArchiveDir() + path;
            if (fileExist(ossParams, key)) download(ossParams, path, local, fileLocation);
        }
    }

    /**
     * 文件下载
     * 本地和OSS文件存储路径一致（仅根目录可能不同）
     *
     * @param ossParams    配置信息
     * @param path         桶内路径（无根目录前缀,带文件名的路径）
     * @param local        保存到本地的根目录
     * @param fileLocation 文件位置 0普通root根目录 1 归档根目录
     * @throws IOException
     */
    public static void download(OssClientParamsDto ossParams, String path, String local, int fileLocation) {
        HCPNamespace hcpClient = getClient(ossParams);
        if (hcpClient == null) return;
        String key;
        String downLoadPath;
        String dir = path.substring(0, path.lastIndexOf("/") + 1);
        downLoadPath = local + dir;
        if (fileLocation == 0) key = ossParams.getOssRootDir() + path;
        else key = ossParams.getOssArchiveDir() + path;
        InputStream in = null;
        FileOutputStream out = null;
        try {
            if (hcpClient.doesObjectExist(key)) {
                HCPObject hcpObject = hcpClient.getObject(key);
                in = hcpObject.getContent();
                out = new FileOutputStream(downLoadPath + hcpObject.getName());
                byte bytes[] = new byte[1024];
                int temp = 0;
                while ((temp = in.read(bytes)) != -1) {
                    out.write(bytes, 0, temp);
                }
                log.info("OSS下载完成");
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("OSS下载失败 ：" + e.getMessage(), e);
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 创建目录
     *
     * @param hcpClient    对象存储客户端 默认，不需要修改
     * @param directoryKey 需要创建的目录名称
     */
    public static void createDir(HCPNamespace hcpClient, String directoryKey) throws Exception {
        if (hcpClient == null) return;
        boolean exist = hcpClient.doesDirectoryExist(directoryKey);
        if (exist) {
            log.error("目录已存在！");
        } else hcpClient.createDirectory(directoryKey);
    }


    /**
     * 创建目录
     *
     * @param ossParams    配置信息
     * @param directoryKey
     */
    public static void deleteDir(OssClientParamsDto ossParams, String directoryKey) throws Exception {
        HCPNamespace hcpClient = getClient(ossParams);
        if (hcpClient == null) return;
        boolean exist = hcpClient.doesDirectoryExist(directoryKey);
        if (exist) {
            log.info("尝试删除该目录下的文件和目录！");
            hcpClient.deleteDirectory(new DeleteDirectoryRequest(directoryKey).withDeleteContainedObjects(true));
            log.info("删除成功！");
        } else log.error("目录不存在！");
    }


    /**
     * 删除文件
     *
     * @param ossParams 配置信息
     * @param key       需要删除的文件的文件名
     */
    public static void deleteFile(OssClientParamsDto ossParams, String key) {
        if (ossParams == null) return;
        HCPNamespace hcpClient = getClient(ossParams);
        if (hcpClient == null) return;
        try {
            hcpClient.deleteObject(key);
            log.info("oss删除文件成功!");
        } catch (Exception e) {
            e.printStackTrace();
            log.error("oss删除文件失败！" + e.getMessage(), e);
        }
    }


    /**
     * 大文件分片上传（大文件至少500MB以上，小文件不建议使用分片方式上传）
     *
     * @param hcpClient
     * @param fileAbsolutePath         文件绝对路径
     * @param destinationDirectoryPath 需要上传到桶中的目录
     * @return key
     */
    public static String multipartUpload(HCPNamespace hcpClient, String fileAbsolutePath, String destinationDirectoryPath) throws Exception {
        // 测试用大文件（**分片上传文件应为大文件至少500MB以上，小文件不建议使用分片方式上传**）
        log.info("大文件分片上传OSS—>Method:multipartUpload");
        final File file = new File(fileAbsolutePath);
        // 上传key
        final String key = destinationDirectoryPath + file.getName();
        log.info("上传OSS->key:" + key);
        if (hcpClient.doesObjectExist(key)) {
            hcpClient.deleteObject(key);
        }
        String uploadId = null;
        // 获得分片上传实例
        MultipartUploadRequest request = new MultipartUploadRequest(key);
        final MultipartUpload api = hcpClient.getMultipartUpload(request);
        // 初始化分片上传
        uploadId = api.initiate();
        final List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());
        final long length = file.length();
        // 指定分片大小此处为100MB，分片上传单个分片最小5MB，建议100MB，如果文件小于5GB不建议使用分片上传
        final long partLength = 1024 * 1024 * 100; // Min 5M
        long remainLength = length;
        long startOffset = 0;
        int index = 1;
        final Queue<long[]> parts = new LinkedList<long[]>();
        while (remainLength > 0) {
            long uploadLength = Math.min(remainLength, partLength);
            parts.add(new long[]{index++, startOffset, uploadLength});
            startOffset += (uploadLength + 0);
            remainLength -= uploadLength;
        }
        final int partsSize = parts.size();

        final CountDownLatch latch = new CountDownLatch(partsSize);

        for (int i = 0; i < partsSize; i++) {
            final int id = i;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    InputStream in = null;
                    try {
                        long[] part = null;
                        synchronized (parts) {
                            part = parts.poll();
                        }

                        if (part != null) {
                            in = new FileInputStream(file);
                            in.skip(part[1]);

                            log.info("Uploading Part... " + id);
                            PartETag etag = api.uploadPart((int) part[0], in, part[2]).getPartETag();
                            log.info("etag=" + etag.getPartNumber() + " " + etag.getETag());
                            partETags.add(etag);
                            log.info("Part... " + id + " Done");
                        } else {
                            log.error("null");
                        }
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        e.printStackTrace();
                    } finally {
                        if (in != null) {
                            try {
                                in.close();
                            } catch (IOException e) {
                            }
                        }
                    }

                    latch.countDown();
                }
            }// .run();
            ).start();
        }
        // 等待上传结束
        latch.await();
        // 合并分片完成分片上传
        api.complete(partETags);
        return key;
    }


    /**
     * 分片下载大文件
     *
     * @param hcpClient
     * @param key                     eg: a.txt
     * @param destinationDownLoadPath 文件下载保存目录
     */
    public static void multipartDownload(HCPNamespace hcpClient, String key, String destinationDownLoadPath) throws Exception {
        HCPObject hcpObject = hcpClient.getObject(key);
        File file = new File(destinationDownLoadPath + hcpObject.getName());
        if (file.exists()) {
            file.delete();
        }

        final CountDownLatch latch = new CountDownLatch(2);
        final long b = Calendar.getInstance().getTimeInMillis();
        MultipartDownloadRequest request = new MultipartDownloadRequest(key)
                // 配置启用分片下载最小的文件大小，此处设置100MB，也就是说需要下载的文件>=100MB才启用分片下载否则直接下载。
                // 此参数最小50MB
                .withMinimumObjectSize(1024 * 1024 * 100)
                // 指定分片数量
                .withParts(4)
                // 是否等待结束
                .withWaitForComplete(false);

        FileWriteHandler handler = new FileWriteHandler(file);
        // 是否覆盖本地即存文件
        handler.setOverrideLocalFile(true);
        // 对于分片上传的文件无法验证内容，请设置为false
        handler.setVerifyContent(false);
        // 配置下载监听
        handler.setListener(new PartialHandlingListener() {
            double size = 0;

            @Override
            public void catchException(HSCException e) {
                e.printStackTrace();
            }

            @Override
            public void completed() {
                long e = Calendar.getInstance().getTimeInMillis();
                double time = e - b;
                double mbs = (size / 1024 / 1024) / (time / 1000);
                latch.countDown();
            }

            @Override
            public void partCompleted(int partNumber, long beginOffset, long length) {
                size += length;
            }

            @Override
            public void outProgress(int id, long seekOffset, long length) {
            }
        });
        // 执行下载
        hcpClient.getObject(request, handler);
        // EXEC TEST FUNCTION ---------------------------------------------------------------------
        latch.countDown();
        // 等待结束
        latch.await();
    }


    /**
     * select ACLs of file
     *
     * @param hcpClient
     * @param filekey   需要查看文件的acl
     * @return key 文件key（唯一标识）
     */
    public static Collection<PermissionGrant> selectFileACL(HCPNamespace hcpClient, String filekey) {
        AccessControlList objectACL = null;
        try {
            objectACL = hcpClient.getObjectACL(filekey);
            Collection<PermissionGrant> allPermissions = objectACL.getAllPermissions();
            return allPermissions;
        } catch (HSCException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * set fileACL
     *
     * @param hcpClient
     * @param filekey
     * @param username
     * @param permissions
     * @throws HSCException
     */
    public static void setFileACL(HCPNamespace hcpClient, String filekey, String username, ACLDefines.ACLPermission... permissions) {

        AccessControlList objectACL = null;
        try {
            // clear the ACL of file
            boolean exist = hcpClient.doesObjectACLExist(filekey);
            if (exist) {
                hcpClient.deleteObjectACL(filekey);
            }
            // set acl
            AccessControlList acl = new AccessControlList();
            acl.grantPermissionsToUser(username, permissions);
            hcpClient.addObjectACL(filekey, acl);
        } catch (HSCException e) {
            e.printStackTrace();
        }
    }
}
