package s3;


import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.S3Actions;
import com.amazonaws.auth.policy.resources.S3ObjectResource;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * S3 处理类
 *
 * @author wangqianping
 * @version 1.0
 */
public class S3Tool {

    /**
     * 创建一个桶
     *
     * @param bucketName 存储桶
     */
    public void initBucket(String bucketName) {

        final AmazonS3 s3 = AmazonS3ClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        Statement allowPublicReadStatement = new Statement(Statement.Effect.Allow)
                .withPrincipals(Principal.AllUsers)
                .withActions(S3Actions.GetObject)
                .withResources(new S3ObjectResource(bucketName, "*"));

        Policy policy = new Policy()
                .withStatements(allowPublicReadStatement);

        s3.setBucketPolicy(bucketName, policy.toJson());
    }

    /**
     * 上传文件
     *
     * @param inputStream 输入流
     * @param bucketName  第一级 bucket名称
     * @param destDir     上传目录  可以多级 例如 logger/123456
     * @param fileName    文件名称 唯一
     * @throws Exception 异常
     */
    public void uploadFile(InputStream inputStream, String bucketName, String destDir, String fileName) throws Exception {
        if (inputStream == null) {
            throw new Exception("inputStream must not be null ");
        }

        if (fileName == null || "".equals(fileName)) {
            throw new Exception("fileName must not be null or empty");
        }

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_WEST_2).build();

        String bucket = bucketName + (destDir == null ? "" : (File.separator + destDir));

        ObjectMetadata metadata = new ObjectMetadata();

        String contentType;
        try {
            switch (fileName.substring(fileName.lastIndexOf("."))) {
                case ".html":
                    contentType = "text/html";
                    break;
                case ".xml":
                    contentType = "text/xml";
                    break;
                case ".pdf":
                    contentType = "application/pdf";
                    break;
                case ".ico":
                    contentType = "image/x-icon";
                    break;
                case ".png":
                    contentType = "image/png";
                    break;
                case ".jpg":
                    contentType = "image/jpeg";
                    break;
                case ".gif":
                    contentType = "image/gif";
                    break;
                case ".jpeg":
                    contentType = "image/jpeg";
                    break;
                case ".mp4":
                    contentType = "video/mpeg4";
                    break;
                case ".avi":
                    contentType = "video/avi";
                    break;
                default:
                    contentType = "text/plain";
            }

            metadata.setContentType(contentType);

        } catch (Exception e) {

            metadata.setContentType("plain/text");
        }

        metadata.addUserMetadata("title", "someTitle");
        metadata.setHeader("bin", "application/octet-stream");
        metadata.setHeader("bz2", "application/x-bzip2");

        PutObjectRequest request = new PutObjectRequest(bucket.replaceAll("\\\\", "/"), fileName, inputStream, metadata);
        s3Client.putObject(request);
        s3Client.shutdown();
        inputStream.close();
    }

    /**
     * 下载文件 至本地
     *
     * @param bucketName 第一级 bucket名称
     * @param sourceDir  文件所在目录
     * @param fileName   文件名
     * @param destDir    下载目录
     * @throws Exception 异常
     */
    public void downloadFile(String bucketName, String sourceDir, String fileName, String destDir) throws Exception {
        if (fileName == null || "".equals(fileName)) {
            throw new Exception("fileName must not be null or empty");
        }
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
        String bucket = bucketName + (sourceDir == null ? "" : (File.separator + sourceDir));

        S3Object s3obj = s3Client.getObject(bucket.replaceAll("\\\\", "/"), fileName);

        S3ObjectInputStream s3is = s3obj.getObjectContent();
        FileOutputStream fos = new FileOutputStream(new File(destDir + File.separator + fileName));
        byte[] read_buf = new byte[1024 * 1024];
        int read_len = 0;
        while ((read_len = s3is.read(read_buf)) > 0) {
            fos.write(read_buf, 0, read_len);
        }
        s3is.close();
        fos.flush();
        fos.close();
        if (s3Client != null) {
            s3Client.shutdown();
        }
    }

    /**
     * 下载文件 返回流
     *
     * @param bucketName 第一级 bucket名称
     * @param sourceDir  文件所在目录
     * @param fileName   文件名
     * @throws Exception 异常
     */
    public void downloadFile(String bucketName, String sourceDir, String fileName, HttpServletResponse response) throws Exception {
        if (fileName == null || "".equals(fileName)) {
            throw new Exception("fileName must not be null or empty");
        }

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
        bucketName = bucketName + (sourceDir == null ? "" : (File.separator + sourceDir));

        S3Object s3obj = s3Client.getObject(bucketName.replaceAll("\\\\", "/"), fileName);

        S3ObjectInputStream s3Input = s3obj.getObjectContent();

        // 先去掉文件名称中的空格,然后转换编码格式为utf-8,保证不出现乱码,这个文件名称用于浏览器的下载框中自动显示的文件名
        response.addHeader(
                "Content-Disposition",
                "attachment;filename=" + new String(fileName.getBytes("utf-8"), "iso8859-1"));
        response.addHeader("Content-Length", "" + s3obj.getObjectMetadata().getContentLength());
        response.setContentType(s3obj.getObjectMetadata().getContentType());

        OutputStream os = new BufferedOutputStream(response.getOutputStream());
        byte[] buffer = new byte[1024 * 1024];
        int length = -1;
        while ((length = s3Input.read(buffer)) != -1) {
            os.write(buffer, 0, length);// 输出文件
        }
        os.flush();
        s3obj.close();
        os.close();
        if (s3Input != null) {
            s3Input.close();
        }
        if (s3Client != null) {
            s3Client.shutdown();
        }
    }

    public void deleteFile(String bucketName, String sourceDir, String fileName) throws Exception {
        if (fileName == null || "".equals(fileName)) {
            throw new Exception("fileName must not be null or empty");
        }

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(Regions.US_WEST_2).build();
        bucketName = bucketName + (sourceDir == null ? "" : (File.separator + sourceDir));
        s3Client.deleteObject(bucketName.replaceAll("\\\\", "/"), fileName);
        if (s3Client != null) {
            s3Client.shutdown();
        }
    }
}
