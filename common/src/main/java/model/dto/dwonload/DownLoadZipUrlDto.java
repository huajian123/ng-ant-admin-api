package model.dto.dwonload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: fire
 * @description:
 * @author: fbl
 * @create: 2021-06-24 17:57
 **/
@Data
public class DownLoadZipUrlDto {

    @NotNull
    private Integer id;

    @NotBlank
    private String url;
}
