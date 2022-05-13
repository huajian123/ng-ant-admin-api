package com.demo.app.service.file;

import model.dto.dwonload.DownLoadZipUrlDto;
import model.dto.dwonload.DownloadDto;
import model.dto.dwonload.DownloadZipDto;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.io.*;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * @program: emergency
 * @description:
 * @author: fbl
 * @create: 2020-11-03 10:57
 **/
@Component
public class DownloadService {


    @Value("${spring.servlet.multipart.location}")
    private String path;

    /**
     * 从网络Url中下载文件
     *
     * @param downloadDto
     * @throws IOException
     */
    public void downLoadFromUrl(DownloadDto downloadDto, HttpServletResponse response) {
        @NotBlank String downloadUrl = downloadDto.getDownloadUrl();

        int lastIndexOf = downloadUrl.lastIndexOf('/');
        String fileName = downloadUrl.substring(lastIndexOf + 1);

        FileInputStream fileIn = null;
        ServletOutputStream out = null;
        try {
            response.setContentType("application/octet-stream");
            // URLEncoder.encode(fileNameString, "UTF-8") 下载文件名为中文的，文件名需要经过url编码
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
            File file;
            String filePathString = path + fileName;
            file = new File(filePathString);
            fileIn = new FileInputStream(file);
            out = response.getOutputStream();

            byte[] outputByte = new byte[1024];
            int readTmp = 0;
            while ((readTmp = fileIn.read(outputByte)) != -1) {
                //并不是每次都能读到1024个字节，所有用readTmp作为每次读取数据的长度，否则会出现文件损坏的错误
                out.write(outputByte, 0, readTmp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fileIn.close();
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 下载压缩包
     *
     * @param downloadZipDto
     * @param response
     * @throws IOException
     */
    public void downloadZipFromUrl(DownloadZipDto downloadZipDto, HttpServletResponse response) {
        List<DownLoadZipUrlDto> downLoadZipUrlDtos = downloadZipDto.getDownLoadZipUrlDtos();

        // 创建临时路径,存放压缩文件
        String zipFilePath = path + "\\11.zip";
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            // 压缩输出流,包装流,将临时文件输出流包装成压缩流,将所有文件输出到这里,打成zip包
            ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream(zipFilePath));
            // 循环调用压缩文件方法,将一个一个需要下载的文件打入压缩文件包
            for (DownLoadZipUrlDto downLoadZipUrlDto : downLoadZipUrlDtos) {
                String url = downLoadZipUrlDto.getUrl();

                int lastIndexOf = url.lastIndexOf('/');
                String name = url.substring(lastIndexOf + 1);
                String newPath = path + "\\" + name;
                // 该方法在下面定义
                fileToZip(newPath, zipOut);
            }

            zipOut.flush();
            // 压缩完成后,关闭压缩流
            zipOut.close();
            response.setHeader("Content-Disposition", "attchment;filename=" + "1.zip");
            //该流不可以手动关闭,手动关闭下载会出问题,下载完成后会自动关闭
            FileInputStream inputStream = new FileInputStream(zipFilePath);
            // 如果是SpringBoot框架,在这个路径
            // org.apache.tomcat.util.http.fileupload.IOUtils产品
            // 否则需要自主引入apache的 commons-io依赖
            // copy方法为文件复制,在这里直接实现了下载效果
            IOUtils.copy(inputStream, outputStream);
            outputStream.close();
            // 关闭输入流
            inputStream.close();
            //下载完成之后，删掉这个zip包
            File fileTempZip = new File(zipFilePath);
            fileTempZip.delete();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void fileToZip(String filePath, ZipOutputStream zipOut) {
        // 需要压缩的文件
        File file = new File(filePath);
        // 获取文件名称,如果有特殊命名需求,可以将参数列表拓展,传fileName
        String fileName = file.getName();
        FileInputStream fileInput = null;
        BufferedInputStream bufferStream = null;
        try {
            fileInput = new FileInputStream(filePath);
            // 缓冲
            byte[] bufferArea = new byte[1024];
            bufferStream = new BufferedInputStream(fileInput);

            // 将当前文件作为一个zip实体写入压缩流,fileName代表压缩文件中的文件名称
            zipOut.putNextEntry(new ZipEntry(fileName));
            int length = 0;
            // 最常规IO操作,不必紧张
            while ((length = bufferStream.read(bufferArea)) != -1) {
                zipOut.write(bufferArea, 0, length);
            }
            // 解决剩余的
            int remain = bufferStream.available();
            byte[] last = new byte[remain];
            bufferStream.read(last);
            zipOut.write(last);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流
                fileInput.close();
                // 需要注意的是缓冲流必须要关闭流,否则输出无效
                bufferStream.close();
                // 压缩流不必关闭,使用完后再关
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
