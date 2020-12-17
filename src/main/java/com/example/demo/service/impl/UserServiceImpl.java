package com.example.demo.service.impl;

import com.example.demo.entity.User;
import com.example.demo.dao.UserDao;
import com.example.demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wyy
 * @since 2020-12-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

    @Autowired
    UserDao userDao;
    @Override
    public List<User> getOne() {

        return userDao.getOne();
    }
}
