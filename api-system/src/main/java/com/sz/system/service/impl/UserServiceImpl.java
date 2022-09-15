package com.sz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sz.common.exception.ExceptionEnum;
import com.sz.common.exception.ServiceException;
import com.sz.system.dao.UserMapper;
import com.sz.system.pojo.dto.UserDTO;
import com.sz.system.pojo.entity.User;
import com.sz.system.pojo.vo.LoginUserVO;
import com.sz.system.service.UserService;
import com.sz.system.util.JwtUtil;
import org.springframework.beans.BeanUtils;
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


    @Override
    public LoginUserVO login(UserDTO userDTO) throws ServiceException {

        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getAccount, userDTO.getAccount());
        User user = userMapper.selectOne(queryWrapper);
        if (user == null) {
            throw new ServiceException(ExceptionEnum.ACCOUNT_NOT_EXIST);
        }

        //todo 密码校验待调整，数据库不能存储明文密码
        if (!userDTO.getPassword().equals(user.getPassword())) {
            throw new ServiceException(ExceptionEnum.PASSWORD_ERROR);
        }

        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        //todo 待完善
        if (user.getAccount().equals("admin")) {
            loginUserVO.setIsAdmin(true);
        }
        String token = JwtUtil.createToken(user);
        loginUserVO.setToken(token);
        return loginUserVO;

    }
}
