package backend.service;

import org.springframework.core.io.Resource;

import backend.dto.FileContentRequestTypeDto;
import backend.dto.FileMetadataDto;

public interface FileService {
    FileMetadataDto getFileMetadata(String filename);

    Resource getFileContent(String filename);

    boolean supportFileContentRequestType(String filename, FileContentRequestTypeDto requestType);
}
