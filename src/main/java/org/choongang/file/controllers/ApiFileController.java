package org.choongang.file.controllers;

import lombok.RequiredArgsConstructor;
import org.choongang.commons.ExceptionRestProcessor;
import org.choongang.commons.exceptions.AlertBackException;
import org.choongang.commons.exceptions.CommonException;
import org.choongang.commons.rests.JSONData;
import org.choongang.file.entities.FileInfo;
import org.choongang.file.service.FileDeleteService;
import org.choongang.file.service.FileDownloadService;
import org.choongang.file.service.FileInfoService;
import org.choongang.file.service.FileUploadService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/file")
@RequiredArgsConstructor
public class ApiFileController implements ExceptionRestProcessor { // 통일된 방식으로 에러 응답

    private final FileUploadService uploadService;
    private final FileDownloadService downloadService;
    private final FileDeleteService deleteService;
    private final FileInfoService infoService;

    @PostMapping
    public JSONData<List<FileInfo>> upload(@RequestParam("file") MultipartFile[] files,
                                           @RequestParam(name = "gid", required = false) String gid,
                                           // gid는 필수, 없으면 기본값을 넣는다. location은 필수는 아니다
                                           @RequestParam(name = "location", required = false) String location,
                                           @RequestParam(name = "imageOnly", required = false) boolean imageOnly,
                                           @RequestParam(name = "singleFile", required = false) boolean singleFile) {

        List<FileInfo> uploadedFiles = uploadService.upload(files, gid, location, imageOnly, singleFile);


        return new JSONData<>(uploadedFiles);
    }

    /**
     * 파일 다운로드 컨트롤러
     */
    @ResponseBody
    @GetMapping("/download/{seq}")
    public void download(@PathVariable("seq") Long seq) {
        try {
            downloadService.download(seq);
        } catch (CommonException e) {
            throw new AlertBackException(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/delete/{seq}")
    public JSONData<Long> delete(@PathVariable("seq") Long seq) {
        deleteService.delete(seq);

        return new JSONData<>(seq);
    }

    /**
     * 특정 위치에서 업로드한 파일 목록 반환
     */
    @GetMapping("/getList")
    public JSONData<Object> getList(@RequestParam(name = "gid")String gid, @RequestParam(name = "location")String location) {
        List<FileInfo> items = infoService.getListDone(gid, location);

        return new JSONData<>(items);
    }
}