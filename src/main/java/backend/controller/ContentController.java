package backend.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import backend.dto.ResponseDto;
import backend.entity.ContentEntity;
import backend.exception.BusinessException;
import backend.exception.ExceptionEnum;
import backend.service.ContentService;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@RestController
@RequestMapping("content")
public class ContentController {

    @Autowired
    private ContentService contentService;

    @GetMapping("/list")
    public ResponseDto<Page<ContentEntity>> getContentList(@RequestParam String page, @RequestParam String size) {
        try {
            int pageNumber = Integer.parseInt(page);
            int pageSize = Integer.parseInt(size);

            if (pageNumber < 0 || pageSize <= 0) {
                log.warn("Invalid pagination parameters: page={}, size={}", page, size);
                throw new BusinessException(ExceptionEnum.INVALID_ENTRY);
            }

            Pageable pageable = PageRequest.of(pageNumber, pageSize);

            return ResponseDto.success(contentService.getContentList(pageable));
        } catch (NumberFormatException e) {
            log.warn("Pagination parameters are not numbers: page={}, size={}", page, size);
            throw new BusinessException(ExceptionEnum.INVALID_ENTRY_TYPE);
        }
    }

}