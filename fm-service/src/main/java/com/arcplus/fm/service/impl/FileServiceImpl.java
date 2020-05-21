package com.arcplus.fm.service.impl;

import com.arcplus.fm.entity.FileInfo;
import com.arcplus.fm.mapper.FileMapper;
import com.arcplus.fm.util.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;


@Service("localFileServiceImpl")
public class FileServiceImpl extends AbstractFileService {

    @Autowired
    private FileMapper fileDao;

    @Override
    protected FileMapper getFileDao() {
        return fileDao;
    }

    @Value("${file.local.urlPrefix}")
    private String urlPrefix;
    /**
     * 上传文件存储在本地的根路径
     */
    @Value("${file.local.path}")
    private String localFilePath;


    @Override
    protected void uploadFile(MultipartFile file, FileInfo fileInfo) throws Exception {
        int index = fileInfo.getName().lastIndexOf(".");
        // 文件扩展名
        String fileSuffix = fileInfo.getName().substring(index);

        String suffix = "/" + LocalDate.now().toString().replace("-", "/") + "/" + fileInfo.getId() + fileSuffix;

        String path = localFilePath + suffix;
        String url = urlPrefix + suffix;
        fileInfo.setPath(path);
        fileInfo.setUrl(url);

        FileUtil.saveFile(file, path);
    }

    @Override
    protected boolean deleteFile(FileInfo fileInfo) {
        return FileUtil.deleteFile(fileInfo.getPath());
    }
}

