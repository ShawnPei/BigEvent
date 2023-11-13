package org.shawn.controller;

import org.shawn.pojo.Result;
import org.shawn.utils.AliOssUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
public class FileUploadController {

    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) throws IOException {
        //1.把文件内容存储到本地磁盘
//        String originalFilename = file.getOriginalFilename();
//        String filename = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
//        file.transferTo(new File("E:\\SpringBoot3_Vue3\\backendcode\\files\\"+filename));
//        return Result.success("访问地址为：");//要给前端返回图片在服务器上的访问地址，现在是存储到本地磁盘，就暂时不填

        //2.存储到aliyun oss
        String originalFileName = file.getOriginalFilename();
        String filename = UUID.randomUUID().toString()+originalFileName.substring(originalFileName.lastIndexOf("."));
        String url = AliOssUtil.uploadFile(filename,file.getInputStream());
        return Result.success(url);
    }
}
