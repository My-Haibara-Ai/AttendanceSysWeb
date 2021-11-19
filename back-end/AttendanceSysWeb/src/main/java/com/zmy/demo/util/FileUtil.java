package com.zmy.demo.util;

import java.io.*;

import org.springframework.http.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 处理文件的工具类
 */
public class FileUtil {
    /**
     * 将文件file保存到目录dir中
     * @param file 要保存的文件
     * @param dir 文件保存的目录,目录必须存在
     */
    public void saveFile(MultipartFile file, File dir) {
        // file对应的本地文件
        File localFile = new File(dir, file.getOriginalFilename());

        try (
            OutputStream out = new FileOutputStream((localFile));
        ) {
            // 将file的字节复制到localFile中
            localFile.createNewFile();
            out.write(file.getBytes());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 将目录dir下文件名为filename的文件封装到Http响应体中
     * @param filename 要获取的文件的文件名
     * @param dir 文件所在目录,目录必须存在
     * @return 封装好文件的Http响应体
     */
    public ResponseEntity<byte[]> sendFile(String filename, File dir) {
        File file = new File(dir, filename);
        byte[] bytes = toBytes(file);
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.IMAGE_PNG);

        return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
    }

    // 将文件file中的内容转换为字节数组返回
    private byte[] toBytes(File file) {
        byte[] bytes = new byte[1024];

        try (
                InputStream in = new FileInputStream(file);
                ByteArrayOutputStream out = new ByteArrayOutputStream()
        ) {
            int cnt;

            // 从in中读入字节并写入out中
            while ((cnt = in.read(bytes)) != -1) {
                out.write(bytes, 0, cnt);
            }

            return out.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
