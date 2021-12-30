package com.kuang.dao;

import com.kuang.pojo.User;

public interface UserMapper {

    //根据ID查询用户
    User getUserById(int id);

}
