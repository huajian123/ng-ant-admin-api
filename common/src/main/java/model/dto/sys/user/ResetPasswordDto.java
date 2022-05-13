package model.dto.sys.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-02-05 14:19
 **/
@Data
public class ResetPasswordDto {

    @NotNull
    private Integer id;

    @NotBlank
    private String password;
}
