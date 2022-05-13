package util;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @program: fire_control
 * @description:
 * @author: fbl
 * @create: 2021-02-04 09:37
 **/
@Data
public class SearchFilter {
    @NotNull
    private Integer pageSize;

    @NotNull
    private Integer pageNum;
    private JSONObject filters;
}
