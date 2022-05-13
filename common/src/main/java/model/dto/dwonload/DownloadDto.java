package model.dto.dwonload;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 类功能描述:　文件下载数据传输层
 *
 * @author fbl
 * @date 2019-38-14 13:38
 */
@Data
public class DownloadDto {

    @NotBlank
    private String downloadUrl;

}
