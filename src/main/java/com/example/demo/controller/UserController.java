package com.example.demo.controller;


import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import java.util.List;

import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import com.github.xiaoymin.knife4j.annotations.ApiSupport;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wyy
 * @since 2020-12-16
 */
@RestController
@RequestMapping("/user")
@ApiSupport(author = "yyyy",order = 1)
@Api(value = "测试",tags = "测试接口")
public class UserController {

    @Autowired
    UserService userService;


//    @ApiOperationSupport(order = 2)
    @ApiOperation("查询测试项目作者")
    @GetMapping("/getUserInformation")
    public List<User> getInfomation (){
        List<User> user = userService.getOne();
        return user;
    }


//    @ApiOperationSupport(order = 1)
    @ApiOperation("查询测试项目作者11")
    @GetMapping("/getUserInformation1")
    public List<User> getInfomation1 (){
        List<User> user = userService.getOne();
        return user;
    }
}

