package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: demo-unit-one
 * @description: 第一个测试类
 * @author: Wyy
 * @create_time: 2020-12-16 10:47
 * @modifier：Wyy
 * @modification_time：2020-12-16 10:47
 **/
@RestController
@RequestMapping("/first")
public class NumberOneController {

    @GetMapping("/getOne")
    public String getOne(){
        return "你好，我的世界！";
    }
}
