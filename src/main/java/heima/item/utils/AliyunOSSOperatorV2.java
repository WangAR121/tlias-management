package heima.item.utils;

import com.aliyun.sdk.service.oss2.OSSClient;
import com.aliyun.sdk.service.oss2.models.DeleteObjectRequest;
import com.aliyun.sdk.service.oss2.models.PutObjectRequest;
import com.aliyun.sdk.service.oss2.transport.BinaryData;
import com.aliyun.sdk.service.oss2.credentials.StaticCredentialsProvider;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class AliyunOSSOperatorV2 {

    @Autowired
    private AliyunOSSProperties prop;

    private OSSClient ossClient;

    // 初始化OSS客户端
    public void initClient() {
        StaticCredentialsProvider provider = new StaticCredentialsProvider(
                prop.getAccessKeyId(),
                prop.getAccessKeySecret()
        );
        this.ossClient = OSSClient.newBuilder()
                .region(prop.getRegion())
                .credentialsProvider(provider)
                .build();
    }

    //上传
    public String upload(byte[] content, String originalFilename) throws Exception {
        if(ossClient == null){
            initClient();
        }
        String dir = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM"));
        String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        String newFileName = UUID.randomUUID() + suffix;
        String objectName = dir + "/" + newFileName;

        PutObjectRequest putReq = PutObjectRequest.newBuilder()
                .bucket(prop.getBucketName())
                .key(objectName)
                .body(BinaryData.fromBytes(content))
                .build();
        ossClient.putObject(putReq);

        return "https://" + prop.getBucketName() + "." + prop.getEndpoint().split("//")[1]+"/"+objectName;
    }

    //删除文件
    public void delete(String objectName) throws Exception{
        if(ossClient == null){
            initClient();
        }
        ossClient.deleteObject(DeleteObjectRequest.newBuilder()
                .bucket(prop.getBucketName())
                .key(objectName)
                .build());
    }

    @PreDestroy
    public void closeClient() throws Exception {
        if(ossClient != null){
            ossClient.close();
        }
    }
}
