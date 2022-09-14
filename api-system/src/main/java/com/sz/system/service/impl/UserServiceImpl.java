package com.sz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sz.system.dao.UserMapper;
import com.sz.system.pojo.dto.UserDTO;
import com.sz.system.pojo.entity.User;
import com.sz.system.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


/**
 * @author wangqianping
 * @date 2022-09-07
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;


    //todo 接口待完善
    @Override
    public void login(UserDTO userDTO) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, userDTO.getAccount());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new RuntimeException("账号不存在");
        }

        if (!userDTO.getPassword().equals(userDTO.getPassword())) {
            throw new RuntimeException("密码错误");
        }


    }
}
