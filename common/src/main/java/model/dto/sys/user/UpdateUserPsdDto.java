package model.dto.sys.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-02-02 08:38
 **/
@Data
public class UpdateUserPsdDto implements Serializable {

    @NotNull
    private Integer id;

    @NotBlank
    private String oldPassword;

    @NotBlank
    private String newPassword;


}
