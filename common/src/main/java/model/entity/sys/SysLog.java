package model.entity.sys;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @program: fire_control
 * @description: 用户操作日志
 * @author: fbl
 * @create: 2021-02-05 10:47
 **/
@Data
@TableName("sys_log")
public class SysLog {
    @TableId(type = IdType.AUTO)
    private Integer id;

    @TableField("user_name")
    private String userName;

    private String operation;

    private String method;

    private String params;

    private String ip;

    @TableField("create_time")
    private Timestamp createTime;
}
