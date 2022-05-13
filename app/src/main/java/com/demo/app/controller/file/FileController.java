package com.demo.app.controller.file;

import com.demo.app.service.file.DownloadService;
import com.demo.app.service.file.UploadService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import model.dto.dwonload.DownloadDto;
import model.dto.dwonload.DownloadZipDto;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import result.Result;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 类功能描述:　BasicController
 *
 * @author Eternal
 * @date 2019-56-15 14:56
 */
@RestController
@Api(value = "FileController", tags = "文件管理")
@RequestMapping("/api/file")
public class FileController {

    @Resource
    UploadService uploadService;

    @Resource
    DownloadService downloadService;

    @PostMapping(value = "/upload/picture")
    @ApiOperation("图片上传")
    public Result upload(@RequestParam("picture") MultipartFile picture) {
        String url = uploadService.uploadPicture(picture);
        return Result.success(url);
    }

    @PostMapping(value = "/upload/document")
    @ApiOperation("文件上传")
    public Result uploadFile(@RequestParam("document") MultipartFile document) {
        return Result.success(uploadService.uploadFile(document));
    }

    @PostMapping(value = "/test/upload/document")
    @ApiOperation("测试文件上传")
    public Result testUploadFile(@RequestParam("document") MultipartFile document) {
        return Result.success(uploadService.testUploadFile(document));
    }
    /**
     * 文件批量打包下载
     *
     * @param downloadZipDto 文件名称
     * @return result
     */
    @ApiOperation("文件批量打包下载")
    @PostMapping(value = "/download/zip")
    public void downloadZipFromUrl(@RequestBody @Valid DownloadZipDto downloadZipDto, HttpServletResponse response) {
        downloadService.downloadZipFromUrl(downloadZipDto, response);
    }

    /**
     * 文件下载
     *
     * @param downloadDto 下载路径
     * @return
     */
    @ApiOperation("文件下载")
    @PostMapping(value = "/download/document")
    public void downloadFile(@RequestBody @Valid DownloadDto downloadDto, HttpServletResponse response) {
        downloadService.downLoadFromUrl(downloadDto,response);
    }

}
