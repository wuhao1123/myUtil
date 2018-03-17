package com.hao.util;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.hao.util.base.CommonValidate;
import com.hao.util.base.DateUtil;
import com.hao.util.base.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.Date;

/**
 * @ClassName AliOSSUtil.java
 * @Description 功能描述： 阿里云OSS文件上传
 * @author 吴昊 2018年3月6日20:01:21
 */
public class AliOSSUtil {

    private static final Logger log           = LoggerFactory.getLogger(AliOSSUtil.class);
    private static String       END_POINT     = "oss-cn-shanghai.aliyuncs.com";
    private static String       BUCKET_NAME   = "hao-image";
    // 文件访问域名
    private static String       FILE_HOST     = "https://image.bianxianmao.com/";
    // tinify文件压缩申请的key
    private static String[]     tinifyApiKeys = { "H3e0yJpIBkHrLrFVsPGmVDuuLq8WSFaf",
                                                  "2lku16Gahn1ASIUr02kPEYb0GJJC_lng",
                                                  "2fKMylTj9-EWJbytvskmZjRptTaelqXl",
                                                  "QoWNCAkiMTel_u4i3r0Tw89ehJ5EDdhV" };

    /**
     * 方法描述:上传文件
     * 
     * @author 吴昊 2018年3月6日20:01:31
     * @param file 文件对象
     * @return
     */
    public static String upload(String accessKeyId, String secret, File file) {
        if (file == null) {
            return null;
        }
        // 创建OSS客户端
        OSSClient ossClient = new OSSClient(END_POINT, accessKeyId, secret);
        try {
            // 判断文件容器是否存在，不存在则创建
            if (!ossClient.doesBucketExist(BUCKET_NAME)) {
                ossClient.createBucket(BUCKET_NAME);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(BUCKET_NAME);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            // 创建文件路径
            String fileName = file.getName();
            String suffix = "";
            if (StringUtil.isNotBlank(fileName) && fileName.indexOf(".") != -1) {
                suffix = fileName.substring(fileName.lastIndexOf("."));
            }
            final String filePath = DateUtil.dateTo8String2(new Date()) + "/" + UuidUtil.getUuidByJdk(true) + suffix;
            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(BUCKET_NAME, filePath, file));
            if (null != result) {
                final String fileUrl = FILE_HOST + filePath;
                // 如果是jpg或者png图片，则启动一个线程压缩图片
                if (StringUtil.equals(suffix, ".jpg") || StringUtil.equals(suffix, ".png")) {
                    // 启动线程压缩
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                byte[] bytes = ImageUtil.compressionFromUrl(tinifyApiKeys[RandomUtil.getRandomNum(tinifyApiKeys.length)],
                                                                            fileUrl);
                                replaceUpload(accessKeyId, BUCKET_NAME, secret, bytes, filePath);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }

                return fileUrl;
            }

        } catch (OSSException oe) {
            log.error(oe.getMessage());
        } catch (ClientException ce) {
            log.error(ce.getMessage());
        } catch (Exception ce) {
            log.error(ce.getMessage());
        } finally {
            // 关闭OSS服务，一定要关闭
            ossClient.shutdown();
        }
        return null;
    }

    /**
     * 方法描述:上传文件
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param inputStream 文件流
     * @return
     * @throws Exception
     */
    public static String upload(String accessKeyId, String secret, InputStream inputStream) throws Exception {
        if (inputStream == null) {
            return null;
        }
        // 创建OSS客户端
        OSSClient ossClient = new OSSClient(END_POINT, accessKeyId, secret);
        try {
            // 判断文件容器是否存在，不存在则创建
            if (!ossClient.doesBucketExist(BUCKET_NAME)) {
                ossClient.createBucket(BUCKET_NAME);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(BUCKET_NAME);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }

            String fileType = FileTypeJudge.getTypeByInputStream(inputStream).getCode();
            String suffix = "";
            if (StringUtil.isNotBlank(fileType)) {
                suffix = "." + fileType;
            }
            // 创建文件路径

            final String filePath = DateUtil.dateTo8String2(new Date()) + "/" + UuidUtil.getUuidByJdk(true) + suffix;
            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(BUCKET_NAME, filePath, inputStream));
            if (null != result) {
                final String fileUrl = FILE_HOST + filePath;
                // 如果是jpg或者png图片，则启动一个线程压缩图片
                if (StringUtil.equals(fileType, FileType.JPG.getCode())
                    || StringUtil.equals(fileType, FileType.PNG.getCode())) {
                    // 启动线程压缩
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                byte[] bytes = ImageUtil.compressionFromUrl(tinifyApiKeys[RandomUtil.getRandomNum(tinifyApiKeys.length)],
                                                                            fileUrl);
                                replaceUpload(accessKeyId, BUCKET_NAME, secret, bytes, filePath);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }

                return fileUrl;
            }
        } catch (OSSException oe) {
            log.error(oe.getMessage());
        } catch (ClientException ce) {
            log.error(ce.getMessage());
        } finally {
            // 关闭OSS服务，一定要关闭
            ossClient.shutdown();
        }
        return null;

    }

    /**
     * @Description 方法描述：
     * @author 吴昊 2018年3月6日20:02:38
     * @param file 文件对象
     * @param bucketName OSS节点名，非空，如bxm-image，hao-guide
     * @param fileHost:非空，如https://image.bianxianmao.com/
     * @param filePath:文件保存路径，可以为空，默认以创建日期为目录，如：2017/08/29/
     * @return
     */
    public static String upload(String accessKeyId, String secret, File file, String bucketName, String fileHost,
                                String filePath) {
        CommonValidate.ME.checkParam(file);
        CommonValidate.ME.checkParam(bucketName);
        CommonValidate.ME.checkParam(fileHost);
        // 创建OSS客户端
        OSSClient ossClient = new OSSClient(END_POINT, accessKeyId, secret);
        try {
            // 判断文件容器是否存在，不存在则创建
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            // 创建文件路径
            String fileName = file.getName();
            String suffix = "";
            if (StringUtil.isNotBlank(fileName) && fileName.indexOf(".") != -1) {
                suffix = fileName.substring(fileName.lastIndexOf("."));
            }
            if (StringUtil.isBlank(filePath)) {
                filePath = DateUtil.dateTo8String2(new Date()) + "/" + UuidUtil.getUuidByJdk(true) + suffix;
            }
            final String uploadfilePath = filePath;
            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, uploadfilePath, file));
            if (null != result) {
                final String fileUrl = fileHost + uploadfilePath;
                // 如果是jpg或者png图片，则启动一个线程压缩图片
                if (StringUtil.equals(suffix, ".jpg") || StringUtil.equals(suffix, ".png")) {
                    // 启动线程压缩
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                byte[] bytes = ImageUtil.compressionFromUrl(tinifyApiKeys[RandomUtil.getRandomNum(tinifyApiKeys.length)],
                                                                            fileUrl);
                                replaceUpload(accessKeyId, bucketName, secret, bytes, uploadfilePath);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }

                return fileUrl;
            }

        } catch (OSSException oe) {
            log.error(oe.getMessage());
        } catch (ClientException ce) {
            log.error(ce.getMessage());
        } catch (Exception ce) {
            log.error(ce.getMessage());
        } finally {
            // 关闭OSS服务，一定要关闭
            ossClient.shutdown();
        }
        return null;
    }

    /**
     * @Description 方法描述：上传文件，自定义bucketName，fileHost，filePath
     * @author 吴昊 2018年3月6日20:02:38
     * @param inputStream
     * @param bucketName OSS节点名，非空，如bxm-image，hao-guide
     * @param fileHost:非空，如https://image.bianxianmao.com/
     * @param filePath:文件保存路径，可以为空，默认以创建日期为目录，如：2017/08/29/
     * @return
     * @throws Exception
     */
    public static String upload(String accessKeyId, String secret, InputStream inputStream, String bucketName,
                                String fileHost, String filePath) throws Exception {
        CommonValidate.ME.checkParam(inputStream);
        CommonValidate.ME.checkParam(bucketName);
        CommonValidate.ME.checkParam(fileHost);
        // 创建OSS客户端
        OSSClient ossClient = new OSSClient(END_POINT, accessKeyId, secret);
        try {
            // 判断文件容器是否存在，不存在则创建
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }

            String fileType = FileTypeJudge.getTypeByInputStream(inputStream).getCode();
            String suffix = "";
            if (StringUtil.isNotBlank(fileType)) {
                suffix = "." + fileType;
            }
            // 创建文件路径
            if (StringUtil.isBlank(filePath)) {
                filePath = DateUtil.dateTo8String2(new Date()) + "/" + UuidUtil.getUuidByJdk(true) + suffix;
            }
            final String uploadfilePath = filePath;

            // 上传文件
            PutObjectResult result = ossClient.putObject(new PutObjectRequest(bucketName, uploadfilePath, inputStream));
            if (null != result) {
                final String fileUrl = fileHost + uploadfilePath;
                // 如果是jpg或者png图片，则启动一个线程压缩图片
                if (StringUtil.equals(fileType, FileType.JPG.getCode())
                    || StringUtil.equals(fileType, FileType.PNG.getCode())) {
                    // 启动线程压缩
                    new Thread(new Runnable() {

                        @Override
                        public void run() {
                            try {
                                byte[] bytes = ImageUtil.compressionFromUrl(tinifyApiKeys[RandomUtil.getRandomNum(tinifyApiKeys.length)],
                                                                            fileUrl);
                                replaceUpload(accessKeyId, bucketName, secret, bytes, uploadfilePath);
                            } catch (IOException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }

                        }
                    }).start();
                }

                return fileUrl;
            }
        } catch (OSSException oe) {
            log.error(oe.getMessage());
        } catch (ClientException ce) {
            log.error(ce.getMessage());
        } finally {
            // 关闭OSS服务，一定要关闭
            ossClient.shutdown();
        }
        return null;

    }

    /**
     * 方法描述:覆盖阿里云OSS路径filePath下的文件
     * 
     * @author 吴昊 2018年3月6日20:02:38
     * @param bytes
     * @param filePath
     * @return
     * @throws FileNotFoundException
     */
    private static void replaceUpload(String accessKeyId, String bucketName, String secret, byte[] bytes,
                                      String filePath) throws FileNotFoundException {
        String[] filePaths = filePath.split("/");
        String fileName = filePaths[filePaths.length - 1];
        // 创建OSS客户端
        OSSClient ossClient = new OSSClient(END_POINT, accessKeyId, secret);
        try {
            File newFile = new File(fileName);
            FileOutputStream outStream = new FileOutputStream(newFile); // 文件输出流用于将数据写入文件
            outStream.write(bytes);
            outStream.close(); // 关闭文件输出流
            // 判断文件容器是否存在，不存在则创建
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }
            // 上传覆盖原有文件
            ossClient.putObject(new PutObjectRequest(bucketName, filePath, newFile));
            newFile.delete();
        } catch (OSSException oe) {
            log.error(oe.getMessage());
        } catch (ClientException ce) {
            log.error(ce.getMessage());
        } catch (Exception ce) {
            log.error(ce.getMessage());
        } finally {
            // 关闭OSS服务，一定要关闭
            ossClient.shutdown();
        }

    }

}
