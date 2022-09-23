package com.sz.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sz.common.exception.ExceptionEnum;
import com.sz.common.exception.ServiceException;
import com.sz.system.dao.UserMapper;
import com.sz.system.pojo.dto.PasswordDTO;
import com.sz.system.pojo.dto.UserDTO;
import com.sz.system.pojo.entity.User;
import com.sz.system.pojo.vo.LoginUserVO;
import com.sz.system.service.UserService;
import com.sz.system.util.JwtUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


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

    @Override
    public List<User> listUser(String account, String name) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotEmpty(account)) {
            queryWrapper.eq(User::getAccount, account);
        }
        if (StringUtils.isNotEmpty(name)) {
            queryWrapper.like(User::getName, name);
        }
        return userMapper.selectList(queryWrapper);
    }

    @Override
    public void updatePassword(PasswordDTO passwordDTO) throws ServiceException {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getId, passwordDTO.getId());
        User user = userMapper.selectOne(queryWrapper);
        if (!user.getPassword().equals(passwordDTO.getOldPassword())) {
            throw new ServiceException(ExceptionEnum.OLD_PASSWORD_ERROR);
        }
        user.setPassword(passwordDTO.getNewPassword());
        userMapper.updateById(user);
    }

    @Override
    public void updateUser(UserDTO userDTO) {
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(User::getId, userDTO.getId());

        if (StringUtils.isNotEmpty(userDTO.getName())) {
            updateWrapper.set(User::getName, userDTO.getName());
        }
        if (StringUtils.isNotEmpty(userDTO.getEmail())) {
            updateWrapper.set(User::getEmail, userDTO.getEmail());
        }
        if (StringUtils.isNotEmpty(userDTO.getPhone())) {
            updateWrapper.set(User::getPhone, userDTO.getPhone());
        }
        if (StringUtils.isNotEmpty(userDTO.getPassword())) {
            updateWrapper.set(User::getPassword, userDTO.getPassword());
        }
        userMapper.update(null, updateWrapper);
    }
}
