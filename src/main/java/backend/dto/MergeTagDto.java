package backend.dto;

import lombok.Data;

@Data
public class MergeTagDto {
    private Long targetTagId;
    private Long sourceTagId;
}