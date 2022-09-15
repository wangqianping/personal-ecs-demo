package com.sz.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.sz.common.exception.ServiceException;
import com.sz.system.pojo.dto.UserDTO;
import com.sz.system.pojo.entity.User;
import com.sz.system.pojo.vo.LoginUserVO;


/**
 * @author wangqianping
 * @date 2022-09-07
 */
public interface UserService extends IService<User> {

    /**
     * 登入
     * @param userDTO
     * @throws ServiceException
     */
    LoginUserVO login(UserDTO userDTO) throws ServiceException;
}
