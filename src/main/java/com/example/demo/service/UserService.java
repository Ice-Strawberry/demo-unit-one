package com.example.demo.service;

import com.example.demo.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wyy
 * @since 2020-12-16
 */
public interface UserService extends IService<User> {

    List<User> getOne ();
}
