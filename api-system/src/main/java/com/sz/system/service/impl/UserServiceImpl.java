package com.sz.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sz.system.dao.UserMapper;
import com.sz.system.pojo.entity.User;
import com.sz.system.service.UserService;
import org.springframework.stereotype.Service;


/**
 * @author wangqianping
 * @date 2022-09-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,User> implements UserService {


}
