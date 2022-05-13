package com.demo.app.mapper.user;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import model.entity.sys.SysUser;
import model.dto.sys.user.SearchUserDto;
import model.vo.sys.DetailUserVo;
import model.vo.sys.SelectUserVo;

import java.util.List;

/**
 * @Author fanbaolin
 * @Description
 * @Date 8:33 2021/1/12
 */
public interface UserMapper extends BaseMapper<SysUser> {

    List<SelectUserVo> listUser(SearchUserDto searchUserDto);

    DetailUserVo selectUserVoById(Integer id);

}
