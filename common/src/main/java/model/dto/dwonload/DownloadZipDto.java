package model.dto.dwonload;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @program: fire
 * @description:
 * @author: fbl
 * @create: 2021-06-08 15:25
 **/
@Data
public class DownloadZipDto {

    @NotEmpty
    @Valid
    private List<DownLoadZipUrlDto> downLoadZipUrlDtos;
}
