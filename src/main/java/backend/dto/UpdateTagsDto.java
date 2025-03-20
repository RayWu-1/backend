package backend.dto;

import java.util.Set;
import lombok.Data;

@Data
public class UpdateTagsDto {
    private Set<Long> tagIds;
}