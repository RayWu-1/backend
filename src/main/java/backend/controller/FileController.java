package backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.FileContentRequestTypeDto;
import backend.dto.FileMetadataDto;
import backend.dto.ResponseDto;
import backend.exception.BusinessException;
import backend.exception.ExceptionEnum;
import backend.service.FileService;

@RestController
@RequestMapping("/files")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/{filename}/metadata")
    public ResponseDto<FileMetadataDto> getFileMetadata(@PathVariable String filename) {
        if (filename == null) {
            throw new BusinessException(ExceptionEnum.MISSING_PARAMETERS);
        }
        return new ResponseDto<>(fileService.getFileMetadata(filename));
    }

    // Range is a request header that specifies the range of bytes to retrieve from
    // the file. The Range header is automatically handled by Spring when we return
    // a Resource object from a controller method.
    @GetMapping("/{filename}/content")
    public ResponseEntity<Resource> getFileContent(
            @RequestHeader(value = "Range", required = false) String range,
            @PathVariable String filename) {
        if (filename == null) {
            throw new BusinessException(ExceptionEnum.MISSING_PARAMETERS);
        }

        // Check if the request type is supported for the file type
        FileContentRequestTypeDto requestType = range == null ? FileContentRequestTypeDto.FULL
                : FileContentRequestTypeDto.RANGE;
        if (!fileService.supportFileContentRequestType(filename, requestType)) {
            throw new BusinessException(ExceptionEnum.INVALID_FILE_REQUEST_TYPE);
        }

        FileMetadataDto fileMetadataDto = fileService.getFileMetadata(filename);
        MediaType mediaType = MediaType.parseMediaType(fileMetadataDto.getType());

        Resource resource = fileService.getFileContent(filename);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + fileMetadataDto.getName() + "\"")
                .contentType(mediaType)
                .body(resource);
    }
}
