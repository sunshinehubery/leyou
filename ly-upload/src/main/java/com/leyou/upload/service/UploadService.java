package com.leyou.upload.service;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import com.leyou.common.enums.ExceptionEnum;
import com.leyou.common.exception.LyException;
import com.leyou.upload.config.UploadProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
* @author: furong
* @date: 2019/4/7
* @description:
*/
@Service
@Slf4j
@EnableConfigurationProperties(UploadProperties.class)
public class UploadService {
    //private static final List<String> ALLOW_TYPES = Arrays.asList("image/jpeg", "image/png", "image/bmp");
    @Autowired
    private FastFileStorageClient storageClient;
    @Autowired
    private UploadProperties uploadProperties;
    public String uploadImage(MultipartFile file) {
        try {
            String originalFilename = file.getOriginalFilename();
            //校验文件类型
            String contentType = file.getContentType();
            if(!uploadProperties.getAllowTypes().contains(contentType)){
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            //校验文件的内容
            BufferedImage image = ImageIO.read(file.getInputStream());
            if(image == null){
                throw new LyException(ExceptionEnum.INVALID_FILE_TYPE);
            }
            //上传到fastDFS
            String extension= StringUtils.substringAfterLast(file.getOriginalFilename(), ".");
            StorePath storePath = storageClient.uploadFile(file.getInputStream(), file.getSize(), extension, null);
            //返回路径
            return uploadProperties.getBaseUrl() + storePath.getFullPath();
            /*file.transferTo(new File("D:\\leyou\\image" + originalFilename));
            return "http://image.leyou.com" + originalFilename;*/
        } catch (IOException e) {
            //上传失败
            log.error("[文件上传] 上传文件失败!", e);
            throw new LyException(ExceptionEnum.UPLOAD_FILE_ERROR);
        }
    }
}
