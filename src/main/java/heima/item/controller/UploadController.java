package heima.item.controller;

import heima.item.pojo.Result;
import heima.item.utils.AliyunOSSOperatorV2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
public class UploadController {
    @Autowired
    private AliyunOSSOperatorV2 aliyunOSSOperatorV2;
    @PostMapping("/upload")
    public Result upload(MultipartFile file)throws Exception{
        log.info("文件上传{}",file.getOriginalFilename());
        String url= aliyunOSSOperatorV2.upload(file.getBytes(), file.getOriginalFilename());
        log.info("文件上传OSS，url：{}",url);
        return Result.success(url);
    }
}
