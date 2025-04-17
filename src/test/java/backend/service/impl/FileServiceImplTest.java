package backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import backend.config.FileProperties;
import backend.dto.FileMetadataDto;
import backend.entity.FileEntity;
import backend.exception.BusinessException;
import backend.exception.ExceptionEnum;
import backend.repository.FileRepository;

@ExtendWith(MockitoExtension.class)
public class FileServiceImplTest {

    @Mock
    private FileRepository fileRepository;

    @TempDir
    private Path storageLocation;

    private FileServiceImpl fileService;

    @BeforeEach
    void setUp() {
        FileProperties fileProperties = new FileProperties();
        fileProperties.setStorageLocation(storageLocation.toString());
        fileService = new FileServiceImpl(fileProperties, fileRepository);
    }

    private FileEntity createFileEntity() {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setId(123L);
        fileEntity.setOriginalFilename("test-file.txt");
        fileEntity.setStoredFilename("13579.txt");
        fileEntity.setContentType("text/plain");
        fileEntity.setSize(12);
        return fileEntity;
    }

    private Path createTestFile(FileEntity fileEntity, String content) throws IOException {
        Path filePath = storageLocation.resolve(fileEntity.getStoredFilename());
        Files.createFile(filePath);
        Files.writeString(filePath, content);
        return filePath;
    }

    @Test
    public void testGetFileMetadata_Success() throws IOException {
        FileEntity fileEntity = createFileEntity();
        createTestFile(fileEntity, "test content");
        String filename = fileEntity.getStoredFilename();

        when(fileRepository.findByStoredFilename(filename)).thenReturn(Optional.of(fileEntity));

        FileMetadataDto fileMetadataDto = fileService.getFileMetadata(filename);

        assertEquals(fileEntity.getOriginalFilename(), fileMetadataDto.getName());
        assertEquals(fileEntity.getContentType(), fileMetadataDto.getType());
        assertEquals((long) fileEntity.getSize(), fileMetadataDto.getSize());
        assertEquals("/files/" + filename + "/content", fileMetadataDto.getUrl());
    }

    @Test
    public void testGetFileMetadata_FileNotFound() {
        String filename = "test.pdf";
        when(fileRepository.findByStoredFilename(filename)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            fileService.getFileMetadata(filename);
        });

        assertEquals(ExceptionEnum.FILE_NOT_FOUND.getCode(), exception.getCode());
    }

    @Test
    public void testGetFileContent_Success() throws IOException {
        FileEntity fileEntity = createFileEntity();
        String filename = fileEntity.getStoredFilename();

        Path filePath = createTestFile(fileEntity, "test content");

        when(fileRepository.findByStoredFilename(filename)).thenReturn(Optional.of(fileEntity));

        Resource resource = fileService.getFileContent(filename);

        assertInstanceOf(UrlResource.class, resource);
        assertEquals(filePath.toUri(), resource.getURI());
    }

    @Test
    public void testGetFileContent_FileNotFound() {
        String filename = "test.pdf";
        when(fileRepository.findByStoredFilename(filename)).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> {
            fileService.getFileContent(filename);
        });

        assertEquals(ExceptionEnum.FILE_NOT_FOUND.getCode(), exception.getCode());
    }
}