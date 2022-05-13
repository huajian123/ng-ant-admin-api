package com.demo.app.service.file;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import result.Result;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 类功能描述:　UploadManager
 *
 * @author Eternal
 * @date 2019年11月9日13:48:27
 */
@Component
public class UploadService {

    @Value("${spring.servlet.multipart.location}")
    private String path;


    @Value("${server.port}")
    private String port;

    @Value("${fbl.uploadPath}")
    private String uploadPath;

    /**
     * 上传图片
     *
     * @param picture
     * @return
     */
    public String uploadPicture(MultipartFile picture) {
        String fileName = getPictureName(picture);
        File file = new File(path + fileName);
        return transferToFile(picture, file, fileName);
    }

    public File getPath(MultipartFile excel) {
        String fileName = getPictureName(excel);
        File filePath = new File(path + fileName);

        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }

        try {
            excel.transferTo(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return filePath;
    }

    /**
     * 上传文件
     *
     * @param word
     * @return
     */
    public Map<String, String> uploadFile(MultipartFile word) {
        return getFileName(word);
    }

    public Result testUploadFile(MultipartFile word){
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip = localHost.getHostAddress();
        return Result.success("http://" + ip + ":" + port + uploadPath + "图纸实际材料量导入模板.xlsx");
    }

    private String getPictureName(MultipartFile file) {
        //获取图片类型
        String exName = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //图片重命名
        String fileName = UUID.randomUUID().toString().replaceAll("-", "") + exName;
        return fileName;
    }

    private HashMap<String, String> getFileName(MultipartFile file) {
        //获取文件类型
        String exName = file.getOriginalFilename();
        File newFile = new File(path, exName);
        for (int i = 1; newFile.exists() && i < Integer.MAX_VALUE; i++) {
            newFile = new File(path, toPrefix(exName) + '(' + i + ')' + toSuffix(exName));
        }
        String url = transferToFile(file, newFile, newFile.getName());
        HashMap<String, String> stringStringHashMap = new HashMap<>(2);
        stringStringHashMap.put("url", url);
        stringStringHashMap.put("fileName", newFile.getName());
        return stringStringHashMap;
    }

    /**
     * 前缀
     *
     * @param name
     * @return
     */
    private String toPrefix(String name) {
        return name.substring(0,name.lastIndexOf("."));
    }

    /**
     * 后缀
     *
     * @param name
     * @return
     */
    private String toSuffix(String name){
        return name.substring(name.lastIndexOf("."));
    }

    private String transferToFile(MultipartFile word, File file, String fileName) {
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            word.transferTo(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        InetAddress localHost = null;
        try {
            localHost = Inet4Address.getLocalHost();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        String ip = localHost.getHostAddress();
        return "http://" + ip + ":" + port + uploadPath + fileName;
    }

}
