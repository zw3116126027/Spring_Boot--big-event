package org.example.controller;

import com.aliyuncs.exceptions.ClientException;
import org.example.pojo.Result;
import org.example.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * @ClassName FileUploadController
 * @Description TODO
 * @Author kli
 * @DATE 2024/9/16 15:01
 * @Version 1.0
 **/
@RestController
public class FileUploadController {
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException, ClientException {
        //把文件的内容存储到阿里oss
        String originalFilename = file.getOriginalFilename();
        //保证文件的名字是唯一的,从而防止文件覆盖  uuid生成名字+截取文件点(.)后面的文件后缀
        String fileName=UUID.randomUUID().toString()+originalFilename.substring(originalFilename.lastIndexOf("."));
        //把文件的内容存储到本地磁盘上
        //file.transferTo(new File("C:\\Users\\zw\\Desktop\\files\\"+fileName));
        String url = AliOssUtil.uploadFile(fileName, file.getInputStream());
        return Result.success(url);
    }
}
