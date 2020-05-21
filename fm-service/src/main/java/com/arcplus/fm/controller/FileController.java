package com.arcplus.fm.controller;

import com.arcplus.fm.entity.FileInfo;
import com.arcplus.fm.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    /**
     * 文件上传<br>
     * 根据fileSource选择上传方式，目前仅实现了上传到本地<br>
     * 如有需要可上传到第三方，如阿里云、七牛等
     *
     * @param file
     * @param fileSource FileSource
     * @return
     * @throws Exception
     */
    @PostMapping
    public FileInfo upload(@RequestParam("file") MultipartFile file, String fileSource) throws Exception {
        return fileService.upload(file);
    }

}