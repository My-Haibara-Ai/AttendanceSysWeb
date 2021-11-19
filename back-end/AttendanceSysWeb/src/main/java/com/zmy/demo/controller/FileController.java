package com.zmy.demo.controller;

// RestController、RequestMapping、RequestParam、RequestBody
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import com.zmy.demo.service.FileService;

/**
 * 文件管理,员工上传证明材料、人事部查看证明材料
 */
@RestController
@RequestMapping("/file")
public class FileController {
    @Autowired private FileService service;

    // DONE
    /**
     * 保存用户名为userId的用户在填写请假单时上传的附件图片
     * @param userId 用户名
     * @param file 上传的附件图片
     */
    @PostMapping("/upload")
    @PreAuthorize("hasAnyRole('staff', 'manager', 'general', 'personnel')")
    public void upload(
            @RequestParam("userId") String userId,
            @RequestParam("file") MultipartFile file
    ) {
        service.upload(userId, file);
    }

    // DONE
    /**
     * 获取用户名为userId的用户上传的附件图片
     * @param userId 用户名
     * @return 用户名为userId的用户上传的附件图片
     */
    @GetMapping("/download")
    @PreAuthorize("hasAnyRole('staff', 'manager', 'general', 'personnel')")
    public ResponseEntity<byte[]> download(
            @RequestParam("userId") String userId
    ) {
        return service.download(userId);
    }
}
