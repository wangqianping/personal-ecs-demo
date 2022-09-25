package com.sz.system.util;

import org.apache.tomcat.jni.Directory;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.stream.FileImageOutputStream;
import javax.imageio.stream.ImageOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class FileUtil {

    private static String UPLOAD_PATH_1 = "/Users/wangqianping/IdeaProjects/img";
    private static String UPLOAD_PATH_2 = "";

    public static String uploadFile(MultipartFile file) {
        if (file == null) {
            return null;
        }
        String fileName = file.getName()+System.currentTimeMillis();
        try {
            File directory = new File(UPLOAD_PATH_1);
            if(!directory.exists()){
                directory.mkdir();
            }
            //创建文件
            File image = new File(UPLOAD_PATH_1+"/"+fileName);
            file.transferTo(image);
            return image.getPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
