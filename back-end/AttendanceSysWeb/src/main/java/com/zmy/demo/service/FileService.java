package com.zmy.demo.service;

import java.io.File;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.zmy.demo.util.FileUtil;

/**
 * 文件管理
 */
@Service public class FileService {
    // 文件处理工具
    private FileUtil fileUtil = new FileUtil();
    // 用户证明材料存放位置的根目录
    private static String credentialRootName = "src/main/resources/credential/";

    // 上传证明材料
    public void upload(String userId, MultipartFile file) {
        // 保存文件的目录
        File dir = new File(credentialRootName, userId);

        // 保证目录存在且为空目录
        if (dir.exists()) {
            // 如果目录存在则清空其下所有文件
            for (File tempFile: dir.listFiles()) {
                tempFile.delete();
            }
        } else {
            // 如果目录不存在则创建目录
            dir.mkdir();
        }

        // 保存文件file
        fileUtil.saveFile(file, dir);
    }

    // 查看证明材料
    public ResponseEntity<byte[]> download(String userId) {
        // 用户证明材料保存的目录及证明材料文件名
        File dir = new File(credentialRootName, userId);
        String filename = (dir.list())[0];    // 每个用户只能上传一张证明材料

        return fileUtil.sendFile(filename, dir);
    }

}
